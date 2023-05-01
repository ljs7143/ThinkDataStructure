package com.allendowney.thinkdast;

/**
 * @author downey
 *
 */
public class
LinkedListExample {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ListNode node1 = new ListNode(1);
		ListNode node2 = new ListNode(2);
		ListNode node3 = new ListNode(3);


		//연결하는 과정
		//관례상 마지막 노드의 next는 null로 처리함
		node1.next = node2;
		node2.next = node3;
		node3.next = null;
		
		ListNode node0 = new ListNode(0, node1);
		System.out.println(node0);
	}
}
