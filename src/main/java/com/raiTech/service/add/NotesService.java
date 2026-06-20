package com.raiTech.service.add;

import com.raiTech.dto.FavouriteNoteDto;
import com.raiTech.dto.NotesDto;
import com.raiTech.dto.NotesResponse;
import com.raiTech.entity.FavouriteNote;
import com.raiTech.entity.FileDetails;
import com.raiTech.exception.ResourceNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface NotesService {

    public Boolean saveNotes(String notes, MultipartFile file) throws Exception;
    public List<NotesDto> getAllNotes();

    byte[] downLoadFile(FileDetails fileDetails) throws Exception;

    FileDetails getFileDetails(Integer id) throws Exception;

    NotesResponse getAllNotesByUser(Integer userId, Integer pageNo, Integer pageSize);

    void softDeleteNotes(Integer id) throws Exception;

    void softRestoreNotes(Integer id) throws Exception;

    List<NotesDto> getUserRecycleBinNotes(Integer userId);

    void hardDeleteNotes(Integer id) throws Exception;

    void emptyRecycleBin(int userId);

    public void favouriteNotes(Integer noteId) throws Exception;

    public void unFavouriteNotes(Integer noteId) throws Exception;

    public List<FavouriteNoteDto> getUserFavouriteNote();


    Boolean copyNotes(Integer id) throws Exception;
}
