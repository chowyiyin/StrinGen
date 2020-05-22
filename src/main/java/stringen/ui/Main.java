package stringen.ui;

import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

    private Generator generator = new Generator();
    private static Stage primaryStage;

    private static final String APPLICATION_ICON = "/images/logo.png";

    @Override
    public void start(Stage primaryStage) throws IOException, InvalidFormatException {
        MainWindow mainWindow = new MainWindow(primaryStage);
        //Set the application icon.
        Image icon = new Image(Main.class.getResourceAsStream(APPLICATION_ICON));
        mainWindow.getIcons().add(icon);
        mainWindow.setOpacity(0);
        mainWindow.show(); //This should be called before creating other UI parts
        mainWindow.fillInnerParts();
        // wait to avoid white screen flash
        PauseTransition wait = new PauseTransition(Duration.seconds(0.3));
        wait.setOnFinished(event -> mainWindow.setOpacity(1));
        wait.play();
    }

    public static void setRoot(Parent node) {
        primaryStage.getScene().setRoot(node);
    }
}
