package huluwa.Replay;
import java.io.*;
import java.util.ArrayList;
import java.io.File;
import huluwa.Thing2D.*;
public class singleRecord {

    ArrayList<Creature> creatures = new ArrayList<>();
    public singleRecord(ArrayList<Creature> creatures)
    {
        this.creatures=creatures;
    }
    public singleRecord()
    {
        this.creatures=new ArrayList<>();
    }

    public ArrayList<Creature> getCreatures() {
        return creatures;
    }

    public void save(DataOutputStream out){
        for(int i=0;i<creatures.size();i++) {
            Creature cre=creatures.get(i);
            try {
                out.writeInt(cre.getEname().ordinal());
                out.writeBoolean(cre.isAlive());
                out.writeInt(cre.getPosition().getX());
                out.writeInt(cre.getPosition().getY());
            }
            catch(IOException e){
                System.out.println("error output");
            }
        }
    }

    public void read(DataInputStream in,int number,replayRecords replay) throws Exception{
        //System.out.println("222");
        creatures = new ArrayList<>();
        try {
            for(int i=0;i<number;i++) {

                int nameord = in.readInt();
                boolean alive = in.readBoolean();
                int x = in.readInt();
                int y = in.readInt();
                creatures.add(new Creature(nameord,alive,x,y));
            }
            replay.add(this);
            //System.out.println("222");

        }
        catch (IOException e){
            throw(e);
        }

    }



}
