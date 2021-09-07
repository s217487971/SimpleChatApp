package com.example.simplechatapp.ui.chat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simplechatapp.R;
import com.example.simplechatapp.contact;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.security.acl.Permission;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static com.example.simplechatapp.R.layout.custom_toolbar;

public class conversation extends AppCompatActivity {

    private Toolbar mActionBarToolbar;
    private contact contact2;
    private int MakeCall = 922;
    String cellUri;
    ArrayList savedTexts =  new ArrayList<>();
    private String pictureFilePath;
    //messageAdapter th;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        //getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        //getSupportActionBar().setCustomView(R.layout.custom_toolbar);
        final RecyclerView recyclerView = findViewById(R.id.messageRecyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        Bundle data = getIntent().getExtras();
        if(data!=null)
        {
            savedTexts = data.getParcelableArrayList(contact2.getID());
        }

        recyclerView.setAdapter(new messageAdapter(this,new ArrayList<message>()));

        /**message dateToday = new message(true);
        DateFormat df = new SimpleDateFormat("EEE, MMM d, ''yy");
        String date = df.format(Calendar.getInstance().getTime());
        dateToday.setContent(date);
        savedTexts.add(dateToday);
        th = (messageAdapter)recyclerView.getAdapter();
        //th.add(dateToday);*/

        TextView textView = findViewById(R.id.toolbar_title);
        //textView.setText("My Custom Title");
        ImageButton button = findViewById(R.id.imageButton3);
        final EditText editText = findViewById(R.id.editText6);
        FloatingActionButton send = findViewById(R.id.floatingActionButton4);

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
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + contact2.getNumber()));
                startActivity(intent);
            }
        };
        View.OnClickListener ProfileDetails = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        };
        View.OnClickListener sendMessage = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = editText.getText().toString();
                DateFormat df = new SimpleDateFormat("h:mm a" );
                String date = df.format(Calendar.getInstance().getTime());
                if(content.length()>0)
                {
                    message sendtext = new message(cellUri,contact2.getID(),content,date.toString(),true);
                    messageAdapter th = (messageAdapter)recyclerView.getAdapter();
                    th.add(sendtext);
                    //savedTexts.add(sendtext);
                    editText.setText("");
                }
            }
        };
        button.setOnClickListener(MakeACall);
        textView.setOnClickListener(ProfileDetails);
        send.setOnClickListener(sendMessage);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, MakeCall);
    }

    public void SaveTextsToFile() throws IOException {
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
        String ListFileName= contact2.getID()+ ".txt";
        pictureFilePath = mediaStorageDir.getPath() + File.separator + ListFileName;
        ListFile = new File(mediaStorageDir.getPath() + File.separator + ListFileName);

        //Save the list file
        FileOutputStream fos = new FileOutputStream(ListFile);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
        for (Object text:savedTexts
        ) {
            //message text2 = (message)text;
            bw.write(text+";");
            bw.newLine();
        }
        bw.close();
    }

    public ArrayList<message> ReadTextsFromFile()
    {
        ArrayList<message> savedMessages;
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + getApplicationContext().getPackageName()
                + "/Files");

        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                return null;
            }
        }
        // Create a media file name
        //String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmm").format(new Date());
        File ListFile;
        String ListFileName= contact2.getID()+ ".txt";
        pictureFilePath = mediaStorageDir.getPath() + File.separator + ListFileName;
        ListFile = new File(mediaStorageDir.getPath() + File.separator + ListFileName);

        //Save the list file
        if(ListFile.exists()) {
            savedMessages = new ArrayList<message>();
            BufferedReader reader;
            try {
                reader = new BufferedReader(new FileReader(ListFile));
                String line = reader.readLine();
                while (line != null) {
                    //System.out.println(line);
                    // read next line
                    String string = line;
                    String[] parts = string.split(";");
                    for (int i = 0; i < parts.length; i++) {
                        String content = parts[i];
                        savedMessages.add(new message("","",content,"",true));
                    }
                    line = reader.readLine();
                }
                reader.close();
                return savedMessages;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
