package com.qianwang.shopping_car.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qianwang.shopping_car.R;
import com.qianwang.shopping_car.bean.Dish;
import com.qianwang.shopping_car.bean.DishMenu;
import com.qianwang.shopping_car.bean.ShopCart;
import com.qianwang.shopping_car.imp.ShopCartImp;

import java.util.List;


/**
 * Created by sky on 2017/4/1.
 */

public class RightMenuAdapter extends RecyclerView.Adapter {

    private List<DishMenu> mList;
    private final int MENU_TYPE = 0;
    private final int DISH_TYPE = 1;
    private int dishCount;
    private ShopCart mShopCart;
    private ShopCartImp shopCartImp;

    public RightMenuAdapter(List<DishMenu> list, ShopCart shopCart) {
        mList = list;
        this.mShopCart = shopCart;
        for (DishMenu menu : mList) {
            dishCount += menu.getDishList().size() + 1;
        }
    }

    public void setShopCartImp(ShopCartImp shopCartImp) {
        this.shopCartImp = shopCartImp;
    }

    @Override
    public int getItemViewType(int position) {

        int sum = 0;
        for (DishMenu menu : mList) {

            if (position == sum) {
                return MENU_TYPE;
            } else {
                sum += menu.getDishList().size() + 1;
            }
        }
        return DISH_TYPE;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == MENU_TYPE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.right_menu_item, parent, false);
            TitleHoder mTitleHoder = new TitleHoder(view);
            return mTitleHoder;
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.right_dish_item, parent, false);
            ViewHoder mViewHoder = new ViewHoder(view);
            return mViewHoder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (getItemViewType(position) == MENU_TYPE) {

            TitleHoder mTitleHoder = (TitleHoder) holder;  //不能作为全局变量  ，应该通过onBindViewHolder 中的holder相关联；
            if (mTitleHoder != null) {
                mTitleHoder.tv_right_menu.setText(getMenuByPosition(position).getMenuName());
                mTitleHoder.right_menu_layout.setContentDescription(position + "");
            }


        } else {
            final ViewHoder mViewHoder = (ViewHoder) holder;
            if (mViewHoder != null) {

                final Dish dish = getDishByPosition(position);
                mViewHoder.right_dish_name.setText(dish.getDishName());
                mViewHoder.right_dish_price.setText(dish.getDishPrice()+"");
                mViewHoder.right_dish_item.setContentDescription(position+"");


                int count = 0;
                if(mShopCart.getShoppingSingleMap().containsKey(dish)){
                    count = mShopCart.getShoppingSingleMap().get(dish);
                }
                if(count<=0){
                    mViewHoder.right_dish_remove_iv.setVisibility(View.GONE);
                    mViewHoder.right_dish_account.setVisibility(View.GONE);
                }else {
                    mViewHoder.right_dish_remove_iv.setVisibility(View.VISIBLE);
                    mViewHoder.right_dish_account.setVisibility(View.VISIBLE);
                    mViewHoder.right_dish_account.setText(count+"");

                }
                mViewHoder.right_dish_add_iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if(mShopCart.addShoppingSingle(dish)) {
                            notifyItemChanged(position);
                            if(shopCartImp!=null)
                                shopCartImp.add(view,position);
                        }
                    }
                });

                mViewHoder.right_dish_remove_iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(mShopCart.subShoppingSingle(dish)){
                            notifyItemChanged(position);
                            if(shopCartImp!=null)
                                shopCartImp.remove(view,position);
                        }
                    }
                });
            }

        }
    }

    public DishMenu getMenuByPosition(int position) {
        int sum = 0;
        for (DishMenu menu : mList) {
            if (position == sum) {
                return menu;
            }
            sum += menu.getDishList().size() + 1;
        }
        return null;
    }

    public Dish getDishByPosition(int position) {
        for(DishMenu menu:mList){
            if(position>0 && position<=menu.getDishList().size()){
                return menu.getDishList().get(position-1);
            }
            else{
                position-=menu.getDishList().size()+1;
            }
        }
        return null;
    }

    public DishMenu getMenuOfMenuByPosition(int position) {
        for (DishMenu menu : mList) {
            if (position == 0) return menu;
            if (position > 0 && position <= menu.getDishList().size()) {
                return menu;
            } else {
                position -= menu.getDishList().size() + 1;
            }
        }
        return null;
    }


    @Override
    public int getItemCount() {

        return dishCount;

    }

    class TitleHoder extends RecyclerView.ViewHolder {


        private TextView tv_right_menu;
        private LinearLayout right_menu_layout;

        public TitleHoder(View itemView) {
            super(itemView);
            tv_right_menu = (TextView) itemView.findViewById(R.id.tv_right_menu);
            right_menu_layout = (LinearLayout) itemView.findViewById(R.id.right_menu_layout);

        }
    }

    class ViewHoder extends RecyclerView.ViewHolder {

        private TextView right_dish_name;
        private TextView right_dish_price;
        private TextView right_dish_account;
        private ImageView right_dish_remove_iv;
        private ImageView right_dish_add_iv;
        private LinearLayout right_dish_item;

        public ViewHoder(View itemView) {
            super(itemView);
            right_dish_name = (TextView) itemView.findViewById(R.id.right_dish_name);
            right_dish_account = (TextView) itemView.findViewById(R.id.right_dish_account);
            right_dish_price = (TextView) itemView.findViewById(R.id.right_dish_price);
            right_dish_add_iv = (ImageView) itemView.findViewById(R.id.right_dish_add);
            right_dish_remove_iv = (ImageView) itemView.findViewById(R.id.right_dish_remove);
            right_dish_item = (LinearLayout) itemView.findViewById(R.id.right_dish_item);
        }
    }
}
