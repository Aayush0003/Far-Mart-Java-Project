package com.yrl;

public class Node<T> {
	
	private T element;
	private Node<T> next;
 
	// Constructs a new Node with the specified element.
	public Node(T element) {
		this.element = element;
		this.next = null;
	}
 
	// Returns the next node in the list.
	public Node<T> getNext() {
		return next;
	}
 
	// Sets the next node in the list to the specified node.
	public void setNext(Node<T> next) {
		this.next = next;
	}
 
	// Returns the element stored in this node.
	public T getElement() {
		return element;

	}
}
