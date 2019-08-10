package com.cxf.easyexcel.utils.ssm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cxf.easyexcel.pojo.Product;

/**
 * 导入excel bean数据工具类
 * @author zhonglinsen
 *
 */
public class ExcelBeanUtil {

    /**
     * 处理产品列表 塞入list-map 等待塞入excel的workbook进行处理
     * @param products
     * @return
     */
    public static List<Map<Integer, Object>> manageProductList(final List<Product> products){
        List<Map<Integer, Object>> dataList=new ArrayList<>();
        if (products!=null && products.size()>0) {
            int length=products.size();

            Map<Integer, Object> dataMap;
            Product bean;
            for (int i = 0; i < length; i++) {
                bean=products.get(i);

                dataMap=new HashMap<>();
                dataMap.put(0, bean.getId());
                dataMap.put(1, bean.getName());
                dataMap.put(2, bean.getUnit());
                dataMap.put(3, bean.getPrice());
                dataMap.put(4, bean.getStock());
                dataMap.put(5, bean.getPurchaseDate());
                dataMap.put(6, bean.getRemark());
                dataList.add(dataMap);
            }
        }
        return dataList;
    }
}


