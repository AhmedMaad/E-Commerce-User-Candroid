package com.maad.buyfromcandroiduser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void showFurnitureProducts(View view) {
        openActivity("Furniture");
    }

    public void showElectronicsProducts(View view) {
        openActivity("Electronics");
    }

    public void showFashionProducts(View view) {
        openActivity("Clothes");
    }

    private void openActivity(String cat){
        Intent i = new Intent(this, ProductActivity.class);
        i.putExtra("cat", cat);
        startActivity(i);
    }

}