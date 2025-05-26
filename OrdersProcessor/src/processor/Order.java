package processor;

import java.io.*;
import java.text.NumberFormat;
import java.util.*;

public class Order implements Runnable {
	
	private int clientId; 	
	private String baseFilename;
	private Map<String, Double> priceMap;
	private Map<String, ItemInfo> itemInfo;
	private Object lock;
	private StringBuilder report;
	private Map<String, ItemInfo> items;

	
	public Order(String orderFilename, Map<String, Double> priceMap,
			Map<String, ItemInfo> itemsInfo, Object lock) {
		this.baseFilename = orderFilename;
        this.priceMap = priceMap;
        this.itemInfo = itemsInfo;
        this.lock = lock;
        
        this.report = new StringBuilder();
        items = new HashMap<>();
	}	
	
	@Override
	public void run() {
		BufferedReader br;
		Scanner scanner;
		try {
			br = new BufferedReader(new FileReader(baseFilename));
			scanner = new Scanner(br); 
			scanner.next();
			clientId = scanner.nextInt(); 
			System.out.println("Reading order for client with id: " + clientId);
			while (scanner.hasNext()) {
				
				String item = scanner.next(); 
				if (scanner.hasNext()) { 
					scanner.next();
				}
				
				
				double price = priceMap.get(item); 
				ItemInfo thisItem = items.get(item);
				if (thisItem == null) {
					thisItem = new ItemInfo(price); 
					items.put(item, thisItem);
				}
				thisItem.addItem();
				synchronized (lock) { 
					ItemInfo itemInDB = itemInfo.get(item);
					if (itemInDB == null) { 
						itemInDB = new ItemInfo(price);
						itemInfo.put(item, itemInDB);
					}
					itemInDB.addItem();
				}
				
			}
			scanner.close();
		}
		catch (FileNotFoundException e) {
			System.err.println("File not found");
		}
	}
	
	private StringBuilder processClientReport() {
		
		double sum = 0.0;
		ArrayList<String> sortedItems = new ArrayList<String>(items.keySet());
		sortedItems.sort(Comparator.naturalOrder());		
		
		NumberFormat dollarF = NumberFormat.getCurrencyInstance();
		report.append("----- Order details for client with Id: ").append(clientId).append(" -----\n");
		
		for (String item: sortedItems) {
			
			ItemInfo itemInfo = items.get(item);
			double itemPrice = priceMap.get(item);
			int itemQty = itemInfo.getQty();
			double total = itemPrice * itemQty;
			sum += total;
			
			report.append("Item's name: ").append(item);
			report.append(", Cost per item: ").append(dollarF.format(itemPrice));
			report.append(", Quantity: ").append(itemQty);
			report.append(", Cost: ").append(dollarF.format(total)).append("\n");
		}
		report.append("Order Total: ").append(dollarF.format(sum));
		return report;
	}
	
	public int getClientId() { 
		return clientId;
	}
	
	public StringBuilder getClientReport()  {
		return processClientReport();
	}

}
