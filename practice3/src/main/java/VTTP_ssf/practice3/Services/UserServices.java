package VTTP_ssf.practice3.Services;



import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import VTTP_ssf.practice3.Model.Users;
import VTTP_ssf.practice3.Repository.userRepo;


@Service
public class UserServices {
    private final Logger logger = Logger.getLogger(UserServices.class.getName());
    @Autowired
    private userRepo userRepo;

    public Users getUser(String name){
        return userRepo.getUser(name);
    }

}
