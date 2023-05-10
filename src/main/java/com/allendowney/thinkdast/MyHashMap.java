/**
 *
 */
package com.allendowney.thinkdast;

import java.util.List;
import java.util.Map;

/**
 * Implementation of a HashMap using a collection of MyLinearMap and
 * resizing when there are too many entries.
 *
 * @author downey
 * @param <K>
 * @param <V>
 *
 */
public class MyHashMap<K, V> extends MyBetterMap<K, V> implements Map<K, V> {

	// average number of entries per map before we rehash
	protected static final double FACTOR = 1.0;  //재해시하기 전 하위 맵당 평균 엔트리 개수. 로드 팩터라고 함

	@Override
	public V put(K key, V value) {
		V oldValue = super.put(key, value);

		//System.out.println("Put " + key + " in " + map + " size now " + map.size());

		// check if the number of elements per map exceeds the threshold

		if (size() > maps.size() * FACTOR) {   //하위 맵당 엔트리의 개수가 임게치를 넘지 않는지 확인. 넘으면 rehash메소드 호출
			rehash();
		}
		return oldValue;
	}

	/**
	 * Doubles the number of maps and rehashes the existing entries.
	 */
	/**
	 *
	 */
	protected void rehash() {  //내장된 맵의 개수 k가 두 배가 되어야 함
		// TODO: FILL THIS IN!
		List<MyLinearMap<K,V>> oldMaps = maps;  //이미 존재하는 entry 저장
		//maps 더 만들기
		int newK = maps.size() *2;
		makeMaps(newK);
		for(MyLinearMap<K,V> map : oldMaps){
			for(Map.Entry<K, V> entry : map.getEntries()){
				put(entry.getKey(), entry.getValue());
			}
		}


	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Map<String, Integer> map = new MyHashMap<String, Integer>();
		for (int i=0; i<10; i++) {
			map.put(new Integer(i).toString(), i);
		}
		Integer value = map.get("3");
		System.out.println(value);
	}
}
