package com.allendowney.thinkdast;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * @author downey
 * @param <T>
 *
 */
public class MyArrayList<T> implements List<T> {
	int size;                    // keeps track of the number of elements
	private T[] array;           // stores the elements

	/**
	 *
	 */
	@SuppressWarnings("unchecked")
	public MyArrayList() {
		// You can't instantiate an array of T[], but you can instantiate an
		// array of Object and then typecast it.  Details at
		// http://www.ibm.com/developerworks/java/library/j-jtp01255/index.html
		array = (T[]) new Object[10];
		size = 0;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// run a few simple tests
		MyArrayList<Integer> mal = new MyArrayList<Integer>();
		mal.add(1);
		mal.add(2);
		mal.add(3);
		System.out.println(Arrays.toString(mal.toArray()) + " size = " + mal.size);

		mal.remove(new Integer(2));
		System.out.println(Arrays.toString(mal.toArray()) + " size = " + mal.size);
	}

	@Override
	public boolean add(T element) {

		//항상 마지막에만 삽입할 수 있는 메서드임
		// TODO: FILL THIS IN!
		if(size >= array.length){
			//큰 배열을 만들고 요소들을 복사함
			T[] bigger = (T[]) new Object[array.length*2];
			System.arraycopy(array, 0, bigger, 0, array.length); //--->위메서드 호출 시 실행시간이 배열의 크기에 비레하기 때문에 선형


			array = bigger;
		}
		array[size] = element;
		size++;
		return true;
	}

	@Override
	public void add(int index, T element) {


	
		//선형메서드 
		//크기를 변경 안해줘도 되는 이유는 arrayList이기 때문임   
		//중간에 삽입가능한 add메소드임
		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException();
		}
		// add the element to get the resizing
		add(element);

		// shift the elements
		for (int i=size-1; i>index; i--) {
			array[i] = array[i-1];
		}
		// put the new one in the right place
		array[index] = element;
	}

	@Override
	public boolean addAll(Collection<? extends T> collection) {
		boolean flag = true;
		for (T element: collection) {
			flag &= add(element);
		}
		return flag;
	}

	@Override
	public boolean addAll(int index, Collection<? extends T> collection) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clear() {
		// note: this version does not actually null out the references
		// in the array, so it might delay garbage collection.
		size = 0;
	}

	@Override
	public boolean contains(Object obj) {
		return indexOf(obj) != -1;
	}

	@Override
	public boolean containsAll(Collection<?> collection) {
		for (Object element: collection) {
			if (!contains(element)) {
				return false;
			}
		}
		return true;
	}

	@Override
	//get메서드에서 모든 것은 상수시간이다
	public T get(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		return array[index];
	}
	@Override
	//평균적으로 n/2가 기대됨으로 선형메서드임 
	public int indexOf(Object target) {
		// TODO: FILL THIS IN!
		//값이 없으면 -1을 반환해야함
		//아니면 index에 해당하는 값을 반환해야함
		for(int i=0; i< size; i++){
			if(equals(target, array[i])){
				return i;
			}
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
	public Iterator<T> iterator() {
		// make a copy of the array
		T[] copy = Arrays.copyOf(array, size);
		// make a list and return an iterator
		return Arrays.asList(copy).iterator();
	}

	@Override
	public int lastIndexOf(Object target) {
		// see notes on indexOf
		for (int i = size-1; i>=0; i--) {
			if (equals(target, array[i])) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public ListIterator<T> listIterator() {
		// make a copy of the array
		T[] copy = Arrays.copyOf(array, size);
		// make a list and return an iterator
		return Arrays.asList(copy).listIterator();
	}

	@Override
	public ListIterator<T> listIterator(int index) {
		// make a copy of the array
		T[] copy = Arrays.copyOf(array, size);
		// make a list and return an iterator
		return Arrays.asList(copy).listIterator(index);
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
	//if문을 돌릴 이유가 없음. 이미 index번호를 argument로 주기 때문에 시작을 index번호로 삼고 한칸씩 앞으로 당긴 후 사이즈를 줄이면 됨.
	public T remove(int index) {
		T element = get(index);
		for(int i=index; i<size-1; i++){
			array[i] = array[i+1];
		}
		size--;
		return element;
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
	//평균적으로 요소 개수의 절반을 테스트하길 기대함 >>> 선형
	public T set(int index, T element) {
		//1차 옵션
		// if (index < 0 || index >= size) {
		// 	throw new IndexOutOfBoundsException();
		// }
		// T old = get(index);
		// array[index]= element;
		// return old;

		//2최적의 코드  --> 명시적으로 배열의 범위를 검사하지 않음. set메서드 또한 상수시간 
		T old = get(index);
		array[index] = element;
		return old;
	}
	//내가 1차로 구현한 코드가 틀린 이유
	//1. arraylist는 크기가 고정되어 있지 않음
	//2. 인덱스 범위를 검사하는 코드도 제대로 구현되어 있지 않음
//	public T set(int index, T element) {
//		if(index<0 || index> array.length){
//			throw new IndexOutOfBoundsException();
//		}
//		else{
//			array[index] = element;
//		}
//		return array[index];
//	}
	@Override
	public int size() {
		return size;
	}
	@Override
	public List<T> subList(int fromIndex, int toIndex) {
		if (fromIndex < 0 || toIndex >= size || fromIndex > toIndex) {
			throw new IndexOutOfBoundsException();
		}
		T[] copy = Arrays.copyOfRange(array, fromIndex, toIndex);
		return Arrays.asList(copy);
	}
	@Override
	public Object[] toArray() {
		return Arrays.copyOf(array, size);
	}
	@Override
	public <U> U[] toArray(U[] array) {
		throw new UnsupportedOperationException();
	}
}
