/**
 * @作者 7七月
 * @微信公号 林间有风
 * @开源项目 $ http://talelin.com
 * @免费专栏 $ http://course.talelin.com
 * @我的课程 $ http://imooc.com/t/4294850
 * @创建时间 2020-05-17 04:51
 */
package io.github.talelin.latticy.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.talelin.autoconfigure.exception.NotFoundException;
import io.github.talelin.latticy.bo.BannerWithItemsBO;
import io.github.talelin.latticy.dto.BannerDTO;
import io.github.talelin.latticy.mapper.BannerItemMapper;
import io.github.talelin.latticy.mapper.BannerMapper;
import io.github.talelin.latticy.model.BannerDO;
import io.github.talelin.latticy.model.BannerItemDO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BannerService extends ServiceImpl<BannerMapper, BannerDO> {
    @Autowired
    private BannerMapper bannerMapper;

    @Autowired
    private BannerItemMapper bannerItemMapper;

    public BannerWithItemsBO getWithItems(Integer id) {
        BannerDO banner = this.getById(id);
        if (banner == null) {
            throw new NotFoundException(20000);
        }

//        LambdaQueryWrapper<BannerItemDO> wrapper = new QueryWrapper<BannerItemDO>().lambda();
//        wrapper.eq("banner_id", id);
//        LambdaQueryWrapper<BannerItemDO> wrapper = new LambdaQueryWrapper<>();
//        wrapper.eq(BannerItemDO::getBannerId, id);
//        List<BannerItemDO> bannerItems = bannerItemMapper.selectList(wrapper);

        List<BannerItemDO> bannerItems =
                new LambdaQueryChainWrapper<>(bannerItemMapper)
                        .eq(BannerItemDO::getBannerId, id)
                        .list();

        return new BannerWithItemsBO(banner, bannerItems);
    }

    public void delete(Integer id) {
        BannerDO banner = this.getById(id);
        if (banner == null) {
            throw new NotFoundException(20000);
        }
        this.getBaseMapper().deleteById(id);
    }

    public void update(BannerDTO dto, Integer id) {
        BannerDO bannerDO = this.getById(id);
        if (bannerDO == null) {
            throw new NotFoundException(20000);
        }
        BeanUtils.copyProperties(dto, bannerDO);
        this.updateById(bannerDO);
    }

}
