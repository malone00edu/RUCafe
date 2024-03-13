package com.example.rucafe.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.rucafe.MainActivity;
import com.example.rucafe.R;
import com.example.rucafe.model.Coffee;
import com.example.rucafe.model.CoffeeSize;
import com.example.rucafe.model.Order;

import java.text.DecimalFormat;

/**
 * The coffee activity class that creates the coffee ordering UI.
 * @author Taze Balbosa, Yulie Ying
 */
public class CoffeeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener,
        CompoundButton.OnCheckedChangeListener, View.OnClickListener{

    private static final int ZERO = 0;
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("##0.00");
    private static final int INITIAL_QTY = 1;
    private static final int INITIAL_ADD_IN_QTY = 0;

    private Coffee coffee = new Coffee(INITIAL_QTY, CoffeeSize.SHORT.getName(), INITIAL_ADD_IN_QTY,
            INITIAL_ADD_IN_QTY, INITIAL_ADD_IN_QTY, INITIAL_ADD_IN_QTY, INITIAL_ADD_IN_QTY);

    private int quantity = 0;
    private String coffeeSize = "";

    private String coffeePrice = "";
    private int coffeeQuantity = ZERO;

    private Spinner coffeeSizeSpinner, coffeeQtySpinner;

    private CheckBox creamCheckBox, milkCheckBox, whippedCheckBox, caramelCheckBox, syrupCheckBox;
    private TextView creamAmtNum, milkAmtNum, whippedAmtNum, caramelAmtNum, syrupAmtNum, coffeeSubTotal;
    private Button addCreamBtn, rmvCreamBtn, addMilkBtn, rmvMilkBtn, addWhippedBtn, rmvWhippedBtn,
            addCaramelBtn, rmvCaramelBtn, addSyrupBtn, rmvSyrupBtn, addToOrderBtn;

    /**
     * Creates and starts the coffee ordering UI.
     * @param savedInstanceState The reference to a Bundle object that is
     * passed into the onCreate method of every Android Activity
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coffee);

        coffeeSizeSpinner = findViewById(R.id.coffeeSizeSpinner);
        coffeeQtySpinner = findViewById(R.id.coffeeQtySpinner);

        creamCheckBox = findViewById(R.id.creamCheckBox);
        milkCheckBox = findViewById(R.id.milkCheckBox);
        whippedCheckBox = findViewById(R.id.whippedCheckBox);
        caramelCheckBox = findViewById(R.id.caramelCheckBox);
        syrupCheckBox = findViewById(R.id.syrupCheckBox);

        addCreamBtn = findViewById(R.id.addCreamBtn);
        addMilkBtn = findViewById(R.id.addMilkBtn);
        addWhippedBtn = findViewById(R.id.addWhippedBtn);
        addCaramelBtn = findViewById(R.id.addCaramelBtn);
        addSyrupBtn = findViewById(R.id.addSyrupBtn);

        rmvCreamBtn = findViewById(R.id.rmvCreamBtn);
        rmvMilkBtn = findViewById(R.id.rmvMilkBtn);
        rmvWhippedBtn = findViewById(R.id.rmvWhippedBtn);
        rmvCaramelBtn = findViewById(R.id.rmvCaramelBtn);
        rmvSyrupBtn = findViewById(R.id.rmvSyrupBtn);

        addToOrderBtn = findViewById(R.id.addCoffeeToOrderBtn);

        creamAmtNum = findViewById(R.id.creamAmtNum);
        milkAmtNum = findViewById(R.id.milkAmtNum);
        whippedAmtNum = findViewById(R.id.whippedAmtNum);
        caramelAmtNum = findViewById(R.id.caramelAmtNum);
        syrupAmtNum = findViewById(R.id.syrupAmtNum);

        coffeeSubTotal = findViewById(R.id.coffeeSubtotalNum);
        coffeeSubTotal.setText(R.string.default_price);

        ArrayAdapter<CharSequence> coffeeSizeAdapter = ArrayAdapter.createFromResource(this, R.array.coffeeSizeNames, android.R.layout.simple_spinner_item);
        coffeeSizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        coffeeSizeSpinner.setAdapter(coffeeSizeAdapter);
        coffeeSizeSpinner.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> coffeeQtyAdapter = ArrayAdapter.createFromResource(this, R.array.coffeeQtyNum, android.R.layout.simple_spinner_item);
        coffeeQtyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        coffeeQtySpinner.setAdapter(coffeeQtyAdapter);
        coffeeQtySpinner.setOnItemSelectedListener(this);

        creamCheckBox.setOnCheckedChangeListener(this);
        milkCheckBox.setOnCheckedChangeListener(this);
        whippedCheckBox.setOnCheckedChangeListener(this);
        caramelCheckBox.setOnCheckedChangeListener(this);
        syrupCheckBox.setOnCheckedChangeListener(this);

        addCreamBtn.setOnClickListener(this);
        addMilkBtn.setOnClickListener(this);
        addWhippedBtn.setOnClickListener(this);
        addCaramelBtn.setOnClickListener(this);
        addSyrupBtn.setOnClickListener(this);

        rmvCreamBtn.setOnClickListener(this);
        rmvMilkBtn.setOnClickListener(this);
        rmvWhippedBtn.setOnClickListener(this);
        rmvCaramelBtn.setOnClickListener(this);
        rmvSyrupBtn.setOnClickListener(this);

        addCreamBtn.setVisibility(View.GONE);
        addMilkBtn.setVisibility(View.GONE);
        addWhippedBtn.setVisibility(View.GONE);
        addCaramelBtn.setVisibility(View.GONE);
        addSyrupBtn.setVisibility(View.GONE);

        rmvCreamBtn.setVisibility(View.GONE);
        rmvMilkBtn.setVisibility(View.GONE);
        rmvWhippedBtn.setVisibility(View.GONE);
        rmvCaramelBtn.setVisibility(View.GONE);
        rmvSyrupBtn.setVisibility(View.GONE);

        creamAmtNum.setVisibility(View.GONE);
        milkAmtNum.setVisibility(View.GONE);
        whippedAmtNum.setVisibility(View.GONE);
        caramelAmtNum.setVisibility(View.GONE);
        syrupAmtNum.setVisibility(View.GONE);

        addToOrderBtn.setOnClickListener(this);
    }

    /**
     * Listener method for coffee size and quantity spinners selections.
     * @param adapterView The AdapterView where the selection happened.
     * @param view The view within the AdapterView that was clicked.
     * @param i The position of the view in the adapter.
     * @param l The row id of the item that is selected.
     */
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();
        int id = adapterView.getId();

        if (id == R.id.coffeeSizeSpinner) {
            coffee.setCoffeeSize(text);
        } else if (id == R.id.coffeeSizeSpinner) {
            coffee.setQuantity((Integer.parseInt(text)));
        }

        coffeeSubTotal.setText(calculateAndDisplayPrice());
    }

    /**
     * Listener method for coffee size and quantity spinners when nothing is selected.
     * @param adapterView The AdapterView that now contains no selected item.
     */
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    /**
     * Listener method for coffee UI check boxes.
     * @param buttonView The compound button view whose state has changed.
     * @param isChecked The new checked state of buttonView.
     */
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int id = buttonView.getId();

        if (id == R.id.creamCheckBox) {
            if (isChecked) {
                addCreamBtn.setVisibility(View.VISIBLE);
                rmvCreamBtn.setVisibility(View.VISIBLE);
                creamAmtNum.setVisibility(View.VISIBLE);
                creamAmtNum.setText(Integer.toString(coffee.getCream()));
            } else {
                addCreamBtn.setVisibility(View.GONE);
                rmvCreamBtn.setVisibility(View.GONE);
                creamAmtNum.setVisibility(View.GONE);
                coffee.setCream(ZERO);
                creamAmtNum.setText(Integer.toString(coffee.getCream()));
            }
        } else if (id == R.id.milkCheckBox) {
                if (isChecked) {
                    addMilkBtn.setVisibility(View.VISIBLE);
                    rmvMilkBtn.setVisibility(View.VISIBLE);
                    milkAmtNum.setVisibility(View.VISIBLE);
                    milkAmtNum.setText(Integer.toString(coffee.getMilk()));
                } else {
                    addMilkBtn.setVisibility(View.GONE);
                    rmvMilkBtn.setVisibility(View.GONE);
                    milkAmtNum.setVisibility(View.GONE);
                    coffee.setMilk(ZERO);
                    milkAmtNum.setText(Integer.toString(coffee.getMilk()));
                }
        } else if (id == R.id.whippedCheckBox) {
            if (isChecked) {
                addWhippedBtn.setVisibility(View.VISIBLE);
                rmvWhippedBtn.setVisibility(View.VISIBLE);
                whippedAmtNum.setVisibility(View.VISIBLE);
                whippedAmtNum.setText(Integer.toString(coffee.getWhippedCream()));
            } else {
                addWhippedBtn.setVisibility(View.GONE);
                rmvWhippedBtn.setVisibility(View.GONE);
                whippedAmtNum.setVisibility(View.GONE);
                coffee.setWhippedCream(ZERO);
                whippedAmtNum.setText(Integer.toString(coffee.getWhippedCream()));
            }
        } else if (id == R.id.caramelCheckBox) {
            if (isChecked) {
                addCaramelBtn.setVisibility(View.VISIBLE);
                rmvCaramelBtn.setVisibility(View.VISIBLE);
                caramelAmtNum.setVisibility(View.VISIBLE);
                caramelAmtNum.setText(Integer.toString(coffee.getCaramel()));
            } else {
                addCaramelBtn.setVisibility(View.GONE);
                rmvCaramelBtn.setVisibility(View.GONE);
                caramelAmtNum.setVisibility(View.GONE);
                coffee.setCaramel(ZERO);
                caramelAmtNum.setText(Integer.toString(coffee.getCaramel()));
            }
        } else if (id == R.id.syrupCheckBox) {
            if (isChecked) {
                addSyrupBtn.setVisibility(View.VISIBLE);
                rmvSyrupBtn.setVisibility(View.VISIBLE);
                syrupAmtNum.setVisibility(View.VISIBLE);
                syrupAmtNum.setText(Integer.toString(coffee.getSyrup()));
            } else {
                addSyrupBtn.setVisibility(View.GONE);
                rmvSyrupBtn.setVisibility(View.GONE);
                syrupAmtNum.setVisibility(View.GONE);
                coffee.setSyrup(ZERO);
                syrupAmtNum.setText(Integer.toString(coffee.getSyrup()));
            }
        }

    }

    /**
     * Listener method for coffee UI buttons.
     * @param view The view that was clicked.
     */
    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.addCreamBtn) {
            coffee.add("Cream");
            creamAmtNum.setText(Integer.toString(coffee.getCream()));
            coffeeSubTotal.setText(calculateAndDisplayPrice());
        } else if (id == R.id.addMilkBtn) {
            coffee.add("Milk");
            milkAmtNum.setText(Integer.toString(coffee.getMilk()));
            coffeeSubTotal.setText(calculateAndDisplayPrice());
        }else if (id == R.id.addWhippedBtn) {
            coffee.add("Whipped Cream");
            whippedAmtNum.setText(Integer.toString(coffee.getWhippedCream()));
            coffeeSubTotal.setText(calculateAndDisplayPrice());
        } else if (id == R.id.addCaramelBtn) {
            coffee.add("Caramel");
            caramelAmtNum.setText(Integer.toString(coffee.getCaramel()));
            coffeeSubTotal.setText(calculateAndDisplayPrice());
        } else if (id == R.id.addSyrupBtn) {
            coffee.add("Syrup");
            syrupAmtNum.setText(Integer.toString(coffee.getSyrup()));
            coffeeSubTotal.setText(calculateAndDisplayPrice());
        } else if (id == R.id.rmvCreamBtn) {
            coffee.remove("Cream");
            creamAmtNum.setText(Integer.toString(coffee.getCream()));
            coffeeSubTotal.setText(calculateAndDisplayPrice());
        } else if (id == R.id.rmvMilkBtn) {
            coffee.remove("Milk");
            milkAmtNum.setText(Integer.toString(coffee.getMilk()));
            coffeeSubTotal.setText(calculateAndDisplayPrice());
        } else if (id ==R.id.rmvWhippedBtn) {
            coffee.remove("Whipped Cream");
            whippedAmtNum.setText(Integer.toString(coffee.getWhippedCream()));
            coffeeSubTotal.setText(calculateAndDisplayPrice());
        } else if (id == R.id.rmvCaramelBtn) {
            coffee.remove("Caramel");
            caramelAmtNum.setText(Integer.toString(coffee.getCaramel()));
            coffeeSubTotal.setText(calculateAndDisplayPrice());
        } else if (id == R.id.rmvSyrupBtn) {
                coffee.remove("Syrup");
                syrupAmtNum.setText(Integer.toString(coffee.getSyrup()));
                coffeeSubTotal.setText(calculateAndDisplayPrice());
        } else if (id == R.id.addCoffeeToOrderBtn) {
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                /**
                 * Checks to see if user click on button to coffee to current order.
                 *
                 * @param dialog The selected dialog to be displayed.
                 * @param which  The choice of dialog chosen.
                 */
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case DialogInterface.BUTTON_POSITIVE:
                            if (Double.parseDouble(calculateAndDisplayPrice()) > ZERO && coffee.getQuantity() > ZERO) {
                                Order.getCurrentOrder().add(coffee);

                                returnToMainActivity();
                                Toast.makeText(CoffeeActivity.this,
                                        "Coffee(s) added to your order.", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(CoffeeActivity.this,
                                        " No coffee(s) to add to your order.", Toast.LENGTH_LONG).show();
                            }
                            break;
                        case DialogInterface.BUTTON_NEGATIVE:
                            Toast.makeText(CoffeeActivity.this,
                                    " Coffee(s) was not added to your order.", Toast.LENGTH_LONG).show();
                            break;
                    }
                }
            };

            AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
            alert.setMessage("Add to order?").setPositiveButton("yes", dialogClickListener)
                    .setNegativeButton("no", dialogClickListener).show();
        }
    }

    /**
     * Calculates and displays coffee item price.
     * @return String of coffee item and price.
     */
    private String calculateAndDisplayPrice() {
        coffee.itemPrice();

        coffeePrice = DECIMAL_FORMAT.format(coffee.getPrice());
        return coffeePrice;
    }

    /**
     * Returns to the main ordering menu.
     */
    private void returnToMainActivity() {
        Intent goToMainActivity = new Intent(this, MainActivity.class);
        startActivity(goToMainActivity);
    }
}
