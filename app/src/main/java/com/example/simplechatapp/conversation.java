package com.example.simplechatapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class conversation extends AppCompatActivity {

    private Toolbar mActionBarToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null)
        {
            contact contact2 = bundle.getParcelable("contact2");
        }

    }
    protected Toolbar getActionBarToolbar() {
        if (mActionBarToolbar == null) {
            //mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
            if (mActionBarToolbar != null) {
                setSupportActionBar(mActionBarToolbar);
            }
        }
        return mActionBarToolbar;
    }
    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        getActionBarToolbar();
    }
}
