package model;

public class Dot {

    private double x;
    private double y;
    private double R;
    private boolean atArea;

    public Dot(double x, double y, double R, boolean hit){
        this.atArea = hit;
        this.x = x;
        this.y = y;
        this.R = R;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getR() {
        return R;
    }

    public void setR(int r) {
        R = r;
    }

    public boolean getAtArea() {
        return atArea;
    }

    public void setAtArea(boolean atArea) {
        this.atArea = atArea;
    }
}