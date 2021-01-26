package io.github.talelin.latticy.service;

import io.github.talelin.latticy.dto.TagDTO;
import io.github.talelin.latticy.model.TagDO;
import com.baomidou.mybatisplus.extension.service.IService;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author generator@TaleLin
 * @since 2021-01-26
 */
public interface TagService extends IService<TagDO> {
    public void update(TagDTO dto, Integer id);
}
