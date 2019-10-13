import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class EntryWindow extends AnchorPane {

    @FXML
    private Label header;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private AnchorPane editingPane;

    @FXML
    private VBox entryDialogs;

    private Module module;
    private Generator generator;
    private CommandBox commandBox;


    public EntryWindow(Module module, Generator generator, CommandBox commandBox) {
        this.module = module;
        this.generator = generator;
        this.commandBox = commandBox;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(CommandBox.class.getResource("/view/EntryWindow.fxml"));
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
        entryDialogs.getChildren().add(new EntryDialog());
        scrollPane.vvalueProperty().bind(editingPane.heightProperty());
    }

    @FXML
    private void generateString() throws UnsupportedEncodingException {
        Iterator<Node> childrenIterator = entryDialogs.getChildren().iterator();
        while (childrenIterator.hasNext()) {
            Node child = childrenIterator.next();
            EntryDialog childWindow = (EntryDialog) child;
            module = childWindow.updateModule(module);
        }
        commandBox.changeScreen(new ResultWindow(generator, module));
    }

}
