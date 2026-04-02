package com.example.notes;

/**
 * Note.java — Data model representing a single note.
 * Stores the note text and the timestamp when it was created.
 */
public class Note {
    private String text;
    private long timestamp;

    public Note(String text, long timestamp) {
        this.text = text;
        this.timestamp = timestamp;
    }

    public String getText() {
        return text;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
