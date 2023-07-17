package datafactory;

import dataobjects.Budget;
import utilities.JavaHelpers;

public class BudgetData {

    public Budget getBudgetData() throws InterruptedException {

        Budget data = new Budget();
        Integer amount = Integer.valueOf(new JavaHelpers().getRandomNumber(1000,5000));
        data.setBudgetAmount(amount);

        return data;
    }
}
