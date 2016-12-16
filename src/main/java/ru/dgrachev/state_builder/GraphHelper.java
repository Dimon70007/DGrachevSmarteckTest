package ru.dgrachev.state_builder;

import java.util.Set;

/**
 * Created by OTBA}|{HbIu` on 15.12.16.
 */
public class GraphHelper {

    public static int printNodesCount(Node root,int acc) {

//        acc+=root.getNextNodes().size();
        for (Node node:root.getNextNodes())
            printNodesCount(node,acc+1);
        System.out.println(acc);

        return acc;
//        return 0;
    }

    public static Set<Node> selectNode(Node root,Set<Node> acc){
        int size=acc.size();
        acc.add(root);
        for (Node node:root.getNextNodes()){
            acc.addAll(selectNode(node,acc));
        }
        if (size<acc.size())
            System.out.println(acc.size());
        return acc;
//        return null;
    }
}
