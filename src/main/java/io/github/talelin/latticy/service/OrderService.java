package io.github.talelin.latticy.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.talelin.autoconfigure.exception.ForbiddenException;
import io.github.talelin.autoconfigure.exception.NotFoundException;
import io.github.talelin.latticy.common.enumeration.OrderStatusEnum;
import io.github.talelin.latticy.common.mybatis.Page;
import io.github.talelin.latticy.common.util.DateDuration;
import io.github.talelin.latticy.common.util.SensitiveDataUtil;
import io.github.talelin.latticy.model.OrderDO;
import io.github.talelin.latticy.mapper.OrderMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.talelin.latticy.vo.OrderSimplifyVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author generator@TaleLin
 * @since 2020-05-28
 */
@Service
public class OrderService extends ServiceImpl<OrderMapper, OrderDO> {

    public void changeOrderStatus(Integer id, Integer status) {
        OrderDO order = this.getBaseMapper().selectById(id);
        if (order == null) {
            throw new NotFoundException(110000);
        }
        // 检查订单状态
        if (order.getStatus() != OrderStatusEnum.PAID.getValue() && order.getStatus() != OrderStatusEnum.DELIVERED.getValue()) {
            throw new ForbiddenException(110001);
        }
        if (order.getStatus() == OrderStatusEnum.PAID.getValue()) {
            if (status != OrderStatusEnum.DELIVERED.getValue()) {
                throw new ForbiddenException(110002);
            }
            this.getBaseMapper().changeOrderStatus(status, id);
        }
        if (order.getStatus() == OrderStatusEnum.DELIVERED.getValue()) {
            if (status != OrderStatusEnum.FINISHED.getValue()) {
                throw new ForbiddenException(110003);
            }
            this.getBaseMapper().changeOrderStatus(status, id);
        }
    }

    public IPage<OrderDO> getOrderByPage(Integer count, Integer page) {
        Page<OrderDO> pager = new Page<>(page, count);
        QueryWrapper<OrderDO> wrapper = new QueryWrapper<>();
        wrapper.lambda().orderByDesc(OrderDO::getId);
        IPage<OrderDO> paging = this.getBaseMapper().selectPage(pager, wrapper);
        return paging;
    }

    public IPage<OrderDO> search(Integer page, Integer count, String keyword, Date start, Date end) {
        Page<OrderDO> pager = new Page<>(page, count);
        IPage<OrderDO> paging = this.baseMapper.searchOrders(pager, "%" + keyword + "%", start, end);
        return paging;
    }

    public List<OrderSimplifyVO> convertFromDO(List<OrderDO> orders) {
        List<OrderSimplifyVO> orderExpires = new ArrayList<>();
        orders.forEach(order -> {
            Date expireTime = order.getExpiredTime();
            OrderSimplifyVO orderSimplifyVO = new OrderSimplifyVO();
            BeanUtils.copyProperties(order, orderSimplifyVO);
            if (expireTime != null) {
                orderSimplifyVO.setExpired(expireTime.before(new Date()));
            }
            orderSimplifyVO.setPrepayId(SensitiveDataUtil.defaultHide(orderSimplifyVO.getPrepayId()));
            orderExpires.add(orderSimplifyVO);
        });
        return orderExpires;
    }

}
