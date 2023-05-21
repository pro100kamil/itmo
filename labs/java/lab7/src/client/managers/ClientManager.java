package client.managers;

import client.Configuration;
import common.commands.CommandDescription;
import common.consoles.Console;
import common.consoles.StandardConsole;
import common.exceptions.EndInputException;
import common.exceptions.EndInputWorkerException;
import common.models.User;
import common.models.Worker;
import common.network.requests.*;
import common.network.responses.CommandResponse;
import common.network.responses.GetAllCommandsResponse;
import common.network.responses.ValidationResponse;

import java.io.IOException;

/**
 * Класс клиентского менеджера.
 * Получает все команды из сервера, отправляет команду на сервер и обрабатывает результат.
 */
public class ClientManager {
    private static final Console console = new StandardConsole();
    private final Client client;

    private User user;

    public ClientManager() {
        client = new Client(Configuration.getHost(), Configuration.getPort());
    }

    public User getUser() {
        return user;
    }

    public CommandDescription[] getAllCommands() throws IOException, ClassNotFoundException {
        client.start();
        writeGetAllCommandsRequest();
        GetAllCommandsResponse response = (GetAllCommandsResponse) client.getObject();
        client.close();
        return response.getCommands();
    }

    public void writeRequest(Request request) throws IOException {
        request.setUser(user);
        client.writeObject(request);
    }

    public void writeGetAllCommandsRequest() throws IOException {
        writeRequest(new GetAllCommandsRequest());
    }

    public void writeValidationRequest(CommandDescription command) throws IOException {
        writeRequest(new ValidationRequest(command));
    }

    public void writeCommandRequest(CommandDescription command) throws IOException {
        writeRequest(new CommandRequest(command));
    }

    public void writeUpdateCollectionRequest() throws IOException {
//        writeRequest(new UpdateCollectionHistoryRequest());
        client.start();
        UpdateCollectionHistoryRequest request = new UpdateCollectionHistoryRequest();
        request.setUser(user);
        client.writeObject(request);
        client.close();
    }

    public void commandHandler(InputManager inputManager, CommandDescription command) throws IOException,
            ClassNotFoundException, ClassCastException {
        client.start();
        writeValidationRequest(command);  //отправляем запрос на валидацию
        ValidationResponse validationResponse = (ValidationResponse) client.getObject();
        client.close();

        client.start();
        if (!validationResponse.getStatus()) { //если команда некорректная
            console.write(validationResponse.getErrorMessage());
        } else {
            try {
                if (command.isWithWorker()) {  //запрашиваем работника
                    Worker worker = inputManager.getWorker();
                    command.setWorker(worker);
                }
            } catch (EndInputWorkerException | EndInputException e) {
                client.close();
                return;
            }
            writeCommandRequest(command);  //отправляем запрос на выполнение команды
            CommandResponse commandResponse = (CommandResponse) client.getObject();

            user = commandResponse.getUser();

            if (!commandResponse.getStatus()) {
                console.write(commandResponse.getErrorMessage());
            } else {
                console.write(commandResponse.getResult());
            }
        }
        client.close();
    }
}
