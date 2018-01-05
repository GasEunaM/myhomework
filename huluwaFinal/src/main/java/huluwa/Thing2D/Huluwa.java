package huluwa.Thing2D;
import javax.naming.Name;
import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.Random;
import huluwa.field.myField;
public class Huluwa extends Creature {
   // final static int GAP=5;
    private COLOR color;
    private SENIORITY seniority;
    //private Position position;

    public COLOR getColor() {
        return color;
    }

    public SENIORITY getSeniority() {
        return seniority;
    }


    public Huluwa(COLOR color, SENIORITY seiority,myField field) {
        super(0,0);
        this.speed=2;
        this.strength=2;
        this.group=Group.GOOD;
        this.color = color;
        this.seniority = seiority;
        this.field=field;
        int ord =  color.ordinal();
        //System.out.println(ord);
        name= NAME.values()[ord].toString();
        ename= NAME.values()[ord];
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
        return this.seniority.toString() + "(" + this.color.toString() + ")@" + this.position.getX() + ","+this.position.getY()+";";
    }




}




