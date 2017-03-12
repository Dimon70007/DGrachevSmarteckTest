package ru.dgrachev;

import java.awt.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * Created by OTBA}|{HbIu` on 08.02.17.
 */
public class NodeHelper {

    public static int convert2DTo1D(final Point p, final int sizeX){
        return p.y==0?p.x:p.y*sizeX+p.x;
    }

    public static int[] convertMatrixToVector(final int [][] arr) {
        final int sizeX =arr.length;
        final int sizeY =arr[sizeX-1].length;
        int[] result=new int[sizeY*sizeX];
        for (int y = 0; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++) {
                result[NodeHelper.convert2DTo1D(
                        new Point(x,y),sizeX)]
                        =arr[x][y];
            }
        }
        return result;
    }

    public static int[][] convertVetorToMatrix(final int[] arr, final int sizeX){
        final int[][] result=new int[arr.length/sizeX][sizeX];
        for (int i = 0; i < arr.length; i++) {
            result[i/sizeX][i%sizeX]=arr[i];
        }
        return result;
    }

    public static Collection<Node> treeToSet(final Node rootNode){
        ForkJoinPool helperPool=new ForkJoinPool();
        final TreeToSetHelper helper=new TreeToSetHelper(rootNode,0);
        final ForkJoinTask<Collection<Node>> helperTask=helper.fork();
        helperPool.submit(helperTask);
        final Collection<Node> nodes=new ArrayList<>();
        nodes.add(rootNode);
        nodes.addAll(helperTask.join());
        return nodes;
    }

    private static class TreeToSetHelper extends RecursiveTask<Collection<Node>> {
        private final Node currentNode;
        private final int deepLevel;
        private final static int DEFAULT_DEEP_LEVEL=3;
//        private static volatile int childrenCount=0;

        public TreeToSetHelper(final Node currentNode, final int deepLevel) {
            this.currentNode=currentNode;
            this.deepLevel=deepLevel;
        }

        @Override
        public  Collection<Node> compute() {
            if (!currentNode.hasChildren()){
                Collection<Node> nodes=new ArrayList<>();
//                nodes.add(currentNode);
                return nodes;
            }
            final Deque<ForkJoinTask<Collection<Node>>> tasks=new ArrayDeque<>();
            final Collection<Node> children=new ArrayList<>();

            children.addAll(currentNode.getChildren());
//                childrenCount+=children.size();
            final Collection<Node> otherChildren=new ArrayList<>();
//            otherChildren.add(currentNode);
            for (Node child:children) {
                final TreeToSetHelper helper =
                        new TreeToSetHelper(child, deepLevel + 1);
                if (isAsync()) {
                    tasks.push(helper.fork());
                } else {
                    otherChildren.addAll(helper.compute());
                }
            }

            if (!tasks.isEmpty()){
                for (ForkJoinTask<Collection<Node>> task:tasks) {
                    otherChildren.addAll(task.join());
                }

            }
            children.addAll(otherChildren);
//            if (childrenCount>50000)
//                System.out.println("childrenCount="+childrenCount);
            return children;
        }

        private boolean isAsync() {
            return deepLevel < DEFAULT_DEEP_LEVEL;
        }
    }
}
