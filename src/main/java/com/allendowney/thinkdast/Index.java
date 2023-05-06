package com.allendowney.thinkdast;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

import org.jsoup.select.Elements;

/**
 * Encapsulates a map from search term to set of TermCounter.
 *
 * @author downey
 *
 */
public class Index {

    //문자열을 키로 가지며 해당 문자열과 관련된 TermCounter 객체들의 집합(Set)을 값으로 가짐
    private Map<String, Set<TermCounter>> index = new HashMap<String, Set<TermCounter>>();

    /**
     * Adds a TermCounter to the set associated with `term`.
     *
     * @param term
     * @param tc
     */
    public void add(String term, TermCounter tc) {
        Set<TermCounter> set = get(term);   // 객체에서 주어진 키(term)에 해당하는 값(Set<TermCounter>)을 반환함. get은 argument의 key값에 대한 value를 반환해줌. 그래서 set(value) 값에 value가 들어갈 수 있도록
        // if we're seeing a term for the first time, make a new Set
        if (set == null) {
            //없으면 객체를 생성하고 Map에 추가하는 과정
            set = new HashSet<TermCounter>();
            index.put(term, set);
        }
        // otherwise we can modify an existing Set
        set.add(tc);  //이미 존재할 시 변경
    }

    /**
     * Looks up a search term and returns a set of TermCounters.
     *
     * @param term
     * @return
     */
    public Set<TermCounter> get(String term) {

        return index.get(term);   //검색어를 인자로 받아 그에 맞는 TermCounter 객체의 집합을 반환함

    }

    /**
     * Prints the contents of the index.
     */
    public void printIndex() {
        // loop through the search terms
        for (String term: keySet()) {
            //Set의 url에 term url이 포함되면 term(url)출력 --> Termcounter 객체를 보면 이해할 수 있음
            System.out.println(term);

            // for each term, print the pages where it appears
            Set<TermCounter> tcs = get(term);
            for (TermCounter tc: tcs) {
                Integer count = tc.get(term);
                System.out.println("    " + tc.getLabel() + " " + count);
            }
        }
    }

    /**
     * Returns the set of terms that have been indexed.
     *
     * @return
     */
    public Set<String> keySet() {
        return index.keySet();
    }

    /**
     * Add a page to the index.
     *
     * @param url         URL of the page.
     * @param paragraphs  Collection of elements that should be indexed.
     */
    public void indexPage(String url, Elements paragraphs) {
        // TODO: Your code here

        TermCounter tc = new TermCounter(url);
        tc.processElements(paragraphs);

        for(String term : tc.keySet()){
            add(term, tc);

        }

        // make a TermCounter and count the terms in the paragraphs

        // for each term in the TermCounter, add the TermCounter to the index
    }

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        WikiFetcher wf = new WikiFetcher();
        Index indexer = new Index();

        String url = "https://en.wikipedia.org/wiki/Java_(programming_language)";
        Elements paragraphs = wf.fetchWikipedia(url);
        indexer.indexPage(url, paragraphs);

        url = "https://en.wikipedia.org/wiki/Programming_language";
        paragraphs = wf.fetchWikipedia(url);
        indexer.indexPage(url, paragraphs);

        indexer.printIndex();
    }
}