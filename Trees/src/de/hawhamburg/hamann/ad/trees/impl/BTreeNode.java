/**
 Autoren:
 Abdussamed Barak, Devid Khan, Onur Aslan
 */

package de.hawhamburg.hamann.ad.trees.impl;

//Knoten für Binary Tree
public class BTreeNode {

    int wert;
    BTreeNode links;
    BTreeNode rechts;
    BTreeNode eltern;

    public BTreeNode(int wert){
        this.wert = wert;
    }
}
