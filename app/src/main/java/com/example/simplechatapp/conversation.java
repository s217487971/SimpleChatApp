package com.example.simplechatapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.security.acl.Permission;

import static com.example.simplechatapp.R.layout.custom_toolbar;

public class conversation extends AppCompatActivity {

    private Toolbar mActionBarToolbar;
    private contact contact2;
    private int MakeCall = 922;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        //getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        //getSupportActionBar().setCustomView(R.layout.custom_toolbar);
        TextView textView = findViewById(R.id.toolbar_title);
        //textView.setText("My Custom Title");
        ImageButton button = findViewById(R.id.imageButton3);

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null)
        {
            contact2 = bundle.getParcelable("user");
        }
        if(contact2!=null)
        {
            textView.setText(contact2.getID());
        }
        View.OnClickListener MakeACall = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if((Manifest.permission.CALL_PHONE)== PackageManager.PERMISSION_GRANTED)
            }
        };
        View.OnClickListener ProfileDetails = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        };
        button.setOnClickListener(MakeACall);
        textView.setOnClickListener(ProfileDetails);

    }
}
