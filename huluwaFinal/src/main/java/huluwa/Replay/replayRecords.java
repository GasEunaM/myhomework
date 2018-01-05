package huluwa.Replay;
import java.util.ArrayList;
import huluwa.Thing2D.*;
public class replayRecords {
    private ArrayList<singleRecord> records = new ArrayList<>();
    private Group win;

    public replayRecords()
    {
        records = new ArrayList<>();
    }
    public ArrayList<singleRecord> getRecords() {
        return records;
    }

    public Group getWin() {
        return win;
    }

    public void setWin(Group win) {

        this.win = win;
    }

    public void add(singleRecord record){
        records.add(record);
    }
}
