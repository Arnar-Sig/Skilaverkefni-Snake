package vidmot;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class Faeda extends Circle {

    /**
     * Býr til nýjan fæðu-hlut.
     *
     * @param x      x-hnit fæðu
     * @param y      y-hnit fæðu
     * @param radius radiús fæðu
     */
    public Faeda(float x, float y, double radius){
        this.setCenterX(x);
        this.setCenterY(y);
        this.setRadius(radius);
        this.setFill(Paint.valueOf("Blue"));
    }
}

