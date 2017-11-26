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

    private JournalEntry mTempEntry;
    private int mTempPosition;

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
        JournalEntry newEntry = new JournalEntry("Hello");
        id = mModel.create(newEntry);
        mView.addEntryToView(newEntry, id);

    }

    public void onJournalEntryClick(JournalEntry entry, View v)
    {
        mView.startEntryDetailActivity(entry.getContent(), entry.getDateCreated(), v);
    }

    public void onEntrySwipedLeft(int position, JournalEntry entry)
    {
        mView.deleteEntryAt(position);
        mView.showUndoSnackBar();

        mTempPosition = position;
        mTempEntry = entry;
    }

    public void onUndoConfirmed()
    {
        if (mTempEntry != null)
        {
            mView.insertEntryAt(mTempPosition, mTempEntry);
            mTempEntry = null;
            mTempPosition = -1;
        }
    }


    public void onSnackBarTimeout()
    {
        if (mTempEntry != null)
            mModel.delete(mTempEntry);

        mTempEntry = null;
        mTempPosition = -1;
    }
}
