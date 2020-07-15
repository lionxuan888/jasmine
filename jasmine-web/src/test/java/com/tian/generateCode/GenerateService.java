package com.tian.generateCode;

import com.google.common.base.CaseFormat;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by xiaoxuan.jin on 2015/1/9.
 */
public class GenerateService {
    private Connection conn ;

    public GenerateService(String url, String userName, String pwd) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, userName, pwd);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static Map<String,String> columnMap = Maps.newHashMap();
    private static Map<String,String> templateNameMap = Maps.newHashMap();
    static {
        columnMap.put("bigint","Long");
        columnMap.put("boolean","Boolean");
        columnMap.put("char","String");
        columnMap.put("varchar","String");
        columnMap.put("datetime","Date");
        columnMap.put("timestamp","Date");
        columnMap.put("date","Date");
        columnMap.put("int","Integer");
        columnMap.put("smallint","Integer");
        columnMap.put("tinyint","Integer");
        columnMap.put("decimal","BigDecimal");
        columnMap.put("double","BigDecimal");
        columnMap.put("float","BigDecimal");

        templateNameMap.put("DO.java", "Model.ftl");
        templateNameMap.put("Mapper.xml", "Mapper.ftl");
        templateNameMap.put("Dao.java", "Dao.ftl");
        templateNameMap.put("Service.java", "Service.ftl");
        templateNameMap.put("ServiceImpl.java", "ServiceImpl.ftl");
//        templateNameMap.put("SearchBox.java", "BaseSearchBox.ftl");
//        templateNameMap.put("ModelSearchBox.java", "SearchBox.ftl");
        templateNameMap.put("Controller.java", "Controller.ftl");
    }

    private List<PropertyModel> getPropertyModel(String schema, String tableName)throws Exception{
        List<PropertyModel> list = Lists.newArrayList();
        String sql = "SELECT COLUMN_NAME,DATA_TYPE,COLUMN_COMMENT FROM INFORMATION_SCHEMA.COLUMNS WHERE table_schema = ? AND table_name = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setString(1, schema);
        preparedStatement.setString(2, tableName);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()){
            PropertyModel model = new PropertyModel();
            String columnName = rs.getString(1);
            model.setColumnName(columnName);
            model.setLname(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, columnName));
            model.setUname(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, columnName));
            model.setType(columnMap.get(rs.getString(2)));
            model.setDesc(rs.getString(3));

            list.add(model);
        }
        rs.close();
        preparedStatement.close();
        return list;
    }

    private ClassModel getClassModel(String schema,String tableName)throws Exception{
        String sql = "SELECT TABLE_COMMENT FROM INFORMATION_SCHEMA.TABLES  WHERE table_schema = ? AND table_name = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setString(1, schema);
        preparedStatement.setString(2, tableName);
        ResultSet rs = preparedStatement.executeQuery();
        if(!rs.next()){
            rs.close();
            preparedStatement.close();
            conn.close();
            return new ClassModel();
        }

        ClassModel model = new ClassModel();
        model.setTableName(tableName);
        model.setSchema(schema);
        model.setLname(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, tableName));
        model.setUname(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, tableName));
        model.setDesc(rs.getString(1));

        rs.close();
        preparedStatement.close();

        return  model;
    }

    public void generateTemplateContent(String templateName, Map<String, Object> templateObject,String fileName) throws IOException, TemplateException {
        if(CollectionUtils.isEmpty(templateObject)){
            return ;
        }
        // 读取模板并设置模板内容
        Configuration configuration = new Configuration();
        configuration.setTemplateLoader(new ClassTemplateLoader(this.getClass(), "/template"));
        configuration.setEncoding(Locale.CHINA, "UTF-8");
        Template template = configuration.getTemplate(templateName);
        File file = new File(fileName);
        if (file.getParentFile() != null) {
            file.getParentFile().mkdirs();
        }
        OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file),"UTF-8");
        template.process(templateObject, writer);
    }

    private void generateCode(String filePath,String schema,String tableName)throws Exception{
        ClassModel classModel = getClassModel(schema,tableName);
        List<PropertyModel> propertyModelList = getPropertyModel(schema, tableName);

        Map<String, Object> templateObject = Maps.newConcurrentMap();
        templateObject.put("classModel",classModel);
        templateObject.put("propertyModelList",propertyModelList);


        for (Map.Entry<String,String> entry:templateNameMap.entrySet()){
            String fileName = getFilePath(filePath, entry.getKey())+classModel.getUname()+entry.getKey();
            if(StringUtils.contains(entry.getValue(),"Base")){
                fileName = getFilePath(filePath, entry.getKey())+entry.getKey();
            }
            generateTemplateContent(entry.getValue(), templateObject, fileName);
        }
    }

    private String getFilePath(String filePath, String key){
        filePath = StringUtils.endsWith(filePath,"/") ? filePath : filePath + "/";
        if (key.contains("DO")) {
            filePath = filePath + "model/";
        } else if (key.contains("Mapper")) {
            filePath = filePath + "mapper/";
        }else if (key.contains("Dao")) {
            filePath = filePath + "dao/";
        }else if (key.contains("Service")) {
            filePath = filePath + "service/";
        }else if (key.contains("Controller")) {
            filePath = filePath + "controller/";
        }

        return filePath;
    }

    public void generateCode(String schema,String[] tableNames,String filePath){
        try{
            for (String tableName:tableNames){
                generateCode(filePath,schema,tableName);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String url = "jdbc:mysql://10.38.161.220:3306/scf_fund_center?useUnicode=true&characterEncoding=utf8";
        String user = "mifi_admin";
        String pwd = "OTg1NjJjNzlkNmI4ZTQzMGJhODRiMDIw";
        GenerateService generateService = new GenerateService(url, user, pwd);
        String[] tables = new String[]{"cmb_finance_apply"};
        String filePath = "/Users/admin/Documents/code";
        generateService.generateCode("scf_fund_center", tables, filePath);
        System.out.println("生成完成");
    }
}
