package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This increment method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if (quantity == 100) {

            Toast.makeText(this, R.string.string_100_coffes, Toast.LENGTH_SHORT).show();

            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    /**
     * This decrement method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if (quantity == 1) {

            Toast.makeText(this, R.string.string_1_coffee, Toast.LENGTH_SHORT).show();

            return;
        }

        quantity = quantity - 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        CheckBox checkBoxOne = (CheckBox) findViewById(R.id.checkbox1);
        boolean stateOne = checkBoxOne.isChecked();

        CheckBox checkBoxTwo = (CheckBox) findViewById(R.id.checkbox2);
        boolean stateTwo = checkBoxTwo.isChecked();

        EditText editText = (EditText) findViewById(R.id.edittext_name);
        String name = editText.getText().toString();

        int price = calculatePrice(stateOne, stateTwo);

        String message = createOrderSummary(price, stateOne, stateTwo, name);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.order_summary_email_subject, name));
        intent.putExtra(Intent.EXTRA_TEXT, message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * Calculates the price of the order.
     *
     * @param stateOne is whether or not the user wants whipped cream topping
     * @param stateTwo is whether or not the user wants chocolate topping
     * @return total price
     */
    private int calculatePrice(boolean stateOne, boolean stateTwo) {

        int price = 5;

        if (stateOne) {
            price += 1;
        }
        if (stateTwo) {
            price += 2;
        }

        return quantity * price;
    }

    /**
     * Create summary of the order
     *
     * @param price    of the order
     * @param stateOne is whether or not the user wants whipped cream topping
     * @param stateTwo is whether or not the user wants chocolate topping
     * @param name     of the customer
     * @return text summary
     */
    private String createOrderSummary(int price, boolean stateOne, boolean stateTwo, String name) {
        return getString(R.string.order_summary_name, name) + "\n" +
                getString(R.string.order_summary_whipped_cream, stateOne) + "\n" +
                getString(R.string.order_summary_chocolate, stateTwo) + "\n" +
                getString(R.string.order_summary_quantity, quantity) + "\n" +
                getString(R.string.order_summary_price, price) + "\n" +
                getString(R.string.thank_you);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int numberOfCoffees) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText(String.valueOf(numberOfCoffees));
    }

}