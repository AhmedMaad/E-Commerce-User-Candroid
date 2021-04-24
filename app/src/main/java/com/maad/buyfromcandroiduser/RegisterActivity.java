package com.maad.buyfromcandroiduser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        auth = FirebaseAuth.getInstance();
    }

    public void startShopping(View view) {
        EditText emailET = findViewById(R.id.et_email);
        EditText passwordET = findViewById(R.id.et_password);
        String email = emailET.getText().toString();
        String password = passwordET.getText().toString();
        signUp(email, password);
    }

    private void signUp(String email, String password) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //create new user in firestore then navigate to home
                            String id = task.getResult().getUser().getUid();
                            String email = task.getResult().getUser().getEmail();
                            createUser(id, email);
                        } else
                            login(email, password);
                    }
                });
    }

    private void login(String email, String password) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                            openHomeActivity();
                    }
                });
    }

    private void createUser(String id, String email) {
        UserModel userModel = new UserModel(email, id);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db
                .collection("users")
                .add(userModel)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        openHomeActivity();
                    }
                });
    }

    private void openHomeActivity() {
        Intent i = new Intent(this, HomeActivity.class);
        startActivity(i);
        finish();
    }

}