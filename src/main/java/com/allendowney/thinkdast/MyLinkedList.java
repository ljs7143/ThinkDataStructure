/**
 * 
 */
package com.allendowney.thinkdast;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * @author downey
 * @param <E>
 *
 */
public class MyLinkedList<E> implements List<E> {

	/**
	 * Node is identical to ListNode from the example, but parameterized with T
	 *
	 * @author downey
	 *
	 */
	private class Node {
		public E data;
		public Node next;

		public Node(E data) {
			this.data = data;
			this.next = null;
		}
		@SuppressWarnings("unused")
		public Node(E data, Node next) {
			this.data = data;
			this.next = next;
		}
		public String toString() {
			return "Node(" + data.toString() + ")";
		}
	}

//size 변수를 명시적으로 저장하면 상수 시간으로 size 메서드를 구현할 수 있다
// 그렇지 않으면 리스트를 순회하여 요소 개수를 세는 선형 시간이 필요하다
// ---> 새로운 노드 추가 시 head변수를 새로운 노드로 업데이트하고 size변수 1증가 시키면 됨
	// 그러나 size변수 저장하지 않으면 리스트 크기를 계산하기 위해 전체 순회해야함 --> O(n)소요 --> 선형
	private int size;    //요소의 개수 추적        // keeps track of the number of elements
	private Node head;    //첫 번째 노드에 대한 참조       // reference to the first node

	/**
	 *
	 */
	public MyLinkedList() {
		head = null;
		size = 0;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// run a few simple tests
		List<Integer> mll = new MyLinkedList<Integer>();
		mll.add(1);
		mll.add(2);
		mll.add(3);
		System.out.println(Arrays.toString(mll.toArray()) + " size = " + mll.size());

		mll.remove(new Integer(2));
		System.out.println(Arrays.toString(mll.toArray()) + " size = " + mll.size());
	}

	@Override
	public boolean add(E element) {
		if (head == null) {
			head = new Node(element);
		} else {
			Node node = head;
			// loop until the last node
			for ( ; node.next != null; node = node.next) {}    //노드의 next포인터가 null이 아니면 node는 next노드로 변경. 즉 끝까지 순환하여 마지막에 새로운 노드를 추가함
			node.next = new Node(element);
		}
		size++;
		return true;
	}

	@Override
	public void add(int index, E element) {  //선형메서드

		//TODO: FILL THIS IN!

		if( index == 0){
			head = new Node(element, head);
		}else{
			Node node = getNode(index-1);
			node.next = new Node(element, node.next);
		}
		size++;



	}

	@Override
	public boolean addAll(Collection<? extends E> collection) {
		boolean flag = true;
		for (E element: collection) {
			flag &= add(element);
		}
		return flag;
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> collection) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clear() {
		head = null;  // 첫번째 Node에 대한 참조를 제거함
		size = 0;
	}

	@Override
	public boolean contains(Object obj) {
		return indexOf(obj) != -1;
	}

	@Override
	public boolean containsAll(Collection<?> collection) {
		for (Object obj: collection) {
			if (!contains(obj)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public E get(int index) {
		Node node = getNode(index);
		return node.data;
	}

	/** Returns the node at the given index.
	 * @param index
	 * @return
	 */

	private Node getNode(int index) {   //선형메서드
		//예외처리
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}


		Node node = head;
		for (int i=0; i<index; i++) {
			node = node.next;
		}
		return node;
	}

	@Override
	public int indexOf(Object target) {
		//TODO: FILL THIS IN!
		//실행시간은 O(n)


		Node node = head; //head의 사본을 얻음
		for(int i=0; i<size-1; i++){
			if(equals(target, node.data)){
				return i;
			}
			node = node.next;
		}
		return -1;
	}

	/** Checks whether an element of the array is the target.
	 *
	 * Handles the special case that the target is null.
	 *
	 * @param target
	 * @param object
	 */
	private boolean equals(Object target, Object element) {
		if (target == null) {
			return element == null;
		} else {
			return target.equals(element);
		}
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public Iterator<E> iterator() {
		E[] array = (E[]) toArray();
		return Arrays.asList(array).iterator();
	}

	@Override
	public int lastIndexOf(Object target) {
		Node node = head;
		int index = -1;
		for (int i=0; i<size; i++) {
			if (equals(target, node.data)) {
				index = i;
			}
			node = node.next;
		}
		return index;
	}

	@Override
	public ListIterator<E> listIterator() {
		return null;
	}

	@Override
	public ListIterator<E> listIterator(int index) {
		return null;
	}

	@Override
	public boolean remove(Object obj) {
		int index = indexOf(obj);
		if (index == -1) {
			return false;
		}
		remove(index);
		return true;
	}

	@Override
	public E remove(int index) {
		//TODO: FILL THIS IN!

		E element = get(index);
		if(index ==0){
			//head가 사라지기 때문에 그 다음꺼를 헤드로 변경해줌
			head = head.next;
		}else{
			Node node = getNode(index-1);
			node.next = node.next.next;

			//ex) 1,2,3,4에서 2를 삭제하는 경우
			//node는 1이 됨, 1의 다음은 3이 됨
			//1,3,4가 됨

		}
		size--;
		return element; //이래야 나중에 혹시라도 무엇을 삭제했는지 정보가 필요하게 되었을 때 문제가 발생하지 않음
	}

	@Override
	public boolean removeAll(Collection<?> collection) {
		boolean flag = true;
		for (Object obj: collection) {
			flag &= remove(obj);
		}
		return flag;
	}

	@Override
	public boolean retainAll(Collection<?> collection) {
		throw new UnsupportedOperationException();
	}

	@Override
	public E set(int index, E element) {
		Node node = getNode(index);
		E old = node.data;
		node.data = element;
		return old;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		if (fromIndex < 0 || toIndex >= size || fromIndex > toIndex) {
			throw new IndexOutOfBoundsException();
		}
		// TODO: classify this and improve it.
		int i = 0;
		MyLinkedList<E> list = new MyLinkedList<E>();
		for (Node node=head; node != null; node = node.next) {
			if (i >= fromIndex && i <= toIndex) {
				list.add(node.data);
			}
			i++;
		}
		return list;
	}

	@Override
	public Object[] toArray() {
		Object[] array = new Object[size];
		int i = 0;
		for (Node node=head; node != null; node = node.next) {
			// System.out.println(node);
			array[i] = node.data;
			i++;
		}
		return array;
	}

	@Override
	public <T> T[] toArray(T[] a) {
		throw new UnsupportedOperationException();
	}
}
