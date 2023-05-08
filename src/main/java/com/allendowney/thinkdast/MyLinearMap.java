/**
 *
 */
package com.allendowney.thinkdast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Implementation of a Map using a List of entries, so most
 * operations are linear time.
 *
 * @author downey
 * @param <K>
 * @param <V>
 *
 */
public class MyLinearMap<K, V> implements Map<K, V> {

	private List<Entry> entries = new ArrayList<Entry>();

	public class Entry implements Map.Entry<K, V> {
		private K key;
		private V value;

		public Entry(K key, V value) {
			this.key = key;
			this.value = value;
		}

		@Override
		public K getKey() {
			return key;
		}
		@Override
		public V getValue() {
			return value;
		}
		@Override
		public V setValue(V newValue) {
			value = newValue;
			return value;
		}
	}

	@Override
	public void clear() {
		entries.clear();
	}

	@Override
	public boolean containsKey(Object target) {
		return findEntry(target) != null;
	}

	/**
	 * Returns the entry that contains the target key, or null if there is none.
	 *
	 * @param target
	 */
	private Entry findEntry(Object target) {
		// TODO: FILL THIS IN!
		//엔트리의 크기에 비례하므로 선형
		for(Entry entry : entries){
			if(equals(target, entry.getKey())){
				return  entry;
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
		//equals메서드는 target과 key의 크기에 의존하지만 엔트리 개수에 희존하지 않으므로 상수 시간임
		if (target == null) {
			return obj == null;
		}
		return target.equals(obj);
	}

	@Override
	public boolean containsValue(Object target) {
		for (Map.Entry<K, V> entry: entries) {
			if (equals(target, entry.getValue())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Set<Map.Entry<K, V>> entrySet() {
		throw new UnsupportedOperationException();
	}

	@Override
	public V get(Object key) {
		// TODO: FILL THIS IN!
		Entry entry = findEntry(key);
		if(entry == null){
			return null;
		}
		return entry.getValue();
	}

	@Override
	public boolean isEmpty() {
		return entries.isEmpty();
	}

	@Override
	public Set<K> keySet() {
		Set<K> set = new HashSet<K>();
		for (Entry entry: entries) {
			set.add(entry.getKey());
		}
		return set;
	}

	@Override
	public V put(K key, V value) {
		Entry entry = findEntry(key);  //엔트리를 key를 통해 찾음
		//findEntry 메서드를 호출한 후에는 모두 상수 시간 
		if(entry == null){ //없으면 새로 넣어줌
			entries.add(new Entry(key, value));
			return null;
		}else {  //key값이 같은 entry가 있으면
			V oldValue = entry.getValue(); //현재 key값에 따른 value값 저장
			entry.setValue(value);  //entry값을 setValue해주어도 위의 변수 entries 또한 갱신됨.
			return oldValue;
									// 하지만 findEntry에서 이미 entries의 참조를 받으므로 entries도 갱신됨
		}
	}


	@Override
	public void putAll(Map<? extends K, ? extends V> map) {
		for (Map.Entry<? extends K, ? extends V> entry: map.entrySet()) {
			put(entry.getKey(), entry.getValue());
		}
	}

	@Override
	public V remove(Object key) {
		// TODO: FILL THIS IN!
		Entry entry = findEntry(key);
		if(entry==null){
			return null;
		}else{
			V value = entry.getValue(); //삭제한 값에 대해 저장 --> entry.getValue()인 이유는
			//이미 findEntry에서 entries의 entry를 찾아왔기 때문에
			entries.remove(entry); //하지만 삭제할 때는 우리가 갱신해줘야하는 대상인 entries에 삭제
			return value;
		}
	}

	@Override
	public int size() {
		return entries.size();
	}

	@Override
	public Collection<V> values() {
		Set<V> set = new HashSet<V>();
		for (Entry entry: entries) {
			set.add(entry.getValue());
		}
		return set;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Map<String, Integer> map = new MyLinearMap<String, Integer>();
		map.put("Word1", 1);
		map.put("Word2", 2);
		Integer value = map.get("Word1");
		System.out.println(value);

		for (String key: map.keySet()) {
			System.out.println(key + ", " + map.get(key));
		}
	}

	/**
	 * Returns a reference to `entries`.
	 *
	 * This is not part of the Map interface; it is here to provide the functionality
	 * of `entrySet` in a way that is substantially simpler than the "right" way.
	 *
	 * @return
	 */
	protected Collection<? extends java.util.Map.Entry<K, V>> getEntries() {
		return entries;
	}
}
