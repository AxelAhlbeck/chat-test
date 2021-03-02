
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class MessageManager extends Thread {
    private Buffer<Message> messageBuffer;
    private PropertyChangeSupport changes;


    public MessageManager(Buffer<Message> messageBuffer){
        this.messageBuffer=messageBuffer;
        changes = new PropertyChangeSupport(this);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changes.addPropertyChangeListener(listener);
    }

    public void run(){
        while(!Thread.interrupted()) {
            try {
                Message message = messageBuffer.get();
                if (message != null) {
                    changes.firePropertyChange("New message", null, message);
                }
            } catch (InterruptedException e) { }
        }
    }
}
