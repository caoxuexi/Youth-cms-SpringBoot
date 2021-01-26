package io.github.talelin.latticy.model;

import lombok.Data;

import java.util.List;

/**
 * @author TaleLin
 */
@Data
public class ActivityDetailDO extends ActivityDO {

    private List<Integer> couponIds;

}
