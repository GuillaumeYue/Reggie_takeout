package com.finalPrj.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.finalPrj.reggie.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
