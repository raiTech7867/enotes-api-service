package com.raiTech.service.add;

import com.raiTech.dto.NotesDto;
import com.raiTech.entity.FileDetails;
import com.raiTech.exception.ResourceNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface NotesService {

    public Boolean saveNotes(String notes, MultipartFile file) throws Exception;
    public List<NotesDto> getAllNotes();

    byte[] downLoadFile(FileDetails fileDetails) throws Exception;

    FileDetails getFileDetails(Integer id) throws Exception;
}
