package io.github.talelin.latticy.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @author 曹学习
 * @description TagDTO
 * @date 2021/1/26 22:04
 */
@Data
public class TagDTO {

    @NotBlank
    @Length(min = 1, max = 60)
    private String title;

    @Length(min = 1, max = 255)
    private String description;

    @NotBlank
    @Length(min = 1, max = 30)
    private Integer highlight;

    @Length(min = 1, max = 30)
    private String type;
}
