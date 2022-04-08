package vidmot;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;
import vinnsla.Leikur;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


/**
 * The type Snakur controller.
 */
public class SnakurController implements Initializable {

    @FXML
    public Label fxStig;
    @FXML
    private Label fxKeyInput;
    @FXML
    public Label fxTime;
    private int timi = 120;
    @FXML
    private SnakurPane snakurBord;
    private boolean isPaused = false;
    private boolean leikurBuinn = false;
    public Leikur leikur;
    private Timeline eiturTimeline;
    private Timeline adalTimeline;
    private Timeline klukkaTimeline;
    @FXML
    private difficultyDialogController setjaDifficulty;
    public int diff;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            setjaDifficulty = hladaDialog();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            diff = setjaDifficulty.erfidleikastigDialog();
            snakurBord.setSc(this);
            snakurBord.setDifficulty(diff);
            leikur = new Leikur();
            stillaTimeline();
            snakurBord.eldaMat();
            snuaEitursnak();
            klukka();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Setur stefnu á snák.
     *
     * @param s stefnan
     */
    public void setStefna(int s){
        if(!isPaused) {
            fxKeyInput.setText(s + "");
            snakurBord.snakur.setStefna(s);
        }
    }

    //aðaltímalínan fyrir leik.
    private void stillaTimeline () {
        KeyFrame k = new KeyFrame(Duration.millis(10),
                e-> {
                    snakurBord.afram(snakurBord.snakur,0);
                    snakurBord.borda();
                    snakurBord.aframEitursnakar();
                });
        adalTimeline = new Timeline(k);
        adalTimeline.setCycleCount(Timeline.INDEFINITE);
        adalTimeline.play();
    }

    //tímalína til að snúa eitursnákum á random hátt
    private void snuaEitursnak () {
        KeyFrame l = new KeyFrame(Duration.millis(5000),
                e-> {
                    for(int i = 0; i<snakurBord.eiturSnakar.getItems().size(); i++){
                        snakurBord.eiturSnakar.getItems().get(i).setStefna(snakurBord.reiknaStefnu());
                    }
                });
        eiturTimeline = new Timeline(l);
        eiturTimeline.setCycleCount(Timeline.INDEFINITE);
        eiturTimeline.play();
    }

    //Tímalína sem telur niður tímann þar til leikur er búinn.
    private void klukka() {
        klukkaTimeline = new Timeline(
                new KeyFrame(Duration.seconds(0),
                        e ->aframTimi()),
                new KeyFrame(Duration.seconds(1)));

        klukkaTimeline.setCycleCount(Timeline.INDEFINITE);
        klukkaTimeline.play();
    }
    //Fall sem keyrist í klukku-tímalínu til að lækka tíma eftir
    private void aframTimi(){
        if (timi >0) {
            timi--;
        }
        else{
            leikurLokid();
            snakurBord.playExplosion();
        }
        fxTime.setText(String.valueOf(timi));
    }


    /**
     * Stöðvar leikinn þegar hann er paused eða þegar leikurinn er búinn.
     */
    public void bida(){
        if(!isPaused) {
            adalTimeline.pause();
            eiturTimeline.pause();
            klukkaTimeline.pause();
            isPaused = true;
        }else if(!leikurBuinn){
            adalTimeline.play();
            eiturTimeline.play();
            klukkaTimeline.play();
            isPaused = false;
        }
    }

    /**
     * Stöðvar leikinn þegar honum er lokið.
     */
    public void leikurLokid(){

        adalTimeline.stop();
        eiturTimeline.stop();
        leikur.leikLokid();
        klukkaTimeline.stop();
        leikurBuinn = true;
        isPaused = true;
        leikLokidAlert();
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Stage stage = (Stage) fxStig.getScene().getWindow();
        stage.close();
    }

    /**
     * Birtir notenda núverandi stig.
     */
    public void setjaFxStig(){
        fxStig.setText(String.valueOf(leikur.getStig()));
    }

    /**
     * Controller fyrir Difficulty-dialog
     * @return difficultyDialogController
     * @throws java.io.IOException
     */
    private difficultyDialogController hladaDialog() throws java.io.IOException{
        FXMLLoader dLoader = new FXMLLoader(getClass().getResource("difficultyDialog.fxml"));
        dLoader.load();
        return dLoader.getController();
    }

    //Alert dialog sem sýnir stig þegar leikur er búinn
    private void leikLokidAlert(){
        ButtonType bType = new ButtonType("Í lagi",
                ButtonBar.ButtonData.OK_DONE);
        Alert a = stofnaAlert(bType);
        a.show();
    }
    //Fall til að stofna alert-dialog sem keyrir þegar leikur er búinn og sýnir stig.
    private Alert stofnaAlert(ButtonType bILagi) {
        Alert a = new Alert(Alert.AlertType.NONE,  "Stig: " + fxStig.getText() , bILagi);
        a.setTitle("Snákur");
        a.setHeaderText("Leik lokið!");
        return a;
    }

}
