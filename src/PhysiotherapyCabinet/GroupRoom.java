package PhysiotherapyCabinet;

/**
 * Klasa opisująca pokoje do zajęć grupowych
 *
 */
public class GroupRoom extends Room {
    private static final long serialVersionUID = 2L;
    boolean exerciseLadder;

    public GroupRoom(int roomNumber, int area, boolean airConditioning, boolean infantRoom, boolean changingTable, boolean exerciseLadder, Integer rehabilitationBallsCount) {
        super(roomNumber, area, airConditioning, infantRoom, changingTable, rehabilitationBallsCount);
        this.exerciseLadder = exerciseLadder;
    }
    @Override
    public String toString() {
        String join = super.toString();
        join += "\nDrabinka do ćwiczeń ";
        if (exerciseLadder == true) join += "tak";
        else join += "Brak";
        return join;
    }
}
