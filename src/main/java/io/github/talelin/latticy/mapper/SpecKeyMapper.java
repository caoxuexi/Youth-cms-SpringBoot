package io.github.talelin.latticy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.talelin.latticy.model.SpecKeyDO;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author generator@TaleLin
 * @since 2020-05-24
 */
public interface SpecKeyMapper extends BaseMapper<SpecKeyDO> {

    /**
     * 根据spuId获取规格键
     * @param spuId spuId
     * @return List
     */
    List<SpecKeyDO> getBySpuId(Integer spuId);

}
