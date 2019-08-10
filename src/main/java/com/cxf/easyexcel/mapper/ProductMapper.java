package com.cxf.easyexcel.mapper;

import com.cxf.easyexcel.pojo.Product;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 产品信息表 Mapper 接口
 * </p>
 *
 * @author JamesFairyTale
 * @since 2019-07-27
 */
public interface ProductMapper extends BaseMapper<Product> {

    List<Product> selectAll(@Param("name") String name);

    void insertBatch(@Param("dataList") List<Product> dataList);


}
