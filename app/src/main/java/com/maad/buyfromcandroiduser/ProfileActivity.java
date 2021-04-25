package com.maad.buyfromcandroiduser;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.Random;

public class ProfileActivity extends AppCompatActivity {

    private int[] backgrounds = {R.drawable.bg1
            , R.drawable.bg2
            , R.drawable.bg3
            , R.drawable.bg4};
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setTitle(R.string.my_profile);

        Random random = new Random();
        int chosenBG = random.nextInt(4);
        ImageView backgroundIV = findViewById(R.id.iv_bg);
        backgroundIV.setImageResource(backgrounds[chosenBG]);

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
            ImageView image = findViewById(R.id.iv_profile);
            image.setImageURI(imageUri);
        }
    }

    public void updateProfile(View view) {
        
    }
}