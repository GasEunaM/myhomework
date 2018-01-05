package huluwa.field;
import huluwa.Thing2D.*;
public class Position <T extends Thing2D>{

    private int x;
    private int y;
    private boolean moving;
    public synchronized T getHolder() {
        return holder;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public boolean isMoving() {

        return moving;
    }

    public synchronized void setHolder(T holder) {
        this.holder = holder;
    }
    public synchronized void deleteHolder() {
        this.holder = null;
        notifyAll();
    }

    private T holder;

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
    public Position()
    {
        this.x=-1;
        this.y=-1;
        this.holder=null;
    }
    public Position(int x,int y){
        super();
        this.x = x;
        this.y=y;
    }
    public synchronized void waitforPosition(Creature cre) throws InterruptedException{
                while (this.holder != null || isMoving()) {
                    if (!getHolder().isAlive())
                        return;
                    if (!cre.isAlive())
                        return;
                    System.out.print("waiting   ");
                    System.out.println(getHolder().getName());
                    wait();

                }
                notifyAll();

    }
}
