package com.example.ea_backstage.service;

import com.alibaba.fastjson.JSONObject;
import com.example.ea_backstage.bean.Leave;
import com.example.ea_backstage.bean.Punch;
import com.example.ea_backstage.bean.Report;
import com.example.ea_backstage.bean.User;
import com.example.ea_backstage.mapper.ReportMapper;
import com.example.ea_backstage.mapper.UserMapper;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
public class POIServiceImpl implements POIServiceI {
    @Resource
    UserMapper userMapper;
    @Resource
    ReportMapper reportMapper;

    @Override
    public void exportUserinfo(HttpServletResponse response) {

        Logger LOGGER = LoggerFactory.getLogger(this.getClass());

        List<String> User_INFO_COLUMNS = new ArrayList<>(
                Arrays.asList("工号", "姓名", "性别", "部门", "联系方式", "邮箱", "学校", "生日", "学历", "地址", "入职日期", "角色")
        );
        List<String> User_COLUMNS = new ArrayList<>(
                Arrays.asList("username", "name", "gender", "dep。department_name", "phone", "email",
                        "school", "birthday", "education", "address", "dateofentry", "role")
        );

        List<User> userList = userMapper.findAllUser();

        Map<String, List<User>> goodInfoMap = userList.stream()
                .collect(Collectors.groupingBy(item -> item.getUsername().toString()));
        // 创建一个空的excel表格
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 创建一个sheet页
        HSSFSheet sheet = workbook.createSheet("用户列表");
        // 创建表头
        HSSFRow row = sheet.createRow(0);
        // 创建表头格式style对象
        HSSFCellStyle style = workbook.createCellStyle();
        // 创建字体对象
        HSSFFont font = workbook.createFont();
        //字体设置为红色
        font.setColor(IndexedColors.RED.index);
        //字体加粗
        font.setBold(true);
        style.setFont(font);
        // 设置单元格水平居中
        style.setAlignment(HorizontalAlignment.CENTER);
        // 设置单元格填充色
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFillForegroundColor(IndexedColors.YELLOW.index);
        Map<Integer, Integer> columnWidthMap = new HashMap<>();

        // 写入表头
        for (int i = 0; i < User_INFO_COLUMNS.size(); i++) {
            // 创建单元格
            HSSFCell cell = row.createCell(i);
            cell.setCellStyle(style);
            // 设置单元格宽度
            sheet.setColumnWidth(i, User_INFO_COLUMNS.get(i).getBytes().length * 256);
            // 将表头值字符长度保存到columnWidthMap中
            columnWidthMap.put(i, User_INFO_COLUMNS.get(i).getBytes().length);
            // 设置表有值
            cell.setCellValue(User_INFO_COLUMNS.get(i));
        }
        // 记录正文行号
        int page = 1;
        // 创建正文格式style对象
        style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        // 遍历正文数据
        for (Map.Entry<String, List<User>> entry : goodInfoMap.entrySet()) {
            // 记录每组数据起始行数
            int firstRow = page;
            for (User goodInfo : entry.getValue()) {
                // 创建正文行
                HSSFRow goodRow = sheet.createRow(page);
                Map<String, Object> map = JSONObject.parseObject(JSONObject.toJSONString(goodInfo), Map.class);
                for (int i = 0; i < User_COLUMNS.size(); i++) {
                    // 创建单元格
                    HSSFCell cell = goodRow.createCell(i);
                    cell.setCellStyle(style);
                    Object object = map.get(User_COLUMNS.get(i));
                    String value = object == null ? "" : object.toString();
                    // 设置单元格狂歌为字段值最大的宽度
                    int headerLength = columnWidthMap.get(i);
                    int valueLength = value.getBytes().length;
                    int columnWidth = headerLength > valueLength ? headerLength : valueLength;
                    columnWidthMap.put(i, columnWidth);
                    sheet.setColumnWidth(i, columnWidth * 256);
                    cell.setCellValue(value);
                }
                page++;
            }
            // 记录每组数据末尾行数
            int lastRow = firstRow + entry.getValue().size() - 1;
            // 合并相同字段的单元格
            if (firstRow < lastRow) {
                for (int j = 0; j < 4; j++) {
                    CellRangeAddress region = new CellRangeAddress(firstRow, lastRow, j, j);
                    sheet.addMergedRegion(region);
                }
            }
        }
        response.setContentType("application/vnd.ms-excel");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String fileName = new String(("userList" + sdf.format(new Date()) + ".xlsx"));
        try {
            OutputStream os = response.getOutputStream();
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            workbook.write(os);
        } catch (Exception e) {
            LOGGER.error("fail: ", e);
        }
    }

