package ru.dgrachev;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Created by OTBA}|{HbIu` on 13.12.16.
 */
public class Node implements INode<Integer[][]> {
    private final Random random =new Random();
    private final INode previous;
    final int xLimit;
    final int yLimit;
    final int colorLimit;
    private final Set<Node> children;

    private Integer[][] state;

    public Node(int xLimit,int yLimit,int colorLimit) {
        this.xLimit=xLimit;
        this.yLimit=yLimit;
        this.colorLimit=colorLimit;
        this.previous=null;
        createBeginState(xLimit,yLimit);
        children = new HashSet<>();
    }

    public Node(Node previous) {
        this.previous = previous;
        this.xLimit = previous.xLimit;
        this.yLimit = previous.yLimit;
        this.colorLimit = previous.colorLimit;
//        this.state = buildStates((Integer[][])previous.getState());
        children = new HashSet<>();
    }
    //так как вероятность создания одинаковых иконок мала-можно генерировать их очень долго
    private Set<Integer[][]> buildStates (Integer[][] state) {
        Set<Integer[][]> states=new HashSet<>();
//        int i=0;
//                while(true) {
        for (int y = 0; y < state.length; y++) {
            for (int x = 0; x < state[y].length; x++) {
                int color = Math.abs(random.nextInt() % colorLimit);
//                    if (state[x][y] < (colorLimit)) {
                Integer[][] nextState = Arrays.copyOf(state, state.length);
                if (nextState[x][y] == color)
                    nextState[x][y] = Integer.hashCode(color) % colorLimit;
                else
                    nextState[x][y] = color;
                states.add(nextState);
            }
        }
        return states.size()==0?null:states;
    }

    private void createBeginState(int xLimit, int yLimit) {
        state=new Integer[xLimit][yLimit];
        for (int y = 0; y < yLimit; y++) {
            Arrays.fill(state[y],0);
        }
    }


    @Override
    public INode<Integer[][]> getNextNode() {
        INode<Integer[][]> inode= new Node(this);
        if (inode.getState()==null)
            return null;
        System.out.println(inode);
        return inode;
    }

    @Override
    public INode<Integer[][]> getPreviousNode() {
        return previous;
    }

    @Override
    public Integer[][] getState() {
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
}
