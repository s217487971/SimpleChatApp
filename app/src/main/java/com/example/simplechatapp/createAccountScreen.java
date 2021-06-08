package com.example.simplechatapp;

//import android.support.v7.app.AppCompatActivity;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.AllPermission;
import java.util.Random;

public class createAccountScreen extends AppCompatActivity {

    private int All_permissions = 450;
    private int uses_internet= 220;
    private int OTP = 0;
    private String cellURI;
    private String messageContent;
    private int MY_CAMERA_PERMISSION_CODE;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account_screen);
        final EditText phoneNumber = findViewById(R.id.editText3);
        Button confirmButton = findViewById(R.id.buttonConfirm1);
        String[] permissionsList = new String[]{Manifest.permission.INTERNET, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        boolean permissions = hasPermissions(getApplicationContext(),permissionsList);
        if(checkSelfPermission(Manifest.permission.INTERNET)!= PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[]{Manifest.permission.INTERNET,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE}, All_permissions);
        }

        phoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().length()==9)
                {
                    cellURI = "+27"+s.toString();
                }
                else if(s.toString().length()>9) {
                    phoneNumber.setError("It allows only 9 Digits");
                }else{
                    phoneNumber.setError(null);
                }
            }
        });
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                if(checkSelfPermission(Manifest.permission.INTERNET)!= PackageManager.PERMISSION_GRANTED)
                {
                    requestPermissions(new String[]{Manifest.permission.INTERNET},uses_internet);
                }
                else
                {
                    OTP = CreateOTP();
                    //Intent i = new Intent(Intent.ACTION_VIEW);
                    //i.setData(Uri.parse("smsto:"));
                    //i.setType("vnd.android-dir/mms-sms");
                    //i.putExtra("address", cellURI);
                    //i.putExtra("sms_body",messageContent);
                    //startActivity(Intent.createChooser(i, "Send sms via:"));
                    //SmsManager smsManager = SmsManager.getDefault();
                    //smsManager.sendTextMessage(cellURI, null, messageContent, null, null);
                    messageContent = CreateMessageContent(OTP);
                    //CommunicationThread thread = new CommunicationThread(cellURI,messageContent);
                    //thread.start();
                    Intent intent2 = new Intent(getApplicationContext(), AccountDetails.class);
                    intent2.putExtra("CellURI",cellURI);
                    startActivity(intent2);
                }
            }
        });
        /**if (checkSelfPermission(Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED )
        {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.SEND_SMS, Manifest.permission.INTERNET}, SEND_SMS_GRANTED);
        }
        else
        {
            messageContent = CreateMessageContent(OTP);
            String sms = sendSms(cellURI,messageContent);
            Intent intent2 = new Intent(getApplicationContext(), ConfirmPhoneActivity.class);
            intent2.putExtra("OTP",OTP);
            startActivity(intent2);
        }*/
    }
    public  String CreateMessageContent(int OTP)
    {
        String content = "The OTP for your Simple Chat Account is :"+Integer.valueOf(OTP);
        return content;
    }

    public int CreateOTP()
    {
        Random random = new Random();
        int OTP = random.nextInt(9999)+1000;
        messageContent = "The OTP for the Simple Chat App is : "+OTP;
        return OTP;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        /**if (requestCode == SEND_SMS_GRANTED)
        {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                /**Toast.makeText(this, "SMS permission granted", Toast.LENGTH_LONG).show();
                OTP = CreateOTP();
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(cellURI, null, messageContent, null, null);
                Intent intent = new Intent(getApplicationContext(), ConfirmPhoneActivity.class);
                intent.putExtra("OTP",OTP);
                startActivity(intent);
                OTP = CreateOTP();
                messageContent = CreateMessageContent(OTP);
                String sms = sendSms(cellURI,messageContent);
                //Intent intent2 = new Intent(getApplicationContext(), ConfirmPhoneActivity.class);
                //intent2.putExtra("OTP",OTP);
                //startActivity(intent2);
            }
            else
            {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }*/
    }
    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
}
