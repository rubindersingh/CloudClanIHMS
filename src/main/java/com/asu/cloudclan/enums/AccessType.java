package com.asu.cloudclan.enums;

/**
 * Created by rubinder on 10/11/16.
 */
public enum AccessType {
    A(5), //Author
    AD(4), //Admin
    RW(3), //Read write
    W(2), //Write only
    R(1); //Read only

    int privilege;
    AccessType(int privilege) {
        this.privilege = privilege;
    }

    public static boolean hasWriteAccess(String accessType) {
        return AccessType.valueOf(accessType).privilege >= AccessType.W.privilege;
    }

    public static boolean hasAdminAccess(String accessType) {
        return AccessType.valueOf(accessType).privilege >= AccessType.AD.privilege;
    }

    public static boolean hasReadAccess(String accessType) {
        return AccessType.valueOf(accessType).privilege != AccessType.W.privilege;
    }
}
