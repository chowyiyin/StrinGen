package stringen.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class EntryWindow extends VBox {

    private int numOfRequirements = 1;

    private final int REQ_START_INDEX = 4;

    @FXML
    private ListView<HBox> entryListView;

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
        cards.add(new TitleListCard("Cohort", false));
        cards.add(new CohortListCard());
        cards.add(new TitleListCard("Requirements", true));
        cards.add(new EntryFieldCard(this, true, numOfRequirements));
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
        cards.add(indexOfPrevCard + 1, entryFieldCard);
        updateScreen(FXCollections.observableArrayList(cards));
    }

    public void addAndEntryFieldCard(int index) {
        System.out.println(index);
        EntryFieldCard entryFieldCard = new EntryFieldCard(this, false, index);
        entryFieldCard.setConjunctionLabel();
        System.out.println(indexPositions.get(index - 1));
        cards.add(indexPositions.get(index - 1), entryFieldCard);
        for (int i = index - 1; i < indexPositions.size(); i++) {
            indexPositions.set(i, indexPositions.get(i) + 1);
        }
        updateScreen(FXCollections.observableArrayList(cards));
    }

    public void addOrEntryFieldCard(int index) {
        EntryFieldCard entryFieldCard = new EntryFieldCard(this, false, index);
        cards.add(indexPositions.get(index - 1), entryFieldCard);
        for (int i = index - 1; i < indexPositions.size(); i++) {
            indexPositions.set(i, indexPositions.get(i) + 1);
        }
        updateScreen(FXCollections.observableArrayList(cards));
    }

    public void addNewRequirement() {
        numOfRequirements++;
        EntryFieldCard entryFieldCard = new EntryFieldCard(this, true, numOfRequirements);
        cards.add(entryFieldCard);
        if (entryFieldCard.getRequirementNumber() != 1) {
            indexPositions.add(entryFieldCard.getRequirementNumber() - 1,
                    indexPositions.get(entryFieldCard.getRequirementNumber() - 2) + 1);
        } else {
            indexPositions.add(entryFieldCard.getRequirementNumber() - 1, 1);
        }
        updateScreen(FXCollections.observableArrayList(cards));
    }

    public ListView<HBox> getListContent() {
        return entryListView;
    }
}
