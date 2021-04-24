package com.maad.buyfromcandroiduser;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ShoppingCartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        setTitle(R.string.my_orders);
    }
}