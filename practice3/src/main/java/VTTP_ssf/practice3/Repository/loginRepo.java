package VTTP_ssf.practice3.Repository;

import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import VTTP_ssf.practice3.Controller.loginController;
import VTTP_ssf.practice3.Model.Users;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@Repository
public class loginRepo {
    private final Logger logger = Logger.getLogger(loginRepo.class.getName());
    @Autowired
    @Qualifier("redis-object")
    private RedisTemplate template;

    public void newUser(String id, Users user) {
        // logger.info("Creating new user");
        JsonObject json = user.toJson();
        // logger.info("JSON VALUES: %s\n".formatted(json.toString()));
        ValueOperations<String, Object> valueOps = template.opsForValue();
        valueOps.set(id, json.toString());

    }

    public Users loginUser(Users user) {
        String id = getID(user);


        String value = (String) template.opsForValue().get(id);
        ValueOperations<String, Object> valueOps = template.opsForValue();
        if (value != null) {
            try (JsonReader reader = Json.createReader(new StringReader(value))) {
                JsonObject body = reader.readObject();
                logger.info("JSON VALUE: %s\n".formatted(body.toString()));

                // Get the current date and time
                Date date = new Date();

                // Format the date as a string in the desired format
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                String dateString = dateFormat.format(date);

                // Update the datelogin field in the JSON object (using JsonObjectBuilder)
                JsonObject updatedBody = Json.createObjectBuilder(body)
                        .add("dateLogin", dateString)
                        .build();

                // Log the updated JSON object
                logger.info("UPDATED JSON VALUE: %s\n".formatted(updatedBody.toString()));
                valueOps.set(id, updatedBody.toString());
                return Users.fromJson(updatedBody);


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;

    }

    public boolean checkUser(Users user) {
        Set<String> keys = template.keys("*");
        if (keys != null) {
            for (String key : keys) {
                // Retrieve the value associated with the key from the data store
                String value = (String) template.opsForValue().get(key);

                if (value != null) {
                    try (JsonReader reader = Json.createReader(new StringReader(value))) {
                        JsonObject body = reader.readObject();

                        // Check if the JSON object contains the "name" key and compare it
                        if (body.containsKey("name") && body.getString("name").equals(user.getName())) {
                            return true;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return false;
    }

    private String getID(Users user) {
        Set<String> keys = template.keys("*");
        if (keys != null) {
            for (String key : keys) {
                // Retrieve the value associated with the key from the data store
                String value = (String) template.opsForValue().get(key);

                if (value != null) {
                    try (JsonReader reader = Json.createReader(new StringReader(value))) {
                        JsonObject body = reader.readObject();

                        // Check if the JSON object contains the "name" key and compare it
                        if (body.containsKey("name") && body.getString("name").equals(user.getName())) {
                            return body.getString("id");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return null;
    }

}
