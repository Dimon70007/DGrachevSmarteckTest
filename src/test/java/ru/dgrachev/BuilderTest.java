package ru.dgrachev;

import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

import static org.junit.Assert.assertTrue;
import static ru.dgrachev.NodeTest.*;
import static ru.dgrachev.NodeHelper.*;
import static ru.dgrachev.Params.*;

/**
 * Created by OTBA}|{HbIu` on 12.03.17.
 */
public class BuilderTest {

    Set<Node> allChildren;
    Node rootNode;
    int expectedSize;
    @Before
    public void setUp() throws Exception {
        int[][] zeroArr=zeroArrCreator(X_LENGTH,Y_LENGTH);
        rootNode=new Node(zeroArr,COLORS);
        Builder builder=new Builder(rootNode,5);
        builder.compute();
        allChildren=new HashSet<>();
        allChildren.addAll(treeToCollection(rootNode));
        expectedSize=(int)Math.pow(COLORS,X_LENGTH*Y_LENGTH);
    }

    @Test
    public void computeTest() throws Exception {
        int actualSize=allChildren.size();
        assertTrue(actualSize==expectedSize);
    }


    @Test
    public void computeParallelTest() throws Exception {

        assertTrue(testNG(10)==10);
    }

    private int testNG(final int countOfRightResult){
        Node rootNode= taskStarter();

        int countOfRightResults=1;
        Collection<Node> listNodes=NodeHelper.treeToCollection(rootNode);
        while (listNodes.size()==expectedSize) {
            Set<Node> uniqNodes=new HashSet<>();
            uniqNodes.addAll(listNodes);
            assertTrue(uniqNodes.size()==expectedSize);

//            System.out.println(Arrays.asList("listNodes.size()="
//            ,nodesCount," expectedSize=",(expectedSize)
//            ," uniqNodes.size()="
//            ,uniqNodes.size()).toString());
            rootNode=taskStarter();
            listNodes=NodeHelper.treeToCollection(rootNode);
            countOfRightResults++;
            if (countOfRightResults==countOfRightResult)
                break;

        }
//        Iterator<Node> iterator=listNodes.iterator();
//        try {
//            for (int i = 0; i < statesCount; i++) {
//                int [][] tmp=iterator.next().get2DArr(xLength);
//                System.out.println(Arrays.deepToString(tmp));
//            }
//        }catch (NoSuchElementException e){}
        return countOfRightResults;
    }

     Node taskStarter(){
        final ForkJoinPool pool=new ForkJoinPool();
        final Node rootNode=new Node(zeroArrCreator(xLength, yLength), colors);
        final Builder builder=new Builder(
                rootNode
                , 0
        );
        final ForkJoinTask<Node> rootTask=builder.fork();
        pool.submit(rootTask);
        final Node rootNode2=rootTask.join();
//        System.out.println("tree of States has been build");
        assertTrue(rootNode.equals(rootNode2));
        return rootNode2;
    }
}