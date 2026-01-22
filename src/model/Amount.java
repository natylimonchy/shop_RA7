package model;
public class Amount {
    private double value;
    private String CURRENCY = "euro";

    public Amount(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Amount{" + "value=" + value + ", CURRENCY=" + CURRENCY + '}';
    }
}
