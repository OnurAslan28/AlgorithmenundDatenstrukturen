package huffman;

import com.github.jinahya.bit.io.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Stack;
import java.util.stream.IntStream;

class BTree<D extends HuffNode> implements Comparable<BTree<D>> {
    D data;
    BTree<D> links;
    BTree<D> rechts;

    public BTree(D data) {
        this(data, null, null);
    }

    public BTree(D data,BTree<D> links,BTree<D> rechts) {
        this.data = data;
        this.links = links;
        this.rechts = rechts;
    }

    public boolean isLeaf() {
        return links == null && rechts == null;
    }

    @Override
    public int compareTo(BTree<D> other) {
        return Integer.compare(data.frequency, other.data.frequency);
    }
}

class HuffNode {

    int frequency;
    char character;


    public HuffNode(char character, int frequency) {
        this.character = character;
        this.frequency = frequency;
    }
}

class Huffman {

    private static final int CHARS = 256;
    private static final int ALIGN = 31;
    private final Path filePath;
    private final Path encodedPath;
    private final Path decodedPath;
    private final int[] freq = new int[CHARS];
    private final Integer[][] codeTable = new Integer[CHARS][ALIGN];
    private final PriorityQueue<BTree<HuffNode>> heap = new PriorityQueue<>();
    private final Stack<Integer> path = new Stack<>();

    public Huffman(Path filePath) {
        this.filePath = filePath;
        encodedPath = this.filePath.getParent()
                .resolve("encoded_" + filePath.getFileName());
        decodedPath = this.filePath.getParent()
                .resolve("decoded_" + filePath.getFileName());
    }

    /**
     * A) Encoding
     */
    public void encode() {
        // Häufikeiten, Code Table mit 0 initialisieren
        IntStream.range(0, freq.length)
                .forEach(i -> freq[i] = 0);

        calculateCharacterFrequencies();
        BTree<HuffNode> root = buildHuffmanTree();
        calculateCodeFromHuffmanTree(root);
        //löscht Datei, wenn sie schon existiert, anonsten wird sie neu erstellt
        try {
            Files.deleteIfExists(encodedPath);
            Files.createFile(encodedPath);

            try (OutputStream out = new BufferedOutputStream(new FileOutputStream(encodedPath.toFile()))) {
                StreamByteOutput sbOut = new StreamByteOutput(out);
                BitOutput bitOut = new DefaultBitOutput(sbOut);

                writeCharacterFrequencies(bitOut);
                encodeDataFile(bitOut);
                bitOut.align(1);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private void initializeHeap() {
        // Min Heap initial mit absoluten Häufigkeiten befüllen
        for (char c = 0; c < CHARS; c++) {
            if (freq[c] > 0) heap.add(new BTree<>(new HuffNode(c, freq[c])));
        }
    }

    private void calculateCharacterFrequencies() {
        // Absolute Häufigkeiten der chars ermitteln
        try (InputStream in = new BufferedInputStream(new FileInputStream(filePath.toFile()))) {
            StreamByteInput sbIn = new StreamByteInput(in);
            BitInput bitIn = new DefaultBitInput(sbIn);

            for (;;) freq[bitIn.readChar(8)]++;
        } catch (EOFException ignored) {
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        initializeHeap();
    }



    private BTree<HuffNode> buildHuffmanTree() {
        while (heap.size() > 1) {
            BTree<HuffNode> tree1 = heap.remove();
            BTree<HuffNode> tree2 = heap.remove();
            heap.add(new BTree<>(new HuffNode((char) 0, tree1.data.frequency + tree2.data.frequency), tree1, tree2));
        }
        return heap.remove();
    }

    private void calculateCodeFromHuffmanTree(BTree<HuffNode> tree) {
        if (tree.isLeaf()) {
            codeTable[tree.data.character] = Arrays.copyOf(path.toArray(), path.size(), Integer[].class);
            return;
        }
        path.push(0);
        calculateCodeFromHuffmanTree(tree.links);
        path.pop();
        path.push(1);
        calculateCodeFromHuffmanTree(tree.rechts);
        path.pop();
    }



    private void encodeDataFile(BitOutput bitOut) throws IOException {
        try (InputStream in = new BufferedInputStream(new FileInputStream(filePath.toFile()))) {
            StreamByteInput sbIn = new StreamByteInput(in);
            BitInput bitIn = new DefaultBitInput(sbIn);

            for (;;) {
                char c = bitIn.readChar(8);
                for (Integer bit : codeTable[c]) {
                    bitOut.writeBoolean(bit == 1);
                }
            }

        } catch (EOFException ignored) { }
    }

    private void writeCharacterFrequencies(BitOutput output) throws IOException {
        for (int i = 0; i < freq.length; i++) {
            output.writeInt(true, ALIGN, freq[i]);
        }
    }

    /**
     * B) Decoding
     */
    public void decode() {
        IntStream.range(0, freq.length)
                .forEach(i -> freq[i] = 0);

        try (InputStream in = new BufferedInputStream(new FileInputStream(encodedPath.toFile()))) {
            StreamByteInput sbIn = new StreamByteInput(in);
            BitInput bitIn = new DefaultBitInput(sbIn);

            readCharacterFrequencies(bitIn);
            initializeHeap();

            BTree<HuffNode> root = buildHuffmanTree();
            calculateCodeFromHuffmanTree(root);

            decodeDataFile(root, bitIn);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    private void decodeDataFile(BTree<HuffNode> root, BitInput bitIn) throws IOException {
        int count = root.data.frequency;
        try (OutputStream out = new BufferedOutputStream(new FileOutputStream(decodedPath.toFile()))) {
            StreamByteOutput sbOut = new StreamByteOutput(out);
            BitOutput bitOut = new DefaultBitOutput(sbOut);

            for (int i = 0; i < count; i++) {
                bitOut.writeChar(8, decodeR(root, bitIn));
            }
        } catch (EOFException ignored) { }
    }


    private void readCharacterFrequencies(BitInput bitIn) throws IOException {
        for (int i = 0; i < freq.length; i++) {
            freq[i] = bitIn.readInt(true, ALIGN);
        }
    }


    private char decodeR(BTree<HuffNode> tree, BitInput bitIn) throws IOException {
        if (tree.isLeaf()) return tree.data.character;
        if (!bitIn.readBoolean()) return decodeR(tree.links, bitIn);
        return decodeR(tree.rechts, bitIn);
    }
}