package io.pivotal.pal.tracker;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class TimeEntryController {

    private TimeEntryRepository timeEntryRepository;

    public TimeEntryController(TimeEntryRepository timeEntryRepository) {
        this.timeEntryRepository = timeEntryRepository;
    }

    public ResponseEntity create(TimeEntry timeEntryToCreate){
        timeEntryRepository.create(timeEntryToCreate);
        return new ResponseEntity(timeEntryToCreate,HttpStatus.CREATED);
    }

    public ResponseEntity<TimeEntry> read(long id){
        TimeEntry timeEntry = timeEntryRepository.find(id);
        if ( null == timeEntry ){
            return  new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return  new ResponseEntity(timeEntry,HttpStatus.OK);
    }

    public ResponseEntity<List<TimeEntry>> list(){
        List<TimeEntry> timeEntryList = timeEntryRepository.list();
        return  new ResponseEntity(timeEntryList,HttpStatus.OK);
    }

    public ResponseEntity update(long id,TimeEntry timeEntryToUpdate){
        timeEntryRepository.update(id,timeEntryToUpdate);
        if ( null == timeEntryToUpdate.getDate()){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity(timeEntryRepository.find(id),HttpStatus.OK);
    }

    public ResponseEntity delete(long id){
        timeEntryRepository.delete(id);
        if ( null == timeEntryRepository.find(id)){
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity(timeEntryRepository.find(id),HttpStatus.OK);
    }
}
