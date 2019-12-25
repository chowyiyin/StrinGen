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

    @FXML
    private ListView<HBox> entryListView;

    private Generator generator;
    private MainWindow mainWindow;
    private List<HBox> cards = new ArrayList<HBox>();

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
        cards.add(new EntryFieldCard(this, true));
        ObservableList<HBox> observableCards= FXCollections.observableArrayList(cards);
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

    public void addAndEntryFieldCard() {
        EntryFieldCard entryFieldCard = new EntryFieldCard(this, false);
        entryFieldCard.setConjunctionLabel();
        cards.add(entryFieldCard);
        updateScreen(FXCollections.observableArrayList(cards));
    }

    public void addOrEntryFieldCard() {
        EntryFieldCard entryFieldCard = new EntryFieldCard(this, false);
        cards.add(entryFieldCard);
        updateScreen(FXCollections.observableArrayList(cards));
    }

    public void addNewRequirement() {
        EntryFieldCard entryFieldCard = new EntryFieldCard(this, true);
        cards.add(entryFieldCard);
        updateScreen(FXCollections.observableArrayList(cards));
    }

    public ListView<HBox> getListContent() {
        return entryListView;
    }

    /*
    @FXML
    private void generateString() throws UnsupportedEncodingException {
        Iterator<Node> childrenIterator = entryDialogs.getChildren().iterator();
        while (childrenIterator.hasNext()) {
            Node child = childrenIterator.next();
            EntryDialog childWindow = (EntryDialog) child;
        }
        //mainWindow.changeScreen(new ResultWindow(generator, module));
    }
    */
}
