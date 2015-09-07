package graf.ethan.alzheimers_project.weather;

import org.json.JSONObject;

/**
 * Created by Ethan on 9/5/2015.
 */
public class Condition implements JSONPopulator {
    private int code;
    private int temperature;
    private String description;

    @Override
    public void populate(JSONObject data) {
        code = data.optInt("code");
        temperature = data.optInt("temp");
        description = data.optString("text");
    }

    public int getCode() {
        return code;
    }

    public int getTemperature() {
        return temperature;
    }

    public String getDescription() {
        return description;
    }
}
