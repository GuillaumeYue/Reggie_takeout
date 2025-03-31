package com.finalPrj.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.finalPrj.reggie.entity.Category;

public interface CategoryService extends IService<Category> {
    //remove方法
    public void remove(Long ids);
}
