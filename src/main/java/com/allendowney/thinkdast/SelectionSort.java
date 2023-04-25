/**
 * 
 */
package com.allendowney.thinkdast;

import java.util.Arrays;

/**
 * @author downey
 *
 */

//*선형 연산을 n번 반복하면 2차가 된다
public class SelectionSort {

	/**
	 * Swaps the elements at indexes i and j.
	 */

	//요소의 크기와 첫 번째 위치를 알고 있다면 한 번의 곱셈과 덧셈으로 어떤 요소의 위치라도 알 수 있기 때문에 상수 시간의 연산임
	public static void swapElements(int[] array, int i, int j) {
		int temp = array[i];
		array[i] = array[j];
		array[j] = temp;
	}

	/**
	 * Finds the index of the lowest value
	 * between indices low and high (inclusive).
	 */


	//start인자가 0이면 전체 배열 검색 > 비교횟수는 n
	//1이면 n-1
	//n-start이므로 선형메서드가 됨
	public static int indexLowest(int[] array, int start) {
		int lowIndex = start;
		for (int i = start; i < array.length; i++) {
			if (array[i] < array[lowIndex]) {
				lowIndex = i;
			}
		}
		return lowIndex;
	}

	/**
	 * Sorts the cards (in place) using selection sort.
	 */
	//sort메서드에서 처음 호출되면 n번 비교 연산을 함.
	//2번째는 n-1비교 연산을 함 위가 반복되면 n^2에 비례함
	//그러므로 sort메서드는 2차다

	public static void selectionSort(int[] array) {
		for (int i = 0; i < array.length; i++) {
			int j = indexLowest(array, i);
			swapElements(array, i, j);
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int[] array = {2, 5, 6, 1, 3};
		selectionSort(array);
		System.out.println(Arrays.toString(array));
	}

}
