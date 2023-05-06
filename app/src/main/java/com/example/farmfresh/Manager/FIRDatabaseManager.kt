package com.example.farmfresh.Manager

import android.util.Log
import com.google.android.gms.auth.api.Auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class FIRDatabaseManager {
    companion object {
        val instance = FIRDatabaseManager()
        private const val TAG = "FIRDatabaseManager"
    }

    fun updateIncome(income: IncomeData, userId: String,callback: (Boolean) -> Unit) {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        val database = Firebase.database.getReference("User")
        val db = database.child(userId).child("Income")
        var incomeData = income
        if (incomeData.incomeId == ""){
            val ref = db.push()
            val key = ref.key  // this is your key
            incomeData.incomeId = key.toString()
        }
        val incomeValues = income.toMap()
        db.child(incomeData.incomeId).updateChildren(incomeValues).addOnSuccessListener {
            callback(true)
        }.addOnFailureListener {
            callback(false)
        }

    }

    fun updateExpense(income: ExpenseData, userId: String,callback: (Boolean) -> Unit) {
        val database = Firebase.database.getReference("User")
        val db = database.child(userId).child("Expense")
        var incomeData = income
        if (incomeData.expenseId == ""){
            val ref = db.push()
            val key = ref.key  // this is your key
            incomeData.expenseId = key.toString()
        }
        val incomeValues = income.toMap()
        db.child(incomeData.expenseId).updateChildren(incomeValues).addOnSuccessListener {
            callback(true)
        }.addOnFailureListener {
            callback(false)
        }

    }

    fun updateReportData(report: ReportData, userId: String,callback: (Boolean) -> Unit) {
        val database = Firebase.database.getReference("User")
        val db = database.child(userId).child("Report")
        var reportData = report
        if (reportData.reportId == ""){
            val ref = db.push()
            val key = ref.key  // this is your key
            reportData.reportId = key.toString()
        }
        val reportValues = report.toMap()
        db.child(reportData.reportId).updateChildren(reportValues).addOnSuccessListener {
            callback(true)
        }.addOnFailureListener {
            callback(false)
        }
    }

    fun updateInventoryData(report: InventoryData, userId: String,callback: (Boolean) -> Unit) {
        val database = Firebase.database.getReference("User")
        val db = database.child(userId).child("Inventory")
        var reportData = report
        if (reportData.inventoryId == ""){
            val ref = db.push()
            val key = ref.key  // this is your key
            reportData.inventoryId = key.toString()
        }
        val reportValues = report.toMap()
        db.child(reportData.inventoryId).updateChildren(reportValues).addOnSuccessListener {
            callback(true)
        }.addOnFailureListener {
            callback(false)
        }
    }

    fun updateUserData(report: User, userId: String,callback: (Boolean) -> Unit) {
        val database = Firebase.database.getReference("User")
        val db = database.child(userId).child("Detail")
        var reportData = report
        val reportValues = report.toMap()
        db.updateChildren(reportValues).addOnSuccessListener {
            callback(true)
        }.addOnFailureListener {
            callback(false)
        }
    }

    fun getIncomeData(userId:String,callback: (ArrayList<IncomeData>) -> Unit){
        val database = Firebase.database.getReference("User")
        val db = database.child(userId).child("Income")

        db.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var dataArry = ArrayList<IncomeData>()
                if (dataSnapshot.exists()) {
                    val map = dataSnapshot.value as Map<String, DataSnapshot>
                    // TODO: handle the post
                    for (snap in map) {
                        val map2 = snap.value as Map<String, String>
                        val income = IncomeData()
                        if (income != null) {
                            income.incomeId = map2["incomeId"].toString()
                            income.amount = map2["amount"].toString()
                            income.date = map2["date"].toString()
                            income.discription = map2["discription"].toString()
                            income.amount = map2["amount"].toString()
                            income.clint_customer = map2["clint_customer"].toString()
                            dataArry.add(income)
                        }
                    }
                }
                DataManager.instance.isWantToLoadInCome = false
                DataManager.instance.incomeDataArray = dataArry
                callback(dataArry)
            }


            override fun onCancelled(databaseError: DatabaseError) {
                var dataArry = ArrayList<IncomeData>()
                callback(dataArry)
                Log.d(TAG, "error:")
            }
        })

    }


    fun getExpenseData(userId:String,callback: (ArrayList<ExpenseData>) -> Unit){
        val database = Firebase.database.getReference("User")
        val db = database.child(userId).child("Expense")


        db.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var dataArry = ArrayList<ExpenseData>()
                if (dataSnapshot.exists()) {
                    val map = dataSnapshot.value as Map<String, DataSnapshot>
                    for (snap in map) {
                        val map2 = snap.value as Map<String, String>
                        val income = ExpenseData()
                        if (income != null) {
                            income.expenseId = map2["incomeId"].toString()
                            income.amount = map2["amount"].toString()
                            income.date = map2["date"].toString()
                            income.discription = map2["discription"].toString()
                            income.amount = map2["amount"].toString()
                            income.clint_customer = map2["clint_customer"].toString()
                            dataArry.add(income)
                        }
                    }
                }
                DataManager.instance.isWantToLoadInExpense = false
                DataManager.instance.expenseDataArray = dataArry
                callback(dataArry)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                var dataArry = ArrayList<ExpenseData>()
                callback(dataArry)
                Log.d(TAG, "error:")
            }
        })

    }


    fun getReportsData(userId:String,callback: (ArrayList<ReportData>) -> Unit){
        val database = Firebase.database.getReference("User")
        val db = database.child(userId).child("Report")

        db.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var dataArry = ArrayList<ReportData>()
                if (dataSnapshot.exists()) {
                    val map = dataSnapshot.value as Map<String, DataSnapshot>
                    for (snap in map) {
                        val map2 = snap.value as Map<String, String>
                        val report = ReportData()
                        if (report != null) {
                            report.reportId = map2["reportId"].toString()
                            report.income = map2["income"].toString()
                            report.expense = map2["expense"].toString()
                            report.date = map2["date"].toString()
                            report.discription = map2["discription"].toString()
                            dataArry.add(report)
                        }
                    }
                }

                DataManager.instance.isWantToLoadReport = false
                DataManager.instance.reportDataArray = dataArry
                callback(dataArry)
            }


            override fun onCancelled(databaseError: DatabaseError) {
                var dataArry = ArrayList<ReportData>()
                callback(dataArry)
                Log.d(TAG, "error:")
            }
        })

    }


    fun getInventoryData(userId:String,callback: (ArrayList<InventoryData>) -> Unit){
        val database = Firebase.database.getReference("User")
        val db = database.child(userId).child("Inventory")

        db.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var dataArry = ArrayList<InventoryData>()
                if (dataSnapshot.exists()) {
                    val map = dataSnapshot.value as Map<String, DataSnapshot>
                    for (snap in map) {
                        val map2 = snap.value as Map<String, String>
                        val income = InventoryData()
                        if (income != null) {
                            income.inventoryId = map2["inventoryId"].toString()
                            income.totalAmount = map2["totalAmount"].toString()
                            income.date = map2["date"].toString()
                            income.itemName = map2["itemName"].toString()
                            income.unitPrice = map2["unitPrice"].toString()
                            income.quantity = map2["quantity"].toString()
                            dataArry.add(income)
                        }
                    }
                }

                DataManager.instance.isWantToLoadInventory = false
                DataManager.instance.inventoryDataArray = dataArry
                callback(dataArry)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                var dataArry = ArrayList<InventoryData>()
                callback(dataArry)
                Log.d(TAG, "error:")
            }
        })

    }

    fun getUserData(userId:String,callback: (User) -> Unit){
        val database = Firebase.database.getReference("User")
        val db = database.child(userId).child("Detail")

        db.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var user = User()

                if (dataSnapshot.exists()) {
                    for (postSnapshot in dataSnapshot.children) {
                        val map2 = dataSnapshot.value as Map<String, String>

                        if (user != null) {
                            user.userId = map2["userId"].toString()
                            user.date = map2["date"].toString()
                            user.email = map2["email"].toString()
                            user.phone = map2["phone"].toString()
                            user.farmName = map2["farmName"].toString()
                            user.fullName = map2["fullName"].toString()
                        }
                    }
                }

                callback(user)
            }


            override fun onCancelled(databaseError: DatabaseError) {
                var dataArry = User()
                dataArry.userId = userId
                callback(dataArry)
                Log.d(TAG, "error:")
            }
        })

    }

    fun deleteIncomeData(incomeId: String, userId: String,callback: (Boolean) -> Unit) {
        val database = Firebase.database.getReference("User")
        val db = database.child(userId).child("Income")
        db.child(incomeId).removeValue().addOnSuccessListener {
            callback(true)
        }.addOnFailureListener {
            callback(false)
        }
    }

    fun deleteExpenseData(expenseId: String, userId: String,callback: (Boolean) -> Unit) {
        val database = Firebase.database.getReference("User")
        val db = database.child(userId).child("Expense")
        db.child(expenseId).removeValue().addOnSuccessListener {
            callback(true)
        }.addOnFailureListener {
            callback(false)
        }
    }

    fun deleteReportData(reportId: String, userId: String,callback: (Boolean) -> Unit) {
        val database = Firebase.database.getReference("User")
        val db = database.child(userId).child("Report")
        db.child(reportId).removeValue().addOnSuccessListener {
            callback(true)
        }.addOnFailureListener {
            callback(false)
        }
    }

    fun deleteInventoryData(inventoryId: String, userId: String,callback: (Boolean) -> Unit) {
        val database = Firebase.database.getReference("User")
        val db = database.child(userId).child("Inventory")
        db.child(inventoryId).removeValue().addOnSuccessListener {
            callback(true)
        }.addOnFailureListener {
            callback(false)
        }
    }

}

