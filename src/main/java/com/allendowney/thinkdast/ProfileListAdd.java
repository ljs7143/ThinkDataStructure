package com.allendowney.thinkdast;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.jfree.data.xy.XYSeries;

import com.allendowney.thinkdast.Profiler.Timeable;

public class ProfileListAdd {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		profileArrayListAddEnd();
		//profileArrayListAddBeginning();
		//profileLinkedListAddBeginning();
		//profileLinkedListAddEnd();
	}

	/**
	 * Characterize the run time of adding to the end of an ArrayList
	 */
	public static void profileArrayListAddEnd() {
		Timeable timeable = new Timeable() {
			List<String> list;

			public void setup(int n) {
				list = new ArrayList<String>();
			}

			public void timeMe(int n) {
				for (int i=0; i<n; i++) {
					list.add("a string");
				}
			}
		};
		int startN = 4000;
		int endMillis = 1000;
		runProfiler("ArrayList add end", timeable, startN, endMillis);
	}
	
	/**
	 * Characterize the run time of adding to the beginning of an ArrayList
	 */
	public static void profileArrayListAddBeginning() {
		Timeable timeable = new Timeable() {
			List<String> list;

			public void setup(int n) {
				list = new ArrayList<String>();
			}

			public void timeMe(int n) {
				for (int i=0; i<n; i++) {
					list.add(0,"a string");
				}
			}
		};
		int startN = 4000;
		int endMillis = 1000;
		runProfiler("ArrayList add end", timeable, startN, endMillis);


		// TODO: FILL THIS IN!
	}

	/**
	 * Characterize the run time of adding to the beginning of a LinkedList
	 */
	public static void profileLinkedListAddBeginning() {
		// TODO: FILL THIS IN!


		//Timeable 인터페이스를 구현한 인ㄱ명클래스 생성
		Timeable timeable = new Timeable() {
			List<String> list ;
			@Override
			public void setup(int n) {
				//준비작업 수행
				//객체 생성하여 리스트 초기화
				list = new LinkedList<String>();
			}

			@Override
			public void timeMe(int n) {

				//실제 테스트 수행
				for(int i=0; i<n; i++){
					//0에 추가한다는 구문이 있으므로 beginning에 추가하는 코드가 됨
					list.add(0, "a string");
				}

			}
		};
		int startN = 128000;
		int endMills = 2000;
		//첫번째 > 측정하려는 작업의 이름, 두번째 > 구현 객체, 세번째 > 매개변수 초기 입력 크기, 네번째 > 측정 시간 제한
		runProfiler("LinkedList add beginning", timeable , startN, endMills);
	}

	/**
	 * Characterize the run time of adding to the end of a LinkedList
	 */
	public static void profileLinkedListAddEnd() {
		// TODO: FILL THIS IN!
		Timeable timeable = new Timeable() {
			List<String> list;
			@Override
			public void setup(int n) {
				list= new LinkedList<String>();
			}

			@Override
			public void timeMe(int n) {

				for(int i = 0; i<n; i++){

					//이게 리스트의 끝에 삽입하는 이유는 계속 뒤에 삽입하기 때문
					//ListAddBeginning은 list.add(0,"a String") --> 이건 0번째 인덱스 즉 맨 앞에 계속 추가한다는 의미. 즉 서로 다름. 헷갈린 부분 이해 완료
						list.add("a String");
				}

			}
		};
		int startN = 64000;
		int endMills = 1000;
		//첫번째 > 측정하려는 작업의 이름, 두번째 > 구현 객체, 세번째 > 매개변수 초기 입력 크기, 네번째 > 측정 시간 제한
		runProfiler("LinkedList add end", timeable , startN, endMills);

	}

	/**
	 * Runs the profiles and displays results.
	 * 
	 * @param timeable
	 * @param startN
	 * @param endMillis
	 */
	private static void runProfiler(String title, Timeable timeable, int startN, int endMillis) {
		Profiler profiler = new Profiler(title, timeable);
		XYSeries series = profiler.timingLoop(startN, endMillis);
		profiler.plotResults(series);
	}
}