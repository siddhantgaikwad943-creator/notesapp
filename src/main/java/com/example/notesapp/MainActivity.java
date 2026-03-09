package com.example.notesapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.*;
import android.widget.*;
import java.util.*;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    NoteAdapter adapter;
    DatabaseHelper db;
    List<Note> noteList;
    Button btnAdd;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        btnAdd = findViewById(R.id.btnAdd);

        db = new DatabaseHelper(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadNotes();

        btnAdd.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, AddEditNoteActivity.class));
        });

        searchView = findViewById(R.id.searchView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.filter(newText);
                return true;
            }
        });
    }

    private void loadNotes() {
        noteList = db.getAllNotes();
        adapter = new NoteAdapter(this, noteList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadNotes();
    }
}