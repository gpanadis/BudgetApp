package budgetapp.util;

import java.lang.reflect.Array;
import java.util.regex.Pattern;
import static budgetapp.constants.BudgetConstants.*;

public class InputValidation {

	private boolean isNumber = false;
	private String validatedAmount = null;
	/**
	 * @return the myNumber
	 */
	public String getValidatedAmount() {
		return validatedAmount;
	}

	/**
	 * @param amountToValidate the myNumber to set
	 */
	public void setValidatedAmount(String amountToValidate) {
		this.validatedAmount = amountToValidate;
	}

	/**
	 * @return the isNumber
	 */
	public boolean getIsNumber() {
		return isNumber;
	}

	/**
	 * @param isNumber the isNumber to set
	 */
	public void setIsNumber(boolean isNumber) {
		this.isNumber = isNumber;
	}
	
	public InputValidation() {
		
	}
	public InputValidation(String amount) throws InvalidStringException {
		String dollarAmount = "00";
		String centAmount = "00";
		try {
			String tokens[] = parser(amount);
			if(!tokens[0].equals("")) {
				dollarAmount = new String(
						tokens[0].replaceFirst(LEADING_ZEROES, ""));
			}
			if (Array.getLength(tokens) == DOLLARS_AND_CENTS) {
				Integer cents = Integer.valueOf(tokens[1]);
				if(cents < 10) {
					centAmount = new String(tokens[1] + "0");
				} else {
					centAmount = new String(tokens[1]);
				}
			}
		} catch (InvalidStringException e) {
			validatedAmount = EMPTY;
		}
		 		
		if ((Pattern.matches(PATTERN, dollarAmount) && !dollarAmount.equals(EMPTY))
				&& (Pattern.matches(PATTERN, centAmount) && !centAmount.equals(EMPTY))) {
			setValidatedAmount(dollarAmount + "." + centAmount);
			setIsNumber(true);
		}
		else setIsNumber(false);
	}
	
	/** 
	 * Checks wither the new category entered by the user is valid.
	 * Can't be null and must contain numbers and letters only.
	 * @param newCategory
	 * @return Either returns an error message or confirmation of successfully adding
	 * the new category to the database table
	 */
	public String validateCategory(String newCategory) { 
		String response = null;
		if (newCategory.equals(EMPTY)) {
			response = CATEGORY_ERROR_EMPTY;
		} else if (!newCategory.matches(CHECK_INPUT_VALIDITY)) {
			response = CATEGORY_ERROR_PATTERN;
		} 
		return response;
	}

	public Integer checkAmount(String expAmount) {
		Integer returnCode = 1;
		if (expAmount.equals(EMPTY)) {
			returnCode = -1;
		} else if (!expAmount.matches(CHECK_AMOUNT_VALIDITY)) {
			returnCode = -2;
		}
		return returnCode;
	}
	

	//Public methods.
	 
	// Takes a String as an argument.
	// Splits the string into multiple tokens every time it encounters a dot (.) and places them into a string array.
	// It returns that array. 
	// If there are more than two strings in the array ( meaning more than one dot (.), it will throw an exception instead of returning the array.
	public String[] parser(String s) throws InvalidStringException {
		// Local variables
		String[] input = s.split("\\.");			// split the String s whenever a dot (.) is found (if any), and store it in the String array tokens[]
		String[] output = null;
		String cents = "00";						// default cents are 00
		
		if(input.length > DOLLARS_AND_CENTS || input.length == 0) {	// if the string length is not 1 or 2, throw exception
			throw new InvalidStringException();
		}
		else if(input.length == DOLLARS_ONLY) {						// if the amount is a dollar amount only without cents (ie 54), return the tokens[] array
			output = input;
		}
		else {
			cents = new String(input[1]);								// else if the amount contains cents
			
			try {
				int intCent = Integer.valueOf(cents);
				if(intCent < 10 && cents.length() == 1){			// ie. stops 05 from printing 50
					intCent = intCent * 10;							// modify string and return array
					input[1] = String.valueOf(intCent);
					output = input;
				}
				if(intCent < DOLLAR && cents.length() <= 2) {		// ie. weeds out 015 from printing
					input[1] = cents;								// modify string and return array
					output = input;
				}
				else
					throw new InvalidStringException();				
			}
			catch (NumberFormatException e) {						// if not a number, throw exception
				throw new InvalidStringException();
			}
			
		}
		return output;
	}
}