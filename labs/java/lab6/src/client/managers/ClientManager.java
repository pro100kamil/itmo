package client.managers;

import client.Configuration;
import common.commands.AbstractCommand;
import common.consoles.Console;
import common.consoles.StandardConsole;
import common.requests.CommandRequest;
import common.requests.GetAllCommandsRequest;
import common.requests.ValidationRequest;
import common.responses.CommandResponse;
import common.responses.GetAllCommandsResponse;
import common.responses.ValidationResponse;

import java.io.IOException;

public class ClientManager {
    private static final Console console = new StandardConsole();
    Client client;

    public ClientManager() {
        client = new Client(Configuration.getHost(), Configuration.getPort());
    }

    public void start() throws IOException {
        client.start();
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

    public void commandHandler(AbstractCommand command) throws IOException,
            ClassNotFoundException, ClassCastException {
        client.start();
        System.out.println(command);
        writeValidationRequest(command);
        ValidationResponse validationResponse = (ValidationResponse) client.getObject();
        if (!validationResponse.getStatus()) { //если команда некорректная
            console.write(validationResponse.getErrorMessage());
            return;
        }

        writeCommandRequest(command);
        CommandResponse commandResponse = (CommandResponse) client.getObject();
        if (!commandResponse.getStatus()) {
            console.write(commandResponse.getErrorMessage());
            return;
        }
        console.write(commandResponse.getResult());
        client.close();
    }

//    public void run(Command command) {
//        try {
////            start();
//            try {
//                writeObject(command);  //отправляем команду
//                try {
//                    String strRes = (String) getObject();  //получаем результат в виде строки
//
//                    console.write(strRes);
//
//                } catch (IOException | ClassNotFoundException e) {
//                    console.write(e.toString());
//                    console.write("Принять данные не получилось");
//                } catch (ClassCastException e) {
//                    console.write(e.toString());
//                    console.write("Передан неправильный тип данных");
//                }
//            } catch (IOException e) {
//                console.write("Не получилось передать данные на сервер");
//            }
//            finally {
//                close();
//            }
//        } catch (IOException e) {
//            console.write(e.toString());
//            console.write("Не получилось подключиться к серверу");
//        }
//    }
}
