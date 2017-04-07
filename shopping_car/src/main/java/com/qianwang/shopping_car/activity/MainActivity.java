package com.qianwang.shopping_car.activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.PointF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qianwang.shopping_car.R;
import com.qianwang.shopping_car.adapter.LeftMenuAdapter;
import com.qianwang.shopping_car.adapter.RightMenuAdapter;
import com.qianwang.shopping_car.bean.Dish;
import com.qianwang.shopping_car.bean.DishMenu;
import com.qianwang.shopping_car.bean.ShopCart;
import com.qianwang.shopping_car.imp.ShopCartImp;
import com.qianwang.shopping_car.wiget.FakeAddImageView;
import com.qianwang.shopping_car.wiget.PointFTypeEvaluator;
import com.qianwang.shopping_car.wiget.ShopCartDialog;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LeftMenuAdapter.onItemSelectedListener, ShopCartImp, ShopCartDialog.ShopCartDialogImp {

    private RecyclerView left_recyclerView;
    private RecyclerView right_recyclerView;
    private List<DishMenu> dishMenuList;
    private LeftMenuAdapter leftAdapter;
    private RightMenuAdapter mRightMenuAdapter;
    private LinearLayout right_menu_layout;
    private TextView tv_right_menu;
    private DishMenu mDishMenu;
    private boolean leftClickType = false;
    private ShopCart mShopCart;
    private TextView tv_totalNum;
    private TextView tv_totalPrice;
    private ImageView im_car;
    private RelativeLayout main_layout;
    private FrameLayout framLayout_car;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
        leftAdapter.setItemSelectedListener(this);

        initHeadView();
        right_recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (recyclerView.canScrollVertically(1) == false) {  //无法下滑
                    showHeadView();
                    return;
                }
                View underView = null;

                if (dy > 0) {
                    underView = right_recyclerView.findChildViewUnder(right_menu_layout.getX(), right_menu_layout.getMeasuredHeight() + 1);
                } else {
                    underView = right_recyclerView.findChildViewUnder(right_menu_layout.getX(), 0);
                }

                if (underView != null && right_menu_layout.getContentDescription() != null) {


                    int position = Integer.parseInt(underView.getContentDescription().toString());
                    DishMenu menu = mRightMenuAdapter.getMenuOfMenuByPosition(position);

                    if (leftClickType || !menu.getMenuName().equals(mDishMenu.getMenuName())) {

                        if (dy > 0 && right_menu_layout.getTranslationY() <= 1 && right_menu_layout.getTranslationY() >=
                                -1 * right_menu_layout.getMeasuredHeight() * 4 / 5 && !leftClickType) {// underView.getTop()>9
                            int dealtY = underView.getTop() - right_menu_layout.getMeasuredHeight();
                            right_menu_layout.setTranslationY(dealtY);
                            // Log.e("520it", "onScrolled: " + right_menu_layout.getTranslationY() + "   " + right_menu_layout.getBottom() +
                            //       "  -  " + right_menu_layout.getMeasuredHeight());
                        } else if (dy < 0 && right_menu_layout.getTranslationY() <= 0 && !leftClickType) {
                            tv_right_menu.setText(menu.getMenuName());
                            int dealtY = underView.getBottom() - right_menu_layout.getMeasuredHeight();
                            right_menu_layout.setTranslationY(dealtY);
                            // Log.e("520it", "onScrolled: " + right_menu_layout.getTranslationY() + "   " + right_menu_layout.getBottom() +
                            //        "  -  " + right_menu_layout.getMeasuredHeight());
                        } else {
                            right_menu_layout.setTranslationY(0);
                            mDishMenu = menu;
                            tv_right_menu.setText(mDishMenu.getMenuName());
                            for (int i = 0; i < dishMenuList.size(); i++) {
                                if (dishMenuList.get(i) == mDishMenu) {
                                    leftAdapter.setSelectPosition(i);
                                    break;
                                }
                            }
                            if (leftClickType) leftClickType = false;
                        }

                    }
                }
            }
        });

        mRightMenuAdapter.setShopCartImp(this);

        framLayout_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(mShopCart.getShoppingAccount()!=0){
                    ShopCartDialog dialog = new ShopCartDialog(MainActivity.this, mShopCart, R.style.cartdialog);
                    Window window = dialog.getWindow();
                    dialog.setShopCartDialogImp(MainActivity.this);
                    dialog.setCanceledOnTouchOutside(true);
                    dialog.setCancelable(true);
                    dialog.show();
                    WindowManager.LayoutParams params = window.getAttributes();
                    params.width = WindowManager.LayoutParams.MATCH_PARENT;
                    params.height = WindowManager.LayoutParams.WRAP_CONTENT;
                    params.gravity = Gravity.BOTTOM;
                    params.dimAmount = 0.5f;
                    window.setAttributes(params);
                }

            }
        });

    }

    private void initView() {

        left_recyclerView = (RecyclerView) findViewById(R.id.left_recycleView);
        right_recyclerView = (RecyclerView) findViewById(R.id.right_recycleView);
        right_menu_layout = (LinearLayout) findViewById(R.id.right_menu_layout);
        tv_right_menu = (TextView) findViewById(R.id.tv_right_menu);
        tv_totalNum = (TextView) findViewById(R.id.tv_totalNum);
        tv_totalPrice = (TextView) findViewById(R.id.tv_totalPrice);
        im_car = (ImageView) findViewById(R.id.im_car);
        main_layout = (RelativeLayout) findViewById(R.id.activity_main);
        framLayout_car = (FrameLayout) findViewById(R.id.framLayout_car);

        leftAdapter = new LeftMenuAdapter(getApplicationContext(), dishMenuList);
        left_recyclerView.setAdapter(leftAdapter);
        left_recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

        mRightMenuAdapter = new RightMenuAdapter(dishMenuList, mShopCart);
        right_recyclerView.setAdapter(mRightMenuAdapter);
        right_recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

    }

    private void initData() {
        mShopCart = new ShopCart();
        dishMenuList = new ArrayList<DishMenu>();
        ArrayList<Dish> dishs1 = new ArrayList<>();
        dishs1.add(new Dish("面包", 1.0, 10));
        dishs1.add(new Dish("蛋挞", 1.0, 10));
        dishs1.add(new Dish("牛奶", 1.0, 10));
        dishs1.add(new Dish("肠粉", 1.0, 10));
        dishs1.add(new Dish("绿茶饼", 1.0, 10));
        dishs1.add(new Dish("花卷", 1.0, 10));
        dishs1.add(new Dish("包子", 1.0, 10));
        DishMenu breakfast = new DishMenu("早点", dishs1);

        ArrayList<Dish> dishs2 = new ArrayList<>();
        dishs2.add(new Dish("粥", 1.0, 10));
        dishs2.add(new Dish("炒饭", 1.0, 10));
        dishs2.add(new Dish("炒米粉", 1.0, 10));
        dishs2.add(new Dish("炒粿条", 1.0, 10));
        dishs2.add(new Dish("炒牛河", 1.0, 10));
        dishs2.add(new Dish("炒菜", 1.0, 10));
        DishMenu launch = new DishMenu("午餐", dishs2);

        ArrayList<Dish> dishs3 = new ArrayList<>();
        dishs3.add(new Dish("淋菜", 1.0, 10));
        dishs3.add(new Dish("川菜", 1.0, 10));
        dishs3.add(new Dish("湘菜", 1.0, 10));
        dishs3.add(new Dish("粤菜", 1.0, 10));
        dishs3.add(new Dish("赣菜", 1.0, 10));
        dishs3.add(new Dish("东北菜", 1.0, 10));
        DishMenu evening = new DishMenu("晚餐", dishs3);

        ArrayList<Dish> dishs4 = new ArrayList<>();
        dishs4.add(new Dish("淋菜", 1.0, 10));
        dishs4.add(new Dish("川菜", 1.0, 10));
        dishs4.add(new Dish("湘菜", 1.0, 10));
        dishs4.add(new Dish("粤菜", 1.0, 10));
        dishs4.add(new Dish("赣菜", 1.0, 10));
        dishs4.add(new Dish("东北菜", 1.0, 10));
        DishMenu menu1 = new DishMenu("宵夜", dishs4);

        dishMenuList.add(breakfast);
        dishMenuList.add(launch);
        dishMenuList.add(evening);
        dishMenuList.add(menu1);
    }

    @Override
    public void onLeftMenuSelect(int position) {

        int sum = 0;
        for (int i = 0; i < position; i++) {
            sum += dishMenuList.get(i).getDishList().size() + 1;
        }
        LinearLayoutManager manager = (LinearLayoutManager) right_recyclerView.getLayoutManager();
        manager.scrollToPositionWithOffset(sum, 0);
        leftClickType = true;

    }

    public void initHeadView() {

        mDishMenu = mRightMenuAdapter.getMenuOfMenuByPosition(0);
        right_menu_layout.setContentDescription("0");
        tv_right_menu.setText(mDishMenu.getMenuName());

    }

    public void showHeadView() {

        right_menu_layout.setTranslationY(0);

        View underView = right_recyclerView.findChildViewUnder(tv_right_menu.getX(), 0);

        if (underView != null && underView.getContentDescription() != null) {

            int position = Integer.valueOf(underView.getContentDescription().toString());

            DishMenu menu = mRightMenuAdapter.getMenuOfMenuByPosition(position);
            mDishMenu = menu;
            for (int i = 0; i < dishMenuList.size(); i++) {

                if (dishMenuList.get(i) == mDishMenu) {
                    leftAdapter.setSelectPosition(i);
                }
            }

        }

    }

    @Override
    public void add(View view, int postion) {

        int[] addLocation = new int[2];
        int[] cartLocation = new int[2];
        int[] recycleLocation = new int[2];
        view.getLocationInWindow(addLocation);
        im_car.getLocationInWindow(cartLocation);
        right_recyclerView.getLocationInWindow(recycleLocation);

        PointF startP = new PointF();
        PointF endP = new PointF();
        PointF controlP = new PointF();

        startP.x = addLocation[0];
        startP.y = addLocation[1] - recycleLocation[1];
        endP.x = cartLocation[0];
        endP.y = cartLocation[1] - recycleLocation[1];
        controlP.x = endP.x;
        controlP.y = startP.y;

        final FakeAddImageView fakeAddImageView = new FakeAddImageView(this);
        main_layout.addView(fakeAddImageView);
        fakeAddImageView.setImageResource(R.drawable.ic_add_circle_blue_700_36dp);
        fakeAddImageView.getLayoutParams().width = getResources().getDimensionPixelSize(R.dimen.item_dish_circle_size);
        fakeAddImageView.getLayoutParams().height = getResources().getDimensionPixelSize(R.dimen.item_dish_circle_size);
        fakeAddImageView.setVisibility(View.VISIBLE);
        ObjectAnimator addAnimator = ObjectAnimator.ofObject(fakeAddImageView, "mPointF",
                new PointFTypeEvaluator(controlP), startP, endP);
        addAnimator.setInterpolator(new AccelerateInterpolator());
        addAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                fakeAddImageView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                fakeAddImageView.setVisibility(View.GONE);
                main_layout.removeView(fakeAddImageView);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        ObjectAnimator scaleAnimatorX = new ObjectAnimator().ofFloat(im_car, "scaleX", 0.6f, 1.0f);
        ObjectAnimator scaleAnimatorY = new ObjectAnimator().ofFloat(im_car, "scaleY", 0.6f, 1.0f);
        scaleAnimatorX.setInterpolator(new AccelerateInterpolator());
        scaleAnimatorY.setInterpolator(new AccelerateInterpolator());
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(scaleAnimatorX).with(scaleAnimatorY).after(addAnimator);
        animatorSet.setDuration(800);
        animatorSet.start();

        showTotalPrice();

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
    public void remove(View view, int postion) {
        showTotalPrice();
    }


    @Override
    public void dismissDialog() {
        showTotalPrice();
        mRightMenuAdapter.notifyDataSetChanged();
    }
}
