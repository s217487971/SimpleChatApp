package com.example.simplechatapp;

//import android.support.v7.app.AppCompatActivity;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
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

import java.util.Random;

public class createAccountScreen extends AppCompatActivity {

    private int SEND_SMS_GRANTED = 112;
    private int OTP = 0;
    private String cellURI;
    private String messageContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account_screen);
        final EditText phoneNumber = findViewById(R.id.editText3);
        Button confirmButton = findViewById(R.id.buttonConfirm1);
        phoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().length()==9)
                {
                    cellURI = "0"+s.toString();
                }
                else if(s.toString().length()>9) {
                    phoneNumber.setError("It allows only 5 character");
                }else{
                    phoneNumber.setError(null);
                }
            }
        });
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ConfirmPhoneActivity.class);
                if(checkSelfPermission(Manifest.permission.SEND_SMS)!= PackageManager.PERMISSION_GRANTED)
                {
                    requestPermissions(new String[]{Manifest.permission.SEND_SMS},SEND_SMS_GRANTED);
                }
                else
                {
                    OTP = CreateOTP();
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(cellURI, null, messageContent, null, null);
                    Intent intent2 = new Intent(getApplicationContext(), ConfirmPhoneActivity.class);
                    intent.putExtra("OTP",OTP);
                    startActivity(intent2);
                }
            }
        });
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
        if (requestCode == SEND_SMS_GRANTED)
        {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "SMS permission granted", Toast.LENGTH_LONG).show();
                OTP = CreateOTP();
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(cellURI, null, messageContent, null, null);
                Intent intent = new Intent(getApplicationContext(), ConfirmPhoneActivity.class);
                intent.putExtra("OTP",OTP);
                startActivity(intent);
            }
            else
            {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }
}
