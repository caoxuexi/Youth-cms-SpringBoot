package io.github.talelin.latticy.controller.v1;


import io.github.talelin.autoconfigure.exception.NotFoundException;
import io.github.talelin.core.annotation.GroupRequired;
import io.github.talelin.core.annotation.LoginRequired;
import io.github.talelin.core.annotation.PermissionMeta;
import io.github.talelin.core.annotation.PermissionModule;
import io.github.talelin.latticy.dto.SpecValueDTO;
import io.github.talelin.latticy.model.SpecValueDO;
import io.github.talelin.latticy.service.SpecValueService;
import io.github.talelin.latticy.vo.CreatedVO;
import io.github.talelin.latticy.vo.DeletedVO;
import io.github.talelin.latticy.vo.UpdatedVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.util.List;

/**
* @author generator@TaleLin
* @since 2020-05-24
*/
@RestController
@RequestMapping("/v1/spec-value")
@Validated
@PermissionModule("规格值")
public class SpecValueController {

    @Autowired
    private SpecValueService specValueService;


    @PostMapping("")
    @PermissionMeta("创建规格值")
    @GroupRequired
    public CreatedVO create(@Validated @RequestBody SpecValueDTO dto) {
        specValueService.create(dto);
        return new CreatedVO();
    }

    @PutMapping("/{id}")
    @PermissionMeta("更新规格值")
    @GroupRequired
    public UpdatedVO update(
            @Validated @RequestBody SpecValueDTO dto,
            @PathVariable @Positive(message = "{id.positive}") Integer id) {
        specValueService.update(dto, id);
        return new UpdatedVO();
    }

    @DeleteMapping("/{id}")
    @PermissionMeta("删除规格值")
    @GroupRequired
    public DeletedVO delete(@PathVariable @Positive(message = "{id.positive}") Integer id) {
        specValueService.delete(id);
        return new DeletedVO();
    }

    @GetMapping("/{id}")
    @LoginRequired
    public SpecValueDO get(@PathVariable(value = "id") @Positive(message = "{id.positive}") Integer id) {
        SpecValueDO specValue = specValueService.getById(id);
        if (specValue == null) {
            throw new NotFoundException(60002);
        }
        return specValue;
    }


    @GetMapping("/by/spec-key/{id}")
    public List<SpecValueDO> getBySpecKeyId(@PathVariable(value = "id") @Positive(message = "{id.positive}") Integer id) {
        return this.specValueService.lambdaQuery().eq(SpecValueDO::getSpecId, id).list();
    }

}
