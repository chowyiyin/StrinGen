package stringen.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import stringen.Util;

public class MainWindow extends Stage {

    @FXML
    private StackPane entryWindowPlaceholder;

    @FXML
    private VBox windowPlaceholder;

    @FXML
    private HBox buttonPlaceholder;

    @FXML
    private StackPane backButtonPlaceholder;

    @FXML
    private StackPane nextButtonPlaceholder;

    @FXML
    private Button backButton;

    @FXML
    private Button nextButton;

    @FXML
    private Scene scene;

    private static final String LOCATION = "/view/MainWindow.fxml";

    private final FXMLLoader fxmlLoader = new FXMLLoader();

    private ArrayList<EntryWindow> entryWindows = new ArrayList<>();
    private Stage primaryStage;
    private Generator generator;
    private ResultWindow resultWindow;

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
        primaryStage.setMinHeight(620);
        primaryStage.setMinWidth(900);
    }

    void fillInnerParts() {
        Util.initialise();
        startUp();
    }

    public void startUp() {
        generator = new Generator();
        EntryWindow entryWindow = new EntryWindow(generator, this);
        entryWindow.prefHeightProperty().bind(this.heightProperty());
        entryWindow.removeDeleteCohortButton();
        backButtonPlaceholder.getChildren().remove(backButton);
        nextButtonPlaceholder.getChildren().remove(nextButton);
        entryWindows.add(entryWindow);
        entryWindowPlaceholder.getChildren().add(entryWindow);
    }

    @FXML
    public void addCohort() {
        EntryWindow newWindow = new EntryWindow(generator, this);
        newWindow.prefHeightProperty().bind(this.heightProperty());
        if (entryWindows.size() == 1) {
            entryWindows.get(0).addDeleteCohortButton();
        }
        if (!backButtonPlaceholder.getChildren().contains(backButton)) {
            backButtonPlaceholder.getChildren().add(backButton);
        }
        nextButtonPlaceholder.getChildren().remove(nextButton);
        entryWindows.add(newWindow);
        entryWindowPlaceholder.getChildren().clear();
        entryWindowPlaceholder.getChildren().add(newWindow);
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
        resultWindow = new ResultWindow(generatedString, this);
        windowPlaceholder.getChildren().add(resultWindow);
    }

    public void refresh() {
        windowPlaceholder.getChildren().remove(resultWindow);
        entryWindows.clear();
        entryWindowPlaceholder.getChildren().clear();
        startUp();
        windowPlaceholder.getChildren().add(entryWindowPlaceholder);
        windowPlaceholder.getChildren().add(buttonPlaceholder);
    }

    public void deleteCohort(EntryWindow toRemove) {
        int windowIndex = entryWindows.indexOf(toRemove);
        entryWindowPlaceholder.getChildren().remove(toRemove);
        if (windowIndex != 0) {
            entryWindowPlaceholder.getChildren().add(entryWindows.get(windowIndex - 1));
            if (windowIndex == 1) {
                backButtonPlaceholder.getChildren().remove(backButton);
            }
        } else {
            EntryWindow nextWindow = entryWindows.get(windowIndex + 1);
            backButtonPlaceholder.getChildren().remove(backButton);
            entryWindowPlaceholder.getChildren().add(nextWindow);
        }
        entryWindows.remove(toRemove);
        if (entryWindows.size() == 1) {
            entryWindows.get(0).removeDeleteCohortButton();
        }
    }

    public boolean isOnlyWindow(EntryWindow entryWindow) {
        return entryWindows.contains(entryWindow) && entryWindows.size() == 1;
    }

    @FXML
    public void goToPreviousWindow() {
        EntryWindow currWindow = (EntryWindow) entryWindowPlaceholder.getChildren().get(0);
        int index = entryWindows.indexOf(currWindow);
        if (index != 0) {
            entryWindowPlaceholder.getChildren().clear();
            entryWindowPlaceholder.getChildren().add(entryWindows.get(index - 1));
        }
        if (index == 1) {
            backButtonPlaceholder.getChildren().remove(backButton);
        }
        if (!nextButtonPlaceholder.getChildren().contains(nextButton)) {
            nextButtonPlaceholder.getChildren().add(nextButton);
        }
    }

    @FXML
    public void goToNextWindow() {
        EntryWindow currWindow = (EntryWindow) entryWindowPlaceholder.getChildren().get(0);
        int index = entryWindows.indexOf(currWindow);
        if (!backButtonPlaceholder.getChildren().contains(backButton)) {
            backButtonPlaceholder.getChildren().add(backButton);
        }
        if (index != entryWindows.size() - 1) {
            entryWindowPlaceholder.getChildren().clear();
            entryWindowPlaceholder.getChildren().add(entryWindows.get(index + 1));
        }
        if (index == entryWindows.size() - 2) {
            nextButtonPlaceholder.getChildren().remove(nextButton);
        }
    }

}