package com.example.simplechatapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

/**public class CommunicationActivity extends AppCompatActivity {
    private ArrayAdapter<String> adapter;
    private String serverAddress;
    private CommunicationThread communicationThread = null;
    private ListView lstMessages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_communication);

        // Add event handler for when user presses enter key.
        EditText edtMessage = findViewById(R.id.edtMessage);
        edtMessage.setOnKeyListener((view, i, keyEvent) -> {
            // If not busy communicating, do nothing.
            if(communicationThread == null) return false;

            // Did not press the enter key, do nothing.
            if ((keyEvent.getKeyCode() != KeyEvent.KEYCODE_ENTER)
                    || (keyEvent.getAction() != KeyEvent.ACTION_DOWN)) return false;

            // Busy communicating with server and pressed enter key, so send message.
            String message = edtMessage.getText().toString();

            // Call method to send message on output stream to server. CANNOT be done
            // on the UI thread.
            new Thread(() -> communicationThread.sendMessage(message)).start();

            // Clear the edit text.
            edtMessage.setText("");

            // Done, so indicate processed event.
            return true;
        });

        // RecyclerView is the more "current" way of doing it, but the set up code
        // is a LOT longer - so not really worth the effort for this example.
        lstMessages = findViewById(R.id.lstMessages);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_activated_1,
                android.R.id.text1,
                new ArrayList<String>());
        lstMessages.setAdapter(adapter);

        // Check whether have permission to use the internet.
        boolean hasPermission =
                checkSelfPermission(Manifest.permission.INTERNET) ==
                        PackageManager.PERMISSION_GRANTED;

        // Get server address from intent.
        try {
            serverAddress = getIntent().getExtras().getString("Server Address");
        } catch (Exception e) {
            serverAddress = "Undefined";
        }

        // Display initial settings.
        addMessage("Internet Permission = " + hasPermission
                + "\nServer Address = " + serverAddress);

        // Open connection.
        openConnection();
    }

    private void openConnection() {
        communicationThread = new CommunicationThread(
                serverAddress, // Address to connect too
                value -> runOnUiThread(() -> addMessage(value)), // How to display messages.
                () -> runOnUiThread(() -> finish())); // What to do when connection closed.
        communicationThread.start();
    }

    public void addMessage(String message) {
        adapter.add(message);
        lstMessages.setSelection(adapter.getCount() - 1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (communicationThread == null) return;

        // Send message to server to let it know that the connection is to be
        // terminated.
        new Thread(() -> communicationThread.sendMessage("TERMINATE")).start();
    }
}*/
