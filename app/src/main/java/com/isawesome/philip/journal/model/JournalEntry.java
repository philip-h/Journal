package com.isawesome.philip.journal.model;

/**
 * Created by Philip on 11/23/2017.
 */

public class JournalEntry
{
    private Long id;
    private String mContent;
    // Format: YYYY-MM-dd
    private String mDateCreated;

    public JournalEntry(String content)
    {
        mContent = content;
    }

    public JournalEntry(Long id, String content, String dateCreated)
    {
        this.id = id;
        mContent = content;
        mDateCreated = dateCreated;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getContent()
    {
        return mContent;
    }

    public void setContent(String content)
    {
        mContent = content;
    }

    public String getDateCreated()
    {
        return mDateCreated;
    }

    public String getMonthCreated()
    {
        return mDateCreated.split("-")[1];
    }

    public String getMonthNameCreated()
    {
        int monthNum = Integer.parseInt(mDateCreated.split("-")[1]);
        String [] monthNames = new String[] {
                "January", "February", "March",
                "April", "May", "June",
                "July", "August", "September",
                "October", "November", "December"
        };
        return monthNames[monthNum-1];
    }

    public String getDayOfMonthCreated()
    {
        return mDateCreated.split("-")[2];
    }

    public String getDayOfWeekCreated()
    {
        return mDateCreated.split("-")[3];
    }



    public void setDateCreated(String dateCreated)
    {
        mDateCreated = dateCreated;
    }
}
