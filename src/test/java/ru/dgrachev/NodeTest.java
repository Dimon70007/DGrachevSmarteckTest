package ru.dgrachev;

import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;
import static ru.dgrachev.NodeHelper.zeroArrCreator;

/**
 * Created by OTBA}|{HbIu` on 12.03.17.
 */
public class NodeTest {
    Node rootNode;
    int expectedCount;
    List<Node> childrenNodes;
    int [][] expectedArr;

    final static int X_LENGTH =3;
    final static int Y_LENGTH =3;
    final static int COLORS =3;

    @Before
    public void setUp() throws Exception {
        int[][] zeroArr= zeroArrCreator(X_LENGTH, Y_LENGTH);
        rootNode=new Node(zeroArr, COLORS);
        rootNode.createChildren();
        expectedCount=(COLORS -1)* X_LENGTH * Y_LENGTH;
        childrenNodes =(List<Node>) rootNode.getChildren();
        expectedArr= new int[][]{
                {1, 0, 0, 0, 0, 0, 0, 0, 0},
                {2, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 0, 0, 0, 0, 0, 0, 0},
                {0, 2, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 1, 0, 0, 0, 0, 0, 0},
                {0, 0, 2, 0, 0, 0, 0, 0, 0}
        };
    }

    @Test
    public void getCoordinateTest() throws Exception {
        assertTrue(rootNode.getCoordinate()==0);
    }

    @Test
    public void hasChildrenTest() throws Exception {
        assertTrue(rootNode.hasChildren());
    }

    @Test
    public void getChildrenTest() throws Exception {
        Collection<Node> children=rootNode.getChildren();
        assertTrue(children.size()==expectedCount);
    }

    @Test
    public void createChildrenTest() throws Exception {
        Set<Node> uniqueNodes=new HashSet<>();
        uniqueNodes.addAll(childrenNodes);
        assertTrue(uniqueNodes.size()==expectedCount);
        Iterator<Node> iterator=childrenNodes.iterator();
        for (int i = 0; i < expectedArr.length; i++) {
            int[] actualArr=iterator.next().getArr();
            int[] expected=expectedArr[i];
            assertTrue(Arrays.equals(actualArr,expected));
        }

    }

    @Test
    public void getParentNodeTest() throws Exception {
        Node child=rootNode.getChildren().iterator().next();
        assertEquals(child.getParentNode(),rootNode);
        assertEquals(rootNode.getParentNode(),null);
    }

    @Test
    public void getArrTest() throws Exception {
        assertTrue(Arrays.equals(rootNode.getArr()
                ,new int[] {0,0,0,0,0,0,0,0,0}
        ));
    }

    @Test
    public void get2DArrTest() throws Exception {
        int [][] expected={
                {0,0,0},
                {0,0,0},
                {0,0,0}
        };
        int [][] actual=rootNode.get2DArr(X_LENGTH);
        assertTrue(Arrays.deepEquals(actual,expected));
    }

    @Test
    public void isFinalTest() throws Exception {
        Node finalChild=childrenNodes.get(childrenNodes.size()-1);
        Node firstChild=childrenNodes.get(0);
        assertTrue(finalChild.isFinal());
        assertFalse(firstChild.isFinal());
    }

    @Test
    public void hashCodeTest() throws Exception {
        int[][] expectedArr={{1, 0, 0}, {0, 0, 0}, {0, 0, 0}};
        Node actual=childrenNodes.get(0);
        Node expected=new Node(expectedArr, COLORS);
        assertTrue(actual.equals(expected));
    }

}