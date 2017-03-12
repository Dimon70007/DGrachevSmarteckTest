package ru.dgrachev;

import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

/**
 * Created by OTBA}|{HbIu` on 09.02.17.
 */
public class Main {
    //задание:
    //сгенерировать все возможные массивы указанного размера (X_LENGTH,Y_LENGTH)с указанным количеством
    //состояний (COLORS) для каждой ячейки массива
    private static final int X_LENGTH=3;
    private static final int Y_LENGTH=3;
    private static final int COLORS=4;



    public static void main(String... args) throws Exception {
        final int countOfLaunch=10;
        double averageCountOfResults=0;
//        for (int i = 0; i < countOfLaunch; i++) {
            averageCountOfResults+=testNG();
//            System.out.println("averageCountOfResults="+averageCountOfResults);
//        }
//        averageCountOfResults=averageCountOfResults/countOfLaunch;
//        System.out.println("averageCountOfResults="+averageCountOfResults);
//         приблизительно в 20% случаях программа дает неправильный результат -
//        это следствие того что CopyOnWriteArraySet не дает 100% гарантию,
//         что не будет дублей при частой записи с нескольких потоков
//        у меня решается дополнительным однопоточным прогоном через HashSet
//        вместо этого можно было бы выбрать ConcurrentHashMap и методы типа putIfAbscent
    }

    private static int testNG(){
        Node rootNode=testStarter();

        int countOfRightResults=1;
        final int expectedSize=(int)Math.pow(COLORS,X_LENGTH*Y_LENGTH);
//        while (listNodes.size()==expectedSize) {
        int nodesCount=0;
        Collection<Node> listNodes=NodeHelper.treeToSet(rootNode);
        Set<Node> uniqNodes=new HashSet<>();
//        uniqNodes.addAll(listNodes);// убрал чтоб лишний проход не делать
        for (Node node : listNodes
             ) {
            uniqNodes.add(node);//а тут добавил
            nodesCount++;
        }
            System.out.println("count of right results="+countOfRightResults);

        System.out.println(Arrays.asList("listNodes.size()=",nodesCount," expectedSize=",(expectedSize) ," uniqNodes.size()=" ,uniqNodes.size()).toString());
//            listNodes=testStarter();
//
//            countOfRightResults++;
//            if (countOfRightResults>10)
//                break;
//
//        }
        assert(expectedSize==nodesCount);
        Iterator<Node> iterator=listNodes.iterator();
        try {

            for (int i = 0; i < 20; i++) {
                int [][] tmp=iterator.next().get2DArr(X_LENGTH);
                System.out.println(Arrays.deepToString(tmp));
            }
        }catch (NoSuchElementException e){}
        return countOfRightResults;
    }

    public static Node testStarter(){
        final ForkJoinPool pool=new ForkJoinPool();
        final Node rootNode=new Node(arrCreator(X_LENGTH,Y_LENGTH),COLORS);
        final Builder builder=new Builder(
                rootNode
                , 0
        );
        final ForkJoinTask<Node> rootTask=builder.fork();
        pool.submit(rootTask);
        final Node rootNode2=rootTask.join();
        System.out.println("phase1 ends");
        assert(rootNode.equals(rootNode2));
//
//        final Set<Node> nodes=new HashSet<>();
//        nodes.addAll(NodeHelper.treeToSet(rootNode));
//        System.out.println("phase2 ends");
        return rootNode2;
    }

    public static int [][] arrCreator(final int xLength, final int yLength){
        int[][] result=new int[xLength][yLength];
        for (int i = 0; i < xLength; i++) {
            Arrays.fill(result[i],0);
        }
        return result;
    }

}
