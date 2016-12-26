package ru.dgrachev.stack_state_builder;

import java.util.Arrays;
import java.util.Set;

/**
 * Created by OTBA}|{HbIu` on 15.12.16.
 */
public class StatesCreater {
    static StatesBuilder statesBuilder;
    public static void main(String[] args) {
// смешно что падает не по StackOverFlow а - OutOfMemoryError
        Set<Node> nodes=createStates(5,5,2);
        for (Node node :
                nodes) {
            System.out.println(node);
        }
        System.out.println(statesBuilder.maxStackSize);

        System.out.println(nodes.size());
    }

    public static Set<Node> createStates(int xLength, int yLength, int colors) {

        int[][] beginState=new int[xLength][yLength];
        for (int i = 0; i < xLength; i++) {
            Arrays.fill(beginState[i],0);
        }
        statesBuilder=new StatesBuilder(colors);
        return statesBuilder.build(beginState);
    }
}
