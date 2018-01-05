package huluwa.field;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JPanel;
import java.util.concurrent.*;
import javax.swing.*;
import java.io.File;
import static java.lang.Thread.sleep;
import huluwa.Thing2D.*;
import huluwa.Replay.*;
public class myField extends JPanel{

    private int space=20;
    private int w = 800;
    private int h = 600;

    int number=0;

    public void setCreatures(ArrayList<Creature> creatures) {
        this.creatures = creatures;
    }

    public Array getArray() {
        return array;
    }

    private Mode mode;
    private Array array=new Array();
    private Group win;

    myRecordFile file;
    myRecordFile replayfile;
    replayRecords replay;
    ArrayList<Thing2D> things = new ArrayList<>();
    private ArrayList<Thing2D> bks = new ArrayList<>();
    private ArrayList<Creature> creatures = new ArrayList<>();
    private ArrayList<Huluwa> brothers=new ArrayList<>();
    ArrayList<Buddy> buddies=new ArrayList<>();
    private Grandpa grandpa=new Grandpa(this);
    private Snake snake=new Snake(this);
    private Scorpion scorpion=new Scorpion(this);

    ExecutorService threads = Executors.newCachedThreadPool();

    public ArrayList<Creature> getCreatures() {
        return creatures;
    }

    HuluwaAndGrandpaQueue h_queue;
    BudsAndScorAndSnakeQueue b_queue;
    public myField(){
        //setBackground(Color.WHITE);
        addKeyListener(new TAdapter());
        setFocusable(true);
        init();

    }
    public int getBoardWidth() {
        return this.w;
    }

    public int getBoardHeight() {
        return this.h;
    }

    public Group getWin() {
        return win;
    }

    public void init() {
        things = new ArrayList<>();
        bks = new ArrayList<>();
        creatures = new ArrayList<>();
        brothers=new ArrayList<>();

        buddies=new ArrayList<>();
        Grandpa grandpa=new Grandpa(this);
        Snake snake=new Snake(this);
        Scorpion scorpion=new Scorpion(this);
        array=new Array();
        threads = Executors.newCachedThreadPool();

        mode=Mode.START;
        bks.add(new Background(0, 0,"bk1"));
        bks.add(new Background(0, 0,"bk2"));
        bks.add(new Background(0, 0,"bk3"));
        bks.add(new Background(0, 0,"bk4"));
        for(int i = 0; i < 7; i++) {
            brothers.add(new Huluwa(COLOR.values()[i], SENIORITY.values()[i],this));
        }
        for(int i = 0; i < 7; i++) {
            buddies.add(new Buddy(i,this));

        }
        h_queue = new HuluwaAndGrandpaQueue(brothers,grandpa);
        b_queue=new BudsAndScorAndSnakeQueue(buddies,scorpion,snake);
        array.SetQueue(h_queue);
        array.SetQueue(b_queue);

    }
    public void initPlay() {

        creatures.addAll(h_queue.getCreatures());
        creatures.addAll(b_queue.getCreatures());
        //creatures.get(0).move(2,0);
        number=creatures.size();
        file=new myRecordFile(number);
    }

    public void drawThings(Graphics g) {
        if(mode ==Mode.START)
        {
            Thing2D item = bks.get(0);
            g.drawImage(item.getImage(), item.x()*space, item.y()*space, item.getW(), item.getH(), this);
        }
        else if(mode== Mode.PLAYING ||mode==Mode.REPLAY) {
            things.add(bks.get(1));
            for(int i=0;i<creatures.size();i++)
            {
                if(!creatures.get(i).isAlive())
                {
                    things.add(creatures.get(i));
                }
            }
            for(int i=0;i<creatures.size();i++)
            {
                if(creatures.get(i).isAlive())
                {
                    things.add(creatures.get(i));
                }
            }

            for (int i = 0; i < things.size(); i++) {
                Thing2D item = (Thing2D) things.get(i);
                g.drawImage(item.getImage(), item.x()*space, item.y()*space, item.getW(), item.getH(), this);
            }
        }
        else if(mode== Mode.END) {
            Thing2D item;
            if(win==Group.GOOD)
                item = bks.get(2);
            else
                item = bks.get(3);
            g.drawImage(item.getImage(), item.x()*space, item.y()*space, item.getW(), item.getH(), this);

        }
    }

    public void paint(Graphics g) {
       // array.update(creatures);
        super.paint(g);
        //array.rollCall();
        drawThings(g);
    }
    public synchronized void update() {
        if(mode==Mode.PLAYING) {
            file.updateRecord(creatures);
            if (isEnd()) {
                mode = Mode.END;
                file.setEnd(win);
            }
        }
        repaint();
    }

    public void setArray(Array array) {
        this.array = array;
    }

    public void replay()
    {
        if (replayfile.read())
        {
            replay=replayfile.getRecord();
            ArrayList<singleRecord> records=replay.getRecords();
            win=replay.getWin();
            //System.out.println(records.size());
            threads = Executors.newCachedThreadPool();
            threads.execute(()-> {
                        for (int i = 0; i < records.size(); i++) {
                            creatures = records.get(i).getCreatures();
                           // System.out.println(creatures.size());
                            try {
                                sleep(100);
                            } catch (Exception e) {
                            }

                            repaint();
                        }
                         mode=Mode.END;
                    }

            );


            //repaint();
        }
        System.out.println("error reading file");
        mode=Mode.START;
        repaint();
    }
    public synchronized Boolean isEnd(){
        int i;
        Group p=Group.GOOD;
        for (i = 0; i < creatures.size(); i++)
        {
            if(creatures.get(i).isAlive())
            {
                p=creatures.get(i).getGroup();
                break;
            }
        }
        for (i++; i < creatures.size(); i++)
        {
            if(creatures.get(i).isAlive())
            {
                Group g=creatures.get(i).getGroup();
                if(p!=g)
                {
                    return false;
                }

            }
        }
        win=p;
        System.out.println(win);
        for (i=0; i < creatures.size(); i++) {
            creatures.get(i).alive=false;
            //creatures.get(i).die();

        }
        threads.shutdownNow();
        try{
            while(!threads.awaitTermination(1, TimeUnit.MILLISECONDS));
            {
                System.out.println("线程池没有关闭");
            }
        } catch (InterruptedException e){
            System.out.println("interrupting");
        }
        System.out.println("线程池已经关闭");
        return true;
    }
    class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();
            if(mode==Mode.START){
                if (key == KeyEvent.VK_SPACE) {
                    init();
                    initPlay();
                    mode=Mode.PLAYING;
                    for (int i = 0; i < creatures.size(); i++)
                        threads.execute(creatures.get(i));
                    //repaint();
                }
                else if (key == KeyEvent.VK_L) {
                    JFileChooser fd = new JFileChooser();
                    fd.showOpenDialog(null);
                    File file = fd.getSelectedFile();
                    replayfile=new myRecordFile(file);
                    mode=Mode.REPLAY;
                    replay();
                }
            }
            else if(mode==Mode.END){
                //System.out.println("1");
                if (key == KeyEvent.VK_SPACE) {
                    init();
                    //initPlay();
                    mode=Mode.START;
                    //for (int i = 0; i < creatures.size(); i++)
                        //threads.execute(creatures.get(i));
                    repaint();
                }
                else if (key == KeyEvent.VK_L) {
                    JFileChooser fd = new JFileChooser();
                    fd.showOpenDialog(null);
                    File file = fd.getSelectedFile();
                    replayfile=new myRecordFile(file);
                    mode=Mode.REPLAY;
                    replay();
                }
            }
        }
    }
}

enum Mode{
    START,PLAYING,REPLAY,END
}