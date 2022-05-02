package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Transaction;

public class DataBaseHelper extends SQLiteOpenHelper {


    private static final String ACCOUNT = "account";
    private static final String ACCOUNT_NO = "account_no";
    private static final String BANK_NAME = "bank_name";
    private static final String ACCOUNT_HOLDER = "account_holder";
    private static final String INITIAL_BALANCE = "initial_balance";
    private static final String TRANSACTION_LOG = "transaction_log";
    private static final String ID = "id";
    private static final String DATE = "date";
    private static final String TRANSACTION_TYPE = "transaction_type";
    private static final String AMOUNT = "amount";

    public DataBaseHelper(@Nullable Context context) {
        super(context, "expenseManager.db", null, 1);
    }

    // Used in creating table for first time
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement1 = "CREATE TABLE " + ACCOUNT + " (" + ACCOUNT_NO + " VARCHAR(50) PRIMARY KEY, " + BANK_NAME + " VARCHAR(100), " + ACCOUNT_HOLDER + " VARCHAR(100), " + INITIAL_BALANCE + " FLOAT);";
        String createTableStatement2 = "CREATE TABLE " + TRANSACTION_LOG + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + DATE + " DATE, " + ACCOUNT_NO+ " VARCHAR(50)," + TRANSACTION_TYPE + " INT, " + AMOUNT + " FLOAT);";

        db.execSQL(createTableStatement1);
        db.execSQL(createTableStatement2);

    }

    // Used when application is upgraded
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean addOneAccount(Account account){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(ACCOUNT_NO, account.getAccountNo());
        cv.put(BANK_NAME, account.getBankName());
        cv.put(ACCOUNT_HOLDER, account.getAccountHolderName());
        cv.put(INITIAL_BALANCE, account.getBalance());

        long insert = db.insert(ACCOUNT, null, cv);

        if (insert == -1){
            return false;
        }
        else {
        return true;
        }
    }

    public boolean addOneTransactionLog(Transaction transaction){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        // Formatting date for db insert
        Date date = transaction.getDate();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String datestr = formatter.format(date);

        // Formatting transaction type for db insert
        Integer transaction_type_value = 0;
        ExpenseType transaction_type = transaction.getExpenseType();
        if (transaction_type == ExpenseType.EXPENSE){
            transaction_type_value = 1;
        }
        else if (transaction_type == ExpenseType.INCOME){
            transaction_type_value = 2;
        }

        cv.put(DATE, datestr);
        cv.put(ACCOUNT_NO, transaction.getAccountNo());
        cv.put(TRANSACTION_TYPE, transaction_type_value);
        cv.put(AMOUNT, transaction.getAmount());

        long insert = db.insert(TRANSACTION_LOG, null, cv);

        if (insert == -1){
            return false;
        }
        else {
            return true;
        }
    }
    
    public List<Transaction> getAllLog(){
        List<Transaction> returnList = new ArrayList<>();

        String sql_query = "SELECT * FROM "+ TRANSACTION_LOG;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql_query, null);

        if(cursor.moveToFirst()){
            do {
                int transactionID = cursor.getInt(0);
                String datestr = cursor.getString(1);
                String accountNo = cursor.getString(2);
                int transactionType = cursor.getInt(3);
                float amount = cursor.getFloat(4);

                Date date = null;
                try {
                    date = new SimpleDateFormat("yyyy-MM-dd").parse(datestr);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                ExpenseType ex = null;
                if(transactionType == 1){
                    ex = ExpenseType.EXPENSE;
                }
                else if (transactionType == 2){
                    ex = ExpenseType.INCOME;
                }

                Transaction curTransaction = new Transaction(date, accountNo, ex, amount);
                returnList.add(curTransaction);

            }while (cursor.moveToNext());

        }else {
            // Do nothing;
        }

        return returnList;
    }

    public List<Account> getAllAccount(){
        List<Account> returnList = new ArrayList<>();

        String sql_query = "SELECT * FROM "+ ACCOUNT;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql_query, null);

        if(cursor.moveToFirst()){
            do {
                String accountNo = cursor.getString(0);
                String bankName = cursor.getString(1);
                String accountHolder = cursor.getString(2);
                float initialBalance = cursor.getFloat(3);

                Account curAccount = new Account(accountNo, bankName, accountHolder, initialBalance);

                returnList.add(curAccount);

            }while (cursor.moveToNext());

        }else {
            // Do nothing;
        }

        return returnList;
    }

}
