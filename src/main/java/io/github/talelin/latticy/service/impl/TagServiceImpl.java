package io.github.talelin.latticy.service.impl;

import io.github.talelin.latticy.mapper.ThemeSpuMapper;
import io.github.talelin.latticy.model.TagDO;
import io.github.talelin.latticy.mapper.TagMapper;
import io.github.talelin.latticy.service.TagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author generator@TaleLin
 * @since 2021-01-26
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, TagDO> implements TagService {
    @Autowired
    private TagMapper tagMapper;


}
