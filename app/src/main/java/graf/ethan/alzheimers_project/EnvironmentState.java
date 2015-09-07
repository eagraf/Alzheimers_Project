package graf.ethan.alzheimers_project;

import android.app.Activity;

import graf.ethan.alzheimers_project.weather.Channel;
import graf.ethan.alzheimers_project.weather.Item;
import graf.ethan.alzheimers_project.weather.WeatherServiceCallback;
import graf.ethan.alzheimers_project.weather.YahooWeatherService;

/**
 * Created by Ethan on 7/22/2015.
 */
public class EnvironmentState implements WeatherServiceCallback {

    private int temperature;
    private String units;
    private String location;

    private MainActivity mainActivity;

    public EnvironmentState(MainActivity activity, String location) {
        this.mainActivity = activity;
        getWeather(location);
    }

    public int getTemperature() {
        return temperature;
    }

    public String getUnits() {
        return units;
    }

    public String getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }

    private String description;

    private YahooWeatherService weatherService;

    public void getWeather(String location) {
        this.location = location;
        weatherService = new YahooWeatherService(this);

        weatherService.refreshWeather(location);
    }

    @Override
    public void serviceSuccess(Channel channel) {
        Item item = channel.getItem();

        temperature = item.getCondition().getTemperature();
        units = channel.getUnits().getTemperature();
        location = weatherService.getLocation();
        description = item.getCondition().getDescription();

        mainActivity.showWeather();
    }

    @Override
    public void serviceFailure(Exception e) {
        System.out.println(e.getMessage());

    }
}

