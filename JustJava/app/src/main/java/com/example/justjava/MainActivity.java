package com.example.justjava;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    private boolean addWhippedCream = false;
    private boolean addChocolate = false;
    private int quantity = 1;
    private int totalPrice = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CheckBox whippedCreamCheckBox = findViewById(R.id.whipped_cream_checkbox);
        whippedCreamCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                addWhippedCream = isChecked;
                setTotalPrice();
            }
        });

        CheckBox chocolateCheckBox = findViewById(R.id.chocolate_checkbox);
        chocolateCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                addChocolate = isChecked;
                setTotalPrice();
            }
        });

        setTotalPrice();
    }

    /**
     * This method set total price in TextView.
     */
    private void setTotalPrice() {
        totalPrice = calculatePrice();
        String priceText = getString(R.string.total_price, totalPrice);

        TextView totalPriceTextView = findViewById(R.id.total_price_text_view);
        totalPriceTextView.setText(priceText);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        // Find the user's name
        EditText nameTextEdit = findViewById(R.id.name_text_edit);
        String name = nameTextEdit.getText().toString();

        // Get order summary message
        String priceMessage = createOrderSummary(name);

        // Use an intent to launch an email app.
        // Send the order summary in the email body.
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Coffee order");
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);

        if (intent.resolveActivity(getPackageManager()) != null)
            startActivity(intent);
    }

    /**
     * Calculates the price of the order.
     * @return total price.
     */
    private int calculatePrice() {
        int basePrice = 5;

        if (addWhippedCream)
            basePrice += 1;
        if (addChocolate)
            basePrice += 2;

        return quantity * basePrice;
    }

    /**
     * Create summary of the order.
     * @param name on the order.
     * @return text summary.
     */
    private String createOrderSummary(String name) {
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

        return getString(R.string.price_val, name, whippedCream, chocolate, quantity, totalPrice);
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if (quantity < 100)
            quantity++;
        setQuantity();
        setTotalPrice();
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if (quantity > 0)
            quantity--;
        setQuantity();
        setTotalPrice();
    }

    /**
     * Change quantity value in TextView.
     */
    private void setQuantity() {
        TextView textViewQuantity = findViewById(R.id.quantity_text_view);
        textViewQuantity.setText(String.valueOf(quantity));
    }
}