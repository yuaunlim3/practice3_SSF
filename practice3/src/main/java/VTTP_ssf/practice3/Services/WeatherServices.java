package VTTP_ssf.practice3.Services;

import java.io.StringReader;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import VTTP_ssf.practice3.Model.Weather;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@Service
public class WeatherServices {
    private final Logger logger = Logger.getLogger(WeatherServices.class.getName());

    public static final String WEATHER_URL = "https://api.openweathermap.org/data/2.5/weather";

    public Weather getInfo(String countryName) {
        Weather data = new Weather();
        RestTemplate template = new RestTemplate();
        String url = UriComponentsBuilder
        .fromUriString(WEATHER_URL)
        .queryParam("q", countryName)
        .queryParam("appid", "5e78c9021adca53c10b1b1b8dd1887ae")
        .toUriString();

        //logger.info("URL: %s".formatted(url));;

        try {
            RequestEntity<Void> req = RequestEntity
                    .get(url)
                    .accept(MediaType.APPLICATION_JSON)
                    .build();

            ResponseEntity<String> resp = template.exchange(req, String.class);
            String payload = resp.getBody();

            JsonReader reader = Json.createReader(new StringReader(payload));
            JsonObject result = reader.readObject();

            // Get weather information
            JsonArray weatherArray = result.getJsonArray("weather");
            if (weatherArray != null && !weatherArray.isEmpty()) {
                JsonObject weatherData = weatherArray.getJsonObject(0);
                String weatherDescription = weatherData.getString("main");
                data.setWeatherInfo(weatherDescription);
                String icon = "https://openweathermap.org/img/wn/" + weatherData.getString("icon") + ".png";
                data.setImageUrl(icon);
            }

            // Get country name
            if (result.containsKey("name")) {
                String country = result.getString("name");
                data.setCountry(country);
            }

            // Get main data (temperature, humidity, pressure)
            JsonObject main = result.getJsonObject("main");
            if (main != null) {
                data.setTemp( main.getJsonNumber("temp").doubleValue());
                data.setHumidity(main.getJsonNumber("humidity").doubleValue());
                data.setPressure(main.getJsonNumber("pressure").doubleValue());
                
                
            }

            // Get sys data (sunrise and sunset)
            JsonObject sys = result.getJsonObject("sys");
            if (sys != null) {
                Long sunriseTime = sys.getJsonNumber("sunrise").longValue();
                Long sunsetTime = sys.getJsonNumber("sunset").longValue();

                // Convert Unix timestamps to LocalDateTime
                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a");
                data.setSunrise(formatUnixTimestamp(sunriseTime));
                data.setSunset(formatUnixTimestamp(sunsetTime));
            }

            //windspeed
            JsonObject wind = result.getJsonObject("wind");

            if (wind != null) {
                double speed = wind.getJsonNumber("speed").doubleValue();
                data.setWindSpeed(speed);
            }
            
            

            //logger.info("DATA %s\n".formatted(data.toString()));

            return data;

        } catch (Exception ex) {
            logger.severe("Error fetching or parsing weather data: " + ex.getMessage());
            ex.printStackTrace();
        }

        return null;
    }

    private String formatUnixTimestamp(Long unixTimestamp) {
        Instant instant = Instant.ofEpochSecond(unixTimestamp);
        LocalDateTime dateTime = instant.atZone(ZoneId.of("Asia/Singapore")).toLocalDateTime();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a");
        return dateTime.format(timeFormatter);
    }
}
