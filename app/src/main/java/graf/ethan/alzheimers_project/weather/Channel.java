package graf.ethan.alzheimers_project.weather;

import org.json.JSONObject;

/**
 * Created by Ethan on 9/5/2015.
 */
public class Channel implements JSONPopulator {

    private Item item;
    private Units units;

    @Override
    public void populate(JSONObject data) {
        units = new Units();
        units.populate(data.optJSONObject("units"));
        System.out.println(data.optJSONObject("units").toString());

        item = new Item();
        item.populate((data.optJSONObject("item")));
        System.out.println(data.optJSONObject("item").toString());
    }

    public Item getItem() {
        return item;
    }

    public Units getUnits() {
        return units;
    }
}
