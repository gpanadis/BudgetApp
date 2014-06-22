/**
 * 
 */
package budgetapp.screens;

import java.util.Locale;

import android.budgetapp.R;
import budgetapp.dao.DatabaseHandler;
import budgetapp.pojos.AddType;
import budgetapp.util.InputValidation;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddTypeActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_type);
		final Button button = (Button) findViewById(R.id.button_add_type);
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// add a type to the Type table when the button is tapped
				EditText editText_type = (EditText) findViewById(R.id.add_type);
				String proposedCategory = editText_type.getText().toString();
				// toast variables
				Context context = getApplicationContext();
				int duration = Toast.LENGTH_SHORT;
				Toast toast = null;    
				InputValidation inputValidation = new InputValidation();
				CharSequence toastText = null;
				toastText = inputValidation.validateCategory(proposedCategory);
				if(toastText == null) {
					toastText = addCategory(proposedCategory);
				}
				editText_type.setText("");
				toast = Toast.makeText(context,  toastText,  duration);
				toast.show();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		return super.onCreateOptionsMenu(menu);
	}
	
	public String addCategory(String proposedCategory) {
		String returnVal = null;
		DatabaseHandler dbHandler = new DatabaseHandler(this);
		
		AddType type = new AddType();
		type.setExpenseType(proposedCategory);
		
		if(dbHandler.addType(type)) {
			returnVal = proposedCategory + " type successfully added!";
		} else
			returnVal = "Oops! The " + proposedCategory.toUpperCase(Locale.getDefault()) + " type already exists!";	
		return returnVal;
	}
}