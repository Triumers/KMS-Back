package org.triumers.kmsback.common.translation.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PostDTO {
    private Integer id;
    private String title;
    private String content;
}
