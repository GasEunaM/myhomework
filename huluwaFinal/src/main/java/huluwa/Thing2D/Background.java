package huluwa.Thing2D;
import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;

public class Background extends Thing2D {
    String name;
    public Background(int x, int y,String name) {
        super(x, y);
        this.name=name;
        setH(600);
        setW(800);
        URL loc = this.getClass().getClassLoader().getResource(name+".png");
        ImageIcon iia = new ImageIcon(loc);
        Image image = iia.getImage();
        this.setImage(image);
    }
}
