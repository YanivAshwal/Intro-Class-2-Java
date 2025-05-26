package listClasses;

import java.util.*;



public class BasicLinkedList<T> implements Iterable<T> {
	
	/* Node definition */
	protected class Node {
		protected T data;
		protected Node next;

		protected Node(T data) {
			this.data = data;
			next = null;
		}
	}

	/* We have both head and tail */
	protected Node head, tail;
	/* size */
	protected int listSize;
	
	public BasicLinkedList() {
		head = null;
		tail = null;
		listSize = 0; 
	}
	
	public int getSize() { 
		return listSize;
	}
	
	public BasicLinkedList<T> addToEnd(T data) {
		Node newNode = new Node(data);
		if (head == null) {
			head = newNode;
			tail = newNode;
		} else {
			tail.next = newNode;
			tail = newNode;
		}
		listSize++;
		return this;
	}
	
	public BasicLinkedList<T> addToFront(T data) { 
		Node node = new Node(data);
		node.next = head;
		head = node;
		
		if (tail == null) {
			tail = node;
		} 
		listSize++;
		return this;
	}
	
	public T getFirst() { 
		return (T) head.data;
	}
	
	public T getLast() { 
		return (T) tail.data;
	}
	
	public T retrieveFirstElement() {
		if (head == null) { //null check
			return null;
		}
		
		Node temp = head; 
		head = head.next; 
		
		if (head == null) { 
			tail = null;
		}
		listSize--;
		return temp.data;
 	}
	
	public T retrieveLastElement() {
		if (head == null) { //null check
			return null;
		}
		Node temp = tail; //what we want
		Node curr = head; //for iteration
		
		if (listSize == 1) {
			head = null;
			tail = null;
			listSize--;
			return temp.data;
		}
		
		while (curr.next != null) { 
			if (curr.next == tail) {
				tail = curr;
				tail.next = null;
				break;
			}
			curr = curr.next;
		}
		
		listSize--;
		return temp.data;
	}
	
	public BasicLinkedList<T> remove(T targetData, Comparator<T> comparator) { 
		if (head == null) {
			return this;
		}
		
		//if head is target, (and if whole list is target)
		while (head != null && comparator.compare(head.data, targetData) == 0) {
			head = head.next;
			listSize--;
		}
		
		//if the whole list was target this is needed
		if (head == null) {
			tail = null;
			return this;
		}
		
	    Node prev = head;
	    Node curr = head.next;
		
		while (curr != null) {
			if (comparator.compare(curr.data, targetData) == 0) { //if curr = target
				prev.next = curr.next; //set the prev pointer to the one after curr, 
				listSize--;			  // removing all pointers to it
			
				if (curr == tail) { 
					tail = prev;
				}
				curr = curr.next;

			} else { 
				prev = curr;
				curr = curr.next;
			}
		}
		return this;
	}
	
	public Iterator<T> iterator() {
		 return new Iterator<T>() { 
			 private Node curr = head;
			 
			 public boolean hasNext() { 
				 return curr != null;
			 }
			 
			 public T next() { 
				 if (!hasNext()) { 
					 throw new NoSuchElementException(); 
				 }
				 T data = curr.data; 
				 curr = curr.next;
				 return data;
			 }
			 
			 public void remove() { 
				 throw new UnsupportedOperationException();
			 }
		 }; 
	}
	
	public ArrayList<T> getReverseArrayList() {
		ArrayList<T> array = new ArrayList<>(); 
		getReverseArrayListHelper(head, array);
		return array;
	}
	
	private void getReverseArrayListHelper(Node curr, ArrayList<T> arr) { 
		if (curr == null) { 
			return;
		}
		getReverseArrayListHelper(curr.next, arr); //recursive call here, so we go down the line
		arr.add(curr.data);				//then add to list, so it adds in reverse order
	}
	
	public BasicLinkedList<T> getReverseList() {
	    BasicLinkedList<T> list = new BasicLinkedList<>();
	    getReverseListHelper(head, list);
		return list;
	}
	
	private void getReverseListHelper(Node curr, BasicLinkedList<T> list) {
		if (curr == null) { 
			return;
		}
		getReverseListHelper(curr.next, list);
		list.addToEnd(curr.data);
	}
	
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("[");
	    Node current = head;
	    while (current != null) {
	        sb.append(current.data);
	        if (current.next != null) {
	            sb.append(", ");
	        }
	        current = current.next;
	    }
	    sb.append("]");
	    return sb.toString();
	}
}