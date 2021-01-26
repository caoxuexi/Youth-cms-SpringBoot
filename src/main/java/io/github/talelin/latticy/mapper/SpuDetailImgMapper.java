package io.github.talelin.latticy.mapper;

import io.github.talelin.latticy.model.SpuDetailImgDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author generator@TaleLin
 * @since 2020-05-27
 */
public interface SpuDetailImgMapper extends BaseMapper<SpuDetailImgDO> {

    /**
     * 物理删除spu详情图
     * @param spuId Integer
     */
    void hardDeleteImgsBySpuId(@Param("spuId") Integer spuId);

}
