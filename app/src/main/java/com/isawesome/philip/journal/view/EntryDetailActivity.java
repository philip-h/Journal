package com.isawesome.philip.journal.view;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.isawesome.philip.journal.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EntryDetailActivity extends AppCompatActivity
{
    private static final String TAG = "JTAG";

    private static final String ENTRY_CONTENT = "ENTRY_CONTENT";
    private static final String ENTRY_DATE = "ENTRY_DATE";

    private TextView mEntryContent;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_detail);

        hookViewsToVars();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        Intent fromMainActivity = getIntent();
        String entryContent = fromMainActivity.getStringExtra(ENTRY_CONTENT);
        String date = fromMainActivity.getStringExtra(ENTRY_DATE);

        if (entryContent != null)
        {
            mEntryContent.setText(entryContent);
        }

        if (date != null)
        {
            ActionBar actionToolbar = getSupportActionBar();
            String title = actionToolbar.getTitle().toString();
            try
            {
                Date entryDate = new SimpleDateFormat("yyyy-MM-dd-E").parse(date);
                SimpleDateFormat readableDateFormat = new SimpleDateFormat("dd MMMM");
                title += " - " + readableDateFormat.format(entryDate);
            } catch (ParseException e)
            {
                e.printStackTrace();
            }

            actionToolbar.setTitle(title);

        }
    }

    private void hookViewsToVars()
    {
        mEntryContent = findViewById(R.id.view_entry_content);
    }
}
