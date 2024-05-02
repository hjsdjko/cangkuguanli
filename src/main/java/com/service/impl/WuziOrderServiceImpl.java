package com.service.impl;

import com.utils.StringUtil;
import org.springframework.stereotype.Service;
import java.lang.reflect.Field;
import java.util.*;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;
import com.utils.PageUtils;
import com.utils.Query;
import org.springframework.web.context.ContextLoader;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import com.dao.WuziOrderDao;
import com.entity.WuziOrderEntity;
import com.service.WuziOrderService;
import com.entity.view.WuziOrderView;

/**
 * 物资订单 服务实现类
 */
@Service("wuziOrderService")
@Transactional
public class WuziOrderServiceImpl extends ServiceImpl<WuziOrderDao, WuziOrderEntity> implements WuziOrderService {

    @Override
    public PageUtils queryPage(Map<String,Object> params) {
        if(params != null && (params.get("limit") == null || params.get("page") == null)){
            params.put("page","1");
            params.put("limit","10");
        }
        Page<WuziOrderView> page =new Query<WuziOrderView>(params).getPage();
        page.setRecords(baseMapper.selectListView(page,params));
        return new PageUtils(page);
    }


}
