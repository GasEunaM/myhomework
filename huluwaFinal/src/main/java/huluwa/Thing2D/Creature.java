package huluwa.Thing2D;
import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import huluwa.field.*;

public class Creature extends Thing2D implements Runnable{

    protected myField field;
    protected String name;
    protected NAME ename;
    protected int speed;
    protected int strength;

    public NAME getEname() {
        return ename;
    }

    @Override
    public String getName() {

        return name;
    }

    protected Group group;
    public boolean alive;

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    private boolean battling;
    private boolean moving;

    public boolean isBattling() {
        return battling;
    }

    public void setBattling(boolean battling) {
        this.battling = battling;
    }

    public int getSpeed() {

        return speed;
    }

    public int getStrength() {
        return strength;
    }

    public Group getGroup() {
        return group;
    }

    @Override
    public boolean isAlive() {
        return alive;
    }

    public void die(){
        synchronized (this) {
            System.out.println(getName() + " die");
            alive = false;
            setBattling(false);
            setMoving(false);
            this.notify();
            position.deleteHolder();
            notifyAll();
            URL loc;
            if (group == Group.GOOD)
                loc = this.getClass().getClassLoader().getResource("GoodDie.png");
            else
                loc = this.getClass().getClassLoader().getResource("BadDie.png");
            try {
                ImageIcon iia = new ImageIcon(loc);
                Image image = iia.getImage();
                this.setImage(image);
            } catch (Exception e) {

            }
        }

    }
    public Creature(int x, int y) {
        super(x, y);
        alive=true;
        battling=false;
    }
    public Creature(int no,boolean alive ,int x, int y) {
        super(x, y);
        setW(40);
        setH(40);
        //System.out.println(NAME.values()[no].name());
        URL loc;
        if(alive)
            loc = this.getClass().getClassLoader().getResource(NAME.values()[no].name()+".png");
        else if(no<=7)
            loc = this.getClass().getClassLoader().getResource("GoodDie.png");
        else
            loc = this.getClass().getClassLoader().getResource("BadDie.png");
        try {
            ImageIcon iia = new ImageIcon(loc);
            Image image = iia.getImage();
            this.setImage(image);
        }catch (Exception e)
        {
           // System.out.println("asdfgh");
        }
        this.alive=alive;
        battling=false;
    }
    public boolean waitFormove(Position<Creature> p) throws InterruptedException {
           //synchronized (p) {
               if (!isAlive())
                   return false;
              // System.out.print(getName() + "   ");
               synchronized (p){
                  p.waitforPosition(this);
               }
               while (isBattling()) {
                   System.out.print(getName()+ "  waiting   ");
                   System.out.println(p.getHolder().getName());
                   wait();

               }

               System.out.println(getName() + "   moving   ");

               setMoving(true);
               p.setMoving(true);
               notifyAll();
               return true;


    }

    public synchronized boolean waitForBattle(Creature c) throws InterruptedException {

            while (isBattling() || c.isBattling() || c.isMoving() || isMoving()) {
                System.out.print("waiting battle   ");
                System.out.println(getName());
                if (!isAlive() || !c.isAlive())
                    return false;
                wait();
            }
            System.out.print("battling   ");
            System.out.println(getName());
            return true;

    }

    public void move(Position currentp, Position p) {
        synchronized (this) {
            currentp.deleteHolder();

            setPosition(p);

            p.setMoving(false);
            setMoving(false);
            notifyAll();
        }

    }

    public synchronized Position<Creature> chooseNextPosition() {
        int currentX = getPosition().getX();
        int currentY = getPosition().getY();
        Array arr = field.getArray();
        for (int x = 0; x < 40; x++) {
            Position<Creature> pos = arr.getPositions().get(x).get(currentY);
            if (pos.getHolder() != null && pos.getHolder().getGroup() != group) {
                if (pos.getX() > currentX)
                    return arr.getPositions().get(currentX + 1).get(currentY);
                else
                    return arr.getPositions().get(currentX - 1).get(currentY);
            }
        }
        for (int y = currentY; y < 30; y++) {
            for (int x = 0; x < 40; x++) {
                Position<Creature> pos = arr.getPositions().get(x).get(y);

                if (pos.getHolder()!= null && pos.getHolder().getGroup() != group)
                    return arr.getPositions().get(currentX).get(currentY+1);

            }

        }
        return arr.getPositions().get(currentX).get(currentY-1);

    }

    public synchronized Creature findEnemy(){
        Array arr = field.getArray();
        int x=position.getX();
        int y=position.getY();

        if (arr.getPositions().get(x+1).get(y).getHolder()!=null)
        {
            Creature cre=arr.getPositions().get(x+1).get(y).getHolder();
            if(cre.getGroup()!=group) {
                //System.out.println('1');
                return cre;
            }
        }
        if (arr.getPositions().get(x-1).get(y).getHolder()!=null)
        {
            Creature cre=arr.getPositions().get(x-1).get(y).getHolder();
            if(cre.getGroup()!=group) {
                //System.out.println('2');
                return cre;
            }
        }
        if (arr.getPositions().get(x).get(y+1).getHolder()!=null)
        {
            Creature cre=arr.getPositions().get(x).get(y+1).getHolder();
            if(cre.getGroup()!=group) {
                //System.out.println('3');
                return cre;
            }
        }
        if (arr.getPositions().get(x).get(y-1).getHolder()!=null)
        {
            Creature cre=arr.getPositions().get(x).get(y-1).getHolder();
            if(cre.getGroup()!=group) {
               // System.out.println('4');
                return cre;
            }
        }
        return null;
    }

    public  void battle(Creature enemy) {
        synchronized (this) {
            battling = true;
            enemy.setBattling(true);
            System.out.print(getName());
            System.out.print(" ");
            System.out.println(enemy.getName());
            int eStrength = enemy.getStrength();
            int winPossibility = 50 + 10 * (strength - eStrength);
            Random rand = new Random();
            int n = rand.nextInt(100);
            //System.out.print(winPossibility);
            //System.out.print(" ");
            //System.out.println(n);
            //System.out.println("lalll ");
            //notifyAll();
            if (n < winPossibility)//Win
            {
                System.out.println(getName());
                enemy.alive = false;
                notifyAll();
                enemy.die();


            } else {
                System.out.println(enemy.getName());
                alive=false;
                notifyAll();
                die();
            }
            //System.out.println("bbbbb ");
            setBattling(false);
            enemy.setBattling(false);
            notifyAll();
        }

    }

    public void run() {
        synchronized (this) {
            while (!Thread.interrupted()) {
                if (!isAlive())
                    return;
                Random rand = new Random();
                try {
                    Array arr = field.getArray();
                    int x = position.getX();
                    int y = position.getY();
                    Creature cre = findEnemy();
                    if (cre != null) {
                        if (waitForBattle(cre))
                            battle(cre);
                    }
                    this.field.update();
                    if (!isAlive())
                        return;

                    Position p = this.chooseNextPosition();
                    if (waitFormove(p))
                        this.move(arr.getPositions().get(x).get(y), p);
                    Thread.sleep((rand.nextInt(500) + 500) * speed);
                    this.field.update();
                } catch (Exception e) {

                }
                notifyAll();
            }
        }
    }

}

