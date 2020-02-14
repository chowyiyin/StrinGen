package stringen.ui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import stringen.Util;
import stringen.logic.Cohort;

/**
 * Represents a UI component that collects the year range of a {@code Cohort}.
 */
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

    /**
     * Extracts information about the starting year of the cohort year range.
     * @return The starting year.
     */
    public String getStartYear() {
        if (cohortStart.getValue() == "YEAR" || cohortStart.getValue().equals(Cohort.DEFAULT_DASH)) {
            return "";
        } else {
            return cohortStart.getValue();
        }
    }

    /**
     * Extracts information about the ending year of the cohort year range.
     * @return The ending year
     */
    public String getEndYear() {
        if (cohortEnd.getValue() == "YEAR" || cohortEnd.getValue().equals(Cohort.DEFAULT_DASH)) {
            return "";
        } else {
            return cohortEnd.getValue();
        }
    }
}
