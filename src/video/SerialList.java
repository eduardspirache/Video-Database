package video;

import java.util.ArrayList;
import java.util.Comparator;

import static common.Constants.ASCENDING;

public class SerialList extends ShowList{
    private ArrayList<Serial> serialList = new ArrayList<>();

    // Constructor
    public SerialList(ArrayList<Serial> serialList) {
        this.serialList = serialList;
    }

    // Getter
    public ArrayList<Serial> getSerialList() {
        return serialList;
    }

}
