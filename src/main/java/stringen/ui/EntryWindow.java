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
        List<HBox> cards = new ArrayList<HBox>();
        cards.add(new TitleListCard("Cohort"));
        cards.add(new CohortListCard());
        cards.add(new TitleListCard("Requirements"));
        cards.add(new EntryFieldCard());
        ObservableList<HBox> observableCards = FXCollections.observableArrayList(cards);
        entryListView.setItems(observableCards);
        entryListView.setCellFactory(lst ->
                new ListCell<HBox>() {
                    @Override
                    protected void updateItem(HBox item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setPrefHeight(45.0);
                            setGraphic(null);
                        } else {
                            setPrefHeight(Region.USE_COMPUTED_SIZE);
                            setGraphic(item);
                        }
                    }
                });
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
