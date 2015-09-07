package graf.ethan.alzheimers_project.weather;

import org.json.JSONObject;

/**
 * Created by Ethan on 9/5/2015.
 */
public class Item implements JSONPopulator {
    private Condition condition;

    @Override
    public void populate(JSONObject data) {
        condition = new Condition();
        condition.populate(data.optJSONObject("condition"));
    }

    public Condition getCondition() {
        return condition;
    }
}
