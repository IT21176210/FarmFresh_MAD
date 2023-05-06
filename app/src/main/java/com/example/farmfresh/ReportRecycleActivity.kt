package com.example.farmfresh

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.farmfresh.Manager.AuthenticationManager
import com.example.farmfresh.Manager.DataManager
import com.example.farmfresh.Manager.FIRDatabaseManager

class ReportRecycleActivity() : RecyclerView.Adapter<ReportRecycleActivity.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_report_cell, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ItemsViewModel = DataManager.instance.reportDataArray[position]
        holder.income.text = ItemsViewModel.income
        holder.expense.text = ItemsViewModel.expense
        holder.dis.text = ItemsViewModel.discription
        holder.date.text = ItemsViewModel.date
        val incomeInt:Int = ItemsViewModel.income!!.toInt()
        val expenseInt:Int = ItemsViewModel.income!!.toInt()
        holder.balance.text = (incomeInt - expenseInt).toString()
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return DataManager.instance.reportDataArray.size
    }

    // Holds the views for adding it to image and text
   inner class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) , View.OnClickListener{
        val date: TextView = itemView.findViewById(R.id.reportDate)
        val dis: TextView = itemView.findViewById(R.id.reportDis)
        val income: TextView = itemView.findViewById(R.id.income)
        val expense: TextView = itemView.findViewById(R.id.expense)
        val balance: TextView = itemView.findViewById(R.id.balanceId)

        var deleteButton: Button = itemView.findViewById(R.id.deleteAction)
        override fun onClick(view: View?) {
            var report = DataManager.instance.reportDataArray[adapterPosition]
            var userId = AuthenticationManager.instance.getUserId()!!
            FIRDatabaseManager.instance.deleteReportData(report.reportId,userId){
                if (it){
                    DataManager.instance.reportDataArray.removeAt(adapterPosition)
                    notifyDataSetChanged()
                }
            }
        }

        init {
            deleteButton.setOnClickListener(this)
        }

    }
}