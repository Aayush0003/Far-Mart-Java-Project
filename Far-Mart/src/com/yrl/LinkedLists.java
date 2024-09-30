package com.yrl;

import java.util.Comparator;
import java.util.Iterator;

/**
 * This class represents a linked list data structure that allows for efficient
 * add, remove, and retrieve of elements in the list. It implements the Iterable
 * interface to enable iteration over the elements of the list using a enhanced
 * loop.
 * 
 * @author  Aayush Subedi, 
 * @param <T> the type of elements stored in the list.
 */
public class LinkedLists<T> implements Iterable<T> {
 
	private int size;
	private Node<T> head;
	private Comparator<T> cmp;
 
	/**
	 * Constructs an empty linked list with the given comparator function as a
	 * argument.
	 */
	public LinkedLists(Comparator<T> cmp) {
		this.cmp = cmp;
		this.head = null;
		this.size = 0;
	}
 
	// Returns the number of elements in the list.
	public int getSize() {
		return this.size;
	}
 
	// Returns the node at the given index in the list.
	private Node<T> getNode(int index) {
		if (index < 0 || index >= this.size) {
			throw new IllegalArgumentException("Index: " + index + "is out of bounds");
		}
		Node<T> current = head;
		for (int i = 0; i < index; i++) {
			current = current.getNext();
		}
		return current;
	}
 
	// Removes the element at the given index from the list.
	public void remove(int index) {
		if (index < 0 || index >= getSize()) {
			throw new IndexOutOfBoundsException("Invalid position");
		}
		if (index == 0) {
 
			head = head.getNext();
			size = size - 1;
		} else {
			Node<T> previous = getNode(index - 1);
			Node<T> current = previous.getNext();
			previous.setNext(current.getNext());
			size = size - 1;
		}
	}
 
	// Returns the element at the given index in the list.
	public T getElement(int index) {
		if (index < 0 || index >= getSize()) {
			throw new IndexOutOfBoundsException("Invalid position");
		}
		return this.getNode(index).getElement();
	}
 
	/**
	 * Adds the given element to the list in the appropriate index according to the
	 * ordering defined by the comparator function.
	 */
	public void addElement(T element) {
		Node<T> newNode = new Node<T>(element);
 
		if (element == null) {
			throw new IllegalArgumentException("Cannot insert null element");
		}
		if (head == null) {
			head = newNode;
		} else if (this.cmp.compare(head.getElement(), element) >= 0) {
			Node<T> temp = head;
 
			head = newNode;
			head.setNext(temp);
		} else {
			Node<T> current = head;
			Node<T> previous = null;
			while (current != null && this.cmp.compare(current.getElement(), element) < 0) {
				previous = current;
				current = current.getNext();
			}
			if (current == null) {
				previous.setNext(newNode);
			} else if (this.cmp.compare(current.getElement(), element) >= 0) {
				previous.setNext(newNode);
				newNode.setNext(current);
			}
		}
		this.size++;
	}
 
	/**
	 * Iterator to enables iteration over the elements of the list using a enhanced
	 * loop.
	 */
	public Iterator<T> iterator() {
		return new Iterator<>() {
			Node<T> current = head;
 
			public boolean hasNext() {
				return current != null;
			}
 
			public T next() {
				T c = current.getElement();
				current = current.getNext();
				return c;
			}
 
			public void remove() {
				throw new UnsupportedOperationException("It's not allowed.");
			}
		};
	}
}
