package VTTP_ssf.practice3.Services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.UUID;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import VTTP_ssf.practice3.Model.Users;
import VTTP_ssf.practice3.Repository.loginRepo;

@Service
public class loginServices {
    private final Logger logger = Logger.getLogger(loginServices.class.getName());
    @Autowired
    private loginRepo loginRepo;

    public void newUser(Users user) {
        String id = UUID.randomUUID().toString().substring(0, 8);
        user.setId(id);
        user.setDateCreated(new Date());
        user.setDateLogin(new Date());

        //logger.info(user.toString());

        loginRepo.newUser(id, user);
    }

    public boolean checkUser(Users user){
        return loginRepo.checkUser(user);
    }

    public Users login(Users user){
        return loginRepo.loginUser(user);
    }

}
