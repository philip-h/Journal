package com.isawesome.philip.journal.model;

import java.util.List;

/**
 * Created by Philip on 11/23/2017.
 */

public interface ModelInterface
{
    // CRUD
    Long create(JournalEntry entry);
    List<JournalEntry> read();
    void update(JournalEntry entry);
    void delete(JournalEntry entry);
}
