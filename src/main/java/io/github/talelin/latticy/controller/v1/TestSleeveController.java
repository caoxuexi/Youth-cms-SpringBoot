/**
 * @作者 7七月
 * @微信公号 林间有风
 * @开源项目 $ http://talelin.com
 * @免费专栏 $ http://course.talelin.com
 * @我的课程 $ http://imooc.com/t/4294850
 * @创建时间 2020-05-14 04:20
 */
package io.github.talelin.latticy.controller.v1;

import io.github.talelin.latticy.mapper.BannerMapper;
import io.github.talelin.latticy.model.BannerDO;
import io.github.talelin.latticy.service.TestSleeveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/v1/test")
@RestController
public class TestSleeveController {
    @Autowired
    private TestSleeveService testSleeveService;

    @Autowired
    private BannerMapper bannerMapper;

    @GetMapping("/test1")
    public List<BannerDO> test1() {
        return testSleeveService.getBanners();
    }

    @GetMapping("/test2")
    public long test2() {
        return testSleeveService.insertBanner();
    }

    @GetMapping("/test3")
    public List<BannerDO> test3() {
        return testSleeveService.getBanners();
    }

    @GetMapping("/test4")
    public List<BannerDO> test4() {
        bannerMapper.selectList(null);
        return testSleeveService.getBanners();
    }

}
