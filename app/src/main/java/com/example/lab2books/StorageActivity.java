package com.example.lab2books;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class StorageActivity extends AppCompatActivity {

    private TextView textStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage);

        textStorage = findViewById(R.id.textStorage);

        String data = StorageHelper.readFromFile(this);

        if (data.isEmpty()) {
            textStorage.setText("Сховище порожнє");
        } else {
            textStorage.setText(data);
        }
    }
}