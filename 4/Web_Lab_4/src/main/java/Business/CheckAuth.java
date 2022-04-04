package Business;

import Entity.LogpassEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Stateless
public class CheckAuth {
    LogpassEntity logpassEntity;

    public boolean checkAuth(LogpassEntity user, String base64) {
        try {
            String token = new String(Base64.getDecoder().decode(base64), StandardCharsets.UTF_8);
            String[] split = token.split(String.valueOf((char) (31)));
            String accessToken = split[1];
            return user.getToken().equals(accessToken);
        } catch (Throwable e) {
            return false;
        }
    }

    public boolean checkAuth(String token) {
        try {
            LogpassEntity user = getUserByToken(token);
            return user.getToken().equals(token);
        } catch (Throwable e) {
            return false;
        }
    }

    public String getEntries(String base64) {
        if (!checkAuth(getUserByToken(base64), base64)) {
            return "Unauthorized";
        }
        return base64;
    }

    public String getTokenByUser(String username) {
        EntityManager entityManager = Persistence.
                createEntityManagerFactory("default").
                createEntityManager();
        try {
            return entityManager.createNamedQuery("findByLogin", LogpassEntity.class)
                    .setParameter("login", username).getResultStream()
                    .findAny().orElse(null).getToken();
        } finally {
            entityManager.close();
        }
    }

    public LogpassEntity getUserByToken(String token) {
        EntityManager entityManager = Persistence.
                createEntityManagerFactory("default").
                createEntityManager();
        try {
            return entityManager.createNamedQuery("findByToken", LogpassEntity.class)
                    .setParameter("token", token).getResultStream()
                    .findAny().orElse(null);
        } finally {
            entityManager.close();
        }
    }
}
