package com.system.artworkspace.exhibition.exhibitionUpdate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ExhibitionUpdate {

    private long id;

    private String title;

    private String description;
}
