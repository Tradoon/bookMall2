package com.tradoon.bookMall.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

/**
 * author:tradoon
 * desciption:
 * date:2022/ / /
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CmsSubjectProductRelation extends PmsProductCommonInfo implements Serializable {
//    private Long id;

    private Long subjectId;

//    private Long productId;

    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CmsSubjectProductRelation sb = (CmsSubjectProductRelation) o;
        return  subjectId==sb.getSubjectId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(subjectId);
    }

}
