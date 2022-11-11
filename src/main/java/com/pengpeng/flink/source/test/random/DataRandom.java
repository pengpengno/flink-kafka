package com.pengpeng.flink.source.test.random;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson2.JSON;
import com.pengpeng.flink.source.test.pojo.Company;
import com.pengpeng.flink.source.test.pojo.Employee;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class DataRandom {

    public static String get1ComPanyJsonData(){
        Company company = new Company();
        company.setCompanyName(getRandomCompanyName());
        company.setEmployees(getRandomEmList(5));
        return JSON.toJSONString(company);
    }

    /**
     * 获取一条随机数据
     * @return
     */
    public static  String  get1RandomJsonData(){
        Employee employee = new Employee();
        employee.setUserName(getRandomUsername());
        employee.setArea(getRandomArea());
        employee.setTime(getCurrentTime());
        return JSON.toJSONString(employee);
    }
    /**
     * 获取一条随机数据
     * @return
     */
    public static List<Employee> getRandomEmList(Integer limit){
        ArrayList<Employee> employees = CollectionUtil.newArrayList();
        if (limit <= 0){
            return employees;
        }
        for (int i = 0; i < limit; i++) {
            Employee employee = new Employee();
            employee.setUserName(getRandomUsername());
            employee.setArea(getRandomArea());
            employee.setTime(getCurrentTime());
            employees.add(employee);
        }
        return employees;
    }

    /**
     * 获取一条随机数据
     * @return
     */
    public static  String  getRandomJsonData(Integer limit){
        List<Employee> employees = getRandomEmList(limit);
        return JSON.toJSONString(employees);
    }

    public static String getCurrentTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }

    /**
     * 生成随机公司信息
     * @return
     */
    public static String getRandomCompanyName(){
        String[] types = {"上海火星云","北京火星云","南京火星云","杭州火星云"};
        Random random = new Random();
        int i = random.nextInt(types.length);
        return types[i];
    }

    public static String getRandomArea(){
        String[] types = {"AREA_US","AREA_CT","AREA_AR","AREA_IN","AREA_ID"};
        Random random = new Random();
        int i = random.nextInt(types.length);
        return types[i];
    }


    public static String getRandomType(){
        String[] types = {"jerry","tom","black","steve","peng.wang"};
        Random random = new Random();
        int i = random.nextInt(types.length);
        return types[i];
    }

    /**
     * 获取随机薪资
     * @return
     */
    public static Long getRandomSalary(){
//        String[] types = {"11223","tom","black","steve","child_unshelf"};
//        char c = RandomUtil.randomChinese();
        long salary = RandomUtil.randomLong(5);
        return salary;
    }


    public static String getRandomUsername(){
        String[] types = {"jerry","tom","black","steve","peng.wang","王鹏"};
        Random random = new Random();
        int i = random.nextInt(types.length);
        return types[i];
    }

}
