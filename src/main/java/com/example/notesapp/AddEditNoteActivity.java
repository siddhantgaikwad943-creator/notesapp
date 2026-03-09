package com.example.notesapp;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class AddEditNoteActivity extends AppCompatActivity {

    EditText etTitle, etContent;
    Button btnSave;
    DatabaseHelper db;
    int noteId = -1;
    Button btnBlue, btnYellow, btnPink;
    String selectedColor = "#E3F2FD"; // default

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_note);

        etTitle = findViewById(R.id.etTitle);
        etContent = findViewById(R.id.etContent);
        btnSave = findViewById(R.id.btnSave);

        db = new DatabaseHelper(this);

        if (getIntent().hasExtra("id")) {
            noteId = getIntent().getIntExtra("id", -1);
            etTitle.setText(getIntent().getStringExtra("title"));
            etContent.setText(getIntent().getStringExtra("content"));
        }

        btnSave.setOnClickListener(v -> {
            String title = etTitle.getText().toString();
            String content = etContent.getText().toString();
            String timestamp = new SimpleDateFormat("dd MMM yyyy, HH:mm",
                    Locale.getDefault()).format(new Date());

            if (noteId == -1) {
                db.insertNote(title, content, timestamp, selectedColor);
            } else {
                db.updateNote(noteId, title, content, timestamp, selectedColor);
            }
            finish();
        });

        btnBlue = findViewById(R.id.btnBlue);
        btnYellow = findViewById(R.id.btnYellow);
        btnPink = findViewById(R.id.btnPink);

        btnBlue.setOnClickListener(v -> selectedColor = "#E3F2FD");
        btnYellow.setOnClickListener(v -> selectedColor = "#FFF9C4");
        btnPink.setOnClickListener(v -> selectedColor = "#F8BBD0");
    }
}