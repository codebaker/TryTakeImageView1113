package com.example.edu.trytakeimageview1113;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    final int IMAGE_CAPTURE = 2000;
    final int IMAGE_GALLERY = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((Button)findViewById(R.id.buttonGallery)).setOnClickListener(this);
        ((Button)findViewById(R.id.buttonTake)).setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = null;
        if (data !=null&&resultCode == RESULT_OK){
            switch (requestCode){
                case IMAGE_CAPTURE:
                    Bundle bundle = data.getExtras();
                    bitmap = (Bitmap) bundle.get("data");
                    ((ImageView)findViewById(R.id.imageView)).setImageBitmap(bitmap);
                    break;
                case IMAGE_GALLERY:
                    Uri selectedImage = data.getData();
                    try {
                        InputStream inputStream = this.getContentResolver().openInputStream(selectedImage);
                        bitmap = BitmapFactory.decodeStream(inputStream);
                        ((ImageView)findViewById(R.id.imageView)).setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    break;
                default:

            }
         }
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.buttonGallery:
                intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,IMAGE_GALLERY);
                break;
            case R.id.buttonTake:
                if(getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)){
                    intent = new Intent();
                    intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent,IMAGE_CAPTURE);
                }
                break;
        }
    }
}

