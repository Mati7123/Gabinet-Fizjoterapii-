package PhysiotherapyCabinet;

import Others.ObjectPlusPlus;
import Others.RoleName;

import java.time.LocalDate;
import java.time.LocalTime;

public class IndividualVisit extends Visit implements RoleName {
    private static final long serialVersionUID = 2L;

    private int price;

    /**
     * Konstruktor prywatny
     * @param visitDate
     * @param visitStartTime
     * @param visitTime
     * @param price
     * @throws Exception
     */
    private IndividualVisit(LocalDate visitDate, LocalTime visitStartTime, int visitTime, int price) throws Exception{
        super(visitDate, visitStartTime, visitTime);
        this.price = price;

    }

    /**
     * Metoda umożliwjaąca utowrzenie wizyty indywidualnej po podanie typu wizyty
     * @param availableVisitType - typ wizyty
     * @param visitDate
     * @param visitStartTime
     * @param visitTime
     * @param price
     * @return
     * @throws Exception
     */
    public static IndividualVisit createIndvidualVisit(AvailableVisitType availableVisitType, LocalDate visitDate, LocalTime visitStartTime, int visitTime, int price) throws Exception {
        if (availableVisitType == null){
            throw new Exception("Nie podano typu wizity");
        }
        IndividualVisit individualVisit  = new IndividualVisit(visitDate,  visitStartTime,visitTime,price);
        availableVisitType.addPart(visitRole, availableVisitTypeRole, individualVisit);
        individualVisit.setPrice(price);
        return individualVisit;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(int price)  throws Exception {
        ObjectPlusPlus o  = this.getLinks(availableVisitTypeRole)[0];
        int minPrice = ((AvailableVisitType) o).getMinPrice();
        int maxPrice = ((AvailableVisitType) o).getMaxPrice();
        if(price <=maxPrice && price >= minPrice){
            this.price = price;
        }
        else{
            throw new  IllegalArgumentException("Wartosc ceny nie zawiera się w przedziale: " +minPrice + " - " +maxPrice );
        }
    }

}
