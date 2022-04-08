package vidmot;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;


public class Snakur extends Rectangle{

    private int stefna;
    private double hradi;
    private int haliStaerd = 0;
    public ArrayList<Rectangle> snakurHali;
    private ConcurrentLinkedQueue<Double> xHali;
    private ConcurrentLinkedQueue<Double> yHali;

    //getters og setters
    public int getStefna() {
        return stefna;
    }
    public ConcurrentLinkedQueue<Double> getxHali() {
        return xHali;
    }
    public ConcurrentLinkedQueue<Double> getyHali() {
        return yHali;
    }
    public void setxHali(ConcurrentLinkedQueue<Double> xHali) {
        this.xHali = xHali;
    }
    public void setyHali(ConcurrentLinkedQueue<Double> yHali) {
        this.yHali = yHali;
    }
    public int getHaliStaerd() {
        return haliStaerd;
    }
    public void setHaliStaerd(int haliStaerd) {
        this.haliStaerd = haliStaerd;
    }
    public void setStefna(int stefna) {
        this.stefna = stefna;
    }

    /**
     * býr til nýjan snák
     *
     * @param x      x-hnit
     * @param y      y-hnit
     * @param stef   stefna
     * @param h      hraði
     * @param width  breidd
     * @param height hæð
     * @param litur  litur
     */
    public Snakur(double x, double y, int stef, double h,int width, int height, String litur){
        this.setX(x);
        this.setY(y);
        this.stefna = stef;
        this.hradi = h;
        this.setWidth(width);
        this.setHeight(height);
        this.setFill(Paint.valueOf(litur));
        this.xHali = new ConcurrentLinkedQueue<>();
        this.yHali = new ConcurrentLinkedQueue<>();
        snakurHali = new ArrayList<>();
    }

    /**
     * Færir snák áfram á hraða sem er geymdur í breytunni hradi og færir snákinn hinum megin á leikborð ef
     * hann klessir á vegg.
     * Býr einnig til halann á snák þegar keyrt er í fyrsta sinn.
     * @param s the s
     */
    public void afram(int s){
        if (xHali == null & s==1) {
            this.xHali = new ConcurrentLinkedQueue<>();
            this.yHali = new ConcurrentLinkedQueue<>();
        }

        if(haliStaerd!=0) {
            xHali.add(this.getX());
            yHali.add(this.getY());
        }

        if(stefna == 90){
            this.setY(this.getY()-hradi);
        }else if(stefna == 270){
            this.setY(this.getY()+hradi);
        }else if(stefna == 180){
            this.setX(this.getX()-hradi);
        }else if(stefna == 360){
            this.setX(this.getX()+hradi);
        }
        if(this.getX() >= getParent().getBoundsInParent().getWidth() - this.getWidth()){
            this.setX(1);
        }
        if(this.getX() < 0){
            this.setX(getParent().getBoundsInParent().getWidth() - this.getWidth()-1);
        }
        if(this.getY() >= getParent().getBoundsInParent().getHeight() - this.getHeight()){
            this.setY(5);
        }
        if(this.getY() < 0){
            this.setY(getParent().getBoundsInParent().getHeight() - this.getHeight()-1);
        }
        if(haliStaerd<xHali.size()){
            xHali.remove();
            yHali.remove();
        }
    }

    /**
     * Stækkar snák með því að bæta við í halann.
     */
    public void vaxa(){
        haliStaerd++;
    }

}
