package com.example.simplechatapp;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;

import com.example.simplechatapp.ui.main.SectionsPagerAdapter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Chat extends AppCompatActivity {

    ArrayList<contact> list;
    contact user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Bundle bundle = getIntent().getExtras();
        try {
            list = ReadListFromFile();
            user = ReadUserFromFile();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if(bundle!=null && list==null)
        {
            list = bundle.getParcelableArrayList("list");
            user = bundle.getParcelable("user");
        }
        else if(bundle==null && list==null)
        {
            list = new ArrayList<>();
            contact contact1 = new contact("+27783607891","Bye...","drawable://" + R.drawable.maleavatar);
            contact contact2 = new contact("+27744807891","See ya...","drawable://"+R.drawable.female);
            contact contact3 = new contact("+27867207891","Cheers...","drawable://" + R.drawable.maleavatar);
            contact contact4 = new contact("+27617757891","Later...","drawable://" + R.drawable.female);
            contact contact5 = new contact("+27844787891","Awe...","drawable://" + R.drawable.female);
            contact contact6 = new contact("+27321444545","Hey there","drawable://" +R.drawable.maleavatar);
            contact contact7 = new contact("Michelle", "See ya budd ","drawable://" +R.drawable.female);
            contact contact8 = new contact("Kate","HEY you","drawable://" + R.drawable.female);
            contact contact9 = new contact("Dad", "Cool...","drawable://" +R.drawable.maleavatar);
            contact contact10 = new contact("Granny","Sonnyboy...","drawable://" + R.drawable.female);
            list.add(contact1);
            list.add(contact2);
            list.add(contact3);
            list.add(contact4);
            list.add(contact5);
            list.add(contact6);
            list.add(contact7);
            list.add(contact8);
            list.add(contact9);
            list.add(contact10);
        }
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager(),list);
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        FloatingActionButton fab = findViewById(R.id.fab);
        final Intent intent2 = new Intent(this,CreateContact.class);
        intent2.putExtra("list",list);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent2);
            }
        });
    }

    public contact ReadUserFromFile()
    {
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
        //list = (ArrayList) fileIn.readObject()  ileOut.writeChars(x.ID+";"+x.lastText+";"+x.imgPath);

        if(ListFile.exists()) {
            BufferedReader reader;
            contact contact2 = new contact("","","");
            try {
                reader = new BufferedReader(new FileReader(ListFile));
                String line = reader.readLine();
                if (line != null) {
                    String string = line;
                    String[] parts = string.split(";");
                    String ID = parts[0]; // Phone Number
                    String lastText = parts[1]; // Name
                    String imgPath = parts[2]; // Picture
                    contact2 = new contact(ID,lastText,imgPath);
                }
                reader.close();
                return contact2;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    public ArrayList<contact> ReadListFromFile() throws IOException, ClassNotFoundException {
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
        String ListFileName="file.txt";
        String pictureFilePath;
        pictureFilePath = mediaStorageDir.getPath() + File.separator + ListFileName;
        ListFile = new File(mediaStorageDir.getPath() + File.separator + ListFileName);
        //list = (ArrayList) fileIn.readObject()  ileOut.writeChars(x.ID+";"+x.lastText+";"+x.imgPath);

        if(ListFile.exists()) {
            list = new ArrayList<contact>();
            BufferedReader reader;
            try {
                reader = new BufferedReader(new FileReader(ListFile));
                String line = reader.readLine();
                while (line != null) {
                    //System.out.println(line);
                    // read next line
                    String string = line;
                    String[] parts = string.split(";");
                    String ID = parts[0]; // Phone Number
                    String lastText = parts[1]; // Name
                    String imgPath = parts[2]; // Picture
                    contact contact2 = new contact(ID,lastText,imgPath);
                    list.add(contact2);
                    line = reader.readLine();
                }
                reader.close();
                return list;
            } catch (IOException e) {
                e.printStackTrace();
            }
            }
        return null;
    }
}