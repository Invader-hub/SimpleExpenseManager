package lk.ac.mrt.cse.dbs.simpleexpensemanager.control;

import android.content.Context;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.exception.ExpenseManagerException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.DataBaseHelper;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.InMemoryAccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.InMemoryTransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.PersistentAccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.PersistentTransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.ui.MainActivity;

public class PersistentExpenseManager extends ExpenseManager{
        private DataBaseHelper dbh;
    public  PersistentExpenseManager(Context context) throws ExpenseManagerException {
        this.dbh =  new DataBaseHelper(context);
        setup();
    }

    @Override
    public void setup() throws ExpenseManagerException {
        /*** Setup SQLite database for persistent storage Implementation ***/

        TransactionDAO persistentMemoryTransactionDAO = new PersistentTransactionDAO(this.dbh);
        setTransactionsDAO(persistentMemoryTransactionDAO);

        AccountDAO persistentMemoryAccountDAO = new PersistentAccountDAO(this.dbh);
        setAccountsDAO(persistentMemoryAccountDAO);

        /*** End ***/
    }
}
