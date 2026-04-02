package com.example.notes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

/**
 * NotesAdapter.java — RecyclerView adapter.
 * Binds each Note object to a card view (item_note.xml).
 * Supports long-press deletion via OnNoteLongClickListener.
 */
public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteViewHolder> {

    public interface OnNoteLongClickListener {
        void onLongClick(int position);
    }

    private final List<Note> notes;
    private OnNoteLongClickListener longClickListener;

    public NotesAdapter(List<Note> notes) {
        this.notes = notes;
    }

    public void setOnNoteLongClickListener(OnNoteLongClickListener listener) {
        this.longClickListener = listener;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = notes.get(position);
        holder.tvNote.setText(note.getText());

        holder.itemView.setOnLongClickListener(v -> {
            if (longClickListener != null) {
                longClickListener.onLongClick(holder.getAdapterPosition());
            }
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    static class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView tvNote;

        NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNote = itemView.findViewById(R.id.tv_note_text);
        }
    }
}
