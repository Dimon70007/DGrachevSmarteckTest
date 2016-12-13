package ru.dgrachev;

/**
 * Created by OTBA}|{HbIu` on 13.12.16.
 */
public interface INode<T extends Object> {

    INode<T> getNextNode();
    INode<T> getPreviousNode();
    T getState();
}
