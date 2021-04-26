package com.maad.buyfromcandroiduser;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class ProductDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        ProductModel product = getIntent().getParcelableExtra("product");


        String imageTransitionName = getIntent().getStringExtra("imageTransition");
        ImageView productIV = findViewById(R.id.iv_product);
        productIV.setTransitionName(imageTransitionName);
        Glide.with(this).load(product.getImage()).into(productIV);

    }
}