package VTTP_ssf.practice3.Controller;

import java.text.ParseException;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import VTTP_ssf.practice3.Model.Users;
import VTTP_ssf.practice3.Services.loginServices;
import jakarta.validation.Valid;

@Controller
@RequestMapping({ "/", "/index.html" })
public class loginController {
    private final Logger logger = Logger.getLogger(loginController.class.getName());
    @Autowired
    private loginServices loginSrv;

    @GetMapping({ "/index", "/"})
    public String start(Model model) {
        model.addAttribute("user", new Users());
        return "login";
    }

    @PostMapping({ "/index", "/"})
    public String logout(Model model) {
        model.addAttribute("user", new Users());
        return "login";
    }
    @GetMapping("/createaccount")
    public String create(Model model) {
        model.addAttribute("newUser", new Users());
        return "createAccount";
    }

    @PostMapping("/loginpage")
    public String loginHtml(@Valid @ModelAttribute("user") Users currUser, BindingResult bindings, Model model)
            throws ParseException {
        if (bindings.hasErrors()) {
            logger.info("ERROR");
            return "login";
        }
        if (!loginSrv.checkUser(currUser)) {
            logger.info("Does not exist");
            FieldError err1 = new FieldError("user", "name", "User does not exist");
            bindings.addError(err1);
            FieldError err2 = new FieldError("user", "password", "");
            bindings.addError(err2);
            return "login";
        }
        Users user = loginSrv.login(currUser);
        logger.info(user.toString());
        model.addAttribute("loginUser", user);
        return "redirect:/homepage/" + currUser.getName();
    }

    @PostMapping("/createaccount")
    public String create(@Valid @ModelAttribute("newUser") Users newUser, BindingResult bindings, Model model) {
        if (bindings.hasErrors()) {
            logger.warning("ERROR");
            // Add the newUser attribute to the model to be available in the view
            model.addAttribute("newUser", new Users());
            return "createAccount";
        }

        if (loginSrv.checkUser(newUser)) {
            FieldError err = new FieldError("newUser", "name", "User is already created");
            bindings.addError(err);
            return "createAccount";
        }

        loginSrv.newUser(newUser);
        model.addAttribute("user", new Users());
        return "login";
    }

}
