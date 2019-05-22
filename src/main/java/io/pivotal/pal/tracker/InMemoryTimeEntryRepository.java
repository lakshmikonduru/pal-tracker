package io.pivotal.pal.tracker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTimeEntryRepository implements TimeEntryRepository {

    Map<Long, TimeEntry> timeEntries = new HashMap<>();
    long defaultId = 1L;
    public TimeEntry create(TimeEntry timeEntry){
        Long id = defaultId++;
        TimeEntry timeEntryToCreate = new TimeEntry(id,timeEntry.getProjectId(),timeEntry.getUserId(),timeEntry.getDate(),timeEntry.getHours());
        timeEntries.put(id, timeEntryToCreate);
        return timeEntryToCreate;
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
        if(null == find(id)){
            return null;
        }
        TimeEntry timeEntryToCreate = new TimeEntry(id,timeEntry.getProjectId(),timeEntry.getUserId(),timeEntry.getDate(),timeEntry.getHours());
        timeEntries.put(id,timeEntryToCreate);
        return timeEntries.get(id);
    }

    public void delete(long id){
        timeEntries.remove(id);
    }
}
