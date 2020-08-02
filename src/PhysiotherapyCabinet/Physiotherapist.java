package PhysiotherapyCabinet;

import Others.ObjectPlus;
import Others.ObjectPlusPlus;
import Others.RoleName;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Klasa dziedząca po klasie Employee
 * Asocjacja kwalfikowna z klasą JobPermission
 * Asocjacja 1..* z klasą Visit
 * Asocjacja *..* z atrybutem z klasą Specialization realizowana przez dodatkową klasę PhysiotherapistSpecialization
 *
 * @see Specialization
 * @see PhysiotherapistSpecialization
 * @see Employee
 * @see Visit
 * @see JobPermission
 */
public class Physiotherapist extends Employee implements RoleName {
    private static final long serialVersionUID = 2L;

    private ArrayList<String> languageList = new ArrayList<>();


    public Physiotherapist(String firstname, String surname, int telefonNumber, String street, String houseNumber, String zipCode, String city, double salary, long pesel, BigInteger accountNumber, LocalDate medicalExaminationDate, String language) {
        super(firstname, surname, telefonNumber, street, houseNumber, zipCode, city, salary, pesel, accountNumber, medicalExaminationDate);
        if (language == null) {
            throw new NullPointerException("Fizjoterapueta musi znać przynajmniej jeden język");
        }
        languageList.add(language);

    }
    @Override
    public String toString() {
        String join = super.toString();
        join += "\nJęzyki: " + languageList +
                "\nSpecjalizacje: ";
        try {
            for (ObjectPlusPlus o : this.getLinks(specializationRole)) {
                join += ((Specialization) o).getSpecName() + " ";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return join;
    }

    public ArrayList<String> getLanguageList() {
        return languageList;
    }

    public void addLanguage(String language) {
        languageList.add(language);
    }

    public void setLanguageList(ArrayList<String> languageList) {
        this.languageList = languageList;
    }

    /**
     * Przysłonięcie metody addLink sprawdzające liczność asocjacji
     * @param roleName
     * @param reverseRoleName
     * @param targetObject
     */
    @Override
    public void addLink(String roleName, String reverseRoleName, ObjectPlusPlus targetObject){
        if (ObjectPlusPlus.checkOneToManySideMany(roleName, reverseRoleName, targetObject, physiotherapistRole))
            super.addLink(roleName, reverseRoleName, targetObject, targetObject);
        else try{
            throw new Exception("Lczność większa niż 1");
        }
        catch (Exception e1){
            e1.printStackTrace();
        }
    }
}
