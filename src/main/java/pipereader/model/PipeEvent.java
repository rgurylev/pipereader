package pipereader.model;

import event.ApplicationEvent;

/**
 * Created by roman on 28.03.15.
 */
public class PipeEvent extends ApplicationEvent {

    private int action;
    private String pipeName;

    private PipeAction state;


    public PipeEvent (Object source, String pipeName, int action) {
        super(source);
        this.pipeName = pipeName;
        this.action = action;

    }

    public PipeEvent (Object source, String pipeName, PipeAction state) {
        super(source);
        this.pipeName = pipeName;
        this.state = state;
    }

    public int getAction() {
        return action;
    }

    public PipeAction getState() {
        return state;
    }

    public String getPipeName() {
        return pipeName;
    }

    @Override
    public String toString() {
        return "PipeEvent{" +
                "action=" + action +
                '}';
    }
}
