package es.upv.sdm.labs.bikeroutes.pojo;

import android.location.Location;

import java.util.ArrayList;

public class Person {

    public int pID;
    public String firstName;
    public String lastName;
    public int age;

    public Person(int pID, String firstName, String lastName, int age) {
        this.pID = pID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public Person(int pID) {
        this.pID = pID;
    }

    public static ArrayList<Person> getPersons() {
        ArrayList<Person> persons = new ArrayList<Person>();


        persons.add(new Person(0, "Juan", "Carlos", 25 ));
        persons.add(new Person(1, "Hans", "Peter", 21 ));
        persons.add(new Person(2, "Max", "Müller", 35 ));
        persons.add(new Person(3, "Klaus", "Meier", 19 ));
        persons.add(new Person(4, "Lucas", "Williams", 23 ));

        return persons;
    }
}
