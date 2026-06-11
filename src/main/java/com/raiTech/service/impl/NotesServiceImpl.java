package com.raiTech.service.impl;

import com.raiTech.dto.NotesDto;
import com.raiTech.dto.NotesResponse;
import com.raiTech.entity.FileDetails;
import com.raiTech.entity.Notes;
import com.raiTech.exception.ResourceNotFoundException;
import com.raiTech.repository.Categoryrepository;
import com.raiTech.repository.FileRepository;
import com.raiTech.repository.NotesRepository;
import com.raiTech.service.add.NotesService;
import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
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
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class NotesServiceImpl implements NotesService {
    @Autowired
    private NotesRepository notesRepository;

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



        checkCategoryExist(notesDto.getCategory());

        Notes notesMap = mapper.map(notesDto, Notes.class);


        FileDetails fileDetails = saveFileDetails(file);

        if (!ObjectUtils.isEmpty(fileDetails)) {

            notesMap.setFileDetails(fileDetails);
        }else {
            notesMap.setFileDetails(null);
        }
        Notes savedNote = notesRepository.save(notesMap);
        if (!ObjectUtils.isEmpty(savedNote)) {
            return true;
        }else {
            return false;
        }
    }

    private FileDetails saveFileDetails(MultipartFile file) throws IOException {
        if (!ObjectUtils.isEmpty(file)&&!file.isEmpty()) {

            String originalFileName=file.getOriginalFilename();
            String extension = FilenameUtils.getExtension(originalFileName);
            List<String> extensionAllow= Arrays.asList("pdf","xlsx","jpg","png");
            if (!extensionAllow.contains(extension)) {
                throw new IllegalArgumentException("invalid file format ! Upload only .pdf, .xlsx, .jpg, .png");
            }


            String rndString= UUID.randomUUID().toString();
            String uploadFileNam=rndString+"."+extension;

            File saveFile=new File(uploadPath);
            if (!saveFile.exists()) {
                saveFile.mkdir();
            }
            //Path:enotesapiservice/notes/java.pdf
            String storePath=uploadPath.concat(uploadFileNam);

            //Upload File
           long upload= Files.copy(file.getInputStream(), Paths.get(storePath));
           if (upload!=0) {
               FileDetails fileDetails = new FileDetails();

               fileDetails.setOriginalFileName(originalFileName);
               fileDetails.setDisplayFileName(getDisplayName(originalFileName));
               fileDetails.setUploadFileName(uploadFileNam);
               fileDetails.setFileSize(file.getSize());
               fileDetails.setPath(storePath);
                 FileDetails saveFileDetails=fileRepository.save(fileDetails);
                 return saveFileDetails;
           }
        }
        return null;
    }

    private String getDisplayName(String originalFileName) {

        //java_programming_tutorials.pdf
        //java_programming.pdf
        String extension= FilenameUtils.getExtension(originalFileName);
        String fileName = FilenameUtils.removeExtension(originalFileName);
        if (fileName.length()>8){
            fileName=fileName.substring(0,7);
        }
        fileName=fileName+"."+extension;
        return fileName;
    }

    private void checkCategoryExist(NotesDto.CategoryDto category) throws Exception{
          categoryRepo.findById(category.getId()).orElseThrow(()->new ResourceNotFoundException("Category id Invalid"));
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


        InputStream io=new FileInputStream(fileDetails.getPath());

        return StreamUtils.copyToByteArray(io);
    }

    @Override
    public FileDetails getFileDetails(Integer id) throws Exception {
        FileDetails fileDetails=fileRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("File is not available"));
        return fileDetails;
    }

    @Override
    public NotesResponse getAllNotesByUser(Integer userId, Integer pageNo, Integer pageSize) {

        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Notes> pageNotes=notesRepository.findByCreatedBy(userId,pageable);
        List<NotesDto> notesDto=pageNotes.get().map(n->mapper.map(n,NotesDto.class)).toList();

        NotesResponse notes=NotesResponse.builder()
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
}
