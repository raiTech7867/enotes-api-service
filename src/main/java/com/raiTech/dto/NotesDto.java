package com.raiTech.dto;

import com.raiTech.entity.Category;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NotesDto {
    private Integer id;
    private String title;
    private String description;
    private CategoryDto category;
    private Integer createdBy;
    @CreatedDate
    @Column(updatable = false)
    private Date createdOn;
    @LastModifiedBy
    @Column(insertable = false)
    private Integer updatedBy;
    @LastModifiedDate
    @Column(insertable  = false)
    private Date updatedOn;
    private FilesDto fileDetails;
    private Boolean isDeleted;
    private LocalDateTime deletedOn;
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class CategoryDto{
        private Integer id;
        private String name;
    }
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class FilesDto{
        private Integer id;
        private String originalFileName;
        private String displayFileName;
    }
}
