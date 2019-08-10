package com.cxf.easyexcel.service;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.cxf.easyexcel.dto.WorkBookVersion;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cxf.easyexcel.pojo.Product;
import com.cxf.easyexcel.utils.ssm.*;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PoiService {

    private static final Logger log=LoggerFactory.getLogger(PoiService.class);

    /**
     * 读取excel数据
     * @param wb
     * @return
     * @throws Exception
     */
    public List<Product> readExcelData(Workbook wb) throws Exception{
        Product product=null;

        List<Product> products=new ArrayList<Product>();
        Row row=null;
        int numSheet=wb.getNumberOfSheets();
        if (numSheet>0) {
            for(int i=0;i<numSheet;i++){
                Sheet sheet=wb.getSheetAt(i);
                int numRow=sheet.getLastRowNum();
                if (numRow>0) {
                    for(int j=1;i<numRow;i++){
                        //TODO：跳过excel sheet表格头部
                        row=sheet.getRow(j);
                        product=new Product();

                        String name=ExcelUtil.manageCell(row.getCell(1), null);
                        String unit=ExcelUtil.manageCell(row.getCell(2), null);
                        Double price=Double.valueOf(ExcelUtil.manageCell(row.getCell(3), null));
                        String stock=ExcelUtil.manageCell(row.getCell(4), null);
                        String remark=ExcelUtil.manageCell(row.getCell(6), null);

                        product.setName(name);
                        product.setUnit(unit);
                        product.setPrice(price);
                        product.setStock(Double.valueOf(stock));
                        String value=ExcelUtil.manageCell(row.getCell(5), "yyyy-MM-dd");
                        product.setPurchaseDate(DateUtil.strToDate(value, "yyyy-MM-dd"));
                        product.setRemark(remark);

                        products.add(product);
                    }
                }
            }
        }

        log.info("获取数据列表: {} ",products);
        return products;
    }

    /**
     * 根据版本来区分获取workbook实例
     * @param version
     * @param inputStream
     * @return
     * @throws Exception
     */
    public Workbook getWorkbook(String version,InputStream inputStream) throws Exception{
        Workbook wk=null;
        if (Objects.equals(WorkBookVersion.WorkBook2003.getCode(), version)) {
            wk=new HSSFWorkbook(inputStream);
        }else if (Objects.equals(WorkBookVersion.WorkBook2007.getCode(), version)) {
            wk=new XSSFWorkbook(inputStream);
        }

        return wk;
    }

    /**
     * 根据file区分获取workbook实例
     * @param file
     * @param suffix
     * @return
     * @throws Exception
     */
    public Workbook getWorkbook(MultipartFile file, String suffix) throws Exception{
        Workbook wk=null;
        if (Objects.equals(WorkBookVersion.WorkBook2003Xls.getCode(), suffix)) {
            wk=new HSSFWorkbook(file.getInputStream());
        }else if (Objects.equals(WorkBookVersion.WorkBook2007Xlsx.getCode(), suffix)) {
            wk=new XSSFWorkbook(file.getInputStream());
        }

        return wk;
    }

}

