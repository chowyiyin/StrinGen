package stringen.ui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import stringen.Util;

public class Main extends Application {

    private Generator generator = new Generator();
    private static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        //Set the application icon.
        //primaryStage.getIcons().add(getImage(ICON_APPLICATION));
        MainWindow mainWindow = new MainWindow(primaryStage);
        mainWindow.show(); //This should be called before creating other UI parts
        mainWindow.fillInnerParts();
    }

    public static void setRoot(Parent node) {
        primaryStage.getScene().setRoot(node);
    }
}
