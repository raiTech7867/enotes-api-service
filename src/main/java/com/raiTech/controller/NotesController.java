package com.raiTech.controller;

import com.raiTech.dto.FavouriteNoteDto;
import com.raiTech.dto.NotesDto;
import com.raiTech.dto.NotesResponse;
import com.raiTech.endpoint.NotesControllerEndPoint;
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
public class NotesController implements NotesControllerEndPoint {

    @Autowired
    private NotesService notesService;


    @Override
    public ResponseEntity<?>saveNotes( String notesDto,  MultipartFile file) throws Exception {
        Boolean saveNote=notesService.saveNotes(notesDto,file);
        if(saveNote){
           return CommonUtil.createBuildResponseMessage("Notes Saved Success", HttpStatus.CREATED);
        }
        return CommonUtil.createBuildResponseMessage("Notes not Saved", HttpStatus.INTERNAL_SERVER_ERROR);
    }

   @Override
    public ResponseEntity<?>getAllNotes(){
        List<NotesDto> notes=notesService.getAllNotes();
        if(CollectionUtils.isEmpty(notes)){
            return ResponseEntity.noContent().build();
        }
        return CommonUtil.createBuildResponse(notes, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> searchNotes(
             String keyword,
             int pageNo,
             int pageSize) {
        NotesResponse notes=notesService.getAllNotesBySearch(pageNo,pageSize,keyword);
        return CommonUtil.createBuildResponse(notes, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?>downLoadFile(Integer id) throws Exception{

        FileDetails fileDetails=notesService.getFileDetails(id);
        byte[] data=notesService.downLoadFile(fileDetails);
        HttpHeaders headers=new HttpHeaders();
        String contentType=CommonUtil.getContentType(fileDetails.getOriginalFileName());
        headers.setContentType(MediaType.parseMediaType(contentType));
        headers.setContentDispositionFormData("attachment", fileDetails.getOriginalFileName());
        return  ResponseEntity.ok().headers(headers).body(data);

    }

    @Override
    public ResponseEntity<?>getAllNotesByUser( Integer pageNo,
                                                  Integer pageSize) throws Exception{
        Integer userId=CommonUtil.getLoggedInUser().getId();
        NotesResponse notes=notesService.getAllNotesByUser(userId,pageNo,pageSize);
        if(ObjectUtils.isEmpty(notes)){
            return ResponseEntity.noContent().build();
        }
        return CommonUtil.createBuildResponse(notes, HttpStatus.OK);

    }

    @Override
    public ResponseEntity<?> deleteNotes( Integer id) throws Exception{

        notesService.softDeleteNotes(id);
        return CommonUtil.createBuildResponseMessage("Notes Deleted Success", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> restoreNotes( Integer id) throws Exception{

        notesService.softRestoreNotes(id);
        return CommonUtil.createBuildResponseMessage("Notes Restore Success", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getUserRecycleBinNotes() throws Exception{
        Integer userId=CommonUtil.getLoggedInUser().getId();
        List<NotesDto> notes=notesService.getUserRecycleBinNotes(userId);
        if (ObjectUtils.isEmpty(notes)) {
            return CommonUtil.createBuildResponseMessage("Notes not available in Recycle Bin", HttpStatus.OK);
        }
        return CommonUtil.createBuildResponse(notes, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> hardDeleteNotes( Integer id) throws Exception{
        notesService.hardDeleteNotes(id);
        return CommonUtil.createBuildResponseMessage("Notes Deleted Success", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> emptyRecycleBin() throws Exception{
        int userId=CommonUtil.getLoggedInUser().getId();
        notesService.emptyRecycleBin(userId);
        return CommonUtil.createBuildResponseMessage("Notes Deleted Success", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> favouritesNote( Integer noteId) throws Exception{
        notesService.favouriteNotes(noteId);
        return CommonUtil.createBuildResponseMessage("Notes added to Favourite", HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> unFavouritesNote( Integer favNoteId) throws Exception{
        notesService.unFavouriteNotes(favNoteId);
        return CommonUtil.createBuildResponseMessage("Removed From Favourites successfully", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> userFavouritesNote() throws Exception{
       List<FavouriteNoteDto> userFavNote=notesService.getUserFavouriteNote();
       if (CollectionUtils.isEmpty(userFavNote)){
           return ResponseEntity.noContent().build();
       }
        return CommonUtil.createBuildResponse(userFavNote, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> copyNotes( Integer id) throws Exception{
        Boolean copyNotes=notesService.copyNotes(id);
        if (copyNotes){
            return CommonUtil.createBuildResponseMessage("Notes Copied Successfully", HttpStatus.CREATED);
        }
        return CommonUtil.createErrorResponseMessage("Copy failed ! Try Again", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
