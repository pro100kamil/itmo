package server.managers;

import common.consoles.StringConsole;
import common.exceptions.NonExistentId;
import common.exceptions.UnavailableCommandException;
import common.exceptions.WrongCommandArgsException;
import common.network.requests.*;
import common.network.responses.*;
import server.commands.ServerCommand;

/**
 * Обработчик запросов, которые приходят на сервер.
 */
public class RequestHandler {
    private final CommandManager commandManager;

    public RequestHandler(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    public Response requestHandler(Request request) {
        if (request instanceof GetAllCommandsRequest) {
            return new GetAllCommandsResponse(CommandManager.getAbstractCommands());
        }
        else if (request instanceof UpdateCollectionHistoryRequest) {
            UpdateCollectionHistoryResponse response = new UpdateCollectionHistoryResponse();
            commandManager.addStateCollection();
            return response;
        }

        else if (request instanceof ValidationRequest) {
            ServerCommand command = CommandManager.getServerCommandFromAbstractCommand(
                    ((ValidationRequest) request).getCommand());
            ValidationResponse response = new ValidationResponse();
            try {
                commandManager.serverValidateCommand(command);
                response.setStatus(true);
            }
            catch (NonExistentId | WrongCommandArgsException | UnavailableCommandException e) {
                response.setStatus(false);
                response.setErrorMessage(e.toString());
            }
            return response;
        }
        else { // if (request instanceof CommandRequest)
            ServerCommand command = CommandManager.getServerCommandFromAbstractCommand(
                    ((CommandRequest) request).getCommand());
            CommandResponse response = new CommandResponse();
            try {
                commandManager.serverValidateCommand(command);
                StringConsole strConsole = new StringConsole();

                commandManager.getCollectionManager().sortByName();  //сортируем коллекцию по имени
                commandManager.getCollectionManager().setConsole(strConsole);

                commandManager.executeCommand(command, strConsole);
                response.setStatus(true);
                response.setResult(strConsole.getAllText());
            }
            catch (NonExistentId | WrongCommandArgsException | UnavailableCommandException e) {
                response.setStatus(false);
                response.setErrorMessage(e.toString());
            }
            return response;
        }
    }
}
