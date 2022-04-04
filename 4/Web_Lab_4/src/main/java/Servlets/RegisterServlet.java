package Servlets;

import Business.DataBaseManager;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/auth/register")
public class RegisterServlet extends HttpServlet{
    @EJB
    private DataBaseManager dataBaseManager;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("login");
        String password = req.getParameter("password");
        String result = dataBaseManager.registration(username, password);
        if(result.equals("User already exists")){
            resp.setStatus(403);
        }
        resp.getWriter().write(result);
    }
}