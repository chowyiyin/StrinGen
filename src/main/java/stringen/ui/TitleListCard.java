package stringen.ui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class TitleListCard extends HBox {

    @FXML
    private Label titleLabel;

    private String title;

    public TitleListCard(String title) {
        this.title = title;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(TitleListCard.class.getResource("/view/TitleListCard.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
            titleLabel.setText(title);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
