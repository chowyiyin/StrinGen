package stringen.ui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import stringen.ui.CommandBox;

public class Main extends Application {

    private Generator generator = new Generator();
    private static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        try {
            this.primaryStage = primaryStage;
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/CommandBox.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            primaryStage.setScene(scene);
            fxmlLoader.<CommandBox>getController().setGen(generator);
            primaryStage.setTitle("StrinGen");
            primaryStage.setHeight(600);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setRoot(Parent node) {
        primaryStage.getScene().setRoot(node);
    }

}
