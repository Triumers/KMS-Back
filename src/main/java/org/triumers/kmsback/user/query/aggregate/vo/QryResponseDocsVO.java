package org.triumers.kmsback.user.query.aggregate.vo;

import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class QryResponseDocsVO {
    String message;
    String docsType;
    List<Map<String, String>> docsInfoList;
}
