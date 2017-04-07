package com.qianwang.shopping_car.bean;

import java.util.List;

/**
 * Created by sky on 2017/4/1.
 */

public class DishMenu {

   private String menuName;
    private List<Dish> mDishList;

    public DishMenu(String menuName, List<Dish> dishList) {
        this.menuName = menuName;
        mDishList = dishList;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public List<Dish> getDishList() {
        return mDishList;
    }

    public void setDishList(List<Dish> dishList) {
        mDishList = dishList;
    }
}
