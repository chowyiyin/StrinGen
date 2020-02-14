package stringen.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * Represents a UI component that corresponds to a {@code Cohort}.
 * It contains all the other UI components that will collect information about the requirements for the specified cohort.
 */
public class EntryWindow extends VBox {

    // total number of numbered requirements in this window
    private int numOfRequirements = 1;

    // starting index of requirements in the list view
    private final int REQ_START_INDEX = 3;

    @FXML
    private ListView<HBox> entryListView;

    @FXML
    private HBox deleteCohortButtonPlaceholder;

    @FXML
    private Button deleteCohortButton;

    private MainWindow mainWindow;
    private List<HBox> cards = new ArrayList<HBox>();
    // stores the ending indexes of each numbered requirement
    private ArrayList<Integer> indexPositions = new ArrayList<Integer>();

    public EntryWindow(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(EntryWindow.class.getResource("/view/EntryWindow.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        initialiseDeleteCohortButton();
        initialiseStartingCards();
        initialiseIndexPositions();
        initialiseEntryFieldCard();
        updateScreen();
    }

    /**
     * Initialises the first {@code EntryFieldCard} and adds it to the list of cards.
     */
    private void initialiseEntryFieldCard() {
        EntryFieldCard firstCard = new EntryFieldCard(this, true, numOfRequirements);
        firstCard.removeDeleteButton();
        cards.add(firstCard);
    }

    /**
     * Initialises the index position of the first {@code EntryFieldCard}.
     */
    private void initialiseIndexPositions() {
        indexPositions.add(0, REQ_START_INDEX);
    }

    /**
     * Initialises the title cards and the cohort list card.
     */
    private void initialiseStartingCards() {
        cards.add(new TitleListCard("COHORT", false));
        cards.add(new CohortListCard());
        cards.add(new TitleListCard("REQUIREMENTS", true));
    }

    /**
     * Initialises the delete cohort button.
     */
    private void initialiseDeleteCohortButton() {
        if (mainWindow.isOnlyWindow(this)) {
            deleteCohortButtonPlaceholder.getChildren().remove(deleteCohortButton);
        }
    }

    /**
     * Updates the screen based on the change in cards.
     */
    public void updateScreen() {
        ObservableList<HBox> observableCards = FXCollections.observableArrayList(cards);
        entryListView.setItems(observableCards);
        entryListView.setCellFactory(lst ->
                new ListCell<HBox>() {
                    @Override
                    protected void updateItem(HBox item, boolean empty) {
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

    /**
     * Adds a new {@code EntryFieldCard} at the index right after that of the given card.
     * This new card has a logical `AND` relationship with the given previous card.
     * @param prevCard
     */
    public void addAndEntryFieldCardAfter(EntryFieldCard prevCard) {
        int indexOfPrevCard = cards.indexOf(prevCard);
        EntryFieldCard entryFieldCard = new EntryFieldCard(this, false, prevCard.getRequirementNumber());
        entryFieldCard.setConjunctionLabel();
        entryFieldCard.setDisjunctionLabel();
        entryFieldCard.removeReqButton();
        for (int i = prevCard.getRequirementNumber() - 1; i < indexPositions.size(); i++) {
            indexPositions.set(i, indexPositions.get(i) + 1);
        }
        updateDeleteButton();
        cards.add(indexOfPrevCard + 1, entryFieldCard);
        updateScreen();
    }

    /**
     * Adds a new {@code EntryFieldCard} after the last card corresponding to the given requirement number.
     * This new card has a logical `AND` relationship with the given previous card.
     */
    public void addAndEntryFieldCard(int reqNum) {
        EntryFieldCard entryFieldCard = new EntryFieldCard(this, false, reqNum);
        entryFieldCard.setConjunctionLabel();
        if (reqNum != numOfRequirements) {
            entryFieldCard.removeReqButton();
        }
        updateDeleteButton();
        cards.add(indexPositions.get(reqNum - 1) + 1, entryFieldCard);
        for (int i = reqNum - 1; i < indexPositions.size(); i++) {
            indexPositions.set(i, indexPositions.get(i) + 1);
        }
        updateScreen();
    }

    /**
     * Adds a new {@code EntryFieldCard} after the last card corresponding to the given requirement number.
     * This new card has a logical `OR` relationship with the given previous card.
     */
    public void addOrEntryFieldCard(int reqNum) {
        EntryFieldCard entryFieldCard = new EntryFieldCard(this, false, reqNum);
        if (reqNum != numOfRequirements) {
            entryFieldCard.removeReqButton();
        }
        updateDeleteButton();
        cards.add(indexPositions.get(reqNum - 1) + 1, entryFieldCard);
        for (int i = reqNum - 1; i < indexPositions.size(); i++) {
            indexPositions.set(i, indexPositions.get(i) + 1);
        }
        updateScreen();
    }

    /**
     * Adds a new {@code EntryFieldCard} corresponding to a new requirement number.
     * This card marks the start of a new numbered requirement.
     */
    public void addNewRequirement() {
        numOfRequirements++;
        EntryFieldCard lastCard = (EntryFieldCard)cards.get(cards.size() - 1);
        lastCard.removeReqButton();
        EntryFieldCard entryFieldCard = new EntryFieldCard(this, true, numOfRequirements);
        updateDeleteButton();
        cards.add(entryFieldCard);
        if (entryFieldCard.getRequirementNumber() != 1) {
            indexPositions.add(entryFieldCard.getRequirementNumber() - 1,
                    indexPositions.get(entryFieldCard.getRequirementNumber() - 2) + 1);
        } else {
            indexPositions.add(entryFieldCard.getRequirementNumber() - 1, 1);
        }
        updateScreen();
    }

    /**
     * Deletes the given {@code EntryFieldCard}.
     * Updates the requirement numbers (if necessary) of all subsequent cards and the two cards directly before and
     * after the card to be deleted.
     * @param toDelete The card to be deleted.
     */
    public void deleteRequirement(EntryFieldCard toDelete) {
        int reqNum = toDelete.getRequirementNumber();
        for (int i = reqNum - 1; i < indexPositions.size(); i++) {
            indexPositions.set(i, indexPositions.get(i) - 1);
        }
        if (isOnlyCardOfReqNum(toDelete)) {
            updateReqNums(toDelete, reqNum);
        }
        int cardIndex = cards.indexOf(toDelete);

        EntryFieldCard prevCard = getPrevCard(cardIndex);
        EntryFieldCard nextCard = getNextCard(cardIndex);
        cards.remove(toDelete);
        updateNeighbouringCards(toDelete, cardIndex, prevCard, nextCard);

        updateScreen();
    }

    /**
     * Updates the card before and after the card to be deleted.
     * Only update the cards if they correspond to the same requirement number.
     */
    private void updateNeighbouringCards(EntryFieldCard toDelete, int cardIndex, EntryFieldCard prevCard, EntryFieldCard nextCard) {
        if (prevCard != null && prevCard.getRequirementNumber() == toDelete.getRequirementNumber()) {
            updatePrevCard(toDelete, prevCard);
        }
        if (nextCard != null && nextCard.getRequirementNumber() == toDelete.getRequirementNumber()) {
            updateNextCard(toDelete, nextCard, cardIndex);
        }
    }

    /**
     * Returns the card in the index right after the given index.
     * If that card does not exist, return null.
     */
    private EntryFieldCard getNextCard(int cardIndex) {
        if (cardIndex < cards.size() - 1) {
            return (EntryFieldCard) cards.get(cardIndex + 1);

        }
        return null;
    }

    /**
     * Returns the card in the index right before the given index.
     * If that card does not exist, return null.
     */
    private EntryFieldCard getPrevCard(int cardIndex) {
        if (cardIndex >= REQ_START_INDEX + 1) {
            return (EntryFieldCard) cards.get(cardIndex - 1);

        }
        return null;
    }

    /**
     * Checks if the given {@code EntryFieldCard} is the only card corresponding to its requirement number.
     */
    private boolean isOnlyCardOfReqNum(EntryFieldCard card) {
        boolean isStartOfReqNum = card.isNewRequirement();
        boolean isEndOfReqNum = cards.size() == cards.indexOf(card) + 1
                || ((EntryFieldCard) cards.get(cards.indexOf(card) + 1)).isNewRequirement();
        return isStartOfReqNum && isEndOfReqNum;
    }

    /**
     * Updates the total number of requirements and requirement numbers of the cards after the one to be deleted.
     * @param toDelete The card to be deleted.
     * @param reqNum The requirement number of the card to be deleted.
     */
    private void updateReqNums(EntryFieldCard toDelete, int reqNum) {
        numOfRequirements--;
        int cardIndex = cards.indexOf(toDelete);

        // decrease the requirement numbers of all the following cards
        for (int i = cardIndex + 1; i < cards.size(); i++) {
            EntryFieldCard currCard = (EntryFieldCard) cards.get(i);
            currCard.setRequirementNumber(currCard.getRequirementNumber() - 1);
        }

        // update the ending indexes of the following requirement numbers
        for (int i = reqNum - 1; i < indexPositions.size() - 1; i++) {
            indexPositions.set(i, indexPositions.get(i + 1));
        }
        indexPositions.remove(indexPositions.size() - 1);
    }

    /**
     * Updates the previous card based on the properties of the card to be deleted.
     */
    private void updatePrevCard(EntryFieldCard toDelete, EntryFieldCard prevCard) {
        if (toDelete.containsAndButton() && !prevCard.containsAndButton()) {
            prevCard.addAndButton();
        }
        if (toDelete.containsOrLabel() && !prevCard.containsOrLabel()) {
            prevCard.setDisjunctionLabel();
        }
        if (toDelete.containsOrButton() && !prevCard.containsOrButton()) {
            prevCard.addOrButton();
        }

        if (toDelete.containsReqButton() && !prevCard.containsReqButton()) {
            prevCard.addReqButton();
        }
        if (isOnlyReq(prevCard) && !prevCard.hasReqCards()) {
            prevCard.removeDeleteButton();
        }
    }

    /**
     * Updates the next card depending on the properties of the card to be deleted and possibly the card before that.
     */
    private void updateNextCard(EntryFieldCard toDelete, EntryFieldCard nextCard, int cardIndex) {
        if (toDelete.isNewRequirement()) {
            nextCard.setAsStart();
            nextCard.removeConjunctionLabel();
        } else {
            EntryFieldCard prevCard = (EntryFieldCard) cards.get(cardIndex - 1);
            if (prevCard.containsOrLabel() && nextCard.getRequirementNumber() == toDelete.getRequirementNumber()) {
                nextCard.removeConjunctionLabel();
            }
        }
        if (isOnlyReq(nextCard) && !nextCard.hasReqCards()) {
            nextCard.removeDeleteButton();
        }
    }

    /**
     * Removes the delete button for an {@code EntryFieldCard} if it is the only card left.
     */
    public void updateDeleteButton() {
        if (cards.size() == REQ_START_INDEX + 1) {
            EntryFieldCard onlyCard = (EntryFieldCard) cards.get(REQ_START_INDEX);
            if (!onlyCard.containsDeleteButton()) {
                onlyCard.addDeleteButton();
            }
        }
    }

    /**
     * Checks if the given card is the only card left.
     */
    public boolean isOnlyReq(EntryFieldCard card) {
        return cards.indexOf(card) == REQ_START_INDEX && cards.size() == REQ_START_INDEX + 1;
    }

    public ListView<HBox> getListContent() {
        return entryListView;
    }

    /**
     * Deletes this {@EntryWindow}.
     */
    @FXML
    public void deleteCohort() {
        mainWindow.deleteCohort(this);
    }

    public void removeDeleteCohortButton() {
        deleteCohortButtonPlaceholder.getChildren().remove(deleteCohortButton);
    }

    public void addDeleteCohortButton() {
        deleteCohortButtonPlaceholder.getChildren().add(deleteCohortButton);
    }
}
