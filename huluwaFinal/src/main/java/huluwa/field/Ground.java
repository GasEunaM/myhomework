package huluwa.field;
import javax.swing.JFrame;


public final class Ground extends JFrame {

    private final int OFFSET = 0;


    public Ground() {
        InitUI();
    }

    public void InitUI() {
        myField field = new myField();
        add(field);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(field.getBoardWidth() + OFFSET,
                field.getBoardHeight() + 2 * OFFSET);
        setLocationRelativeTo(null);
        setTitle("Ground");
    }


    /*public static void main(String[] args) {
        Ground ground = new Ground();
        ground.setVisible(true);
    }*/
}