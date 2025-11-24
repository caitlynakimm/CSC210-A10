import java.util.Scanner;

public class HuffDecode {
    //read code from file and build Huffman tree
    //use tree to convert file from code into plantext (decoding via recursive)
    //show secret message in typescript
    public static void main(String[] args) {
        String codeFilename = args[0]; //first argument is code file
        HuffTree tree = HuffTree.readHuffTree(codeFilename);

        StringBuilder encodedBits = new StringBuilder();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            encodedBits.append(scanner.next());
        }
        scanner.close();
    }
}
