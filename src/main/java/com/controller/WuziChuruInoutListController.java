
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
 * 入库详情
 * 后端接口
 * @author
 * @email
*/
@RestController
@Controller
@RequestMapping("/wuziChuruInoutList")
public class WuziChuruInoutListController {
    private static final Logger logger = LoggerFactory.getLogger(WuziChuruInoutListController.class);

    @Autowired
    private WuziChuruInoutListService wuziChuruInoutListService;


    @Autowired
    private TokenService tokenService;
    @Autowired
    private DictionaryService dictionaryService;

    //级联表service
    @Autowired
    private WuziService wuziService;
    @Autowired
    private WuziChuruInoutService wuziChuruInoutService;

    @Autowired
    private YuangongService yuangongService;


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
        PageUtils page = wuziChuruInoutListService.queryPage(params);

        //字典表数据转换
        List<WuziChuruInoutListView> list =(List<WuziChuruInoutListView>)page.getList();
        for(WuziChuruInoutListView c:list){
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
        WuziChuruInoutListEntity wuziChuruInoutList = wuziChuruInoutListService.selectById(id);
        if(wuziChuruInoutList !=null){
            //entity转view
            WuziChuruInoutListView view = new WuziChuruInoutListView();
            BeanUtils.copyProperties( wuziChuruInoutList , view );//把实体数据重构到view中

                //级联表
                WuziEntity wuzi = wuziService.selectById(wuziChuruInoutList.getWuziId());
                if(wuzi != null){
                    BeanUtils.copyProperties( wuzi , view ,new String[]{ "id", "createDate"});//把级联的数据添加到view中,并排除id和创建时间字段
                    view.setWuziId(wuzi.getId());
                }
                //级联表
                WuziChuruInoutEntity wuziChuruInout = wuziChuruInoutService.selectById(wuziChuruInoutList.getWuziChuruInoutId());
                if(wuziChuruInout != null){
                    BeanUtils.copyProperties( wuziChuruInout , view ,new String[]{ "id", "createDate"});//把级联的数据添加到view中,并排除id和创建时间字段
                    view.setWuziChuruInoutId(wuziChuruInout.getId());
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
    public R save(@RequestBody WuziChuruInoutListEntity wuziChuruInoutList, HttpServletRequest request){
        logger.debug("save方法:,,Controller:{},,wuziChuruInoutList:{}",this.getClass().getName(),wuziChuruInoutList.toString());

        String role = String.valueOf(request.getSession().getAttribute("role"));
        if(StringUtil.isEmpty(role))
            return R.error(511,"权限为空");

        Wrapper<WuziChuruInoutListEntity> queryWrapper = new EntityWrapper<WuziChuruInoutListEntity>()
            .eq("wuzi_churu_inout_id", wuziChuruInoutList.getWuziChuruInoutId())
            .eq("wuzi_id", wuziChuruInoutList.getWuziId())
            .eq("wuzi_churu_inout_list_number", wuziChuruInoutList.getWuziChuruInoutListNumber())
            ;

        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        WuziChuruInoutListEntity wuziChuruInoutListEntity = wuziChuruInoutListService.selectOne(queryWrapper);
        if(wuziChuruInoutListEntity==null){
            wuziChuruInoutList.setInsertTime(new Date());
            wuziChuruInoutList.setCreateTime(new Date());
            wuziChuruInoutListService.insert(wuziChuruInoutList);
            return R.ok();
        }else {
            return R.error(511,"表中有相同数据");
        }
    }

    /**
    * 后端修改
    */
    @RequestMapping("/update")
    public R update(@RequestBody WuziChuruInoutListEntity wuziChuruInoutList, HttpServletRequest request){
        logger.debug("update方法:,,Controller:{},,wuziChuruInoutList:{}",this.getClass().getName(),wuziChuruInoutList.toString());

        String role = String.valueOf(request.getSession().getAttribute("role"));
//        if(StringUtil.isEmpty(role))
//            return R.error(511,"权限为空");
        //根据字段查询是否有相同数据
        Wrapper<WuziChuruInoutListEntity> queryWrapper = new EntityWrapper<WuziChuruInoutListEntity>()
            .notIn("id",wuziChuruInoutList.getId())
            .andNew()
            .eq("wuzi_churu_inout_id", wuziChuruInoutList.getWuziChuruInoutId())
            .eq("wuzi_id", wuziChuruInoutList.getWuziId())
            .eq("wuzi_churu_inout_list_number", wuziChuruInoutList.getWuziChuruInoutListNumber())
            ;

        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        WuziChuruInoutListEntity wuziChuruInoutListEntity = wuziChuruInoutListService.selectOne(queryWrapper);
        if(wuziChuruInoutListEntity==null){
            //  String role = String.valueOf(request.getSession().getAttribute("role"));
            //  if("".equals(role)){
            //      wuziChuruInoutList.set
            //  }
            wuziChuruInoutListService.updateById(wuziChuruInoutList);//根据id更新
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
        wuziChuruInoutListService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }


    /**
     * 批量上传
     */
    @RequestMapping("/batchInsert")
    public R save( String fileName){
        logger.debug("batchInsert方法:,,Controller:{},,fileName:{}",this.getClass().getName(),fileName);
        try {
            List<WuziChuruInoutListEntity> wuziChuruInoutListList = new ArrayList<>();//上传的东西
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
                            WuziChuruInoutListEntity wuziChuruInoutListEntity = new WuziChuruInoutListEntity();
//                            wuziChuruInoutListEntity.setWuziChuruInoutId(Integer.valueOf(data.get(0)));   //入库 要改的
//                            wuziChuruInoutListEntity.setWuziId(Integer.valueOf(data.get(0)));   //物资 要改的
//                            wuziChuruInoutListEntity.setWuziChuruInoutListNumber(Integer.valueOf(data.get(0)));   //操作数量 要改的
//                            wuziChuruInoutListEntity.setInsertTime(date);//时间
//                            wuziChuruInoutListEntity.setCreateTime(date);//时间
                            wuziChuruInoutListList.add(wuziChuruInoutListEntity);


                            //把要查询是否重复的字段放入map中
                        }

                        //查询是否重复
                        wuziChuruInoutListService.insertBatch(wuziChuruInoutListList);
                        return R.ok();
                    }
                }
            }
        }catch (Exception e){
            return R.error(511,"批量插入数据异常，请联系管理员");
        }
    }






}
