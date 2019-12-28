package stringen.ui;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    private Generator generator = new Generator();
    private static Stage primaryStage;

    private static final String APPLICATION_ICON = "/images/logo.png";

    @Override
    public void start(Stage primaryStage) {
        MainWindow mainWindow = new MainWindow(primaryStage);
        //Set the application icon.
        Image icon = new Image(Main.class.getResourceAsStream(APPLICATION_ICON));
        mainWindow.getIcons().add(icon);
        mainWindow.show(); //This should be called before creating other UI parts
        mainWindow.fillInnerParts();
    }

    public static void setRoot(Parent node) {
        primaryStage.getScene().setRoot(node);
    }
}
