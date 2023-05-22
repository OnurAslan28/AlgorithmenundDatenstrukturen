package lzw;

import java.nio.file.Path;

public class Main {

    public static void main(String[] args) {
        LZW lzw = new LZW(Path.of("Trees", "resources", "lzw", "README.md"));
        //LZW lzw = new LZW(Path.of("Trees", "resources", "lzw", "test.json"));


        lzw.encode();
        lzw.decode();
    }
}
