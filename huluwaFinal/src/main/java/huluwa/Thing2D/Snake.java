package huluwa.Thing2D;
import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.Random;
import huluwa.field.myField;
public class Snake extends Creature {
    //private Position<Creature> position;

    public Snake(myField field)
    {
        super(0,0);
        this.speed=1;
        this.strength=2;
        this.group=Group.BAD;
        this.field=field;
        name="SHEJING";
        ename=NAME.SHEJING;
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
        return "蛇精@" + this.position.getX() + ","+this.position.getY()+";";
    }

}
