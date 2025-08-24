package POS;

public class IdStrategy {
    private boolean isPWD;
    private boolean isSenior;

    public IdStrategy (boolean isPWd, boolean isSenior) {
        this.isPWD = isPWd;
        this.isSenior = isSenior;
    }

    public boolean getIsPWD() {
        return isPWD;
    }

    public boolean getIsSenior() {
        return isSenior;
    }
}
