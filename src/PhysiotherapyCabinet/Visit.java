package PhysiotherapyCabinet;

import Others.ObjectPlus;
import Others.ObjectPlusPlus;
import Others.RoleName;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Stream;

/**
 * Abstrakycjna klasa opisująca wizyty po ktrój dziedziczą klasy IndividualVisit oraz GroupVisit
 * "część" kompozycji z klasa AvailableVisitType
 * Asocjacja 1..10-1 z klasą Customer
 * Asocjacja *-1 z klasą Receptionist
 * Asocjacja *-1 z klasą Physiotherapist
 * Asocjacja *-1 z klasą Room
 *
 * @see IndividualVisit
 * @see GroupVisit
 * @see Customer
 * @see Receptionist
 * @see Physiotherapist
 * @see Room
 */
public abstract class Visit extends ObjectPlusPlus implements RoleName {

    private static final long serialVersionUID = 2L;
    private LocalDate visitDate;
    private LocalTime visitStartTime;
    private LocalTime visitEndTime;
    private int visitTime;
    private VisitStatus status;
    private static Comparator<Visit> compareByDate = Comparator
            .comparing(Visit::getVisitDate)
            .thenComparing(Visit::getVisitStartTime);

    public Visit(LocalDate visitDate, LocalTime visitStartTime, int visitTime) {
        super();
        if (visitDate == null && visitStartTime == null) {
            throw new NullPointerException("Podaj datę i godzinę wizity");
        }
        this.visitDate = visitDate;
        this.visitStartTime = visitStartTime;
        this.visitTime = visitTime;
        setStatus(VisitStatus.Zarezerwowana);
        setVisitEndTime(visitStartTime.plusMinutes(visitTime));
    }

    /**
     * Rozpoczyna wizytę
     */
    public void startVisit() {
        setStatus(VisitStatus.Rozpoczęta);
    }

    /**
     * Kończy wizytę i ustala jej nowy czas zakończenia
     */
    public void endVisit() {
        setStatus(VisitStatus.Zakończona);
        System.out.print("Zakończona");
        setVisitEndTime(LocalTime.now());
    }

    /**
     * Anuluje wizytę
     */
    public void cancelVisit() {
        setStatus(VisitStatus.Anulowana);
    }

    /**
     * Metoda zwracająca wizyty o podanym statusie w formie dwuwymiarowego wektora
     *
     * @param status
     * @return
     * @throws Exception
     */
    public static Vector<Vector> showVisits(VisitStatus status) throws Exception {
        Vector<Vector> visits = new Vector<>();
        createVisitStream().filter(v -> v.getStatus().equals(status)).sorted(compareByDate).forEach(v -> visits.addElement(createRow(v)));
        return visits;
    }

    /**
     * Metoda zwracająca wizyty z danego dnia w formie dwuwymiarowego wektora
     *
     * @param date
     * @return dwuwymiarowy wektor
     * @throws Exception
     */
    public static Vector<Vector> showVisits(LocalDate date) throws Exception {
        Vector<Vector> visits = new Vector<>();
        createVisitStream().filter(v -> v.getVisitDate().equals(date)).sorted(compareByDate).forEach(v -> visits.addElement(createRow(v)));
        return visits;
    }

    /**
     * Metoda zwracająca wszyszystkie wizyty w formie dwuwymiarowego wektora
     *
     * @return dwuwymiarowy wektor
     * @throws Exception
     */
    public static Vector<Vector> showAllVisits() throws Exception {
        Vector<Vector> visits = new Vector<>();
        createVisitStream().sorted(compareByDate).forEach(v -> visits.addElement(createRow(v)));
        return visits;
    }

    /**
     * Metoda tworząca strumień z istniejących Visit
     *
     * @return strumień wizyt
     * @throws Exception
     */
    public static Stream<Visit> createVisitStream() throws Exception {
        Stream<Visit> visitStream;
        if (ObjectPlus.ifExtentExist(IndividualVisit.class) && ObjectPlus.ifExtentExist(GroupVisit.class)) {
            visitStream = Stream.concat((ObjectPlus.getExtent(IndividualVisit.class)).stream(), (ObjectPlus.getExtent(GroupVisit.class)).stream());
        } else if (!ObjectPlus.ifExtentExist(IndividualVisit.class)) {
            if (!ObjectPlus.ifExtentExist(GroupVisit.class)) {
                throw new Exception("Nienzna klasa ");
            } else {
                visitStream = ObjectPlus.getExtent(GroupVisit.class).stream();
            }
        } else {
            visitStream = ObjectPlus.getExtent(IndividualVisit.class).stream();
        }
        return visitStream;
    }