   @Override
    public XSSFWorkbook userreport() {

        List<User> list = userMapper.findAllUser();
        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet("Userlist");
        Row titleRow = sheet.createRow(0);
        titleRow.createCell(0).setCellValue("编号");
        titleRow.createCell(1).setCellValue("工号");
        titleRow.createCell(2).setCellValue("姓名");
        titleRow.createCell(3).setCellValue("性别");
        titleRow.createCell(4).setCellValue("毕业院校");
        titleRow.createCell(5).setCellValue("学历");
        titleRow.createCell(6).setCellValue("手机");
       titleRow.createCell(7).setCellValue("部门");
       titleRow.createCell(8).setCellValue("生日");
       titleRow.createCell(9).setCellValue("邮箱");
       titleRow.createCell(10).setCellValue("入职日期");
       titleRow.createCell(11).setCellValue("身份");
        int cell = 1;
        for (User user : list) {
            Row row = sheet.createRow(cell);
            row.createCell(0).setCellValue(user.getId());
            row.createCell(1).setCellValue(user.getUsername());
            row.createCell(2).setCellValue(user.getName());
            row.createCell(3).setCellValue(user.getGender());
            row.createCell(4).setCellValue(user.getSchool());
            row.createCell(5).setCellValue(user.getEducation());
            row.createCell(6).setCellValue(user.getPhone());
            row.createCell(7).setCellValue(user.getDep().getDepartment_name());
            row.createCell(8).setCellValue(user.getBirthday());
            row.createCell(9).setCellValue(user.getEmail());
            row.createCell(10).setCellValue(user.getDateofentry());
            row.createCell(11).setCellValue(user.getRole());

            cell++;

        }
        return wb;
    }


    @Override
    public XSSFWorkbook exportpunchreport() {

        List<Punch> list = reportMapper.findAllpunch();
        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet("Punchlist");
        Row titleRow = sheet.createRow(0);
        titleRow.createCell(0).setCellValue("编号");
        titleRow.createCell(1).setCellValue("工号");
        titleRow.createCell(2).setCellValue("姓名");
        titleRow.createCell(3).setCellValue("部门");
        titleRow.createCell(4).setCellValue("签到时间");
        titleRow.createCell(5).setCellValue("签退时间");
        titleRow.createCell(6).setCellValue("签到结果");
        int cell = 1;
        for (Punch punch : list) {
            Row row = sheet.createRow(cell);
            row.createCell(0).setCellValue(punch.getId());
            row.createCell(1).setCellValue(punch.getUsername());
            row.createCell(2).setCellValue(punch.getUser().getName());
            row.createCell(3).setCellValue(punch.getUser().getDep().getDepartment_name());
            row.createCell(4).setCellValue(punch.getPunchtime1());
            row.createCell(5).setCellValue(punch.getPunchtime2());
            row.createCell(6).setCellValue(punch.getPr().getPunchresult());
            cell++;

        }
        return wb;
    }

    @Override
    public XSSFWorkbook exportleavereport() {

        List<Leave> list = reportMapper.findAllleave();
        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet("Leavelist");
        Row titleRow = sheet.createRow(0);
        titleRow.createCell(0).setCellValue("编号");
        titleRow.createCell(1).setCellValue("工号");
        titleRow.createCell(2).setCellValue("姓名");
        titleRow.createCell(3).setCellValue("部门");
        titleRow.createCell(4).setCellValue("请假开始时间");
        titleRow.createCell(5).setCellValue("请假结束时间");
        titleRow.createCell(6).setCellValue("请假类型");
        titleRow.createCell(7).setCellValue("请假理由");
        titleRow.createCell(8).setCellValue("请假结果");
        int cell = 1;
        for (Leave leave : list) {
            Row row = sheet.createRow(cell);
            row.createCell(0).setCellValue(leave.getId());
            row.createCell(1).setCellValue(leave.getUser().getUsername());
            row.createCell(2).setCellValue(leave.getUser().getName());
            row.createCell(3).setCellValue(leave.getUser().getDep().getDepartment_name());
            row.createCell(4).setCellValue(leave.getStarttime());
            row.createCell(5).setCellValue(leave.getEndtime());
            row.createCell(6).setCellValue(leave.getLts().getLeavetypes());
            row.createCell(7).setCellValue(leave.getReasons());
            row.createCell(8).setCellValue(leave.getRs().getResult());
            cell++;

        }
        return wb;
    }

    @Override
    public XSSFWorkbook mainreport() {

        List<User> list = reportMapper.findallreport();
        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet("Reportlist");
        Row titleRow = sheet.createRow(0);
        titleRow.createCell(0).setCellValue("工号");
        titleRow.createCell(1).setCellValue("姓名");
        titleRow.createCell(2).setCellValue("部门");
        titleRow.createCell(3).setCellValue("出勤天数");
        titleRow.createCell(4).setCellValue("请假天数");
        int cell = 1;
        for (User user : list) {
            Row row = sheet.createRow(cell);
            row.createCell(0).setCellValue(user.getId());
            row.createCell(1).setCellValue(user.getUsername());
            row.createCell(2).setCellValue(user.getDep().getDepartment_name());
            row.createCell(3).setCellValue(user.getCountpunch());
            row.createCell(4).setCellValue(user.getAllleavedays());
            cell++;

        }
        return wb;
    }


}

