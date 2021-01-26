package io.github.talelin.latticy.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.talelin.autoconfigure.exception.ForbiddenException;
import io.github.talelin.autoconfigure.exception.NotFoundException;
import io.github.talelin.autoconfigure.exception.ParameterException;
import io.github.talelin.latticy.dto.SkuDTO;
import io.github.talelin.latticy.dto.SkuSelector;
import io.github.talelin.latticy.mapper.SkuMapper;
import io.github.talelin.latticy.model.SkuDO;
import io.github.talelin.latticy.model.SkuSpecDO;
import io.github.talelin.latticy.model.SpecKeyValueDO;
import io.github.talelin.latticy.model.SpuDO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author generator@TaleLin
 * @since 2020-05-23
 */
@Service
public class SkuService extends ServiceImpl<SkuMapper, SkuDO> {

    @Autowired
    private SpuService spuService;

    @Autowired
    private SpecValueService specValueService;

    @Autowired
    private SkuSpecService skuSpecService;

    @Transactional(rollbackFor = Exception.class)
    public void create(SkuDTO dto) {
        // 1. 检测数据
        QueryWrapper<SkuDO> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(SkuDO::getTitle, dto.getTitle());
        int count = this.count(wrapper);
        if (count > 0) {
            throw new ForbiddenException(80000);
        }
        SpuDO spu = spuService.getById(dto.getSpuId());
        if (spu == null) {
            throw new NotFoundException(70000);
        }
        List<SkuSelector> selectors = dto.getSelectors();
        List<SpecKeyValueDO> specs = this.checkSelectors(selectors);
        if (specs == null) {
            throw new ParameterException(80001);
        }
        // 2. 存储sku基础信息
        SkuDO sku = new SkuDO();
        BeanUtils.copyProperties(dto, sku);
        String code = this.generateSkuCode(selectors, dto.getSpuId());
        sku.setCode(code);
        sku.setCategoryId(spu.getCategoryId());
        sku.setRootCategoryId(spu.getRootCategoryId());
        sku.setSpecs(specs);
        this.save(sku);
        // 3. 存储信息到关联表中
        this.insertSpecs(specs, dto.getSpuId(), sku.getId());
    }

    @Transactional(rollbackFor = Exception.class)
    public void update(SkuDTO dto, Integer id) {
        // 1. 检测数据
        SpuDO spu = spuService.getById(dto.getSpuId());
        if (spu == null) {
            throw new NotFoundException(70000);
        }
        List<SkuSelector> selectors = dto.getSelectors();
        List<SpecKeyValueDO> specs = this.checkSelectors(selectors);
        if (specs == null) {
            throw new ParameterException(80001);
        }
        // 2. 存储sku基础信息
        SkuDO sku = this.getById(id);
        if (sku == null) {
            throw new NotFoundException(80002);
        }
        BeanUtils.copyProperties(dto, sku);
        String code = this.generateSkuCode(selectors, dto.getSpuId());
        sku.setCode(code);
        sku.setCategoryId(spu.getCategoryId());
        sku.setRootCategoryId(spu.getRootCategoryId());
        sku.setSpecs(specs);
        this.updateById(sku);
        // 3.先删除关联信息，再存储信息到关联表中
        skuSpecService.getBaseMapper().deleteSpecs(sku.getSpuId(), sku.getId());
        this.insertSpecs(specs, dto.getSpuId(), sku.getId());
    }

    public void delete(Integer id) {
        // 删除 sku
        SkuDO sku = this.getById(id);
        if (sku == null) {
            throw new NotFoundException(80002);
        }
        this.getBaseMapper().deleteById(id);
        // 删除 sku_spec
        skuSpecService.getBaseMapper().deleteSpecs(sku.getSpuId(), sku.getId());
    }

    private List<SpecKeyValueDO> checkSelectors(List<SkuSelector> selectors) {
        List<SpecKeyValueDO> specs = new ArrayList<>();
        for (SkuSelector selector : selectors) {
            SpecKeyValueDO specKeyAndValue = specValueService.getBaseMapper().
                    getSpecKeyAndValueById(selector.getKeyId(), selector.getValueId());
            if (specKeyAndValue == null) {
                return null;
            }
            specs.add(specKeyAndValue);
        }
        return specs;
    }

    /**
     * 向sku_specs 表中插入数据
     */
    private void insertSpecs(List<SpecKeyValueDO> specs, Integer spuId, Integer skuId) {
        ArrayList<SkuSpecDO> skuSpecList = new ArrayList<>();
        specs.forEach(spec -> {
            SkuSpecDO skuSpec = new SkuSpecDO();
            skuSpec.setSpuId(spuId);
            skuSpec.setSkuId(skuId);
            skuSpec.setKeyId(spec.getKeyId());
            skuSpec.setValueId(spec.getValueId());
            skuSpecList.add(skuSpec);
        });
        skuSpecService.saveBatch(skuSpecList);
    }

    private String generateSkuCode(List<SkuSelector> selectors, Integer spuId) {
        // 调整：sku的code 调整成$分隔spu和sku，#分隔sku片段
        selectors.sort((o1, o2) -> (int) (o1.getKeyId() - o2.getKeyId()));
        StringBuilder builder = new StringBuilder();
        builder.append(spuId);
        builder.append("$");
        for (int i = 0; i < selectors.size(); i++) {
            SkuSelector skuSelector = selectors.get(i);
            builder.append(skuSelector.getKeyId());
            builder.append("-");
            builder.append(skuSelector.getValueId());
            if (i < selectors.size() - 1) {
                builder.append("#");
            }
        }
        // blob law
        return builder.toString();
    }

}
