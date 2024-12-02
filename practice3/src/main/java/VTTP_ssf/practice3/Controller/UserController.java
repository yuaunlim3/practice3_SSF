package VTTP_ssf.practice3.Controller;


import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import VTTP_ssf.practice3.Model.Users;

import VTTP_ssf.practice3.Services.UserServices;


@Controller
@RequestMapping
public class UserController {
    private final Logger logger = Logger.getLogger(UserController.class.getName());
    @Autowired
    private UserServices userSrv;

    @PostMapping("/homepage/{name}")
    public String backHomepage(@PathVariable String name, Model model) {

        Users loginUser = userSrv.getUser(name);
        model.addAttribute("loginUser", loginUser);
        System.out.println("loginUser: " + loginUser);


        return "homepage";
    }

    @GetMapping("/homepage/{name}")
    public String Homepage(@PathVariable String name, Model model) {
        Users loginUser = userSrv.getUser(name);
        if (loginUser == null) {
            System.out.println("User not found: " + name);
        } else {
            System.out.println("User found: " + loginUser);
        }
        model.addAttribute("loginUser", loginUser);
    
        return "homepage";
    }


}
