package com.boido0138.asesment1_0138.model

object ExpenseList {
    private val _expenseList =  mutableListOf<Expense>()

    val expenseList : List<Expense> get() = _expenseList

    var tempExpense : Expense = Expense("", 0, "Tags" ,"Date")

    fun addToExpenseLis(expense: Expense){
        _expenseList.add(expense)
    }

    fun sumExpense() : Int{
        var totalValue = 0

        for (expense in _expenseList) {
            totalValue += expense.values
        }

        return  totalValue
    }
}