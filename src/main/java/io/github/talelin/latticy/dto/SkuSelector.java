package io.github.talelin.latticy.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * @author TaleLin
 */
@Data
public class SkuSelector {

    @Positive
    @NotNull
    private Integer keyId;

    @Positive
    @NotNull
    private Integer valueId;

}
