package vidmot;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.GridPane;
import java.net.URL;
import java.util.ResourceBundle;

public class alertController implements Initializable {

    @FXML
    public GridPane fxAlert;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DialogPane p = new DialogPane();
        fxAlert.setVisible(true);
        p.setContent(fxAlert);
        Dialog d = new Dialog<>();
        d.setDialogPane(p);
    }
}
