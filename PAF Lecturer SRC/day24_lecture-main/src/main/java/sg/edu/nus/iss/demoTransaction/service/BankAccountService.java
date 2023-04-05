package sg.edu.nus.iss.demoTransaction.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import sg.edu.nus.iss.demoTransaction.model.BankAccount;
import sg.edu.nus.iss.demoTransaction.repository.BankAccountRepo;

@Service
public class BankAccountService {
    @Autowired
    BankAccountRepo bankAcctRepo;

    @Transactional //(isolation = Isolation.READ_UNCOMMITTED)
    public Boolean transferMoney(Integer accountFrom, Integer accountTo, Float amount) {
        Boolean bTransferred = false;
        Boolean bWithdrawn = false;
        Boolean bDeposited = false;

        BankAccount depositAccount = null;
        BankAccount withdrawalAccount = null;
        Boolean bDepositAccountValid = false;
        Boolean bWithdrawalAccountValid = false;
        Boolean bProceed = false;

        // check if accounts (withdrawer and depositor) are valid (active)
        withdrawalAccount = bankAcctRepo.retrieveAccountDetails(accountFrom);
        depositAccount = bankAcctRepo.retrieveAccountDetails(accountTo);
        bWithdrawalAccountValid = withdrawalAccount.getIsActive();
        bDepositAccountValid = depositAccount.getIsActive();
        if (bWithdrawalAccountValid && bDepositAccountValid)
            bProceed = true;

        // check withdrawn account has more money than withdrawal amount
        if (bProceed) {
            if (withdrawalAccount.getBalance() < amount)
                bProceed = false;
        }

        if (bProceed) {
            // perform the withdrawal (requires transaction)
            bWithdrawn = bankAcctRepo.withdrawAmount(accountFrom, amount);

            bWithdrawn = false;
            if (!bWithdrawn) {
                throw new IllegalArgumentException("Simulate error before Withdrawal");
            }

            // perform the deposit (requires transaction)
            bDeposited = bankAcctRepo.depositAmount(accountTo, amount);
        }

        // return transactions successful
        if (bWithdrawn && bDeposited)
            bTransferred = true;

        return bTransferred;
    }
}
