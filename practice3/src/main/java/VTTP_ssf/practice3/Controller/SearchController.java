package VTTP_ssf.practice3.Controller;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import VTTP_ssf.practice3.Model.Weather;
import VTTP_ssf.practice3.Services.UserServices;
import VTTP_ssf.practice3.Services.WeatherServices;

@Controller
@RequestMapping
public class SearchController {
    private final Logger logger = Logger.getLogger(SearchController.class.getName());

    @Autowired
    private WeatherServices weatherSrv;
    @PostMapping("/weatherData")
    public String weatherData(@RequestBody MultiValueMap<String,String> form, 
                              Model model) {
        String city= form.getFirst("country");
        String[] infos= city.split(" ");
        String cityName = "";
        for(String info:infos){
            cityName = String.join("+", infos);

        }
        String unit = form.getFirst("unit");
        String name = form.getFirst("name");
        logger.info("Name: %s\n".formatted(name));
        //logger.info("Received country: %s/n".formatted(country));
        //logger.info("Received unit: %s/n".formatted(unit));

        //logger.info(cityName);
        
        Weather weatherInfo = weatherSrv.getInfo(cityName);
        int value = -1;
        if(unit.equalsIgnoreCase("metric")){
            weatherInfo.MetricUnits();
            value = 1;
        }
        if(unit.equalsIgnoreCase("imperial")){
            weatherInfo.ImperialUnits();
            value = 0;
        }
        model.addAttribute("weatherInfo", weatherInfo);
        model.addAttribute("unit",value);
        model.addAttribute("name", form.getFirst("name"));
        String gifUrl = "/gifs/" + weatherInfo.getWeatherInfo()+".gif"; 
        model.addAttribute("gifUrl", gifUrl);
        return "weather";
    }

}
