package video;

import java.util.List;

public class SerialList {
    private final List<Serial> serialList;
    public SerialList(final List<Serial> serialList) {
        this.serialList = serialList;
    }

    /**
     * To copy the list, make sure to do:
     * List<Serial> newList = new ArrayList<>(serial.getSerialList());
     */
    public List<Serial> getSerialList() {
        return serialList;
    }
}
