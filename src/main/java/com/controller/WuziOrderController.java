
package com.controller;

import java.io.File;
import java.math.BigDecimal;
import java.net.URL;
import java.text.SimpleDateFormat;
import com.alibaba.fastjson.JSONObject;
import java.util.*;
import org.springframework.beans.BeanUtils;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.context.ContextLoader;
import javax.servlet.ServletContext;
import com.service.TokenService;
import com.utils.*;
import java.lang.reflect.InvocationTargetException;

import com.service.DictionaryService;
import org.apache.commons.lang3.StringUtils;
import com.annotation.IgnoreAuth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.entity.*;
import com.entity.view.*;
import com.service.*;
import com.utils.PageUtils;
import com.utils.R;
import com.alibaba.fastjson.*;

/**
 * 物资订单
 * 后端接口
 * @author
 * @email
*/
@RestController
@Controller
@RequestMapping("/wuziOrder")
public class WuziOrderController {
    private static final Logger logger = LoggerFactory.getLogger(WuziOrderController.class);

    @Autowired
    private WuziOrderService wuziOrderService;


    @Autowired
    private TokenService tokenService;
    @Autowired
    private DictionaryService dictionaryService;

    //级联表service
    @Autowired
    private KehuService kehuService;
    @Autowired
    private WuziService wuziService;



