package com.example.justjava;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    private int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        // Find the user's name
        EditText nameTextEdit = findViewById(R.id.name_text_edit);
        String name = nameTextEdit.getText().toString();

        // Figure out if whe user wants whipped cream topping
        CheckBox whippedCreamCheckBox = findViewById(R.id.whipped_cream_checkbox);
        boolean addWhippedCream = whippedCreamCheckBox.isChecked();

        // Figure out if whe user wants chocolate topping
        CheckBox chocolateCheckBox = findViewById(R.id.chocolate_checkbox);
        boolean addChocolate = chocolateCheckBox.isChecked();

        // Calculate the price
        int price = calculatePrice(5, addWhippedCream, addChocolate);

        // Get order summary message
        String priceMessage = createOrderSummary(name, price, addWhippedCream, addChocolate);

        // Use an intent to launch an email app.
        // Send the order summary in the email body.
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Coffee order");
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);

        if (intent.resolveActivity(getPackageManager()) != null)
            startActivity(intent);
    }

    /**
     * Calculates the price of the order.
     * @param basePrice price of 1 cup coffee.
     * @param addWhippedCream is whether or not the user wants whipped cream topping.
     * @param addChocolate is whether or not the user wants chocolate topping.
     * @return total price.
     */
    private int calculatePrice(int basePrice, boolean addWhippedCream, boolean addChocolate) {
        if (addWhippedCream)
            basePrice += 1;
        if (addChocolate)
            basePrice += 2;

        return quantity * basePrice;
    }

    /**
     * Create summary of the order.
     * @param name on the order.
     * @param price on the order.
     * @param addWhippedCream is whether or not the user wants whipped cream topping.
     * @param addChocolate is whether or not the user wants chocolate topping.
     * @return text summary.
     */
    private String createOrderSummary(String name, int price, boolean addWhippedCream, boolean addChocolate) {
        String whippedCream;
        if (addWhippedCream)
            whippedCream = getString(R.string.answer_yes);
        else
            whippedCream = getString(R.string.answer_no);

        String chocolate;
        if (addChocolate)
            chocolate = getResources().getString(R.string.answer_yes);
        else
            chocolate = getString(R.string.answer_no);

        return getString(R.string.price_val, name, whippedCream, chocolate, quantity, price);
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if (quantity < 100)
            quantity++;
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if (quantity > 0)
            quantity--;
    }
}