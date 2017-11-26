package com.isawesome.philip.journal.view;

import android.view.View;

import com.isawesome.philip.journal.model.JournalEntry;

/**
 * Created by Philip on 11/23/2017.
 */

public interface ViewInterface
{
    void init();

    void startAddNoteActivity();

    void addEntryToView(JournalEntry entry, Long id);

    void updateLatestEntry(JournalEntry entry);

    void startEntryDetailActivity(String content, String dateCreated, View viewRoot);
}
