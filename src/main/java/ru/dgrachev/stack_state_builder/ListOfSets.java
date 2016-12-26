package ru.dgrachev.stack_state_builder;

import java.util.*;

/**
 * Created by OTBA}|{HbIu` on 22.12.16.
 */
public class ListOfSets<T> extends AbstractSet<T> implements Set<T>{
    private final List<Set<T>> setsList;
    private long size;
    private int threshOld;
    private final static int DEFAULT_THRESHOLD=1000000;

    public ListOfSets() {
        this(DEFAULT_THRESHOLD);
    }

    public ListOfSets(final int threshOld) {
        this.threshOld=threshOld;
        this.size= 0L;
        this.setsList =new ArrayList<>(new HashSet<>());

    }

    @Override
    public int size() {
        return (int)size;
    }

    @Override
    public boolean isEmpty() {
        return size==0;
    }

    @Override
    public boolean contains(Object o) {
        for (Set set:setsList){
            if (set.contains(o))
                return true;
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            @Override
            public boolean hasNext() {
                return setsList.iterator().hasNext()
                        && setsList.iterator().next().iterator().hasNext();
            }

            @Override
            public T next() {
                return setsList.iterator().next().iterator().next();
            }
        };
    }

    @Override
    public boolean add(T t) {
        if (!contains(t)) {
            if (setsList.size()==0
                    || size/setsList.size()>=threshOld){
                setsList.add(new HashSet<T>());
                return setsList.get(setsList.size()-1).add(t);
            }
            return setsList.get(setsList.size() - 1).add(t);
        }
        return false;
    }

    @Override
    public boolean remove(Object o) {
        for (Set<T> s:setsList){
            if (s.remove(o))
                return true;
        }
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        boolean flag=false;
        for (T t:c){
            if (add(t))
                flag=true;
        }
        return flag;
    }
}
