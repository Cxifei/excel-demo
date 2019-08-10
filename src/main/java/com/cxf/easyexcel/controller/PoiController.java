package com.cxf.easyexcel.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.cxf.easyexcel.service.PoiService;
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
import com.cxf.easyexcel.mapper.ProductMapper;
import com.cxf.easyexcel.pojo.Product;
import com.cxf.easyexcel.utils.ssm.ExcelBeanUtil;
import com.cxf.easyexcel.utils.ssm.ExcelUtil;
import com.cxf.easyexcel.utils.ssm.WebUtil;

/**
 * 导入导出controller
 * @author zhonglinsen
 *
 */
@Api("poi接口")
@Controller
@CrossOrigin
public class PoiController {

    private static final Logger log=LoggerFactory.getLogger(PoiController.class);

    private static final String prefix="poi";

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private PoiService poiService;

    @Value("${poi.excel.sheet.name}")
    private String sheetProductName;

    @Value("${poi.excel.file.name}")
    private String excelProductName;

    /**
     * 获取产品列表-可用于搜索
     * @param name
     * @return
     */
    @RequestMapping(value=prefix+"/list",method=RequestMethod.GET)
    @ResponseBody
    public BaseResponse<List<Product>> list(String name){
        BaseResponse<List<Product>> response=new BaseResponse<List<Product>>(StatusCode.Success);
        try {
            List<Product> products=productMapper.selectAll(name);
            response.setData(products);
        } catch (Exception e) {
            log.error("获取产品列表发生异常: ",e.fillInStackTrace());
        }

        return response;
    }

    /**
     * 下载excel
     * @param response
     * @return
     */
    @RequestMapping(value=prefix+"/excel/export",method=RequestMethod.GET)
    @ResponseBody
    public String exportExcel(HttpServletResponse response,String search){
        try {
            List<Product> products=productMapper.selectAll(search);
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
     * 1、不要忘了支持springmvc上传文件的配置
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value=prefix+"/excel/upload",method=RequestMethod.POST,consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public BaseResponse uploadExcel(MultipartHttpServletRequest request){
        BaseResponse response=new BaseResponse<>(StatusCode.Success);
        try {
            String version=request.getParameter("version");
            MultipartFile file=request.getFile("productFile");
            if (StringUtils.isEmpty(version) || file==null) {
                return new BaseResponse<>(StatusCode.Invalid_Param);
            }
            log.debug("版本号：{} file：{} ",version,file);

            HSSFWorkbook wb=new HSSFWorkbook(file.getInputStream());
            List<Product> products=poiService.readExcelData(wb);
            productMapper.insertBatch(products);
        } catch (Exception e) {
            log.error("上传excel导入数据 发生异常：",e.fillInStackTrace());
            return new BaseResponse<>(StatusCode.System_Error);
        }
        return response;
    }
}

