package Servlets;

import Entity.PointsEntity;
import Business.DataBaseManager;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringJoiner;


@WebServlet("/AreaCheck")
public class AreaCheckServlet extends HttpServlet {
    @EJB
    private DataBaseManager dataBaseManager;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        double x = Double.parseDouble(request.getParameter("x"));
        double y = Double.parseDouble(request.getParameter("y"));
        double r = Double.parseDouble(request.getParameter("r"));
        String login = request.getAttribute("login").toString();
        System.out.println("x=" + x + " y=" + y + " r=" + r);
        boolean result = CheckSquare(x, y, r) || CheckQuarterCircle(x, y, r) || CheckTriangle(x, y, r);
        System.out.println(result);
        PointsEntity pointsEntity = new PointsEntity();
        pointsEntity.setR(r);
        pointsEntity.setX(x);
        pointsEntity.setY(y);
        pointsEntity.setResult(result);
        pointsEntity.setLogin(login);
        dataBaseManager.addPoint(pointsEntity);

        ArrayList<PointsEntity> entries = dataBaseManager.getCollectionFromDataBase(login);
        entries.forEach(element -> System.out.println(element.getResult()));
        StringJoiner joiner = new StringJoiner(",");
        for (PointsEntity entry : entries) {
            String stringBuilder = String.format("{\"x\":\"%s\", \"y\":\"%s\", \"r\":\"%s\", \"result\":%s}",
                    entry.getX().toString(),
                    entry.getY().toString(),
                    entry.getR().toString(),
                    entry.getResult());
            joiner.add(stringBuilder);
        }
        response.getWriter().write("[" + joiner.toString() + "]");
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    private static boolean CheckSquare(double x, double y, double r) {
        return -r <= x && x <= 0 && 0 <= y && y <= r / 2;
    }

    private static boolean CheckQuarterCircle(double x, double y, double r) {
        return (x <= 0) && (y <= 0) && ((x * x + y * y) <= (r / 2) * (r / 2));
    }

    private static boolean CheckTriangle(double x, double y, double r) {
        return x >= 0 && x <= r / 2 && y <= 0 && y >= -r / 2 && (x + -y <= r / 2);
    }
}
