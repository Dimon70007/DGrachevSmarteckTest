package ru.dgrachev.state_builder;

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

    public Set<Node> getNextStates() {
        return nextStates;
    }

    public boolean setNodes(Set<Node> nodes){
        return nextStates.addAll(nodes);
    }

    public int[][] getState() {
        return state;
    }
}
