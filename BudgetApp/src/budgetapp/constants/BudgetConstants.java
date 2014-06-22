package budgetapp.constants;

public class BudgetConstants {

	// common
	public static final String EMPTY = "";
	public static final String PREFS_NAME = "MyPrefsFile";
	public static final String FIRST_TIME_LAUNCH = "first_time_launch";
	public static final int ZERO = 0;
	public static final int FIRST = 1;
	public static final int SECOND = 2;
	public static final int THIRD = 3;
	public static final int FOURTH = 4;
	public static final int FIFTH = 5;
	
	// InputValidation.class
	public static final String CHECK_INPUT_VALIDITY = "[a-zA-Z0-9 ]+";
	public static final String CHECK_AMOUNT_VALIDITY = "[0-9]+";
	public static final int DOLLARS_AND_CENTS = 2;
	public static final int DOLLARS_ONLY = 1;
	public static final int CENTS_IN_A_DOLLAR = 100;
	public static final String CATEGORY_ERROR_EMPTY = "Category cannot be empty";
	public static final String CATEGORY_ERROR_PATTERN = "Category must contain a combination of letters and numbers";
	public static final String PATTERN = "\\d*";
	public static final String LEADING_ZEROES = "0*";
	public static final int DOLLAR = 100;
	
	
	/** DatabaseHandler **/
	 // Database Version 
    public static final int DATABASE_VERSION = 1; 
  
    // Database Name 
    public static final String DATABASE_NAME = "DB_BUDGETAPP"; 
  
    // Expenses table name 
    public static final String TABLE_EXPENSES = "EXPENSES"; 
    public static final String TABLE_TYPE = "TYPE";
  
    // Expenses Table Columns names 
    public static final String KEY_ID = "_id"; 
    public static final String KEY_EXPENSE_TYPE = "type"; 
    public static final String KEY_AMOUNT = "amount";
    public static final String KEY_DATE = "date";
    public static final String KEY_ITEM_DESCRIPTION = "description";
    public static final String KEY_IS_EXPENSE = "isExpense";
    
    public static final String CREATE_EXMPENSES_TABLE = "CREATE TABLE " + TABLE_EXPENSES + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_ITEM_DESCRIPTION + " TEXT," + KEY_EXPENSE_TYPE + " TEXT,"
            + KEY_AMOUNT + " TEXT," + KEY_DATE + " DATE, " + KEY_IS_EXPENSE + " TEXT" + ")"; 
    
    public static final String CREATE_TYPE_TABLE = "CREATE TABLE " + TABLE_TYPE + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_EXPENSE_TYPE + " TEXT" + ")"; 
	
    
    
}
