package server.managers;

import common.commands.AbstractCommand;
import common.consoles.StringConsole;
import common.exceptions.NonExistentId;
import common.exceptions.WrongCommandArgsException;
import common.requests.CommandRequest;
import common.requests.GetAllCommandsRequest;
import common.requests.Request;
import common.requests.ValidationRequest;
import common.responses.CommandResponse;
import common.responses.GetAllCommandsResponse;
import common.responses.Response;
import common.responses.ValidationResponse;
import server.commands.Command;

/**
 * Обработчик запросов, которые приходят на сервер
 */
public class RequestHandler {
    private final CommandManager commandManager;

    public RequestHandler(CommandManager commandManager) {
        this.commandManager = commandManager;
    }
    public Response requestHandler(Request request) {
        if (request instanceof GetAllCommandsRequest) {
            return new GetAllCommandsResponse(CommandManager.getAllCommands());
        }
        else if (request instanceof ValidationRequest) {
            AbstractCommand command = ((ValidationRequest) request).getCommand();
            ValidationResponse response = new ValidationResponse();
            try {
                commandManager.serverValidateCommand((Command) command);
                response.setStatus(true);
            }
            catch (NonExistentId | WrongCommandArgsException e) {
                response.setStatus(false);
                response.setErrorMessage(e.toString());
            }
            return response;
        }
        else { // if (request instanceof CommandRequest)
            AbstractCommand command = ((CommandRequest) request).getCommand();
            CommandResponse response = new CommandResponse();
            try {
                commandManager.serverValidateCommand((Command) command);
                StringConsole strConsole = new StringConsole();

                commandManager.getCollectionManager().sortByName();  //сортируем коллекцию по имени
                commandManager.getCollectionManager().setConsole(strConsole);

                commandManager.executeCommand((Command) command, strConsole);
                response.setStatus(true);
                response.setResult(strConsole.getAllText());
            }
            catch (NonExistentId | WrongCommandArgsException e) {
                response.setStatus(false);
                response.setErrorMessage(e.toString());
            }
            return response;
        }
    }
}
