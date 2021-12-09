package controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/check")
public class ControllerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] xVals = req.getParameterValues("x");
        String[] yVals = req.getParameterValues("y");
        String[] RVals = req.getParameterValues("r");

        if (xVals != null && yVals != null && RVals != null) {
            req.getRequestDispatcher("/areaCheck").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {

    }

}