package ru.dgrachev;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * Created by OTBA}|{HbIu` on 08.02.17.
 */
public class Builder extends RecursiveTask<Node> {
    private final Node currentNode;
    private final int deepLevel;
    private final static int DEFAULT_DEEP_LEVEL=3;

    public Builder(
              final Node currentNode
              , final int deepLevel
                ) {
        this.currentNode=currentNode;
        this.deepLevel=deepLevel;
    }

    @Override
    public Node compute() {
        final List<ForkJoinTask<Node>> tasks=new ArrayList<>();
        if (currentNode.isFinal()){
            return currentNode;//тут без разницы что возвращать так как мы строим дерево состояний
        };
        final Collection<Node> children=currentNode.createChildren();

//        System.out.println("curentNodeChildrenSize="+currentNode.getChildren().size());
        for (Node child:children) {
            final Builder builder = new Builder(
                    child
                    , deepLevel + 1
            );

            if (isAsync()) {
                tasks.add(builder.fork());
            } else {
                builder.compute();
            }
        }

        if (!tasks.isEmpty()){
            for (ForkJoinTask<Node> task: tasks) {
                task.join();
            }
        }

        return currentNode;
    }

    private boolean isAsync() {
        return deepLevel <= DEFAULT_DEEP_LEVEL;
    }
}
