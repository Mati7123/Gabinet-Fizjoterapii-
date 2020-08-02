package Others;

import java.io.Serializable;

import java.io.PrintStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;

/**
 * The class which simplifies connections between objects. Because it inherits from the ObjectPlus, it also helps with extent management.
 *
 * @author Mariusz Trzaska
 * Fill free to send me any remarks: mtrzaska@pjwstk.edu.pl
 */
public class ObjectPlusPlus extends ObjectPlus implements Serializable {
    private static final long serialVersionUID = 2L;
    /**
     * Zachowuje informacje o wszystkich częściach połączonych z dowolnym obiektem
     */
    protected static HashSet<ObjectPlusPlus> allParts = new HashSet<>();
    /**
     * Zachowuje informacje o wszystkich połaczeniach obiektu
     */
    protected Hashtable<String, HashMap<Object, ObjectPlusPlus>> links = new Hashtable<>();

    /**
     * Konstruktor
     */
    public ObjectPlusPlus() {
        super();
    }

    /**
     * Tworzy nowe połączenie
     *
     * @param roleName
     * @param reverseRoleName
     * @param targetObject
     * @param qualifier
     * @param counter
     */
    private void addLink(String roleName, String reverseRoleName, ObjectPlusPlus targetObject, Object qualifier, int counter) {
        HashMap<Object, ObjectPlusPlus> objectLink;

        if (counter < 1) {
            return;
        }

        if (links.containsKey(roleName)) {

            objectLink = links.get(roleName);
        } else {
            objectLink = new HashMap<Object, ObjectPlusPlus>();
            links.put(roleName, objectLink);
        }

        if (!objectLink.containsKey(qualifier)) {
            objectLink.put(qualifier, targetObject);
            targetObject.addLink(reverseRoleName, roleName, this, this, counter - 1);
        }
    }

    /**
     * Tworzy nową asocjację do danego obiektu (opcjonalnie jako asocjacja kwalfikowana)
     *
     * @param roleName
     * @param reverseRoleName
     * @param targetObject
     * @param qualifier
     */

    public void addLink(String roleName, String reverseRoleName, ObjectPlusPlus targetObject, Object qualifier) {
        addLink(roleName, reverseRoleName, targetObject, qualifier, 2);
    }

    /**
     * Tworzy nowe asocacje do do danego obiektu
     *
     * @param roleName
     * @param reverseRoleName
     * @param targetObject
     */

    public void addLink(String roleName, String reverseRoleName, ObjectPlusPlus targetObject) {
        addLink(roleName, reverseRoleName, targetObject, targetObject);
    }

    /**
     * Dodaje informacje o asocjacji - wykorzystywane dla kompozycji
     *
     * @param roleName
     * @param reverseRoleName
     * @param partObject
     * @throws Exception
     */
    public void addPart(String roleName, String reverseRoleName, ObjectPlusPlus partObject) throws Exception {
        if (allParts.contains(partObject)) {
            throw new Exception("Ta czesc jest juz powiazana z jakas caloscia!");
        }

        addLink(roleName, reverseRoleName, partObject);

        allParts.add(partObject);
    }

    /**
     * Tworzy tablice połączonych obiektów dla danej nazwy roli
     *
     * @param roleName
     * @return
     * @throws Exception
     */
    public ObjectPlusPlus[] getLinks(String roleName) throws Exception {
        HashMap<Object, ObjectPlusPlus> objectLinks;

        if (!links.containsKey(roleName)) {
            throw new Exception("Brak powiazan dla roli: " + roleName);
        }

        objectLinks = links.get(roleName);

        return (ObjectPlusPlus[]) objectLinks.values().toArray(new ObjectPlusPlus[0]);
    }

    /**
     * Określa czy istnieją asocjacje dla danej nazwy roli
     *
     * @param roleName
     * @return
     */

    public boolean areLinks(String roleName) {
        if (!links.containsKey(roleName)) {
            return false;
        }

        HashMap<Object, ObjectPlusPlus> objectLinks = links.get(roleName);
        return objectLinks.size() > 0;
    }

    /**
     * Przekazuje asocjacje do podanego strumienia
     *
     * @param roleName
     * @param stream
     * @throws Exception
     */
    public void showLinks(String roleName, PrintStream stream) throws Exception {
        HashMap<Object, ObjectPlusPlus> objectLink;

        if (!links.containsKey(roleName)) {
            // Brak powiazan dla tej roli
            throw new Exception("No links for the role: " + roleName);
        }

        objectLink = links.get(roleName);

        Collection col = objectLink.values();

        stream.println(this.toString() + " (" + this.getClass().getSimpleName() + ") links for the '" + roleName + "' role:");

        for (Object obj : col) {
            stream.println("   " + obj);
        }
    }


    /**
     * Zwraca obiekt odnaleziony na podstawie kwalifikatora (dla podanej nazwy roli).
     * Dziala w oparciu o asocjacje kwalifikowana.
     *
     * @param roleName
     * @param qualifier
     * @return
     * @throws Exception
     */

    public ObjectPlusPlus getLinkedObject(String roleName, Object qualifier) throws Exception {
        HashMap<Object, ObjectPlusPlus> objectLinks;

        if (!links.containsKey(roleName)) {
            // Brak powiazan dla tej roli
            throw new Exception("Brak powiazan dla roli: " + roleName);
        }

        objectLinks = links.get(roleName);
        if (!objectLinks.containsKey(qualifier)) {
            // Brak powiazan dla tej roli
            throw new Exception("Brak powiazania dla kwalifikatora: " + qualifier);
        }
        return objectLinks.get(qualifier);
    }

    /**
     * Usuwa asocjacje o podanej nazwie roi
     *
     * @param roleName
     * @throws Exception
     */
    public void deleteLinks(String roleName) {
        HashMap<Object, ObjectPlusPlus> objectLink;

        if (links.containsKey(roleName)) {

            links.remove(roleName);
        }

    }

    /**
     * Sprawdzenie asocjacji 1 do * po stronie wiele
     * @param roleName
     * @param reverseRoleName
     * @param targetObject
     * @param checkRoleName
     * @return
     */
    public static boolean checkOneToManySideMany(String roleName, String reverseRoleName, ObjectPlusPlus targetObject, String checkRoleName) {
        try {
            if (roleName.equals(checkRoleName)) {
                if (targetObject.areLinks(reverseRoleName)) return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * Sprawdzenie asocjacji 1 do Number
     * @param roleName
     * @param reverseRoleName
     * @param targetObject
     * @param checkRoleName
     * @param number
     * @return
     */
    public static boolean checkOneToNumberSideNumber(String roleName, String reverseRoleName, ObjectPlusPlus targetObject, String checkRoleName, int number) {
        try{
            if (roleName.equals(checkRoleName)){
                if (targetObject.areLinks(reverseRoleName)){
                    if (targetObject.getLinks(reverseRoleName).length <= (number -1) ){
                        return true;
                    }else  {
                        return false;
                    }
                }return true;
            }return true;
        }
        catch (Exception e1){
            e1.printStackTrace();
        }
        return true;
    }

    /**
     * Sprawdzenie asocjacji 1 do wiele po stronie jeden
     * @param object
     * @param roleName
     * @param checkRoleName
     * @return
     */
    public static boolean checkOneToManySideOne(ObjectPlusPlus object, String roleName, String checkRoleName) {
        try {
            if (roleName.equals(checkRoleName))
                if (object.areLinks(roleName)) return false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }


}