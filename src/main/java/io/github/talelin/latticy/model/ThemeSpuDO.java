package io.github.talelin.latticy.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.talelin.latticy.model.BaseModel;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author generator@TaleLin
 * @since 2020-06-03
 */
@Data
@TableName("theme_spu")
public class ThemeSpuDO {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer themeId;

    private Integer spuId;

}
