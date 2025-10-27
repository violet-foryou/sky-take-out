package com.sky.service;

import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.result.PageResult;

public interface EmployeeService {

    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    /**
     * 新增员工
     * @param employeeDTO
     */
     void addnew(EmployeeDTO employeeDTO);

    /**
     * 员工分页查询
     * @param employeePageQueryDTO
     * @return
     */
    PageResult pagequery(EmployeePageQueryDTO employeePageQueryDTO);

    /**
     * 变更员工信息
     * @param status
     * @param id
     */
    void empchange(Integer status, Long id);

    Employee getbyid(Long id);

    void update(EmployeeDTO employeeDTO);

}
