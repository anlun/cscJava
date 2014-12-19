package ru.compscicenter.java2013.collections;

import junit.framework.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * User: anlun
 */
public class MultiSetImplTest {
    @Test
    public void testAdd() {
        MultiSetImpl<Integer> set = new MultiSetImpl<Integer>();
        set.add(1);
        set.add(1);
        set.add(1);
        set.add(2);
        set.add(2);
        Assert.assertEquals(5, set.size());
    }

    @Test
    public void testIterate() {
        MultiSetImpl<Integer> set = new MultiSetImpl<Integer>();
        set.add(1);
        set.add(2);
        set.add(3);
        set.add(4);
        set.add(5);

        int count = 0;
        for (Integer e: set) {
            if (e < 1 || e > 5) {
                Assert.fail();
            }
            count++;
        }
        Assert.assertEquals(5, count);
    }

    @Test
    public void testRemove() {
        MultiSetImpl<Integer> set = new MultiSetImpl<Integer>();
        set.add(1);
        set.add(2);
        set.add(3);
        set.add(4);
        set.add(5);

        set.remove(5);

        int count = 0;
        for (Integer e: set) {
            if (e < 1 || e > 4) {
                Assert.fail();
            }
            count++;
        }
        Assert.assertEquals(4, count);
    }

    @Test
    public void testCount() {
        MultiSetImpl<Integer> set = new MultiSetImpl<Integer>();
        set.add(1);
        set.add(2);
        set.add(3);
        set.add(4);

        set.add(5);
        set.add(5);
        set.add(5);
        set.add(5);

        set.remove(5);

        Assert.assertEquals(3, set.count(5));
    }

    @Test
    public void testToArray() {
        MultiSetImpl<Integer> set = new MultiSetImpl<Integer>();
        set.add(1);
        set.add(2);
        set.add(3);
        set.add(4);

        set.add(5);
        set.add(5);
        set.add(5);
        set.add(5);

        set.remove(5, 2);
        set.remove(3, 2);

        Object[] objects = set.toArray();
        Assert.assertEquals(8-3, objects.length);
    }

    @Test
    public void testToArrayT() {
        MultiSetImpl<Integer> set = new MultiSetImpl<Integer>();
        set.add(1);
        set.add(2);
        set.add(3);
        set.add(4);

        set.add(5);
        set.add(5);
        set.add(5);
        set.add(5);

        set.remove(5, 2);
        set.remove(3, 2);

        Integer[] ints = {-1, -2, -3, -4, -8, -9};
        Object[] objects = set.toArray(ints);
        Assert.assertEquals(null, objects[5]);
    }

    @Test
    public void testToArrayT_creation() {
        MultiSetImpl<Integer> set = new MultiSetImpl<Integer>();
        set.add(1);
        set.add(2);
        set.add(3);
        set.add(4);
        set.add(1);
        set.add(2);
        set.add(3);
        set.add(4);

//        set.remove(3, 2);

        Integer[] ints = {-1, -2, -3, -4, -8, -9};
        Object[] objects = set.toArray(ints);
        for (Object i: objects) {
            System.out.println(i);
        }
    }

    @Test
    public void testRetainAll() {
        MultiSetImpl<Integer> set = new MultiSetImpl<Integer>();
        set.add(1);
        set.add(2);
        set.add(3);
        set.add(4);

        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        set.retainAll(list);
        Assert.assertEquals(3, set.size());
    }

    @Test
    public void testRemoveAll() {
        MultiSetImpl<Integer> set = new MultiSetImpl<Integer>();
        set.add(1);
        set.add(2);
        set.add(3);
        set.add(4);

        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        set.removeAll(list);
        Assert.assertEquals(1, set.size());
    }
}
