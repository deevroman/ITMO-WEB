package Servlets;

import Entity.PointsEntity;
import Business.CheckAuth;
import Business.DataBaseManager;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringJoiner;


@WebServlet("/controller")
public class ControllerServlet extends HttpServlet {
    @EJB
    private CheckAuth auth;
    @EJB
    private DataBaseManager dataBaseManager;
    double[] arrayXR = {-3, -2, -1, 0, 1, 2, 3, 4, 5};

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String token = request.getParameter("token");
        try {
            double x = Double.parseDouble(request.getParameter("x"));
            double y = Double.parseDouble(request.getParameter("y"));
            double r = Double.parseDouble(request.getParameter("r"));
            if (Arrays.asList(arrayXR).contains(x) || Arrays.asList(arrayXR).contains(r) /*|| y > 3 || y < -5 || r <= 0*/)
                throw new NumberFormatException();
            if (auth.checkAuth(token) == false) throw new NullPointerException();
            request.setAttribute("login", auth.getUserByToken(token).getLogin());
            getServletContext().getRequestDispatcher("/AreaCheck").forward(request, response);
        } catch (NumberFormatException | NullPointerException e) {
            response.setStatus(403);
            response.getWriter().write(String.valueOf(auth.checkAuth(token)));
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String token = request.getParameter("token");
        dataBaseManager.removeAllPoints(token);
        ArrayList<PointsEntity> entries = dataBaseManager.getCollectionFromDataBase(token);
        StringJoiner joiner = new StringJoiner(",");
        for (PointsEntity entry : entries) {
            String stringBuilder = String.format("{\"x\":\"%s\", \"y\":\"%s\", \"r\":\"%s\", \"result\":%s}",
                    entry.getX().toString(),
                    entry.getY().toString(),
                    entry.getR().toString(),
                    entry.getResult());
            joiner.add(stringBuilder);
        }
        response.getWriter().write("[" + joiner + "]");
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String token = request.getParameter("token");
        ArrayList<PointsEntity> entries = dataBaseManager.getCollectionFromDataBase(auth.getUserByToken(token).getLogin());
        StringJoiner joiner = new StringJoiner(",");
        for (PointsEntity entry : entries) {
            String stringBuilder = String.format("{\"x\":\"%s\", \"y\":\"%s\", \"r\":\"%s\", \"result\":%s}",
                    entry.getX().toString(),
                    entry.getY().toString(),
                    entry.getR().toString(),
                    entry.getResult());
            joiner.add(stringBuilder);
        }
        response.getWriter().write("[" + joiner + "]");
    }
}