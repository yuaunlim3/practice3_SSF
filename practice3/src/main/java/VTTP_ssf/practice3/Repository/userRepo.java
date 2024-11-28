package VTTP_ssf.practice3.Repository;

import java.io.StringReader;
import java.text.ParseException;
import java.util.Set;
import java.util.logging.Logger;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import VTTP_ssf.practice3.Model.Users;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@Repository
public class userRepo {
    private final Logger logger = Logger.getLogger(userRepo.class.getName());
    @Autowired
    @Qualifier("redis-object")
    private RedisTemplate template;

    public Users getUser(String name){
        Set<String> keys = template.keys("*");
        if (keys != null) {
            for (String key : keys) {
                // Retrieve the value associated with the key from the data store
                String value = (String) template.opsForValue().get(key);

                if (value != null) {
                    try (JsonReader reader = Json.createReader(new StringReader(value))) {
                        JsonObject body = reader.readObject();

                        // Check if the JSON object contains the "name" key and compare it
                        if (body.containsKey("name") && body.getString("name").equals(name)) {
                            return Users.fromJson(body);
                        } 
                    } catch (Exception e) {
                        System.err.println("Error parsing JSON for key " + key + ": " + e.getMessage());
                    }
                }
            }
        }
        return null;
    }

}
