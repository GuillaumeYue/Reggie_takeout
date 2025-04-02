package com.finalPrj.reggie.dto;

import com.finalPrj.reggie.entity.Dish;
import com.finalPrj.reggie.entity.DishFlavor;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class DishDto extends Dish {

    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}
