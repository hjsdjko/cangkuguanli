package com.entity.view;

import com.entity.WuziChuruInoutListEntity;
import com.baomidou.mybatisplus.annotations.TableName;
import org.apache.commons.beanutils.BeanUtils;
import java.lang.reflect.InvocationTargetException;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;

/**
 * 入库详情
 * 后端返回视图实体辅助类
 * （通常后端关联的表或者自定义的字段需要返回使用）
 */
@TableName("wuzi_churu_inout_list")
public class WuziChuruInoutListView extends WuziChuruInoutListEntity implements Serializable {
    private static final long serialVersionUID = 1L;




		//级联表 wuzi
			/**
			* 物资名称
			*/
			private String wuziName;
			/**
			* 物资照片
			*/
			private String wuziPhoto;
			/**
			* 物资类型
			*/
			private Integer wuziTypes;
				/**
				* 物资类型的值
				*/
				private String wuziValue;
			/**
			* 物资库存
			*/
			private Integer wuziKucunNumber;
			/**
			* 预警值
			*/
			private Integer yujingNumber;
			/**
			* 逻辑删除
			*/
			private Integer wuziDelete;
			/**
			* 物资简介
			*/
			private String wuziContent;

		//级联表 wuzi_churu_inout
			/**
			* 入库流水号
			*/
			private String wuziChuruInoutUuidNumber;
			/**
			* 入库名称
			*/
			private String wuziChuruInoutName;
			/**
			* 入库类型
			*/
			private Integer wuziChuruInoutTypes;
				/**
				* 入库类型的值
				*/
				private String wuziChuruInoutValue;
			/**
			* 备注
			*/
			private String wuziChuruInoutContent;

	public WuziChuruInoutListView() {

	}

	public WuziChuruInoutListView(WuziChuruInoutListEntity wuziChuruInoutListEntity) {
		try {
			BeanUtils.copyProperties(this, wuziChuruInoutListEntity);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

















				//级联表的get和set wuzi
					/**
					* 获取： 物资名称
					*/
					public String getWuziName() {
						return wuziName;
					}
					/**
					* 设置： 物资名称
					*/
					public void setWuziName(String wuziName) {
						this.wuziName = wuziName;
					}
					/**
					* 获取： 物资照片
					*/
					public String getWuziPhoto() {
						return wuziPhoto;
					}
					/**
					* 设置： 物资照片
					*/
					public void setWuziPhoto(String wuziPhoto) {
						this.wuziPhoto = wuziPhoto;
					}
					/**
					* 获取： 物资类型
					*/
					public Integer getWuziTypes() {
						return wuziTypes;
					}
					/**
					* 设置： 物资类型
					*/
					public void setWuziTypes(Integer wuziTypes) {
						this.wuziTypes = wuziTypes;
					}


						/**
						* 获取： 物资类型的值
						*/
						public String getWuziValue() {
							return wuziValue;
						}
						/**
						* 设置： 物资类型的值
						*/
						public void setWuziValue(String wuziValue) {
							this.wuziValue = wuziValue;
						}
					/**
					* 获取： 物资库存
					*/
					public Integer getWuziKucunNumber() {
						return wuziKucunNumber;
					}
					/**
					* 设置： 物资库存
					*/
					public void setWuziKucunNumber(Integer wuziKucunNumber) {
						this.wuziKucunNumber = wuziKucunNumber;
					}
					/**
					* 获取： 预警值
					*/
					public Integer getYujingNumber() {
						return yujingNumber;
					}
					/**
					* 设置： 预警值
					*/
					public void setYujingNumber(Integer yujingNumber) {
						this.yujingNumber = yujingNumber;
					}
					/**
					* 获取： 逻辑删除
					*/
					public Integer getWuziDelete() {
						return wuziDelete;
					}
					/**
					* 设置： 逻辑删除
					*/
					public void setWuziDelete(Integer wuziDelete) {
						this.wuziDelete = wuziDelete;
					}
					/**
					* 获取： 物资简介
					*/
					public String getWuziContent() {
						return wuziContent;
					}
					/**
					* 设置： 物资简介
					*/
					public void setWuziContent(String wuziContent) {
						this.wuziContent = wuziContent;
					}


				//级联表的get和set wuzi_churu_inout
					/**
					* 获取： 入库流水号
					*/
					public String getWuziChuruInoutUuidNumber() {
						return wuziChuruInoutUuidNumber;
					}
					/**
					* 设置： 入库流水号
					*/
					public void setWuziChuruInoutUuidNumber(String wuziChuruInoutUuidNumber) {
						this.wuziChuruInoutUuidNumber = wuziChuruInoutUuidNumber;
					}
					/**
					* 获取： 入库名称
					*/
					public String getWuziChuruInoutName() {
						return wuziChuruInoutName;
					}
					/**
					* 设置： 入库名称
					*/
					public void setWuziChuruInoutName(String wuziChuruInoutName) {
						this.wuziChuruInoutName = wuziChuruInoutName;
					}
					/**
					* 获取： 入库类型
					*/
					public Integer getWuziChuruInoutTypes() {
						return wuziChuruInoutTypes;
					}
					/**
					* 设置： 入库类型
					*/
					public void setWuziChuruInoutTypes(Integer wuziChuruInoutTypes) {
						this.wuziChuruInoutTypes = wuziChuruInoutTypes;
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
					/**
					* 获取： 备注
					*/
					public String getWuziChuruInoutContent() {
						return wuziChuruInoutContent;
					}
					/**
					* 设置： 备注
					*/
					public void setWuziChuruInoutContent(String wuziChuruInoutContent) {
						this.wuziChuruInoutContent = wuziChuruInoutContent;
					}













}
