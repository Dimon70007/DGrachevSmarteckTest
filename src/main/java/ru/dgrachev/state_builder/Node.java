package ru.dgrachev.state_builder;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by OTBA}|{HbIu` on 15.12.16.
 */
public class Node {
    private final Set<Node> nextStates;
    private final int[][] state;

    /**
     * Node инкапсулирует в себе текущее состояние и множество следующих состояний
     * @param nextStates-множество следующих состояний
     * @param state - текущее состояние
     */
    public Node(final Set<Node> nextStates, final int[][] state) {
        this.nextStates = nextStates;
        this.state = state;
    }

    public Node(int[][] state) {
        this.nextStates=new HashSet<>();
        this.state = state;
    }

    public Set<Node> getNextNodes() {
        return nextStates;
    }

    public boolean addNodes(Set<Node> nodes){
        return nextStates.addAll(nodes);
    }

    public int[][] getState() {
        return state;
    }

    @Override
    public String toString() {
        return Arrays.deepToString(state);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node node = (Node) o;

        return Arrays.deepEquals(state, node.state);

    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(state);
    }

    public void print(){
        for (Node n:nextStates){
            n.print();
        }
        System.out.println(toString());
    }

    public int getCount(int acc){
        acc++;
        for (Node n:nextStates){
            acc+=n.getCount(acc);
        }
        return acc;
    }
}
