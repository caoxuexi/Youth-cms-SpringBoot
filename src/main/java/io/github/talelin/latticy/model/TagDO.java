package io.github.talelin.latticy.model;

import io.github.talelin.latticy.model.BaseModel;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author generator@TaleLin
 * @since 2021-01-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("tag")
public class TagDO extends BaseModel {


    /**
     * 中文限制6个，英文限制12个，由逻辑层控制
     */
    private String title;

    private String description;

    private Integer highlight;

    private Integer type;
}
