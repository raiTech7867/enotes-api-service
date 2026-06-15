package com.raiTech.schedular;

import com.raiTech.entity.Notes;
import com.raiTech.repository.NotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class NotesSchedular {

    @Autowired
    private NotesRepository notesRepository;

    @Scheduled(cron = "0 0 0 * * ?")
    public void deleteNotesSchedular(){
    //20 Nov-14 Nov-7days

        LocalDateTime cutOffDate = LocalDateTime.now().minusDays(7);
        List<Notes> deleteNotes=notesRepository.findAllByIsDeletedAndDeletedOnBefore(true,cutOffDate);
       notesRepository.deleteAll(deleteNotes);

    }
}
