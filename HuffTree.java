import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HuffTree extends BinaryTree<Character> {
    /**
     * Creates a leaf node with given data
     * @param data animal name or question for this node
     */
    public HuffTree(Character data) {
        super(data);
    }

    /**
     * Creates branch node with left and right children
     * @param data question for this node
     * @param left left child of node
     * @param right right child of node
     */
    public HuffTree(Character data, BinaryTree<Character> left, BinaryTree<Character> right) {
        super(data);
        this.setLeft(left); //checks that left is type HuffTree
        this.setRight(right); //checks that right is type HuffTree
    }

    /**
     * Copy constructor makes deep copy of tree
     * @param tree tree to copy
     */
    public HuffTree(HuffTree tree) {
        super(tree.getData());
        this.setLeft(tree.getLeft());
        this.setRight(tree.getRight());
    }

    /**
     * Gets left child as a HuffTree
     * @return left child node
     */
    public HuffTree getLeft() {
        return (HuffTree)super.getLeft();
    }

    /**
     * Gets right child as a HuffTree
     * @return right child node
     */
    public HuffTree getRight() {
        return (HuffTree)super.getRight();
    }

    /**
     * Sets left child and checks type
     * @param left node to set as left child
     * @throws UnsupportedOperationException if left isn't HuffTree type
     */
    public void setLeft(BinaryTree<Character> left) {
        if (left == null || left instanceof HuffTree) {
            super.setLeft(left);
        } else {
            throw new UnsupportedOperationException("Left child needs to be a HuffTree or null");
        }
    }

    /**
     * Sets right child and checks type
     * @param right node to set as right child
     * @throws UnsupportedOperationException if right isn't HuffTree type
     */
    public void setRight(BinaryTree<Character> right) {
        if (right == null || right instanceof HuffTree) {
            super.setRight(right);
        } else {
            throw new UnsupportedOperationException("Right child needs to be a HuffTree or null");
        }
    }

    /**
     * Navigates through tree following path consisting of 0/1 directions
     * @param path string of '0' (left) and '1' (right) characters
     * @return node at end of path
     * @throws IllegalArgumentException for invalid paths
     */
    public HuffTree followPath(String path) {
        HuffTree current = this;

        for (int i = 0; i < path.length(); i++) {
            char direction = path.charAt(i);

            if (direction == '0') {
                current = current.getLeft();
            } else if (direction == '1') {
                current = current.getRight();
            } else {
                throw new IllegalArgumentException("Path must only include '0' or '1' characters.");
            }

            if (current == null) { //if new node you are at is null, throw exception
                throw new IllegalArgumentException("Path leads to a null node.");
            }
        }

        return current;
    }

    /**
     * Reads a huff tree from a file and reconstructs tree structure
     * @param filename file to read from
     * @return root node of reconstructed tree
     * @throws IOException if file reading fails or format is invalid
     */
    public static HuffTree readHuffTree(String filename) throws IOException {
        HuffTree root = new HuffTree((Character) null); //create root with null character
        
        try(BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            
            while((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split("\\s+"); //separate line into code and ASCII value
                if (parts.length != 2) {
                    throw new IOException("Invalid file format: " + line);
                }
                String path = parts[0];
                int asciiValue = Integer.parseInt(parts[1]);
                char character = (char) asciiValue;

                //traverse and create tree based on path
                HuffTree current = root;

                for (int i = 0; i < path.length(); i++) {
                    char direction = path.charAt(i);

                    if (direction == '0') {
                        if (current.getLeft() == null) {
                            current.setLeft(new HuffTree((Character) null)); //create new internal node if needed
                        }
                        current = current.getLeft();
                    } else if (direction == '1') {
                        if (current.getRight() == null) {
                            current.setRight(new HuffTree((Character) null)); //create new internal node if needed
                        }
                        current = current.getRight();
                    } else {
                        throw new IOException("Invalid character in path: " + path);
                    }
                }
                current.setData(character); //set character at leaf node
            }
        }
        return root;
    } 
}
