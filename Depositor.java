public class Depositor {
    private Name name;
    private int ssn;

    //no arg constructor which assigns Name field to emoty Name object and ssn to 0
    public Depositor() {
        name = new Name();
        ssn = 0;
    }

    // contructor which takes in Name object ans int representing ssn
    // assigns Name data field of this object to a copy of passed name. and this ssn to ssn
    public Depositor(Name name, int ssn) {
        this.name = new Name(name);
        this.ssn = ssn;
    }

    // copy constuctor which takes in a Depositor object and assigns all data fields of this object
    // to corresponding data fields of passed Depositor
    public Depositor(Depositor depositor) {
        this.name = depositor.getName();
        this.ssn = depositor.ssn;
    }

    //gets a copy of name object
    public Name getName() {
        return new Name(this.name);
    }

    // gets ssn
    public int getSsn() {
        return ssn;
    }

    //returns a string representing Depositor object
    public String toString() {
        return  String.format("%10s %15d",name.toString(),ssn);
    }

    // checks if two depositors are equal
    public boolean equals(Depositor depositor) {
        return this.name.equals(depositor.name) && depositor.ssn == this.ssn;
    }
}
