package io.github.talelin.latticy.model;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.talelin.latticy.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author generator@TaleLin
 * @since 2020-05-27
 */
@Data
@TableName("category")
public class CategoryDO {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;

    private String description;

    private Integer isRoot;

    private Integer parentId;

    private String img;

    @TableField(value = "`index`" )
    private Integer index;

    private Integer online;

    private Integer level;

    @JsonIgnore
    private Date createTime;

    @JsonIgnore
    private Date updateTime;

    @TableLogic
    @JsonIgnore
    private Date deleteTime;


}
