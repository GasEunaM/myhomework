package huluwa.field;
import java.util.ArrayList;
import huluwa.Thing2D.*;
public class Array {
    static final int X=40;
    static final int Y=30;

    private ArrayList<ArrayList<Position<Creature>>> positions;

    public ArrayList<ArrayList<Position<Creature>>> getPositions() {
        return positions;
    }

    public Array()
    {
        positions=new ArrayList<ArrayList<Position<Creature>>>();
        for(int i=0;i<X;i++)
        {
            positions.add(new ArrayList<Position<Creature>>());
            for(int j=0;j<Y;j++)
            {
                positions.get(i).add(new Position<Creature>(i,j));
            }
        }
    }

    public void SetQueue(Queue q)//将阵型放在array上
    {
        for (int i = 0; i < q.getPositions().size(); i++) {

            int x= q.getPositions().get(i).getX();
            int y= q.getPositions().get(i).getY();
            positions.get(x).set(y,new Position<Creature>(x,y));
            q.getPositions().get(i).getHolder().setPosition(positions.get(x).get(y));
        }
    }
    public void rollCall() {
        for (int i=0;i<Y;i++) {
            for (int j=0;j<X;j++) {
                if(positions.get(j).get(i).getHolder()!=null)
                    System.out.print('1');
                else
                    System.out.print(' ');
            }
            System.out.println("\n");
        }

        System.out.println("\n");
        System.out.println("\n");
        //System.out.flush();
    }
    public void update(ArrayList<Creature> creatures) {
        positions=new ArrayList<ArrayList<Position<Creature>>>();
        for(int i=0;i<X;i++) {
            positions.add(new ArrayList<Position<Creature>>());
            for (int j = 0; j < Y; j++) {
                positions.get(i).add(new Position<Creature>());
            }
        }
        for(int i=0;i<creatures.size();i++)
        {
            Creature creature=creatures.get(i);
            if(!creature.isAlive())
                continue;
            int x= creature.getPosition().getX();
            int y= creature.getPosition().getY();
            positions.get(x).set(y,new Position<Creature>(x,y));
            creature.setPosition(positions.get(x).get(y));
        }
    }
}
