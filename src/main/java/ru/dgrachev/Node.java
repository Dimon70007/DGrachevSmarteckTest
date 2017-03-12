package ru.dgrachev;

import java.util.*;

/**
 * Created by OTBA}|{HbIu` on 08.02.17.
 */
public class Node implements Iterable<Node>{

    private final int[] arr;
    private final int lastElement;
    private final int colorsCount;
    private final int coordinate;
    private final Collection<Node> children;
    private final Node parentNode;
    private final int arrLength;

    public Node(final Node parentNode
            , final int[] arr
            , final int colorsCount
            , final int coordinate) {
        this.parentNode=parentNode;
        this.arr=arr;
        this.colorsCount=colorsCount;
        this.coordinate=coordinate;
        this.children=new ArrayList<>();
        lastElement = this.arr[this.arr.length-1];
        arrLength = this.arr.length;
    }

    public Node(final int[][] arr, final int colorsCount) {
        this.arr= NodeHelper
                .convertMatrixToVector(arr);
        this.coordinate=0;
        this.colorsCount=colorsCount;
        this.parentNode=null;
        this.children=new ArrayList<>();
        lastElement = this.arr[this.arr.length-1];
        arrLength = this.arr.length;
    }

    public int getCoordinate() {
        return coordinate;
    }

    public boolean hasChildren(){
        return !this.children.isEmpty();
    }

    public Collection<Node> getChildren() {
        return children;
    }

    public synchronized Collection<Node> createChildren() {
        if(!hasChildren()){
        //если мы все равно сначала генерируем все возможные состояния для первой ячейки
            //то для второй - нужно начинать генерацию...со второй, но что делать с первой...
            //какое значение в ней оставлять - скорее всего там будет максимальное значение
            // (как выяснилось позже решение - это дерево состояний, то есть у нас есть лес потомков,
            // у которых есть лес потомков, у которых... при этом с каждым уходом в глубину индекс
            // ячейк сдвигается на 1 и в конце мы получаем самое малое количество состояний, зависящее
            // от количества цветов)
            //и этот элемент трогать не надо, т.е. если на вход поступила координата 5 то мы вызываем
            //некст стэйт у координат больше 5 - эта логика должна быть в билдере
            //              0  0  0
            //       V         V          V
            //  0  0  1     0  1  0    1  0  0
            //     V   V
//        1  0  1    0  1  1
//           V           V
            // 1  1  1      1  1  1

            //    0  0
            //     V           V
            //1  0   2  0    0  1   0  2
            //  V
            //1  1   1  2
            //в общем будем генерировать множество потомков по одной координате с разными состояниями координаты
            // и потом у этих потомков брать следующую координату и генерить дальше, пока не окажется что
            //последняя координата = colorsCount-1
            this.children.addAll(getNextNodes(1,coordinate,new ArrayList<>()));
//            System.out.println(Arrays.toString(children.toArray())+"\n");
        }
        return this.children;
    }

    public Node getParentNode() {
        return parentNode;
    }

    public int[] getArr() {
        return arr;
    }

    public int[][] get2DArr(final int sizeX) {
        return NodeHelper.convertVetorToMatrix(arr,sizeX);
    }

    public boolean isFinal(){
        return this.coordinate==arrLength;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node node = (Node) o;

        return Arrays.equals(arr, node.arr);

    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(arr);
    }

    /*
    создаем множество нод изменяя значение ячейки с текущей координатой,
    а сохраняем в ноды-потомки следующую координату, полученную параметром
    * */
    private Collection<Node> getNextNodes(final int currentColor,final int currentCoordinate,final Collection<Node> acc){
        //если у нас нода с координатой ==arrLength у нее нечего генерить
        if (currentCoordinate==arrLength) {
//            System.out.println();
            return acc;
        }
        //если предыдущая проверка ==false значит у нас координата в пределах массива
        //и мы проверяем не вышли ли мы за границу цветов
        //если вышли сбрасываем цвет к начальному состоянию и увеличиваем координату
        if(currentColor==colorsCount) {
                return getNextNodes(1, currentCoordinate + 1, acc);
        }
        int[] nextArr = Arrays.copyOf(arr, arrLength);
        nextArr[currentCoordinate] = currentColor;//                           |
                                                        //all magic is here    V
        final Node nextNode = new Node(this, nextArr, colorsCount, currentCoordinate+1);
//        System.out.println(Arrays.toString(nextNode.arr));
        acc.add(nextNode);

        return getNextNodes(currentColor+1,currentCoordinate,acc);
    }

    @Override
    public Iterator<Node> iterator() {

        return new NodeIterator();
    }

    private class NodeIterator implements Iterator<Node>{
        private int deepLevel;
        Node next;        // next entry to return
        Node current;     // current entry
        Iterator<Node> iterator;
        Deque<Iterator<Node>> parentIterators;


        NodeIterator() {
            current = next = null;
            next=Node.this;
            iterator=children.iterator();
            parentIterators=new ArrayDeque<>();
            this.deepLevel=0;
        }

        public final boolean hasNext() {
            return iterator.hasNext();
        }

        public final Node next() {
            if (next==null)
                throw new NoSuchElementException();

            if (iterator.hasNext()){//iter on children
                current=next;
                Node childNode=iterator.next();
                if(childNode.iterator().hasNext()){
                    System.out.println("children.size()="+children.size());
                    deepLevel=parentIterators.size();
                    if(deepLevel>0 && deepLevel%100000==0)
                            System.out.println("deeplevel="+deepLevel);

                    parentIterators.push(iterator);
                    iterator=childNode.iterator();//go down in 1 level lower
                } else{
                    next=childNode;
                }
            } else{
                try {
                    iterator=parentIterators.pop();
//                    deepLevel--;
                }catch (NoSuchElementException e){
                    iterator=null;
                }
                next=iterator!=null? iterator.next(): null;
            }
            return current;
        }

    }
}
