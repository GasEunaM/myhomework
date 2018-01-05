package huluwa.Thing2D;
import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.Random;
import huluwa.field.myField;
public class Grandpa extends Creature {
    //private Position<Creature> position;
    public Grandpa(myField field)
    {
        super(0,0);
        this.speed=3;
        this.strength=3;
        this.group=Group.GOOD;
        this.field=field;
        name="YEYE";
        ename=NAME.YEYE;
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
        return "爷爷@" + this.position.getX() + ","+this.position.getY()+";";
    }



}
