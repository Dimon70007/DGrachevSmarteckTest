package ru.dgrachev.state_builder;

import java.util.Set;

/**
 * Created by OTBA}|{HbIu` on 15.12.16.
 */
public class GraphHelper {

    public static int printNodesCount(Node root,int acc) {

        acc+=root.getNextNodes().size();
        if (root.getNextNodes().size()!=0)
            printNodesCount(root,acc+1);
        System.out.println(acc);
        return acc;

    }

    public static Set<Node> selectNode(Node root,Set<Node> acc){
        acc.add(root);
        for (Node node:root.getNextNodes()){
            selectNode(node,acc);
        }
        return acc;
    }
}
