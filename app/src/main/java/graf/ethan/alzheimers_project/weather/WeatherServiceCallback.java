package graf.ethan.alzheimers_project.weather;

/**
 * Created by Ethan on 9/5/2015.
 */
public interface WeatherServiceCallback {
    void serviceSuccess(Channel channel);
    void serviceFailure(Exception e);
}
