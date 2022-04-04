package Business;

import Entity.LogpassEntity;
import Entity.PointsEntity;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.NativeQuery;
import org.hibernate.service.ServiceRegistry;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;

@Stateless
public class DataBaseManager {

    private static final String TABLE_NAME = "users4";
    static SessionFactory sessionFactoryObj;
    static Session sessionObj;
    static SessionFactory sessionFactoryLogin;
    static Session sessionLogin;
    @EJB
    CheckAuth checkAuth;

    private static SessionFactory buildSessionFactory() {
        if (sessionFactoryObj != null) {
            return sessionFactoryObj;
        }
        // Creating Configuration Instance & Passing Hibernate Configuration File
        Configuration configObj = new Configuration();
        configObj.configure("hibernate.cfg.xml");
        configObj.addAnnotatedClass(LogpassEntity.class);
        configObj.addAnnotatedClass(PointsEntity.class);


        // Since Hibernate Version 4.x, ServiceRegistry Is Being Used
        ServiceRegistry serviceRegistryObj = new StandardServiceRegistryBuilder().applySettings(configObj.getProperties()).build();

        // Creating Hibernate SessionFactory Instance
        sessionFactoryObj = configObj.buildSessionFactory(serviceRegistryObj);
        return sessionFactoryObj;
    }


    public ArrayList<PointsEntity> getCollectionFromDataBase(String login) {
        ArrayList result = new ArrayList<>();
        sessionObj = buildSessionFactory().openSession();
        NativeQuery query = sessionObj.createSQLQuery("select * from points where login=:login");
        query.setString("login", login);
        query.addEntity(PointsEntity.class);
        List<PointsEntity> collection = query.list();
        collection.stream().forEach(el -> {
            System.out.println(el.getX() + " " + el.getY() + " " + " " + el.getR() + " " + el.getResult());
            result.add(el);
        });
        sessionObj.close();
        return result;
    }

    public void addPoint(Object object) {
        sessionObj = buildSessionFactory().openSession();
        sessionObj.beginTransaction();
        sessionObj.save(object);
        sessionObj.getTransaction().commit();
        sessionObj.close();
    }

    public void removeAllPoints(String token) {
        sessionObj = buildSessionFactory().openSession();
        sessionObj.beginTransaction();
        String login = checkAuth.getUserByToken(token).getLogin();
        NativeQuery query = sessionObj.createSQLQuery("delete from points where login=:login");
        query.setString("login", login);
        query.executeUpdate();
        sessionObj.getTransaction().commit();
        sessionObj.close();
    }

    public String registration(String user, String pass) {
        if (user_is_exist(user)) {
            return "User already exists";
        } else {
            sessionLogin = buildSessionFactory().openSession();
            LogpassEntity logpassEntity = new LogpassEntity();
            logpassEntity.setLogin(user);
            logpassEntity.setPass(pass);
            logpassEntity.generateToken();
            sessionLogin.beginTransaction();
            sessionLogin.save(logpassEntity);
            sessionLogin.getTransaction().commit();
            sessionLogin.close();
            return logpassEntity.getToken();
        }
    }

    public boolean user_is_exist(String user) {
        System.out.println();
        LogpassEntity logpassEntity;
        try {
            sessionLogin = buildSessionFactory().openSession();
            logpassEntity = sessionLogin.get(LogpassEntity.class, user);
            if (logpassEntity == null) throw new NullPointerException();
            return true;
        } catch (ObjectNotFoundException ex) {
            ex.printStackTrace();
            return false;
        } catch (NullPointerException e) {
            System.out.println("NULL");
            return false;
        } finally {
            sessionLogin.close();
        }
    }

    public String authorization(String user, String pass) {
        if (user_is_exist(user)) {
            sessionLogin = buildSessionFactory().openSession();
            sessionLogin.beginTransaction();
            LogpassEntity logpassEntity = sessionLogin.get(LogpassEntity.class, user);
            sessionLogin.getTransaction().commit();
            try {
                if (logpassEntity.getPass().equals(pass)) {
                    return checkAuth.getTokenByUser(user);
                } else {
                    return "Not authorized";
                }
            } catch (NullPointerException e) {
                return "No such user found";
            } finally {
                sessionLogin.close();
            }
        } else {
            return "No such user found";
        }
    }
}