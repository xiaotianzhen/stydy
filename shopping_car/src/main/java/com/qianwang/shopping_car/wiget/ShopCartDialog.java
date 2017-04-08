package com.qianwang.shopping_car.wiget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qianwang.shopping_car.R;
import com.qianwang.shopping_car.adapter.PopupDishAdapter;
import com.qianwang.shopping_car.bean.ShopCart;
import com.qianwang.shopping_car.imp.ShopCartImp;

/**
 * Created by sky on 2017/4/4.
 */

public class ShopCartDialog extends Dialog implements View.OnClickListener,ShopCartImp {


    private ShopCart mShopCart;
    private TextView tv_totalNum;
    private TextView tv_totalPrice;
    private RecyclerView mRecyclerView;
    private PopupDishAdapter mPopupDishAdapter;
    private LinearLayout linearlayout;
    private FrameLayout shopingcartLayout;
    private TextView tv_clear;
    private ShopCartDialogImp mShopCartDialogImp;


    public ShopCartDialog(Context context, ShopCart shopCart, int themeResId) {
        super(context, themeResId);
        this.mShopCart = shopCart;
    }

    public void setShopCartDialogImp(ShopCartDialogImp shopCartDialogImp) {
        mShopCartDialogImp = shopCartDialogImp;
    }

    public interface ShopCartDialogImp{

        public  void dismissDialog();
    }

    private void initView() {
        tv_totalNum = (TextView) findViewById(R.id.shopping_cart_total_num);
        tv_totalPrice = (TextView)findViewById(R.id.shopping_cart_total_tv);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycleview);
        linearlayout= (LinearLayout) findViewById(R.id.linearlayout);
        shopingcartLayout= (FrameLayout) findViewById(R.id.shopping_cart_layout);
        tv_clear= (TextView) findViewById(R.id.tv_clear);

        shopingcartLayout.setOnClickListener(this);
        tv_clear.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_popupview);
        initView();
        mPopupDishAdapter=new PopupDishAdapter(getContext(),mShopCart);
        mRecyclerView.setAdapter(mPopupDishAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mPopupDishAdapter.setShopCartImp(this);
        showTotalPrice();

    }

    private void  animationShow(int duration){

        AnimatorSet animatorSet=new AnimatorSet();
        animatorSet.playTogether(ObjectAnimator.ofFloat(linearlayout,"translationY",1000,0).setDuration(duration));
        animatorSet.start();
    }
    private void animationHide(int duration){
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(linearlayout, "translationY",0,1000).setDuration(duration)
        );
        animatorSet.start();

        if(mShopCartDialogImp!=null){
            mShopCartDialogImp.dismissDialog();
        }

        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                ShopCartDialog.super.dismiss();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }
    @Override
    public void add(View view, int postion) {
        showTotalPrice();
    }

    @Override
    public void remove(View view, int postion) {

        if(mShopCart.getShoppingAccount()==0){
            this.dismiss();
        }
        showTotalPrice();
    }

    @Override
    public void show() {
        super.show();
        animationShow(1000);
    }

    @Override
    public void dismiss() {
        animationHide(1000);
    }

    private void showTotalPrice() {

        if (mShopCart != null && mShopCart.getShoppingTotalPrice() > 0) {
            tv_totalPrice.setVisibility(View.VISIBLE);
            tv_totalPrice.setText("￥ " + mShopCart.getShoppingTotalPrice());
            tv_totalNum.setVisibility(View.VISIBLE);
            tv_totalNum.setText("" + mShopCart.getShoppingAccount());

        } else {
            tv_totalPrice.setVisibility(View.GONE);
            tv_totalNum.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {
        int id= view.getId();

        switch (id){
            case R.id.shopping_cart_layout:
                this.dismiss();
                break;
            case R.id.tv_clear:
                mShopCart.clear();
                showTotalPrice();

                if(mShopCart.getShoppingAccount()==0){
                    this.dismiss();
                }
        }
    }
}
