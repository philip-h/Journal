package com.isawesome.philip.journal.presenter;

import android.view.View;

import com.isawesome.philip.journal.model.JournalEntry;
import com.isawesome.philip.journal.model.ModelInterface;
import com.isawesome.philip.journal.view.ViewInterface;

import java.util.List;

/**
 * Created by Philip on 11/23/2017.
 */

public class MainPresenter
{
    private ViewInterface mView;
    private ModelInterface mModel;

    public MainPresenter(ViewInterface view, ModelInterface model)
    {
        mView = view;
        mModel = model;
    }

    public void onFabClick()
    {
        mView.startAddNoteActivity();
    }

    public List<JournalEntry> getEntriesFromModel()
    {
        return mModel.read();
    }

    public void updateEntry(JournalEntry entry)
    {
        mModel.update(entry);
        mView.updateLatestEntry(entry);
    }

    public void addEntry(JournalEntry entry)
    {
        Long id = mModel.create(entry);
        mView.addEntryToView(entry, id);

    }

    public void onJournalEntryClick(JournalEntry entry, View v)
    {
        mView.startEntryDetailActivity(entry.getContent(), entry.getDateCreated(), v);
    }
}
