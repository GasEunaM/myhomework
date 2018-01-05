package huluwa.Thing2D;
import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.Random;
import huluwa.field.*;
public class Scorpion extends Creature {
    //private Position<Creature> position;
    public Scorpion(myField field)
    {
        super(0,0);
        this.speed=2;
        this.strength=4;
        this.group=Group.BAD;
        this.field=field;
        name="XIEZIJING";
        ename=NAME.XIEZIJING;
        setW(40);
        setH(40);
        URL loc = this.getClass().getClassLoader().getResource(name+".png");
        try {
            ImageIcon iia = new ImageIcon(loc);
            Image image = iia.getImage();
            this.setImage(image);
        }
        catch (Exception e){

        }
    }

    @Override
    public String toString(){
        return "蝎子精@" + this.position.getX() + ","+this.position.getY()+";";
    }



}
