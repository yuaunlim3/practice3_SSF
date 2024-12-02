package VTTP_ssf.practice3.Model;


import jakarta.json.Json;
import jakarta.json.JsonObject;

public class Weather {
    private String imageUrl;
    private String weatherInfo;
    private double temp;
    private double pressure;
    private double humidity;
    private String sunrise;
    private String sunset;
    private String country;
    private double windSpeed; 

    @Override
    public String toString() {
        return "Weather [imageUrl=" + imageUrl + ", weatherInfo=" + weatherInfo + ", temp=" + temp + ", pressure="
                + pressure + ", humidity=" + humidity + ", sunrise=" + sunrise + ", sunset=" + sunset + ", country="
                + country + ", windSpeed=" + windSpeed + "]";
    }
    public JsonObject toJson() {
        return Json.createObjectBuilder()
            .add("imageUrl", this.imageUrl)
            .add("weatherInfo", this.weatherInfo)
            .add("temp", this.temp)
            .add("pressure", this.pressure)
            .add("humidity", this.humidity)
            .add("sunrise", this.sunrise)
            .add("sunset", this.sunset)
            .add("country", this.country)
            .add("windSpeed",windSpeed)
            .build();
    }
    public void ImperialUnits(){
        this.temp = Double.parseDouble(String.format("%.2f", ((this.temp- 273.15) * 9 / 5 + 32)));
        this.pressure = Double.parseDouble(String.format("%.2f", (this.pressure / 33.8638)));
        this.windSpeed = Double.parseDouble(String.format("%.2f", (this.windSpeed * 2.23694)));     
        
    }

    public void MetricUnits(){
        this.temp = Double.parseDouble(String.format("%.2f",(this.temp - 273.15)));
        
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public String getWeatherInfo() {
        return weatherInfo;
    }
    public void setWeatherInfo(String weatherInfo) {
        this.weatherInfo = weatherInfo;
    }
    public double getTemp() {
        return temp;
    }
    public void setTemp(double temp) {
        this.temp = temp;
    }
    public double getPressure() {
        return pressure;
    }
    public void setPressure(double pressure) {
        this.pressure = pressure;
    }
    public double getHumidity() {
        return humidity;
    }
    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }
    public String getSunrise() {
        return sunrise;
    }
    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }
    public String getSunset() {
        return sunset;
    }
    public void setSunset(String sunset) {
        this.sunset = sunset;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public double getWindSpeed() {
        return windSpeed;
    }
    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }


}
