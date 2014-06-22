package budgetapp.screens;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.budgetapp.R;

import budgetapp.constants.BudgetConstants;
import budgetapp.dao.DatabaseHandler;
import budgetapp.pojos.AddType;
import budgetapp.pojos.BudgetItem;
import budgetapp.util.DatePickerFragment;
import budgetapp.util.InputValidation;
import budgetapp.util.InvalidStringException;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AddToBudgetActivity extends FragmentActivity {

	TextView textViewDate = null;
	Spinner spinner = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_add_to_budget);
		// Create drop down menu
		spinner = (Spinner) findViewById(R.id.spinner);
		// Populate drop down menu
		loadSpinnerData();
		// create the submit button
		final Button button = (Button) findViewById(R.id.submitButton);
		// add a click listener to the submit button
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// Perform action on click
				try {
					addItemToDB();
				} catch (InvalidStringException e) {
					e.printStackTrace();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void addItemToDB() throws InvalidStringException, ParseException {

		// Toast variables
		Context context = getApplicationContext();
		CharSequence toastText = null;
		int duration = Toast.LENGTH_SHORT;
		Toast toast = null;

		// Provides database functionality
		DatabaseHandler db = new DatabaseHandler(this);
		
		int id = 1;
		
		// initialize Views
		EditText description = (EditText) findViewById(R.id.description);
		EditText amount = (EditText) findViewById(R.id.cAmount);
		Spinner budgetItemType = (Spinner) findViewById(R.id.spinner);
		CheckBox expenseCheckBox = (CheckBox) findViewById(R.id.expenseCheckBox);
	
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
		BudgetItem budgetItem = new BudgetItem();
		budgetItem.setItemType(budgetItemType.getSelectedItem().toString());
		budgetItem.setAmount(amount.getText().toString());
		budgetItem.setDate(new Date());
		budgetItem.setDescription(description.getText().toString());
		budgetItem.setId(id);
		budgetItem.setExpense((expenseCheckBox.isChecked())? "1" : "0");
		
		// validate user input (make sure its not empty and in the right format)
		// if the description is not empty..
		if(!(budgetItem.getDescription()).equals(BudgetConstants.EMPTY)) {
			InputValidation validate = new InputValidation(budgetItem.getAmount());
			if(budgetItem.getAmount() != null && !(budgetItem.getAmount()).equals(BudgetConstants.EMPTY)) {
				if(validate.getIsNumber()) {
					if(textViewDate != null)
						budgetItem.setDate(sdf.parse(textViewDate.getText().toString()));
					// grab the amount
					budgetItem.setAmount(validate.getValidatedAmount());
					// all input correct. add to database
					db.addItem(budgetItem);
					// inform user that the item was added successfully to the database
					toastText = "$" + budgetItem.getAmount() + " successfully added to "
							+ budgetItem.getItemType() + " for " + sdf.format(budgetItem.getDate());
				} else {
					toastText = "Incorrect amount entered";
				}
			} else {
				toastText = "Amount cannot be empty!";
			}

		} else {
			// inform user that the description can't be empty
			toastText = "Description cannot be empty!";
		}
		// initialize toast
		toast = Toast.makeText(context, toastText, duration);
		// show toast
		toast.show();

		// reset textViews to show nothing (they do show the hint)
		amount.setText(BudgetConstants.EMPTY);
		description.setText(BudgetConstants.EMPTY);
	}

	public void showDatePickerDialog(View v) {
		textViewDate = (TextView) findViewById(R.id.textViewDate);
		DialogFragment newFragment = new DatePickerFragment(
				textViewDate);
		newFragment.show(getSupportFragmentManager(), "datePicker");
	}
	
	// Populates the drop down menu from the database
	private void loadSpinnerData() {
		
		int c = 0;
        // database handler
        DatabaseHandler db = new DatabaseHandler(getApplicationContext());
 
        // Spinner Drop down elements
        List<AddType> labels = db.getAllTypes();
        String[] dropdownArray = new String[labels.size()];
        
        /** check if labels is empty/null? */
        for(AddType t: labels) {
        	dropdownArray[c++] = t.getExpenseType();
        }
 
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, dropdownArray);
 
        // Drop down layout style -
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
 
        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
	}
}
