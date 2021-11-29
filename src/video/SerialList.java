package video;

import user.UserList;

import java.util.List;
import java.util.Comparator;

import static common.Constants.ASCENDING;

public class SerialList {
    private List<Serial> serialList;

    // Constructor
    public SerialList(List<Serial> serialList) {
        this.serialList = serialList;
    }

    // Getter
    public List<Serial> getSerialList() {
        return serialList;
    }
}
