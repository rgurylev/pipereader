package pipereader;

import java.io.IOException;
import java.io.InputStream;

import event.EventsDispatcher;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import pipereader.controller.PipeReaderController;
import pipereader.model.PipeReader;
import util.TextAreaAppenderFX;

public class MainApp extends Application {
    {
        DOMConfigurator.configure(System.getProperty("log4j.configuration"));
    }

    private static final Logger log = Logger.getLogger(MainApp.class);

    private Stage primaryStage;
    private BorderPane rootLayout;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("pipe reader");

        initRootLayout();

        showPersonOverview();
    }
    
    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/fxml/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();
            
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the person overview inside the root layout.
     */
    public void showPersonOverview() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/fxml/PipeReader.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.



            Button button;
            Image image;
            button = (Button)personOverview.lookup("#btnAdd");
            image = new Image(getClass().getResourceAsStream("/resources/ico/add-icon.png"));
            button.setGraphic(new ImageView(image));
            button = (Button)personOverview.lookup("#btnDel");
            image = new Image(getClass().getResourceAsStream("/resources/ico/Close-2-icon.png"));
            button.setGraphic(new ImageView(image));

            TextArea textArea = (TextArea) personOverview.lookup("#txtLog");
            TextAreaAppenderFX.setTextArea(textArea);
            log.info("Go!");
            rootLayout.setCenter(personOverview);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    
	/**
	 * Returns the main stage.
	 * @return
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}

    public static void main(String[] args) {

        ApplicationContext context = new ClassPathXmlApplicationContext("Spring-Module-FX.xml");
        PipeReader pr = (PipeReader) context.getBean("pipereader");
        EventsDispatcher.registerListener(pr, PipeReaderController.class);
        pr.start();
        launch(args);
    }
}