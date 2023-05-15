package com.example.farmfresh

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import com.example.farmfresh.Manager.*

class AddInventoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_inventory)

        val submitButton: Button = findViewById(R.id.addInventory)
        submitButton.setOnClickListener {
            submitInventoryData()
        }

        val homeButton: ImageButton = findViewById(R.id.backBtnOfViewProfile)
        homeButton.setOnClickListener {
            finish()
        }

        val resetButton: Button = findViewById(R.id.reset)
        resetButton.setOnClickListener {
            val title: EditText = findViewById(R.id.itemName)
            val data: EditText = findViewById(R.id.Date)
            val unitPrice: EditText = findViewById(R.id.UnitPrice)
            val quantity: EditText = findViewById(R.id.quantity)
            val amount: EditText = findViewById(R.id.total_amount)
            title.setText("")
            data.setText("")
            unitPrice.setText("")
            quantity.setText("")
            amount.setText("")
        }

    }
    fun submitInventoryData(){
        val title: EditText = findViewById(R.id.itemName)
        val data: EditText = findViewById(R.id.Date)
        val unitPrice: EditText = findViewById(R.id.UnitPrice)
        val quantity: EditText = findViewById(R.id.quantity)
        val amount: EditText = findViewById(R.id.total_amount)

        val titleString: String = title.text.toString()
        val dataString: String = data.text.toString()
        val unitPriceString: String = unitPrice.text.toString()
        val quantityString: String = quantity.text.toString()
        val amountString: String = amount.text.toString()

        if (isValidText(titleString)){
            if (isValidText(dataString) ) {
                if (isValidText(unitPriceString) &&  isNumeric(unitPriceString)) {
                    if (isValidText(quantityString) && isNumeric(quantityString)) {
                        if (isValidText(amountString) && isNumeric(amountString)){
                            val inventoryDate = InventoryData("",titleString,dataString,unitPriceString,quantityString, amountString)
                            FIRDatabaseManager.instance.updateInventoryData(inventoryDate,
                                AuthenticationManager.instance.getUserId()!!){
                                if (it){
                                    Toast.makeText(this, "Sucess", 2000).show();
                                    DataManager.instance.inventoryDataArray.add(inventoryDate)
                                    var intent = getIntent()
                                    intent.putExtra("reloadState",true)
                                    setResult(Activity.RESULT_OK,intent)
                                    finish()
                                }else{
                                    Toast.makeText(this, "Fail", 2000).show();
                                }
                            }
                        }else{
                            Toast.makeText(this, "your amount text is not valid", 2000).show();
                        }
                    }else{
                        Toast.makeText(this, "your discription text is not valid", 2000).show();
                    }
                }else{
                    Toast.makeText(this, "your total text is not valid", 2000).show();
                }
            }else{
                Toast.makeText(this, "your date is not valid", 2000).show();
            }
        }else{
            //sourse error
            Toast.makeText(this, "your source is not valid", 2000).show();
        }
    }

    internal fun isValidText(password: String): Boolean {
        if (password.length <= 0) return false
        return true
    }

    fun isNumeric(toCheck: String): Boolean {
        return toCheck.all { char -> char.isDigit() }
    }

}