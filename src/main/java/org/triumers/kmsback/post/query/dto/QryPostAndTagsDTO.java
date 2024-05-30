package org.triumers.kmsback.post.query.dto;

import lombok.Data;
import org.triumers.kmsback.user.command.Application.dto.CmdEmployeeDTO;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class QryPostAndTagsDTO {
    private Integer id;
    private String title;
    private String content;
    private String postImg;
    private LocalDateTime createdAt;
    private CmdEmployeeDTO author;
    private Integer originId;
    private Integer recentId;
    private Integer tabRelationId;
    private Integer categoryId;
    private List<String> tags;

    private List<QryPostAndTagsDTO> history;
    private List<CmdEmployeeDTO> participants;
    private List<CmdEmployeeDTO> likeList;

    public QryPostAndTagsDTO() {
    }

    public QryPostAndTagsDTO(Integer id, String title, String content, String postImg, LocalDateTime createdAt,
                             CmdEmployeeDTO author, Integer originId, Integer recentId, Integer tabRelationId,
                             Integer categoryId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.postImg = postImg;
        this.createdAt = createdAt;
        this.author = author;
        this.originId = originId;
        this.recentId = recentId;
        this.tabRelationId = tabRelationId;
        this.categoryId = categoryId;
    }
}
