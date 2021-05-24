package com.example.simplechatapp;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;

import com.example.simplechatapp.ui.main.SectionsPagerAdapter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Chat extends AppCompatActivity {

    ArrayList<contact> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Bundle bundle = getIntent().getExtras();
        try {
            list = ReadListFromFile();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if(bundle!=null && list==null)
        {
            list = bundle.getParcelableArrayList("list");
        }
        else if(bundle==null && list==null)
        {
            list = new ArrayList<>();
            contact contact1 = new contact("+27783607891","Bye...","drawable://" + R.drawable.maleavatar);
            contact contact2 = new contact("+27744807891","See ya...","");
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

    public ArrayList<contact> ReadListFromFile() throws IOException, ClassNotFoundException {
        ObjectInputStream fileIn = new ObjectInputStream(new FileInputStream("file.dat"));
        list = (ArrayList) fileIn.readObject();
        fileIn.close();
        return  list;
    }

}