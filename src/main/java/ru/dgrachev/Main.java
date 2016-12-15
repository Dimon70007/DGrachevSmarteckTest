package ru.dgrachev;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by OTBA}|{HbIu` on 13.12.16.
 */
public class Main {
    //последовательно генерирует граф состояний (вариантов расцветки) и сохраняет их в список
    //нашел ошибку... у меня генерируется только состояния для 255 элемента, а должно - для каждого
    //как вариант можно реализовать компаратор или хэшкод и закидывать в set чтобы при вращении не было повторений
    //написал сам... в учебник не подсматривал. наверно в спокойной обстановке лучше думается
    //В общем если бы стояла задача чтобы сделать хоть как то худо-бедно генерацию цветов -
    // наверно сходу сделал бы рандомную с ограничением на нужное количество иконок
    public static void main(String[] args) {
        int xLength=3;
        int yLength=3;
        int colorLimit=2;
        int iconsLimit=Integer.MAX_VALUE/2;
        int maxNodes= (int) Math.pow(colorLimit, xLength * yLength);
        INode<Integer[][]> node=new Node(xLength,yLength,colorLimit);
        Set<INode<Integer[][]>> nodesSet=new HashSet<>();
        int count=0;
        for (int i=0,nodesCount = 0; i < iconsLimit && nodesCount<maxNodes; i++){
            boolean isAdded=nodesSet.add(node.getNextNode());
            if (!isAdded) {
                i--;
                count++;
            }else
                nodesCount++;
        }
        //количество лишних операций
        System.out.println("Лишних операций - "+count);
        //ну а реализовать структуру данных, которая хранила бы 256 в степени (32*32) иконок
        // наверно не позволяет отсутствие алгоритмической подготовки (хотя есть подозрение, что
        // задание было не для решения , а для проверки реакции)
        //Однако, спасибо за мотивацию к изучению алгоритмов. Сижу - читаю))
    }
}
