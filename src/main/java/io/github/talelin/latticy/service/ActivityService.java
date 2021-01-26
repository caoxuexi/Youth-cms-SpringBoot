package io.github.talelin.latticy.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.talelin.autoconfigure.exception.NotFoundException;
import io.github.talelin.latticy.dto.ActivityDTO;
import io.github.talelin.latticy.mapper.ActivityMapper;
import io.github.talelin.latticy.mapper.CouponMapper;
import io.github.talelin.latticy.model.ActivityDO;
import io.github.talelin.latticy.model.ActivityDetailDO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author generator@TaleLin
 * @since 2020-05-20
 */
@Service
public class ActivityService extends ServiceImpl<ActivityMapper, ActivityDO> {

    @Autowired
    private CouponMapper couponMapper;

    public void create(ActivityDTO dto) {
        ActivityDO activity = new ActivityDO();
        BeanUtils.copyProperties(dto, activity);
        this.save(activity);
    }

    public void update(ActivityDTO dto, Integer id) {
        ActivityDO activity = this.getById(id);
        if (activity == null) {
            throw new NotFoundException(90000);
        }
        BeanUtils.copyProperties(dto, activity);
        this.updateById(activity);
    }

    public ActivityDetailDO getDetailById(Integer id) {
        ActivityDO activity = this.getById(id);
        if (activity == null) {
            throw new NotFoundException(90000);
        }
        List<Integer> coupons = couponMapper.getCouponsByActivityId(id);
        ActivityDetailDO activityDetail = new ActivityDetailDO();
        activityDetail.setCouponIds(coupons);
        BeanUtils.copyProperties(activity, activityDetail);
        return activityDetail;
    }

}
