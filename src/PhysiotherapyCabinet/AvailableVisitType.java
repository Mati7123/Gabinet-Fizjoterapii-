package PhysiotherapyCabinet;

import Others.ObjectPlus;
import Others.ObjectPlusPlus;
import Others.RoleName;

/**
 * Klasa przechowująca informacje o typach wizyt oferowanych przez gabinet.
 * Część "całość" kompozycji z klasa Visit
 *
 * @see Visit
 */
public class AvailableVisitType extends ObjectPlusPlus implements RoleName {

    private static final long serialVersionUID = 2L;
    private String visitTypeName;
    private int minPrice;
    private int maxPrice;
    private String description;


    public AvailableVisitType(String visitTypeName, int minPrice, int maxPrice) {
        super();
        if (visitTypeName == null) {
            throw new NullPointerException("Pole nie może być puste.");
        }
        this.visitTypeName = visitTypeName;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }
    @Override
    public String toString() {

        String join = "";
        join += "\nNazwa typu wizty: " + visitTypeName +
                "\nPrzedział cen od " + minPrice + " do " + maxPrice + " zł";
        return join;
    }

    /**
     * Zapewnia usnięcie zarówno "całości" jak jego "części" oraz połączenia między nimi
     *
     * @param role
     * @param reverseRoleName
     * @throws Exception
     */
    public void deleteObjectAndLink(String role, String reverseRoleName) throws Exception {
        for (Object o : this.getLinks(role)) {
            ObjectPlus.deleteEkstension(this);
            ObjectPlus.deleteEkstension(o);
            deleteLinks(role);
            ((Visit) o).deleteLinks(reverseRoleName);
        }
    }


    public String getVisitTypeName() {
        return visitTypeName;
    }

    public void setVisitTypeName(String visitTypeName) {
        this.visitTypeName = visitTypeName;
    }

    public int getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(int minPrice) {
        this.minPrice = minPrice;
    }

    public int getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(int maxPrice) {
        this.maxPrice = maxPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
