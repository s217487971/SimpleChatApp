package com.example.simplechatapp;

//import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ConfirmPhoneActivity extends AppCompatActivity {

    private int OPT;
    private String CellURI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_phone);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            OPT = bundle.getInt("OTP");
            CellURI = bundle.getString("CellURI");
        }
        final TextView countDown = findViewById(R.id.textView6);
        final EditText otpIn = findViewById(R.id.editText4);
        otpIn.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() == 4) {
                    if (Integer.valueOf(s.toString()) == OPT) {
                        Intent intent = new Intent(getApplicationContext(), Chat.class);
                        intent.putExtra("CellURI", CellURI);
                        startActivity(intent);
                    }
                } else if (s.toString().length() > 9) {
                    otpIn.setError("It allows only 5 character");
                } else {
                    otpIn.setError(null);
                }
            }
        });
        final Button resendOTP = findViewById(R.id.button2);
        Button confirm = findViewById(R.id.button3);
        resendOTP.setClickable(false);
        CountDownTimer cTimer = null;
        cTimer = new CountDownTimer(60000, 1000) {
            public void onTick(long millisUntilFinished) {
                countDown.setText(String.valueOf(millisUntilFinished));

            }

            public void onFinish() {
                resendOTP.setClickable(true);
            }
        };
        cTimer.start();
        resendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Chat.class);
                
            }
        });

    }
}
