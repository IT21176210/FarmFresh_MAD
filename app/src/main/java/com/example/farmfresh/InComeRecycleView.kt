import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.farmfresh.Manager.AuthenticationManager
import com.example.farmfresh.Manager.DataManager
import com.example.farmfresh.Manager.FIRDatabaseManager
import com.example.farmfresh.R
import com.facebook.appevents.codeless.internal.ViewHierarchy.setOnClickListener
import java.lang.ref.WeakReference

class InComeRecycleView() : RecyclerView.Adapter<InComeRecycleView.ViewHolder>() {

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

        if (DataManager.instance.incomeDataArray.size == position){
            holder.incomeView.text = "Total Income"
            holder.priceDate.text = ""
            holder.priceValue.text = getTotalIncome().toString()
            holder.deleteButton.isVisible = false
        }else {
            val ItemsViewModel = DataManager.instance.incomeDataArray[position]
            holder.incomeView.text = ItemsViewModel.discription
            holder.priceDate.text = ItemsViewModel.date
            holder.priceValue.text = ItemsViewModel.amount
        }
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        if (DataManager.instance.incomeDataArray.isEmpty()){
            return  0
        }
        return DataManager.instance.incomeDataArray.size + 1
    }

    // Holds the views for adding it to image and text
    inner class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView), View.OnClickListener {
        val incomeView: TextView = itemView.findViewById(R.id.incomeName)
        val priceDate:TextView = itemView.findViewById(R.id.incomeDate)
        val priceValue:TextView = itemView.findViewById(R.id.incomeValue)
        var deleteButton:Button = itemView.findViewById(R.id.deleteAction)

        override fun onClick(view: View?) {
            var income = DataManager.instance.incomeDataArray[adapterPosition]
            var userId = AuthenticationManager.instance.getUserId()!!
            FIRDatabaseManager.instance.deleteIncomeData(income.incomeId,userId){
                if (it){
                    DataManager.instance.incomeDataArray.removeAt(adapterPosition)
                    notifyDataSetChanged()
                }
            }
        }

        init {
            deleteButton.setOnClickListener(this)
        }

    }


    fun getTotalIncome():Int{
        var amount = 0
        for (data in DataManager.instance.incomeDataArray){
            amount += data.amount?.toInt() ?: 0
        }
        return  amount
    }

}

class IncomeData internal constructor(
    var name: String,
    var date: String,
    var price: String
)
