/**
 * @作者 7七月
 * @微信公号 林间有风
 * @开源项目 $ http://talelin.com
 * @免费专栏 $ http://course.talelin.com
 * @我的课程 $ http://imooc.com/t/4294850
 * @创建时间 2020-05-14 05:08
 */
package io.github.talelin.latticy.service;

import io.github.talelin.latticy.mapper.BannerMapper;
import io.github.talelin.latticy.model.BannerDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestSleeveService {

    @Autowired
    private BannerMapper bannerMapper;

    public List<BannerDO> getBanners() {
        return this.bannerMapper.getAllBanners();
    }

    public List<BannerDO> getBanners1() {
        return this.bannerMapper.getAllBanners1();
    }

    public long insertBanner() {
        BannerDO bannerDO = new BannerDO();
        bannerDO.setName("NewBanner");
        bannerDO.setTitle("NewBannerTitle");

        bannerMapper.insertBanner(bannerDO);
        return bannerDO.getId();
    }
}
