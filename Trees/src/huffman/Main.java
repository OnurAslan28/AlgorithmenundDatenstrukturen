package huffman;

import java.nio.file.Path;

public class Main {

    public static void main(String[] args) {
        Huffman huffman = new Huffman(Path.of("Trees", "resources", "huffman", "README.md"));
        //Huffman huffman = new Huffman(Path.of("Trees", "resources", "huffman", "README.md"));


        huffman.encode();
        huffman.decode();
    }
}
