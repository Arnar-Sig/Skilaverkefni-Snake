package vinnsla;

public class Leikur {

    public int getStig() {
        return stig;
    }
    private int stig;

    /**
     * Býr til nýjan leik.
     */
    public Leikur(){
        stig = 0;
    }

    /**
     * Bætir við stigi.
     */
    public void vinningur(){
        stig++;
    }

    /**
     * Prentar út að leik sé lokið auk núverandi stiga.
     */
    public void leikLokid(){
        System.out.println("Leik lokid! Stig: " + stig);
    }
}
