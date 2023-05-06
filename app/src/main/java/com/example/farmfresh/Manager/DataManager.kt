package com.example.farmfresh.Manager

class DataManager {

    var isWantToLoadInCome = true
    var isWantToLoadInExpense = true
    var isWantToLoadReport = true
    var isWantToLoadInventory = true
    var incomeDataArray = ArrayList<IncomeData>()
    var expenseDataArray = ArrayList<ExpenseData>()
    var reportDataArray = ArrayList<ReportData>()
    var inventoryDataArray = ArrayList<InventoryData>()

    companion object {
        val instance = DataManager()
    }

    fun clearManager(){
        isWantToLoadInCome = true
        isWantToLoadInExpense = true
        isWantToLoadReport = true
        isWantToLoadInventory = true
        incomeDataArray.clear()
        expenseDataArray.clear()
        reportDataArray.clear()
        inventoryDataArray.clear()
    }

}