package io.github.talelin.latticy.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.talelin.latticy.dto.ThemeSpuDTO;
import io.github.talelin.latticy.mapper.ThemeMapper;
import io.github.talelin.latticy.mapper.ThemeSpuMapper;
import io.github.talelin.latticy.model.SimplifySpuDO;
import io.github.talelin.latticy.model.SpuDO;
import io.github.talelin.latticy.model.ThemeDO;
import io.github.talelin.latticy.model.ThemeSpuDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author generator@TaleLin
 * @since 2020-06-03
 */
@Service
public class ThemeService extends ServiceImpl<ThemeMapper, ThemeDO> {

    @Autowired
    private ThemeSpuMapper themeSpuMapper;

    public List<SimplifySpuDO> getSpus(Integer id) {
        return this.getBaseMapper().getSpus(id);
    }

    public void addThemeSpu(ThemeSpuDTO dto) {
        ThemeSpuDO themeSpu = new ThemeSpuDO();
        themeSpu.setThemeId(dto.getThemeId());
        themeSpu.setSpuId(dto.getSpuId());
        themeSpuMapper.insert(themeSpu);
    }

    public void deleteThemeSpu(Integer id) {
        themeSpuMapper.deleteById(id);
    }

    public List<SpuDO> getSimplifySpus(Integer id) {
        return themeSpuMapper.getSimplifySpus(id);
    }

}
