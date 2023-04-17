package paf.rev.pokemart.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import paf.rev.pokemart.repository.CustomerRepo;

@Service
public class AccountService {
    @Autowired
    CustomerRepo customerRepo;

    public int authenticateAccount(String email, String password){
        Optional<String> checkPassword = customerRepo.getPassword(email);
        if(checkPassword.isEmpty())  return -1;
        if(checkPassword.get().equals(password)) return 1; //VALID
        if(checkPassword.get().equals(password)) return 0;
        return 0;
    }
    
}
