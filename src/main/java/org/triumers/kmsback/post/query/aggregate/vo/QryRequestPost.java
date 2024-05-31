package org.triumers.kmsback.post.query.aggregate.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QryRequestPost {

    private Integer tabRelationId;
    private Integer categoryId;

    private String title;
    private String keyword;
    private List<String> tags;
    private List<Integer> tabList;

    public QryRequestPost(Integer tabRelationId, Integer categoryId) {
        this.tabRelationId = tabRelationId;
        this.categoryId = categoryId;
    }
}
