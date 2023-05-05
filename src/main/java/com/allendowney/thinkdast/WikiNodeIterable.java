/**
 * 
 */
package com.allendowney.thinkdast;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.jsoup.nodes.Node;


/**
 * Performs a depth-first traversal of a jsoup Node.
 *
 * @author downey
 *
 */
public class WikiNodeIterable implements Iterable<Node> {

	//전통적인 공식을 따라야함
	//1. 생성자는 루트 노드에 대한 참조를 인자로 받아 저장
	//2. iterator 메서드는 Iterator 객체를 생성하여 반환함
	private Node root;

	/**
	 * Creates an iterable starting with the given Node.
	 *
	 * @param root
	 */
	public WikiNodeIterable(Node root) {
	    this.root = root;
	}  // --> 인자로 받아 저장하는 코드
	@Override
	public Iterator<Node> iterator() {
		return new WikiNodeIterator(root);
	}

	/**
	 * Inner class that implements the Iterator.
	 *
	 * @author downey
	 *
	 */
	private class WikiNodeIterator implements Iterator<Node> {

		// this stack keeps track of the Nodes waiting to be visited
		Deque<Node> stack;

		/**
		 * Initializes the Iterator with the root Node on the stack.
		 *
		 * @param node
		 */
		public WikiNodeIterator(Node node) {
			stack = new ArrayDeque<Node>();
		    stack.push(root);
		}

		@Override
		public boolean hasNext() {
			return !stack.isEmpty();
		}

		@Override
		public Node next() {
			// if the stack is empty, we're done
			if (stack.isEmpty()) {
				throw new NoSuchElementException();
			}

			// otherwise pop the next Node off the stack
			Node node = stack.pop();
			//System.out.println(node);

			// push the children onto the stack in reverse order
			List<Node> nodes = new ArrayList<Node>(node.childNodes());
			Collections.reverse(nodes);
			for (Node child: nodes) {
				stack.push(child);
			}
			return node;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
}
