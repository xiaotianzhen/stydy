package com.qianwang.shopping_car.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.qianwang.shopping_car.R;
import com.qianwang.shopping_car.bean.Dish;
import com.qianwang.shopping_car.bean.ShopCart;
import com.qianwang.shopping_car.imp.ShopCartImp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sky on 2017/4/4.
 */

public class PopupDishAdapter extends RecyclerView.Adapter<PopupDishAdapter.ViewHoder>  {

    private  ShopCart mShopCart;
    private List<Dish> mDishes=new ArrayList<Dish>();
    private int itemCount;
    private ShopCartImp mShopCartImp;
    private Context context;

    public PopupDishAdapter(Context context,ShopCart shopCart) {
        this.mShopCart = shopCart;
        this.context = context;
        this.itemCount = shopCart.getDishAccount();
        Log.i("520it", "itemCount" + "**************************"+itemCount);
        this.mDishes = new ArrayList<>();
        mDishes.addAll(shopCart.getShoppingSingleMap().keySet());
        Log.i("520it", "" + "**************************"+mDishes);
    }

    public void setShopCartImp(ShopCartImp shopCartImp) {
        mShopCartImp = shopCartImp;
    }

    @Override
    public PopupDishAdapter.ViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.right_dish_item,parent,false);
        return new ViewHoder(view);
    }

    @Override
    public void onBindViewHolder(PopupDishAdapter.ViewHoder holder, final int position) {

        final Dish dish=getDishByPosition(position);

        if(dish!=null){
            holder.right_dish_name.setText(dish.getDishName());
            holder.right_dish_price.setText(dish.getDishPrice()+"");

            int  num=mShopCart.getShoppingSingleMap().get(dish);

            Log.i("520it", "num" + "**************************" +num);
            holder.right_dish_account.setText(num+"");

            holder.right_dish_add_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(mShopCart.addShoppingSingle(dish)){
                        notifyItemChanged(position);
                        if(mShopCartImp!=null){
                            mShopCartImp.add(view,position);
                        }
                    }
                }
            });
            holder.right_dish_remove_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mShopCart.subShoppingSingle(dish)){
                        mDishes.clear();
                        mDishes.addAll(mShopCart.getShoppingSingleMap().keySet());
                        itemCount = mShopCart.getDishAccount();
                        notifyDataSetChanged();
                        if(mShopCartImp!=null)
                            mShopCartImp.remove(view,position);
                    }
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return itemCount;
    }

    public Dish getDishByPosition(int position){


        return mDishes.get(position);
    }

    class  ViewHoder extends RecyclerView.ViewHolder{


        private TextView right_dish_name;
        private TextView right_dish_price;
        private TextView right_dish_account;
        private ImageView right_dish_remove_iv;
        private ImageView right_dish_add_iv;


        public ViewHoder(View itemView) {
            super(itemView);

            right_dish_name = (TextView) itemView.findViewById(R.id.right_dish_name);
            right_dish_account = (TextView) itemView.findViewById(R.id.right_dish_account);
            right_dish_price = (TextView) itemView.findViewById(R.id.right_dish_price);
            right_dish_add_iv = (ImageView) itemView.findViewById(R.id.right_dish_add);
            right_dish_remove_iv = (ImageView) itemView.findViewById(R.id.right_dish_remove);
        }
    }
}
