public class Name implements Comparable<Name>{

    private String firstName;
    private String lastName;

    //no arg constructor which assigns values to empty strings;
    public Name() {
        firstName = "";
        lastName = "";
    }

    //Copy constructor which assigns all data fields of this object to passed Name object
    public Name(Name name) {
        this.firstName = name.getFirstName();
        this.lastName = name.getLastName();
    }

    //Constructor which takes in two strings representing first and last name
    //sets them to appropriate datafields in this object
    public Name(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;

    }

    // getter which gets String representing first name
    public String getFirstName() {
        return firstName;
    }

    // getter which gets String representing last name
    public String getLastName() {
        return lastName;
    }

    // returns a string which represents this object
    public String toString() {
        return String.format("%-10s %15s",lastName,firstName);
    }

    // takes in a Name object and compares if it is equal to this object. returns boolean
    public boolean equals(Name name) {
        return name.firstName.equals(this.firstName) && name.lastName.equals(this.lastName);
    }

    // compares two names if surname is equal compares firstname. returns either negative, positive or 0
    public int compareTo(Name n) {
        if (lastName.equals(n.getLastName()))
            return firstName.compareTo(n.firstName);
        return lastName.compareTo(n.getLastName());
    }
}
