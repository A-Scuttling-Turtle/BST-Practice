import java.util.*;


public interface WilnerTree<E> extends Collection<E> {
    public boolean search(E e); //Search for an element and return true if found
    
    public boolean insert(E e); //Insert an element and return true if inserted (no duplicates)
    
    public boolean delete(E e); //Delete an element and return true if removed
    
    public void inorder(); //Print elements in inorder traversal
    
    public void postorder(); //Print elements in postorder traversal
    
    public void preorder(); //Print elements in preorder traversal
    
    public int getSize(); //Return the number of nodes in the tree
    
    public void clear(); //Remove all elements from the tree
    
    public Iterator<E> iterator(); //Return an inorder iterator over the elements
    
    public List<E> inorderList();   //Return elements as a List in inorder
    
    public List<E> preorderList();  //Return elements as a List in preorder
    
    public List<E> postorderList(); //Return elements as a List in postorder
    
    @Override
    default int size() {    //Provide Collection.Size() using getSize()
        return getSize();   //Delegate to tree's size
    }
    
    @Override
    default boolean isEmpty() { //Check if the tree is empty
        return getSize() == 0; //Empty if size is zero
    }
    
    @Override
    @SuppressWarnings("unchecked")
    default boolean contains(Object o) { //Collection.contains(Object)
        try { //Attempt a safe cast to E
            return search((E)o); //Use the tree's search
        } catch (ClassCastException ex) { //If wrong type
            return false; //Not contained if types mismatch
        }
    }
    
    @Override
    default Object[] toArray() { //First toArray variant
        Object[] arr = new Object[getSize()]; //Create an array of appropriate length
        int i = 0; //Index counter
        for (E e : this) { //Iterate inorder
            arr[i++] = e; //Fill array sequentially
        }
        return arr; //Return the populated array
    }
    
    @Override
    @SuppressWarnings("unchecked")
    default <T> T[] toArray(T[] a) { //Second toArray variant with runtime type of a
        int n = getSize(); //Get size once
        T[] r = a.length >= n ? a : (T[])java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), n); //Allocate result array
        int i = 0; //Index counter
        for (E e : this) { //Iterate inorder
            r[i++] = (T)e; //Assign with unchecked cast (caller ensures type)
        }
        if (r.length > n) r[n] = null; //Per spec, set element after last to null if array bigger
        return r; //Return the filled array
    }
    
    @Override
    default boolean add(E e) { //Collection.add delegates to insert
        return insert(e); //Insert element into the tree
    }
    
    @Override
    @SuppressWarnings("unchecked")
    default boolean remove(Object o) { //Collection.remove(Object) delegates to delete
        try { // Attempt to cast
            return delete((E)o); //Delete element if present
        } catch (ClassCastException ex) { //Wrong type
            return false; //Nothing removed
        }
    }
    
    @Override
    default boolean containsAll(Collection<?> c) { //Check if tree contains all elements in c
        for (Object x : c) { //Iterate over the collection
            if (!contains(x)) return false; //If any element is missing, return false
        }
        return true; //All elements found
    }
    
    @Override
    @SuppressWarnings("unchecked")
    default boolean addAll(Collection<? extends E> c) { //Add all elements from c
        boolean changed = false; //Track if the tree changed
        for (E x : c) { //Iterate elements to add
            changed |= insert(x); //Insert each; OR-assign change flag
        }
        return changed; //Return whether any insertion changed the tree
    }
    
    @Override
    default boolean removeAll(Collection<?> c) { //Remove all elements found in c
        boolean changed = false; //Change flag
        for (Object x : c) { //Iterate elements to remove
            changed |= remove(x); //Remove each; OR-assign change flag
        }
        return changed; //Return whether any deletion changed the tree
    }
    
    @Override
    @SuppressWarnings("unchecked")
    default boolean retainAll(Collection<?> c) { //Retain only elements that are also in c
        boolean changed = false; //Change flag
        List<E> toDelete = new ArrayList<>(); //Accumulate deletions to avoid concurrent modification
        for (E x : this) { //Iterate current elements
            if (!c.contains(x)) toDelete.add(x); //Mark for removal if not in c
        }
        for (E x : toDelete) { //Remove after iteration completes
            changed |= delete(x); //Delete and record change
        }
        return changed; //Indicate if any element was removed
    }
}
