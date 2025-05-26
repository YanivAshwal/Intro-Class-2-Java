package processor;

class ItemInfo {
	private double price; 
	private int qty;
	private double total;

	public ItemInfo(double price) {
		this.price = price;
		this.qty = 0;
		this.total = 0.0;
	}
	
	public synchronized void addItem() {
		total += price;
		qty++;
	}
	
	public double getPrice() {
		return price; 
	}
	
	public int getQty() {
		return qty;
	}
	
	public double getTotal() {
		return total;
	}
}
