package com.example.ea_backstage.service;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletResponse;

public interface POIServiceI {

    public void exportUserinfo(HttpServletResponse response);
    public XSSFWorkbook exportpunchreport();

    public XSSFWorkbook exportleavereport();

    public XSSFWorkbook mainreport();

    public XSSFWorkbook userreport();

}
