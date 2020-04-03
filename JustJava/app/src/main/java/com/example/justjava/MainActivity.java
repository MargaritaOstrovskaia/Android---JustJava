package com.example.justjava;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    private TextView quantityTextView;
    private TextView priceTextView;
    private int quantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        quantityTextView = findViewById(R.id.quantity_text_view);
        priceTextView = findViewById(R.id.price_text_view);
        quantity = 1;
        changeTextViews();
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        changeTextViews();
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        quantityTextView.setText(String.valueOf(number));
    }

    /**
     * This method displays the given price on the screen.
     */
    private void displayPrice(int number) {
        // NumberFormat.getCurrencyInstance().format(number)
        priceTextView.setText(getString(R.string.price_val, quantity));
    }

    private void changeTextViews()
    {
        display(quantity);
        displayPrice(quantity * 5);
    }

    public void increment(View view) {
        quantity++;
        changeTextViews();
    }

    public void decrement(View view) {
        if (quantity > 0) {
            quantity--;
            changeTextViews();
        }
    }
}