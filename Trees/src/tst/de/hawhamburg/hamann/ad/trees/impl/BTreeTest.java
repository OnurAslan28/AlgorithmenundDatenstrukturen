package de.hawhamburg.hamann.ad.trees.impl;

import de.hawhamburg.hamann.ad.trees.BinaryTree;
import org.junit.jupiter.api.Test;


import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;

public class BTreeTest {
    @Test
    void getData(){
        BTree<Integer> tree = new BTree<>(4);
        tree.links = new BTree<>(2);
        tree.rechts = new BTree<>(8);
        assertEquals(tree.getData(),4);
        assertEquals(tree.links.getData(),2);
        assertEquals(tree.rechts.getData(),8);
    }

    @Test
    void getNode(){
        BTree<Integer> tree = new BTree<>(4);
        tree.links = new BTree<>(2);
        tree.rechts = new BTree<>(8);
        assertEquals(tree.getRightNode(),tree.rechts);
        assertEquals(tree.getLeftNode(),tree.links);
    }

    @Test
    void isLeaf(){
        BTree<Integer> tree = new BTree<>(4);
        tree.links = new BTree<>(2);
        tree.rechts = new BTree<>(8);
        tree.rechts.rechts = new BTree<>(10);
        assertFalse(tree.rechts.isLeaf());
        assertTrue(tree.links.isLeaf());
        assertFalse(tree.isLeaf());
        assertTrue(tree.rechts.rechts.isLeaf());
    }

    @Test
    void traversierung(){
        BTree<Integer> tree = new BTree(55);
        tree.rechts = new BTree<>(73);
        tree.rechts.rechts = new BTree<>(92);
        tree.links = new BTree<>(13);
        tree.links.rechts = new BTree<>(15);
        tree.links.links = new BTree<>(9);

        Consumer<BinaryTree<Integer>> visitor = x -> System.out.print(x.getData()+" ");
        System.out.print("InOrder Test:\nerwartet: 9 13 15 55 73 92\naktuell:   ");
        tree.visitInOrder(visitor);
        System.out.print("\nPreOrder Test:\nerwartet: 55 13 9 15 73 92 \naktuell:   ");
        tree.visitPreOrder(visitor);
        System.out.print("\nPostOrder Test:\nerwartet: 9 15 13 92 73 55 \naktuell:   ");
        tree.visitPostOrder(visitor);
    }


}
