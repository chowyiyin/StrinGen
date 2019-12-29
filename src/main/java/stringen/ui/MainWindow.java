package stringen.ui;

import java.io.IOException;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import stringen.Util;
import stringen.ui.exceptions.InvalidInputException;

/**
 * Represents the main window of the application.
 * Provides the basic application layout and space where other JavaFX elements are placed.
 */
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

    /**
     * Initialises required information in {@code Util} and other UI component placeholders.
     */
    void fillInnerParts() {
        Util.initialise();
        startUp();
    }

    private void startUp() {
        // initialise generator
        generator = new Generator();

        // initialise entry window
        EntryWindow entryWindow = new EntryWindow(this);
        entryWindow.prefHeightProperty().bind(this.heightProperty());

        // remove buttons
        entryWindow.removeDeleteCohortButton();
        backButtonPlaceholder.getChildren().remove(backButton);
        nextButtonPlaceholder.getChildren().remove(nextButton);

        // add entry window
        entryWindowPlaceholder.getChildren().add(entryWindow);
        entryWindows.add(entryWindow);
    }

    /**
     * Adds a new {@code EntryWindow} that represents another cohort and changes the screen to show the new window.
     */
    @FXML
    public void addCohort() {
        EntryWindow newWindow = new EntryWindow(this);
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

    /**
     * Generates the string based on the information entered into the entry windows.
     */
    @FXML
    private void generateString() {
        try {
            String generatedString = generator.generateString(entryWindows);
            showResultWindow(generatedString);
        } catch (InvalidInputException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR, exception.getMessage(), ButtonType.CLOSE);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.CLOSE) {
                alert.close();
            }
        }
    }

    /**
     * Changes the screen to show the result window.
     * @param generatedString
     */
    private void showResultWindow(String generatedString) {
        windowPlaceholder.getChildren().remove(entryWindowPlaceholder);
        windowPlaceholder.getChildren().remove(buttonPlaceholder);
        resultWindow = new ResultWindow(generatedString, this);
        windowPlaceholder.getChildren().add(resultWindow);
    }

    /**
     * Refreshes the screen and shows a new {@code EntryWindow}.
     */
    public void refresh() {
        windowPlaceholder.getChildren().remove(resultWindow);
        entryWindows.clear();
        entryWindowPlaceholder.getChildren().clear();
        startUp();
        windowPlaceholder.getChildren().add(entryWindowPlaceholder);
        windowPlaceholder.getChildren().add(buttonPlaceholder);
    }

    /**
     * Removes an {@code EntryWindow} corresponding to a given cohort.
     * The screen is changed to show another {@code EntryWindow} and the buttons are updated accordingly.
     * @param toRemove The window to be removed.
     */
    public void deleteCohort(EntryWindow toRemove) {
        int windowIndex = entryWindows.indexOf(toRemove);
        entryWindowPlaceholder.getChildren().remove(toRemove);
        if (windowIndex != 0) {
            // show previous window
            entryWindowPlaceholder.getChildren().add(entryWindows.get(windowIndex - 1));
            if (windowIndex == 1) {
                // update back button
                backButtonPlaceholder.getChildren().remove(backButton);
            }
        } else {
            // show next window if the window to be deleted is the first
            EntryWindow nextWindow = entryWindows.get(windowIndex + 1);
            entryWindowPlaceholder.getChildren().add(nextWindow);
            // update back button
            backButtonPlaceholder.getChildren().remove(backButton);
        }

        entryWindows.remove(toRemove);

        if (entryWindows.size() == 1) {
            // update delete cohort button
            entryWindows.get(0).removeDeleteCohortButton();
        }
    }

    /**
     * Checks if the given {@code EntryWindow} is the only window left.
     */
    public boolean isOnlyWindow(EntryWindow entryWindow) {
        return entryWindows.contains(entryWindow) && entryWindows.size() == 1;
    }

    /**
     * Changes the screen to the previous window.
     * Checks and updates all the buttons correspondingly.
     */
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

    /**
     * Changes the screen to the next window.
     * Checks and updates all the buttons correspondingly.
     */
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