package ru.dgrachev.stack_state_builder;

import java.util.Arrays;

/**
 * Created by OTBA}|{HbIu` on 15.12.16.
 */
public class Node{
     final int[][] state;
     final int nextStateNumber;

    /**
     * Node инкапсулирует в себе текущее состояние и цифру, управляющую циклом
     * @param state - текущее состояние
     * @param nextStateNumber - управляет циклом в зависимости от значения
     */
    public Node(int[][] state, int nextStateNumber) {
        this.nextStateNumber = nextStateNumber;
        this.state = state;
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
}
