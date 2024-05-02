package com.entity.view;

import com.entity.WuziChuruInoutEntity;
import com.baomidou.mybatisplus.annotations.TableName;
import org.apache.commons.beanutils.BeanUtils;
import java.lang.reflect.InvocationTargetException;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;

/**
 * 入库
 * 后端返回视图实体辅助类
 * （通常后端关联的表或者自定义的字段需要返回使用）
 */
@TableName("wuzi_churu_inout")
public class WuziChuruInoutView extends WuziChuruInoutEntity implements Serializable {
    private static final long serialVersionUID = 1L;

		/**
		* 入库类型的值
		*/
		private String wuziChuruInoutValue;



	public WuziChuruInoutView() {

	}

	public WuziChuruInoutView(WuziChuruInoutEntity wuziChuruInoutEntity) {
		try {
			BeanUtils.copyProperties(this, wuziChuruInoutEntity);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



			/**
			* 获取： 入库类型的值
			*/
			public String getWuziChuruInoutValue() {
				return wuziChuruInoutValue;
			}
			/**
			* 设置： 入库类型的值
			*/
			public void setWuziChuruInoutValue(String wuziChuruInoutValue) {
				this.wuziChuruInoutValue = wuziChuruInoutValue;
			}











}
