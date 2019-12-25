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
import stringen.logic.prerequisites.ALevelPrerequisite;
import stringen.logic.prerequisites.CapPrerequisite;
import stringen.logic.prerequisites.CoursePreclusion;
import stringen.logic.prerequisites.CoursePrerequisite;
import stringen.logic.prerequisites.MajorPreclusion;
import stringen.logic.prerequisites.MajorPrerequisite;
import stringen.logic.prerequisites.MajorRequirement;
import stringen.logic.prerequisites.MajorRequirementList;
import stringen.logic.prerequisites.McPrerequisite;
import stringen.logic.prerequisites.ModulePreclusion;
import stringen.logic.prerequisites.ModulePrerequisite;
import stringen.logic.prerequisites.ModuleRequirement;
import stringen.logic.prerequisites.ModuleRequirementList;
import stringen.logic.prerequisites.Prerequisite;

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

    private static int index = 0;

    private EntryWindow parent;
    private boolean isNewRequirement;
    private Label orLabel;
    private int requirementNumber;
    private EntryType entryType;

    public EntryFieldCard(EntryWindow parent, boolean isNewRequirement) {
        this.parent = parent;
        this.isNewRequirement = isNewRequirement;
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
            index++;
            requirementIndexLabel.setText("Requirement " + index);
        } else {
            requirementIndexLabel.setText(null);
            requirementIndexLabel.prefHeightProperty().set(0);
        }
        requirementNumber = index;
        orLabel = new Label("OR");
        orLabel.getStyleClass().add("label");
    }

    @FXML
    public void showEntries() {
        String entryTypeString = requirementOptions.getValue();
        entryType = EntryType.getEntryType(entryTypeString);
        if (cardPlaceholder.getChildren().size() > 2) {
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
            cardPlaceholder.getChildren().add(new McPrerequisiteCard());
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
        parent.addAndEntryFieldCard();
    }

    @FXML
    public void addOr() {
        orButtonPlaceholder.getChildren().remove(addOrButton);
        orButtonPlaceholder.getChildren().add(orLabel);
        newRequirementButtonPlaceholder.getChildren().remove(newRequirementButton);
        parent.addOrEntryFieldCard();
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

    public ArrayList<? extends Prerequisite> getResponses() {
        switch(entryType) {
        case MOD_PREREQ:
            ArrayList<ModuleRequirementList> modulePrerequisiteLists = new ArrayList<>();
            ArrayList<ModuleRequirement> modulePrerequisites = new ArrayList<>();
            for (int i = 2; i < cardPlaceholder.getChildren().size(); i++) {
                ModulePrerequisiteCard modulePrerequisiteCard = (ModulePrerequisiteCard) cardPlaceholder
                        .getChildren().get(i);
                modulePrerequisites.add(modulePrerequisiteCard.getModulePrerequisite());
            }
            modulePrerequisiteLists.add(new ModuleRequirementList(modulePrerequisites, 1,
                    ModulePrerequisite.PREFIX));
            return modulePrerequisiteLists;
        case COURSE_PREREQ:
            ArrayList<CoursePrerequisite> coursePrerequisites = new ArrayList<>();
            for (int i = 2; i < cardPlaceholder.getChildren().size(); i++) {
                CoursePrerequisiteCard coursePrerequisiteCard = (CoursePrerequisiteCard) cardPlaceholder
                        .getChildren().get(i);
                coursePrerequisites.add(coursePrerequisiteCard.getCoursePrerequisite());
            }
            return coursePrerequisites;
        case MC_PREREQ:
            ArrayList<McPrerequisite> mcPrerequisites = new ArrayList<>();
            for (int i = 2; i < cardPlaceholder.getChildren().size(); i++) {
                McPrerequisiteCard mcPrerequisiteCard = (McPrerequisiteCard) cardPlaceholder
                        .getChildren().get(i);
                mcPrerequisites.add(mcPrerequisiteCard.getMcPrerequisite());
            }
            return mcPrerequisites;
        case MAJOR_PREREQ:
            ArrayList<MajorRequirementList> majorPrequisiteLists = new ArrayList<>();
            ArrayList<MajorRequirement> majorPrerequisites = new ArrayList<>();
            for (int i = 2; i < cardPlaceholder.getChildren().size(); i++) {
                MajorPrerequisiteCard majorPrerequisiteCard = (MajorPrerequisiteCard) cardPlaceholder
                        .getChildren().get(i);
                majorPrerequisites.add(majorPrerequisiteCard.getMajorPrerequisite());
            }
            majorPrequisiteLists.add(new MajorRequirementList(majorPrerequisites, 1,
                    MajorPrerequisite.PREFIX));
            return majorPrequisiteLists;
        case CAP_PREREQ:
            ArrayList<CapPrerequisite> capPrerequisites = new ArrayList<>();
            for (int i = 2; i < cardPlaceholder.getChildren().size(); i++) {
                CapPrerequisiteCard capPrerequisiteCard = (CapPrerequisiteCard) cardPlaceholder
                        .getChildren().get(i);
                capPrerequisites.add(capPrerequisiteCard.getCapPrerequisite());
            }
            return capPrerequisites;
        case A_LEVEL_PREREQ:
            ArrayList<ALevelPrerequisite> aLevelPrerequisites = new ArrayList<>();
            for (int i = 2; i < cardPlaceholder.getChildren().size(); i++) {
                ALevelPrerequisiteCard aLevelPrerequisiteCard = (ALevelPrerequisiteCard) cardPlaceholder
                        .getChildren().get(i);
                aLevelPrerequisites.add(aLevelPrerequisiteCard.getALevelPrerequisite());
            }
            return aLevelPrerequisites;
        case COURSE_PRECLUSION:
            ArrayList<CoursePreclusion> coursePreclusions = new ArrayList<>();
            for (int i = 2; i < cardPlaceholder.getChildren().size(); i++) {
                CoursePreclusionCard coursePreclusionCard = (CoursePreclusionCard) cardPlaceholder
                        .getChildren().get(i);
                coursePreclusions.add(coursePreclusionCard.getCoursePreclusion());
            }
            return coursePreclusions;
        case MODULE_PRECLUSION:
            ArrayList<ModuleRequirementList> modulePreclusionLists = new ArrayList<>();
            ArrayList<ModuleRequirement> modulePreclusions = new ArrayList<>();
            for (int i = 2; i < cardPlaceholder.getChildren().size(); i++) {
                ModulePreclusionCard modulePreclusionCard = (ModulePreclusionCard) cardPlaceholder
                        .getChildren().get(i);
                modulePreclusions.add(modulePreclusionCard.getModulePreclusion());
            }
            modulePreclusionLists.add(new ModuleRequirementList(modulePreclusions, 1,
                    ModulePreclusion.PREFIX));
            return modulePreclusionLists;
        case MAJOR_PRECLUSION:
            ArrayList<MajorRequirementList> majorPreclusionLists = new ArrayList<>();
            ArrayList<MajorRequirement> majorPreclusions = new ArrayList<>();
            for (int i = 2; i < cardPlaceholder.getChildren().size(); i++) {
                MajorPreclusionCard majorPreclusionCard = (MajorPreclusionCard) cardPlaceholder
                        .getChildren().get(i);
                majorPreclusions.add(majorPreclusionCard.getMajorPreclusion());
            }
            majorPreclusionLists.add(new MajorRequirementList(majorPreclusions, 1,
                    MajorPreclusion.PREFIX));
            return majorPreclusions;
        default:
            throw new IllegalArgumentException("Invalid requirement type");
        }
    }

}
