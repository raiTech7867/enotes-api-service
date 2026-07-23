package com.raiTech.endpoint;

import com.raiTech.util.Constants;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.raiTech.util.Constants.*;


@RequestMapping("/api/v1/notes")
public interface NotesControllerEndPoint {

    @PostMapping("/save")
    @PreAuthorize(Constants.ROLE_USER)
    public ResponseEntity<?> saveNotes(@RequestParam String notesDto, @RequestParam(required = false) MultipartFile file) throws Exception;


    @GetMapping("/")
    @PreAuthorize(Constants.ROLE_ADMIN)
    public ResponseEntity<?>getAllNotes();


    @GetMapping("/search")
    @PreAuthorize(Constants.ROLE_USER)
    public ResponseEntity<?> searchNotes(
            @RequestParam("keyword") String keyword,
            @RequestParam(defaultValue = DEFAULT_PAGE_NO) int pageNo,
            @RequestParam(defaultValue =  DEFAULT_PAGE_SIZE) int pageSize);


    @GetMapping("/download/{id}")
    @PreAuthorize(ROLE_ADMIN_USER)
    public ResponseEntity<?>downLoadFile(@PathVariable Integer id) throws Exception;


    @GetMapping("/user-notes")
    @PreAuthorize(Constants.ROLE_USER)
    public ResponseEntity<?>getAllNotesByUser(@RequestParam(name = "pageNo",defaultValue = DEFAULT_PAGE_NO) Integer pageNo,
                                              @RequestParam(name = "pageSize",defaultValue = DEFAULT_PAGE_SIZE) Integer pageSize) throws Exception;


    @GetMapping("/delete/{id}")
    @PreAuthorize(Constants.ROLE_USER)
    public ResponseEntity<?> deleteNotes(@PathVariable Integer id) throws Exception;


    @GetMapping("/restore/{id}")
    @PreAuthorize(Constants.ROLE_USER)
    public ResponseEntity<?> restoreNotes(@PathVariable Integer id) throws Exception;


    @GetMapping("/recycle-bin")
    @PreAuthorize(Constants.ROLE_USER)
    public ResponseEntity<?> getUserRecycleBinNotes() throws Exception;


    @DeleteMapping("/delete/{id}")
    @PreAuthorize(Constants.ROLE_USER)
    public ResponseEntity<?> hardDeleteNotes(@PathVariable Integer id) throws Exception;


    @DeleteMapping("/delete")
    @PreAuthorize(Constants.ROLE_USER)
    public ResponseEntity<?> emptyRecycleBin() throws Exception;


    @GetMapping("/fav/{noteId}")
    @PreAuthorize(Constants.ROLE_USER)
    public ResponseEntity<?> favouritesNote(@PathVariable Integer noteId) throws Exception;

    @DeleteMapping("/un-fav/{favNoteId}")
    @PreAuthorize(Constants.ROLE_USER)
    public ResponseEntity<?> unFavouritesNote(@PathVariable Integer favNoteId) throws Exception;


    @GetMapping("/fav-note")
    @PreAuthorize(Constants.ROLE_USER)
    public ResponseEntity<?> userFavouritesNote() throws Exception;


    @GetMapping("/copy/{id}")
    @PreAuthorize(Constants.ROLE_USER)
    public ResponseEntity<?> copyNotes(@PathVariable Integer id) throws Exception;

}
