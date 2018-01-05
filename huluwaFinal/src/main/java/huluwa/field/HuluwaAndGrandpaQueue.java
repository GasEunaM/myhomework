package huluwa.field;
import java.util.ArrayList;
import huluwa.Thing2D.*;
//葫芦娃阵型

public class HuluwaAndGrandpaQueue extends Queue{
    private int begin;
    public HuluwaAndGrandpaQueue(ArrayList<Huluwa> brothers, Grandpa grandpa) {
        begin=(Y-2*brothers.size())/2-2;
        this.positions = new ArrayList<Position<Creature>>();
        this.creatures = new ArrayList<Creature>();
        this.creatures.add(grandpa);
        creatures.addAll(brothers);

        for (int i = 0; i < creatures.size(); i++) {
            this.positions.add( new Position(X/4,begin+2*i));
            Position<Creature> p = this.positions.get(i);
            this.creatures.get(i).setPosition(p);
        }
    }
}
