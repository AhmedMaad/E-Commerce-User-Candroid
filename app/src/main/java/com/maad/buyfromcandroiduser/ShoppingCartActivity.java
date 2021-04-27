package com.maad.buyfromcandroiduser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartActivity extends AppCompatActivity {

    private ArrayList<OrderModel> orders = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        setTitle(R.string.my_orders);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db
                .collection("orders")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> documentSnapshots =
                                queryDocumentSnapshots.getDocuments();
                        for (int i = 0; i < documentSnapshots.size(); ++i) {
                            if (UserModel.id.equals(documentSnapshots.get(i).getString("userID"))) {
                                OrderModel order = documentSnapshots.get(i).toObject(OrderModel.class);
                                orders.add(order);
                            }
                        }
                        showOrders();
                    }
                });
    }

    private void showOrders() {
        OrderAdapter adapter = new OrderAdapter(this, orders);
        RecyclerView recyclerView = findViewById(R.id.rv);
        recyclerView.setAdapter(adapter);
    }

}