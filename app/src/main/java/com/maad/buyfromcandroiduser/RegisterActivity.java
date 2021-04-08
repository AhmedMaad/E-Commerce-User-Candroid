package com.maad.buyfromcandroiduser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //make user name and password fields appear in card view with animation
    }

    public void startShopping(View view) {
        Intent i = new Intent(this, HomeActivity.class);
        startActivity(i);
    }
}