package graf.ethan.alzheimers_project.weather;

import org.json.JSONObject;

/**
 * Created by Ethan on 9/5/2015.
 */
public class Units implements JSONPopulator {
    private String temperature;

    @Override
    public void populate(JSONObject data) {
        temperature = data.optString("temperature");
    }

    public String getTemperature() {
        return temperature;
    }
}
