package Others;

import PhysiotherapyCabinet.*;

/**
 * Interface określający nazwy ról
 */

public interface RoleName {

    String physiotherapistRole = Physiotherapist.class.getSimpleName();
    String permissionRole = JobPermission.class.getSimpleName();
    String receptionistRole = Receptionist.class.getSimpleName();
    String specializationRole = Specialization.class.getSimpleName();
    String availableVisitTypeRole = AvailableVisitType.class.getSimpleName();
    String visitRole = Visit.class.getSimpleName();
    String customerRole = Customer.class.getSimpleName();
    String roomRole = Room.class.getSimpleName();

}
