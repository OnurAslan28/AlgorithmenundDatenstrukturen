package lzw;

import com.github.jinahya.bit.io.*;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;



public class LZW {

    private static final boolean DEBUG = false;
    private static final int CHAR_BIT_SIZE = 8;
    private final Path filePath;
    private final Path encodedPath;
    private final Path decodedPath;

    private static final int DICT_BIT_SIZE = 12;
    private static final int CHARS = 256;
    private BSTree dictionary;

    public LZW(Path filePath) {
        this.filePath = filePath;
        encodedPath = this.filePath.getParent()
                .resolve("encoded_" + filePath.getFileName());
        decodedPath = this.filePath.getParent()
                .resolve("decoded_" + filePath.getFileName());
    }


    public void encode() {
        initDictionary();

        try (InputStream is = new FileInputStream(filePath.toFile())) {
            try (OutputStream os = new FileOutputStream(encodedPath.toFile())) {
                BitOutput bitOut = new DefaultBitOutput(new StreamByteOutput(os));

                int statStringLength = 0;
                int statCodes = 0;
                List<Integer> statCodeList = new ArrayList<>();

                List<Character> last = new ArrayList<>();

                int aktuell;
                int nummer = CHARS;

                debug(String.format("\n\n%15s | %10s | %22s | %10s", "last", "current", "newEntry", "Encoded Output"));
                while ((aktuell = is.read()) != -1) {
                    List<Character> lookup = new ArrayList<>(last);
                    lookup.add((char) aktuell);
                    if (!dictionary.includes(lookup)) {
                        dictionary.insert(lookup, nummer++);
                        int code = dictionary.getByPrefix(last).getNumber();
                        bitOut.writeInt(true, DICT_BIT_SIZE, code);
                        debug(String.format("\n%15s | %10s | %15s = %4s | %10s", last, (char) aktuell, lookup, nummer - 1, code));
                        statStringLength += last.size();
                        statCodes++;
                        statCodeList.add(code);
                        last = List.of((char) aktuell);
                    } else {
                        debug(String.format("\n%15s | %10s | %15s   %4s | %10s", last, (char) aktuell, "", "", ""));
                        last = new ArrayList<>(last);
                        last.add((char) aktuell);
                    }
                }
                int code = dictionary.getByPrefix(last).getNumber();
                bitOut.writeInt(true, DICT_BIT_SIZE, code);
                debug(String.format("\n%15s | %10s | %15s   %4s | %10s", last, "EOF", "", "", code));
                bitOut.align(DICT_BIT_SIZE);

                statStringLength += last.size();
                statCodes++;
                statCodeList.add(code);
                System.out.println("\n\n********** AUSGABE **********");
                System.out.println("********** Mittlere codierte Stringlänge: " + (1.0 * statStringLength / statCodes) + " **********");
                System.out.println("********** Codes: " + statCodeList.stream()
                        .collect(Collectors.toMap(c -> c, c -> 1, Integer::sum)) + " **********");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void initDictionary() {
        dictionary = new BSTree();
        for (int i = 0; i < CHARS; i++) {
            dictionary.insert(List.of((char) i), i);
            System.out.println(List.of((char) i)+ " " + i);
        }
    }


    public void decode() {
        initDictionary();

        try (InputStream is = new FileInputStream(encodedPath.toFile())) {
            BitInput bitIn = new DefaultBitInput(new StreamByteInput(is));
            try (OutputStream os = new FileOutputStream(decodedPath.toFile())) {
                BitOutput bitOut = new DefaultBitOutput(new StreamByteOutput(os));

                debug(String.format("\n\n%15s | %10s | %22s | %10s | %10s", "last", "current", "m", "Output", "newEntry"));
                int aktuell = bitIn.readInt(true, DICT_BIT_SIZE);
                List<Character> letzteswort = dictionary.getByNum(aktuell);
                assert letzteswort.size() == 1;
                bitOut.writeChar(CHAR_BIT_SIZE, letzteswort.get(0));
                List<Character> m = null;
                debug(String.format("\n%15s | %10s | %22s | %10s | %10s", "", aktuell, "", letzteswort.get(0), ""));
                int nummer = CHARS;
                while (is.available() != 0) {
                    aktuell = bitIn.readInt(true, DICT_BIT_SIZE);
                    if (dictionary.containsNumber(aktuell)) {
                        m = dictionary.getByNum(aktuell);
                        if (m.isEmpty()) {
                            // Gelesener Code nicht verwendet => fertig (Zeichen für EOF)
                            System.out.println("\n ********** Dekodirung fertig **********");
                            break;
                        }
                        writeString(bitOut, m);
                        List<Character> newEntry = new ArrayList<>(letzteswort);
                        newEntry.add(m.get(0));
                        dictionary.insert(newEntry, nummer++);
                        debug(String.format("\n%15s | %10s | %22s | %10s | %10s = %4s", letzteswort, aktuell, m, m, newEntry, nummer - 1));
                        letzteswort = m;
                    } else {
                        assert m != null; // dieser Fall tritt nie zuerst ein
                        List<Character> newEntry = new ArrayList<>(letzteswort);
                        newEntry.add(m.get(0));
                        dictionary.insert(newEntry, nummer++);
                        writeString(bitOut, newEntry);
                        letzteswort = newEntry;
                        debug(String.format("\n%15s | %10s | %22s | %10s | %10s = %4s", letzteswort, aktuell, m, newEntry, newEntry, nummer - 1));
                    }
                }

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private void writeString(BitOutput bitOut, List<Character> charList) throws IOException {
        for (char c : charList) {
            bitOut.writeChar(CHAR_BIT_SIZE, c);
        }
    }

    private void debug(String str) {
        if (DEBUG) System.out.println(DEBUG);
    }



}
