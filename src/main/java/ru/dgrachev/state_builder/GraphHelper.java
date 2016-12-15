package ru.dgrachev.state_builder;

/**
 * Created by OTBA}|{HbIu` on 15.12.16.
 */
public class GraphHelper {

    public static int printNodesCount(Node root,int acc) {

        for (Node node: root.getNextStates()){
            acc+=printNodesCount(node,acc+1);
        }
            System.out.println(acc);
        return acc;

    }
}
