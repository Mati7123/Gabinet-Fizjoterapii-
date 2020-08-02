package PhysiotherapyCabinet;

import Others.ObjectPlus;
import Others.ObjectPlusPlus;
import Others.RoleName;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.spec.ECField;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;

/**
 * Klasa opisująca klientów kliniki
 * asocjacja *..* z klasą Visit
 *
 * @see Visit
 */
public class Customer extends Person implements RoleName {

    private static final long serialVersionUID = 2L;
    private int customerNumber;
    private static Hashtable<Integer, ObjectPlus> customerNumberMap = new Hashtable<>();
    private String parentFirstname;
    private String paretSurname;
    private LocalDate birthDate;
    private boolean regularCustomer = false;
    private Integer discount = 0;
    private Vector<String> medicalHisotry = new Vector<>();

    public Customer(String firstname, String surname, int telefonNumber, String street, String houseNumber, String zipCode, String city, String parentFirstname, String paretSurname, LocalDate birthDate) throws Exception {
        super(firstname, surname, telefonNumber, street, houseNumber, zipCode, city);
        if (parentFirstname == null) {
            throw new NullPointerException("Pole imie opiekuna nie może być puste.");
        }
        this.parentFirstname = parentFirstname;
        if (surname == null) {
            throw new NullPointerException("Pole nazwisko opiekuna nie może być puste.");
        }
        this.paretSurname = paretSurname;
        if (birthDate == null) {
            throw new NullPointerException("Pole nazwisko opiekuna nie może być puste.");
        }
        this.birthDate = birthDate;

        customerNumber = setClinetNumber();
        if (!customerNumberMap.containsKey(customerNumber)) {
            customerNumberMap.put(customerNumber, this);
        } else {
            throw new NullPointerException("Taki numer klienta już istnieje");
        }


    }
    @Override
    public String toString() {
        String join = "Numer klienta: " + customerNumber +
                super.toString() +
                "\n Data urodzenia: " + birthDate +
                "\nWiek :" + getAge();

        return join;
    }

    /**
     * Nadaje klientowi unikatowy numer
     *
     * @return
     * @throws Exception
     */
    private Integer setClinetNumber() throws Exception {
        int customerNumber;
        if (ObjectPlus.getExtent(Customer.class).isEmpty()) {
            customerNumber = 0;
        } else {
            customerNumber = Customer.customerNumberMap.size() + 1;
            return customerNumber;
        }
        return customerNumber;
    }


    /**
     * zwraca klienta o podanym numerze
     *
     * @param customerNumber
     * @return
     */

    public static Customer getCustomerByNumber(int customerNumber) {
        return (Customer) Customer.customerNumberMap.get(customerNumber);
    }

    /**
     * zapisuje do pliku mapę numerów klientów
     *
     * @param stream
     * @throws IOException
     */
    public static void saveCustomerMap(ObjectOutputStream stream) throws IOException {
        stream.writeObject(customerNumberMap);
    }

    /**
     * Wczytuje z pliku mapę numerów klientów
     *
     * @param stream
     * @throws Exception
     */
    public static void readCustomerMap(ObjectInputStream stream) throws Exception {
        customerNumberMap = (Hashtable) stream.readObject();
    }

    /**
     * Zwraca wiek klienta
     *
     * @return
     */
    public int getAge() {
        LocalDate currentDate = LocalDate.now();
        return Period.between(birthDate, currentDate).getYears();
    }

    /**
     * Przysłonięcie metody addLink sprawdzające liczność asocjacji
     * @param roleName
     * @param reverseRoleName
     * @param targetObject
     */
    @Override
    public void addLink(String roleName, String reverseRoleName, ObjectPlusPlus targetObject) {
        try{
            if (ObjectPlusPlus.checkOneToNumberSideNumber(roleName,reverseRoleName,targetObject, visitRole,10))
                super.addLink(roleName, reverseRoleName, targetObject, targetObject);
            else throw new Exception("Liczność większa niż 10");

        }
        catch (Exception e1){
            e1.printStackTrace();
        }
    }



    public int getCustomerNumber() {

        return customerNumber;
    }

    public void setCustomerNumber(int customerNumber) {
        this.customerNumber = customerNumber;
    }

    public String getParentFirstname() {
        return parentFirstname;
    }

    public void setParentFirstname(String parentFirstname) {
        this.parentFirstname = parentFirstname;
    }

    public String getParetSurname() {
        return paretSurname;
    }

    public void setParetSurname(String paretSurname) {
        this.paretSurname = paretSurname;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public boolean isRegularCustomer() {
        return regularCustomer;
    }

    public void setRegularCustomer(boolean regularCustomer) {
        if (regularCustomer == true) {
            setDiscount(10);
        }
        this.regularCustomer = regularCustomer;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Vector<String> getMedicalHisotry() {
        return medicalHisotry;
    }

    public void setMedicalHisotry(Vector<String> medicalHisotry) {
        this.medicalHisotry = medicalHisotry;
    }


}

