package pipereader.model;

import java.util.EventListener;

/**
 * Created by roman on 28.03.15.
 */
public interface PipeEventListener extends EventListener {

    void notify (pipereader.model.PipeEvent e);
}
