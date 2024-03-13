package com.example.rucafe

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageButton
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.rucafe.activity.CoffeeActivity
import com.example.rucafe.activity.DonutActivity
import com.example.rucafe.activity.OrderActivity
import com.example.rucafe.activity.StoreActivity
import com.example.rucafe.viewmodel.DonutViewModel

/**
 * This is an example of an Activity where a RecyclerView is used.
 * @author Lily Chang
 */
class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: DonutViewModel

    // XML References
    private var coffeeImageButton: ImageButton? = null
    private var donutImageButton: ImageButton? = null
    private var orderImageButton: ImageButton? = null
    private var storeImageButton: ImageButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize the ViewModel
        viewModel = ViewModelProvider(this)[DonutViewModel::class.java]

        // Assign buttons by id
        coffeeImageButton = findViewById(R.id.coffeeImageButton)
        donutImageButton = findViewById(R.id.donutImageButton)
        orderImageButton = findViewById(R.id.orderImageButton)
        storeImageButton = findViewById(R.id.storeImageButton)

        // Add on click handler for order coffee button
        coffeeImageButton!!.setOnClickListener { navigateToOrderingCoffee() }

        // Add on click listener for order donut button
        donutImageButton!!.setOnClickListener { navigateToOrderingDonut() }

        // Add on click listener for current order button
        orderImageButton!!.setOnClickListener { navigateToCurrentOrder() }

        // Add on click listener for store orders button
        storeImageButton!!.setOnClickListener { navigateToStoreOrders() }

        // Calling the action bar
        val actionBar: ActionBar = supportActionBar!!

        actionBar.setDisplayHomeAsUpEnabled(true)
    }

    // This event will enable the back
    // function to the button on press
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                this.finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * Navigate to the CoffeeActivity
     */
    fun navigateToOrderingCoffee() {
        val gotoOrderCoffee: Intent = Intent(this, CoffeeActivity::class.java)
        startActivity(gotoOrderCoffee)
    }

    /**
     * Navigate to the DonutActivity
     */
    fun navigateToOrderingDonut() {

        // Use the donut list from the ViewModel
        val donuts = viewModel.getDonuts().value

        val gotoOrderDonut: Intent = Intent(this, DonutActivity::class.java)
        gotoOrderDonut.putExtra("Donuts", donuts?.let { ArrayList(it) })
        startActivity(gotoOrderDonut)
    }

    /**
     * Navigate to the CurrentOrderActivity
     */
    fun navigateToCurrentOrder() {
        val gotoCurrentOrder: Intent = Intent(this, OrderActivity::class.java)
        startActivity(gotoCurrentOrder)
    }

    /**
     * Navigate to the StoreOrdersActivity
     */
    fun navigateToStoreOrders() {
        val gotoStoreOrders: Intent = Intent(this, StoreActivity::class.java)
        startActivity(gotoStoreOrders)
    }
}