package com.raiTech.controller;

import com.raiTech.dto.FavouriteNoteDto;
import com.raiTech.dto.NotesDto;
import com.raiTech.dto.NotesResponse;
import com.raiTech.entity.FavouriteNote;
import com.raiTech.entity.FileDetails;
import com.raiTech.entity.Notes;
import com.raiTech.service.add.NotesService;
import com.raiTech.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notes")
public class NotesController {

    @Autowired
    private NotesService notesService;

    @PostMapping("/save")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?>saveNotes(@RequestParam String notesDto, @RequestParam(required = false) MultipartFile file) throws Exception {
        Boolean saveNote=notesService.saveNotes(notesDto,file);
        if(saveNote){
           return CommonUtil.createBuildResponseMessage("Notes Saved Success", HttpStatus.CREATED);
        }
        return CommonUtil.createBuildResponseMessage("Notes not Saved", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?>getAllNotes(){
        List<NotesDto> notes=notesService.getAllNotes();
        if(CollectionUtils.isEmpty(notes)){
            return ResponseEntity.noContent().build();
        }
        return CommonUtil.createBuildResponse(notes, HttpStatus.OK);
    }

    @GetMapping("/download/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<?>downLoadFile(@PathVariable Integer id) throws Exception{

        FileDetails fileDetails=notesService.getFileDetails(id);
        byte[] data=notesService.downLoadFile(fileDetails);
        HttpHeaders headers=new HttpHeaders();
        String contentType=CommonUtil.getContentType(fileDetails.getOriginalFileName());
        headers.setContentType(MediaType.parseMediaType(contentType));
        headers.setContentDispositionFormData("attachment", fileDetails.getOriginalFileName());
        return  ResponseEntity.ok().headers(headers).body(data);

    }
    @GetMapping("/user-notes")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?>getAllNotesByUser(@RequestParam(name = "pageNo",defaultValue = "0") Integer pageNo,
                                                  @RequestParam(name = "pageSize",defaultValue = "10") Integer pageSize) throws Exception{
        Integer userId=CommonUtil.getLoggedInUser().getId();
        NotesResponse notes=notesService.getAllNotesByUser(userId,pageNo,pageSize);
        if(ObjectUtils.isEmpty(notes)){
            return ResponseEntity.noContent().build();
        }
        return CommonUtil.createBuildResponse(notes, HttpStatus.OK);

    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> deleteNotes(@PathVariable Integer id) throws Exception{

        notesService.softDeleteNotes(id);
        return CommonUtil.createBuildResponseMessage("Notes Deleted Success", HttpStatus.OK);
    }
    @GetMapping("/restore/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> restoreNotes(@PathVariable Integer id) throws Exception{

        notesService.softRestoreNotes(id);
        return CommonUtil.createBuildResponseMessage("Notes Restore Success", HttpStatus.OK);
    }
    @GetMapping("/recycle-bin")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getUserRecycleBinNotes() throws Exception{
        Integer userId=CommonUtil.getLoggedInUser().getId();
        List<NotesDto> notes=notesService.getUserRecycleBinNotes(userId);
        if (ObjectUtils.isEmpty(notes)) {
            return CommonUtil.createBuildResponseMessage("Notes not available in Recycle Bin", HttpStatus.OK);
        }
        return CommonUtil.createBuildResponse(notes, HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> hardDeleteNotes(@PathVariable Integer id) throws Exception{
        notesService.hardDeleteNotes(id);
        return CommonUtil.createBuildResponseMessage("Notes Deleted Success", HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> emptyRecycleBin() throws Exception{
        int userId=CommonUtil.getLoggedInUser().getId();
        notesService.emptyRecycleBin(userId);
        return CommonUtil.createBuildResponseMessage("Notes Deleted Success", HttpStatus.OK);
    }
    @GetMapping("/fav/{noteId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> favouritesNote(@PathVariable Integer noteId) throws Exception{
        notesService.favouriteNotes(noteId);
        return CommonUtil.createBuildResponseMessage("Notes added to Favourite", HttpStatus.CREATED);
    }
    @DeleteMapping("/un-fav/{favNoteId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> unFavouritesNote(@PathVariable Integer favNoteId) throws Exception{
        notesService.unFavouriteNotes(favNoteId);
        return CommonUtil.createBuildResponseMessage("Removed From Favourites successfully", HttpStatus.OK);
    }
    @GetMapping("/fav-note")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> userFavouritesNote() throws Exception{
       List<FavouriteNoteDto> userFavNote=notesService.getUserFavouriteNote();
       if (CollectionUtils.isEmpty(userFavNote)){
           return ResponseEntity.noContent().build();
       }
        return CommonUtil.createBuildResponse(userFavNote, HttpStatus.OK);
    }
    @GetMapping("/copy/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> copyNotes(@PathVariable Integer id) throws Exception{
        Boolean copyNotes=notesService.copyNotes(id);
        if (copyNotes){
            return CommonUtil.createBuildResponseMessage("Notes Copied Successfully", HttpStatus.CREATED);
        }
        return CommonUtil.createErrorResponseMessage("Copy failed ! Try Again", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
