package stringen.ui;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import stringen.logic.Module;
import stringen.logic.Parser;

public class EntryWindow extends GridPane {

    @FXML
    private Label header;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private AnchorPane editingPane;

    @FXML
    private VBox entryDialogs;

    @FXML
    private GridPane informationContainer;

    private Module module;
    private Generator generator;
    private CommandBox commandBox;
    private Parser parser;


    public EntryWindow(Module module, Generator generator, CommandBox commandBox, Parser parser) {
        this.module = module;
        this.generator = generator;
        this.commandBox = commandBox;
        this.parser = parser;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(CommandBox.class.getResource("/view/testEntryWindow.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        header.setText("Module Code : " + module.getModuleCode());
        EntryDialog entryDialog = new EntryDialog();
        entryDialogs.getChildren().add(entryDialog);
        scrollPane.setVvalue(scrollPane.getVvalue());
        scrollPane.prefHeightProperty().bind(informationContainer.prefHeightProperty());
        scrollPane.prefWidthProperty().bind(informationContainer.prefWidthProperty());
    }

    @FXML
    private void generateString() throws UnsupportedEncodingException {
        Iterator<Node> childrenIterator = entryDialogs.getChildren().iterator();
        while (childrenIterator.hasNext()) {
            Node child = childrenIterator.next();
            EntryDialog childWindow = (EntryDialog) child;
            module = childWindow.updateModule(module, parser);
        }
        System.out.println("module " + module);
        commandBox.changeScreen(new ResultWindow(generator, module));
    }

}
