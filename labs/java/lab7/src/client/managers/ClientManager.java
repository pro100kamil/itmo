package client.managers;

import client.Configuration;
import common.commands.AbstractCommand;
import common.consoles.Console;
import common.consoles.StandardConsole;
import common.exceptions.EndInputException;
import common.exceptions.EndInputWorkerException;
import common.models.Worker;
import common.network.requests.CommandRequest;
import common.network.requests.GetAllCommandsRequest;
import common.network.requests.UpdateCollectionHistoryRequest;
import common.network.requests.ValidationRequest;
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
    Client client;

    public ClientManager() {
        client = new Client(Configuration.getHost(), Configuration.getPort());
    }

    public AbstractCommand[] getAllCommands() throws IOException, ClassNotFoundException {
        client.start();
        writeGetAllCommandsRequest();
        GetAllCommandsResponse response = (GetAllCommandsResponse) client.getObject();
        client.close();
        return response.getCommands();
    }

    public void writeGetAllCommandsRequest() throws IOException {
        client.writeObject(new GetAllCommandsRequest());
    }

    public void writeValidationRequest(AbstractCommand command) throws IOException {
        client.writeObject(new ValidationRequest(command));
    }

    public void writeCommandRequest(AbstractCommand command) throws IOException {
        client.writeObject(new CommandRequest(command));
    }

    public void writeUpdateCollectionRequest() throws IOException {
        client.start();
        client.writeObject(new UpdateCollectionHistoryRequest());
        client.close();
    }

    public void commandHandler(InputManager inputManager, AbstractCommand command) throws IOException,
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
            if (!commandResponse.getStatus()) {
                console.write(commandResponse.getErrorMessage());
            } else {
                console.write(commandResponse.getResult());
            }
        }
        client.close();
    }
}
