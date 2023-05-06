package com.example.farmfresh

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.farmfresh.Manager.AuthenticationManager
import com.example.farmfresh.Manager.DataManager
import com.example.farmfresh.Manager.FIRDatabaseManager
import com.example.farmfresh.R

class ExpenseRecycleView() : RecyclerView.Adapter<ExpenseRecycleView.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.income_cell, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (DataManager.instance.expenseDataArray.size == position){
            holder.incomeView.text = "Total Expense"
            holder.priceDate.text = ""
            holder.priceValue.text = getTotalExpense().toString()
        }else {
            val ItemsViewModel = DataManager.instance.expenseDataArray[position]
            holder.incomeView.text = ItemsViewModel.discription
            holder.priceDate.text = ItemsViewModel.date
            holder.priceValue.text = ItemsViewModel.amount
        }
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        if (DataManager.instance.expenseDataArray.isEmpty()){
            return  0
        }
        return DataManager.instance.expenseDataArray.size + 1
    }

    // Holds the views for adding it to image and text
   inner class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView), View.OnClickListener {
        val incomeView: TextView = itemView.findViewById(R.id.incomeName)
        val priceDate:TextView = itemView.findViewById(R.id.incomeDate)
        val priceValue:TextView = itemView.findViewById(R.id.incomeValue)
        var deleteButton: Button = itemView.findViewById(R.id.deleteAction)

        override fun onClick(view: View?) {
            var expense = DataManager.instance.expenseDataArray[adapterPosition]
            var userId = AuthenticationManager.instance.getUserId()!!
            FIRDatabaseManager.instance.deleteExpenseData(expense.expenseId,userId){
                if (it){
                    DataManager.instance.expenseDataArray.removeAt(adapterPosition)
                    notifyDataSetChanged()
                }
            }
        }

        init {
            deleteButton.setOnClickListener(this)
        }

    }

    fun getTotalExpense():Int{
        var amount = 0
        for (data in DataManager.instance.expenseDataArray){
            amount += data.amount?.toInt() ?: 0
        }
        return  amount
    }

}

