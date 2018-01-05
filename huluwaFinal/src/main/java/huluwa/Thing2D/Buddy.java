package huluwa.Thing2D;
import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.Random;
import huluwa.field.myField;
public class Buddy extends Creature {
    private int index;
    @Override
    public String toString(){
        return "小喽啰"+this.index+"@" + this.position.getX() + ","+this.position.getY()+";";
    }

    public Buddy(int i,myField field)
    {
        super(0,0);
        this.speed=2;
        this.strength=1;
        this.group=Group.BAD;
        this.index = i;
        this.field=field;
        name="LOULUO";
        ename=NAME.LOULUO;
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
    public String getName()
    {
        return name+String.valueOf(index);
    }


}

