package budgetapp.dao;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import budgetapp.constants.BudgetConstants;
import budgetapp.pojos.AddType;
import budgetapp.pojos.BudgetItem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper { 
	  
    public DatabaseHandler(Context context) { 
        super(context, BudgetConstants.DATABASE_NAME, null, BudgetConstants.DATABASE_VERSION); 
    } 
  
    // Creating Tables 
    @Override
    public void onCreate(SQLiteDatabase db) { 
        db.execSQL(BudgetConstants.CREATE_EXMPENSES_TABLE);
        db.execSQL(BudgetConstants.CREATE_TYPE_TABLE);
    } 
  
    // Upgrading database 
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { 
        // Drop older table if it exists
        db.execSQL("DROP TABLE IF EXISTS " + BudgetConstants.TABLE_EXPENSES); 
        db.execSQL("DROP TABLE IF EXISTS " + BudgetConstants.TABLE_TYPE);
        // Create database again 
        onCreate(db); 
    }
    
    // Adding new item
    public void addItem(BudgetItem item) {
    	SQLiteDatabase db = this.getWritableDatabase();
    	ContentValues values = new ContentValues();
    	final SimpleDateFormat parser = new SimpleDateFormat("MM-dd-yyyy", Locale.US);
    	values.put(BudgetConstants.KEY_AMOUNT, item.getAmount());
    	values.put(BudgetConstants.KEY_EXPENSE_TYPE,  item.getItemType());
    	values.put(BudgetConstants.KEY_DATE, parser.format(item.getDate()));
    	values.put(BudgetConstants.KEY_ITEM_DESCRIPTION, item.getDescription());
    	values.put(BudgetConstants.KEY_IS_EXPENSE, item.isExpense());
    	
    	// Inserting Row
    	db.insert(BudgetConstants.TABLE_EXPENSES, null, values);
    	db.close();
    } 
    
    // Adding new type
    public boolean addType(AddType type) {
    	
    	boolean returnVal = false;
    	boolean isInTable = false;
    	// check if the type already exists in the table
    	List<AddType> typeList = getAllTypes();
    	String typeInDatabase = null;
    	String potentialType = null;
    	for(AddType t: typeList) {
    		typeInDatabase = t.getExpenseType().toLowerCase(Locale.US);
    		potentialType = type.getExpenseType().toLowerCase(Locale.US);
    		if(typeInDatabase.equals(potentialType)) {
    			isInTable = true;
    		}
    	}
    	// if match was not found, add it
    	if(!isInTable) {
    		ContentValues values = new ContentValues();
	    	values.put(BudgetConstants.KEY_EXPENSE_TYPE,  type.getExpenseType());
	    	SQLiteDatabase db = this.getWritableDatabase();
	    	// Inserting Row
	    	db.insert(BudgetConstants.TABLE_TYPE,  null,  values);
	    	db.close();
	    	returnVal = true;
    	}
    	return returnVal;
    }
      
    // Getting single budget item
    public BudgetItem getExpense(int id) throws NumberFormatException, ParseException {
    	
    	SQLiteDatabase db = this.getReadableDatabase();
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
		Cursor cursor = db.query(BudgetConstants.TABLE_EXPENSES, new String[] {
				BudgetConstants.KEY_ID, BudgetConstants.KEY_EXPENSE_TYPE,
				BudgetConstants.KEY_AMOUNT, BudgetConstants.KEY_DATE,
				BudgetConstants.KEY_ITEM_DESCRIPTION,
				BudgetConstants.KEY_IS_EXPENSE }, BudgetConstants.KEY_ID
				+ "=?", new String[] { String.valueOf(id) }, null, null, null,
				null);
    	if(cursor != null) 
    		cursor.moveToFirst();
    	BudgetItem expense = new BudgetItem();
    	expense.setId(Integer.parseInt(cursor.getString(BudgetConstants.ZERO)));
    	expense.setItemType(cursor.getString(BudgetConstants.FIRST));
    	expense.setAmount(cursor.getString(BudgetConstants.SECOND));
    	expense.setDate(sdf.parse(cursor.getString(BudgetConstants.THIRD)));
    	expense.setDescription(cursor.getString(BudgetConstants.FOURTH));
    	expense.setExpense(cursor.getString(BudgetConstants.FIFTH));
    	
    	db.close();
		return expense;
	} 
      
    // Getting All expenses 
    public List<BudgetItem> getAllExpenses() throws ParseException { 
        List<BudgetItem> contactList = new ArrayList<BudgetItem>(); 
        // Select All Query 
        String selectQuery = "SELECT * FROM " + BudgetConstants.TABLE_EXPENSES; 
     
        SQLiteDatabase db = this.getWritableDatabase(); 
        Cursor cursor = db.rawQuery(selectQuery, null); 
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
     
        // looping through all rows and adding to list 
        if (cursor.moveToFirst()) { 
            do { 
            	BudgetItem expense = new BudgetItem(); 
            	expense.setId(Integer.parseInt(cursor.getString(BudgetConstants.ZERO))); 
            	expense.setDescription(cursor.getString(BudgetConstants.FIRST));
            	expense.setItemType(cursor.getString(BudgetConstants.SECOND)); 
            	expense.setAmount(cursor.getString(BudgetConstants.THIRD));
            	expense.setDate(sdf.parse(cursor.getString(BudgetConstants.FOURTH)));
            	expense.setExpense(cursor.getString(BudgetConstants.FIFTH));
            	
                // Adding expenses to list
                contactList.add(expense);
            } while (cursor.moveToNext());
        }
     
        db.close();
        // return expenses list 
        return contactList;
    }
    
    /**
     * Return a Cursor over the list of all items in the database
     * @return	Cursor over all items
     */
    public Cursor fetchAllItems() {
    	SQLiteDatabase db = this.getWritableDatabase();
    	String[] columns = new String[] {
    			BudgetConstants.KEY_ID, BudgetConstants.KEY_AMOUNT, BudgetConstants.KEY_EXPENSE_TYPE, 
    			BudgetConstants.KEY_DATE, BudgetConstants.KEY_ITEM_DESCRIPTION, BudgetConstants.KEY_IS_EXPENSE
    	};
    	return db.query(BudgetConstants.TABLE_EXPENSES, columns,
    			null, null, null, null, BudgetConstants.KEY_DATE + " DESC");
    }
      
    // Getting expense Count 
    public int getUserCount() {
		return 0;
	} 
    
    // Updating single expense 
    public int updateUser(BudgetItem user) {
    	return 0;
    } 
      
    // Deleting single expense 
    public void deleteUser(BudgetItem user) {
    	
    	SQLiteDatabase db = this.getWritableDatabase(); 
    	db.delete(BudgetConstants.TABLE_EXPENSES, BudgetConstants.KEY_ID + " = ?", new String[] { String.valueOf(user.getId()) }); 
    	db.close(); 
    }

	public List<AddType> getAllTypes() {

		List<AddType> typeList = new ArrayList<AddType>();
		// Select All Query
		String selectQuery = "SELECT * FROM " + BudgetConstants.TABLE_TYPE;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				AddType type = new AddType();
				type.setId(Integer.parseInt(cursor.getString(0)));
				type.setExpenseType(cursor.getString(1));
				// Adding type to list
				typeList.add(type);
			} while (cursor.moveToNext());
		}
		db.close();
		// return type list
		return typeList;
	}

	public BigDecimal getIncomeTotal() {
		
		String query = "SELECT " + BudgetConstants.KEY_AMOUNT + " FROM " + BudgetConstants.TABLE_EXPENSES
				+ " WHERE " + BudgetConstants.KEY_EXPENSE_TYPE + " ='Income'";
		BigDecimal sum = new BigDecimal(0);
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);
		if(cursor.moveToFirst()) {
			do {
				
				sum = sum.add(new BigDecimal(cursor.getString(0)));
			} while( cursor.moveToNext());
		}
		db.close();
		return sum;
	}

	public BigDecimal getExpenseTotal() {
		
		String query = "SELECT " + BudgetConstants.KEY_AMOUNT + " FROM " + BudgetConstants.TABLE_EXPENSES
				+ " WHERE " + BudgetConstants.KEY_EXPENSE_TYPE + " <> 'Income'";
		
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);
		BigDecimal sum = new BigDecimal(0);
		
		if(cursor.moveToFirst()) {
			do {
				sum = sum.add(new BigDecimal(cursor.getString(0)));
			} while( cursor.moveToNext());
		}
		db.close();
		return sum;
	}
}
