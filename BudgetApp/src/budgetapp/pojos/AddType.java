
/** POJO for the type of input */
package budgetapp.pojos;

public class AddType {
	private int id;
	private String expenseType;
	
	public AddType() {
		
	}
	
	public AddType(int id, String expenseType) {
		this.id = id;
		this.expenseType = expenseType;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getExpenseType() {
		return expenseType;
	}

	public void setExpenseType(String expenseType) {
		this.expenseType = expenseType;
	}
}
