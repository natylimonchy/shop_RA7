package model;
public class Amount {
    private double value;
    private String CURRENCY = "euro";

    public Amount(double value) {
        this.value = value;
    }

    public Amount() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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
