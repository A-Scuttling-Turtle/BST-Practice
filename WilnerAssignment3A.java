import java.util.*;

public class WilnerAssignment3A {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in); // Create scanner for user input
        System.out.println("Welcome! Enter 10 integers (press Enter after each):"); // Friendly prompt per requirements

        Integer[] data = new Integer[10]; // Array to hold user entries
        for (int i = 0; i < 10; i++) { // Loop for 10 values
            System.out.print("Value " + (i + 1) + ": "); // Prompt for the i-th value
            data[i] = readInt(in); // Read an integer robustly
        }

        WilnerBinarySearchTree<Integer> bst = new WilnerBinarySearchTree<>(); // Create BST instance
        WilnerAVLTree<Integer> avl = new WilnerAVLTree<>(); // Create AVL instance

        System.out.println("\n--- INSERT PHASE (BST) ---"); // Section header
        for (int x : data) { // Insert each user number into BST
            System.out.println("Inserting " + x + " into BST..."); // Describe operation
            bst.insert(x); // Insert
            bst.WilnerOutput(); // Show size and traversals after insertion
        }

        System.out.println("\n--- INSERT PHASE (AVL) ---"); // Section header
        for (int x : data) { // Insert into AVL
            System.out.println("Inserting " + x + " into AVL..."); // Describe operation
            avl.insert(x); // Insert
            avl.WilnerOutput(); // Show size and traversals after insertion
        }

        System.out.println("\n--- SEARCH PHASE ---"); // Search demonstration header
        int searchHit = data[0]; // Choose an existing value to search
        int searchMiss = data[0] + 1; // Likely a non-existing neighbor (may or may not exist)
        System.out.println("BST search(" + searchHit + "): " + bst.search(searchHit)); // Report BST search hit
        bst.WilnerOutput(); // Output traversals afterwards
        System.out.println("BST search(" + searchMiss + "): " + bst.search(searchMiss)); // Report BST search miss
        bst.WilnerOutput(); // Output again per instructions
        System.out.println("AVL search(" + searchHit + "): " + avl.search(searchHit)); // Report AVL search hit
        avl.WilnerOutput(); // Output traversals afterwards
        System.out.println("AVL search(" + searchMiss + "): " + avl.search(searchMiss)); // Report AVL search miss
        avl.WilnerOutput(); // Output again per instructions

        System.out.println("\n--- TRAVERSAL METHODS (explicit calls) ---"); // Traversal header
        System.out.print("BST inorder: "); bst.inorder(); // Show inorder directly
        System.out.print("BST preorder: "); bst.preorder(); // Show preorder directly
        System.out.print("BST postorder: "); bst.postorder(); // Show postorder directly
        bst.WilnerOutput(); // And the consolidated output
        System.out.print("AVL inorder: "); avl.inorder(); // AVL inorder
        System.out.print("AVL preorder: "); avl.preorder(); // AVL preorder
        System.out.print("AVL postorder: "); avl.postorder(); // AVL postorder
        avl.WilnerOutput(); // Consolidated output

        System.out.println("\n--- DELETE PHASE ---"); // Delete demonstration header
        // Choose up to 3 elements to delete: first, last, and median by sorted order
        List<Integer> sorted = new ArrayList<>(Arrays.asList(data)); // Copy user data to list
        Collections.sort(sorted); // Sort to locate median-like elements
        int del1 = sorted.get(0); // Smallest
        int del2 = sorted.get(sorted.size() / 2); // Middle
        int del3 = sorted.get(sorted.size() - 1); // Largest

        System.out.println("Deleting " + del1 + " from BST..."); // Announce deletion from BST
        bst.delete(del1); // Delete first
        bst.WilnerOutput(); // Show result
        System.out.println("Deleting " + del2 + " from BST..."); // Announce deletion from BST
        bst.delete(del2); // Delete middle
        bst.WilnerOutput(); // Show result
        System.out.println("Deleting " + del3 + " from BST..."); // Announce deletion from BST
        bst.delete(del3); // Delete last
        bst.WilnerOutput(); // Show result

        System.out.println("Deleting " + del1 + " from AVL..."); // Announce deletion from AVL
        avl.delete(del1); // Delete first
        avl.WilnerOutput(); // Show result
        System.out.println("Deleting " + del2 + " from AVL..."); // Announce deletion from AVL
        avl.delete(del2); // Delete middle
        avl.WilnerOutput(); // Show result
        System.out.println("Deleting " + del3 + " from AVL..."); // Announce deletion from AVL
        avl.delete(del3); // Delete last
        avl.WilnerOutput(); // Show result

        System.out.println("\nAll operations demonstrated. Goodbye!"); // Friendly ending
        in.close(); // Close scanner resource
    }

    private static int readInt(Scanner in) { // Helper to read an integer robustly
        while (true) { // Loop until a valid integer is read
            try { // Try reading an int
                return Integer.parseInt(in.nextLine().trim()); // Parse line to int
            } catch (NumberFormatException ex) { // If invalid
                System.out.print("Please enter a valid integer: "); // Reprompt
            }
        }
    }
}
