package com.cxf.easyexcel.service.impl;

import com.cxf.easyexcel.mapper.ProductMapper;
import com.cxf.easyexcel.pojo.Product;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxf.easyexcel.service.IProductService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 产品信息表 服务实现类
 * </p>
 *
 * @author JamesFairyTale
 * @since 2019-07-27
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {

}
