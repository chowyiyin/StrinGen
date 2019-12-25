package stringen.ui;

import java.io.IOException;
import java.util.ArrayList;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import stringen.logic.requirements.ALevelPrerequisite;
import stringen.logic.requirements.CoursePreclusion;
import stringen.logic.requirements.CoursePrerequisite;
import stringen.logic.requirements.MajorPreclusion;
import stringen.logic.requirements.MajorPrerequisite;
import stringen.logic.requirements.ModulePreclusion;
import stringen.logic.requirements.ModulePrerequisite;
import stringen.logic.requirements.Requirement;
import stringen.logic.requirements.RequirementList;

public class EntryFieldCard extends HBox {

    @FXML
    private Label requirementIndexLabel;

    @FXML
    private HBox cardPlaceholder;

    @FXML
    private ComboBox<String> requirementOptions;

    @FXML
    private VBox boxAndButtonPlaceholder;

    @FXML
    private HBox orButtonPlaceholder;

    @FXML
    private Button addAndButton;

    @FXML
    private Button addOrButton;

    @FXML
    private Label conjunctionLabel;

    @FXML
    private HBox newRequirementButtonPlaceholder;

    @FXML
    private Button newRequirementButton;

    private EntryWindow parent;
    private boolean isNewRequirement;
    private Label orLabel;
    private int requirementNumber;
    private EntryType entryType;

    public EntryFieldCard(EntryWindow parent, boolean isNewRequirement, int requirementNumber) {
        this.parent = parent;
        this.isNewRequirement = isNewRequirement;
        this.requirementNumber = requirementNumber;
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
        conjunctionLabel.setText(null);
        if (isNewRequirement) {
            requirementIndexLabel.setText("Requirement " + requirementNumber);
        } else {
            requirementIndexLabel.setText(null);
            requirementIndexLabel.prefHeightProperty().set(0);
        }
        orLabel = new Label("OR");
        orLabel.getStyleClass().add("label");
    }

    @FXML
    public void showEntries() {
        String entryTypeString = requirementOptions.getValue();
        entryType = EntryType.getEntryType(entryTypeString);
        if (cardPlaceholder.getChildren().size() > 0) {
            ObservableList<Node> children = cardPlaceholder.getChildren();
            for (int i = children.size() - 1; i > 1; i--) {
                cardPlaceholder.getChildren().remove(children.get(i));
            }
        }
        switch(entryType) {
        case MOD_PREREQ:
            cardPlaceholder.getChildren().add(new ModulePrerequisiteCard(cardPlaceholder));
            break;
        case COURSE_PREREQ:
            cardPlaceholder.getChildren().add(new CoursePrerequisiteCard(cardPlaceholder));
            break;
        case MC_PREREQ:
            cardPlaceholder.getChildren().add(new McPrerequisiteCard(cardPlaceholder));
            break;
        case MAJOR_PREREQ:
            cardPlaceholder.getChildren().add(new MajorPrerequisiteCard(cardPlaceholder));
            break;
        case CAP_PREREQ:
            cardPlaceholder.getChildren().add(new CapPrerequisiteCard());
            break;
        case A_LEVEL_PREREQ:
            cardPlaceholder.getChildren().add(new ALevelPrerequisiteCard(cardPlaceholder));
            break;
        case COURSE_PRECLUSION:
            cardPlaceholder.getChildren().add(new CoursePreclusionCard(cardPlaceholder));
            break;
        case MODULE_PRECLUSION:
            cardPlaceholder.getChildren().add(new ModulePreclusionCard(cardPlaceholder));
            break;
        case MAJOR_PRECLUSION:
            cardPlaceholder.getChildren().add(new MajorPreclusionCard(cardPlaceholder));
            break;
        }
    }

    @FXML
    public void addAnd() {
        boxAndButtonPlaceholder.getChildren().remove(addAndButton);
        orButtonPlaceholder.getChildren().remove(addOrButton);
        newRequirementButtonPlaceholder.getChildren().remove(newRequirementButton);
        parent.addAndEntryFieldCard(requirementNumber);
    }

    @FXML
    public void addOr() {
        orButtonPlaceholder.getChildren().remove(addOrButton);
        orButtonPlaceholder.getChildren().add(orLabel);
        newRequirementButtonPlaceholder.getChildren().remove(newRequirementButton);
        parent.addOrEntryFieldCard(requirementNumber);
    }

    public void setConjunctionLabel() {
        conjunctionLabel.setText("AND");
    }

    @FXML
    public void addNewRequirement() {
        parent.addNewRequirement();
    }

    public Label getOrLabel() {
        return orLabel;
    }

    public boolean containsOrLabel() {
        return orButtonPlaceholder.getChildren().contains(orLabel);
    }

    public int getRequirementNumber() {
        return requirementNumber;
    }

    public boolean isNewRequirement() {
        return isNewRequirement;
    }

