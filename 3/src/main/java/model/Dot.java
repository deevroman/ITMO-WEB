package model;

public class Dot {

    private Double x;
    private Double y;
    private Integer r;
    private String atArea;

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public Integer getR() {
        return r;
    }

    public void setR(Integer r) {
        this.r = r;
    }

    public String getAtArea() {
        return atArea;
    }

    public void setAtArea(String atArea) {
        this.atArea = atArea;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    private String currentTime;

    public Dot(double x, double y, Integer r, String hit){
        this.atArea = hit;
        this.x = x;
        this.y = y;
        this.r = r;
    }

    public Dot() {

    }

}