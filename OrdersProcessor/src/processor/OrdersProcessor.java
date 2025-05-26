package processor;

import java.io.*;
import java.text.NumberFormat;
import java.util.*;

public class OrdersProcessor {
	
	public static void main(String[] args) throws IOException { 
	//get info to process
		Scanner scanner = new Scanner(System.in);
		
		//filename for priceMap
	 	System.out.println("Enter item's data file name: ");
    	String itemsDataFilename = scanner.nextLine().trim();
    	
    	//threads?
    	System.out.println("Enter 'y' for multiple threads, any other charachter otherwise: ");
    	String character = scanner.nextLine().trim();
    	System.out.println("Enter number of orders to process: ");
    	int orderNums = Integer.parseInt(scanner.nextLine().trim());
    	
    	//Base filenames of orders to process
		System.out.println("Enter order's base filename: ");
    	String baseFilename = scanner.nextLine().trim();
    
    	//where to print the results
    	System.out.println("Enter result's filename: ");
    	String resultsFilename = scanner.nextLine().trim();
    	
    	scanner.close();
    	
    	//global map organized by itemName, stores itemSummary Objects
    	Map<String, ItemInfo> allItemsOrdered = new HashMap<>();
    	
    	//list storing Order items
    	ArrayList<Order> allOrders = new ArrayList<Order>();
    	Object lock = new Object();
    	
    	//reads the itemData file and returns a map with the <item, price> (<S,D>)
    	Map<String, Double> priceMap = readpriceMap(itemsDataFilename);
    	
    	long startTime = System.currentTimeMillis();
    	
    	if (character.equalsIgnoreCase("y")) {
    		processMultiThreads(orderNums, baseFilename, priceMap, allItemsOrdered, lock, allOrders);
    	} else { 
    		processSingleThread(orderNums, baseFilename, priceMap, allItemsOrdered, allOrders);
    	}
    	
    	allOrders.sort(Comparator.comparingInt(Order::getClientId));

    	try (PrintWriter result = new PrintWriter(new FileWriter(resultsFilename))) {
    		for (Order order: allOrders) {
    			result.println(order.getClientReport().toString());
    		}
    		result.println(getFullReport(allItemsOrdered).toString());
    	} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	long endTime = System.currentTimeMillis();
    	
    	System.out.println("Processing time (msec): " + (endTime - startTime));
    	System.out.println("Results can be found in the file: "+ resultsFilename); 
	}
	
	
	private static Map<String, Double> readpriceMap(String filename) throws IOException { 
		Map<String, Double> priceMap = new HashMap<>();
		
		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			String line; 
   
			while ((line = br.readLine()) != null)  {
				String[] parts = line.trim().split(" ");
	   
				if (parts.length >= 2) {
					String itemName = parts[0];
					Double itemPrice = Double.parseDouble(parts[1]);
					priceMap.put(itemName, itemPrice);
				}
			}
		}
		
		return priceMap;
	}
	
	private static void processMultiThreads(int orderNums, String baseFilename, 
					Map<String, Double> priceMap, Map<String, ItemInfo> allItemsOrdered,
					Object lock,List<Order> allOrders) {
		
		ArrayList<Thread> threads = new ArrayList<Thread>();
		for (int i = 1; i <= orderNums; i++) {
			String filename = baseFilename + i + ".txt";
			
			Order order = new Order(filename, priceMap, allItemsOrdered, lock);
			allOrders.add(order);
			
			Thread thread = new Thread(order);
			threads.add(thread);
			thread.start();
		}
		
		for (Thread t: threads) {
			try {
				t.join();
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static void processSingleThread(int orderNums, String baseName, 
						Map<String, Double> priceMap, Map<String, ItemInfo> allItems,
						List<Order> allOrders) {
		for (int i = 1; i <= orderNums; i++) {
            String filename = baseName + i + ".txt";
            Order order = new Order(filename, priceMap, allItems, new Object());
            allOrders.add(order);
            order.run();
		}
	}
	
	public static StringBuilder getFullReport(Map<String, ItemInfo> map) {
		StringBuilder summary = new StringBuilder();
		ArrayList<String> items = new ArrayList<String>(map.keySet());
		NumberFormat dollarF = NumberFormat.getCurrencyInstance();
		
		items.sort(Comparator.naturalOrder());
		double sum = 0.0;
		
		summary.append("***** Summary of all orders *****\n");
		
		for (String item: items) {
			ItemInfo itemInfo = map.get(item);
			double price = itemInfo.getPrice();
			int totalSold = itemInfo.getQty();
			double totalCost = price * totalSold;
			sum += totalCost;
			
			summary.append("Summary - Item's name: ").append(item);
			summary.append(", Cost per item: ").append(dollarF.format(price));
			summary.append(", Number sold: ").append(totalSold);
			summary.append(", Item's Total: ").append(dollarF.format(totalCost)).append("\n");
		}
		summary.append("Summary Grand Total: ").append(dollarF.format(sum));
		return summary;
	}
	
}