package org.triumers.kmsback.post.command.domain.aggregate.vo;

import java.time.LocalDate;
import java.util.List;

public class CmdRequestPost {
    private String title;
    private String content;
    private LocalDate createdAt;
    private LocalDate deletedAt;
    private Integer authorId;
    private Integer originId;
    private Integer recentId;
    private Integer tabRelationId;

    private List<CmdTagVO> tags;

}
