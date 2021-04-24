package com.maad.buyfromcandroiduser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class ProductActivity extends AppCompatActivity {

    private ArrayList<ProductModel> products = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        loadProducts(getIntent().getStringExtra("cat"));
        //show any icon fading while loading from firestore
        //make picture transition when clicking the product and navigating to product details
    }

    //TODO: Load product by category
    private void loadProducts(String cat) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collection = db.collection("products");
        collection.addSnapshotListener((value, error) -> {
            if (value != null) {
                List<DocumentSnapshot> documentSnapshots = value.getDocuments();
                for (int i = 0; i < documentSnapshots.size(); ++i) {
                    ProductModel productModel = documentSnapshots.get(i).toObject(ProductModel.class);
                    if (productModel.getCategory().equals(cat))
                        products.add(productModel);
                }
                showProducts();
            }
        });
    }

    private void showProducts() {
        ProductAdapter adapter = new ProductAdapter(this, products);
        RecyclerView recyclerView = findViewById(R.id.rv);
        GridLayoutManager manager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_profile:
                startActivity(new Intent(this, ProfileActivity.class));
                return true;

            case R.id.item_orders:
                startActivity(new Intent(this, ShoppingCartActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}