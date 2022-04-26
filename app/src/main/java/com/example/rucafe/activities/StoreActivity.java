package com.example.rucafe.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.rucafe.MainActivity;
import com.example.rucafe.R;
import com.example.rucafe.models.MenuItem;
import com.example.rucafe.models.Order;
import com.example.rucafe.models.StoreOrders;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class StoreActivity extends AppCompatActivity {
    private static DecimalFormat DECIMAL_FORMAT = new DecimalFormat("$###,##0.00");

    private TextView tv_totalPrice, tv_salesTax, tv_subtotal;
    private ListView lv_storeOrderListView;
    private Button btn_removeOrder, btn_showOrder;
    private Order currentOrder;
    private StoreOrders currentStoreOrder;
    protected ArrayList<Order> storeOrders = new ArrayList<>();
    double total, subtotal, salesTax;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        currentOrder = null;
        currentStoreOrder = StoreOrders.getCurrentOrder();

        tv_totalPrice = (TextView) findViewById(R.id.tv_totalOrder);
        tv_subtotal = (TextView) findViewById(R.id.tv_subtotalOrder);
        tv_salesTax = (TextView) findViewById(R.id.tv_salesTaxOrder);
        lv_storeOrderListView = (ListView) findViewById(R.id.lv_listStoreOrder);

        btn_removeOrder = (Button) findViewById(R.id.btn_removeStoreOrder);
        btn_showOrder = (Button) findViewById(R.id.btn_showOrder);

        lv_storeOrderListView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        lv_storeOrderListView.setSelector(R.color.design_default_color_primary);
    }

    @Override
    protected void onStart() {
        super.onStart();

        lv_storeOrderListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentOrder = (Order) lv_storeOrderListView.getItemAtPosition(position);
                btn_showOrder.setEnabled(true);
                btn_removeOrder.setEnabled(true);
            }
        });

        // Remove order from store orders listener
        btn_removeOrder.setOnClickListener(v -> {
            this.removeOrderFromStore();
        });

        btn_showOrder.setOnClickListener(v -> {
            this.showOrder();
            this.calculateAndDisplayPrice();
        });

        // Update UI and recompute price
        this.updateUI();
    }

    private void removeOrderFromStore() {
        if (storeOrders.isEmpty()) {
            Toast.makeText(StoreActivity.this, "Error: Cannot remove order.", Toast.LENGTH_LONG).show();
        }
        else {
            storeOrders.remove(this.currentOrder);
            Toast.makeText(StoreActivity.this, "Removed order successfully.", Toast.LENGTH_LONG).show();
        }

        this.updateUI();
    }

    private void showOrder() {
        lv_storeOrderListView.clearChoices();
        lv_storeOrderListView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, currentOrder.getOrderList()));
        this.btn_showOrder.setEnabled(false);
    }

    /**
     * Update UI, clear selections and repopulate order listview. Also check if placeOrder should be disabled
     */
    private void updateUI() {
        // Update UI
        lv_storeOrderListView.clearChoices();
        lv_storeOrderListView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, currentStoreOrder.getOrderBook()));
        this.btn_showOrder.setEnabled(false);

        // Check if remove order button should be disabled
        btn_removeOrder.setEnabled(!this.currentStoreOrder.isOrderBookEmpty());
    }

    /**
     * Recompute all the prices and update text boxes
     */
    private void calculateAndDisplayPrice() {
        // reset subtotal and recalc
        subtotal = currentOrder.getSubTotal();
        // Update subtotal formatted as currency
        tv_subtotal.setText(DECIMAL_FORMAT.format(subtotal));

        // reset sales tax and recalc
        salesTax = currentOrder.calcSalesTax();
        // Update sales tax formatted as currency
        tv_salesTax.setText(DECIMAL_FORMAT.format(salesTax));

        // reset total price and recalc
        total = subtotal + salesTax;
        // Update total formatted as currency
        tv_totalPrice.setText(DECIMAL_FORMAT.format(total));
    }
}
