import java.util.Scanner;

public class HuffEncode {
    public static void encodeMessage(HuffTree tree, String originalTextLines) {
        HuffTree current = tree;

        for (int i = 0; i < originalTextLines.length(); i++) {
            char bit = originalTextLines.charAt(i);

            if (bit == '0') {
                current = current.getLeft();
            } else if (bit == '1') {
                current = current.getRight();
            } else { 
                continue;
            }

            //check if reached a leaf in tree
            if (current.getLeft() == null && current.getRight() == null) {
                System.out.print(current.getData()); //print character
                current = tree; //return to root
            }
        }      
    }
    public static void main(String[] args) {
        if (args.length < 1) { //when user doesn't provide code file
            System.err.println("User didn't provide code file");
            System.exit(1);
        }

        try {
            String codeFilename = args[0]; //first argument is code file
            HuffTree tree = HuffTree.readHuffTree(codeFilename);

            StringBuilder originalTextLines = new StringBuilder();
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNext()) {
                originalTextLines.append(scanner.next());
            }
            scanner.close();

            encodeMessage(tree, originalTextLines.toString()); //encode originalText using Huffman tree via encodeMessage method
        } catch (Exception e) {
            System.err.println("An error occured: " + e.getMessage());
            System.exit(1);
        }
        

    }
}
