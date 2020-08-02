package PhysiotherapyCabinet;

import Others.ObjectPlus;
import Others.ObjectPlusPlus;
import Others.RoleName;

import java.math.BigInteger;
import java.time.LocalDate;

/**
 * Klasa dziedzicząca po klasie Employee
 * Asocjacja 1..* z klasą Visit
 *
 * @see Visit
 */
public class Receptionist extends Employee implements RoleName {
    private static final long serialVersionUID = 2L;

    private String email;

    public Receptionist(String firstname, String surname, int telefonNumber, String street, String houseNumber, String zipCode, String city, double salary, long pesel, BigInteger accountNumber, LocalDate medicalExaminationDate, String email) {
        super(firstname, surname, telefonNumber, street, houseNumber, zipCode, city, salary, pesel, accountNumber, medicalExaminationDate);
        if (email == null) {
            throw new NullPointerException("Pole email nie moze być puste");
        }
        this.email = email;
    }
    @Override
    public String toString() {
        return super.toString() + "\nAdres email: " + email;

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Przysloniecie metody addLink sprawdzające liczność asocjacji
     * @param roleName
     * @param reverseRoleName
     * @param targetObject
     */
    @Override
    public void addLink(String roleName, String reverseRoleName, ObjectPlusPlus targetObject) {
        if (ObjectPlusPlus.checkOneToManySideMany(roleName, reverseRoleName, targetObject, visitRole))
            super.addLink(roleName, reverseRoleName, targetObject, targetObject);
        else {
        try{
            throw new Exception("Liczność większa niż 1");
        }
        catch (Exception e1){
            e1.printStackTrace();
        }
    }
}



}
