package pl.edu.agh.mwo.invoice;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import pl.edu.agh.mwo.invoice.product.Product;

public class Invoice {
	private Map<Product,Integer> products = new LinkedHashMap<>();
	private String invoiceNumber;
	
	Invoice(int id){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM");  
		Date date = new Date(); 
		invoiceNumber = "INV/" + formatter.format(date) + "/" +id;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void addProduct(Product product) {
		this.products.put(product, 1);
	}

	public void addProduct(Product product, Integer quantity) {
		if (quantity <= 0) {
			throw new IllegalArgumentException("Incorrect quantity");
		} else {
			this.products.put(product, quantity);
		}
	}

	public BigDecimal getSubtotal() {
		BigDecimal subtotal = BigDecimal.ZERO;
		for (Product product : products.keySet()) {
			BigDecimal quantity = new BigDecimal(products.get(product));
			subtotal = subtotal.add(product.getPrice().multiply(quantity));
		}
		return subtotal;
	}

	public BigDecimal getTax() {
		BigDecimal tax = BigDecimal.ZERO;
		for (Product product : products.keySet()) {
			BigDecimal quantity = new BigDecimal(products.get(product));
			tax = tax.add(product.getPrice().multiply(product.getTaxPercent()).multiply(quantity));
		}
		return tax;
	}

	public BigDecimal getTotal() {
		BigDecimal total = BigDecimal.ZERO;
		for (Product product : products.keySet()) {
			BigDecimal quantity = new BigDecimal(products.get(product));
			total = total.add(product.getPriceWithTax().multiply(quantity));
		}
		return total;
	}
}
