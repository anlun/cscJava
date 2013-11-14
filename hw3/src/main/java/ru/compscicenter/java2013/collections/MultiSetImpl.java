package ru.compscicenter.java2013.collections;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * User: anlun
 */
public class MultiSetImpl<E> implements MultiSet<E> {
    public MultiSetImpl() {
        mySize   = 0;
        mySetMap = initMap();
    }

    public MultiSetImpl(Collection<? extends E> collection) {
        mySize = 0;
        mySetMap = initMap();
        addAll(collection);
    }

    @Override
    public boolean isEmpty() {
        return mySize == 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new MyIterator();
    }

    private class MyIterator implements Iterator<E> {
        public MyIterator() {
            myMapIterator     = mySetMap.entrySet().iterator();
            myCurElementCount = null;
            myCurElement      = null;
        }

        @Override
        public E next() {
            if (myCurElementCount == null || myCurElementCount == 0) {
                final Map.Entry<Object, Integer> nextEntry = myMapIterator.next();
                myCurElementCount = nextEntry.getValue();
                myCurElement      = nextEntry.getKey();
            }

            myCurElementCount--;
            return (E) myCurElement;
        }

        @Override
        public boolean hasNext() {
            return (myCurElementCount != null && myCurElementCount > 0) || myMapIterator.hasNext();
        }

        @Override
        public void remove() {
            if (myCurElementCount == null) {
                throw new IllegalStateException();
            }
            mySetMap.remove(myCurElement);
        }

        private Iterator<Map.Entry<Object, Integer>> myMapIterator;
        private Integer myCurElementCount;
        private Object  myCurElement;
    }

    @Override
    public boolean add(E e) {
        add(e, 1);
        return true;
    }

    @Override
    public int add(E e, int occurrences) {
        if (occurrences < 0) {
            throw new IllegalArgumentException();
        }

        mySize += occurrences;

        final int oldValue = getOccurrences(e);
        mySetMap.put(e, oldValue + occurrences);
        return oldValue;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        for (E e : c) {
            add(e);
        }
        return true;
    }

    private int getOccurrences(Object e) {
        if (!mySetMap.containsKey(e)) {
            return 0;
        }
        return mySetMap.get(e);
    }

    @Override
    public boolean remove(Object e) {
        return remove(e, 1) == 0;
    }

    @Override
    public int remove(Object e, int occurrences) {
        if (occurrences < 0) {
            throw new IllegalArgumentException();
        }

        final int oldValue = getOccurrences(e);
        if (oldValue == 0 || occurrences == 0) {
            return oldValue;
        }

        final int newValue = Math.max(0, oldValue - occurrences);
        mySize -= oldValue - newValue;

        if (newValue != 0) {
            mySetMap.put(e, newValue);
        } else {
            mySetMap.remove(e);
        }

        return oldValue;
    }

    private boolean removeAllElementOccurrences(Object e) {
        if (!mySetMap.containsKey(e)) {
            return false;
        }
        final int value = mySetMap.get(e);
        mySize -= value;
        mySetMap.remove(e);
        return true;
    }

    @Override
    public int count(Object e) {
        if (!mySetMap.containsKey(e)) {
            return 0;
        }
        return mySetMap.get(e);
    }

    @Override
    public boolean contains(Object obj) {
        return mySetMap.containsKey(obj);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object e: c) {
            if (!mySetMap.containsKey(e)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Object[] toArray() {
        final Object[] resultArray = new Object[mySize];

        int curPosition = 0;
        for (Map.Entry<Object, Integer> entry: mySetMap.entrySet()) {
            final Object obj = entry.getKey();
            final int  count = entry.getValue();

            for (int i = 0; i < count; i++) {
                resultArray[i + curPosition] = obj;
            }

            curPosition += count;
        }

        return resultArray;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if (a == null) {
            throw new NullPointerException();
        }

        T[] resultArray = a;
        if (resultArray.length < mySize) {
            resultArray = (T[]) Array.newInstance(a.getClass().getComponentType(), mySize);
        } else {
            for (int i = mySize; i < resultArray.length; i++) {
                resultArray[i] = null;
            }
        }

        try {
            int curPosition = 0;
            for (Map.Entry<Object, Integer> entry: mySetMap.entrySet()) {
                final Object obj = entry.getKey();
                final int  count = entry.getValue();

                for (int i = 0; i < count; i++) {
                    resultArray[i + curPosition] = (T) obj;
                }

                curPosition += count;
            }
        } catch(ClassCastException e) {
            throw new ArrayStoreException();
        }

        return resultArray;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean flag = false;
        for (Object e: c) {
            if (removeAllElementOccurrences(e)) {
                flag = true;
            }
        }
        return flag;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean flag = false;

        final Map<Object, Integer> newMap = initMap();
        int newSize = 0;

        for (Object e: c) {
            if (mySetMap.containsKey(e) && !newMap.containsKey(e)) {
                final int value = mySetMap.get(e);
                newSize += value;
                newMap.put(e, value);
            }
        }

        if (newSize < mySize) {
            flag = true;
        }

        mySetMap = newMap;
        mySize   = newSize;

        return flag;
    }

    @Override
    public void clear() {
        mySize = 0;
        mySetMap.clear();
    }

    @Override
    public int size() {
        return mySize;
    }

    private Map<Object, Integer> initMap() {
        return new HashMap<Object, Integer>();
    }

    Map<Object, Integer> mySetMap;
    Integer mySize;
}
