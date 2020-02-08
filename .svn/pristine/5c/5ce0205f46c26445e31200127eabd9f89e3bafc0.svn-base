/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.system.util.comparator;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 *
 * @author chanuka
 */
public class MapValueSort {

    /** inner class to do soring of the map **/
    public static class ValueComparer implements Comparator {

        private Map _data = null;

        public ValueComparer(Map data) {
            super();
            _data = data;
        }

//        public int compare(Object o1, Object o2) {
//            String e1 = (String) _data.get(o1);
//            String e2 = (String) _data.get(o2);
//            return e1.compareTo(e2);
//        }
        public int compare(Object key1, Object key2) {
            Comparable value1 = (Comparable) _data.get(key1);
            Comparable value2 = (Comparable) _data.get(key2);
            int c = value1.compareTo(value2);
            if (0 != c) {
                return c;
            }
            Integer h1 = key1.hashCode(), h2 = key2.hashCode();
            return h1.compareTo(h2);
        }
    }

//    public static void main(String[] args) {
//
//        Map unsortedData = new HashMap();
//        unsortedData.put("2", "chanu");
//        unsortedData.put("1", "mahesh");
//        unsortedData.put("4", "janaka");
//        unsortedData.put("3", "mahesh");
//
//
//        SortedMap sortedData = new TreeMap(new MapValueSort.ValueComparer(unsortedData));
//
//        printMap(unsortedData);
//
//        sortedData.putAll(unsortedData);
//        System.out.println();
//        printMap(sortedData);
//    }

//    private static void printMap(Map data) {
//        for (Iterator iter = data.keySet().iterator(); iter.hasNext();) {
//            String key = (String) iter.next();
//            System.out.println("Value/key:" + data.get(key) + "/" + key);
//        }
//    }
}
