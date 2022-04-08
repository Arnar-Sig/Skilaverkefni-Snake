package vidmot;

import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

import java.net.MalformedURLException;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;


public class SnakurPane extends Pane {

    public Snakur snakur;
    public ListView<Faeda> matur;
    public ListView<Snakur> eiturSnakar;
    private final Random rand = new Random();
    public SnakurController sc;
    private MediaView mediaview;

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }
    private int difficulty = 1;
    private int teljari = 0;

    /**
     * Bendir á snákurcontrollerinn sem er í notkun
     *
     * @param sc SnákurController
     */
    public void setSc(SnakurController sc) {
        this.sc = sc;
    }

    public SnakurPane() throws MalformedURLException {
        matur = new ListView<Faeda>();
        Faeda matur1 = new Faeda(rand.nextInt(400),rand.nextInt(225),5);
        matur.getItems().add(matur1);
        eiturSnakar = new ListView<>();
        snakur = nyrSnakur(60,60,360,1,15,15, "Red");
        getChildren().add(matur.getItems().get(0)); //
        getChildren().add(snakur);
        playMedia("raising-me-higher.mp3");
    }
    //fall til að spila hljóð í MediaPlayer
    private void playMedia(String soundFile) throws MalformedURLException {
        Media media = new Media(String.valueOf(getClass().getResource(soundFile)));
        MediaPlayer mplayer = new MediaPlayer(media);
        mediaview = new MediaView(mplayer);
        mplayer.setAutoPlay(true);
    }
    //fall til að spila AudioClips
    private void playAudioClip(String audioFile) throws MalformedURLException {
        String bip = audioFile;
        Media hit = new Media(Paths.get(bip).toUri().toString());
        AudioClip mediaPlayer = new javafx.scene.media.AudioClip(hit.getSource());
        mediaPlayer.play();
    }

    /**
     * Snákur s færður áfram. Bætir einnig við halanum ef lengdin á snák er of stutt.
     * @param s Snákur
     * @param l aðgreinir snák leikmanns og eitursnák, ef leikmaður þá er l=0, annars l=1
     */
    public void afram(Snakur s, int l) {
        s.afram(1);
        ConcurrentLinkedQueue<Double> x = s.getxHali();
        ConcurrentLinkedQueue<Double> y = s.getyHali();
        while(s.getHaliStaerd()>s.snakurHali.size()){
            if(l==0) {
                s.snakurHali.add(new Rectangle(15, 15, Paint.valueOf("Red")));
            }else{
                s.snakurHali.add(new Rectangle(15, 15, Paint.valueOf("Purple")));
            }
            s.snakurHali.get(s.snakurHali.size()-1).setX(snakur.getX());
            s.snakurHali.get(s.snakurHali.size()-1).setY(snakur.getY());
        }
        for (int i = 0; i < s.getHaliStaerd(); i++) {
            getChildren().remove(s.snakurHali.get(i));
            x.add(s.snakurHali.get(i).getX());
            y.add(s.snakurHali.get(i).getY());
            s.snakurHali.get(i).setX(x.remove());
            s.snakurHali.get(i).setY(y.remove());
            getChildren().add(s.snakurHali.get(i));
        }
        s.setxHali(x);
        s.setyHali(y);
    }


    /**
     * Færir eitursnákana áfram og athugar hvort þeir rekast á snákinn. Ef svo er er leikurinn stöðvaður.
     * Athugar einnig hvort eitursnákar rekast hvor á annan og snýr þeim þá við.
     * Bætir einnig við eitursnákunum í fylkið ef fylkið er tómt - gerist einu sinni í byrjun leiks
     */
    public void aframEitursnakar(){
        setjaEitursnakaABord();
        for(int i=0; i<eiturSnakar.getItems().size(); i++){
            afram(eiturSnakar.getItems().get(i), 1);
        }
        athugaArekstra();
        teljari++;

    }
    //Bætir við eitursnákum á borðið einu sinni
    private void setjaEitursnakaABord(){
        if(eiturSnakar.getItems().isEmpty()){
            fleiriEitursnakar(2*difficulty);
            for(int i = 0; i<eiturSnakar.getItems().size();i++){
                getChildren().add(eiturSnakar.getItems().get(i));
            }
        }
    }
    //athugar árekstra tengda eitursnákum
    private void athugaArekstra(){
        //Athugar hvort haus snáks rekst á eitursnák
        for(int i=0; i<eiturSnakar.getItems().size();i++) {
            if (eiturSnakar.getItems().get(i).intersects(snakur.getBoundsInParent())) {
                arekstur();
                return;
            }
            //Þegar snákur rekst á eitursnáks-hala
            int haliStaerd = eiturSnakar.getItems().get(i).getHaliStaerd();
            for(int g = 1; g<haliStaerd ;g++){
                if (Math.abs(eiturSnakar.getItems().get(i).snakurHali.get(g).getX() - snakur.getX()) < 15 &&
                        Math.abs(eiturSnakar.getItems().get(i).snakurHali.get(g).getY()
                                - snakur.getY()) < 15 && teljari>15){
                    arekstur();
                    return;
                }
            }
            //ef eitursnákar rekast á hvorn annan
            for(int k=0; k<eiturSnakar.getItems().size();k++){
                if(k==i)continue;
                if (eiturSnakar.getItems().get(i).intersects(eiturSnakar.getItems().get(k).getBoundsInParent())) {
                    eiturSnakar.getItems().get(i).setStefna(ofugStefna(eiturSnakar.getItems().get(i).getStefna()));
                }
            }
        }
        //Hali snáks rekst á eitursnáka
        for(int i=0; i<snakur.snakurHali.size();i++) {
            for (Snakur k: eiturSnakar.getItems()) {
                if (k.intersects(snakur.snakurHali.get(i).getBoundsInParent())) {
                    arekstur();
                    return;
                }
            }
        }
    }

    //Fall sem er kallað á þegar árekstur verður. Spilar hljóð og leik er svo lokið.
    private void arekstur() {
        System.out.println("AREKSTUR!");
        playExplosion();
        sc.leikurLokid();
    }

    /**
     * spilar hljóð sem hljómar eins og sprenging.
     */
    public void playExplosion(){
        try {
            playMedia("explosion.mp3");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Borðar fæðu, bætir við einu stigi, stækkar snákinn og bætir við annarri fæðu á leikborð.
     */
    public void borda(){
        Faeda m0 = matur.getItems().get(0);
        if(snakur.intersects(m0.getBoundsInParent())){
            getChildren().remove(matur.getItems().get(0));
            matur.getItems().remove(0);
            eldaMat();
            try {
                playAudioClip("src/vidmot/eat.mp3");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            getChildren().add(matur.getItems().get(0));
            snakur.vaxa();
            sc.leikur.vinningur();
            sc.setjaFxStig();
        }
    }

    /**
     * Nýr snákur hlutur
     *
     * @param x      x-hnit snáks
     * @param y      y-hnit snáks
     * @param stef   stefna snáks
     * @param h      hraði snáks
     * @param width  breidd snáks
     * @param height hæð snáks
     * @param litur  litur snáks
     * @return snákur hlutur
     */
    public Snakur nyrSnakur(float x, float y, int stef, int h,int width, int height, String litur){
        return new Snakur(x, y, stef, h, width, height, litur);
    }

    /**
     * Býr til nýja fæðu á random stað á leikborði.
     */
    public void eldaMat(){
        matur.getItems().add(new Faeda(5 + rand.nextInt(418),5 + rand.nextInt(330),5));
    }

    /**
     * Bætir við eitursnákum randomly á leikborðið. Endurtekur þangað til að enginn snákur er of nálægt hvor öðrum.
     *
     * @param i the
     */
    public void fleiriEitursnakar(int i){
        for(int k=0; k<i; k++) {
            eiturSnakar.getItems().add(new Snakur((400 - rand.nextInt(150)), rand.nextInt(325),
                    360, 1, 15, 15, "Purple"));
            Snakur nuverandi = eiturSnakar.getItems().get(k);
            nuverandi.setStefna(reiknaStefnu());
            nuverandi.setHaliStaerd(5*difficulty);
        }
        while(true) {
            boolean obreytt = true;
            for (int k = 0; k < eiturSnakar.getItems().size(); k++) {
                for (int j = 0; j < eiturSnakar.getItems().size(); j++) {
                    if (k == j) continue;
                    if (Math.abs(eiturSnakar.getItems().get(k).getX() - eiturSnakar.getItems().get(j).getX()) < 20 ||
                            Math.abs(eiturSnakar.getItems().get(k).getY() - eiturSnakar.getItems().get(j).getY()) < 20){
                        eiturSnakar.getItems().get(k).setX(400-rand.nextInt(150));
                        eiturSnakar.getItems().get(k).setY(rand.nextInt(325));
                        obreytt= false;
                    }
                }
            }
            if(obreytt){
                for(int l = 0; l<eiturSnakar.getItems().size(); l++){
                    ConcurrentLinkedQueue<Double> tempX = new ConcurrentLinkedQueue<>();
                    ConcurrentLinkedQueue<Double> tempY = new ConcurrentLinkedQueue<>();
                    double currentX = eiturSnakar.getItems().get(l).getX();
                    double currentY = eiturSnakar.getItems().get(l).getY();
                    for(int o = 0; o<eiturSnakar.getItems().get(l).getHaliStaerd();o++){
                        tempX.add(currentX);
                        tempY.add(currentY);
                    }
                    eiturSnakar.getItems().get(l).setxHali(tempX);
                    eiturSnakar.getItems().get(l).setyHali(tempY);
                }
                break;
            }
        }
    }

    /**
     * Reiknar random stefnu. á snák
     *
     * @return stefnunni int
     */
    public int reiknaStefnu(){
        double m = (int) (Math.random() * ((3 - 1 + 1) + 1));
        if(m==1) return 180;
        if(m==2) return 270;
        if(m==3) return 360;
        return 90;
    }

    /**
     * Snýr við stefnu á snák.
     *
     * @param stefna stefnan sem snákurinn hefur í byrjun.
     * @return stefnan eftir að búið er að snúa við.
     */
    public int ofugStefna(int stefna){
        if(stefna==90) return 270;
        if(stefna==270) return 90;
        if(stefna==360) return 180;
        return 360;
    }
}
