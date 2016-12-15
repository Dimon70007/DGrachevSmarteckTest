package ru.dgrachev.state_builder;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by OTBA}|{HbIu` on 15.12.16.
 */
public class StatesBuilder {
    private final int colorsCount;
//    String delimeter="";

    /**
     * @param colorsCount-количество цветов, необходимое для генерации
     */
    public StatesBuilder(int colorsCount) {
        this.colorsCount = colorsCount;
    }

    /**
     *пробегаемся по всем ячейкам массива, создаем варианты массивов с ячейкой, увеличенной на 1
     * от входного массива и каждую новую итерацию добавляем ноду с новым состоянием, рекурсивно вызывая
     * метод build для построения новых вариантов состояний, зависящих от передаваемого
     * есть еще желание заменить тройной for на что-то рекурсивное, однако боюсь усложнять читаемость кода
     * @param state - текущее состояние node-иконка
     * @param node - объект, обладающий состоянием и множеством разновидностей состояний
     * @return множество node,содержащих множество node, содержащих множество...
     */
    public Set<Node> build(final int[][] state, final Node node){
        Set<Node> result=new HashSet<>();
        //добавил на всякий случай... в реальности мы не должны вызвать
        if (state==null)
            return result;

        for (int y = 0; y < state.length; y++) {
            for (int x = 0; x < state[y].length; x++) {
                int[][] nextState=incrementState(x,y,state);

//                if (nextState!=null)
//                    System.out.println(Arrays.deepToString(nextState));
//                delimeter+="      ";
                //после цепочки рекурсивных вызовов на нулевом шаге
                // - получим цепочку вызовов, отличающихся на 1 в интервале от [0:colorsCount)
                // и провалимся на следующую итерацию, не вернув при этом результат...
                //есть вероятность что при большом colorsCount и длине state - закончится память или стек)))
                if (nextState!=null)
                    result.add(new Node(build(nextState,node),nextState));
//                delimeter="";
            }
        }
        return result;
    }

    /**
     *
     * @param x -индех состояния по х
     * @param y - индекс состояния по у
     * @param state - текущее состояние
     * @return состояние (массив), отличающееся от текущего элементом state[x][y], увеличенным на 1
     *  если дальнейшее увеличение текущего элемента >=colorsCount
     */
    private int[][] incrementState(int x, int y, int[][] state) {
        int[][] nextState = new int[state.length][];
        for (int i = 0; i < state.length; i++) {
            nextState[i] = Arrays.copyOf(state[i], state[i].length);
        }
        if (nextState[x][y] < colorsCount-1) {
            nextState[x][y]++;
            return nextState;
        }
        return null;
    }
}
