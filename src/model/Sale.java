package model;

import java.util.ArrayList;
import java.util.Objects;


public class Sale {
	Client client;
	ArrayList <Product> products;
	Amount amount;

	public Sale(Client client, ArrayList<Product> products, Amount amount) {
    this.client = client;
    this.products = new ArrayList<>(products);
    this.amount = amount;
}

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
        
        
	

	

	public Amount getAmount() {
		return amount;
	}

	public void setAmount(Amount amount) {
		this.amount = amount;
	}

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return client.getName() + " " + products + " " + amount.getValue();
    }

    
	

}