import huluwa.Replay.myRecordFile;
import huluwa.Thing2D.*;
import huluwa.field.Array;
import huluwa.field.Position;
import org.junit.Test;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import huluwa.field.myField;
public class huluwaFinalTest {
    @Test
    public void testEndAndWin()//测试战斗是否正常结束，选出正确的获胜方
    {
        myField field = new myField();
        ArrayList<Creature> creatures=new ArrayList<>();
        creatures.add(new Huluwa(COLOR.values()[0], SENIORITY.values()[0],field));
        creatures.add(new Huluwa(COLOR.values()[1], SENIORITY.values()[1],field));
        creatures.add(new Grandpa(field));
        field.setCreatures(creatures);
        boolean end=field.isEnd();
        Group p =field.getWin();
        assert (end); //战斗应该结束
        assert (p==Group.GOOD); //葫芦娃胜利


        field = new myField();
        creatures=new ArrayList<>();
        creatures.add(new Huluwa(COLOR.values()[0], SENIORITY.values()[0],field));
        creatures.add(new Huluwa(COLOR.values()[1], SENIORITY.values()[1],field));
        creatures.add(new Snake(field));
        field.setCreatures(creatures);
        end=field.isEnd();
        assert (!end); //战斗不应该结束
        //FileManager fileManager = new FileManager();
        //fileManager.readRecord(file);
    }

    @Test
    public void testFindEnemy()//测试是否正确找到敌人
    {
        myField field = new myField();
        Array arr = new Array();
        Snake snake=new Snake(field);
        //Position p = new Position(3,5);
        arr.getPositions().get(3).set(5,new Position<>(3,5));
        snake.setPosition(arr.getPositions().get(3).get(5));

        Grandpa grandpa=new Grandpa(field);
        //Position p1 = new Position(3,6);
        arr.getPositions().get(3).set(6,new Position<>(3,6));
        grandpa.setPosition(arr.getPositions().get(3).get(6));

        field.setArray(arr);

        Creature cre=snake.findEnemy();//爷爷在蛇精旁边，蛇精应找到敌人
        assert (!(cre==null));

        Scorpion scor=new Scorpion(field);
        ///p = new Position(3,2);
        arr.getPositions().get(3).set(2,new Position<>(3,2));
        scor.setPosition(arr.getPositions().get(3).get(2));

        cre=scor.findEnemy();//蝎子精旁边没有敌人，不应找到敌人
        assert (cre==null);
    }

    @Test
    public void testFileread()//测试Replay时读取文件是否正确（即只能成功读取符合要求的文件）
    {
        File file = new File("demo.dat");
        myRecordFile replayfile=new myRecordFile(file);
        assert (replayfile.read());//可以成功读文件

        file = new File("a.rtf");
        replayfile=new myRecordFile(file);
        assert (!replayfile.read());//不可以成功读文件
    }
}
