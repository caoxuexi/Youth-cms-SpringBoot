package io.github.talelin.latticy.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.talelin.autoconfigure.exception.NotFoundException;
import io.github.talelin.latticy.dto.BannerItemDTO;
import io.github.talelin.latticy.mapper.BannerItemMapper;
import io.github.talelin.latticy.model.BannerDO;
import io.github.talelin.latticy.model.BannerItemDO;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author generator@TaleLin
 * @since 2020-05-22
 */
@Service
public class BannerItemService extends ServiceImpl<BannerItemMapper, BannerItemDO> {

    public void delete(Integer id) {
        BannerItemDO bannerItem = this.getById(id);
        if (bannerItem == null) {
            throw new NotFoundException(20001);
        }
        this.getBaseMapper().deleteById(id);
    }

    public void update(BannerItemDTO dto, Integer id) {
        BannerItemDO bannerItemDO = this.getById(id);
        if (bannerItemDO == null) {
            throw new NotFoundException(20001);
        }
        BeanUtils.copyProperties(dto, bannerItemDO);
        this.updateById(bannerItemDO);
    }
}
