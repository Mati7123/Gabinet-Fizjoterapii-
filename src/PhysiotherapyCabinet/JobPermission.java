package PhysiotherapyCabinet;

import Others.ObjectPlusPlus;

/**
 * Klasa określiająca uprawienia które mogą posiadać Fizjoterapeuci
 * Asocjacja kwalfikowana z klasa Physiotherapist
 *
 * @see Physiotherapist
 */

public class JobPermission extends ObjectPlusPlus {
    private static final long serialVersionUID = 2L;
    private String permissionName;
    private int permissionNumber;

    public JobPermission(String permissionName) {
        super();
        if (permissionName == null) {
            throw new NullPointerException("Pole nie może być puste.");
        }
        this.permissionName = permissionName;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public int getPermissionNumber() {
        return permissionNumber;
    }

    public void setPermissionNumber(int permissionNumber) {
        this.permissionNumber = permissionNumber;
    }
    @Override
    public String toString() {
        return permissionName;
    }
}
