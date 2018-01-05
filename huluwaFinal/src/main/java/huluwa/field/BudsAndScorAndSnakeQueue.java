package huluwa.field;
import java.util.ArrayList;
//小喽啰，蝎子精阵型
import huluwa.Thing2D.*;
public class BudsAndScorAndSnakeQueue extends Queue{
    private int begin;
    private int distance;
    //阵型1
    public BudsAndScorAndSnakeQueue(ArrayList<Buddy> buddies, Scorpion scor,Snake snake) {
        //this.positions = new ArrayList<Position<Thing2D>>();
        distance=2;
        this.creatures = new ArrayList<Creature>();
        begin=(Y-buddies.size()*2)/2;
        this.creatures.add(scor);
        this.creatures.add(snake);
        this.creatures.addAll(buddies);

        /*for (int i = 0; i <creatures.size(); i++) {

            this.positions.add(new Position(i+begin,N/2-i));
            this.creatures.get(i).setPosition(this.positions.get(i));
        }*/
        Heyi();
    }

    //阵型2
    public void Heyi()
    {
        this.positions = new ArrayList<Position<Creature>>();
        int i;
        for (i = 0; i < creatures.size()/2+1; i++) {

            this.positions.add(new Position<Creature>(X/2+i+distance,2*i+begin));
            this.creatures.get(i).setPosition(this.positions.get(i));
        }
        for (int j = 0; j+i < creatures.size(); j++) {

            this.positions.add(new Position<Creature>(X/2+i+j+distance,2*(i-j-2)+begin));
            this.creatures.get(j+i).setPosition(this.positions.get(i+j));
        }
    }
}
