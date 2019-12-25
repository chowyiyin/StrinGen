package stringen.ui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import stringen.Util;
import stringen.logic.Cohort;

public class CohortListCard extends HBox {

    @FXML
    private ComboBox<String> cohortStart;

    @FXML
    private ComboBox<String> cohortEnd;

    public CohortListCard() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(CohortListCard.class.getResource("/view/CohortListCard.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        cohortStart.getItems().addAll(Util.YEARS);
        cohortStart.getSelectionModel().select("YEAR");
        cohortEnd.getItems().addAll(Util.YEARS);
        cohortEnd.getSelectionModel().select("YEAR");
    }

    public String getStartYear() {
        if (cohortStart.getValue() == "YEAR" || cohortStart.getValue().equals(Cohort.DEFAULT_DASH)) {
            return "";
        } else {
            return cohortStart.getValue();
        }
    }

    public String getEndYear() {
        if (cohortEnd.getValue() == "YEAR" || cohortEnd.getValue().equals(Cohort.DEFAULT_DASH)) {
            return "";
        } else {
            return cohortEnd.getValue();
        }
    }
}
