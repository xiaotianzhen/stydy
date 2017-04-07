package com.qianwang.shopping_car.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.qianwang.shopping_car.R;
import com.qianwang.shopping_car.bean.DishMenu;
import java.util.List;

/**
 * Created by sky on 2017/4/1.
 */

public class LeftMenuAdapter extends RecyclerView.Adapter<LeftMenuAdapter.ViewHodler> {

    private Context mContext;
    private List<DishMenu> mList;
    private onItemSelectedListener mListener;
    private int mSelectPosion;

    public interface onItemSelectedListener {

        public void onLeftMenuSelect(int position);

    }

    public void setItemSelectedListener(onItemSelectedListener listener) {
        this.mListener = listener;
    }

    public LeftMenuAdapter(Context context, List<DishMenu> mData) {
        mContext = context;
        this.mList = mData;
         mSelectPosion=-1;

    }

    @Override
    public ViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.left_menu_item, parent, false);
        return new ViewHodler(view);
    }

    @Override
    public void onBindViewHolder(ViewHodler holder, int position) {

        holder.tv_left_menu.setText(mList.get(position).getMenuName());
        if(mSelectPosion==position){
            holder.left_menu_item_ll.setSelected(true);
        }else {
            holder.left_menu_item_ll.setSelected(false);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setSelectPosition(int positon) {
        if (positon>=0&&positon<mList.size()){
            mSelectPosion = positon;
            notifyDataSetChanged();
        }
    }

    class ViewHodler extends RecyclerView.ViewHolder {
        private TextView tv_left_menu;
        private LinearLayout left_menu_item_ll;

        public ViewHodler(View itemView) {
            super(itemView);
            tv_left_menu = (TextView) itemView.findViewById(R.id.tv_left_menu);
            left_menu_item_ll = (LinearLayout) itemView.findViewById(R.id.left_menu_item_ll);


            left_menu_item_ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (mListener != null) {
                        mListener.onLeftMenuSelect(getAdapterPosition());
                        setSelectPosition(getAdapterPosition());
                    }
                }
            });
        }
    }

}
