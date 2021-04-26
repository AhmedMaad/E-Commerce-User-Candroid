package com.maad.buyfromcandroiduser;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Calendar;
import java.util.Random;

public class ProfileActivity extends AppCompatActivity {

    private int[] backgrounds = {R.drawable.bg1
            , R.drawable.bg2
            , R.drawable.bg3
            , R.drawable.bg4};
    private UserModel user;
    private Uri imageUri;
    private FirebaseFirestore db;
    private EditText nameET;
    private EditText mobileET;
    private EditText addressET;
    private EditText emailET;
    private ImageView profileIV;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setTitle(R.string.my_profile);

        Random random = new Random();
        int chosenBG = random.nextInt(4);
        ImageView backgroundIV = findViewById(R.id.iv_bg);
        backgroundIV.setImageResource(backgrounds[chosenBG]);

        nameET = findViewById(R.id.et_name);
        mobileET = findViewById(R.id.et_mobile);
        addressET = findViewById(R.id.et_address);
        profileIV = findViewById(R.id.iv_profile);
        emailET = findViewById(R.id.et_email);

        db = FirebaseFirestore.getInstance();
        db
                .collection("users")
                .document(UserModel.id)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        user = documentSnapshot.toObject(UserModel.class);
                        if (user.getName() != null)
                            nameET.setText(user.getName());
                        if (user.getAddress() != null)
                            addressET.setText(user.getAddress());
                        if (user.getPhoneNumber() != null)
                            mobileET.setText(user.getPhoneNumber());
                        if (user.getProfilePicture() != null)
                            Glide.with(ProfileActivity.this)
                                    .load(user.getProfilePicture()).into(profileIV);
                        email = user.getEmail();
                        emailET.setText(getString(R.string.email) + email);
                    }
                });


    }

    public void choosePicture(View view) {
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.setType("image/*");
        startActivityForResult(i, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            imageUri = data.getData();
            profileIV.setImageURI(imageUri);
        }
    }

    public void updateProfile(View view) {
        if (imageUri != null)
            uploadImage();
        else
            uploadProfile(imageUri); //Image Uri in this case is null
    }

    private void uploadImage() {
        //Accessing Cloud Storage bucket by creating an instance of FirebaseStorage
        FirebaseStorage storage = FirebaseStorage.getInstance();
        //Create a reference to upload, download, or delete a file

        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH) + 1; // Note: zero based!
        int day = now.get(Calendar.DAY_OF_MONTH);
        int hour = now.get(Calendar.HOUR_OF_DAY);
        int minute = now.get(Calendar.MINUTE);
        int second = now.get(Calendar.SECOND);
        int millis = now.get(Calendar.MILLISECOND);
        String imageName = "image: " + day + '-' + month + '-' + year + ' ' + hour + ':' + minute
                + ':' + second + '.' + millis;

        StorageReference storageRef = storage.getReference().child(imageName);
        storageRef.putFile(imageUri)
                .addOnSuccessListener(taskSnapshot -> {
                    getLinkForUploadedImage(storageRef.getDownloadUrl());
                });
    }

    //Getting a download link after uploading a picture
    private void getLinkForUploadedImage(Task<Uri> task) {
        task.addOnSuccessListener(uri -> {
            uploadProfile(uri);
        });
    }

    private void uploadProfile(Uri imageUri) {
        String writtenName = nameET.getText().toString();
        String writtenMobile = mobileET.getText().toString();
        String writtenAddress = addressET.getText().toString();

        String imageLink;
        if (imageUri != null)
            imageLink = imageUri.toString();
        else
            imageLink = user.getProfilePicture();

        user = new UserModel(writtenName, email, writtenAddress
                , writtenMobile, imageLink, UserModel.id);

        db
                .collection("users")
                .document(UserModel.id)
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(ProfileActivity.this, R.string.profile_updated
                                , Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }

}