package com.example.simplechatapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Parcelable;
import android.widget.ImageView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.AllPermission;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int Read_Storage = 231;
    private contact contact2;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView imageView = findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.welcomescreen2);
        try {
            if (checkSelfPermission(Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED )
            {
                requestPermissions(new String[]{ Manifest.permission.INTERNET}, Read_Storage);
            }
            else{
                contact2 = ReadContactFromFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        if(contact2==null)
        {
            final Intent intent = new Intent(this, createAccountScreen.class);
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(intent);
                }
            }, 2000);
        }
        else if(contact2!=null){
            final Intent intent = new Intent(this, Chat.class);
            intent.putExtra("contact", (Parcelable) contact2);
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(intent);
                }
            }, 2000); }


    }
    public contact ReadContactFromFile() throws IOException, ClassNotFoundException {
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + getApplicationContext().getPackageName()
                + "/Files");
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                return null;
            }
        }
        File ListFile;
        String ListFileName="user.txt";
        String pictureFilePath;
        pictureFilePath = mediaStorageDir.getPath() + File.separator + ListFileName;
        ListFile = new File(mediaStorageDir.getPath() + File.separator + ListFileName);
        if(ListFile.exists()) {
            contact contactCurrent = null;
            BufferedReader reader;
            try {
                reader = new BufferedReader(new FileReader(ListFile));
                String line = reader.readLine();
                if (line != null) {
                    String string = line;
                    String[] parts = string.split(";");
                    String ID = parts[0]; // Phone Number
                    String lastText = parts[1]; // Name
                    String imgPath = parts[2]; // Picture
                    contactCurrent = new contact(ID,lastText,imgPath);
                }
                reader.close();
                return contactCurrent;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
