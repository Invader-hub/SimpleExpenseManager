package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import java.util.Date;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Transaction;

// Persistent DAO for Transaction Class
public class PersistentTransactionDAO implements TransactionDAO {
    private DataBaseHelper dbh;

    public PersistentTransactionDAO(DataBaseHelper dbh) {
        this.dbh = dbh;
    }

    @Override
    public void logTransaction(Date date, String accountNo, ExpenseType expenseType, double amount) {
        Transaction curTransaction = new Transaction(date, accountNo, expenseType, amount);
        boolean b = dbh.addOneTransactionLog(curTransaction);
    }

    @Override
    public List<Transaction> getAllTransactionLogs() {
        return dbh.getAllLog();
    }

    @Override
    public List<Transaction> getPaginatedTransactionLogs(int limit) {
        List<Transaction> transactions = this.getAllTransactionLogs();
        int size = transactions.size();
        if (size <= limit) {
            return transactions;
        }
        // return the last <code>limit</code> number of transaction logs
        return transactions.subList(size - limit, size);
    }
}
