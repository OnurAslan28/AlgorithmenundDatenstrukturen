package de.hawhamburg.hamann.ad.trees.impl;

import de.hawhamburg.hamann.ad.trees.BinaryTree;


import java.util.Stack;
import java.util.function.Consumer;

public class BTree<Data extends Comparable<Data>> implements BinaryTree<Data> {
    Data data;
    BTree<Data> links;
    BTree<Data> rechts;


    public BTree(Data data){
        this.data = data;
    }

    @Override
    public Data getData() {
        return this.data;
    }

    @Override
    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public BTree<Data> getLeftNode() {
        return links;
    }

    @Override
    public BTree<Data> getRightNode() {
        return rechts;
    }

    @Override
    public boolean isLeaf() {
        return rechts == null && links == null;
    }

    @Override
    public void visitPreOrder(Consumer<BinaryTree<Data>> visitor) {
        Stack<BinaryTree> nodes = new Stack<>();
        nodes.push(this);
        while (!nodes.isEmpty()) {
            BinaryTree current = nodes.pop();
            visitor.accept(current);
            if (current.getRightNode() != null) {
                nodes.push(current.getRightNode());
            }
            if (current.getLeftNode() != null) {
                nodes.push(current.getLeftNode());
            }
        }
    }

    @Override
    public void visitInOrder(Consumer<BinaryTree<Data>> visitor) {
        Stack<BinaryTree> nodes = new Stack<>();

        BinaryTree curr = this;

        while (!nodes.empty() || curr != null)
        {
            if (curr != null)
            {
                nodes.push(curr);
                curr = curr.getLeftNode();
            }
            else {
                curr = nodes.pop();
                visitor.accept(curr);

                curr = curr.getRightNode();
            }
        }
    }

    @Override
    public void visitPostOrder(Consumer<BinaryTree<Data>> visitor) {
        if (this == null) {
            return;
        }
        Stack<BinaryTree> nodes = new Stack<>();
        nodes.push(this);

        Stack<BinaryTree> out = new Stack<>();

        while (!nodes.empty())
        {
            BinaryTree curr = nodes.pop();
            out.push(curr);

            if (curr.getLeftNode() != null) {
                nodes.push(curr.getLeftNode());
            }

            if (curr.getRightNode() != null) {
                nodes.push(curr.getRightNode());
            }
        }

        while (!out.empty()) {
            visitor.accept(out.pop());
        }
    }
}

