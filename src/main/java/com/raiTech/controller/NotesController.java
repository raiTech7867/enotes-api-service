package com.raiTech.controller;

import com.raiTech.dto.NotesDto;
import com.raiTech.service.add.NotesService;
import com.raiTech.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?>saveNotes(@RequestParam String notesDto, @RequestParam(required = false) MultipartFile file) throws Exception {
        Boolean saveNote=notesService.saveNotes(notesDto,file);
        if(saveNote){
           return CommonUtil.createBuildResponseMessage("Notes Saved Success", HttpStatus.CREATED);
        }
        return CommonUtil.createBuildResponseMessage("Notes not Saved", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/")
    public ResponseEntity<?>getAllNotes(){
        List<NotesDto> notes=notesService.getAllNotes();
        if(CollectionUtils.isEmpty(notes)){
            return ResponseEntity.noContent().build();
        }
        return CommonUtil.createBuildResponse(notes, HttpStatus.OK);
    }

}
