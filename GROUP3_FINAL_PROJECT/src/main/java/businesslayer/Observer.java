package businesslayer;

/**
 * Observer interface for alert notifications.
 * @author Hongchen Guo
 */
public interface Observer {
    /**
     * Updates the observer when an alert is added.
     */
    void update();
}