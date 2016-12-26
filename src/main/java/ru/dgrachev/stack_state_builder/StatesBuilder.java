package ru.dgrachev.stack_state_builder;

import java.util.*;

/**
 * Created by OTBA}|{HbIu` on 15.12.16.
 */
public class StatesBuilder {
    private final int colorsCount;
    private int color;
    private int x;
    private int y;
    private final Set<Node> result;
    private final Deque<Node> theStack;
    private int stateNumber;
    private int[][] initArr;
    int maxStackSize;
    /**
     * @param colorsCount-количество цветов, необходимое для генерации
     */
    public StatesBuilder(int colorsCount) {
        this.colorsCount = colorsCount;
        result = new ListOfSets<>();
        theStack=new ArrayDeque<>();
        color=1;
        x=0;
        y=0;
    }

    /**
     * генерим множество нод, следующих для текущей ноды, и запихиваем их в ноду,
     * потом для каждой ноды рекурсивно вызываем build
     * @param initArr - массив, от которого начинается отсчет всех состояний
     * @return множество node,содержащих множество node, содержащих множество...
     */
    public Set<Node> build(int[][] initArr){
        stateNumber=1;
        this.initArr=initArr;
        while(true){
            if (statesCreated())
                break;
        }

        System.out.println(maxStackSize);
        return result;
    }

    private boolean statesCreated() {
        switch (stateNumber){

            case 0://enter in function
                Node node0=new Node(initArr,3);
                theStack.push(node0);
                result.add(node0);
                stateNumber=1;
                break;

            case 1:
                Set<Node> nextStates = getIncrementStates(initArr, 1);
                if (nextStates.size() != 0) {
                    for (Node n1 :
                            nextStates) {
                        theStack.push(n1);
                    }
                    maxStackSize=Math.max(maxStackSize,theStack.size());
                    result.addAll(nextStates);
                }
                stateNumber=2;
                break;
            case 2:
                Node n2=theStack.poll();
                if (n2==null){
                    return true;
                }
                initArr=n2.state;
                stateNumber=n2.nextStateNumber;
                break;
            case 3:
                return true;

        }
        return false;
    }


    /**
     *
     * @param currentState - текущее состояние (массив), от которого генерируется множество состояний, отличающихся только
     * одним элементом на 1
     * @param nextStateNumber - параметр, управляющий циклом: 1-запускаем заново, 3-выход
     * @return множество состояний (массивов), если дальнейшее увеличение текущего
     * элемента >=colorsCount - возвращается пустое множество (не null)
     */
    private Set<Node> getIncrementStates(int[][] currentState,int nextStateNumber){
        Set<Node> incrementedStates=new HashSet<>();
        for (int x = 0; x < currentState.length; x++) {
            for (int y = 0; y < currentState[x].length; y++) {
                if (currentState[x][y] < colorsCount - 1) {
                    currentState[x][y]++;
                    if (!result.contains(new Node(currentState,nextStateNumber)))
                        incrementedStates.add(
                                new Node(arayDeepCopy(currentState)
                                        , nextStateNumber));
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
