package model;

public class Request {

    private final Double[] x;
    private final Double[] y;
    private final Double[] r;

    public Request(Double[] x, Double[] y, Double[] r){
        this.x = x;
        this.y = y;
        this.r = r;
    }

    public Double[] getX() {
        return x;
    }

    public Double[] getY() {
        return y;
    }

    public Double[] getR(){
        return r;
    }

}