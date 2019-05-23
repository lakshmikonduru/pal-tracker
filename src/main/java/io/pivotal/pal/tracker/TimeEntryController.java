package io.pivotal.pal.tracker;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/time-entries")
public class TimeEntryController {

    private TimeEntryRepository timeEntryRepository;
    private final DistributionSummary timeEntrySummary;
    private final Counter actionCounter;

    public TimeEntryController(TimeEntryRepository timeEntryRepository, MeterRegistry meterRegistry) {
        this.timeEntryRepository = timeEntryRepository;
        timeEntrySummary = meterRegistry.summary("timeEntry.summary");
        actionCounter = meterRegistry.counter("timeEntry.actionCounter");
    }
    @PostMapping
    public ResponseEntity create(@RequestBody TimeEntry timeEntryToCreate){
        TimeEntry newTimeEntryTocreate = timeEntryRepository.create(timeEntryToCreate);
        actionCounter.increment();
        timeEntrySummary.record(timeEntryRepository.list().size());
        return new ResponseEntity(newTimeEntryTocreate,HttpStatus.CREATED);
    }
    @GetMapping("{id}")
    public ResponseEntity<TimeEntry> read(@PathVariable long id){
        TimeEntry timeEntry = timeEntryRepository.find(id);
        if ( null != timeEntry ){
            actionCounter.increment();
            return  new ResponseEntity(timeEntry,HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping
    public ResponseEntity<List<TimeEntry>> list(){
        actionCounter.increment();
        List<TimeEntry> timeEntryList = timeEntryRepository.list();
        return  new ResponseEntity(timeEntryList,HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity update(@PathVariable long id,@RequestBody TimeEntry timeEntryToUpdate){
        TimeEntry updatedTimeEntry = timeEntryRepository.update(id,timeEntryToUpdate);
        if ( null != updatedTimeEntry){
            actionCounter.increment();
            return new ResponseEntity(updatedTimeEntry,HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable long id){
        timeEntryRepository.delete(id);
        actionCounter.increment();
        timeEntrySummary.record(timeEntryRepository.list().size());
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
