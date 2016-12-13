package ru.dgrachev;

import java.util.Arrays;

/**
 * Created by OTBA}|{HbIu` on 13.12.16.
 */
public class Node implements INode<Integer[][]> {

    private final INode previous;
    private final int xLimit;
    private final int yLimit;
    private final int colorLimit;

    private Integer[][] state;

    public Node(INode previous,int xLimit,int yLimit,int colorLimit) {
        this.xLimit=xLimit;
        this.yLimit=yLimit;
        this.colorLimit=colorLimit;
        this.previous=previous;
        if(previous==null){
            createBeginState(xLimit,yLimit);
        }else
            state= incrementState((Integer[][])previous.getState());
    }

    private Integer[][] incrementState(Integer[][] state) {
        for (int y = 0; y< state.length; y++){
            for (int x = 0; x< state[y].length; x++){
                if(state[x][y]<(colorLimit-1) ){
                    Integer[][] nextState= Arrays.copyOf(state, state.length);
                    nextState[x][y]++;
                    return nextState;
                }
            }
        }
        return null;
    }

    private void createBeginState(int xLimit, int yLimit) {
        state=new Integer[xLimit][yLimit];
        for (int y = 0; y < yLimit; y++) {
            Arrays.fill(state[y],0);
        }
    }


    @Override
    public INode<Integer[][]> getNextNode() {
        INode<Integer[][]> inode= new Node(this,xLimit,yLimit,colorLimit);
        if (inode.getState()==null)
            return null;
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
}
