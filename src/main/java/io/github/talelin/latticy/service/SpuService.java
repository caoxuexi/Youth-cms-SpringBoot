package io.github.talelin.latticy.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.talelin.autoconfigure.exception.NotFoundException;
import io.github.talelin.latticy.dto.SpuDTO;
import io.github.talelin.latticy.mapper.SpuMapper;
import io.github.talelin.latticy.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author generator@TaleLin
 * @since 2020-05-23
 */
@Service
public class SpuService extends ServiceImpl<SpuMapper, SpuDO> {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SpuImgService spuImgService;

    @Autowired
    private SpuDetailImgService spuDetailImgService;

    @Autowired
    private SpuKeyService spuKeyService;

    public SpuDetailDO getDetail(Integer id) {
        return this.getBaseMapper().getDetail(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public void create(SpuDTO dto) {
        SpuDO spuDO = new SpuDO();
        BeanUtils.copyProperties(dto, spuDO);
        CategoryDO categoryDO = categoryService.getCategoryById(dto.getCategoryId());
        spuDO.setRootCategoryId(categoryDO.getParentId());
        this.save(spuDO);

        List<String> spuImgList = new ArrayList<>();
        if (dto.getSpuImgList() == null) {
            spuImgList.add(dto.getImg());
        } else {
            spuImgList = dto.getSpuImgList();
        }

        this.insertSpuImgList(spuImgList, spuDO.getId());
        if (dto.getSpuDetailImgList() != null) {
            this.insertSpuDetailImgList(dto.getSpuDetailImgList(), spuDO.getId());
        }

        if (dto.getSpecKeyIdList() != null) {
            this.insertSpuKeyList(dto.getSpecKeyIdList(), spuDO.getId());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void update(SpuDTO dto, Integer id) {
        SpuDO spuDO = this.getById(id);
        if (spuDO == null) {
            throw new NotFoundException(70000);
        }
        BeanUtils.copyProperties(dto, spuDO);
        CategoryDO category = categoryService.getCategoryById(dto.getCategoryId());
        if (category.getParentId() != null) {
            spuDO.setRootCategoryId(category.getParentId());
        }
        this.updateById(spuDO);

        List<String> spuImgList = new ArrayList<>();
        if (dto.getSpuImgList() == null) {
            spuImgList.add(dto.getImg());
        } else {
            spuImgList = dto.getSpuImgList();
        }
        spuImgService.getBaseMapper().hardDeleteImgsBySpuId(spuDO.getId());
        spuDetailImgService.getBaseMapper().hardDeleteImgsBySpuId(spuDO.getId());
        this.insertSpuImgList(spuImgList, spuDO.getId());
        if (dto.getSpuDetailImgList() != null) {
            this.insertSpuDetailImgList(dto.getSpuDetailImgList(), spuDO.getId());
        }
        this.updateSpuKey(spuDO.getId(), dto.getSpecKeyIdList());
    }

    public void delete(Integer id) {
        SpuDO exist = this.getById(id);
        if (exist == null) {
            throw new NotFoundException(70000);
        }
        this.getBaseMapper().deleteById(id);
    }

    private void insertSpuImgList(List<String> spuImgList, Integer spuId) {
        List<SpuImgDO> spuImgDOList = spuImgList.stream().map(s -> {
            SpuImgDO spuImgDO = new SpuImgDO();
            spuImgDO.setImg(s);
            spuImgDO.setSpuId(spuId);
            return spuImgDO;
        }).collect(Collectors.toList());
        this.spuImgService.saveBatch(spuImgDOList);
    }

    private void insertSpuDetailImgList(List<String> spuDetailImgList, Integer spuId) {
        List<SpuDetailImgDO> spuDetailImgDOList = new ArrayList<>();
        for (int i = 0; i < spuDetailImgList.size(); i++) {
            SpuDetailImgDO spuDetailImgDO = new SpuDetailImgDO();
            spuDetailImgDO.setImg(spuDetailImgList.get(i))
                    .setSpuId(spuId)
                    .setIndex(i);
            spuDetailImgDOList.add(spuDetailImgDO);
        }
        this.spuDetailImgService.saveBatch(spuDetailImgDOList);
    }

    private void insertSpuKeyList(List<Integer> spuKeyIdList, Integer spuId) {
        List<SpuKeyDO> spuKeyDOList = spuKeyIdList.stream()
                .map(sk -> {
                    SpuKeyDO spuKeyDO = new SpuKeyDO();
                    spuKeyDO.setSpuId(spuId)
                            .setSpecKeyId(sk);
                    return spuKeyDO;
                }).collect(Collectors.toList());
        this.spuKeyService.saveBatch(spuKeyDOList);
    }

    /**
     * 更新spu_key表
     * @param spuId spu id
     * @param newSpecKeyIdList 前端传递过来的 spu_key id列表
     */
    private void updateSpuKey(Integer spuId, List<Integer> newSpecKeyIdList) {
        QueryWrapper<SpuKeyDO> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(SpuKeyDO::getSpuId, spuId);
        List<SpuKeyDO> exists = spuKeyService.getBaseMapper().selectList(wrapper);
        List<Integer> existsIds = new ArrayList<>();
        List<SpuKeyDO> newSpuKeyList = new ArrayList<>();
        for (SpuKeyDO exist: exists) {
            existsIds.add(exist.getId());
        }
        for (Integer specKeyId: newSpecKeyIdList) {
            SpuKeyDO spuKeyDO = new SpuKeyDO();
            spuKeyDO.setSpecKeyId(specKeyId);
            spuKeyDO.setSpuId(spuId);
            newSpuKeyList.add(spuKeyDO);
        }
        spuKeyService.removeByIds(existsIds);
        spuKeyService.saveBatch(newSpuKeyList);
    }

}