    /**
     * Służy do stowrzenie pojedyńczej linii (wiersza) opisującego wizytę
     *
     * @param
     * @return wektor stringów
     */
    private static Vector<String> createRow(Visit v) {
        Vector<String> row = new Vector<>();
        row.addElement(v.getVisitDate().toString());
        row.addElement(v.getVisitStartTime().toString());
        row.addElement(v.getVisitEndTime().format(DateTimeFormatter.ofPattern("HH:mm")));
        if (v.isGroupVisit()) row.addElement("tak");
        else row.addElement("nie");
        try {
            row.addElement(((AvailableVisitType) v.getLinks(availableVisitTypeRole)[0]).getVisitTypeName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            row.addElement(((Physiotherapist) v.getLinks(physiotherapistRole)[0]).getFirstname() + " " + ((Physiotherapist) v.getLinks(physiotherapistRole)[0]).getSurname());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (v.getLinks(customerRole).length == 1)
                row.addElement(((Customer) v.getLinks(customerRole)[0]).getFirstname() + " " + ((Customer) v.getLinks(customerRole)[0]).getSurname());
            else
                row.addElement("Liczba klientów: " + ((GroupVisit) v).getGroupSize());
        } catch (Exception e) {
            e.printStackTrace();
        }
        row.addElement(v.getStatus().toString());

        if (v.isGroupVisit())
            row.addElement(((GroupVisit) v).getPrice().toString());
        else
            row.addElement(((IndividualVisit) v).getPrice().toString());
        return row;
    }

    /**
     * Określa czy wizytya jest grupowa
     *
     * @return
     */
    public boolean isGroupVisit() {
        if (this.getClass().getSimpleName().equals(GroupVisit.class.getSimpleName())) {
            return true;
        } else
            return false;
    }
    @Override
    public String toString() {
        String join = "";
        join += "\nWizyta o statusie:" + getStatus().toString() +
                "\nData wizyty: " + getVisitDate() +
                "\nGodzina wizyty: " + getVisitStartTime();

        return join;
    }

    /**
     * Przysłonięcie metody addLink sprawdzające liczność asocjacji
     * @param roleName
     * @param reverseRoleName
     * @param targetObject
     */
    @Override
    public void addLink(String roleName, String reverseRoleName, ObjectPlusPlus targetObject) {

        try {
            if (roleName.equals(receptionistRole)) {
                if (ObjectPlusPlus.checkOneToManySideOne(this, roleName, roleName)) {
                    super.addLink(roleName, reverseRoleName, targetObject, targetObject);
                } else throw new Exception("Liczność większa niż 1");
            } else if (roleName.equals(physiotherapistRole)) {
                if (ObjectPlusPlus.checkOneToManySideOne(this, roleName, roleName)) {
                    super.addLink(roleName, reverseRoleName, targetObject, targetObject);
                } else throw new Exception("Liczność większa niż 1");
            } else if (roleName.equals(roomRole)) {
                if (ObjectPlusPlus.checkOneToManySideOne(this, roleName, roleName)) {
                    super.addLink(roleName, reverseRoleName, targetObject, targetObject);
                } else throw new Exception("Liczność większa niż 1");
            } else if (roleName.equals(customerRole)) {
                if (ObjectPlusPlus.checkOneToNumberSideNumber(customerRole, roleName, this, customerRole, 10)) {
                    super.addLink(roleName, reverseRoleName, targetObject, targetObject);
                    if (isGroupVisit()) ((GroupVisit) this).setGroupSize();
                } else throw new Exception("Liczność większa niż 10");
            } else super.addLink(roleName, reverseRoleName, targetObject, targetObject);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public LocalDate getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(LocalDate visitDate) {
        this.visitDate = visitDate;
    }

    public LocalTime getVisitStartTime() {
        return visitStartTime;
    }

    public void setVisitStartTime(LocalTime visitStartTime) {
        this.visitStartTime = visitStartTime;
    }

    public LocalTime getVisitEndTime() {
        return visitEndTime;
    }

    public void setVisitEndTime(LocalTime visitEndTime) {

        this.visitEndTime = visitEndTime;
    }

    public int getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(int visitTime) {
        this.visitTime = visitTime;
    }

    public VisitStatus getStatus() {
        return status;
    }

    public void setStatus(VisitStatus status) {
        this.status = status;
    }

    public static Comparator<Visit> getCompareByDate() {
        return compareByDate;
    }

}