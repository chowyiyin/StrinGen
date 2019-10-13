import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class EntryDialog extends AnchorPane {

    @FXML
    private ComboBox<String> cohortStart;

    @FXML
    private ComboBox<String> cohortEnd;

    public EntryDialog() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(CommandBox.class.getResource("/view/EntryDialog.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        cohortStart.getItems().addAll("2011", "2012", "2013");
        cohortStart.getSelectionModel().select("YEAR");
        cohortEnd.getItems().addAll("2011", "2012", "2013");
        cohortEnd.getSelectionModel().select("YEAR");
    }

    public Module updateModule(Module module) {
        String startYear = cohortStart.getSelectionModel().getSelectedItem();
        String endYear = cohortEnd.getSelectionModel().getSelectedItem();
        module.addNewCohort(startYear, endYear);
        //...
        return module;
    }

    @FXML
    private void createNewPane() {
        Parent parent = this.getParent();
        VBox entryDialogs = (VBox) parent;
        entryDialogs.getChildren().add(new EntryDialog());
    }

}


