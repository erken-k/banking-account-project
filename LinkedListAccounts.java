import java.util.ArrayList;

public class LinkedListAccounts {
    private class Node{
        Account value;
        Node next;
        public Node(Account value){   // no node constrctor
            this.value=value;
            next=null;
        }
        public Node(Account value,Node next){
            this.value=value;
            this.next=next;
        }
    }
     private Node first;
     private Node last;
    public LinkedListAccounts(){
        first=null;
        last=null;
    }
    //returns a boolean representing whether there are any elements in list
    public boolean isEmpty(){
        return first==null;
    }
    //returns size of the list
    public int size(){
        Node t=first;
        int count=0;
        while(t!=null){
            count++;
            t=t.next;
        }
        return count;
    }
    //adds element to the end of list
    public void add(Account acc){
        if(isEmpty()){
            first=new Node(acc);
            last=first;
        }else{
            last.next=new Node(acc);
            last=last.next;
        }
    }
    //adds element in proper position, to maintain sorted order, using generic list;
    public <E extends Comparable>void addSortedGeneric(Account acct, E e2, ArrayList<E> e){
        int pos=0;
        if(isEmpty()){
           set(0,acct);
           return;
        }
        while(pos<size()&&e.get(pos).compareTo(e2)<0){
            pos++;
        }
        set(pos,acct);
    }
    // using addSortedGeneric adds acct in proper position by acct number
    public void addSortedByAcctNum(Account acct){
        int accNum=acct.getAccountNumber();
        ArrayList<Integer> l=new ArrayList<>();
        Node t=first;
        while(t!=null){
            l.add(t.value.getAccountNumber());
            t=t.next;
        }
        addSortedGeneric(acct,accNum,l);

    }
    // using addSortedGeneric adds acct in proper position to maintain sorted order by SSN
    public void addSortedBySSN(Account acct){
        int ssn=acct.getDepositor().getSsn();
        ArrayList<Integer> l=new ArrayList<>();
        Node t=first;
        while(t!=null){
            l.add(t.value.getDepositor().getSsn());
            t=t.next;
        }
        addSortedGeneric(acct,ssn,l);

    }
    // using addSortedGeneric adds acct in proper position to maintain sorted order by name
    public void addSortedByName(Account acct){
        Name name=acct.getDepositor().getName();
        ArrayList<Name> l=new ArrayList<>();
        Node t=first;
        while(t!=null){
            l.add(t.value.getDepositor().getName());
            t=t.next;
        }
        addSortedGeneric(acct,name,l);

    }
    // using addSortedGeneric adds acct in proper position to maintain sorted order by balance
    public void addSortedByBalance(Account acct){
        double balance=acct.getAccountBalance();
        ArrayList<Double> l=new ArrayList<>();
        Node t=first;
        while(t!=null){
            l.add(t.value.getAccountBalance());
            t=t.next;
        }
        addSortedGeneric(acct,balance,l);

    }
    //returns account at a given index
    public Account get(int pos){
        if(pos<0 || pos>=size())
            throw new IndexOutOfBoundsException(pos);
        Node t=first;
        int i=0;
        while(pos!=i){
            t=t.next;
            i++;
        }
        return t.value;
    }
    //sets account to a given index. account located at this index before is moved to the right
    public void set(int pos, Account acct) {
        if (pos < 0 || pos > size()) {
            String message = String.valueOf(pos);
            throw new IndexOutOfBoundsException(message);
        }


        if (pos == 0) {
            first = new Node(acct, first);
            if (last == null)
                last = first;
            return;
        }

        Node pred = first;
        for (int k = 1; k <= pos - 1; k++) {
            pred = pred.next;
        }

        pred.next = new Node(acct, pred.next);
        if (pred.next.next == null)
            last = pred.next;
    }
    // sets acct into list accoridng to its value of acct number
    public void setSortedByAcctNumber(Account acct){
        Node f=first;
        if(first.value.getAccountNumber()==acct.getAccountNumber()){
            first=new Node(acct,first.next);
            return;
        }
        while(f!=null&&f.next.value.getAccountNumber()!=acct.getAccountNumber()){
            f=f.next;
        }
        Node succ=f.next.next;
        f.next=new Node(acct,succ);
        if(f.next.next==null)
            last=f.next;

    }
//removes account at a given index. returns it
    public Account remove(int pos) {
        if (pos < 0 || pos >= size()) {
            String message = String.valueOf(pos);
            throw new IndexOutOfBoundsException(message);
        }
        Account el;
        if (pos == 0) {
            el = first.value;
            first = first.next;
            if (first == null)
                last = null;
        } else {
            Node pred = first;
            for (int k = 1; k <= pos - 1; k++)
                pred = pred.next;
            el = pred.next.value;

            pred.next = pred.next.next;

            if (pred.next == null)
                last = pred;
        }
        return el;

    }
    //removes from sorted list
    public void removeSorted(Account acct){
        Node f=first;
        if(first.value.getAccountNumber()==acct.getAccountNumber()){
            first= first.next;
            return;
        }
        while(f!=null&&f.next.value.getAccountNumber()!=acct.getAccountNumber()){
            f=f.next;
        }
        f.next=f.next.next;
        if(f.next==null){
            last=f;
        }
    }
    //removes acct, returning boolean as a success indicator
    public boolean remove(Account acct){
        Node f=first;
        if(first.value.getAccountNumber()==acct.getAccountNumber()){
            first= first.next;
            return true;
        }
        while(f!=null&&f.next.value.getAccountNumber()!=acct.getAccountNumber()){
            f=f.next;
        }
        if(f==null){
            return false;
        }
        f.next=f.next.next;
        if(f.next==null){
            last=f;
        }
        return true;
    }



}

