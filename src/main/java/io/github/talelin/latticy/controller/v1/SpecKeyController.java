package io.github.talelin.latticy.controller.v1;


import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.talelin.core.annotation.GroupRequired;
import io.github.talelin.core.annotation.LoginRequired;
import io.github.talelin.core.annotation.PermissionMeta;
import io.github.talelin.core.annotation.PermissionModule;
import io.github.talelin.latticy.bo.SpecKeyAndItemsBO;
import io.github.talelin.latticy.common.mybatis.Page;
import io.github.talelin.latticy.common.util.PageUtil;
import io.github.talelin.latticy.dto.SpecKeyDTO;
import io.github.talelin.latticy.model.SpecKeyDO;
import io.github.talelin.latticy.service.SpecKeyService;
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
 * @since 2020-05-24
 */
@RestController
@RequestMapping("/v1/spec-key")
@PermissionModule("规格名")
@Validated
public class SpecKeyController {

    @Autowired
    private SpecKeyService specKeyService;

    @PostMapping("")
    @PermissionMeta(value = "创建规格名")
    @GroupRequired
    public CreatedVO create(@Validated @RequestBody SpecKeyDTO dto) {
        specKeyService.create(dto);
        return new CreatedVO();
    }

    @PutMapping("/{id}")
    @PermissionMeta(value = "更新规格名")
    @GroupRequired
    public UpdatedVO update(
            @Validated @RequestBody SpecKeyDTO dto,
            @PathVariable @Positive(message = "{id.positive}") Integer id) {
        specKeyService.update(dto, id);
        return new UpdatedVO();
    }

    @DeleteMapping("/{id}")
    @PermissionMeta(value = "删除规格名")
    @GroupRequired
    public DeletedVO delete(@PathVariable @Positive(message = "{id.positive}") Integer id) {
        specKeyService.delete(id);
        return new DeletedVO();
    }

    @GetMapping("/{id}/detail")
    @LoginRequired
    public SpecKeyAndItemsBO detail(@PathVariable @Positive(message = "{id}") Integer id) {
        SpecKeyAndItemsBO specKeyAndItems = specKeyService.getKeyAndValuesById(id);
        return specKeyAndItems;
    }

    @GetMapping("/by/spu/{id}")
    public List<SpecKeyDO> getBySpuId(@PathVariable(value = "id") @Positive Integer spuId) {
        return this.specKeyService.getBySpuId(spuId);
    }

    @GetMapping("/page")
    public PageResponseVO<SpecKeyDO> page(
            @RequestParam(name = "count", required = false, defaultValue = "10")
            @Min(value = 1, message = "{page.count.min}")
            @Max(value = 30, message = "{page.count.max}") Integer count,
            @RequestParam(name = "page", required = false, defaultValue = "0")
            @Min(value = 0, message = "{page.number.min}") Integer page
    ) {
        Page<SpecKeyDO> pager = new Page<>(page, count);
        IPage<SpecKeyDO> paging = specKeyService.getBaseMapper().selectPage(pager, null);
        return PageUtil.build(paging);
    }

    @GetMapping("/list")
    @LoginRequired
    public List<SpecKeyDO> getList() {
        return specKeyService.list();
    }

}
