package model;

import main.Payable;


public class Client extends Person implements Payable{
    
        private int memberId;
        private Amount balance;
        public static final int MEMBER_ID = 456;
        public static Amount BALANCE;

public Client(String name) {
    super(name);
    this.memberId = MEMBER_ID;
    this.balance = new Amount(50.00); 
}

       
    public Amount getBalance() {
        return balance;
    }

    public void setBalance(Amount balance) {
        this.balance = balance;
    }
        

    
    public int getMemberId() {
        return memberId;
    }

    
    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    

    @Override
    public boolean pay(Amount amount) {
        if(balance.getValue()>=amount.getValue()){
            balance.setValue(balance.getValue()-amount.getValue());
            return true;
        } else{
            balance.setValue(balance.getValue()-amount.getValue());
            return false;
        }
        
    }
    
    
}
