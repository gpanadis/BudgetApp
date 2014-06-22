package budgetapp.util;

import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.TextView;

@SuppressLint("ValidFragment")
public class DatePickerFragment extends DialogFragment
                            implements DatePickerDialog.OnDateSetListener  {

	TextView lblDate;
	public DatePickerFragment() {
		
	}
	public DatePickerFragment(TextView lblDate) {
		this.lblDate = lblDate;
	}
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
	public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
    	lblDate.setText( (month+1) + "/" + day + "/" + year);
    	// add it to the database
    }
}