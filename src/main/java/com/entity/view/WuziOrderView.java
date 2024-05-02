package com.entity.view;

import com.entity.WuziOrderEntity;
import com.baomidou.mybatisplus.annotations.TableName;
import org.apache.commons.beanutils.BeanUtils;
import java.lang.reflect.InvocationTargetException;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;

/**
 * 物资订单
 * 后端返回视图实体辅助类
 * （通常后端关联的表或者自定义的字段需要返回使用）
 */
@TableName("wuzi_order")
public class WuziOrderView extends WuziOrderEntity implements Serializable {
    private static final long serialVersionUID = 1L;




		//级联表 kehu
			/**
			* 客户姓名
			*/
			private String kehuName;
			/**
			* 客户手机号
			*/
			private String kehuPhone;
			/**
			* 客户身份证号
			*/
			private String kehuIdNumber;
			/**
			* 客户头像
			*/
			private String kehuPhoto;
			/**
			* 性别
			*/
			private Integer sexTypes;
				/**
				* 性别的值
				*/
				private String sexValue;
			/**
			* 电子邮箱
			*/
			private String kehuEmail;

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

	public WuziOrderView() {

	}

	public WuziOrderView(WuziOrderEntity wuziOrderEntity) {
		try {
			BeanUtils.copyProperties(this, wuziOrderEntity);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}














				//级联表的get和set kehu
					/**
					* 获取： 客户姓名
					*/
					public String getKehuName() {
						return kehuName;
					}
					/**
					* 设置： 客户姓名
					*/
					public void setKehuName(String kehuName) {
						this.kehuName = kehuName;
					}
					/**
					* 获取： 客户手机号
					*/
					public String getKehuPhone() {
						return kehuPhone;
					}
					/**
					* 设置： 客户手机号
					*/
					public void setKehuPhone(String kehuPhone) {
						this.kehuPhone = kehuPhone;
					}
					/**
					* 获取： 客户身份证号
					*/
					public String getKehuIdNumber() {
						return kehuIdNumber;
					}
					/**
					* 设置： 客户身份证号
					*/
					public void setKehuIdNumber(String kehuIdNumber) {
						this.kehuIdNumber = kehuIdNumber;
					}
					/**
					* 获取： 客户头像
					*/
					public String getKehuPhoto() {
						return kehuPhoto;
					}
					/**
					* 设置： 客户头像
					*/
					public void setKehuPhoto(String kehuPhoto) {
						this.kehuPhoto = kehuPhoto;
					}
					/**
					* 获取： 性别
					*/
					public Integer getSexTypes() {
						return sexTypes;
					}
					/**
					* 设置： 性别
					*/
					public void setSexTypes(Integer sexTypes) {
						this.sexTypes = sexTypes;
					}


						/**
						* 获取： 性别的值
						*/
						public String getSexValue() {
							return sexValue;
						}
						/**
						* 设置： 性别的值
						*/
						public void setSexValue(String sexValue) {
							this.sexValue = sexValue;
						}
					/**
					* 获取： 电子邮箱
					*/
					public String getKehuEmail() {
						return kehuEmail;
					}
					/**
					* 设置： 电子邮箱
					*/
					public void setKehuEmail(String kehuEmail) {
						this.kehuEmail = kehuEmail;
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
















}
