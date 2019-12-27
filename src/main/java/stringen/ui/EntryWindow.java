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

public class EntryWindow extends VBox {

    private int numOfRequirements = 1;

    private final int REQ_START_INDEX = 3;

    @FXML
    private ListView<HBox> entryListView;

    @FXML
    private HBox deleteCohortButtonPlaceholder;

    @FXML
    private Button deleteCohortButton;

    private Generator generator;
    private MainWindow mainWindow;
    private List<HBox> cards = new ArrayList<HBox>();
    private ArrayList<Integer> indexPositions = new ArrayList<Integer>();

    public EntryWindow(Generator generator, MainWindow mainWindow) {
        this.generator = generator;
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
        if (mainWindow.isOnlyWindow(this)) {
            deleteCohortButtonPlaceholder.getChildren().remove(deleteCohortButton);
        }
        cards.add(new TitleListCard("Cohort", false));
        cards.add(new CohortListCard());
        cards.add(new TitleListCard("Requirements", true));
        EntryFieldCard firstCard = new EntryFieldCard(this, true, numOfRequirements);
        firstCard.removeDeleteButton();
        cards.add(firstCard);
        ObservableList<HBox> observableCards = FXCollections.observableArrayList(cards);
        indexPositions.add(0, REQ_START_INDEX);
        updateScreen(observableCards);
    }

    public void updateScreen(ObservableList<HBox> cards) {
        entryListView.setItems(cards);
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
        updateScreen(FXCollections.observableArrayList(cards));
    }

    public void addAndEntryFieldCard(int index) {
        EntryFieldCard entryFieldCard = new EntryFieldCard(this, false, index);
        entryFieldCard.setConjunctionLabel();
        if (index != numOfRequirements) {
            entryFieldCard.removeReqButton();
        }
        updateDeleteButton();
        cards.add(indexPositions.get(index - 1) + 1, entryFieldCard);
        for (int i = index - 1; i < indexPositions.size(); i++) {
            indexPositions.set(i, indexPositions.get(i) + 1);
        }
        updateScreen(FXCollections.observableArrayList(cards));
    }

    public void addOrEntryFieldCard(int index) {
        EntryFieldCard entryFieldCard = new EntryFieldCard(this, false, index);
        if (index != numOfRequirements) {
            entryFieldCard.removeReqButton();
        }
        updateDeleteButton();
        cards.add(indexPositions.get(index - 1) + 1, entryFieldCard);
        for (int i = index - 1; i < indexPositions.size(); i++) {
            indexPositions.set(i, indexPositions.get(i) + 1);
        }
        updateScreen(FXCollections.observableArrayList(cards));
    }

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
        updateScreen(FXCollections.observableArrayList(cards));
    }

    public void deleteRequirement(EntryFieldCard toDelete) {
        int reqNum = toDelete.getRequirementNumber();
        for (int i = reqNum - 1; i < indexPositions.size(); i++) {
            indexPositions.set(i, indexPositions.get(i) - 1);
        }

        boolean isStartOfReqNum = toDelete.isNewRequirement();
        boolean isEndOfReqNum = cards.size() == cards.indexOf(toDelete) + 1
                || ((EntryFieldCard) cards.get(cards.indexOf(toDelete) + 1)).isNewRequirement();
        boolean isOnlyCardOfReqNum = isStartOfReqNum && isEndOfReqNum;
        if (isOnlyCardOfReqNum) {
            updateReqNums(toDelete, reqNum);
        }

        int cardIndex = cards.indexOf(toDelete);
        EntryFieldCard prevCard = null;
        if (cardIndex >= REQ_START_INDEX + 1) {
            prevCard = (EntryFieldCard) cards.get(cardIndex - 1);

        }
        EntryFieldCard nextCard = null;
        if (cardIndex < cards.size() - 1) {
            nextCard = (EntryFieldCard) cards.get(cardIndex + 1);

        }
        cards.remove(toDelete);

        if (prevCard != null && prevCard.getRequirementNumber() == toDelete.getRequirementNumber()) {
            updatePrevCard(toDelete, prevCard);
        }
        if (nextCard != null && nextCard.getRequirementNumber() == toDelete.getRequirementNumber()) {
            updateNextCard(toDelete, nextCard, cardIndex);
        }
        updateScreen(FXCollections.observableArrayList(cards));
    }

    private void updateReqNums(EntryFieldCard toDelete, int reqNum) {
        numOfRequirements--;
        int cardIndex = cards.indexOf(toDelete);
        for (int i = cardIndex + 1; i < cards.size(); i++) {
            EntryFieldCard currCard = (EntryFieldCard) cards.get(i);
            currCard.setRequirementNumber(currCard.getRequirementNumber() - 1);
        }
        for (int i = reqNum - 1; i < indexPositions.size() - 1; i++) {
            indexPositions.set(i, indexPositions.get(i + 1));
        }
        indexPositions.remove(indexPositions.size() - 1);
    }

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
        if (isOnlyReq(prevCard)) {
            prevCard.removeDeleteButton();
        }
    }

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
        if (isOnlyReq(nextCard)) {
            nextCard.removeDeleteButton();
        }
    }

    public void updateDeleteButton() {
        if (cards.size() == REQ_START_INDEX + 1) {
            EntryFieldCard onlyCard = (EntryFieldCard) cards.get(REQ_START_INDEX);
            if (!onlyCard.containsDeleteButton()) {
                onlyCard.addDeleteButton();
            }
        }
    }

    public boolean isOnlyReq(EntryFieldCard card) {
        return cards.indexOf(card) == REQ_START_INDEX && cards.size() == REQ_START_INDEX + 1;
    }

    public ListView<HBox> getListContent() {
        return entryListView;
    }

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
