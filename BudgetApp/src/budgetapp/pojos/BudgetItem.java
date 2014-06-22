
/** POJO for entries of the Expense table */
package budgetapp.pojos;

import java.util.Date;

public class BudgetItem {
	private int id = 0;
	private String itemType, description = null; 
	private String amount = null;
	private Date date = null;
	private String isExpense = null;
	
	public BudgetItem() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String expenseType) {
		this.itemType = expenseType;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String isExpense() {
		return isExpense;
	}

	public void setExpense(String isExpense) {
		this.isExpense = isExpense;
	}

}
