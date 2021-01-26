package io.github.talelin.latticy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.talelin.latticy.model.SkuDO;
import io.github.talelin.latticy.model.SkuDetailDO;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author generator@TaleLin
 * @since 2020-05-23
 */
@Repository
public interface SkuMapper extends BaseMapper<SkuDO> {

    /**
     * 根据 skuId 获取 sku 详情
     * @param id skuId
     * @return SkuDetailDO
     */
    SkuDetailDO getDetail(Integer id);

}
