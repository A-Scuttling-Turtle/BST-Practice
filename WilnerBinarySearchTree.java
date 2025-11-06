import java.util.*;

public class WilnerBinarySearchTree<E extends Comparable<E>> implements WilnerTree<E> {
    protected class WilnerTreeNode<T> { //Tree node class
        T element;            //Stored element
        WilnerTreeNode<T> left;  //Left child reference
        WilnerTreeNode<T> right; //Right child reference
        WilnerTreeNode(T e) { this.element = e; } //Constructor sets the element
    }
    
    protected WilnerTreeNode<E> root; //Root of the BST
    protected int size = 0;           //Number of nodes

    public WilnerBinarySearchTree() {} //No-arg constructor

    public WilnerBinarySearchTree(E[] objects) { //Construct from array
        for (E e : objects) insert(e); //Insert each element
    }
    
    @Override
    public boolean search(E e) { //Search for an element starting at root
        WilnerTreeNode<E> current = root; //Start at the root
        while (current != null) { //Traverse until null
            int cmp = e.compareTo(current.element); //Compare target with current
            if (cmp < 0) current = current.left;     //Go left if smaller
            else if (cmp > 0) current = current.right; //Go right if larger
            else return true; //Found equal element
        }
        return false; //Not found
    }
    
    @Override
    public boolean insert(E e) { //Insert a new element if not duplicate
        if (root == null) { //Empty tree case
            root = new WilnerTreeNode<>(e); //New root node
            size++; //Increment size
            return true; //Inserted
        }
        WilnerTreeNode<E> parent = null; //Track parent during search
        WilnerTreeNode<E> current = root; //Start at root
        while (current != null) { //Traverse tree to find position
            int cmp = e.compareTo(current.element); // Compare
            if (cmp < 0) { parent = current; current = current.left; } //Move left
            else if (cmp > 0) { parent = current; current = current.right; } //Move right
            else return false; // Duplicate: not inserted
        }
        int cmp = e.compareTo(parent.element); //Compare with parent to attach
        if (cmp < 0) parent.left = new WilnerTreeNode<>(e); //Attach as left child
        else parent.right = new WilnerTreeNode<>(e);        //Attach as right child
        size++; //Increment size
        return true; //Inserted successfully
    }
    
    @Override
    public boolean delete(E e) { //Delete an element if present
        WilnerTreeNode<E> parent = null; //Parent pointer
        WilnerTreeNode<E> current = root; //Start at root
        while (current != null) { //Find the node
            int cmp = e.compareTo(current.element); //Compare
            if (cmp < 0) { parent = current; current = current.left; } //Go left
            else if (cmp > 0) { parent = current; current = current.right; } //Go right
            else break; //Found node
        }
        if (current == null) return false; //Not found: nothing to delete

        if (current.left == null) { //Case 1: no left child
            if (parent == null) root = current.right; //Deleting root: move right child up
            else if (parent.left == current) parent.left = current.right; //Link parent's left
            else parent.right = current.right; //Link parent's right
        } else { //Case 2: has left child -> find rightmost in left subtree
            WilnerTreeNode<E> parentOfRightMost = current; //Start at current
            WilnerTreeNode<E> rightMost = current.left;    //Move into left subtree
            while (rightMost.right != null) { //Find rightmost
                parentOfRightMost = rightMost; //Advance parent pointer
                rightMost = rightMost.right;   //Advance rightMost pointer
            }
            current.element = rightMost.element; //Replace current's element with rightMost's element
            if (parentOfRightMost.right == rightMost) parentOfRightMost.right = rightMost.left; //Relink around removed node
            else parentOfRightMost.left = rightMost.left; //If the rightMost was directly current.left
        }
        size--; //Decrement size
        return true; //Deleted
    }
    
    @Override
    public void inorder() { //Print inorder traversal
        for (E e : inorderList()) System.out.print(e + " "); //Print each with space
        System.out.println(); //Newline at end
    }
    
    @Override
    public void postorder() { //Print postorder traversal
        for (E e : postorderList()) System.out.print(e + " "); //Print each with space
        System.out.println(); //Newline
    }
    
    @Override
    public void preorder() { //Print preorder traversal
        for (E e : preorderList()) System.out.print(e + " "); //Print each with space
        System.out.println(); //Newline
    }
    
    @Override
    public int getSize() { //Return size
        return size; //Return the number of nodes
    }
    
    @Override
    public void clear() { //Clear the tree
        root = null; //Drop reference to root
        size = 0;    //Reset size
    }
    
    @Override
    public Iterator<E> iterator() { //Return an inorder iterator
        List<E> list = inorderList(); //Get inorder list
        return list.iterator(); //Return its iterator
    }
    
    @Override
    public List<E> inorderList() { //Build inorder list
        List<E> list = new ArrayList<>(); //Create list
        inorder(root, list); //Fill list via recursion
        return list; //Return list
    }
    
    @Override
    public List<E> preorderList() { //Build preorder list
        List<E> list = new ArrayList<>(); //Create list
        preorder(root, list); //Fill list
        return list; //Return list
    }
    
    @Override
    public List<E> postorderList() { //Build postorder list
        List<E> list = new ArrayList<>(); //Create list
        postorder(root, list); //Fill list
        return list; //Return list
    }
    
    private void inorder(WilnerTreeNode<E> node, List<E> list) { // Recursive inorder helper
        if (node == null) return; //Base case
        inorder(node.left, list); //Traverse left
        list.add(node.element);   //Visit node
        inorder(node.right, list);//Traverse right
    }
    
    private void preorder(WilnerTreeNode<E> node, List<E> list) { // Recursive preorder helper
        if (node == null) return; //Base case
        list.add(node.element);   //Visit node
        preorder(node.left, list);//Traverse left
        preorder(node.right, list);//Traverse right
    }
    
    private void postorder(WilnerTreeNode<E> node, List<E> list) { // Recursive postorder helper
        if (node == null) return; //Base case
        postorder(node.left, list); //Traverse left
        postorder(node.right, list);//Traverse right
        list.add(node.element);     //Visit node
    }

    public void WilnerOutput() { // rint size and traversals in the specified format
        System.out.println("Number of nodes: " + getSize()); //Print number of nodes
        System.out.print("Inorder: "); inorder();            //Print inorder on one line
        System.out.print("Preorder: "); preorder();          //Print preorder on one line
        System.out.print("Postorder: "); postorder();        //Print postorder on one line
    }
}
