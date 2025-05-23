package com.finalPrj.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.finalPrj.reggie.common.CustomException;
import com.finalPrj.reggie.dto.DishDto;
import com.finalPrj.reggie.entity.Dish;
import com.finalPrj.reggie.entity.DishFlavor;
import com.finalPrj.reggie.mapper.DishMapper;
import com.finalPrj.reggie.service.DishFlavorService;
import com.finalPrj.reggie.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DishServiceImpl extends ServiceImpl<DishMapper,Dish> implements DishService {

    @Autowired
    private DishFlavorService dishFlavorService;

    /**
     * 新增菜品，同时保存对应的口味数据
     * @param dishDto
     */
    @Transactional
    public void saveWithFlavor(DishDto dishDto) {
        //保存菜品的基本信息到菜品表dish
        this.save(dishDto);

        Long dishId = dishDto.getId();//菜品id

        //菜品口味
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors = flavors.stream().map((item) -> {
            item.setDishId(dishId);
            return item;
        }).collect(Collectors.toList());

        //保存菜品口味数据到菜品口味表dish_flavor
        dishFlavorService.saveBatch(flavors);

    }

    /**
     * 根据id查询菜品信息和对应的口味信息
     * @param id
     * @return
     */
    public DishDto getByIdWithFlavor(Long id) {
        //查询菜品基本信息，从dish表查询
        Dish dish = this.getById(id);

        DishDto dishDto = new DishDto();
        BeanUtils.copyProperties(dish,dishDto);

        //查询当前菜品对应的口味信息，从dish_flavor表查询
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId,dish.getId());
        List<DishFlavor> flavors = dishFlavorService.list(queryWrapper);
        dishDto.setFlavors(flavors);

        return dishDto;
    }

    @Override
    @Transactional
    public void updateWithFlavor(DishDto dishDto) {
        //更新dish表基本信息
        this.updateById(dishDto);

        //清理当前菜品对应口味数据---dish_flavor表的delete操作
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(DishFlavor::getDishId,dishDto.getId());

        dishFlavorService.remove(queryWrapper);

        //添加当前提交过来的口味数据---dish_flavor表的insert操作
        List<DishFlavor> flavors = dishDto.getFlavors();

        flavors = flavors.stream().map((item) -> {
            item.setDishId(dishDto.getId());
            return item;
        }).collect(Collectors.toList());

        dishFlavorService.saveBatch(flavors);
    }

    /**
     * 批量删除
     * @param ids
     */
    @Override
    public void removeWithFlavor(List<Long> ids) {
        // 查询是否有启售中的菜品，不能删
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Dish::getId, ids).eq(Dish::getStatus, 1);

        int count = this.count(queryWrapper);
        if (count > 0) {
            throw new CustomException("选中菜品中有启售状态，不能删除");
        }

        // 1. 删除 dish 表中的数据
        this.removeByIds(ids);

        // 2. 删除 dish_flavor 表中相关数据
        LambdaQueryWrapper<DishFlavor> flavorWrapper = new LambdaQueryWrapper<>();
        flavorWrapper.in(DishFlavor::getDishId, ids);
        dishFlavorService.remove(flavorWrapper);
    }

    /**
     * 批量启售/停售
     * @param status
     * @param ids
     */
    @Override
    @Transactional
    public void updateStatusBatch(int status, List<Long> ids) {
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Dish::getId, ids);

        List<Dish> dishes = this.list(queryWrapper);

        for (Dish dish : dishes) {
            dish.setStatus(status); // 修改状态
        }

        this.updateBatchById(dishes);
    }



}
