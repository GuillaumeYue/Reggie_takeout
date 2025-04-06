package com.finalPrj.reggie.dto;

import com.finalPrj.reggie.entity.Setmeal;
import com.finalPrj.reggie.entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
