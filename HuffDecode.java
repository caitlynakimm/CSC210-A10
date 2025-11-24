import java.util.Scanner;

public class HuffDecode {
    public static void decodeMessage(HuffTree tree, String encodedBits) {
        HuffTree current = tree;

        for (int i = 0; i < encodedBits.length(); i++) {
            char bit = encodedBits.charAt(i);

            if (bit == '0') {
                current = current.getLeft();
            } else if (bit == '1') {
                current = current.getRight();
            } else { //skip any non-bit characters
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

            StringBuilder encodedBits = new StringBuilder();
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNext()) {
                encodedBits.append(scanner.next());
            }
            scanner.close();

            decodeMessage(tree, encodedBits.toString()); //decode encodedBits using Huffman tree via decodeMessage method
            //Secret message from encoded.txt is: Rutabaga 
        } catch (Exception e) {
            System.err.println("An error occured: " + e.getMessage());
            System.exit(1);
        }
        

    }
}
