package common.responses;

/**
 * Ответ на запрос о валидации.
 */
public class ValidationResponse extends Response {
    private boolean status;
    // status == true - серверная валидация прошла успешно, аргументы корректны
    // status == false - аргументы некорректные

    private String errorMessage;  //errorMessage есть, если status==false

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean getStatus() {
        return status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
