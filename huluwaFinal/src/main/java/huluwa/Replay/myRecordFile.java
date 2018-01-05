package huluwa.Replay;
import java.io.*;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Date;
import huluwa.Thing2D.*;
import huluwa.field.*;
public class myRecordFile {
    File file;
    Group win;
    singleRecord record;
    int number;
    replayRecords replay;
    public replayRecords getRecord() {
        return replay;
    }

    DataOutputStream out=null;
    DataInputStream in=null;
    public myRecordFile(int number){
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//"Huluwa"+"yyyyMMddHHmmss");//设置日期格式
        String fileName = df.format(new Date()) + ".dat";
        this.number=number;
        file = new File(fileName);
        try {
            if (!file.exists())
                file.createNewFile();
        }catch(Exception e)
        {
            System.out.println("error creating file "+fileName);
        }
        try {
            out = new DataOutputStream(new FileOutputStream(file));
            out.writeInt(number);
        }catch (IOException e)
        {
            System.out.println("error creating output");
        }

    }
    public myRecordFile(File file){
        this.file=file;
        try {
            in = new DataInputStream(new FileInputStream(file));
        }catch (IOException e)
        {
            System.out.println("error creating output");
        }
    }

    public void updateRecord(ArrayList<Creature> creatures)
    {
        record=new singleRecord(creatures);
        record.save(out);
    }

    public void setEnd(Group win)
    {
        this.win=win;
        try {
            out.writeInt(win.ordinal());
            out.close();
        }
        catch(IOException e){
            System.out.println("error output");
        }
    }
    public boolean read()
    {
        replay=new replayRecords();
        System.out.println("1");
        try {
            number = in.readInt();
            //System.out.println(number);
        }catch(Exception e){
            return false;
        }
        System.out.println("2");
        boolean flag=true;
        int n=1000;
        try{
            n=in.available();
            System.out.println(n);
        }
        catch (Exception e){

        }
        while(n>4&&flag)
        {
            try {
                record=new singleRecord();
                record.read(in,number,replay);
                n-=13*number;
                //System.out.println(n);
            }catch(Exception e){
                //System.out.println("4");
                flag=false;
            }
        }
        System.out.println("3");
        try {
            int num = in.readInt();
            replay.setWin(Group.values()[num]);
        }catch(Exception e){
            return false;
        }

        //System.out.println("aaa");
        return true;
    }

}
