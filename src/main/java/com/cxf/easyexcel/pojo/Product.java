package com.cxf.easyexcel.pojo;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 产品信息表
 * </p>
 *
 * @author JamesFairyTale
 * @since 2019-07-27
 */
@Data
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Integer id;

    /**
     * 名称
     */
    private String name;

    /**
     * 单位
     */
    private String unit;

    /**
     * 单价
     */
    private Double price;

    /**
     * 库存量
     */
    private Double stock;

    private String remark;

    /**
     * 采购日期
     */
    private Date purchaseDate;


}
