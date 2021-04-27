package com.maad.buyfromcandroiduser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ProductDetailsActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private ProductModel product;
    private TextView quantityTV;
    private SeekBar quantitySB;
    private long returnedQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        db = FirebaseFirestore.getInstance();
        product = getIntent().getParcelableExtra("product");

        String imageTransitionName = getIntent().getStringExtra("imageTransition");
        ImageView productIV = findViewById(R.id.iv_product);
        productIV.setTransitionName(imageTransitionName);
        Glide.with(this).load(product.getImage()).into(productIV);

        TextView titleTV = findViewById(R.id.tv_title);
        TextView descriptionTV = findViewById(R.id.tv_description);
        TextView priceTV = findViewById(R.id.tv_price);
        quantityTV = findViewById(R.id.tv_quantity);
        quantitySB = findViewById(R.id.sb_quantity);

        titleTV.setText(product.getTitle());
        descriptionTV.setText(product.getDescription());
        priceTV.setText("$ " + product.getPrice());


        quantitySB.setEnabled(false);
        db
                .collection("products")
                .document(product.getId())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        returnedQuantity = (long) documentSnapshot.get("quantity");
                        updateSeekBarSettings();
                    }
                });
    }

    private void updateSeekBarSettings() {
        quantitySB.setEnabled(true);
        quantitySB.setMax((int) returnedQuantity);
        quantitySB.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                quantityTV.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    public void addToBasket(View view) {

        if (quantitySB.getProgress() == 0)
            Toast.makeText(this, R.string.choose_quantity, Toast.LENGTH_SHORT).show();
        else {
            updateProductQuantityInFirebase();
        }

    }

    private void updateProductQuantityInFirebase() {

        if (quantitySB.getProgress() - returnedQuantity == 0)
            db
                    .collection("products")
                    .document(product.getId())
                    .delete()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            addOrderedProduct();
                        }
                    });
        else
            db
                    .collection("products")
                    .document(product.getId())
                    .update("quantity", returnedQuantity - quantitySB.getProgress())
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            addOrderedProduct();
                        }
                    });

    }

    private void addOrderedProduct() {
        /*Map<String, Object> order = new HashMap<>();
        order.put("title", product.getTitle());
        order.put("quantity", quantitySB.getProgress());
        order.put("image", product.getImage());
        order.put("userID", UserModel.id);*/

        OrderModel order = new OrderModel(UserModel.id, product.getTitle()
                , quantitySB.getProgress(), product.getImage());

        //add item in new collection with userID who bought the product
        db
                .collection("orders")
                .add(order)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        //intent to shopping cart activity and finish this activity
                        Intent i = new Intent(ProductDetailsActivity.this
                                , ShoppingCartActivity.class);
                        startActivity(i);
                        finish();
                    }
                });
    }

}