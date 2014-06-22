package budgetapp.screens;

import android.budgetapp.R;

import budgetapp.constants.BudgetConstants;
import budgetapp.dao.DatabaseHandler;
import budgetapp.pojos.AddType;


import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Main activity for the Budget Application
 * Presents the user with the "main menu" where they can select to :
 * Add an item to the budget
 * Add a new item category
 * View your budget
 * @author hpTheGreat
 */
public class MainActivity extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Produces a list of predefined items to act as an interactive list. 
		// Doesn't use a traditional setContentView(R.layout.activity_main method call
		String[] list_products = getResources().getStringArray(R.array.list_products);		// array of the list items		layout_list_data.xml
		this.setListAdapter(new ArrayAdapter<String>(this, 
														R.layout.list_item,	 				// what each item looks like	layout/list_item.xml  Note the R.layout. call
														R.id.label, 						// name of that TextView		layout/list_item.xml
														list_products));
		ListView lv = getListView();
        // listening to single list item on click
        lv.setOnItemClickListener(new OnItemClickListener() {
          @Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {              
              // Create a new Intent based on the position of the selected item
              Intent i = null;
              switch(position) {
              case 0: 
            	  i = new Intent(getApplicationContext(), AddToBudgetActivity.class);
            	  break;
              case 1: 
            	  i = new Intent(getApplicationContext(), AddTypeActivity.class);
            	  break;
              case 2: 
            	  i = new Intent(getApplicationContext(), ViewBudgetActivity.class);
            	  break;
              }
              // Launch activity
              startActivity(i);
          }
        });
        
		// check if application is running for the first time
		SharedPreferences settings = getSharedPreferences(BudgetConstants.PREFS_NAME, 0);
		if(settings.getBoolean(BudgetConstants.FIRST_TIME_LAUNCH,  true)) {
			// if this is the first time the application is run, populate the database table with some predefined categories
			initTypeTable();
			settings.edit().putBoolean(BudgetConstants.FIRST_TIME_LAUNCH,  false).commit();
		}
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	/**
	 * Initializes the Type table which holds a list of all the different types of items that populate the
	 * drop down menu in the AddExpenseType activity. 
	 * 
	 * @param no parameters
	 * @return nothing to return
	 */
	private void initTypeTable() {
		String[] types = new String[]{ 
				"Student Loans", 
				"Mortgage", 
				"Groceries",
				"Allowance"};
		
		DatabaseHandler db = new DatabaseHandler(this);
		
		for(int i = 0; i < types.length; i++) {
			AddType type = new AddType();
			type.setExpenseType(types[i]);
			db.addType(type);
		}
	}
}