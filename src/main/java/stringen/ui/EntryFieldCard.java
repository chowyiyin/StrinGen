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
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import stringen.logic.requirements.ALevelPrerequisite;
import stringen.logic.requirements.CoursePreclusion;
import stringen.logic.requirements.CoursePrerequisite;
import stringen.logic.requirements.MajorPreclusion;
import stringen.logic.requirements.MajorPrerequisite;
import stringen.logic.requirements.ModuleConcurrent;
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
    private VBox boxPlaceholder;

    @FXML
    private HBox orButtonPlaceholder;

    @FXML
    private HBox andButtonPlaceholder;

    @FXML
    private Button andButton;

    @FXML
    private Button orButton;

    @FXML
    private Label conjunctionLabel;

    @FXML
    private HBox newRequirementButtonPlaceholder;

    @FXML
    private Button newRequirementButton;

    @FXML
    private Button deleteButton;

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
        initialiseRequirementOptions();
        initialiseLabels();
        configureDeleteButton();
    }

    private void configureDeleteButton() {
        if (parent.isOnlyReq(this)) {
            cardPlaceholder.getChildren().remove(deleteButton);
        }
    }

    private void initialiseLabels() {
        conjunctionLabel.setText(null);
        if (isNewRequirement) {
            requirementIndexLabel.setText("Requirement " + requirementNumber);
        } else {
            requirementIndexLabel.setText(null);
            requirementIndexLabel.prefHeightProperty().set(0);
        }
        orLabel = new Label("OR");
        orLabel.setId("orLabel");
        orLabel.prefWidthProperty().bind(orButtonPlaceholder.widthProperty());
        orLabel.setTextAlignment(TextAlignment.RIGHT);
    }

    private void initialiseRequirementOptions() {
        for (EntryType entryType: EntryType.values()) {
            requirementOptions.getItems().add(entryType.getName());
        }
        requirementOptions.setButtonCell(new ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty) ;
                if (empty || item == null) {
                    setText("REQUIREMENT");
                } else {
                    setText(item);
                }
            }
        });
    }

    @FXML
    public void showEntries() {
        String entryTypeString = requirementOptions.getValue();
        if (entryTypeString == null) {
            return;
        }
        entryType = EntryType.getEntryType(entryTypeString);
        resetCardPlaceholder();
        switch(entryType) {
        case MOD_PREREQ:
            cardPlaceholder.getChildren().add(new ModulePrerequisiteCard(this));
            break;
        case COURSE_PREREQ:
            cardPlaceholder.getChildren().add(new CoursePrerequisiteCard(this));
            break;
        case MC_PREREQ:
            cardPlaceholder.getChildren().add(new McPrerequisiteCard(this));
            break;
        case MAJOR_PREREQ:
            cardPlaceholder.getChildren().add(new MajorPrerequisiteCard(this));
            break;
        case CAP_PREREQ:
            cardPlaceholder.getChildren().add(new CapPrerequisiteCard());
            break;
        case A_LEVEL_PREREQ:
            cardPlaceholder.getChildren().add(new ALevelPrerequisiteCard(this));
            break;
        case COURSE_PRECLUSION:
            cardPlaceholder.getChildren().add(new CoursePreclusionCard(this));
            break;
        case MODULE_PRECLUSION:
            cardPlaceholder.getChildren().add(new ModulePreclusionCard(this));
            break;
        case MAJOR_PRECLUSION:
            cardPlaceholder.getChildren().add(new MajorPreclusionCard(this));
            break;
        case CONCURRENT_MODULE:
            cardPlaceholder.getChildren().add(new ModuleConcurrentCard(this));
            break;
        }
        cardPlaceholder.getChildren().add(deleteButton);
    }

    private void resetCardPlaceholder() {
        ObservableList<Node> children = cardPlaceholder.getChildren();
        for (int i = children.size() - 1; i >= 0; i--) {
            cardPlaceholder.getChildren().remove(children.get(i));
        }
    }

    @FXML
    public void addAnd() {
        andButtonPlaceholder.getChildren().remove(andButton);
        if (!orButtonPlaceholder.getChildren().contains(orButton)) {
            orButtonPlaceholder.getChildren().remove(orLabel);
            parent.addAndEntryFieldCardAfter(this);
        } else {
            orButtonPlaceholder.getChildren().remove(orButton);
            newRequirementButtonPlaceholder.getChildren().remove(newRequirementButton);
            parent.addAndEntryFieldCard(requirementNumber);
        }
    }

    @FXML
    public void addOr() {
        orButtonPlaceholder.getChildren().remove(orButton);
        orButtonPlaceholder.getChildren().add(orLabel);
        newRequirementButtonPlaceholder.getChildren().remove(newRequirementButton);
        parent.addOrEntryFieldCard(requirementNumber);
    }

    public void addNewCard(HBox newCard) {
        cardPlaceholder.getChildren().remove(deleteButton);
        cardPlaceholder.getChildren().add(newCard);
        cardPlaceholder.getChildren().add(deleteButton);
    }

    public void setConjunctionLabel() {
        conjunctionLabel.setText("AND");
    }

    public void removeConjunctionLabel() {
        conjunctionLabel.setText(null);
    }

    public void setDisjunctionLabel() {
        orButtonPlaceholder.getChildren().remove(orButton);
        orButtonPlaceholder.getChildren().add(orLabel);
    }

    public void removeReqButton() {
        newRequirementButtonPlaceholder.getChildren().remove(newRequirementButton);
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

    public boolean containsOrButton() {
        return orButtonPlaceholder.getChildren().contains(orButton);
    }

    public void addOrButton() {
        orButtonPlaceholder.getChildren().remove(orLabel);
        orButtonPlaceholder.getChildren().add(orButton);
    }

    public boolean containsAndButton() {
        return andButtonPlaceholder.getChildren().contains(andButton);
    }

    public void addAndButton() {
        andButtonPlaceholder.getChildren().add(andButton);
    }

    public boolean containsReqButton() {
        return newRequirementButtonPlaceholder.getChildren().contains(newRequirementButton);
    }

    public void addReqButton() {
        newRequirementButtonPlaceholder.getChildren().add(newRequirementButton);
    }

    public int getRequirementNumber() {
        return requirementNumber;
    }

    public void setRequirementNumber(int newNumber) {
        requirementNumber = newNumber;
    }

    public boolean isNewRequirement() {
        return isNewRequirement;
    }

    public Requirement getResponses() {
        if (entryType == null) {
            return null;
        }
        switch(entryType) {
        case MOD_PREREQ:
            ArrayList<ModulePrerequisite> modulePrerequisites = new ArrayList<>();
            for (int i = 0; i < cardPlaceholder.getChildren().size() - 1; i++) {
                ModulePrerequisiteCard modulePrerequisiteCard = (ModulePrerequisiteCard) cardPlaceholder
                        .getChildren().get(i);
                modulePrerequisites.add(modulePrerequisiteCard.getModulePrerequisite());
            }
            return new RequirementList(modulePrerequisites, ModulePrerequisite.PREFIX);
        case COURSE_PREREQ:
            ArrayList<CoursePrerequisite> coursePrerequisites = new ArrayList<>();
            for (int i = 0; i < cardPlaceholder.getChildren().size() - 1; i++) {
                CoursePrerequisiteCard coursePrerequisiteCard = (CoursePrerequisiteCard) cardPlaceholder
                        .getChildren().get(i);
                coursePrerequisites.add(coursePrerequisiteCard.getCoursePrerequisite());
            }
            return new RequirementList(coursePrerequisites, CoursePrerequisite.PREFIX);
        case MC_PREREQ:
            McPrerequisiteCard card = (McPrerequisiteCard) cardPlaceholder.getChildren().get(0);
            if (cardPlaceholder.getChildren().size() == 2 && !card.hasPrefix()) {
                return card.getMcPrerequisite();
            } else {
                int numberOfModules = 0;
                ArrayList<ModulePrerequisite> modules = new ArrayList<>();
                for (int i = 0; i < cardPlaceholder.getChildren().size() - 1; i++) {
                    McPrerequisiteCard mcPrerequisiteCard = (McPrerequisiteCard) cardPlaceholder
                            .getChildren().get(i);
                    if (i == 0) {
                        numberOfModules = mcPrerequisiteCard.getNumberOfModules();
                    }
                    modules.add(mcPrerequisiteCard.getModule());
                }
                return new RequirementList(modules, numberOfModules, ModulePrerequisite.PREFIX, true);
            }
        case MAJOR_PREREQ:
            ArrayList<MajorPrerequisite> majorPrerequisites = new ArrayList<>();
            for (int i = 0; i < cardPlaceholder.getChildren().size() - 1; i++) {
                MajorPrerequisiteCard majorPrerequisiteCard = (MajorPrerequisiteCard) cardPlaceholder
                        .getChildren().get(i);
                majorPrerequisites.add(majorPrerequisiteCard.getMajorPrerequisite());
            }
            return new RequirementList(majorPrerequisites, MajorPrerequisite.PREFIX);
        case CAP_PREREQ:
            CapPrerequisiteCard capPrerequisiteCard = (CapPrerequisiteCard) cardPlaceholder
                    .getChildren().get(0);
            return capPrerequisiteCard.getCapPrerequisite();
        case A_LEVEL_PREREQ:
            ArrayList<ALevelPrerequisite> aLevelPrerequisites = new ArrayList<>();
            for (int i = 0; i < cardPlaceholder.getChildren().size() - 1; i++) {
                ALevelPrerequisiteCard aLevelPrerequisiteCard = (ALevelPrerequisiteCard) cardPlaceholder
                        .getChildren().get(i);
                aLevelPrerequisites.add(aLevelPrerequisiteCard.getALevelPrerequisite());
            }
            return new RequirementList(aLevelPrerequisites, ALevelPrerequisite.PREFIX);
        case COURSE_PRECLUSION:
            ArrayList<CoursePreclusion> coursePreclusions = new ArrayList<>();
            for (int i = 0; i < cardPlaceholder.getChildren().size() - 1; i++) {
                CoursePreclusionCard coursePreclusionCard = (CoursePreclusionCard) cardPlaceholder
                        .getChildren().get(i);
                coursePreclusions.add(coursePreclusionCard.getCoursePreclusion());
            }
            return new RequirementList(coursePreclusions, CoursePreclusion.PREFIX);
        case MODULE_PRECLUSION:
            ArrayList<ModulePreclusion> modulePreclusions = new ArrayList<>();
            for (int i = 0; i < cardPlaceholder.getChildren().size() - 1; i++) {
                ModulePreclusionCard modulePreclusionCard = (ModulePreclusionCard) cardPlaceholder
                        .getChildren().get(i);
                modulePreclusions.add(modulePreclusionCard.getModulePreclusion());
            }
            return new RequirementList(modulePreclusions, ModulePreclusion.PREFIX);
        case MAJOR_PRECLUSION:
            ArrayList<MajorPreclusion> majorPreclusions = new ArrayList<>();
            for (int i = 0; i < cardPlaceholder.getChildren().size() - 1; i++) {
                MajorPreclusionCard majorPreclusionCard = (MajorPreclusionCard) cardPlaceholder
                        .getChildren().get(i);
                majorPreclusions.add(majorPreclusionCard.getMajorPreclusion());
            }
            return new RequirementList(majorPreclusions, MajorPreclusion.PREFIX);
        case CONCURRENT_MODULE:
            ArrayList<ModuleConcurrent> moduleConcurrents = new ArrayList<>();
            for (int i = 0; i < cardPlaceholder.getChildren().size() - 1; i++) {
                ModuleConcurrentCard moduleConcurrentCard = (ModuleConcurrentCard) cardPlaceholder.getChildren().get(i);
                moduleConcurrents.add(moduleConcurrentCard.getModuleConcurrent());
            }
            return new RequirementList(moduleConcurrents, ModuleConcurrent.PREFIX);
        default:
            return null;
        }
    }

    @FXML
    public void delete() {
        int numOfChildren = cardPlaceholder.getChildren().size();
        if (numOfChildren > 2) {
            // there is more than one card
            cardPlaceholder.getChildren().remove(numOfChildren - 2);
            RequirementCard lastCard = (RequirementCard) cardPlaceholder.getChildren().get(numOfChildren - 3);
            lastCard.changeOrLabelToButton();
        } else if (numOfChildren == 2) {
            // there is only one card
            RequirementCard onlyCard = (RequirementCard) cardPlaceholder.getChildren().get(0);
            cardPlaceholder.getChildren().remove(onlyCard);
            requirementOptions.getSelectionModel().clearSelection();
            requirementOptions.setPromptText("REQUIREMENT");
            configureDeleteButton();
        } else if (numOfChildren == 1) {
            // there are no cards
            parent.deleteRequirement(this);
        } else {
            System.out.println(numOfChildren);
            throw new IllegalStateException();
        }
    }

    public void setAsStart() {
        requirementIndexLabel.setText("Requirement " + requirementNumber);
        requirementIndexLabel.prefHeightProperty().set(Region.USE_COMPUTED_SIZE);
        isNewRequirement = true;
    }

    public void removeDeleteButton() {
        cardPlaceholder.getChildren().remove(deleteButton);
    }

    public void addDeleteButton() {
        cardPlaceholder.getChildren().add(deleteButton);
    }

    public boolean containsDeleteButton() {
        return cardPlaceholder.getChildren().contains(deleteButton);
    }

}
