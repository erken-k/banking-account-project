import MyExceptions.EmptyHeapException;

import java.util.ArrayList;
import java.util.Comparator;


public class ArrayHeap<Account extends Comparable> {
    private ArrayList<Account> arrayHeap=new ArrayList<>();
    // method is used to place inserted to the end of arraylist element, to its correct position
    // it swaps child with parent until parent is less than child
    private void siftUp(){
        int p=arrayHeap.size()-1;
        while(p!=0){
            int parent=(p-1)/2;
            if(valueAt(parent).compareTo(valueAt(p))<=0){
                break;
            }else{
                Account temp=arrayHeap.get(parent);
                arrayHeap.set(parent,arrayHeap.get(p));
                arrayHeap.set(p,temp);
                p=parent;
            }
        }
    }
    //method receives comparator
    // method is used to place inserted to the end of arraylist element, to its correct position
    // it swaps child with parent until parent is less than child.
    // when comparing uses received Comparator
    private void siftUp(Comparator<Account> comparator){
        int p=arrayHeap.size()-1;
        while(p!=0){
            int parent=(p-1)/2;
            if(comparator.compare(valueAt(p),valueAt(parent))>=0){
                break;
            }else{
                Account temp=arrayHeap.get(parent);
                arrayHeap.set(parent,arrayHeap.get(p));
                arrayHeap.set(p,temp);
                p=parent;
            }
        }
    }
    //method restructures tree after removal of the top(min) element.
    //it places last element of arraylist to the root of tree, and swappes it with
    //one of the childs until child is larger
    private void siftDown(){
        int p=0;
        int size=arrayHeap.size();
        while(2*p+1<size){
            int leftChild=2*p+1;
            int rightChild=leftChild+1;
            int minChild=leftChild;
            if(rightChild<size){
                if(valueAt(rightChild).compareTo(valueAt(leftChild))<0)
                    minChild=rightChild;
            }
            if(valueAt(p).compareTo(valueAt(minChild))<=0){
                break;
            }else{
                Account temp=valueAt(p);
                arrayHeap.set(p,valueAt(minChild));
                arrayHeap.set(minChild,temp);
            }
            p=minChild;
        }
    }
    //receives comparator
    //method restructures tree after removal of the top(min) element.
    //it places last element of arraylist to the root of tree, and swappes it with
    //one of the childs until child is larger
    //when comparing uses comparator
    private void siftDown(Comparator<Account> comparator){
        int p=0;
        int size=arrayHeap.size();
        while(2*p+1<size){
            int leftChild=2*p+1;
            int rightChild=leftChild+1;
            int minChild=leftChild;
            if(rightChild<size){
                if(comparator.compare(valueAt(leftChild),valueAt(rightChild))>0)
                    minChild=rightChild;
            }
            if(comparator.compare(valueAt(p),valueAt(minChild))<=0){
                break;
            }else{
                Account temp=valueAt(p);
                arrayHeap.set(p,valueAt(minChild));
                arrayHeap.set(minChild,temp);
            }
            p=minChild;
        }
    }
    // recievies element, adds it to the end of tree, and calls siftup. returns true;

    public boolean add(Account acc){
        arrayHeap.add(acc);
        siftUp();
        return true;
    }
    //receives element to be inserted, and Comparator, adds element to the end
    // calls siftUp() to adjust tree. returns true
    public boolean add(Account acc, Comparator<Account> comparator){
        arrayHeap.add(acc);
        siftUp(comparator);
        return true;
    }
    //method is used to remove min element of tree, which is its root
    // it saves root to temp, places last element to the root, calls sift down
    // to readjust tree
    //returns element removed
    public Account removeMin() throws EmptyHeapException {
        if(isEmpty()){
            throw new EmptyHeapException();
        }else{
            Account value=arrayHeap.get(0);
            int lastPos=arrayHeap.size()-1;
            arrayHeap.set(0,valueAt(lastPos));
            arrayHeap.remove(lastPos);
            siftDown();
            return value;
        }
    }
    //receives comparator
    //method is used to remove min element of tree, which is its root
    // it saves root to temp, places last element to the root, calls sift down passing comparator
    // to readjust tree
    //returns element removed
    public Account removeMin(Comparator<Account> comparator) throws EmptyHeapException {
        if(isEmpty()){
            throw new EmptyHeapException();
        }else{
            Account value=arrayHeap.get(0);
            int lastPos=arrayHeap.size()-1;
            arrayHeap.set(0,valueAt(lastPos));
            arrayHeap.remove(lastPos);
            siftDown(comparator);
            return value;
        }
    }
    //removes element at a given position in arrayheap
    // after removal recreates tree to fix it
    public Account remove(int pos){
        Account temp=arrayHeap.remove(pos);
        ArrayHeap<Account> newHeap=new ArrayHeap<>();
        for(int i=0;i<arrayHeap.size();i++){
            newHeap.add(arrayHeap.get(i));
        }
        arrayHeap.clear();
        for(int i=0;i<newHeap.size();i++) {
            arrayHeap.add(newHeap.get(i));
        }
        return temp;
    }
    //removes element at a given position in arrayheap
    // after removal recreates tree to fix it
    public Account remove(int pos,Comparator<Account> comp){
        Account temp=arrayHeap.remove(pos);
        ArrayHeap<Account> newHeap=new ArrayHeap<>();
        for(int i=0;i<arrayHeap.size();i++){
            newHeap.add(arrayHeap.get(i),comp);
        }
        arrayHeap.clear();
        for(int i=0;i<newHeap.size();i++) {
            arrayHeap.add(newHeap.get(i));
        }
        return temp;
    }
    //returns value at a given position
    private Account valueAt(int pos){
        return arrayHeap.get(pos);
    }
    ///returns boolean rempresenting whether heap is empty
    public boolean isEmpty(){
        return arrayHeap.size()==0;
    }
    //returns number of elements in arrayheap
    public int size(){
        return arrayHeap.size();
    }
    //returns element at a given positron
    public Account get(int pos){
        return arrayHeap.get(pos);
    }
    //sets element to a given position
    public void set(int pos, Account acct){
        arrayHeap.set(pos,acct);
    }


}
