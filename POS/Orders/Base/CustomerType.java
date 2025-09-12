package POS.Orders.Base;

public class CustomerType {
    private boolean isPWD;
    private boolean isSenior;

    public CustomerType (boolean isPWd, boolean isSenior) {
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
