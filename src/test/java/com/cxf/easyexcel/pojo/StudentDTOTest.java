package com.cxf.easyexcel.pojo;

import com.cxf.easyexcel.utils.test.ExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.*;
import java.util.List;

@Slf4j
public class StudentDTOTest {

    @Test
    public void readExcel(){
        String filePath = "/Users/always_on_the_way/Desktop/test.xlsx";

        FileInputStream inputStream = null;
        try {
             inputStream = new FileInputStream(filePath);
            List<StudentDTO> list = ExcelUtil.readExcel(new BufferedInputStream(inputStream), StudentDTO.class);
            log.info(list+"");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @Test
    public void writeExcel(){

        File file = new File("/Users/always_on_the_way/Desktop/学生表.xlsx");

        String filePath = "/Users/always_on_the_way/Desktop/test.xlsx";

        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(filePath);
            List<StudentDTO> list = ExcelUtil.readExcel(new BufferedInputStream(inputStream), StudentDTO.class);
            ExcelUtil.writeExcel(file,list);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}