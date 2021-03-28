package pl.edu.agh.mwo.invoice.product;

import java.math.BigDecimal;

public abstract class Product {
	protected final String name;

    protected final BigDecimal price;

    protected final BigDecimal taxPercent;

    protected Product(String name, BigDecimal price, BigDecimal tax) {
        if (name == null || name.equals("")) {
        	throw new IllegalArgumentException("Incorrect product name");
        }
        if (price == null || price.compareTo(BigDecimal.ZERO)<0) {
        	throw new IllegalArgumentException("Incorrect product price");
        }
    	this.name = name;
        this.price = price;
        this.taxPercent = tax;
    }

    public String getName() {
        return this.name;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public BigDecimal getTaxPercent() {
        return this.taxPercent;
    }

    public BigDecimal getPriceWithTax() {
        return this.price.add(this.price.multiply(this.taxPercent));
    }
}
