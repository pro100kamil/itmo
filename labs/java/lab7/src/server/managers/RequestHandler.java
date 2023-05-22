package server.managers;

import common.consoles.StringConsole;
import common.exceptions.NonExistentId;
import common.exceptions.UnavailableCommandException;
import common.exceptions.UnavailableModelException;
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

    public GetAllCommandsResponse handleGetAllCommandsRequest(GetAllCommandsRequest request) {
        if (request.getUser() == null)
            return new GetAllCommandsResponse(commandManager.getCommandDescriptionsForUnauthorizedUser());
        else
            return new GetAllCommandsResponse(commandManager.getCommandDescriptions());
    }

    public UpdateCollectionHistoryResponse handleUpdateCollectionHistoryRequest(UpdateCollectionHistoryRequest request) {
        UpdateCollectionHistoryResponse response = new UpdateCollectionHistoryResponse();
        commandManager.addStateCollection();
        return response;
    }

    public ValidationResponse handleValidationRequest(ValidationRequest request) {
        ServerCommand command = commandManager.getServerCommandFromCommandDescription(
                request.getCommand());
        command.setUser(request.getUser());
        ValidationResponse response = new ValidationResponse();
        try {
            commandManager.serverValidateCommand(command);
            response.setStatus(true);
        }
        catch (NonExistentId | WrongCommandArgsException |
               UnavailableCommandException | UnavailableModelException e) {
            response.setStatus(false);
            response.setErrorMessage(e.toString());
        }
        response.setUser(command.getUser());
        return response;
    }

    public CommandResponse handleCommandRequest(CommandRequest request) {
        ServerCommand command = commandManager.getServerCommandFromCommandDescription(
                request.getCommand());
        command.setUser(request.getUser());
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
        catch (NonExistentId | WrongCommandArgsException |
               UnavailableCommandException | UnavailableModelException e) {
            response.setStatus(false);
            response.setErrorMessage(e.toString());
        }
        response.setUser(command.getUser());
        return response;
    }

    public Response handleRequest(Request request) {
        if (request instanceof GetAllCommandsRequest) {
            return handleGetAllCommandsRequest((GetAllCommandsRequest) request);
        }
        else if (request instanceof UpdateCollectionHistoryRequest) {
            return handleUpdateCollectionHistoryRequest((UpdateCollectionHistoryRequest) request);
        }

        else if (request instanceof ValidationRequest) {
            return handleValidationRequest((ValidationRequest) request);
        }
        else { // if (request instanceof CommandRequest)
            return handleCommandRequest((CommandRequest) request);
        }
    }
}