@IgnoreExtraProperties
data class IncomeData(
    var incomeId:String = "",
    var source: String? = "",
    var date: String? = "",
    var amount: String? = "",
    var clint_customer: String? = "",
    var discription: String = "",
) {

    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "incomeId" to incomeId,
            "source" to source,
            "date" to date,
            "amount" to amount,
            "clint_customer" to clint_customer,
            "discription" to discription,
        )
    }
}

@IgnoreExtraProperties
data class ExpenseData(
    var expenseId:String = "",
    var source: String? = "",
    var date: String? = "",
    var amount: String? = "",
    var clint_customer: String? = "",
    var discription: String = "",
) {

    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "expenseId" to expenseId,
            "source" to source,
            "date" to date,
            "amount" to amount,
            "clint_customer" to clint_customer,
            "discription" to discription,
        )
    }
}

@IgnoreExtraProperties
data class ReportData(
    var reportId:String = "",
    var discription: String? = "",
    var income: String? = "",
    var expense: String? = "",
    var date: String? = "",
) {

    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "reportId" to reportId,
            "discription" to discription,
            "income" to income,
            "expense" to expense,
            "date" to date,
        )
    }
}

@IgnoreExtraProperties
data class InventoryData(
    var inventoryId:String = "",
    var itemName:String = "",
    var date: String? = "",
    var unitPrice: String? = "",
    var quantity: String? = "",
    var totalAmount: String? = "",
) {

    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "inventoryId" to inventoryId,
            "itemName" to itemName,
            "date" to date,
            "unitPrice" to unitPrice,
            "quantity" to quantity,
            "totalAmount" to totalAmount
        )
    }
}

@IgnoreExtraProperties
data class User(
    var userId:String = "",
    var fullName:String = "",
    var date: String? = "",
    var phone: String? = "",
    var email: String? = "",
    var farmName: String? = "",
) {

    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "userId" to userId,
            "fullName" to fullName,
            "date" to date,
            "phone" to phone,
            "email" to email,
            "farmName" to farmName
        )
    }
}