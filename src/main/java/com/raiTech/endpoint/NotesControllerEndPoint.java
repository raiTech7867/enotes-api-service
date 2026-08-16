package com.raiTech.endpoint;

import com.raiTech.dto.NotesDto;
import com.raiTech.dto.NotesRequest;
import com.raiTech.util.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.raiTech.util.Constants.*;


@Tag(name = "NotesApi",description = "All the notes  Api")
@RequestMapping("/api/v1/notes")
public interface NotesControllerEndPoint {

    @Operation(summary = "Save Notes ",tags = {"NotesApi"},description = "User can save the notes")
    @PostMapping(value = "/save",consumes = "multipart/form-data")
    @PreAuthorize(Constants.ROLE_USER)
    public ResponseEntity<?> saveNotes(@RequestParam @Parameter(description = "Json String Notes",required =
                                                   true,content = @Content(schema = @Schema(implementation = NotesRequest.class)))
                                           String notesDto,
                                       @RequestParam(required = false) MultipartFile file) throws Exception;

    @Operation(summary = "Get All Notes ",tags = {"NotesApi"},description = "Admin can get all the notes")
    @GetMapping("/")
    @PreAuthorize(Constants.ROLE_ADMIN)
    public ResponseEntity<?>getAllNotes();

    @Operation(summary = "Search Notes ",tags = {"NotesApi"},description = "User can search the notes")
    @GetMapping("/search")
    @PreAuthorize(Constants.ROLE_USER)
    public ResponseEntity<?> searchNotes(
            @RequestParam("keyword") String keyword,
            @RequestParam(defaultValue = DEFAULT_PAGE_NO) int pageNo,
            @RequestParam(defaultValue =  DEFAULT_PAGE_SIZE) int pageSize);


    @Operation(summary = "Download File  ",tags = {"NotesApi"},description = "Admin or User can download the file")
    @GetMapping("/download/{id}")
    @PreAuthorize(ROLE_ADMIN_USER)
    public ResponseEntity<?>downLoadFile(@PathVariable Integer id) throws Exception;


    @Operation(summary = "Save Notes ",tags = {"NotesApi"},description = "User can save the notes")
    @GetMapping("/user-notes")
    @PreAuthorize(Constants.ROLE_USER)
    public ResponseEntity<?>getAllNotesByUser(@RequestParam(name = "pageNo",defaultValue = DEFAULT_PAGE_NO) Integer pageNo,
                                              @RequestParam(name = "pageSize",defaultValue = DEFAULT_PAGE_SIZE) Integer pageSize) throws Exception;

    @Operation(summary = "Delete Notes ",tags = {"NotesApi"},description = "User can Delete the notes")
    @GetMapping("/delete/{id}")
    @PreAuthorize(Constants.ROLE_USER)
    public ResponseEntity<?> deleteNotes(@PathVariable Integer id) throws Exception;

    @Operation(summary = "Restore Notes ",tags = {"NotesApi"},description = "User can Restore the notes")
    @GetMapping("/restore/{id}")
    @PreAuthorize(Constants.ROLE_USER)
    public ResponseEntity<?> restoreNotes(@PathVariable Integer id) throws Exception;

    @Operation(summary = "Recycle Notes ",tags = {"NotesApi"},description = "User can get recycle the notes")
    @GetMapping("/recycle-bin")
    @PreAuthorize(Constants.ROLE_USER)
    public ResponseEntity<?> getUserRecycleBinNotes() throws Exception;

    @Operation(summary = "Delete Notes By Id",tags = {"NotesApi"},description = "User can Delete the notes by id")
    @DeleteMapping("/delete/{id}")
    @PreAuthorize(Constants.ROLE_USER)
    public ResponseEntity<?> hardDeleteNotes(@PathVariable Integer id) throws Exception;

    @Operation(summary = "Empty Recycle Notes ",tags = {"NotesApi"},description = "User can Empty Recycle notes")
    @DeleteMapping("/delete")
    @PreAuthorize(Constants.ROLE_USER)
    public ResponseEntity<?> emptyRecycleBin() throws Exception;

    @Operation(summary = "Make Favourite Notes ",tags = {"NotesApi"},description = "User can make notes favourite")
    @GetMapping("/fav/{noteId}")
    @PreAuthorize(Constants.ROLE_USER)
    public ResponseEntity<?> favouritesNote(@PathVariable Integer noteId) throws Exception;

    @Operation(summary = "Make UnFavourite Notes ",tags = {"NotesApi"},description = "User can make  unFavourite")
    @DeleteMapping("/un-fav/{favNoteId}")
    @PreAuthorize(Constants.ROLE_USER)
    public ResponseEntity<?> unFavouritesNote(@PathVariable Integer favNoteId) throws Exception;

    @Operation(summary = "Get Favourite Notes ",tags = {"NotesApi"},description = "User can get favourite notes")
    @GetMapping("/fav-note")
    @PreAuthorize(Constants.ROLE_USER)
    public ResponseEntity<?> userFavouritesNote() throws Exception;

    @Operation(summary = "Copy Favourite Notes ",tags = {"NotesApi"},description = "User can copy otes")
    @GetMapping("/copy/{id}")
    @PreAuthorize(Constants.ROLE_USER)
    public ResponseEntity<?> copyNotes(@PathVariable Integer id) throws Exception;

}
