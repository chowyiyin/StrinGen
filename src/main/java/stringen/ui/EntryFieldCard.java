package stringen.ui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;

public class EntryFieldCard extends HBox {

    @FXML
    private HBox cardPlaceholder;

    @FXML
    private ComboBox<String> requirementOptions;

    public EntryFieldCard() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(EntryFieldCard.class.getResource("/view/EntryFieldCard.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        for (EntryType entryType: EntryType.values()) {
            requirementOptions.getItems().add(entryType.getName());
        }
    }

    @FXML
    public void showEntries() {
        String entryTypeString = requirementOptions.getValue();
        EntryType entryType = EntryType.getEntryType(entryTypeString);
        switch(entryType) {
        case MOD_PREREQ:
            cardPlaceholder.getChildren().add(new ModulePrerequisiteCard(this));
        case COURSE_PREREQ:
        case MC_PREREQ:
        case MAJOR_PREREQ:
        case CAP_PREREQ:
        case A_LEVEL_PREREQ:
        case COURSE_PRECLUSION:
        case MODULE_PRECLUSION:
        case MAJOR_PRECLUSION:
        }

    }

}
