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
    private Parser parser = new Parser();
    private static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        //Set the application icon.
        //primaryStage.getIcons().add(getImage(ICON_APPLICATION));
        MainWindow mainWindow = new MainWindow(primaryStage);
        mainWindow.show(); //This should be called before creating other UI parts
        mainWindow.fillInnerParts();
    }

    /*
    @Override
    public void start(Stage primaryStage) {
        try {
            this.primaryStage = primaryStage;
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/CommandBox.fxml"));
            GridPane gp = fxmlLoader.load();
            Scene scene = new Scene(gp);
            primaryStage.setScene(scene);
            //fxmlLoader.<MainWindow>getController().setGen(generator);
            //fxmlLoader.<MainWindow>getController().setParser(parser);
            primaryStage.setTitle("StrinGen");
            primaryStage.setMinHeight(620);
            primaryStage.setMinWidth(900);
            primaryStage.show();
            Util.initialise();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
     */

    public static void setRoot(Parent node) {
        primaryStage.getScene().setRoot(node);
    }
}
