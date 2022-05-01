package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;

public class DataBaseHelper extends SQLiteOpenHelper {


    private static final String ACCOUNT = "account";
    private static final String ACCOUNT_NO = "account_no";
    private static final String BANK_NAME = "bank_name";
    private static final String ACCOUNT_HOLDER = "account_holder";
    private static final String INITIAL_BALANCE = "initial_balance";
    private static final String LOG = "log";
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
        String createTableStatement2 = "CREATE TABLE " + LOG + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + DATE + " DATE, " + ACCOUNT_NO+ " VARCHAR(50)," + TRANSACTION_TYPE + " INT, " + AMOUNT + " FLOAT);";

        db.execSQL(createTableStatement1);
        db.execSQL(createTableStatement2);

    }

    // Used when application is upgraded
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean addOne(Account account){
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
        4}
    }
}
