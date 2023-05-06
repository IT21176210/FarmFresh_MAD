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

class InventoryRecycleView() : RecyclerView.Adapter<InventoryRecycleView.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_inventory_cell, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ItemsViewModel = DataManager.instance.inventoryDataArray[position]
        holder.inventoryUnitPrice.text = ItemsViewModel.unitPrice
        holder.inventoryDiscription.text = ItemsViewModel.itemName
        holder.inventoryPrice.text = ItemsViewModel.totalAmount
        holder.inventoryQnantity.text = ItemsViewModel.quantity
        holder.inventoryDate.text = ItemsViewModel.date
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return DataManager.instance.inventoryDataArray.size
    }

    // Holds the views for adding it to image and text
  inner class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) , View.OnClickListener{

        val inventoryUnitPrice:TextView = itemView.findViewById(R.id.unitPrice)
        val inventoryDiscription:TextView = itemView.findViewById(R.id.inventoryDiscription)
        val inventoryPrice:TextView = itemView.findViewById(R.id.inventoryPrice)
        val inventoryQnantity:TextView = itemView.findViewById(R.id.inventoryQnantity)
        val inventoryDate:TextView = itemView.findViewById(R.id.inventoryDate)
        val inventoryImageView: ImageView = itemView.findViewById(R.id.imageView)

        var deleteButton: Button = itemView.findViewById(R.id.deleteAction)

        override fun onClick(view: View?) {
            var inventry = DataManager.instance.inventoryDataArray[adapterPosition]
            var userId = AuthenticationManager.instance.getUserId()!!
            FIRDatabaseManager.instance.deleteInventoryData(inventry.inventoryId,userId){
                if (it){
                    DataManager.instance.inventoryDataArray.removeAt(adapterPosition)
                    notifyDataSetChanged()
                }
            }
        }

        init {
            deleteButton.setOnClickListener(this)
        }


    }
}