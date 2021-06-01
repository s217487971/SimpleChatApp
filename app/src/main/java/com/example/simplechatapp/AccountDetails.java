package com.example.simplechatapp;

//import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class AccountDetails extends AppCompatActivity {

    String cellURI;
    EditText editText;
    private String pictureFilePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_details);
        editText = findViewById(R.id.editText5);
        FloatingActionButton donebutton = findViewById(R.id.floatingActionButton3);
        Bundle bundle = getIntent().getExtras();
        FloatingActionButton captureButton = findViewById(R.id.floatingActionButton2);
        if(bundle!=null)
        {
            cellURI = bundle.getString("CellURI");
        }
        donebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editText.getText().toString();
                contact user = new contact(cellURI,name,"");
                try {
                    SaveContactToFile(user);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(getApplicationContext(),Chat.class);
                intent.putExtra("user",(Parcelable) user);
                startActivity(intent);
            }
        });
    }
    public void SaveContactToFile(contact user) throws IOException {
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + getApplicationContext().getPackageName()
                + "/Files");

        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                return;
            }
        }
        // Create a media file name
        //String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmm").format(new Date());
        File ListFile;
        String ListFileName="user.txt";
        pictureFilePath = mediaStorageDir.getPath() + File.separator + ListFileName;
        ListFile = new File(mediaStorageDir.getPath() + File.separator + ListFileName);

        //Save the list file
        FileOutputStream fos = new FileOutputStream(ListFile);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
            bw.write(user.ID+";"+user.lastText+";"+user.imgPath);
            bw.newLine();
        bw.close();
    }
}
