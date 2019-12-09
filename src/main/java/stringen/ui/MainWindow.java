package stringen.ui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import stringen.Util;
import stringen.logic.Parser;

public class MainWindow extends Stage {

    @FXML
    private VBox entryWindowPlaceHolder;

    @FXML
    private HBox buttonPlaceHolder;

    private static final String LOCATION = "/view/MainWindow.fxml";

    private final FXMLLoader fxmlLoader = new FXMLLoader();

    private Stage primaryStage;

    public MainWindow(Stage primaryStage) {
        fxmlLoader.setController(this);
        fxmlLoader.setRoot(this);
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource(LOCATION));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Set dependencies
        this.primaryStage = primaryStage;
    }

    void fillInnerParts() {
        Util.initialise();
        entryWindowPlaceHolder.getChildren().add(new EntryWindow(new Generator(), this, new Parser()));
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        primaryStage.hide();
    }

    /*
    void changeScreen(AnchorPane newScreen) {
        newScreen.maxHeightProperty().bind(stackPane.heightProperty());
        stackPane.getChildren().add(newScreen);
    }
    */
}