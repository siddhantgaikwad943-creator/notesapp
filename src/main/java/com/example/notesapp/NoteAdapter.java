package com.example.notesapp;

import android.content.*;

import android.util.Log;

import android.view.*;
import android.widget.*;
import androidx.recyclerview.widget.RecyclerView;
import java.util.*;
import android.graphics.Color;
import android.content.Intent;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    Context context;
    List<Note> noteList;
    DatabaseHelper db;
    List<Note> fullList;

    public NoteAdapter(Context context, List<Note> noteList) {
        this.context = context;
        this.noteList = noteList;
        this.fullList = new ArrayList<>(noteList);
        db = new DatabaseHelper(context);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, content, timestamp;
        ImageButton btnDelete;
        ImageButton btnShare;

        public ViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.tvTitle);
            content = view.findViewById(R.id.tvContent);
            timestamp = view.findViewById(R.id.tvTimestamp);
            btnDelete = view.findViewById(R.id.btnDelete);
            btnShare = view.findViewById(R.id.btnShare);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_note, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Note note = noteList.get(position);
        holder.title.setText(note.getTitle());
        holder.content.setText(note.getContent());
        holder.timestamp.setText(note.getTimestamp());
        if (note.getColor() != null) {
            holder.itemView.setBackgroundColor(Color.parseColor(note.getColor()));
        } else {
            holder.itemView.setBackgroundColor(Color.parseColor("#E3F2FD"));
        }
        Log.d("COLOR_DEBUG", note.getColor());
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, AddEditNoteActivity.class);
            intent.putExtra("id", note.getId());
            intent.putExtra("title", note.getTitle());
            intent.putExtra("content", note.getContent());
            context.startActivity(intent);
        });

        holder.btnDelete.setOnClickListener(v -> {
            db.deleteNote(note.getId());
            noteList.remove(position);
            notifyDataSetChanged();
        });
        holder.btnShare.setOnClickListener(v -> {

            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");

            String shareText = note.getTitle() + "\n\n" + note.getContent();

            shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);

            context.startActivity(Intent.createChooser(shareIntent, "Share via"));

        });
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public void filter(String text) {
        noteList.clear();
        if (text.isEmpty()) {
            noteList.addAll(fullList);
        } else {
            text = text.toLowerCase();
            for (Note note : fullList) {
                if (note.getTitle().toLowerCase().contains(text)) {
                    noteList.add(note);
                }
            }
        }
        notifyDataSetChanged();
    }
}