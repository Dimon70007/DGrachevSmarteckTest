package ru.dgrachev.state_builder;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by OTBA}|{HbIu` on 15.12.16.
 */
public class StatesBuilder {
    private final int colorsCount;

    /**
     * @param colorsCount-количество цветов, необходимое для генерации
     */
    public StatesBuilder(int colorsCount) {
        this.colorsCount = colorsCount;

    }

    /**
     * генерим множество нод, следующих для текущей ноды, и запихиваем их в ноду,
     * потом для каждой ноды рекурсивно вызываем build
     * @param node- текущая нода
     * @return множество node,содержащих множество node, содержащих множество...
     */
    public Node build(final Node node){
        int[][] state=node.getState();
        node.addNodes(getIncrementStates(state));

        for (Node n:node.getNextNodes()){
            build(n);
        }
        return node;
    }

    /**
     *
     * @param currentState - текущее состояние (массив), от которого генерируется множество состояний, отличающихся только
     * одним элементом на 1
     * @return множество состояний (массивов), если дальнейшее увеличение текущего
     * элемента >=colorsCount - возвращается пустое множество (не null)
     */
    private Set<Node> getIncrementStates(int[][] currentState){
        Set<Node> incrementedStates=new HashSet<>();
        for (int x = 0; x < currentState.length; x++) {
            for (int y = 0; y < currentState[x].length; y++) {
                if (currentState[x][y] < colorsCount - 1) {
                    currentState[x][y]++;
                    incrementedStates.add(new Node(arayDeepCopy(currentState)));
                    currentState[x][y]--;
                }
            }
        }
        return incrementedStates;
    }

    private int[][] arayDeepCopy(int[][] sourceAr){
        int [][] targetArr=new int[sourceAr.length][];
        for (int i = 0; i < sourceAr.length; i++) {
            targetArr[i]= Arrays.copyOf(sourceAr[i],sourceAr[i].length);
        }
        return targetArr;
    }
}
