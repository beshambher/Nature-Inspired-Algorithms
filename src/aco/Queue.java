/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aco;

import java.util.LinkedList;

/**
 *
 * @author bishu
 */
public class Queue {

    private LinkedList list;

    public Queue() {
        list = new LinkedList();
    }

    public boolean isEmpty() {
        return (list.size() == 0);
    }
    
    public boolean empty() {
        return (list.size() == 0);
    }

    public void add(Object item) {
        list.add(item);
    }

    public int remove() {
        int item = (Integer) list.get(0);
        System.out.println("Item to be remove: "+item);
        list.remove(0);
        return item;
    }
    
    public int pop() {
        int item = (Integer) list.get(0);
        System.out.println("Item to be remove: "+item);
        list.remove(0);
        return item;
    }

    public void print() {
        System.out.print("Size: " + list.size() + " Elements: ");
        for (int i = 0; i < list.size(); i++) {
            System.out.print(list.get(i) + ", ");
        }
        System.out.println("");
    }
}
