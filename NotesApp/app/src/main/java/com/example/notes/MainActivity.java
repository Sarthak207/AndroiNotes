package com.example.notes;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

/**
 * MainActivity.java — Entry point of the app.
 *
 * Responsibilities:
 *  - Displays an EditText for note input and an "Add" button
 *  - Shows all saved notes in a RecyclerView (newest first)
 *  - Saves/loads notes using SharedPreferences (JSON format)
 *  - Long-press on any note shows a delete confirmation dialog
 */
public class MainActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "NotesPrefs";
    private static final String KEY_NOTES  = "notes_json";

    private EditText      etNote;
    private RecyclerView  recyclerView;
    private NotesAdapter  adapter;
    private List<Note>    noteList;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs        = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        etNote       = findViewById(R.id.et_note_input);
        Button btnAdd = findViewById(R.id.btn_add_note);
        recyclerView  = findViewById(R.id.recycler_notes);

        noteList = loadNotes();
        adapter  = new NotesAdapter(noteList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Long-press → confirm delete
        adapter.setOnNoteLongClickListener(position -> {
            new AlertDialog.Builder(this)
                .setTitle("Delete Note")
                .setMessage("Remove this note?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    noteList.remove(position);
                    adapter.notifyItemRemoved(position);
                    saveNotes();
                    Toast.makeText(this, "Note deleted", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", null)
                .show();
        });

        btnAdd.setOnClickListener(v -> {
            String text = etNote.getText().toString().trim();
            if (TextUtils.isEmpty(text)) {
                Toast.makeText(this, "Please enter a note", Toast.LENGTH_SHORT).show();
                return;
            }
            Note note = new Note(text, System.currentTimeMillis());
            noteList.add(0, note);            // newest at top
            adapter.notifyItemInserted(0);
            recyclerView.scrollToPosition(0);
            etNote.setText("");               // clear input after adding
            saveNotes();
            Toast.makeText(this, "Note added!", Toast.LENGTH_SHORT).show();
        });
    }

    // ── Persistence (SharedPreferences + JSON) ───────────────────────────────

    private void saveNotes() {
        JSONArray array = new JSONArray();
        for (Note n : noteList) {
            try {
                JSONObject obj = new JSONObject();
                obj.put("text", n.getText());
                obj.put("ts",   n.getTimestamp());
                array.put(obj);
            } catch (JSONException ignored) {}
        }
        prefs.edit().putString(KEY_NOTES, array.toString()).apply();
    }

    private List<Note> loadNotes() {
        List<Note> list = new ArrayList<>();
        String json = prefs.getString(KEY_NOTES, null);
        if (json == null) return list;
        try {
            JSONArray array = new JSONArray(json);
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                list.add(new Note(obj.getString("text"), obj.getLong("ts")));
            }
        } catch (JSONException ignored) {}
        return list;
    }
}
