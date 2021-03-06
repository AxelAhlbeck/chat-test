package clientserver;

import java.util.List;

public interface Callback {
    <T> void updateListView(T[] messages);
}
