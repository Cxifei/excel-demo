package com.cxf.easyexcel.controller;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.cxf.easyexcel.mapper.ProductMapper;
import com.cxf.easyexcel.pojo.Product;
import com.cxf.easyexcel.utils.ssm.*;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.cxf.easyexcel.dto.*;
import com.cxf.easyexcel.service.*;

@Api("excel接口")
@Controller
@CrossOrigin
public class ProductController {

    private static final Logger log=LoggerFactory.getLogger(ProductController.class);

    private static final String prefix="product";

    @Value("${poi.excel.sheet.name}")
    private String sheetProductName;

    @Value("${poi.excel.file.name}")
    private String excelProductName;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private PoiService poiService;

    @Autowired
    private IProductService productService;

    /**
     * 获取产品列表
     * @param name
     * @return
     */
    @RequestMapping(value=prefix+"/list",method=RequestMethod.GET)
    @ResponseBody
    public List<Product> list(String name){
        List<Product> products=new ArrayList<Product>();
        try {
            products=productMapper.selectAll(name);
        } catch (Exception e) {
            log.error("获取产品列表发生异常: ",e.fillInStackTrace());
        }

        return products;
    }

    /**
     * 导出excel
     * @param response
     * @return
     */
    @RequestMapping(value=prefix+"/excel/export",method=RequestMethod.GET)
    @ResponseBody
    public String exportExcel(HttpServletResponse response,String search){
        try {
            List<Product> products = productMapper.selectAll(search);
            String[] headers=new String[]{"id编号","名称","单位","单价","库存量","采购日期","备注信息"};
            List<Map<Integer, Object>> dataList=ExcelBeanUtil.manageProductList(products);
            log.info("excel下载填充数据： {} ",dataList);

            Workbook wb=new HSSFWorkbook();
            ExcelUtil.fillExcelSheetData(dataList, wb, headers, sheetProductName);
            WebUtil.downloadExcel(response, wb, excelProductName);
            return excelProductName;
        } catch (Exception e) {
            log.error("下载excel 发生异常：",e.fillInStackTrace());
        }
        return null;
    }

    /**
     * 上传excel导入数据
     * @param request
     * @return
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value=prefix+"/excel/upload",method=RequestMethod.POST,consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public BaseResponse uploadExcel(MultipartHttpServletRequest request){
        BaseResponse response=new BaseResponse<>(StatusCode.Success);
        try {
            MultipartFile file=request.getFile("productFile");
            if (file==null || file.getName()==null) {
                return new BaseResponse<>(StatusCode.Invalid_Param);
            }
            String fileName=file.getOriginalFilename();
            String suffix=StringUtils.substring(fileName, fileName.lastIndexOf(".")+1);
            if (WorkBookVersion.valueOfSuffix(suffix)==null) {
                return new BaseResponse<>(StatusCode.WorkBook_Version_Invalid);
            }
            log.info("文件名：{} 文件后缀名：{} ",fileName,suffix);

            Workbook wb=poiService.getWorkbook(file,suffix);
            List<Product> products=poiService.readExcelData(wb);

            //批量插入-第一种方法
            //productService.insertBatch(products);

            //批量插入-第二种方法(注意jdbc链接mysql允许批量插入删除的配置)
            productMapper.insertBatch(products);
        } catch (Exception e) {
            log.error("上传excel导入数据 发生异常：",e.fillInStackTrace());
            return new BaseResponse<>(StatusCode.System_Error);
        }
        return response;
    }

}

