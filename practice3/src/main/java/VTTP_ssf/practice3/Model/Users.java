package VTTP_ssf.practice3.Model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class Users {

    @NotEmpty(message = "Name cannot be empty")
    private String name;

    private String Id;
    private Date dateCreated;
    private Date dateLogin;

    @NotEmpty(message = "Password cannot be empty")
    private String password;

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateLogin() {
        return dateLogin;
    }

    public void setDateLogin(Date dateLogin) {
        this.dateLogin = dateLogin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public JsonObject toJson() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        return Json.createObjectBuilder()
                .add("name", this.name)
                .add("password", this.password)
                .add("id", this.Id)
                .add("dateCreated", dateFormat.format(this.dateCreated))
                .add("datelogin", dateFormat.format(this.dateLogin))
                .build();
    }

    public static Users fromJson(JsonObject json) throws ParseException {
        Users user = new Users();
        String name = json.getString("name");
        String password = json.getString("password");
        String id = json.getString("id");
        String dateCreated = json.getString("dateCreated");
        String datelogin = json.getString("dateLogin");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        Date parsedDateCreated = dateFormat.parse(dateCreated);
        Date parsedDateLogin = dateFormat.parse(datelogin);

        user.setDateCreated(parsedDateCreated);
        user.setDateLogin(parsedDateLogin);
        user.setId(id);
        user.setName(name);
        user.setPassword(password);
        return user;
    }

    @Override
    public String toString() {
        return "[name=" + name + ", Id=" + Id + ", dateCreated=" + dateCreated + ", dateLogin=" + dateLogin
                + ", password=" + password + "]";
    }



}
