

import model.Dot;

import javax.enterprise.inject.Model;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Model
public class DBManager implements Serializable {

    //Добавить точку в БД
    public boolean addPoint(Dot dot) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://pg:5432/studs", "s311693", "");
            PreparedStatement preparedStatement
                    = connection.prepareStatement("INSERT INTO s311693.lab3 (id, x, y, r, \"currentTime\", \"hit\") VALUES (DEFAULT, ?, ?, ?, ?, ?);");
            preparedStatement.setString(1, String.valueOf(dot.getX()));
            preparedStatement.setString(2, String.valueOf(dot.getY()));
            preparedStatement.setString(3, String.valueOf(dot.getR()));
            preparedStatement.setString(4, dot.getCurrentTime());
            preparedStatement.setString(5, dot.getAtArea());
            preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public ArrayList<Dot> getPoints() {
        Connection connection = null;
        try {
            ArrayList<Dot> ans = new ArrayList<Dot>();
            connection = DriverManager.getConnection("jdbc:postgresql://pg:5432/studs", "s311693", "mfu631");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * from lab3");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Dot dot = new Dot();
                dot.setX(Double.parseDouble(resultSet.getString("x")));
                dot.setY(Double.parseDouble(resultSet.getString("y")));
                dot.setR(Integer.parseInt(resultSet.getString("r")));
                dot.setCurrentTime(resultSet.getString("currentTime"));
                dot.setAtArea(resultSet.getString("hit"));
                ans.add(dot);
            }
            return ans;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<Dot>();
    }

    public void clearTable(){
        Connection connection = null;
        try {
            ArrayList<Dot> ans = new ArrayList<Dot>();
            connection = DriverManager.getConnection("jdbc:postgresql://pg:5432/studs", "s311693", "mfu631");
            PreparedStatement preparedStatement = connection.prepareStatement("TRUNCATE TABLE lab3");
            ResultSet resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
