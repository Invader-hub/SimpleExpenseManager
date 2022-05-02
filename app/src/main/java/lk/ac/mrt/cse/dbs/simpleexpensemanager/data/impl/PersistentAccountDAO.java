package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import java.util.ArrayList;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.exception.InvalidAccountException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.ui.MainActivity;

public class PersistentAccountDAO implements AccountDAO {
    private DataBaseHelper dbh;

    public PersistentAccountDAO(DataBaseHelper dbh) {
        this.dbh = dbh;
    }

    @Override
    public List<String> getAccountNumbersList() {
        List<String> resultAccounts = new ArrayList<>();
        List<Account> allAccount = dbh.getAllAccount();

        for (Account var : allAccount)
        {
            resultAccounts.add(var.getAccountNo());
        }
        return resultAccounts;
    }

    @Override
    public List<Account> getAccountsList() {
        return dbh.getAllAccount();
    }

    @Override
    public Account getAccount(String accountNo) throws InvalidAccountException {
        return dbh.getAccountFromNo(accountNo);
    }

    @Override
    public void addAccount(Account account) {
        dbh.addOneAccount(account);
    }

    @Override
    public void removeAccount(String accountNo) throws InvalidAccountException {
        dbh.deleteOneAccount(accountNo);
    }

    @Override
    public void updateBalance(String accountNo, ExpenseType expenseType, double amount) throws InvalidAccountException {
        float value = 0;
        if (expenseType == ExpenseType.EXPENSE){
            value -= amount;
        }
        else if (expenseType == ExpenseType.INCOME){
            value += amount;
        }
        dbh.updateAccountBalance(accountNo, value);
    }
}
