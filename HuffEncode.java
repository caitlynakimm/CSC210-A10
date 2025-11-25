import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Encodes plain text using Huffman encoding
 * Builds encoding map from Huffman tree and converts text to binary codes
 */
public class HuffEncode {

    /**
     * Builds encoding map that maps characters to their binary codes
     * @param tree tree to traverse for code generating
     * @return mapping of characters to their binary Huffman codes
     */
    public static Map<Character, String> buildEncodingMap(HuffTree tree) {
        Map<Character, String> encodingMap = new HashMap<>();
        traverse(tree, "", encodingMap);
        return encodingMap;  
    }

    /**
     * Recursively traverses Huffman tree to build character to code mappings
     * @param tree current node in tree
     * @param path binary path from root to current node
     * @param map encoding map to add character-code pairs
     */
    public static void traverse(HuffTree tree, String path, Map<Character, String> map) {
        //check if reached a leaf in tree
        if (tree.getLeft() == null && tree.getRight() == null) {
            map.put(tree.getData(), path);
        } else { //if current node has left or right child
            if (tree.getLeft() != null) {
                traverse(tree.getLeft(), path + '0', map);
            }
            
            if (tree.getRight() != null) {
                traverse(tree.getRight(), path + '1', map);
            }
        }
    }

    /**
     * Main method for Huffman encoding program
     * @param args command line arguments (codeFile)
     * @throws IOException if input/output operations fail
     */
    public static void main(String[] args) throws IOException {
        if (args.length < 1) { //when user doesn't provide code file
            System.err.println("User didn't provide code file");
            System.exit(1);
        }

        try {
            String codeFilename = args[0]; //first argument is code file
            HuffTree huffmanTree = HuffTree.readHuffTree(codeFilename);
            Map<Character, String> treeMap = buildEncodingMap(huffmanTree);

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            int charCode;
            while ((charCode = reader.read()) != -1) { //reader.read() returns ASCII code of character. If charCode is -1, reached end of stream.
                char character = (char) charCode; //converts ASCII code to the actual character
                String binaryCode = treeMap.get(character);

                if (binaryCode != null) {
                    System.out.print(binaryCode);
                } else {
                    System.err.println("Warning: character '" + character + "' is not in encoding map.");
                }
            }
            reader.close();

        } catch (IOException e) {
            System.err.println("Error in reading file: " + e.getMessage());
            System.exit(1);
        }
        
    }
}
