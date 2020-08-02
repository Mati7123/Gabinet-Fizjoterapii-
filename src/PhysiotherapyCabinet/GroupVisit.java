package PhysiotherapyCabinet;

import Others.ObjectPlusPlus;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Klasa opisująca wizyty grupowe
 */
public class GroupVisit extends Visit {


    private static final long serialVersionUID = 2L;
    private int price;
    private int groupSize = 0;

    /**
     * Konstruktor prywatny
     *
     * @param visitDate
     * @param visitStartTime
     * @param visitTime
     * @param price
     * @return
     * @throws Exception
     */
    private GroupVisit(LocalDate visitDate, LocalTime visitStartTime, int visitTime, int price) {
        super(visitDate, visitStartTime, visitTime);
        this.price = price;
    }

    /**
     * Metoda umożliwiająca utworzenie wizyty po podaniu jej typu
     *
     * @param availableVisitType - zapewnia brak możliwośc utowrzenia obiektu bez istniejącego typu
     * @param visitDate
     * @param visitStartTime
     * @param visitTime
     * @param price
     * @return
     * @throws Exception
     */

    public static GroupVisit createGroupVisit(AvailableVisitType availableVisitType, LocalDate visitDate, LocalTime visitStartTime, int visitTime, int price) throws Exception {
        if (availableVisitType == null) {
            throw new Exception("Nie podano typu wizity");
        }
        GroupVisit groupVisit = new GroupVisit(visitDate, visitStartTime, visitTime, price);
        availableVisitType.addPart(visitRole, availableVisitTypeRole, groupVisit);
        groupVisit.setPrice(price);
        return groupVisit;
    }

    /**
     * Określa cene wizyty w zakresie zgodnym z danym typem wizyty
     *
     * @param price
     * @throws Exception
     */
    public void setPrice(int price) throws Exception {
        ObjectPlusPlus o = this.getLinks(availableVisitTypeRole)[0];
        int minPrice = ((AvailableVisitType) o).getMinPrice();
        int maxPrice = ((AvailableVisitType) o).getMaxPrice();
        if (price <= maxPrice && price >= minPrice) {
            this.price = price;
        } else {
            throw new IllegalArgumentException("Wartosc ceny nie zawiera się w przedziale: " + minPrice + " - " + maxPrice);
        }
    }

    /**
     * Określa ilość osób biorących udział w wizycie
     *
     * @return
     * @throws Exception
     */
    public int setGroupSize() throws Exception {

        if (this.areLinks(customerRole)) {
            groupSize = this.getLinks(customerRole).length;

        }
        return groupSize;
    }


    public Integer getPrice() {
        return price;
    }

    public int getGroupSize() {
        return groupSize;
    }

    /**
     * Zwraca zysk z wizyty
     *
     * @return
     */
    public Integer getProfit() {
        return getPrice() * getGroupSize();

    }
}
