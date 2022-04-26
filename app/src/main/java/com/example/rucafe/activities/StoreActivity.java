package com.example.rucafe.activities;

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

import com.example.rucafe.R;
import com.example.rucafe.models.Order;
import com.example.rucafe.models.StoreOrders;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * The Activity class that creates the store orders GUI display.
 * @author Taze Balbosa, Yulie Ying
 */
public class StoreActivity extends AppCompatActivity {
    private static DecimalFormat DECIMAL_FORMAT = new DecimalFormat("$###,##0.00");

    private TextView tv_totalPrice, tv_salesTax, tv_subtotal;
    private ListView lv_storeOrderListView;
    private Button btn_removeOrder, btn_showOrder;
    private Order currentOrder;
    private StoreOrders currentStoreOrder;
    protected ArrayList<Order> storeOrders = new ArrayList<>();
    double total, subtotal, salesTax;

    /**
     * Creates and starts the store order menu UI.
     * @param savedInstanceState The reference to a Bundle object that is
     * passed into the onCreate method of every Android Activity
     */
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

    /**
     * Starts the ListViews and contains methods for button actions.
     */
    @Override
    protected void onStart() {
        super.onStart();

        lv_storeOrderListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /**
             * Processes action when clicking a specific order in store orders list.
             * @param parent The AdapterView where the selection happened.
             * @param view The view within the AdapterView that was clicked.
             * @param position The position of the view in the adapter.
             * @param id The row id of the item that is selected.
             */
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentOrder = (Order) lv_storeOrderListView.getItemAtPosition(position);
                btn_showOrder.setEnabled(true);
                btn_removeOrder.setEnabled(true);
            }
        });

        btn_removeOrder.setOnClickListener(v -> {
            this.removeOrderFromStore();
        });
        btn_showOrder.setOnClickListener(v -> {
            this.showOrder();
            this.calculateAndDisplayPrice();
        });


        this.updateUI();
    }

    /**
     * Removes an order from the store orders list.
     */
    private void removeOrderFromStore() {
        if (currentStoreOrder.isOrderBookEmpty()) {
            Toast.makeText(StoreActivity.this, "Error: Cannot remove order.", Toast.LENGTH_LONG).show();
        }
        else {
            currentStoreOrder.remove(this.currentOrder);
            Toast.makeText(StoreActivity.this, "Removed order successfully.", Toast.LENGTH_LONG).show();
        }

        this.updateUI();
    }

    /**
     * Shows a detailed list of a order in the store orders.
     */
    private void showOrder() {
        lv_storeOrderListView.clearChoices();
        lv_storeOrderListView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, currentOrder.getOrderList()));
        this.btn_showOrder.setEnabled(false);
    }

    /**
     * Update UI, clear selections and repopulate order listview. Also check if placeOrder should be disabled
     */
    private void updateUI() {

        lv_storeOrderListView.clearChoices();
        lv_storeOrderListView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, currentStoreOrder.getOrderBook()));
        this.btn_showOrder.setEnabled(false);
        btn_removeOrder.setEnabled(!this.currentStoreOrder.isOrderBookEmpty());
    }

    /**
     * Recompute all the prices and update text boxes
     */
    private void calculateAndDisplayPrice() {
        subtotal = currentOrder.getSubTotal();
        tv_subtotal.setText(DECIMAL_FORMAT.format(subtotal));
        salesTax = currentOrder.calcSalesTax();
        tv_salesTax.setText(DECIMAL_FORMAT.format(salesTax));
        total = subtotal + salesTax;
        tv_totalPrice.setText(DECIMAL_FORMAT.format(total));
    }
}
