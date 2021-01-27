package io.github.talelin.latticy.controller.v1;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.talelin.autoconfigure.exception.NotFoundException;
import io.github.talelin.core.annotation.GroupRequired;
import io.github.talelin.core.annotation.LoginRequired;
import io.github.talelin.core.annotation.PermissionMeta;
import io.github.talelin.core.annotation.PermissionModule;
import io.github.talelin.latticy.common.mybatis.Page;
import io.github.talelin.latticy.dto.TagDTO;
import io.github.talelin.latticy.dto.ThemeDTO;
import io.github.talelin.latticy.model.TagDO;
import io.github.talelin.latticy.model.ThemeDO;
import io.github.talelin.latticy.service.TagService;
import io.github.talelin.latticy.vo.CreatedVO;
import io.github.talelin.latticy.vo.DeletedVO;
import io.github.talelin.latticy.vo.PageResponseVO;
import io.github.talelin.latticy.vo.UpdatedVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.util.List;

/**
 * @author 曹学习
 * @description TagController 热门搜索tag返回
 * @date 2020/12/29 11:04
 */
@RestController
@RequestMapping("/v1/tag")
@PermissionModule(value = "Tag")
@Validated
public class TagController {
    @Autowired
    private TagService tagService;

    @PostMapping("")
    @PermissionMeta("创建Tag")
    @GroupRequired
    public CreatedVO create(@Validated @RequestBody TagDTO dto) {
        TagDO tag = new TagDO();
        BeanUtils.copyProperties(dto, tag);
        tagService.save(tag);
        return new CreatedVO();
    }

    @PutMapping("/{id}")
    @PermissionMeta(value = "更新Tag")
    @GroupRequired
    public UpdatedVO update(@RequestBody @Validated TagDTO dto,
                            @PathVariable @Positive Integer id) {
        tagService.update(dto, id);
        return new UpdatedVO();
    }

    @DeleteMapping("/{id}")
    @PermissionMeta("删除Tag")
    @GroupRequired
    public DeletedVO delete(@PathVariable @Positive(message = "{id.positive}") Integer id) {
        TagDO theme = tagService.getById(id);
        if (theme == null) {
            throw new NotFoundException(30000);
        }
        tagService.getBaseMapper().deleteById(id);
        return new DeletedVO();
    }

    @GetMapping("/{id}")
    @LoginRequired
    @PermissionMeta(value = "查询Tag")
    public TagDO get(@PathVariable(value = "id") @Positive(message = "{id.positive}") Integer id) {
        TagDO theme = tagService.getById(id);
        if (theme == null) {
            throw new NotFoundException(30000);
        }
        return theme;
    }

    @GetMapping("/page")
    @LoginRequired
    public PageResponseVO<TagDO> page(
            @RequestParam(name = "count", required = false, defaultValue = "10")
            @Min(value = 1, message = "{page.count.min}")
            @Max(value = 30, message = "{page.count.max}") Integer count,
            @RequestParam(name = "page", required = false, defaultValue = "0")
            @Min(value = 0, message = "{page.number.min}") Integer page
    ) {
        Page<TagDO> pager = new Page<>(page, count);
        IPage<TagDO> paging = tagService.getBaseMapper().selectPage(pager, null);
        return new PageResponseVO<>(paging.getTotal(), paging.getRecords(), paging.getCurrent(), paging.getSize());
    }
}
