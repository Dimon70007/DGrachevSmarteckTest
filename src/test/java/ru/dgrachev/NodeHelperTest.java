package ru.dgrachev;

import org.junit.Test;

import java.awt.*;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertTrue;
import static ru.dgrachev.BuilderTest.EXPECTED_SIZE;
import static ru.dgrachev.NodeHelper.*;
import static ru.dgrachev.NodeTest.X_LENGTH;
import static ru.dgrachev.NodeTest.Y_LENGTH;

/**
 * Created by OTBA}|{HbIu` on 12.03.17.
 */
public class NodeHelperTest {
    @Test
    public void convert2DTo1DTest() throws Exception {
        int coordinate =0;
        for (int y = 0; y < Y_LENGTH; y++) {
            for (int x = 0; x < X_LENGTH; x++) {
                assertTrue(convert2DTo1D(new Point(x,y),X_LENGTH)==coordinate);
                coordinate++;
            }
        }
    }

    @Test
    public void convertVectorToMatrixToVectorTest() throws Exception {
        int[] expected={0,1,2,3,4,5,6,7,8};
        int[][] actualMatrix=convertVetorToMatrix(expected,X_LENGTH);
        int[] actualVector=convertMatrixToVector(actualMatrix);
        assertTrue(Arrays.equals(actualVector,expected));
    }


    @Test
    public void treeToCollectionTest() throws Exception {
        Node rootNode=BuilderTest.taskStarter();
        Collection<Node> nodes=treeToCollection(rootNode);
        int actualSize=nodes.size();
//        System.out.println(actualSize);
        assertTrue(actualSize==EXPECTED_SIZE);
    }

}