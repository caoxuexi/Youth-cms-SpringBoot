package io.github.talelin.latticy.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.talelin.autoconfigure.validator.Enum;
import io.github.talelin.latticy.common.enumeration.CouponTypeEnum;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author TaleLin
 */
@Data
public class CouponDTO extends CouponTemplateDTO {

    @Positive
    @NotNull
    private Integer activityId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    @NotNull
    private Date startTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    @NotNull
    private Date endTime;

}
