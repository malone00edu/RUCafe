package com.example.rucafe.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.rucafe.MainActivity;
import com.example.rucafe.R;
import com.example.rucafe.models.MenuItem;
import com.example.rucafe.models.Order;
import java.text.DecimalFormat;

/**
 * The Activity class that creates the ordering GUI display.
 * @author Taze Balbosa, Yulie Ying
 */
public class OrderActivity extends AppCompatActivity {
    private static DecimalFormat DECIMAL_FORMAT = new DecimalFormat("$###,##0.00");

    private TextView tv_totalPrice, tv_salesTax, tv_subtotal;
    private ListView lv_orderListView;
    private Button btn_removeItem, btn_placeOrder;
    private Order currentOrder;
    private MenuItem currentItem;
    double total, subtotal, salesTax;

    /**
     * Creates and starts the order menu UI.
     * @param savedInstanceState The reference to a Bundle object that is
     * passed into the onCreate method of every Android Activity
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        currentItem = null;
        currentOrder = Order.getCurrentOrder();

        tv_totalPrice = (TextView) findViewById(R.id.tv_totalOrder);
        tv_subtotal = (TextView) findViewById(R.id.tv_subtotalOrder);
        tv_salesTax = (TextView) findViewById(R.id.tv_salesTaxOrder);
        lv_orderListView = (ListView) findViewById(R.id.lv_listOrder);
        btn_placeOrder = (Button) findViewById(R.id.btn_showOrder);
        btn_removeItem = (Button) findViewById(R.id.btn_removeStoreOrder);
        lv_orderListView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        lv_orderListView.setSelector(R.color.design_default_color_primary);
    }

    /**
     * Starts the ListViews and contains methods for button actions.
     */
    @Override
    protected void onStart() {
        super.onStart();

        lv_orderListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /**
             * Processes action when clicking a specific item in order.
             * @param parent The AdapterView where the selection happened.
             * @param view The view within the AdapterView that was clicked.
             * @param position The position of the view in the adapter.
             * @param id The row id of the item that is selected.
             */
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentItem = (MenuItem) lv_orderListView.getItemAtPosition(position);
                btn_removeItem.setEnabled(true);
            }
        });

        btn_removeItem.setOnClickListener(v -> {
            this.removeItemFromOrder();
            Toast.makeText(OrderActivity.this, "Item removed.", Toast.LENGTH_LONG).show();
        });

        btn_placeOrder.setOnClickListener(v -> {
            this.placeOrder();
            Toast.makeText(OrderActivity.this, "Order placed.", Toast.LENGTH_LONG).show();
        });

        this.updateUI();
        this.calculateAndDisplayPrice();
    }

    /**
     * Remove selected item from order
     */
    private void removeItemFromOrder() {
        currentOrder.remove(this.currentItem);
        // Update UI and calculate price
        this.updateUI();
        this.calculateAndDisplayPrice();
    }

    /**
     * Update UI, clear selections and repopulate order listview. Also check if placeOrder should be disabled
     */
    private void updateUI() {

        lv_orderListView.clearChoices();
        lv_orderListView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, currentOrder.getOrderList()));
        this.btn_removeItem.setEnabled(false);
        btn_placeOrder.setEnabled(!this.currentOrder.isMenuEmpty());
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

    /**
     * Place the order, updating StoreOrders.
     */
    private void placeOrder() {
        currentOrder.submitOrder();
        Toast.makeText(getBaseContext(), R.string.order_successfully_placed, Toast.LENGTH_SHORT).show();
        Intent gotoMainActivity = new Intent(this, MainActivity.class);
        startActivity(gotoMainActivity);
    }
}
