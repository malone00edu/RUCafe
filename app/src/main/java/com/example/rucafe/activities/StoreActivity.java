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
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import com.example.rucafe.R;
import com.example.rucafe.models.MenuItems;
import com.example.rucafe.models.Order;
import com.example.rucafe.models.StoreOrders;
import java.text.DecimalFormat;

/**
 * The Activity class that creates the store orders GUI display.
 * @author Taze Balbosa, Yulie Ying
 */
public class StoreActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    private static DecimalFormat DECIMAL_FORMAT = new DecimalFormat("$###,##0.00");
    private static final int NOT_CHECKED = 0;
    private static final int CHECKED = 1;

    private TextView tv_totalPrice, tv_salesTax, tv_subtotal;
    private ListView lv_storeOrderListView;
    private Button btn_removeOrder, btn_showOrder;
    private MenuItems currentItem;
    private Order currentOrder;
    private StoreOrders currentStoreOrder;
    double total, subtotal, salesTax;

    private int checkedOrder = NOT_CHECKED;

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
        lv_storeOrderListView.setSelector(com.google.android.material.R.color.design_default_color_primary);

    }


    /**
     * Starts the ListViews and contains methods for button actions.
     */
    @Override
    protected void onStart() {
        super.onStart();

        lv_storeOrderListView.setOnItemClickListener(this);

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
     * Processes action when clicking a specific order in store orders list.
     * @param adapterView The AdapterView where the selection happened.
     * @param view The view within the AdapterView that was clicked.
     * @param i The position of the view in the adapter.
     * @param l The row id of the item that is selected.
     */
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (checkedOrder == NOT_CHECKED) {
            currentOrder = (Order) lv_storeOrderListView.getItemAtPosition(i);
            btn_showOrder.setEnabled(true);
            btn_removeOrder.setEnabled(true);
        }
        else if (checkedOrder == CHECKED) {
            currentItem = (MenuItems) lv_storeOrderListView.getItemAtPosition(i);
        }
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
     * Shows a detailed list of an order in the store orders.
     */
    private void showOrder() {
        lv_storeOrderListView.clearChoices();
        lv_storeOrderListView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, currentOrder.getOrderList()));
        this.btn_showOrder.setEnabled(false);
        checkedOrder = CHECKED;
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

    /**
     * Overrides the behavior of the bottom back button for StoreOrder GUI
     */
    @Override
    public void onBackPressed() {
        if (checkedOrder == CHECKED) {
            this.updateUI();
            checkedOrder = NOT_CHECKED;
        }
        else {
            super.onBackPressed();
        }
    }

    /**
     * Overrides teh behavior of the top back button for StoreOrder GUI
     * @param item The item that is selected.
     * @return True if home id is found in the AndroidManifest.xml
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}