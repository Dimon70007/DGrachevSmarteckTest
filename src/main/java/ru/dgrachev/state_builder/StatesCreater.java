package ru.dgrachev.state_builder;

import java.util.Arrays;

/**
 * Created by OTBA}|{HbIu` on 15.12.16.
 */
public class StatesCreater {
    public static void main(String[] args) {

        Node root=createStates(3,3,2);

    }

    public static Node createStates(int xLength,int yLength,int colors) {

        int[][] beginState=new int[xLength][yLength];
        for (int i = 0; i < xLength; i++) {
            Arrays.fill(beginState[i],0);
        }
        StatesBuilder statesBuilder=new StatesBuilder(colors);
        Node node=new Node(beginState);
        node.setNodes(statesBuilder.build(node.getState(),node));
        return node;
    }
}
