package exceptions;

/**
 * ���������� �������������, ����� ������������ ������������ id
 */
public class NotUniqueIdException extends Exception {
    @Override
    public String toString() {
        return "����� id ��� ����������";
    }
}