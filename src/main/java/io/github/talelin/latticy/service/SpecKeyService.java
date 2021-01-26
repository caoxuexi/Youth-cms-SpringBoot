package io.github.talelin.latticy.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.talelin.autoconfigure.exception.ForbiddenException;
import io.github.talelin.autoconfigure.exception.NotFoundException;
import io.github.talelin.latticy.bo.SpecKeyAndItemsBO;
import io.github.talelin.latticy.dto.SpecKeyDTO;
import io.github.talelin.latticy.mapper.SkuMapper;
import io.github.talelin.latticy.mapper.SkuSpecMapper;
import io.github.talelin.latticy.mapper.SpecKeyMapper;
import io.github.talelin.latticy.mapper.SpecValueMapper;
import io.github.talelin.latticy.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author generator@TaleLin
 * @since 2020-05-24
 */
@Service
public class SpecKeyService extends ServiceImpl<SpecKeyMapper, SpecKeyDO> {

    @Autowired
    private SkuSpecMapper skuSpecMapper;

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private SpecKeyMapper specKeyMapper;

    @Autowired
    private SpecValueMapper specValueMapper;

    public void create(SpecKeyDTO dto) {
        // 不可创建重复的规格名
        QueryWrapper<SpecKeyDO> wrapper = new QueryWrapper();
        wrapper.lambda().eq(SpecKeyDO::getName, dto.getName());
        SpecKeyDO existed = this.getOne(wrapper);
        if (existed != null) {
            throw new ForbiddenException(60000);
        }
        SpecKeyDO specKey = new SpecKeyDO();
        BeanUtils.copyProperties(dto, specKey);
        this.save(specKey);
    }

    @Transactional(rollbackFor = Exception.class)
    public void update(SpecKeyDTO dto, Integer id) {
        SpecKeyDO specKey = this.getById(id);
        if (specKey == null) {
            throw new NotFoundException(60001);
        }
        BeanUtils.copyProperties(dto, specKey);
        this.updateById(specKey);
        // 更新 sku 的 specs 字段
        List<Integer> skuIds = skuSpecMapper.getSkuIdsByKeyId(id);
        if (skuIds.isEmpty()) {
            return;
        }
        List<SkuDO> skuList = skuMapper.selectBatchIds(skuIds);
        skuList.forEach(sku -> {
            QueryWrapper<SkuSpecDO> wrapper = new QueryWrapper<>();
            wrapper.lambda().eq(SkuSpecDO::getKeyId, id);
            wrapper.lambda().eq(SkuSpecDO::getSkuId, sku.getId());
            List<SkuSpecDO> skuSpecs = skuSpecMapper.selectList(wrapper);
            ArrayList<SpecKeyValueDO> specs = new ArrayList<>();
            skuSpecs.forEach(skuSpec -> {
                SpecKeyValueDO specKeyAndValue = specValueMapper.getSpecKeyAndValueById(skuSpec.getKeyId(), skuSpec.getValueId());
                specs.add(specKeyAndValue);
            });
            sku.setSpecs(specs);
            skuMapper.updateById(sku);
        });

    }

    public void delete(Integer id) {
        SpecKeyDO specKey = this.getById(id);
        if (specKey == null) {
            throw new NotFoundException(60001);
        }
        this.getBaseMapper().deleteById(id);
    }

    public SpecKeyAndItemsBO getKeyAndValuesById(Integer id) {
        SpecKeyDO specKey = this.getById(id);
        if (specKey == null) {
            throw new NotFoundException(60001);
        }
        QueryWrapper<SpecValueDO> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(SpecValueDO::getSpecId, specKey.getId());
        List<SpecValueDO> items = specValueMapper.selectList(wrapper);
        SpecKeyAndItemsBO specKeyAndItems = new SpecKeyAndItemsBO(specKey, items);
        return specKeyAndItems;
    }

    public List<SpecKeyDO> getBySpuId(Integer spuId) {
        return this.baseMapper.getBySpuId(spuId);
    }

}
