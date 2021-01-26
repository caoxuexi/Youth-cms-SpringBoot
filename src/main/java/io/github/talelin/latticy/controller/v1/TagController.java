package io.github.talelin.latticy.controller.v1;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.talelin.autoconfigure.exception.NotFoundException;
import io.github.talelin.core.annotation.LoginRequired;
import io.github.talelin.latticy.common.mybatis.Page;
import io.github.talelin.latticy.common.util.PageUtil;
import io.github.talelin.latticy.model.TagDO;
import io.github.talelin.latticy.model.ThemeDO;
import io.github.talelin.latticy.service.TagService;
import io.github.talelin.latticy.vo.PageResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("tag")
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping("/{id}")
    @LoginRequired
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
        Page<ThemeDO> pager = new Page<>(page, count);
        IPage<ThemeDO> paging = tagService.getBaseMapper().selectPage(pager, null);
        return PageUtil.build(paging);
    }

//    @GetMapping("")
//    public List<Tag> getHotSearchTag(@PathVariable Integer isHot) throws Exception{
//        List<Tag> hotSearchTag = this.tagService.getHotSearchTag(isHot);
//        return hotSearchTag;
//    }

}
