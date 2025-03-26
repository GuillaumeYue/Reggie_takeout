package com.finalPrj.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.finalPrj.reggie.entity.Employee;
import com.finalPrj.reggie.mapper.EmployeeMapper;
import com.finalPrj.reggie.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
