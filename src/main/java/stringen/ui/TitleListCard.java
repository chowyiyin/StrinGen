package stringen.ui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * Represents a UI component that shows major headers in the UI.
 */
public class TitleListCard extends HBox {

    @FXML
    private Label titleLabel;

    private String title;
    private boolean isRequirementLine;

    public TitleListCard(String title, boolean isRequirementLine) {
        this.title = title;
        this.isRequirementLine = isRequirementLine;
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

    /**
     * Checks if the current card is the header for requirements.
     */
    public boolean isRequirementLine() {
        return isRequirementLine;
    }

}
