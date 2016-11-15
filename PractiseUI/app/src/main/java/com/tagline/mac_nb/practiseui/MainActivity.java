package com.tagline.mac_nb.practiseui;

/**
 * Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 *
 * package com.example.android.justjava;
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import java.text.NumberFormat;

import static android.R.attr.button;
import static android.R.attr.rating;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    public int numberOfCoffee;


    MainActivity(){
        numberOfCoffee = 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RatingBar rb_customColor = (RatingBar) findViewById(R.id.ratingBar);
        LayerDrawable stars = (LayerDrawable) rb_customColor
                .getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.parseColor("#FEC400"),
                PorterDuff.Mode.SRC_ATOP); // for filled stars
        stars.getDrawable(1).setColorFilter(Color.parseColor("#FEC400"),
                PorterDuff.Mode.SRC_ATOP); // for half filled stars
        stars.getDrawable(0).setColorFilter(Color.parseColor("#D7D7D9"),
                PorterDuff.Mode.SRC_ATOP); // for empty stars

        addListenerOnButton();

    }


    /**
     * This Method adds coffee number to the   qauntity
     */
    public void addCoffee(View view){
        numberOfCoffee += 1;
        displayQuantity(numberOfCoffee);
        displayPrice(numberOfCoffee*5);
    }

    /**
     * This Method adds coffee number to the   qauntity
     */
    public void subtractCoffee(View view){
        if(numberOfCoffee == 0){
            displayQuantity(numberOfCoffee);
            displayPrice(0);
        }
        else{
            numberOfCoffee -= 1;
            displayQuantity(numberOfCoffee);
            displayPrice(numberOfCoffee*5);
        }
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        displayQuantity(numberOfCoffee);
        displayPrice(numberOfCoffee*5);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given price on the screen.
     */
    private void displayPrice(int number) {
        String totalVal = "Total: " + NumberFormat.getCurrencyInstance().format(number) + "\n" + "Thank You";
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText(totalVal);
    }


    public Button button;

    public void addListenerOnButton() {

        final Context context = this;

        button = (Button) findViewById(R.id.grid_layout);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, GridActivity.class);
                startActivity(intent);

            }

        });

    }
}