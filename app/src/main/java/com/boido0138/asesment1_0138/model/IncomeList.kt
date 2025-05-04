package com.boido0138.asesment1_0138.model

object IncomeList {
    private val _incomeList =  mutableListOf<Income>()

    val incomeList : List<Income> get() = _incomeList

    var tempIncome : Income = Income(1,"", 0,"Date")

    fun addToIncomeList(income: Income){
        _incomeList.add(income)
    }

    fun sumIncome() : Int{
        var totalValue = 0

        for (income in _incomeList) {
            totalValue += income.values
        }

        return  totalValue
    }
}