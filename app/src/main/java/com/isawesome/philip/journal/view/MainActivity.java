package com.isawesome.philip.journal.view;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.transition.AutoTransition;
import android.transition.ChangeBounds;
import android.transition.Fade;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.isawesome.philip.journal.R;
import com.isawesome.philip.journal.model.FakeDatasource;
import com.isawesome.philip.journal.model.JournalDatabase;
import com.isawesome.philip.journal.model.JournalEntry;
import com.isawesome.philip.journal.presenter.MainPresenter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements ViewInterface
{
    private static final String TAG = "JTAG";

    private static final String ENTRY_CONTENT = "ENTRY_CONTENT";
    private static final String ENTRY_DATE = "ENTRY_DATE";
    private static final int ADD_NOTE_REQUEST_CODE = 22418;

    private RecyclerView mRecyclerView;
    private JournalRowAdapter mAdapter;
    private LayoutInflater mLayoutInflater;
    private FloatingActionButton mFab;


    private MainPresenter mPresenter;

    private List<JournalEntry> mJournalEntries;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        if (!mJournalEntries.isEmpty() && aJournalEntryWasCreatedToday())
        {
            mFab.setImageResource(R.drawable.ic_edit_24dp);
        }

        mFab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mPresenter.onFabClick();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == ADD_NOTE_REQUEST_CODE && resultCode == RESULT_OK)
        {
            String content = data.getStringExtra(ENTRY_CONTENT);
            if (!mJournalEntries.isEmpty() && aJournalEntryWasCreatedToday())
            {
                int positionToUpdate = mJournalEntries.size() - 1;
                JournalEntry entry = mJournalEntries.get(positionToUpdate);
                entry.setContent(content);
                mPresenter.updateEntry(entry);
            } else
            {
                Log.d(TAG, "onActivityResult: Adding journal Entry");
                JournalEntry entry = new JournalEntry(content);
                mPresenter.addEntry(entry);
            }
        }
    }

    @Override
    public void init()
    {
        hookViewsToVars();
        setPresenter();
        setUpRecyclerView();
    }

    @Override
    public void startAddNoteActivity()
    {
        Intent toAddNoteActivity = new Intent(MainActivity.this, AddEntryActivity.class);
        if (!mJournalEntries.isEmpty() && aJournalEntryWasCreatedToday())
        {
            toAddNoteActivity.putExtra(
                    ENTRY_CONTENT,
                    mJournalEntries.get(mJournalEntries.size() - 1).getContent()
            );
        }
        startActivityForResult(toAddNoteActivity, ADD_NOTE_REQUEST_CODE);
    }

    @Override
    public void addEntryToView(JournalEntry entry, Long id)
    {
        entry.setId(id);
        mJournalEntries.add(entry);
        int endOfList = mJournalEntries.size() - 1;
        mAdapter.notifyDataSetChanged();
        mRecyclerView.smoothScrollToPosition(endOfList);
        mFab.setImageResource(R.drawable.ic_edit_24dp);
    }

    @Override
    public void updateLatestEntry(JournalEntry entry)
    {
        int position = mJournalEntries.size() - 1;
        mJournalEntries.set(position, entry);
        mAdapter.notifyItemChanged(position);
        mRecyclerView.smoothScrollToPosition(position);
    }

    @Override
    public void deleteEntryAt(int position)
    {
        mJournalEntries.remove(position);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void startEntryDetailActivity(String content, String dateCreated, View viewRoot)
    {
        Intent toEntryDetailActivity = new Intent(MainActivity.this, EntryDetailActivity.class);
        toEntryDetailActivity.putExtra(ENTRY_CONTENT, content);
        toEntryDetailActivity.putExtra(ENTRY_DATE, dateCreated);
        startActivity(toEntryDetailActivity);
    }

    @Override
    public void showUndoSnackBar()
    {
        Snackbar.make(
                findViewById(R.id.root_journal_row),
                getString(R.string.snack_delete_entry),
                Snackbar.LENGTH_LONG
        )
        .setAction(R.string.snack_undo, new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                mPresenter.onUndoConfirmed();
            }
        })
        .addCallback(new BaseTransientBottomBar.BaseCallback<Snackbar>()
        {
            @Override
            public void onDismissed(Snackbar transientBottomBar, int event)
            {
                super.onDismissed(transientBottomBar, event);
                mPresenter.onSnackBarTimeout();
            }
        })
        .show();
    }

    @Override
    public void insertEntryAt(int position, JournalEntry entry)
    {
        mJournalEntries.add(position, entry);
        mAdapter.notifyDataSetChanged();
    }


    private void hookViewsToVars()
    {
        mRecyclerView = findViewById(R.id.recycler_view);
        mFab = findViewById(R.id.fab);
    }

    private void setPresenter()
    {
        mPresenter = new MainPresenter(this, new JournalDatabase(MainActivity.this));
        mLayoutInflater = getLayoutInflater();
        Log.d(TAG, "setPresenter: Getting entries from the model!");
        mJournalEntries = mPresenter.getEntriesFromModel();
    }

    private void setUpRecyclerView()
    {
        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mAdapter = new JournalRowAdapter();
        mRecyclerView.setAdapter(mAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchHelperCallback());

        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    private boolean aJournalEntryWasCreatedToday()
    {
        return mJournalEntries.get(mJournalEntries.size()-1).getDateCreated()
                .equals(new SimpleDateFormat("yyyy-MM-dd-E", Locale.CANADA).format(new Date()));
    }



    private class JournalRowAdapter extends RecyclerView.Adapter<JournalRowAdapter.ViewHolder>
    {

        @Override
        public JournalRowAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View v = mLayoutInflater.inflate(R.layout.journal_row, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(JournalRowAdapter.ViewHolder holder, int position)
        {
            JournalEntry entry = mJournalEntries.get(position);

            holder.mEntryBlurb.setText(entry.getContent());
            holder.mMonth.setText(entry.getMonthNameCreated());
            holder.mDateNumber.setText(entry.getDayOfMonthCreated());
            holder.mDateDow.setText(entry.getDayOfWeekCreated());

            // Logic for Section Headers (The month):
            // Always display the month at the top
            // Only display the month else where if we are switching months
            // In the middle.
            if (position == mJournalEntries.size()-1)
            {
                holder.mMonth.setVisibility(View.VISIBLE);
            } else
            {
                int thisEntryMonth = Integer.parseInt(entry.getMonthCreated());
                int nextEntryMonth = Integer.parseInt(mJournalEntries.get(position + 1).getMonthCreated());
                if (nextEntryMonth > thisEntryMonth)
                {
                    holder.mMonth.setVisibility(View.VISIBLE);
                } else
                {
                    holder.mMonth.setVisibility(View.GONE);
                }
            }
        }

        @Override
        public int getItemCount()
        {
            return mJournalEntries.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
        {
            public TextView mMonth;
            public TextView mEntryBlurb;
            public TextView mDateNumber;
            public TextView mDateDow;

            public ViewHolder(View itemView)
            {
                super(itemView);
                mMonth = itemView.findViewById(R.id.tv_date_month);
                mEntryBlurb = itemView.findViewById(R.id.tv_entry_blurb);
                mDateNumber = itemView.findViewById(R.id.tv_date_number);
                mDateDow = itemView.findViewById(R.id.tv_date_dow);
                itemView.findViewById(R.id.root_journal_row).setOnClickListener(this);
            }

            @Override
            public void onClick(View v)
            {
                JournalEntry entry = mJournalEntries.get(getAdapterPosition());
                mPresenter.onJournalEntryClick(entry, v);
            }
        }
    }

    private ItemTouchHelper.Callback itemTouchHelperCallback()
    {
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT
        )
        {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target)
            {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction)
            {
                int position = viewHolder.getAdapterPosition();
                mPresenter.onEntrySwipedLeft(position, mJournalEntries.get(position));
            }
        };
        return simpleCallback;
    }


}
