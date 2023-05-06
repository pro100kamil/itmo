package common.responses;

/**
 * Ответ на запрос о выполнении команды.
 * При выполнении нужно провести ещё раз серверную валидацию, потому что что-то могло поменяться.
 */
public class CommandResponse extends Response {
    private boolean status;
    // status == true - аргументы корректны, команда выполнилась успешно
    // status == false - аргументы некорректные
    private String errorMessage;  //errorMessage есть, если status==false
    private String result;  //результат выполнения команды. res есть, если status == true

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
