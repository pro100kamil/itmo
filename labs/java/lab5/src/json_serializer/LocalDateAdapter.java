package json_serializer;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateAdapter implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {
	@Override
    public JsonElement serialize(LocalDate date, Type typeOfSrc, JsonSerializationContext context) {
		return new JsonPrimitive(date.toString()); // "yyyy-mm-dd"
	}

	@Override
	public LocalDate deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
		return LocalDate.parse(json.getAsString());
		//return LocalDate.parse(json.getAsJsonPrimitive().getAsString());
	}
}