package com.finalPrj.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.finalPrj.reggie.dto.SetmealDto;
import com.finalPrj.reggie.entity.Setmeal;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {
    /**
     * 新增套餐，同时需要保存套餐和菜品的关联关系
     * @param setmealDto
     */
    public void saveWithDish(SetmealDto setmealDto);

    /**
     * 删除套餐，同时需要删除套餐和菜品的关联数据
     * @param ids
     */
    public void removeWithDish(List<Long> ids);

    /**
     * 修改套餐，同时更新套餐和菜品的关联关系
     * @param setmealDto
     */
    public void updateWithDish(SetmealDto setmealDto);

    /**
     * 根据id查询套餐和菜品的关联数据，用于回显
     * @param id
     * @return
     */
    public SetmealDto getByIdWithDish(Long id);

    void updateSetmealStatus(int status, List<Long> ids);
}
