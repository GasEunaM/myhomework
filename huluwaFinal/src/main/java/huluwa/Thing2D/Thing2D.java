package huluwa.Thing2D;
import java.awt.Image;
import huluwa.field.*;

public class Thing2D{

    protected Position position;

    protected int w;
    protected int h;

    private Image image;
    public Thing2D(){};
    public Thing2D(int x, int y) {
        position = new Position<>(x,y);
        this.w=0;
        this.h=0;
    }
    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
        this.position.setHolder(this);
    }

    public Image getImage() {
        return this.image;
    }

    public void setImage(Image img) {
        image = img;
    }

    public int x() {
        return this.position.getX();
    }

    public int y() {
        return this.position.getY();
    }

    public void setX(int x) {
        this.position.setX(x);
    }

    public void setY(int y) {
        this.position.setY(y);
    }

    public void setW(int w) {
        this.w = w;
    }

    public void setH(int h) {
        this.h = h;
    }

    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }

    public String getName(){return " ";};
    public boolean isAlive(){return false;};
} 