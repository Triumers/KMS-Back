package org.triumers.kmsback.post.command.domain.aggregate.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class CmdRequestPostAI {
    private String type;
    private String content;
}
