/**
 Autoren:
 Abdussamed Barak, Devid Khan, Onur Aslan
 */


package de.hawhamburg.hamann.ad.trees.impl;
import de.hawhamburg.hamann.ad.trees.BinarySearchTree;
import java.util.NoSuchElementException;

public class BSTree<K extends Comparable<K>,E> implements BinarySearchTree<K,E> {
    Node<K,E> wurzel;
    public int sum = 0;
    public int counter;

    @Override
    public void insert(K k, E e) {
        wurzel = insertR(wurzel,k,e);
    }

    private Node<K,E> insertR(Node<K,E> node, K k, E e){
        if(node == null) return new Node<>(k,e);
        if(node.k.compareTo(k) > 0){
            node.links = insertR(node.links, k, e);
        }
        else if(node.k.compareTo(k) < 0){
            node.rechts = insertR(node.rechts, k, e);
        }
        counter++;
        return node;
    }

    @Override
    public void remove(K k) throws NoSuchElementException {
        if(!contains(k)) throw new NoSuchElementException();
        else{
            wurzel = removeR(wurzel,k);
        }
    }

    private Node removeR(Node<K,E> node,K k){
        if(node == null) return node;
        if(node.k.compareTo(k) > 0){
            node.links = removeR(node.links,k);
        }else if(node.k.compareTo(k) < 0){
            node.rechts = removeR(node.rechts,k);
        }
        else {
            if(node.links == null){
                return node.rechts;
            }
            if(node.rechts == null) {
                return node.links;
            }
            if(node.rechts != null){
                node.k = anhaenger(node);
                node.rechts = removeR(node.rechts,k);
            }else {
                node.k = eltern(node);
                node.links = removeR(node.links,k);
            }
        }
        return node;
    }


    @Override
    public E get(K k) throws NoSuchElementException {
        if(!contains(k)) throw new NoSuchElementException();
        else {
            return getR(wurzel,k);
        }
    }

    private E getR (Node<K,E> n,K k){

        if(n.k.compareTo(k) == 0)
            return n.e;

        if(n.k.compareTo(k) > 0){
            return getR(n.links,k);
        }else{
            return getR(n.rechts,k);
        }
    }

    @Override
    public int size() {
        return sizeR(wurzel);
    }

    private int sizeR(Node<K,E> node){
        if(node==null){
            return 0;
        }
        int linksgroesse = sizeR(node.links);
        int rechtsgroesse = sizeR(node.rechts);
        int gesamtgroesse = linksgroesse + rechtsgroesse + 1;
        return gesamtgroesse;
    }

    @Override
    public boolean contains(K k) {
        return containsR(wurzel,k);
    }

    private boolean containsR(Node<K,E> n,K k){
        if (n == null) return false;

        if(n.k.compareTo(k) == 0) return true;

        if(n.k.compareTo(k) > 0){
            return containsR(n.links,k);
        }else{
            return containsR(n.rechts,k);
        }
    }

    private K anhaenger(Node<K,E> n){
        n = n.rechts;
        while(n.links != null){
            n = n.links;
        }
        return n.k;
    }
    private K eltern(Node<K,E> n){
        n = n.links;
        while(n.rechts != null){
            n = n.rechts;
        }
        return n.k;
    }




}
