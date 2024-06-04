package org.triumers.kmsback.user.query.dto;

import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class QryDocsDTO {
    String docsType;
    List<Map<String, String>> docsInfoList;
}
