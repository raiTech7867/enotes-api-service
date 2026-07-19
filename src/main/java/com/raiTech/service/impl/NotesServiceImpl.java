package com.raiTech.service.impl;

import com.raiTech.dto.FavouriteNoteDto;
import com.raiTech.dto.NotesDto;
import com.raiTech.dto.NotesResponse;
import com.raiTech.entity.FavouriteNote;
import com.raiTech.entity.FileDetails;
import com.raiTech.entity.Notes;
import com.raiTech.exception.ResourceNotFoundException;
import com.raiTech.repository.Categoryrepository;
import com.raiTech.repository.FavouriteNoteRepository;
import com.raiTech.repository.FileRepository;
import com.raiTech.repository.NotesRepository;
import com.raiTech.service.add.NotesService;
import com.raiTech.util.CommonUtil;
import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;
import tools.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class NotesServiceImpl implements NotesService {
    @Autowired
    private NotesRepository notesRepository;

    @Autowired
    private FavouriteNoteRepository favouriteNoteRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private Categoryrepository categoryRepo;

    @Value("${file.upload.path}")
    private String uploadPath;

    @Autowired
    private FileRepository fileRepository;

    @Override
    public Boolean saveNotes(String notes, MultipartFile file) throws Exception {

        //System.out.println("Notes JSON = " + notes);

        ObjectMapper objectMapper = new ObjectMapper();

        NotesDto notesDto = objectMapper.readValue(notes, NotesDto.class);

        notesDto.setIsDeleted(false);
        notesDto.setDeletedOn(null);

        if (!ObjectUtils.isEmpty(notesDto.getId())) {
            updateNotes(notesDto, file);
        }

        checkCategoryExist(notesDto.getCategory());

        Notes notesMap = mapper.map(notesDto, Notes.class);


        FileDetails fileDetails = saveFileDetails(file);

        if (!ObjectUtils.isEmpty(fileDetails)) {

            notesMap.setFileDetails(fileDetails);
        } else {
            if (ObjectUtils.isEmpty(notesDto.getId())) {
                notesMap.setFileDetails(null);
            }

        }
        Notes savedNote = notesRepository.save(notesMap);
        if (!ObjectUtils.isEmpty(savedNote)) {
            return true;
        } else {
            return false;
        }
    }

    private void updateNotes(NotesDto notesDto, MultipartFile file) throws Exception {

        Notes existNote = notesRepository.findById(notesDto.getId()).orElseThrow(() -> new ResourceNotFoundException("Invalid Notes Id"));
        if (ObjectUtils.isEmpty(file)) {
            notesDto.setFileDetails(mapper.map(existNote.getFileDetails(), NotesDto.FilesDto.class));
        }
    }

    private FileDetails saveFileDetails(MultipartFile file) throws IOException {
        if (!ObjectUtils.isEmpty(file) && !file.isEmpty()) {

            String originalFileName = file.getOriginalFilename();
            String extension = FilenameUtils.getExtension(originalFileName);
            List<String> extensionAllow = Arrays.asList("pdf", "xlsx", "jpg", "png");
            if (!extensionAllow.contains(extension)) {
                throw new IllegalArgumentException("invalid file format ! Upload only .pdf, .xlsx, .jpg, .png");
            }


            String rndString = UUID.randomUUID().toString();
            String uploadFileNam = rndString + "." + extension;

            File saveFile = new File(uploadPath);
            if (!saveFile.exists()) {
                saveFile.mkdir();
            }
            //Path:enotesapiservice/notes/java.pdf
            String storePath = uploadPath.concat(uploadFileNam);

            //Upload File
            long upload = Files.copy(file.getInputStream(), Paths.get(storePath));
            if (upload != 0) {
                FileDetails fileDetails = new FileDetails();

                fileDetails.setOriginalFileName(originalFileName);
                fileDetails.setDisplayFileName(getDisplayName(originalFileName));
                fileDetails.setUploadFileName(uploadFileNam);
                fileDetails.setFileSize(file.getSize());
                fileDetails.setPath(storePath);
                FileDetails saveFileDetails = fileRepository.save(fileDetails);
                return saveFileDetails;
            }
        }
        return null;
    }

    private String getDisplayName(String originalFileName) {

        //java_programming_tutorials.pdf
        //java_programming.pdf
        String extension = FilenameUtils.getExtension(originalFileName);
        String fileName = FilenameUtils.removeExtension(originalFileName);
        if (fileName.length() > 8) {
            fileName = fileName.substring(0, 7);
        }
        fileName = fileName + "." + extension;
        return fileName;
    }

    private void checkCategoryExist(NotesDto.CategoryDto category) throws Exception {
        categoryRepo.findById(category.getId()).orElseThrow(() -> new ResourceNotFoundException("Category id Invalid"));
    }

    @Override
    public List<NotesDto> getAllNotes() {

        return notesRepository.findAll()
                .stream()
                .map(note -> {

                    NotesDto dto = mapper.map(note, NotesDto.class);

                    if (note.getFileDetails() != null) {

                        NotesDto.FilesDto filesDto =
                                mapper.map(note.getFileDetails(),
                                        NotesDto.FilesDto.class);

                        dto.setFileDetails(filesDto);
                    }

                    return dto;
                })
                .toList();
    }

    @Override
    public byte[] downLoadFile(FileDetails fileDetails) throws Exception {


        InputStream io = new FileInputStream(fileDetails.getPath());

        return StreamUtils.copyToByteArray(io);
    }

    @Override
    public FileDetails getFileDetails(Integer id) throws Exception {
        FileDetails fileDetails = fileRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("File is not available"));
        return fileDetails;
    }

    @Override
    public NotesResponse getAllNotesByUser(Integer userId, Integer pageNo, Integer pageSize) {

        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Notes> pageNotes = notesRepository.findByCreatedByAndIsDeletedFalse(userId, pageable);
        List<NotesDto> notesDto = pageNotes.get().map(n -> mapper.map(n, NotesDto.class)).toList();

        NotesResponse notes = NotesResponse.builder()
                .notes(notesDto)
                .pageNo(pageNotes.getNumber())
                .pageSize(pageNotes.getSize())
                .totalElements(pageNotes.getTotalElements())
                .totalPages(pageNotes.getTotalPages())
                .isFirst(pageNotes.isFirst())
                .isLast(pageNotes.isLast())
                .build();

        return notes;
    }

    @Override
    public void softDeleteNotes(Integer id) throws Exception {
        Notes notes = notesRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Notes id invalid! not found Exception"));
        notes.setIsDeleted(true);
        notes.setDeletedOn(LocalDateTime.now());
        notesRepository.save(notes);
    }

    @Override
    public void softRestoreNotes(Integer id) throws Exception {
        Notes notes = notesRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Notes id invalid! not found Exception"));
        notes.setIsDeleted(false);
        notes.setDeletedOn(null);
        notesRepository.save(notes);
    }

    @Override
    public List<NotesDto> getUserRecycleBinNotes(Integer userId) {

        List<Notes> recycleNotes = notesRepository.findByCreatedByAndIsDeletedTrue(userId);
        List<NotesDto> notesDtoList = recycleNotes.stream().map(note -> mapper.map(note, NotesDto.class)).toList();
        return notesDtoList;
    }

    @Override
    public void hardDeleteNotes(Integer id) throws Exception {
        Notes notes = notesRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Notes id invalid! not found Exception"));

        if (notes.getIsDeleted()) {
            notesRepository.delete(notes);
        } else {
            throw new IllegalArgumentException("Sorry you can't hard delete this note");
        }

    }

    @Override
    public void emptyRecycleBin(int userId) {
        List<Notes> recycleNotes = notesRepository.findByCreatedByAndIsDeletedTrue(userId);
        if (!CollectionUtils.isEmpty(recycleNotes)) {
            notesRepository.deleteAll(recycleNotes);
        } else {
            throw new IllegalArgumentException("Sorry Recycle View is Empty");
        }


    }

    @Override
    public void favouriteNotes(Integer noteId) throws Exception {
        int userId = 1;
        Notes notes = notesRepository.findById(noteId).orElseThrow(() -> new ResourceNotFoundException("Notes id invalid! not found"));
        FavouriteNote favouriteNote = FavouriteNote.builder().note(notes).userId(userId).build();
        favouriteNoteRepository.save(favouriteNote);
    }

    @Override
    public void unFavouriteNotes(Integer favouriteNoteId) throws Exception {
        FavouriteNote favNote = favouriteNoteRepository.findById(favouriteNoteId).orElseThrow(() -> new ResourceNotFoundException("Favourite Notes id invalid! not found"));
        favouriteNoteRepository.delete(favNote);
    }

    @Override
    public List<FavouriteNoteDto> getUserFavouriteNote() {
        int userId = CommonUtil.getLoggedInUser().getId();
        List<FavouriteNote> favouriteNotes = favouriteNoteRepository.findByUserId(userId);

        return favouriteNotes.stream().map(note -> mapper.map(note, FavouriteNoteDto.class)).toList();
    }

    @Override
    public Boolean copyNotes(Integer id) throws Exception {
        Notes notes = notesRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Notes id invalid! not found"));
        Notes copyNote = Notes.builder().title(notes.getTitle()).description(notes.getDescription()).category(notes.getCategory())
                .isDeleted(false)
                .fileDetails(null)
                .build();
        Notes saveCopyNotes=  notesRepository.save(copyNote);
        if (!ObjectUtils.isEmpty(saveCopyNotes)) {
            return true;
        }
        return false;
    }
}
