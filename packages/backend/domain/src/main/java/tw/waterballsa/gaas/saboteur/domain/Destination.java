package tw.waterballsa.gaas.saboteur.domain;

/**
 * @author johnny@waterballsa.tw
 */
public class Destination extends Path {
    private final boolean isGold;

    public Destination(int row, int col, boolean isGold) {
        this(row, col, PathCard.十字路口(), isGold
                /*預設是十字路口*/);
    }

    public Destination(int row, int col, PathCard path, boolean isGold) {
        super(row, col, path, false);
        this.isGold = isGold;
    }

    public boolean isGold() {
        return isGold;
    }

}
