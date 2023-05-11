package com.example.ea_backstage.controller;


import com.example.ea_backstage.service.POIServiceI;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class PoiController {

    @Resource
    POIServiceI poiServiceI;

//    @RequestMapping("/userexport")
//    public ResponseEntity<Void> exportuser(HttpServletResponse response) {
//        poiServiceI.exportUserinfo(response);
//        return ResponseEntity.ok().build();
//    }

    @RequestMapping(value = "/punchreport")
    public void PunchExcel(HttpServletResponse response) {
        XSSFWorkbook wb = poiServiceI.exportpunchreport();
        String fileName = "用户打卡报表" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + ".xlsx";
        OutputStream outputStream = null;
        try {
            fileName = URLEncoder.encode(fileName,"UTF-8");
            //设置ContentType请求信息格式
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName);
            outputStream = response.getOutputStream();
            wb.write(outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/leavereport")
    public void LeaveExcel(HttpServletResponse response) {
        XSSFWorkbook wb = poiServiceI.exportleavereport();
        String fileName = "用户请假报表" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + ".xlsx";
        OutputStream outputStream = null;
        try {
            fileName = URLEncoder.encode(fileName,"UTF-8");
            //设置ContentType请求信息格式
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName);
            outputStream = response.getOutputStream();
            wb.write(outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/mainreport")
    public void MainExcel(HttpServletResponse response) {
        XSSFWorkbook wb = poiServiceI.mainreport();
        String fileName = "总报表" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + ".xlsx";
        OutputStream outputStream = null;
        try {
            fileName = URLEncoder.encode(fileName,"UTF-8");
            //设置ContentType请求信息格式
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName);
            outputStream = response.getOutputStream();
            wb.write(outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/userreport")
    public void userreport(HttpServletResponse response) {
        XSSFWorkbook wb = poiServiceI.userreport();
        String fileName = "用户报表" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + ".xlsx";
        OutputStream outputStream = null;
        try {
            fileName = URLEncoder.encode(fileName,"UTF-8");
            //设置ContentType请求信息格式
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName);
            outputStream = response.getOutputStream();
            wb.write(outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
