package vidmot;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.scene.media.AudioClip;

import java.awt.*;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Main extends Application {
    private static final int UPP=90;
    private static final int NIDUR=270;
    private static final int VINSTRI=180;
    private static final int HAEGRI=360;


    private final HashMap<KeyCode, Integer> map= new HashMap<KeyCode,Integer>();

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Snake.fxml"));
        FXMLLoader.load(getClass().getResource("difficultyDialog.fxml"));
        Parent root = loader.load();
        SnakurController sc = loader.getController();
        primaryStage.setTitle("Snake");
        Scene s = new Scene(root, 600, 400);
        orvatakkar(sc, s);
        primaryStage.setScene(s);
        primaryStage.show();

    }

    private void orvatakkar(SnakurController sc, Scene s) {
        map.put(KeyCode.UP, UPP);
        map.put(KeyCode.DOWN, NIDUR);
        map.put(KeyCode.RIGHT, HAEGRI);
        map.put(KeyCode.LEFT, VINSTRI);
        s.addEventFilter(KeyEvent.KEY_PRESSED,
                event -> {
                    if(event.getCode().getCode()!=37 && event.getCode().getCode()!=38 &&
                            event.getCode().getCode()!=39 && event.getCode().getCode()!=40 && event.getCode().getCode()!=32){
                        return;
                    }
                    else if(event.getCode().getCode()==37 || event.getCode().getCode()==38 ||
                            event.getCode().getCode()==39 || event.getCode().getCode()==40) {
                        sc.setStefna(map.get(event.getCode()));
                    }
                    else{
                        sc.bida();
                    }
                });
    }


    public static void main(String[] args) {
        launch(args);
    }
}
