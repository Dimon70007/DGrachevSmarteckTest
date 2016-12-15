package ru.dgrachev.state_builder;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by OTBA}|{HbIu` on 15.12.16.
 */
public class StatesCreater {
    public static void main(String[] args) {
// смешно что падает не по StackOverFlow а - OutOfMemoryError
        Node root=createStates(3,3,2);
        Set<Node> normalizeNodeSet=GraphHelper.selectNode(root,new HashSet<>());
//        for (Node node :
//                normalizeNodeSet) {
//            System.out.println(node);
//        }
        System.out.println(normalizeNodeSet.size());

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
