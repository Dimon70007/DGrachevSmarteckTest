package ru.dgrachev;

import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

import static ru.dgrachev.Params.*;

/**
 * Created by OTBA}|{HbIu` on 12.03.17.
 */
public class BuilderTest {

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void compute() throws Exception {

    }


    private static int testNG(){
        Node rootNode= taskStarter();

        int countOfRightResults=1;
        final int expectedSize=(int)Math.pow(colors, xLength * yLength);
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
//            System.out.println("count of right results="+countOfRightResults);

        System.out.println(Arrays.asList("listNodes.size()=",nodesCount," expectedSize=",(expectedSize) ," uniqNodes.size()=" ,uniqNodes.size()).toString());
//            listNodes=taskStarter();
//
//            countOfRightResults++;
//            if (countOfRightResults>10)
//                break;
//
//        }
        assert(expectedSize==nodesCount);
        Iterator<Node> iterator=listNodes.iterator();
        try {
            for (int i = 0; i < statesCount; i++) {
                int [][] tmp=iterator.next().get2DArr(xLength);
                System.out.println(Arrays.deepToString(tmp));
            }
        }catch (NoSuchElementException e){}

        return countOfRightResults;
    }

    static Node taskStarter(){
        final ForkJoinPool pool=new ForkJoinPool();
        final Node rootNode=new Node(arrCreator(xLength, yLength), colors);
        final Builder builder=new Builder(
                rootNode
                , 0
        );
        final ForkJoinTask<Node> rootTask=builder.fork();
        pool.submit(rootTask);
        final Node rootNode2=rootTask.join();
//        System.out.println("tree of States has been build");
        assert(rootNode.equals(rootNode2));
        return rootNode2;
    }

    static int [][] arrCreator(final int xLength, final int yLength){
        int[][] result=new int[xLength][yLength];
        for (int i = 0; i < xLength; i++) {
            Arrays.fill(result[i],0);
        }
        return result;
    }
}