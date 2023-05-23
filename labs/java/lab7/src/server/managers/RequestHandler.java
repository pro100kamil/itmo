package server.managers;

import common.consoles.StringConsole;
import common.exceptions.NonExistentId;
import common.exceptions.UnavailableCommandException;
import common.exceptions.UnavailableModelException;
import common.exceptions.WrongCommandArgsException;
import common.models.User;
import common.network.requests.*;
import common.network.responses.*;
import server.Configuration;
import server.commands.ServerCommand;
import server.managers.databaseManagers.UserDatabaseManager;
import server.models.ServerUser;

import java.sql.SQLException;

/**
 * Обработчик запросов, которые приходят на сервер.
 */
public class RequestHandler {
    private final CommandManager commandManager;
    public UserDatabaseManager databaseManager = new UserDatabaseManager(Configuration.getDbUrl(),
            Configuration.getDbLogin(), Configuration.getDbPass());

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

    public User handleUser(User user) {
        try {
            ServerUser serverUser = databaseManager.getUser(user.getName(), user.getPassword());
            if (serverUser == null) return null;
            user.setRole(serverUser.getRole());
            return user;
        } catch (SQLException e) {
            return null;
        }
    }

    public ValidationResponse handleValidationRequest(ValidationRequest request) {
        ServerCommand command = commandManager.getServerCommandFromCommandDescription(
                request.getCommand());
        command.setUser(handleUser(request.getUser()));
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
        command.setUser(handleUser(request.getUser()));
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
