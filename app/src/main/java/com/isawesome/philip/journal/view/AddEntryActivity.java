package com.isawesome.philip.journal.view;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;

import com.isawesome.philip.journal.R;

public class AddEntryActivity extends AppCompatActivity
{
    private static final String TAG = "JTAG";

    private static final String ENTRY_CONTENT = "ENTRY_CONTENT";

    private EditText mEntryContent;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entry);

        hookViewsToVars();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        Intent fromMainActivity = getIntent();
        String entryContent = fromMainActivity.getStringExtra(ENTRY_CONTENT);
        if (entryContent != null)
        {
            mEntryContent.setText(entryContent);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                startMainActivity();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed()
    {
        startMainActivity();
    }

    private void hookViewsToVars()
    {
        mEntryContent = findViewById(R.id.edit_entry_content);
    }

    private void startMainActivity()
    {
        Intent toMainActivity = new Intent(AddEntryActivity.this, MainActivity.class);
        toMainActivity.putExtra(ENTRY_CONTENT, mEntryContent.getText().toString());
        setResult(Activity.RESULT_OK, toMainActivity);
        finish();
    }

}
