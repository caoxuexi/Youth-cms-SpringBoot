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
@TableName("theme")
public class ThemeDO {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @JsonIgnore
    private Date createTime;

    @JsonIgnore
    private Date updateTime;

    @TableLogic
    @JsonIgnore
    private Date deleteTime;

    private String title;

    private String description;

    private String name;

    private String tplName;

    private String entranceImg;

    private String extend;

    private String internalTopImg;

    private String titleImg;

    private Integer online;


}
