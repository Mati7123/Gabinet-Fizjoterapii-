package Others;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Hashtable;

import java.util.Vector;

/**
 * @author Mariusz Trzaska
 * Fill free to send me any remarks: mtrzaska@pjwstk.edu.pl
 *
 */
public class ObjectPlus implements Serializable {
    private static final long serialVersionUID = 2L;

    private static Hashtable extents = new Hashtable<>();
    private static Vector extent;

    /**
     * Konstruktor
     */
    public ObjectPlus() {
        extent = null;
        Class aClass = this.getClass();

        if (extents.containsKey(aClass)) {
            extent = (Vector) extents.get(aClass);
        } else {
            extent = new Vector();
            extents.put(aClass, extent);
        }
        extent.add(this);

    }

    /**
     * Metoda zapisująca wszystkie ekstensje do podanego strumienia
     *
     * @param stream
     * @throws IOException
     */
    public static void saveExtents(ObjectOutputStream stream) throws IOException {
        stream.writeObject(extents);
    }

    /**
     * Metoda odczytująca wszystkie ekstensje z podanego strumienia
     *
     * @param stream
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static void readExtents(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        extents = (Hashtable) stream.readObject();
    }

    /**
     * Meotda Pokazująca ekstensje danej klasy
     *
     * @param klasa
     * @throws Exception
     */
    public static void showExtents(Class klasa) throws Exception {
        Vector extent = null;
        if (extents.containsKey(klasa)) {
            extent = (Vector) extents.get(klasa);
        } else {
            throw new Exception("Nienzna klasa " + klasa);
        }
        System.out.println("Ekstensja klasy: " + klasa.getSimpleName());
        for (Object o : extent) {
            System.out.println(o + "\n");
        }
    }

    /**
     * Zwraca ekstensje danej klasy
     * @param klasa
     * @return
     * @throws ClassNotFoundException
     */
    public static Vector getExtent(Class klasa) throws ClassNotFoundException {
        Vector extent = null;
        if (extents.containsKey(klasa)) {
            extent = (Vector) extents.get(klasa);
        } else {
            throw new ClassNotFoundException("Nienzna klasa " + klasa);
        }
        return extent;
    }

    /**
     * Metoda określająca czy istnieje ekstensja danej klasy
     *
     * @param klasa
     * @throws Exception
     */
    public static boolean ifExtentExist(Class klasa) {
        if (extents.containsKey(klasa)) {
            return true;
        } else return false;
    }

    /**
     * Metoda usuwająca ekstensje
     *
     * @param name
     * @param <T>
     */

    public static <T> void deleteEkstension(T name) {
        Vector<T> extent = (Vector<T>) extents.get(name.getClass());
        extent.remove(name);
    }

}

