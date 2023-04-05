package sg.edu.nus.iss.demoTransaction.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sg.edu.nus.iss.demoTransaction.payload.TransferRequest;
import sg.edu.nus.iss.demoTransaction.service.BankAccountService;

@RestController
@RequestMapping("/accounts")
public class BankAccountController {
    @Autowired
    BankAccountService bankAcctSvc;

    @PostMapping
    public ResponseEntity<Boolean> transferMoney(@RequestBody TransferRequest transferRequest) {
        Boolean bTransferred = false;

        bTransferred = bankAcctSvc.transferMoney(transferRequest.getAccountFrom(), transferRequest.getAccountTo(), transferRequest.getAmount());

        if (bTransferred) {
            return new ResponseEntity<Boolean>(bTransferred, HttpStatus.OK);
        } else {
            return new ResponseEntity<Boolean>(bTransferred, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
