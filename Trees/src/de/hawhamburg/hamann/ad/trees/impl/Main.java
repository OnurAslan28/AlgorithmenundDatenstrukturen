package de.hawhamburg.hamann.ad.trees.impl;
import java.util.Random;

public class Main {

    private static final int N_START        =    100;
    private static final int N_INCREMENT    =    100;
    private static final int N_END          =  10000;

    public static void main(String[] args) {
        BSTree<Integer, Integer> randomTree = new BSTree<>();

        for (int n = N_START; n <= N_END; n += N_INCREMENT) {
            int acc = 0;
            for (int j = 1; j <= 1000; j++) {
                Random random = new Random();
                randomTree.insert(random.nextInt(), random.nextInt());

                acc += pfaddurchschnitt(randomTree.counter, randomTree.size());
            }
            int neu = acc / 1000;
            System.out.println("n " + n + " ::::::::::::::  durchschnitt pfad " + neu);
        }
    }

    private static int pfaddurchschnitt(int allePfade, int size) {
        return allePfade / size + 1;
    }

}

