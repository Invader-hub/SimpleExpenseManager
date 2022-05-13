/*
 * Copyright 2015 Department of Computer Science and Engineering, University of Moratuwa.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *                  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package lk.ac.mrt.cse.dbs.simpleexpensemanager;

import static org.junit.Assert.assertTrue;

import android.content.Context;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;

import androidx.test.core.app.ApplicationProvider;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.ExpenseManager;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.PersistentExpenseManager;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.exception.ExpenseManagerException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.exception.InvalidAccountException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Transaction;

public class ApplicationTest {
    private ExpenseManager expenseManager;

    @Before
    public void setUp() throws ExpenseManagerException {
        Context context = ApplicationProvider.getApplicationContext();
        expenseManager = new PersistentExpenseManager(context);
    }

    @Test
    public void testAddAccount() {
        expenseManager.addAccount("1234", "AAA", "BBB", 200.0);
        expenseManager.addAccount("190137J", "BOC", "Name", 20000.0);
        List<String> accountNumbers = expenseManager.getAccountNumbersList();
        assertTrue(accountNumbers.contains("1234"));
        assertTrue(accountNumbers.contains("190137J"));
    }

    @Test
    public void testAddTransaction() {
        try {
            expenseManager.updateAccountBalance("1234", 11, 04, 2022, ExpenseType.EXPENSE, "500.0");
            expenseManager.updateAccountBalance("190137J", 10, 02, 1999, ExpenseType.INCOME, "9999.57");
        } catch (InvalidAccountException e) {
            assertTrue(false);
        }
        List<Transaction> transactions = expenseManager.getTransactionLogs();
        for (Transaction t : transactions) {
            if (t.getAccountNo().equals("1234")) {
                assertTrue(t.getAccountNo().equals("1234"));
                assertTrue(t.getAmount() == 500.0);

                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String strDate = dateFormat.format(t.getDate());
                assertTrue(strDate.equals("2022-05-11"));
                assertTrue(t.getExpenseType() == ExpenseType.EXPENSE);

            }
            else if (t.getAccountNo().equals("190137J")) {
                assertTrue(t.getAccountNo().equals("190137J"));
                assertTrue(t.getAmount() == 9999.57);

                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String strDate = dateFormat.format(t.getDate());
                assertTrue(strDate.equals("1999-03-10"));
                assertTrue(t.getExpenseType() == ExpenseType.INCOME);

            }
            else {
                assertTrue(false);
            }
        }
    }
}