package org.triumers.kmsback.post.command.domain.aggregate.vo;

import java.time.LocalDateTime;
import java.util.List;

public class CmdRequestPost {
    private String title;
    private String content;
    private String postImg;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;
    private Integer authorId;
    private Integer originId;
    private Integer recentId;
    private Integer tabRelationId;

    private List<CmdTagVO> tags;

}
