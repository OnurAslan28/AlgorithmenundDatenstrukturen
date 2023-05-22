package de.hawhamburg.hamann.ad.trees.impl;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class BSTreeTest {

    @Test
    void insert() {
        BSTree<String, String> bst = new BSTree<>();
        bst.insert("a", "Element 1");
        bst.insert("e", "Element 2");
        bst.insert("k", "Element 3");
        // Alle drei Elemente eingefügt
        assertTrue(bst.contains("a"));
        assertTrue(bst.contains("e"));
        assertTrue(bst.contains("k"));

        bst.insert("c", "Element 4");
        bst.insert("g", "Element 5");
        assertTrue(bst.contains("a"));
        assertTrue(bst.contains("e"));
        assertTrue(bst.contains("k"));
        assertTrue(bst.contains("c"));
        assertTrue(bst.contains("g"));
    }

    @Test
    void remove() {
        BSTree<String, String> bst = new BSTree<>();
        bst.insert("a", "Element1");
        assertTrue(bst.contains("a"));
        bst.remove("a");
        assertFalse(bst.contains("a"));
        bst.insert("h", "Element1");
        bst.insert("a", "Element2");
        bst.insert("d", "Element3");
        bst.insert("k", "Element4");
        bst.insert("b", "Element5");

        assertTrue(bst.contains("k"));
        bst.remove("k");
        assertFalse(bst.contains("k"));
    }

    @Test
    void get() {
        BSTree<String, String> bst = new BSTree<>();
        bst.insert("1","1");
        bst.insert("Hallo","2");
        bst.insert("123","3");
        assertEquals("1",bst.get("1"));
        assertEquals("2",bst.get("Hallo"));
        assertEquals("3",bst.get("123"));
    }

    @Test
    void size() {
        BSTree<String, String> bst = new BSTree<>();
        assertEquals(0, bst.size());
        bst.insert("a", "Element1");
        assertEquals(1, bst.size());
        bst.insert("a", "Element2");
        assertEquals(1, bst.size());
        bst.insert("b", "Element3");
        bst.insert("c", "Element1");
        assertEquals(3, bst.size());

        bst.remove("a");
        assertEquals(2, bst.size());

        try {
            bst.remove("a");
        } catch (NoSuchElementException e) {

        }
        assertEquals(2, bst.size());

        bst.remove("c");
        assertEquals(1, bst.size());

        bst.remove("b");
        assertEquals(0, bst.size());
    }
}
