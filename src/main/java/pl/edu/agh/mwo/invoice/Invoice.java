package pl.edu.agh.mwo.invoice;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

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
		this.addProduct(product, 1);	
	}

	public void addProduct(Product product, Integer quantity) {
		if (quantity <= 0) {
			throw new IllegalArgumentException("Incorrect quantity");
		} else {
			Boolean duplicate = false;
			Integer oldQuantity = 0;
			for (Product p : products.keySet()) {
				if (p.getName().equals(product.getName())) {
					oldQuantity = products.get(p);
					products.remove(p);
					duplicate = true;
					products.put(product, oldQuantity + quantity);
				}
			}
			if (!duplicate) {
				this.products.put(product, quantity);
			}	
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
	public String toString() {
		String result = this.invoiceNumber;
		DecimalFormat df = new DecimalFormat("#,##0.00");
		Integer productsCount = 0;
		
		for (Entry<Product, Integer> entry : products.entrySet()) {
			String name = entry.getKey().getName();
			Integer quantity = entry.getValue();
			BigDecimal price = entry.getKey().getPrice();
			result += "\r\nNazwa: " + name + ", Liczba sztuk: " + quantity + ", Cena: " + df.format(price);
			productsCount += 1;
		}
		result += "\r\nLiczba pozycji: " + productsCount;
		return result;
	}
}
