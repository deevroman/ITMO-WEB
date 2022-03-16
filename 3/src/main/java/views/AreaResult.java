package views;

import model.Dot;

//Проверка на попадание точки в область
public class AreaResult {

    public AreaResult() {
    }


    //Проверка по самой точке (В виде объекта)
    public static boolean isItInArea(Dot dot) {
        try {
            return isItInArea(dot.getX(), dot.getY(), dot.getR());
        } catch (NullPointerException e) {
            return false;
        }
    }

    //Проверка точки по её координатам
    public static boolean isItInArea(double x, double y, double r) {
        return (x >= 0 && y >= 0 && y <= -x + r / 2) || //треугольник
                //прямоугольник
                (x <= 0 && y <= 0 && x >= -r && y >= -r / 2) ||
                //четверть круга
                (x >= 0 && y <= 0 && x * x + y * y <= (r / 2) * (r / 2));
    }

}