    /**
    * 后端列表
    */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params, HttpServletRequest request){
        logger.debug("page方法:,,Controller:{},,params:{}",this.getClass().getName(),JSONObject.toJSONString(params));
        String role = String.valueOf(request.getSession().getAttribute("role"));
        if(StringUtil.isEmpty(role))
            return R.error(511,"权限为空");
        else if("员工".equals(role))
            params.put("yuangongId",request.getSession().getAttribute("userId"));
        if(params.get("orderBy")==null || params.get("orderBy")==""){
            params.put("orderBy","id");
        }
        PageUtils page = wuziOrderService.queryPage(params);

        //字典表数据转换
        List<WuziOrderView> list =(List<WuziOrderView>)page.getList();
        for(WuziOrderView c:list){
            //修改对应字典表字段
            dictionaryService.dictionaryConvert(c, request);
        }
        return R.ok().put("data", page);
    }

    /**
    * 后端详情
    */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id, HttpServletRequest request){
        logger.debug("info方法:,,Controller:{},,id:{}",this.getClass().getName(),id);
        WuziOrderEntity wuziOrder = wuziOrderService.selectById(id);
        if(wuziOrder !=null){
            //entity转view
            WuziOrderView view = new WuziOrderView();
            BeanUtils.copyProperties( wuziOrder , view );//把实体数据重构到view中

                //级联表
                KehuEntity kehu = kehuService.selectById(wuziOrder.getKehuId());
                if(kehu != null){
                    BeanUtils.copyProperties( kehu , view ,new String[]{ "id", "createDate"});//把级联的数据添加到view中,并排除id和创建时间字段
                    view.setKehuId(kehu.getId());
                }
                //级联表
                WuziEntity wuzi = wuziService.selectById(wuziOrder.getWuziId());
                if(wuzi != null){
                    BeanUtils.copyProperties( wuzi , view ,new String[]{ "id", "createDate"});//把级联的数据添加到view中,并排除id和创建时间字段
                    view.setWuziId(wuzi.getId());
                }
            //修改对应字典表字段
            dictionaryService.dictionaryConvert(view, request);
            return R.ok().put("data", view);
        }else {
            return R.error(511,"查不到数据");
        }

    }

    /**
    * 后端保存
    */
    @RequestMapping("/save")
    public R save(@RequestBody WuziOrderEntity wuziOrder, HttpServletRequest request){
        logger.debug("save方法:,,Controller:{},,wuziOrder:{}",this.getClass().getName(),wuziOrder.toString());

        String role = String.valueOf(request.getSession().getAttribute("role"));
        if(StringUtil.isEmpty(role))
            return R.error(511,"权限为空");

        Integer wuziId = wuziOrder.getWuziId();
        WuziEntity wuziEntity = wuziService.selectById(wuziId);
        if(wuziEntity != null){
            if((wuziEntity.getWuziKucunNumber() -wuziOrder.getBuyNumber())<0){
                return R.error(511,"购买数量大于库存数量");
            }
            wuziEntity.setWuziKucunNumber(wuziEntity.getWuziKucunNumber()-wuziOrder.getBuyNumber());
            wuziService.updateById(wuziEntity);
        }
        wuziOrder.setInsertTime(new Date());
        wuziOrder.setCreateTime(new Date());
        wuziOrderService.insert(wuziOrder);
        return R.ok();
    }

    /**
    * 后端修改
    */
    @RequestMapping("/update")
    public R update(@RequestBody WuziOrderEntity wuziOrder, HttpServletRequest request){
        logger.debug("update方法:,,Controller:{},,wuziOrder:{}",this.getClass().getName(),wuziOrder.toString());

        String role = String.valueOf(request.getSession().getAttribute("role"));
//        if(StringUtil.isEmpty(role))
//            return R.error(511,"权限为空");
        //根据字段查询是否有相同数据
        Wrapper<WuziOrderEntity> queryWrapper = new EntityWrapper<WuziOrderEntity>()
            .eq("id",0)
            ;

        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        WuziOrderEntity wuziOrderEntity = wuziOrderService.selectOne(queryWrapper);
        if(wuziOrderEntity==null){
            //  String role = String.valueOf(request.getSession().getAttribute("role"));
            //  if("".equals(role)){
            //      wuziOrder.set
            //  }
            wuziOrderService.updateById(wuziOrder);//根据id更新
            return R.ok();
        }else {
            return R.error(511,"表中有相同数据");
        }
    }

    /**
    * 删除
    */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids){
        logger.debug("delete:,,Controller:{},,ids:{}",this.getClass().getName(),ids.toString());
        wuziOrderService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }


    /**
     * 批量上传
     */
    @RequestMapping("/batchInsert")
    public R save( String fileName){
        logger.debug("batchInsert方法:,,Controller:{},,fileName:{}",this.getClass().getName(),fileName);
        try {
            List<WuziOrderEntity> wuziOrderList = new ArrayList<>();//上传的东西
            Map<String, List<String>> seachFields= new HashMap<>();//要查询的字段
            Date date = new Date();
            int lastIndexOf = fileName.lastIndexOf(".");
            if(lastIndexOf == -1){
                return R.error(511,"该文件没有后缀");
            }else{
                String suffix = fileName.substring(lastIndexOf);
                if(!".xls".equals(suffix)){
                    return R.error(511,"只支持后缀为xls的excel文件");
                }else{
                    URL resource = this.getClass().getClassLoader().getResource("static/upload/" + fileName);//获取文件路径
                    File file = new File(resource.getFile());
                    if(!file.exists()){
                        return R.error(511,"找不到上传文件，请联系管理员");
                    }else{
                        List<List<String>> dataList = PoiUtil.poiImport(file.getPath());//读取xls文件
                        dataList.remove(0);//删除第一行，因为第一行是提示
                        for(List<String> data:dataList){
                            //循环
                            WuziOrderEntity wuziOrderEntity = new WuziOrderEntity();
//                            wuziOrderEntity.setWuziOrderUuidNumber(data.get(0));                    //订单号 要改的
//                            wuziOrderEntity.setWuziId(Integer.valueOf(data.get(0)));   //物资 要改的
//                            wuziOrderEntity.setKehuId(Integer.valueOf(data.get(0)));   //客户 要改的
//                            wuziOrderEntity.setBuyNumber(Integer.valueOf(data.get(0)));   //购买数量 要改的
//                            wuziOrderEntity.setWuziOrderContent("");//照片
//                            wuziOrderEntity.setInsertTime(date);//时间
//                            wuziOrderEntity.setCreateTime(date);//时间
                            wuziOrderList.add(wuziOrderEntity);


                            //把要查询是否重复的字段放入map中
                                //订单号
                                if(seachFields.containsKey("wuziOrderUuidNumber")){
                                    List<String> wuziOrderUuidNumber = seachFields.get("wuziOrderUuidNumber");
                                    wuziOrderUuidNumber.add(data.get(0));//要改的
                                }else{
                                    List<String> wuziOrderUuidNumber = new ArrayList<>();
                                    wuziOrderUuidNumber.add(data.get(0));//要改的
                                    seachFields.put("wuziOrderUuidNumber",wuziOrderUuidNumber);
                                }
                        }

                        //查询是否重复
                         //订单号
                        List<WuziOrderEntity> wuziOrderEntities_wuziOrderUuidNumber = wuziOrderService.selectList(new EntityWrapper<WuziOrderEntity>().in("wuzi_order_uuid_number", seachFields.get("wuziOrderUuidNumber")));
                        if(wuziOrderEntities_wuziOrderUuidNumber.size() >0 ){
                            ArrayList<String> repeatFields = new ArrayList<>();
                            for(WuziOrderEntity s:wuziOrderEntities_wuziOrderUuidNumber){
                                repeatFields.add(s.getWuziOrderUuidNumber());
                            }
                            return R.error(511,"数据库的该表中的 [订单号] 字段已经存在 存在数据为:"+repeatFields.toString());
                        }
                        wuziOrderService.insertBatch(wuziOrderList);
                        return R.ok();
                    }
                }
            }
        }catch (Exception e){
            return R.error(511,"批量插入数据异常，请联系管理员");
        }
    }






}
