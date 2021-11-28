package video;

import java.util.List;
import java.util.Comparator;

import static common.Constants.ASCENDING;

public class ShowList {
    private List<Show> showList;

    // Constructor
    public ShowList(List<Show> showList) {
        this.showList = showList;
    }

    // Getter
    public List<Show> getShowList() {
        return showList;
    }
}
