package stringen.ui;

import java.io.IOException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import stringen.Util;

public class MainWindow extends Stage {

    @FXML
    private ListView<EntryWindow> entryWindowPlaceholder;

    @FXML
    private VBox windowPlaceholder;

    @FXML
    private HBox buttonPlaceholder;

    @FXML
    private Scene scene;

    private static final String LOCATION = "/view/MainWindow.fxml";

    private final FXMLLoader fxmlLoader = new FXMLLoader();

    private ArrayList<EntryWindow> entryWindows = new ArrayList<>();
    private Stage primaryStage;
    private Generator generator;

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
        generator = new Generator();
        EntryWindow entryWindow = new EntryWindow(generator, this);
        entryWindow.prefHeightProperty().bind(this.heightProperty());
        entryWindows.add(entryWindow);
        entryWindowPlaceholder.setItems(FXCollections.observableArrayList(entryWindows));
        entryWindowPlaceholder.setCellFactory(lst ->
                new ListCell<EntryWindow>() {
                    @Override
                    protected void updateItem(EntryWindow item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setPrefHeight(0);
                            setGraphic(null);
                        } else {
                            setPrefHeight(Region.USE_COMPUTED_SIZE);
                            setGraphic(item);
                        }
                    }
                });
    }

    @FXML
    public void addCohort() {
        EntryWindow newWindow = new EntryWindow(generator, this);
        newWindow.prefHeightProperty().bind(this.heightProperty());
        entryWindows.add(newWindow);
        entryWindowPlaceholder.setItems(FXCollections.observableArrayList(entryWindows));
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        primaryStage.hide();
    }

    @FXML
    private void generateString() {
        String generatedString = generator.generateString(entryWindows);
        changeScreen(generatedString);
    }

    void changeScreen(String generatedString) {
        windowPlaceholder.getChildren().remove(entryWindowPlaceholder);
        windowPlaceholder.getChildren().remove(buttonPlaceholder);
        windowPlaceholder.getChildren().add(new ResultWindow(generatedString));
    }

    /*
    void changeScreen(AnchorPane newScreen) {
        newScreen.maxHeightProperty().bind(stackPane.heightProperty());
        stackPane.getChildren().add(newScreen);
    }
    */
}