    public ArrayList<Requirement> getResponses() {
        ArrayList<Requirement> requirements = new ArrayList<>();
        if (entryType == null) {
            return requirements;
        }
        switch(entryType) {
        case MOD_PREREQ:
            ArrayList<ModulePrerequisite> modulePrerequisites = new ArrayList<>();
            for (int i = 0; i < cardPlaceholder.getChildren().size(); i++) {
                ModulePrerequisiteCard modulePrerequisiteCard = (ModulePrerequisiteCard) cardPlaceholder
                        .getChildren().get(i);
                modulePrerequisites.add(modulePrerequisiteCard.getModulePrerequisite());
            }
            requirements.add(new RequirementList(modulePrerequisites, ModulePrerequisite.PREFIX));
            return requirements;
        case COURSE_PREREQ:
            ArrayList<CoursePrerequisite> coursePrerequisites = new ArrayList<>();
            for (int i = 0; i < cardPlaceholder.getChildren().size(); i++) {
                CoursePrerequisiteCard coursePrerequisiteCard = (CoursePrerequisiteCard) cardPlaceholder
                        .getChildren().get(i);
                coursePrerequisites.add(coursePrerequisiteCard.getCoursePrerequisite());
            }
            requirements.add(new RequirementList(coursePrerequisites, CoursePrerequisite.PREFIX));
            return requirements;
        case MC_PREREQ:
            ArrayList<Requirement> mcPrerequisites = new ArrayList<>();
            for (int i = 0; i < cardPlaceholder.getChildren().size(); i++) {
                McPrerequisiteCard mcPrerequisiteCard = (McPrerequisiteCard) cardPlaceholder
                        .getChildren().get(i);
                mcPrerequisites.add(mcPrerequisiteCard.getMcPrerequisite());
            }
            return mcPrerequisites;
        case MAJOR_PREREQ:
            ArrayList<MajorPrerequisite> majorPrerequisites = new ArrayList<>();
            for (int i = 0; i < cardPlaceholder.getChildren().size(); i++) {
                MajorPrerequisiteCard majorPrerequisiteCard = (MajorPrerequisiteCard) cardPlaceholder
                        .getChildren().get(i);
                majorPrerequisites.add(majorPrerequisiteCard.getMajorPrerequisite());
            }
            requirements.add(new RequirementList(majorPrerequisites, MajorPrerequisite.PREFIX));
            return requirements;
        case CAP_PREREQ:
            ArrayList<Requirement> capPrerequisites = new ArrayList<>();
            for (int i = 0; i < cardPlaceholder.getChildren().size(); i++) {
                CapPrerequisiteCard capPrerequisiteCard = (CapPrerequisiteCard) cardPlaceholder
                        .getChildren().get(i);
                capPrerequisites.add(capPrerequisiteCard.getCapPrerequisite());
            }
            return capPrerequisites;
        case A_LEVEL_PREREQ:
            ArrayList<ALevelPrerequisite> aLevelPrerequisites = new ArrayList<>();
            for (int i = 0; i < cardPlaceholder.getChildren().size(); i++) {
                ALevelPrerequisiteCard aLevelPrerequisiteCard = (ALevelPrerequisiteCard) cardPlaceholder
                        .getChildren().get(i);
                aLevelPrerequisites.add(aLevelPrerequisiteCard.getALevelPrerequisite());
            }
            requirements.add(new RequirementList(aLevelPrerequisites, ALevelPrerequisite.PREFIX));
            return requirements;
        case COURSE_PRECLUSION:
            ArrayList<CoursePreclusion> coursePreclusions = new ArrayList<>();
            for (int i = 0; i < cardPlaceholder.getChildren().size(); i++) {
                CoursePreclusionCard coursePreclusionCard = (CoursePreclusionCard) cardPlaceholder
                        .getChildren().get(i);
                coursePreclusions.add(coursePreclusionCard.getCoursePreclusion());
            }
            requirements.add(new RequirementList(coursePreclusions, CoursePreclusion.PREFIX));
            return requirements;
        case MODULE_PRECLUSION:
            ArrayList<ModulePreclusion> modulePreclusions = new ArrayList<>();
            for (int i = 0; i < cardPlaceholder.getChildren().size(); i++) {
                ModulePreclusionCard modulePreclusionCard = (ModulePreclusionCard) cardPlaceholder
                        .getChildren().get(i);
                modulePreclusions.add(modulePreclusionCard.getModulePreclusion());
            }
            requirements.add(new RequirementList(modulePreclusions, ModulePreclusion.PREFIX));
            return requirements;
        case MAJOR_PRECLUSION:
            ArrayList<MajorPreclusion> majorPreclusions = new ArrayList<>();
            for (int i = 0; i < cardPlaceholder.getChildren().size(); i++) {
                MajorPreclusionCard majorPreclusionCard = (MajorPreclusionCard) cardPlaceholder
                        .getChildren().get(i);
                majorPreclusions.add(majorPreclusionCard.getMajorPreclusion());
            }
            requirements.add(new RequirementList(majorPreclusions, MajorPreclusion.PREFIX));
            return requirements;
        default:
            return requirements;
        }
    }

}
