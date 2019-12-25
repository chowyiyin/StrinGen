package stringen.ui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import stringen.Util;

public class EntryDialog extends GridPane {

    @FXML
    private ComboBox<String> cohortStart;
    @FXML
    private ComboBox<String> cohortEnd;
    @FXML
    private ComboBox<String> otherRequirement;
    @FXML
    private ComboBox<String> modulePrefix;
    @FXML
    private Button addCohortButton;
    @FXML
    private Button addOtherSubRequirementButton;
    @FXML
    private Button addSubRequirementButton;
    @FXML
    private Button addModPrereqButton;
    @FXML
    private VBox mcSubRequirements;
    @FXML
    private VBox otherRequirements;
    @FXML
    private AnchorPane entryDialog;
    @FXML
    private TextField mcs;
    @FXML
    private VBox modulePrereqs;

    public EntryDialog() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(EntryDialog.class.getResource("/view/EntryDialog.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        cohortStart.getItems().addAll(Util.YEARS);
        cohortStart.getSelectionModel().select("YEAR");
        cohortEnd.getItems().addAll(Util.YEARS);
        cohortEnd.getSelectionModel().select("YEAR");
    }

    /*
    public Module updateModule(Module module, Parser parser) {
        // get cohort year range
        String startYear = cohortStart.getSelectionModel().getSelectedItem();
        String endYear = cohortEnd.getSelectionModel().getSelectedItem();

        Cohort cohort = parser.parseCohort(startYear, endYear);
        updateCohort(cohort, parser);
        module.addNewCohort(cohort);
        return module;
    }

    public void updateCohort(Cohort cohort, Parser parser) {
        // get mc prerequisites
        McPrerequisite mcPrerequisite = null;
        if (!mcs.getText().equals("")) {
            mcPrerequisite = parser.parseMcPrerequisite(mcs.getText(), cohort);
        }

        // get module prerequisites
        //ModuleRequirementList modPrereq = getModPrereqs(parser);
        //if (!modPrereq.isEmpty()) {
        //}
    }

    @FXML
    private void createNewPane() {
        Parent parent = this.getParent();
        VBox entryDialogs = (VBox) parent;
        entryDialog.getChildren().remove(addCohortButton);
        entryDialogs.getChildren().add(new EntryDialog());
    }

    @FXML
    private void addSubRequirement() {
        mcSubRequirements.getChildren().remove(addSubRequirementButton);
        mcSubRequirements.getChildren().add(new McSubRequirement());
        mcSubRequirements.getChildren().add(addSubRequirementButton);
    }

    @FXML
    private void createNewOtherSubRequirement() {
        otherRequirements.getChildren().remove(addOtherSubRequirementButton);
        ObservableList<Node> children = otherRequirements.getChildren();
        for (Node child : children) {
            if (child instanceof OtherSubRequirement) {
                OtherSubRequirement childRequirements = (OtherSubRequirement) child;
                childRequirements.enableOptionBox();
            }
        }
        otherRequirements.getChildren().add(new OtherSubRequirement());
        otherRequirements.getChildren().add(new StackPane());
        otherRequirements.getChildren().add(addOtherSubRequirementButton);
    }

    @FXML
    private void addModuleRequirement() {
        modulePrereqs.getChildren().remove(addModPrereqButton);
        //modulePrereqs.getChildren().add(new ModulePrerequisiteEntry());
        modulePrereqs.getChildren().add(addModPrereqButton);
    }

    /*
    private ModuleRequirementList getModPrereqs(Parser parser) {
        ArrayList<ModuleRequirement> modules = new ArrayList<>();

        int numberOfModules = modulePrereqs.getChildren().size() - 1;

        // ignore the last child (button)
        assert(modulePrereqs.getChildren().get(numberOfModules) instanceof Button);
        for (int i = 0; i < numberOfModules; i++) {
            ModulePrerequisiteEntry moduleRequirement = (ModulePrerequisiteEntry) modulePrereqs.getChildren().get(i);
            String moduleCode = moduleRequirement.getCode();
            String grade = moduleRequirement.getMinimumGrade();
            if (moduleCode.length() == 0) {
                continue;
            }
            modules.add(parser.parseModulePrerequisite(moduleCode, grade));
        }
        return new ModuleRequirementList(modules, ModulePrerequisite.PREFIX);
    }
     */
}


