/**
 Autoren:
 Abdussamed Barak, Devid Khan, Onur Aslan
 */

package de.hawhamburg.hamann.ad.trees.impl;

public class Node<K,E> {
    Node<K,E> links;
    Node<K,E> rechts;
    K k;
    E e;

    public Node(K k, E e) {
        this.k = k;
        this.e = e;
    }

}
