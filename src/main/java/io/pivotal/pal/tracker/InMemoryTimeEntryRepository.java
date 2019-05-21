package io.pivotal.pal.tracker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTimeEntryRepository implements TimeEntryRepository {

    Map<Long, TimeEntry> timeEntries = new HashMap<>();

    public TimeEntry create(TimeEntry timeEntry){
        if ( null != timeEntry ) {
            timeEntries.put(timeEntry.getId(), timeEntry);
        }
        return timeEntry;
    }

    public TimeEntry find(long id){
        return timeEntries.get(id);
    }

    public List<TimeEntry> list(){
        List<TimeEntry> timeList = new ArrayList<>();
        if (null != timeEntries.values() ) {
            timeList.addAll(timeEntries.values());
        }
        return timeList;
    }

    public TimeEntry update(long id, TimeEntry timeEntry){
        timeEntries.put(id,timeEntry);
        return timeEntries.get(id);
    }

    public void delete(long id){
        timeEntries.remove(id);
    }
}
