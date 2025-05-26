package listClasses;

import java.util.*;


/**
 * Implements a generic sorted list using a provided Comparator. It extends
 * BasicLinkedList class.
 * 
 *  @author Dept of Computer Science, UMCP
 *  
 */

public class SortedLinkedList<T> extends BasicLinkedList<T> {
	private Comparator<T> comparator;

	public SortedLinkedList(Comparator<T> comparator) {
		this.comparator = comparator;
		head = null;
		tail = null;
	}
	
	public SortedLinkedList<T> add​(T element) {
		Node node = new Node(element); 
		
		if (head == null) {
			head = node; //if empty, add it as head and tail
			tail = node;
			listSize++;
			return this;
		} else if (comparator.compare(element, head.data) <= 0) { //if less than head
			node.next = head; 
			head = node;
		} else {
			Node curr = head;
			Node prev = null;
			
			while (curr != null && comparator.compare(element, curr.data) > 0) {
				prev = curr;
				curr = curr.next;
			}
			
			prev.next = node;
			node.next = curr;
			
			if (curr == null) {
				tail = node;
			}
		}
		listSize++;
		return this;
	}
	
	public SortedLinkedList<T> remove​(T targetData) {
		super.remove(targetData, comparator);
		return this;
	}
	
	public BasicLinkedList<T> addToEnd​(T data) {
		throw new UnsupportedOperationException("Invalid operation for sorted list.");
	}
	
	public BasicLinkedList<T> addToFront​(T data) {
		throw new UnsupportedOperationException("Invalid operation for sorted list.");
	}
	
}