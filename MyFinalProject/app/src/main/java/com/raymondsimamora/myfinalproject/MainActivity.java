package com.raymondsimamora.myfinalproject;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.EditText;


import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView welcomeMessageTextView;
    private EditText userInputEditText;

    private TextView resultTextView;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        welcomeMessageTextView = findViewById(R.id.welcomeMessageTextView);
        userInputEditText = findViewById(R.id.userInputEditText);
        resultTextView = findViewById(R.id.resultTextView);
        dbHelper = new DatabaseHelper(this);

        // Ambil username dari database
        String username = getUsernameFromDatabase();

        if(username != null) {
            // Tampilkan pesan selamat datang dengan username
            String welcomeMessage = "Hello, " + username;
            welcomeMessageTextView.setText(welcomeMessage);
        } else {
            // Tampilkan pesan kesalahan jika tidak dapat mengambil username
            Toast.makeText(this, "Failed to get username from database", Toast.LENGTH_SHORT).show();
        }

        // Set listener untuk tombol Submit
        Button submitButton = findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ambil teks dari input pengguna
                String userInput = userInputEditText.getText().toString().trim();

                // Tampilkan teks input di resultTextView
                resultTextView.setText(userInput);
            }
        });
    }

    // Method untuk mengambil username dari database
    @SuppressLint("Range")
    private String getUsernameFromDatabase() {
        String username = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + DatabaseHelper.COLUMN_USERNAME + " FROM " + DatabaseHelper.TABLE_USERS, null);
        if (cursor.moveToFirst()) {
            username = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_USERNAME));
        }
        cursor.close();
        return username;
    }
}

