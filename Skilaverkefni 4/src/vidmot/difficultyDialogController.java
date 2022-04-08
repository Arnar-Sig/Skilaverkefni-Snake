package vidmot;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class difficultyDialogController implements Initializable {

    @FXML
    public Button fxErfid1;
    @FXML
    public Button fxErfid2;
    @FXML
    public Button fxErfid3;
    @FXML
    private GridPane fxGrid;
    private int difficulty;

    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
    private Dialog buaTilDialog() throws IOException {
        DialogPane p = new DialogPane();
        fxGrid.setVisible(true);
        p.setContent(fxGrid);
        Dialog d = new Dialog<>();
        d.setDialogPane(p);
        return d;
    }

    /**
     * Dialog til að velja erfiðleikastig
     *
     * @return int difficulty sem merkir erfiðleikastig, 1=easy, 2=medium, 3=hard
     * @throws IOException the io exception
     */
    public int erfidleikastigDialog() throws IOException{
        Optional dialogi = buaTilDialog().showAndWait();
        return difficulty;
    }

    /**
     * Handler fyrir easy erfiðleikastig
     * @param actionEvent the action event
     */
    public void Erfid1Handler(ActionEvent actionEvent) {
        difficulty = 1;
        Stage stage = (Stage) fxErfid1.getScene().getWindow();
        stage.close();
    }

    /**
     * Handler fyrir medium erfiðleikastig
     * @param actionEvent the action event
     */
    public void Erfid2Handler(ActionEvent actionEvent) {
        difficulty = 2;
        Stage stage = (Stage) fxErfid2.getScene().getWindow();
        stage.close();
    }

    /**
     * Handler fyrir hard erfiðleikastig
     * @param actionEvent the action event
     */
    public void Erfid3Handler(ActionEvent actionEvent) {
        difficulty = 3;
        Stage stage = (Stage) fxErfid3.getScene().getWindow();
        stage.close();
    }
}

