package common.managers;

import common.models.Position;
import common.models.Status;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class WorkerValidateManager {
    public static boolean isId(String value) {
        return ValidateManager.isInteger(value) && Integer.parseInt(value) > 0;
    }

    public static Integer getId(String value) {
        return Integer.valueOf(value);
    }

    public static boolean isName(String value) {
        return !value.equals("");
    }

    public static String getName(String value) {
        return value;
    }

    public static boolean isCoordinateX(String value) {
        return ValidateManager.isInteger(value);
    }

    public static Integer getCoordinateX(String value) {
        return Integer.valueOf(value);
    }

    public static boolean isCoordinateY(String value) {
        return ValidateManager.isInteger(value);
    }

    public static Integer getCoordinateY(String value) {
        return Integer.valueOf(value);
    }

    public static boolean isCreationDate(String value) {
        return ValidateManager.isDateTime(value);
    }

    public static LocalDateTime getCreationDate(String value) {
        return LocalDateTime.parse(value);
    }

    public static boolean isSalary(String value) {
        if (value.equals("")) return true;
        return ValidateManager.isFloat(value) && Float.parseFloat(value) > 0;
    }

    public static Float getSalary(String value) {
        if (value.equals("")) return null;
        return Float.valueOf(value);
    }

    public static boolean isPosition(String value) {
        if (value.equals("")) return true;
        return ValidateManager.isEnum(value, Position.class);
    }

    public static Position getPosition(String value) {
        if (value.equals("")) return null;
        return Position.valueOf(value);
    }

    public static boolean isStatus(String value) {
        if (value.equals("")) return true;
        return ValidateManager.isEnum(value, Status.class);
    }

    public static Status getStatus(String value) {
        if (value.equals("")) return null;
        return Status.valueOf(value);
    }

    public static boolean isBirthday(String value) {
        if (value.equals("")) return true;
        return ValidateManager.isDate(value);
    }

    public static LocalDate getBirthday(String value) {
        if (value.equals("")) return null;
        return LocalDate.parse(value);
    }

    public static boolean isHeight(String value) {
        return ValidateManager.isFloat(value) && Float.parseFloat(value) > 0;
    }

    public static float getHeight(String value) {
        return Float.parseFloat(value);
    }

    public static boolean isPassportID(String value) {
        if (value.equals("")) return true;
        return !value.isBlank() && value.length() >= 7;
    }

    public static String getPassportID(String value) {
        if (value.equals("")) return null;
        return value;
    }

    public static boolean isCreatorId(String value) {
        return ValidateManager.isInteger(value) && Integer.parseInt(value) > 0;
    }

    public static Integer getCreatorId(String value) {
        return Integer.valueOf(value);
    }

}
