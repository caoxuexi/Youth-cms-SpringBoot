package io.github.talelin.latticy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.talelin.latticy.model.SimplifySpuDO;
import io.github.talelin.latticy.model.ThemeDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author generator@TaleLin
 * @since 2020-06-03
 */
public interface ThemeMapper extends BaseMapper<ThemeDO> {

    /**
     * 获取主题下的spu
     * @param id 主题id
     * @return spu列表
     */
    List<SimplifySpuDO> getSpus(@Param("id") Integer id);

}
