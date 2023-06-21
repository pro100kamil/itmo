package common.network.responses;

import common.models.Worker;

import java.util.Collection;

/**
 * Ответ на запрос о получении коллекции
 */
public class GetCollectionResponse extends Response {
    private final Collection<Worker> collection;

    public GetCollectionResponse(Collection<Worker> collection) {
        this.collection = collection;
    }

    public Collection<Worker> getCollection() {
        return collection;
    }
}
