package PhysiotherapyCabinet;

/**
 * Klasa opisująca pokoje do wizyt indywidualnych
 */
public class IndividualRoom  extends Room {
    private static final long serialVersionUID = 2L;
    boolean massageTable;

    public IndividualRoom(int roomNumber, int area, boolean airConditioning, boolean infantRoom, boolean changingTable, boolean massageTable, Integer rehabilitationBallsCount) {
        super(roomNumber, area, airConditioning, infantRoom, changingTable, rehabilitationBallsCount);
        this.massageTable = massageTable;
    }
    @Override
    public String toString() {
        String join = super.toString();
        join += "\nStół do masażu: ";
        if (massageTable ==true) join+= "tak";
        else join += "Brak";
        return join;
    }
}
