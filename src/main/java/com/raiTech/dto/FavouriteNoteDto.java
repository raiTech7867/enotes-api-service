package com.raiTech.dto;

import com.raiTech.entity.Notes;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FavouriteNoteDto {

    private Integer id;
    private NotesDto note;
    private Integer userId;

}
