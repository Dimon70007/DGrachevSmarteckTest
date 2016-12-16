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
        Node root=createStates(3,3,3);
//        root.print();
        System.out.println(root.getCount(1));
        Set<Node> allNodes=GraphHelper.selectNode(root,new HashSet<>());
        System.out.println(allNodes.size());
//        int count=GraphHelper.printNodesCount(root,1);
//        System.out.println(count);
//        for (Node node :
//                normalizeNodeSet) {
//            System.out.println(node);
//        }
//        for (Node n:allNodes){
//            System.out.println(n);
//        }
//        System.out.println(allNodes.size());

    }

    public static Node createStates(int xLength,int yLength,int colors) {

        int[][] beginState=new int[xLength][yLength];
        for (int i = 0; i < xLength; i++) {
            Arrays.fill(beginState[i],0);
        }
        StatesBuilder statesBuilder=new StatesBuilder(colors);
        return statesBuilder.build(new Node(beginState));
    }
}
