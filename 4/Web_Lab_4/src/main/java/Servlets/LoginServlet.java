package Servlets;


import Business.DataBaseManager;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/auth/login")
public class LoginServlet extends HttpServlet {
    @EJB
    private DataBaseManager dataBaseManager;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("login");
        String password = req.getParameter("password");
        try {
            String result = dataBaseManager.authorization(username, password);
            if (result.equals("No such user found") || result.equals("Not authorized")) {
                resp.setStatus(403);
            }
            resp.getWriter().write(result);
        } catch (Exception e) {
            resp.setStatus(401);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}