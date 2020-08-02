package PhysiotherapyCabinet;

import Others.ObjectPlusPlus;
import Others.RoleName;

/**
 * Abstrakacyjna klasa opisująca pokoje.
 * Asocjacja 1..* z klasą Visit
 *
 * @see Visit
 */
public abstract class Room extends ObjectPlusPlus implements RoleName {
    private static final long serialVersionUID = 2L;
    private int roomNumber;
    private int area;
    private boolean airConditioning;
    private boolean infantRoom;
    private boolean changingTable;
    private Integer rehabilitationBallsCount;

    /**
     * Konstruktor
     *
     * @param roomNumber
     * @param area
     * @param airConditioning
     * @param infantRoom               określa czy pomieszczenie jest przystosowane do zajęc z niemowlętami (Pokój dla niemowląt)
     * @param changingTable
     * @param rehabilitationBallsCount
     */
    public Room(int roomNumber, int area, boolean airConditioning, boolean infantRoom, boolean changingTable, Integer rehabilitationBallsCount) {
        super();
        this.roomNumber = roomNumber;
        this.area = area;
        this.airConditioning = airConditioning;
        this.infantRoom = infantRoom;
        this.changingTable = changingTable;
        this.rehabilitationBallsCount = rehabilitationBallsCount;

    }
    @Override
    public String toString() {
        String join = "";
        join += "Pokój numer: " + roomNumber +
                "\nPowierzchnia: " + area +
                "\nKlimatyzowany: ";
        if (airConditioning == true) join += "tak";
        else join += "nie";
        if (infantRoom == true) join += "\nPokój niemowlęcy";
        else join += "\nPokój dla dzieci powyżej 1 roku";
        join += "\nPrzewijak: ";
        if (changingTable == true) join += "tak";
        else join += "Brak";
        join += "\nPiłki rehabilitacyjne: ";
        if (rehabilitationBallsCount == null || rehabilitationBallsCount == 0) join += "Brak";
        else join += rehabilitationBallsCount + " sztuk";


        return join;
    }
    /**
     * Przysloniecie metody addLink sprawdzające liczność asocjacji
     * @param roleName
     * @param reverseRoleName
     * @param targetObject
     */
    @Override
    public void addLink(String roleName, String reverseRoleName, ObjectPlusPlus targetObject) {
        if (ObjectPlusPlus.checkOneToManySideMany(roleName, reverseRoleName, targetObject, roomRole))
            super.addLink(roleName, reverseRoleName, targetObject, targetObject);
        else try{
                throw new Exception("Liczność większa niż 1");
            }
            catch (Exception e1){
                e1.printStackTrace();
            }
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public boolean isAirConditioning() {
        return airConditioning;
    }

    public void setAirConditioning(boolean airConditioning) {
        this.airConditioning = airConditioning;
    }

    public boolean isInfantRoom() {
        return infantRoom;
    }

    public void setInfantRoom(boolean infantRoom) {
        this.infantRoom = infantRoom;
    }

    public boolean isChangingTable() {
        return changingTable;
    }

    public void setChangingTable(boolean changingTable) {
        this.changingTable = changingTable;
    }
}
