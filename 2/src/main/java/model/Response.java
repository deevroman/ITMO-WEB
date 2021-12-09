package model;


public class Response {
    public double x;

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getR() {
        return r;
    }

    public String getAtArea() {
        return atArea;
    }

    public long getTime() {
        return time;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public double y;
    public double r;
    public String atArea; // TODO

    public long time;
    public String currentTime;

    public Response(){
        this.time = 0L;
        this.currentTime = "";
    }

    public Response(Dot dot, long workingTime, String currentTime){
        this.x = dot.getX();
        this.y = dot.getY();
        this.r = dot.getR();
        this.atArea = dot.getAtArea() ? "Попадание" : "Промах";
        this.time = workingTime;
        this.currentTime = currentTime;
    }
}