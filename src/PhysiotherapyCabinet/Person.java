package PhysiotherapyCabinet;

import Others.ObjectPlusPlus;

/**
 * Abstrakyjna klasa dziedząca po ObjectPlusPlus
 */
public abstract class Person extends ObjectPlusPlus {
    private static final long serialVersionUID = 2L;

    private String firstname;
    private String surname;
    private int telefonNumber;
    private String address;

    public Person(String firstname, String surname, int telefonNumber, String street, String houseNumber, String zipCode, String city) {
        super();
        if (firstname == null) {
            throw new NullPointerException("Pole imie nie może być puste.");
        }
        this.firstname = firstname;
        if (surname == null) {
            throw new NullPointerException("Pole nazwisko nie może być puste.");
        }
        this.surname = surname;
        if (street == null) {
            throw new NullPointerException("Pole ulica nie może być puste.");
        }
        if (zipCode == null) {
            throw new NullPointerException("Pole kod pocztowy nie może być puste.");
        }
        if (city == null) {
            throw new NullPointerException("Pole miasto nie może być puste.");
        }
        if (houseNumber == null) {
            throw new NullPointerException("Pole numer domu nie może być puste.");
        }
        address = street + " " + houseNumber + " " + zipCode + " " + city;
        this.telefonNumber = telefonNumber;

    }
    @Override
    public String toString() {
        String join = "";
        join += "\nImie: " + firstname +
                "\nNazwisko: " + surname +
                "\nTelefon: " + telefonNumber +
                "\nAdress: ul." + address;
        return join;
    }

    public void setAddress(String street, String houseNumber, String zipCode, String city) {
        String address = street + " " + houseNumber + " " + zipCode + " " + city;
        this.address = address;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getTelefonNumber() {
        return telefonNumber;
    }

    public void setTelefonNumber(int telefonNumber) {
        this.telefonNumber = telefonNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
