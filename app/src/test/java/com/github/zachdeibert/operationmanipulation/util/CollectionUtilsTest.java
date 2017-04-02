package com.github.zachdeibert.operationmanipulation.util;

import com.github.zachdeibert.operationmanipulation.AbstractUnitTest;

import junit.framework.Assert;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class CollectionUtilsTest extends AbstractUnitTest {
    @Test
    @SuppressWarnings("unchecked")
    public void checkedAssignmentList() {
        ArrayList list = new ArrayList();
        list.addAll(Arrays.asList("a", "b", "c", 1, 2, "d", 3, "f", 42, 0.9, "e", this));
        ArrayList<String> stringList = CollectionUtils.checkedAssignment(list, String.class);
        ArrayList<Integer> intList = CollectionUtils.checkedAssignment(list, Integer.class);
        ArrayList<Double> doubleList = CollectionUtils.checkedAssignment(list, Double.class);
        ArrayList<CollectionUtilsTest> testList = CollectionUtils.checkedAssignment(list, CollectionUtilsTest.class);
        Assert.assertTrue("ArrayList<String>", Arrays.deepEquals(new String[] {
                "a", "b", "c", "d", "f", "e"
        }, stringList.toArray()));
        Assert.assertTrue("ArrayList<Integer>", Arrays.deepEquals(new Integer[] {
                1, 2, 3, 42
        }, intList.toArray()));
        Assert.assertTrue("ArrayList<Double>", Arrays.deepEquals(new Double[] {
                0.9
        }, doubleList.toArray()));
        Assert.assertTrue("ArrayList<CollectionUtilsTest>", Arrays.deepEquals(new CollectionUtilsTest[] {
                this
        }, testList.toArray()));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void checkedAssignmentMap() {
        HashMap map = new HashMap();
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);
        map.put("d", "e");
        map.put("f", "g");
        map.put(1, "h");
        map.put(2, "i");
        map.put(3, this);
        map.put("j", this);
        HashMap<String, Integer> stringIntegerMap = CollectionUtils.checkedAssignment(map, String.class, Integer.class);
        HashMap<String, String> stringStringMap = CollectionUtils.checkedAssignment(map, String.class, String.class);
        HashMap<Integer, String> integerStringMap = CollectionUtils.checkedAssignment(map, Integer.class, String.class);
        HashMap<Integer, CollectionUtilsTest> integerTestMap = CollectionUtils.checkedAssignment(map, Integer.class, CollectionUtilsTest.class);
        HashMap<String, CollectionUtilsTest> stringTestMap = CollectionUtils.checkedAssignment(map, String.class, CollectionUtilsTest.class);
        Assert.assertEquals("Map<String, Integer>.size()", 3, stringIntegerMap.size());
        Assert.assertTrue("Map<String, Integer>.keySet()", stringIntegerMap.keySet().containsAll(Arrays.asList("a", "b", "c")));
        Assert.assertEquals("Map<String, String>.size()", 2, stringStringMap.size());
        Assert.assertTrue("Map<String, String>.keySet()", stringStringMap.keySet().containsAll(Arrays.asList("d", "f")));
        Assert.assertEquals("Map<Integer, String>.size()", 2, integerStringMap.size());
        Assert.assertTrue("Map<Integer, String>.keySet()", integerStringMap.keySet().containsAll(Arrays.asList(1, 2)));
        Assert.assertEquals("Map<Integer, CollectionUtilsTest>.size()", 1, integerTestMap.size());
        Assert.assertTrue("Map<Integer, CollectionUtilsTest>.keySet()", integerTestMap.containsKey(3));
        Assert.assertEquals("Map<String, CollectionUtilsTest>.size()", 1, stringTestMap.size());
        Assert.assertTrue("Map<String, CollectionUtilsTest>.keySet()", stringTestMap.containsKey("j"));
        HashMap values = new HashMap();
        values.putAll(stringIntegerMap);
        values.putAll(stringStringMap);
        values.putAll(integerStringMap);
        values.putAll(integerTestMap);
        values.putAll(stringTestMap);
        Assert.assertEquals("Recreated map size", map.size(), values.size());
        for (Object key : values.keySet()) {
            Assert.assertEquals("Recreated map data", map.get(key), values.get(key));
        }
    }
}
