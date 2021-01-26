package io.github.talelin.latticy.controller.v1;


import io.github.talelin.autoconfigure.exception.NotFoundException;
import io.github.talelin.core.annotation.PermissionMeta;
import io.github.talelin.core.annotation.PermissionModule;
import io.github.talelin.latticy.dto.BannerItemDTO;
import io.github.talelin.latticy.model.BannerItemDO;
import io.github.talelin.latticy.service.BannerItemService;
import io.github.talelin.latticy.vo.CreatedVO;
import io.github.talelin.latticy.vo.DeletedVO;
import io.github.talelin.latticy.vo.UpdatedVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;

/**
* @author generator@TaleLin
* @since 2020-05-22
*/
@RestController
@RequestMapping("/v1/banner-item")
@PermissionModule("Banner item")
public class BannerItemController {

    @Autowired
    private BannerItemService bannerItemService;

    @PostMapping("")
    @PermissionMeta(value = "创建Banner item")
    public CreatedVO create(@Validated @RequestBody BannerItemDTO dto) {
        BannerItemDO bannerItemDO = new BannerItemDO();
        BeanUtils.copyProperties(dto, bannerItemDO);
        bannerItemService.save(bannerItemDO);
        return new CreatedVO();
    }

    @PutMapping("/{id}")
    @PermissionMeta(value = "更新Banner item")
    public UpdatedVO update(
            @PathVariable @Positive(message = "{id.positive}") Integer id,
            @Validated @RequestBody BannerItemDTO dto) {
        bannerItemService.update(dto, id);
        return new UpdatedVO();
    }

    @DeleteMapping("/{id}")
    @PermissionMeta(value = "删除Banner item")
    public DeletedVO delete(@PathVariable @Positive(message = "{id.positive}") Integer id) {
        bannerItemService.delete(id);
        return new DeletedVO();
    }

    @GetMapping("/{id}")
    @PermissionMeta(value = "查询Banner item")
    public BannerItemDO get(@PathVariable(value = "id") @Positive(message = "{id.positive}") Integer id) {
        BannerItemDO bannerItem = bannerItemService.getById(id);
        if (bannerItem == null) {
            throw new NotFoundException(20001);
        }
        return bannerItem;
    }

}
