package io.pivotal.pal.tracker;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
public class TimeEntryController {

    private TimeEntryRepository timeEntryRepository;

    public TimeEntryController(TimeEntryRepository timeEntryRepository) {
        this.timeEntryRepository = timeEntryRepository;
    }
    @PostMapping("/time-entries")
    public ResponseEntity create(@RequestBody TimeEntry timeEntryToCreate){
        timeEntryRepository.create(timeEntryToCreate);
        return new ResponseEntity(timeEntryToCreate,HttpStatus.CREATED);
    }
    @GetMapping("/time-entries/{id}")
    public ResponseEntity<TimeEntry> read(@PathVariable long id){
        TimeEntry timeEntry = timeEntryRepository.find(id);
        if ( null == timeEntry ){
            return  new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return  new ResponseEntity(timeEntry,HttpStatus.OK);
    }
    @GetMapping("/time-entries")
    public ResponseEntity<List<TimeEntry>> list(){
        List<TimeEntry> timeEntryList = timeEntryRepository.list();
        return  new ResponseEntity(timeEntryList,HttpStatus.OK);
    }

    @PutMapping("/time-entries/{id}")
    public ResponseEntity update(@PathVariable long id,@RequestBody TimeEntry timeEntryToUpdate){
        timeEntryRepository.update(id,timeEntryToUpdate);
        if ( null == timeEntryToUpdate.getDate()){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity(timeEntryRepository.find(id),HttpStatus.OK);
    }

    @DeleteMapping("/time-entries/{id}")
    public ResponseEntity delete(@PathVariable long id){
        timeEntryRepository.delete(id);
        if ( null == timeEntryRepository.find(id)){
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity(timeEntryRepository.find(id),HttpStatus.OK);
    }
}
