package pipereader.controller;

import event.EventsDispatcher;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.apache.log4j.Logger;
import pipereader.model.PipeEvent;
import pipereader.model.PipeAction;

/**
 * Created by roman on 26.03.16.
 */
public class PipeReaderController {
    private static final Logger log = Logger.getLogger(PipeReaderController.class);

    @FXML
    private ComboBox cmbPipes;

    @FXML
    private TextField txtPipeName;

    @FXML
    private void showTestMessage() {

        //  log.info("pipe: " + cmbPipes.getValue());
        log.info("test message");
        txtPipeName.setText("BLA");
    }

    ;

    @FXML
    private void startWriting() {
        PipeEvent event = new PipeEvent(this, txtPipeName.getText().trim(), PipeAction.STARTWRITING);
        EventsDispatcher.fireEvent(event);
        log.debug("STARTWRITING:" +  txtPipeName.getText().trim());
    }

    @FXML
    void stopWriting() {
        PipeEvent event = new PipeEvent(this,  txtPipeName.getText().trim(), PipeAction.STOPWRITING);
        EventsDispatcher.fireEvent(event);
        log.debug("STOPWRITING:" +  txtPipeName.getText().trim());
}

}
