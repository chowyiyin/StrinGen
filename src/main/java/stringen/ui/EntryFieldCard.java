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
import stringen.ui.exceptions.InvalidInputException;

/**
 * Represents a UI component that corresponds to a single {@code EntryType}. Represented horizontally in the UI.
 */
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

    /**
     * Configures the delete button based on the state of the parent.
     */
    private void configureDeleteButton() {
        if (parent.isOnlyReq(this)) {
            cardPlaceholder.getChildren().remove(deleteButton);
        }
    }

    /**
     * Initialises the labels of the card.
     */
    private void initialiseLabels() {
        conjunctionLabel.setText(null);
        initialiseRequirementIndexLabel();
        initialiseOrLabel();
    }

    private void initialiseOrLabel() {
        orLabel = new Label("OR");
        orLabel.setId("orLabel");
        orLabel.prefWidthProperty().bind(orButtonPlaceholder.widthProperty());
        orLabel.setTextAlignment(TextAlignment.RIGHT);
    }

    /**
     * Initialises the requirementIndexLabel depending on the card's property.
     */
    private void initialiseRequirementIndexLabel() {
        if (isNewRequirement) {
            requirementIndexLabel.setText("Requirement " + requirementNumber);
        } else {
            requirementIndexLabel.setText(null);
            requirementIndexLabel.prefHeightProperty().set(0);
        }
    }

    /**
     * Initialise the {@code ComboBox} for with each {@code EntryType}.
     */
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

    /**
     * Shows the {@code RequirementCard} corresponding to teh selected {@code EntryType}.
     */
    @FXML
    public void showEntries() {
        String entryTypeString = requirementOptions.getValue();
        if (entryTypeString == null) {
            return;
        }
        entryType = EntryType.getEntryType(entryTypeString);
        cardPlaceholder.getChildren().clear();
        setCard();
        cardPlaceholder.getChildren().add(deleteButton);
    }

    private void setCard() {
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
    }

    /**
     * Adds a new {@code EntryFieldCard} in the parent window.
     * Update labels and buttons to indicate that the new card has a logical `AND` relationship with this card.
     * The new card is either added right after this card or at the end of this card's requirement number.
     */
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

    /**
     * Adds a new {@code EntryFieldCard} in the parent window.
     * Update labels and buttons to indicate a logical `OR` relationship with this card and all cards above this
     * and before the previous `OR` label.
     * The new card is added right after this card.
     */
    @FXML
    public void addOr() {
        orButtonPlaceholder.getChildren().remove(orButton);
        orButtonPlaceholder.getChildren().add(orLabel);
        newRequirementButtonPlaceholder.getChildren().remove(newRequirementButton);
        parent.addOrEntryFieldCard(requirementNumber);
    }

    /**
     * Adds a new {@code RequirementCard} to the right of all current cards.
     * @param newCard The new card to be added.
     */
    public void addNewCard(RequirementCard newCard) {
        cardPlaceholder.getChildren().remove(deleteButton);
        cardPlaceholder.getChildren().add(newCard);
        cardPlaceholder.getChildren().add(deleteButton);
    }

    @FXML
    public void addNewRequirement() {
        parent.addNewRequirement();
    }

    /**
     * Removes the last {@code RequirementCard}.
     */
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

            // change dropdown back to default
            requirementOptions.getSelectionModel().clearSelection();
            requirementOptions.setPromptText("REQUIREMENT");
            entryType = null;
            configureDeleteButton();
        } else if (numOfChildren == 1) {
            // there are no cards
            // delete the entire card
            parent.deleteRequirement(this);
        } else {
            throw new IllegalStateException();
        }
    }

    /**
     * Extracts information from the user input depending on the entry type and returns a {@code Requirement}.
     */
    public Requirement getResponses() throws InvalidInputException {
        if (entryType == null) {
            return null;
        }
        switch(entryType) {
        case MOD_PREREQ:
            return getModPrereq();
        case COURSE_PREREQ:
            return getCoursePrereq();
        case MC_PREREQ:
            return getMcPrereq();
        case MAJOR_PREREQ:
            return getMajorPrereq();
        case CAP_PREREQ:
            return getCapPrereq();
        case A_LEVEL_PREREQ:
            return getALevelPrereq();
        case COURSE_PRECLUSION:
            return getCoursePreclusion();
        case MODULE_PRECLUSION:
            return getModulePreclusion();
        case MAJOR_PRECLUSION:
            return getMajorPreclusion();
        case CONCURRENT_MODULE:
            return getConcurrentModule();
        default:
            return null;
        }
    }

    /**
     * Extracts responses from the user input returns a {@code RequirementList} of {@code ModuleConcurrent}.
     */
    private Requirement getConcurrentModule() throws InvalidInputException {
        ArrayList<ModuleConcurrent> moduleConcurrents = new ArrayList<>();
        for (int i = 0; i < cardPlaceholder.getChildren().size() - 1; i++) {
            ModuleConcurrentCard moduleConcurrentCard = (ModuleConcurrentCard) cardPlaceholder.getChildren().get(i);
            moduleConcurrents.add(moduleConcurrentCard.getModuleConcurrent());
        }
        return new RequirementList(moduleConcurrents, ModuleConcurrent.PREFIX);
    }

    /**
     * Extracts responses from the user input returns a {@code RequirementList} of {@code MajorPreclusion}.
     */
    private Requirement getMajorPreclusion() throws InvalidInputException {
        ArrayList<MajorPreclusion> majorPreclusions = new ArrayList<>();
        for (int i = 0; i < cardPlaceholder.getChildren().size() - 1; i++) {
            MajorPreclusionCard majorPreclusionCard = (MajorPreclusionCard) cardPlaceholder
                    .getChildren().get(i);
            majorPreclusions.add(majorPreclusionCard.getMajorPreclusion());
        }
        return new RequirementList(majorPreclusions, MajorPreclusion.PREFIX);
    }

    /**
     * Extracts responses from the user input returns a {@code RequirementList} of {@code ModulePreclusion}.
     */
    private Requirement getModulePreclusion() throws InvalidInputException {
        ArrayList<ModulePreclusion> modulePreclusions = new ArrayList<>();
        for (int i = 0; i < cardPlaceholder.getChildren().size() - 1; i++) {
            ModulePreclusionCard modulePreclusionCard = (ModulePreclusionCard) cardPlaceholder
                    .getChildren().get(i);
            modulePreclusions.add(modulePreclusionCard.getModulePreclusion());
        }
        return new RequirementList(modulePreclusions, ModulePreclusion.PREFIX);
    }

    /**
     * Extracts responses from the user input returns a {@code RequirementList} of {@code CoursePreclusion}.
     */
    private Requirement getCoursePreclusion() throws InvalidInputException {
        ArrayList<CoursePreclusion> coursePreclusions = new ArrayList<>();
        for (int i = 0; i < cardPlaceholder.getChildren().size() - 1; i++) {
            CoursePreclusionCard coursePreclusionCard = (CoursePreclusionCard) cardPlaceholder
                    .getChildren().get(i);
            coursePreclusions.add(coursePreclusionCard.getCoursePreclusion());
        }
        return new RequirementList(coursePreclusions, CoursePreclusion.PREFIX);
    }

    /**
     * Extracts responses from the user input returns a {@code RequirementList} of {@code ALevelPrerequisite}.
     */
    private Requirement getALevelPrereq() throws InvalidInputException {
        ArrayList<ALevelPrerequisite> aLevelPrerequisites = new ArrayList<>();
        for (int i = 0; i < cardPlaceholder.getChildren().size() - 1; i++) {
            ALevelPrerequisiteCard aLevelPrerequisiteCard = (ALevelPrerequisiteCard) cardPlaceholder
                    .getChildren().get(i);
            aLevelPrerequisites.add(aLevelPrerequisiteCard.getALevelPrerequisite());
        }
        return new RequirementList(aLevelPrerequisites, ALevelPrerequisite.PREFIX);
    }

    /**
     * Extracts responses from the user input returns a {@code RequirementList} of {@code CapPrerequisite}.
     */
    private Requirement getCapPrereq() throws InvalidInputException {
        CapPrerequisiteCard capPrerequisiteCard = (CapPrerequisiteCard) cardPlaceholder
                .getChildren().get(0);
        return capPrerequisiteCard.getCapPrerequisite();
    }

    /**
     * Extracts responses from the user input returns a {@code RequirementList} of {@code MajorPrerequisite}.
     */
    private Requirement getMajorPrereq() throws InvalidInputException {
        ArrayList<MajorPrerequisite> majorPrerequisites = new ArrayList<>();
        for (int i = 0; i < cardPlaceholder.getChildren().size() - 1; i++) {
            MajorPrerequisiteCard majorPrerequisiteCard = (MajorPrerequisiteCard) cardPlaceholder
                    .getChildren().get(i);
            majorPrerequisites.add(majorPrerequisiteCard.getMajorPrerequisite());
        }
        return new RequirementList(majorPrerequisites, MajorPrerequisite.PREFIX);
    }

    /**
     * Extracts responses from the user input returns a {@code RequirementList} of {@code ModulePrerequisite} or a
     * single {@code McPrerequisite}.
     */
    private Requirement getMcPrereq() throws InvalidInputException {
        McPrerequisiteCard card = (McPrerequisiteCard) cardPlaceholder.getChildren().get(0);
        if (cardPlaceholder.getChildren().size() == 2 && !card.hasModuleCode()) {
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
    }

    /**
     * Extracts responses from the user input returns a {@code RequirementList} of {@code CoursePrerequisite}.
     */
    private Requirement getCoursePrereq() throws InvalidInputException {
        ArrayList<CoursePrerequisite> coursePrerequisites = new ArrayList<>();
        for (int i = 0; i < cardPlaceholder.getChildren().size() - 1; i++) {
            CoursePrerequisiteCard coursePrerequisiteCard = (CoursePrerequisiteCard) cardPlaceholder
                    .getChildren().get(i);
            coursePrerequisites.add(coursePrerequisiteCard.getCoursePrerequisite());
        }
        return new RequirementList(coursePrerequisites, CoursePrerequisite.PREFIX);
    }

    /**
     * Extracts responses from the user input returns a {@code RequirementList} of {@code ModulePrerequisite}.
     */
    private Requirement getModPrereq() throws InvalidInputException {
        ArrayList<ModulePrerequisite> modulePrerequisites = new ArrayList<>();
        for (int i = 0; i < cardPlaceholder.getChildren().size() - 1; i++) {
            ModulePrerequisiteCard modulePrerequisiteCard = (ModulePrerequisiteCard) cardPlaceholder
                    .getChildren().get(i);
            modulePrerequisites.add(modulePrerequisiteCard.getModulePrerequisite());
        }
        return new RequirementList(modulePrerequisites, ModulePrerequisite.PREFIX);
    }

    /**
     * Update the requirementIndexLabel and Boolean property to mark this card as the start of a new requirement.
     */
    public void setAsStart() {
        requirementIndexLabel.setText("Requirement " + requirementNumber);
        requirementIndexLabel.prefHeightProperty().set(Region.USE_COMPUTED_SIZE);
        isNewRequirement = true;
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

    public void removeDeleteButton() {
        cardPlaceholder.getChildren().remove(deleteButton);
    }

    public void addDeleteButton() {
        cardPlaceholder.getChildren().add(deleteButton);
    }

    public boolean containsDeleteButton() {
        return cardPlaceholder.getChildren().contains(deleteButton);
    }

    public boolean hasReqCards() {
        return cardPlaceholder.getChildren().size() > 1;
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

}
