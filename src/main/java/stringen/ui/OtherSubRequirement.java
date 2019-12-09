package stringen.ui;

import java.io.IOException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class OtherSubRequirement extends HBox {

    @FXML
    private ComboBox<String> requirement;

    @FXML
    private ComboBox<String> requirementStandard;

    @FXML
    private ComboBox<String> optionOtherSubRequirement;


    public OtherSubRequirement() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(OtherSubRequirement.class.
                    getResource("/view/OtherSubRequirement.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        requirement.getItems().addAll("Minimum CAP", "Honours");
        requirement.getSelectionModel().select("REQUIREMENT");
    }

    @FXML
    public void setStandard() {
        if (requirement.getValue().equals("Minimum CAP")) {
            for (double i = 2.5; i < 5; i = i + 0.1) {
                requirementStandard.getItems().add(String.format("%.1f", i));
            }
        } else if (requirement.getValue().equals("Honours")) {
            requirementStandard.getItems().addAll("YES", "NO");
        }
    }

    @FXML
    public void changeSelected() {
        System.out.println(requirementStandard.getOnKeyTyped().toString());
        requirementStandard.accessibleTextProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                for (String item : requirementStandard.getItems()) {
                    if (item.contains(requirementStandard.getOnKeyTyped().toString())) {
                        requirementStandard.getSelectionModel().select(item);
                        requirementStandard.show();
                    }
                }
            }
        });
    }

    public void enableOptionBox() {
        optionOtherSubRequirement.disableProperty().setValue(false);
        optionOtherSubRequirement.getItems().addAll("OR", "AND");
    }

}


