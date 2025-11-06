import java.util.*;

public class WilnerAVLTree<E extends Comparable<E>> extends WilnerBinarySearchTree<E> {
    protected class WilnerAVLTreeNode<T> extends WilnerTreeNode<T> { //Extend base node with height
        int height = 1; //Height of node (leaf height = 1)
        WilnerAVLTreeNode(T e) { super(e); } //Constructor delegates to super
    }
    
    @Override
    public boolean insert(E e) { //Override insert to rebalance
        if (root == null) { //Empty tree
            root = new WilnerAVLTreeNode<>(e); //New root as AVL node
            size++; //Increment size
            return true; //Inserted
        }
        if (search(e)) return false; //Do not insert duplicates
        root = insert((WilnerAVLTreeNode<E>)root, e); //Insert recursively and rebalance from root
        size++; //Increment size
        return true; //Inserted
    }
    
    private WilnerAVLTreeNode<E> insert(WilnerAVLTreeNode<E> node, E e) { //Recursive AVL insert
        if (node == null) return new WilnerAVLTreeNode<>(e); //Create new leaf
        int cmp = e.compareTo(node.element); //Compare values
        if (cmp < 0) node.left = insert((WilnerAVLTreeNode<E>)node.left, e); //Insert left
        else node.right = insert((WilnerAVLTreeNode<E>)node.right, e);       //Insert right
        updateHeight(node); //Update node height after insertion
        return rebalance(node); //Rebalance and return new subtree root
    }
    
    @Override
    public boolean delete(E e) { //Override delete to rebalance
        if (!search(e)) return false; //If not present, nothing to delete
        root = delete((WilnerAVLTreeNode<E>)root, e); //Delete and rebalance from root
        size--; //Decrement size
        return true; //Deleted
    }
    
    private WilnerAVLTreeNode<E> delete(WilnerAVLTreeNode<E> node, E e) { //Recursive AVL delete
        if (node == null) return null; //Base case
        int cmp = e.compareTo(node.element); //Compare target
        if (cmp < 0) node.left = delete((WilnerAVLTreeNode<E>)node.left, e); //Go left
        else if (cmp > 0) node.right = delete((WilnerAVLTreeNode<E>)node.right, e); //Go right
        else { //Found node to delete
            if (node.left == null) return (WilnerAVLTreeNode<E>)node.right;  //Replace by right child
            if (node.right == null) return (WilnerAVLTreeNode<E>)node.left;  //Replace by left child
            WilnerAVLTreeNode<E> succ = minNode((WilnerAVLTreeNode<E>)node.right); //Find inorder successor
            node.element = succ.element; //Copy successor's value
            node.right = delete((WilnerAVLTreeNode<E>)node.right, succ.element); //Delete successor
        }
        updateHeight(node); //Update height after deletion
        return rebalance(node); //Rebalance and return
    }
    
    private WilnerAVLTreeNode<E> minNode(WilnerAVLTreeNode<E> n) { //Find minimum node in subtree
        while (n.left != null) n = (WilnerAVLTreeNode<E>)n.left; //Move left until null
        return n; //Return leftmost node
    }
    
    private int height(WilnerTreeNode<E> n) { //Get height of node or 0 if null
        if (n == null) return 0; //Null height = 0 (helps with leaf height 1)
        if (n instanceof WilnerAVLTreeNode) return ((WilnerAVLTreeNode<E>)n).height; //Return stored height
        return 1; //Fallback (shouldn't occur for AVL nodes)
    }
    
    private void updateHeight(WilnerAVLTreeNode<E> n) { //Recompute height from children
        int hl = height(n.left); //Height of left child
        int hr = height(n.right); //Height of right child
        n.height = Math.max(hl, hr) + 1; //Height is 1 + max child height
    }
    
    private int balanceFactor(WilnerAVLTreeNode<E> n) { //Compute balance factor
        return height(n.left) - height(n.right); //Left height minus right height
    }
    
    private WilnerAVLTreeNode<E> rebalance(WilnerAVLTreeNode<E> z) { //Rebalance at node z
        int bf = balanceFactor(z); //Compute balance factor
        if (bf > 1) { //Left heavy
            if (balanceFactor((WilnerAVLTreeNode<E>)z.left) < 0) //Left-Right case
                z.left = rotateLeft((WilnerAVLTreeNode<E>)z.left); //First rotate left on left child
            return rotateRight(z); //Then rotate right on z
        }
        if (bf < -1) { //Right heavy
            if (balanceFactor((WilnerAVLTreeNode<E>)z.right) > 0) //Right-Left case
                z.right = rotateRight((WilnerAVLTreeNode<E>)z.right); //First rotate right on right child
            return rotateLeft(z); //Then rotate left on z
        }
        return z; //Already balanced
    }
    
    private WilnerAVLTreeNode<E> rotateRight(WilnerAVLTreeNode<E> y) { //Right rotation
        WilnerAVLTreeNode<E> x = (WilnerAVLTreeNode<E>)y.left; //x becomes new root of subtree
        WilnerAVLTreeNode<E> T2 = (WilnerAVLTreeNode<E>)x.right; //T2 is x's right subtree
        x.right = y; //Put y as right child of x
        y.left = T2; //Move T2 to y's left
        updateHeight(y); //Update height of y
        updateHeight(x); //Update height of x
        return x; //Return new root
    }

    private WilnerAVLTreeNode<E> rotateLeft(WilnerAVLTreeNode<E> y) { //Left rotation
        WilnerAVLTreeNode<E> x = (WilnerAVLTreeNode<E>)y.right; //x becomes new root of subtree
        WilnerAVLTreeNode<E> T2 = (WilnerAVLTreeNode<E>)x.left; //T2 is x's left subtree
        x.left = y; //Put y as left child of x
        y.right = T2; //Move T2 to y's right
        updateHeight(y); //Update height of y
        updateHeight(x); //Update height of x
        return x; //Return new root
    }
}
