package com.cxf.easyexcel.pojo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 学生类值传递对象
 *
 * @author always_on_the_way
 * @date 2019-07-23
 */
@Data
public class StudentDTO extends BaseRowModel {

    @ExcelProperty(index = 0,value = "编号")
    private Long id;

    @ExcelProperty(index = 1,value = "姓名")
    private String name;

    @ExcelProperty(index = 2,value = "年龄")
    private BigDecimal age;

    @ExcelProperty(index = 3,value = "注册时间",format = "yyyy-MM-dd")
    private Date registerTime;

}
