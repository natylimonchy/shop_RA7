
package main;

import model.Amount;

public interface Payable {
    public abstract boolean pay (Amount amount);
    
}
