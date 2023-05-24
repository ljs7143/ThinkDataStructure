/**
 *
 */
package com.allendowney.thinkdast;

import java.util.Collection;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

/**
 * Implementation of a Map using a binary search tree.
 *
 * @param <K>
 * @param <V>
 *
 */
public class MyTreeMap<K, V> implements Map<K, V> {

	private int size = 0;  //키의 개수를 추적
	private Node root = null;  //트리의 루트 노드를 참조

	/**
	 * Represents a node in the tree.
	 *
	 */
	protected class Node {
		public K key;
		public V value;
		public Node left = null;
		public Node right = null;

		/**
		 * @param key
		 * @param value
		 * @param left
		 * @param right
		 */
		public Node(K key, V value) {
			this.key = key;
			this.value = value;
		}
	}

	@Override
	public void clear() {
		size = 0;
		root = null;
	}

	@Override
	public boolean containsKey(Object target) {
		return findNode(target) != null;
	}

	/**
	 * Returns the entry that contains the target key, or null if there is none.
	 *
	 * @param target
	 */
	private Node findNode(Object target) {
		// some implementations can handle null as a key, but not this one
		if (target == null) {
			throw new IllegalArgumentException();
		}

		// something to make the compiler happy
		@SuppressWarnings("unchecked")
		Comparable<? super K> k = (Comparable<? super K>) target;
		//트리의 한 가지 경로만 검색해야 하므로 트리의 높이에 비레한 시간이 걸린다
		Node node = root;
		while(node != null){
			int cmp = k.compareTo(node.key);
			if(cmp<0) {
				node = node.left;
			}
			else if(cmp>0){
				node = node.right;
			}
			else{
				return node;
			}

		}
		return null;
	}

	/**
	 * Compares two keys or two values, handling null correctly.
	 *
	 * @param target
	 * @param obj
	 * @return
	 */
	private boolean equals(Object target, Object obj) {
		if (target == null) {
			return obj == null;
		}
		return target.equals(obj);
	}

	@Override
	public boolean containsValue(Object target) {
		return containsValueHelper(root, target);
	}

	private boolean containsValueHelper(Node node, Object target) { //모든 노드를 방문하므로 노드의 개수에 시간 비례
		// TODO: FILL THIS IN!
		if(node == null){
			return false;  //node가 null이면 tree 바닥에 이른 것이므로 false반환
		}
		if(equals(target, node.value)){
			return true; //찾았다면 true반환
		}
		if(containsValueHelper(node.left, target)){
			return true;  //왼쪽 하위 트리에서 찾는 재귀호출. 찾으면 오른쪽 하위트리 탐색 X
		}
		if(containsValueHelper(node.right, target)){
			return true; //오른쪽 하위트렝서 재귀호출. 찾지못하면 계속 진행
		}
		return false;
	}

	@Override
	public Set<Map.Entry<K, V>> entrySet() {
		throw new UnsupportedOperationException();
	}

	@Override
	public V get(Object key) {
		Node node = findNode(key);
		if (node == null) {
			return null;
		}
		return node.value;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public Set<K> keySet() {
		Set<K> set = new LinkedHashSet<K>();
		// TODO: FILL THIS IN!
		addInOrder(root, set);
		return set;
	}

	private void addInOrder(Node node, Set<K> set){
		if(node == null){
			return;
		}
		addInOrder(node.left, set);
		set.add(node.key);
		addInOrder(node.right, set);
	}

	@Override
	public V put(K key, V value) {
		if (key == null) {
			throw new NullPointerException();
		}
		if (root == null) {
			root = new Node(key, value);
			size++;
			return null;
		}
		return putHelper(root, key, value);
	}

	private V putHelper(Node node, K key, V value) {

		Comparable<? super K> k = (Comparable<? super K>) key;
		int cmp  = k.compareTo(node.key);
		if(cmp<0){
			if(node.left == null){  //왼쪽 자식이 비어있으면 생성
				node.left = new Node(key, value);
				size ++;
				return null; //생성 후 아무 작업도 수행하지 않는다는 것을 의미함

			} else{
				return putHelper(node.left, key, value);  //null이 아닐 시 재귀호출함 계속 반복하여 조건이 맞는 null부분에 새로 생성함
			}
		}
		if(cmp >0){ //위와 같은 원리지만 오른쪽 자식을 targeting하여 코드 작성
			if(node.right == null){
				node.right = new Node(key, value);
				size ++;
				return null;
			} else{
				return putHelper(node.right, key, value);
			}
		}
		V oldValue = node.value;   //. ex) oldValue = map.put("key1", 3);은 map의 key1에 매핑된 이전 값을 저장.
		// 만약 map에 key1이 존재하지 않으면 oldValue는 null을 반환
		node.value = value; //새로운 값 저장
		return  oldValue;
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> map) {
		for (Map.Entry<? extends K, ? extends V> entry: map.entrySet()) {
			put(entry.getKey(), entry.getValue());
		}
	}

	@Override
	public V remove(Object key) {
		// OPTIONAL TODO: FILL THIS IN!
		throw new UnsupportedOperationException();
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public Collection<V> values() {
		Set<V> set = new HashSet<V>();
		Deque<Node> stack = new LinkedList<Node>();
		stack.push(root);
		while (!stack.isEmpty()) {
			Node node = stack.pop();
			if (node == null) continue;
			set.add(node.value);
			stack.push(node.left);
			stack.push(node.right);
		}
		return set;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Map<String, Integer> map = new MyTreeMap<String, Integer>();
		map.put("Word1", 1);
		map.put("Word2", 2);
		Integer value = map.get("Word1");
		System.out.println(value);

		for (String key: map.keySet()) {
			System.out.println(key + ", " + map.get(key));
		}
	}

	/**
	 * Makes a node.
	 *
	 * This is only here for testing purposes.  Should not be used otherwise.
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public MyTreeMap<K, V>.Node makeNode(K key, V value) {
		return new Node(key, value);
	}

	/**
	 * Sets the instance variables.
	 *
	 * This is only here for testing purposes.  Should not be used otherwise.
	 *
	 * @param node
	 * @param size
	 */
	public void setTree(Node node, int size ) {
		this.root = node;
		this.size = size;
	}

	/**
	 * Returns the height of the tree.
	 *
	 * This is only here for testing purposes.  Should not be used otherwise.
	 *
	 * @return
	 */
	public int height() {
		return heightHelper(root);
	}

	private int heightHelper(Node node) {
		if (node == null) {
			return 0;
		}
		int left = heightHelper(node.left);
		int right = heightHelper(node.right);
		return Math.max(left, right) + 1;
	}
}
