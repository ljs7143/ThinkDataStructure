package com.allendowney.thinkdast;

import java.util.LinkedList;
import java.util.List;

public class ListClientExample {
	@SuppressWarnings("rawtypes")
	private List list;

	@SuppressWarnings("rawtypes")
	//List 인터페이스를 사용하여 LinkedList 클래스의 인스턴스(객체)를 다루고 있다
	public ListClientExample() {
		list = new LinkedList();
		//ArrayList로 생성 시, 정상적으로 코드가 작동함

	}

	@SuppressWarnings("rawtypes")
	public List getList() {
		return list;
	}

	public static void main(String[] args) {
		ListClientExample lce = new ListClientExample();
		@SuppressWarnings("rawtypes")
		List list = lce.getList();
		System.out.println(list);
	}
}
//	ArrayList 클래스를 사용하고자 한다면 생성자만 바꾸면 되고 그 외에는 그대로 두면 된다

