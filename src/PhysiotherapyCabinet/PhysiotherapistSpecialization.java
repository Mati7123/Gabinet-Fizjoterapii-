package PhysiotherapyCabinet;

import Others.ObjectPlusPlus;
import Others.RoleName;

import java.time.LocalDate;

/**
 * Klasa asocjacji między klasami Physiotherapist i Specialization
 *
 * @see Specialization
 * @see Physiotherapist
 */
public class PhysiotherapistSpecialization extends ObjectPlusPlus implements RoleName {
    private static final long serialVersionUID = 2L;
    private LocalDate date;


    /**
     * Konstuktor tworzący asocjację między obiektami
     *
     * @param date            - atrybut asocjacji
     * @param specialization
     * @param physiotherapist
     */
    public PhysiotherapistSpecialization(LocalDate date, Specialization specialization, Physiotherapist physiotherapist) {
        if (date == null) {
            throw new NullPointerException("Pole data nie może być puste.");
        }
        this.date = date;
        specialization.addLink(RoleName.physiotherapistRole, RoleName.specializationRole, physiotherapist);
        physiotherapist.addLink(RoleName.specializationRole, RoleName.physiotherapistRole, specialization);
    }

    public String toString() {
        String join = "";
        join += "\nSpecjalizacja Fizjoterapeuty:" +
                "\nData przyznania: " + date;

        return join;
    }

}