package io.github.talelin.latticy.controller.v1;


import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.talelin.core.annotation.GroupRequired;
import io.github.talelin.core.annotation.LoginRequired;
import io.github.talelin.core.annotation.PermissionMeta;
import io.github.talelin.core.annotation.PermissionModule;
import io.github.talelin.latticy.common.mybatis.Page;
import io.github.talelin.latticy.common.util.PageUtil;
import io.github.talelin.latticy.dto.SpuDTO;
import io.github.talelin.latticy.model.SpuDO;
import io.github.talelin.latticy.model.SpuDetailDO;
import io.github.talelin.latticy.service.SpuService;
import io.github.talelin.latticy.vo.CreatedVO;
import io.github.talelin.latticy.vo.DeletedVO;
import io.github.talelin.latticy.vo.PageResponseVO;
import io.github.talelin.latticy.vo.UpdatedVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.util.List;

/**
 * @author generator@TaleLin
 * @since 2020-05-23
 */
@RestController
@RequestMapping("/v1/spu")
@Validated
@PermissionModule("SPU")
public class SpuController {

    @Autowired
    private SpuService spuService;

    @PostMapping("")
    @PermissionMeta("创建SPU")
    @GroupRequired
    public CreatedVO create(@RequestBody @Validated SpuDTO dto) {
        this.spuService.create(dto);
        return new CreatedVO();
    }

    @PutMapping("/{id}")
    @PermissionMeta("更新SPU")
    @GroupRequired
    public UpdatedVO update(@RequestBody @Validated SpuDTO dto,
                            @PathVariable @Positive(message = "{id.positive}") Integer id) {
        spuService.update(dto, id);
        return new UpdatedVO();
    }

    @DeleteMapping("/{id}")
    @PermissionMeta("删除SPU")
    @GroupRequired
    public DeletedVO delete(@PathVariable @Positive(message = "{id.positive}") Integer id) {
        spuService.delete(id);
        return new DeletedVO();
    }

    @GetMapping("/{id}/detail")
    @LoginRequired
    public SpuDetailDO getDetail(@PathVariable(value = "id") @Positive Integer id) {
        SpuDetailDO detail = this.spuService.getDetail(id);
        return detail;
    }

    @GetMapping("/key")
    @LoginRequired
    public List<Integer> getSpecKeys(@RequestParam(name = "id") @Positive(message = "{id}") Integer id) {
        return spuService.getBaseMapper().getSpecKeys(id);
    }

    @GetMapping("/page")
    @LoginRequired
    public PageResponseVO<SpuDO> page(
            @RequestParam(name = "count", required = false, defaultValue = "10")
            @Min(value = 1, message = "{page.count.min}")
            @Max(value = 30, message = "{page.count.max}") Integer count,
            @RequestParam(name = "page", required = false, defaultValue = "0")
            @Min(value = 0, message = "{page.number.min}") Integer page
    ) {
        Page<SpuDO> pager = new Page<>(page, count);
        IPage<SpuDO> paging = spuService.getBaseMapper().selectPage(pager, null);
        return PageUtil.build(paging);
    }

    @GetMapping("/list")
    @LoginRequired
    public List<SpuDO> getList() {
        return spuService.list();
    }

}
