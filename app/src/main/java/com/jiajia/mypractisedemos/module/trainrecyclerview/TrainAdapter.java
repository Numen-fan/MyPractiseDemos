package com.jiajia.mypractisedemos.module.trainrecyclerview;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jiajia.mypractisedemos.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * <pre>
 *  Created by fanjiajia on 2019/3/21.
 *  desc:
 */

public class TrainAdapter extends RecyclerView.Adapter<TrainAdapter.Holder>{


    private Context mContext;
    private List<ItemBean> list;
    private onItemSelectListener listener;
    private int latestSelectedIndex = -1; // 上一次选中的
    private int selectedNums = 0; // 选中项的总数

    public TrainAdapter(Context context, List<ItemBean> list) {
        this.mContext = context;
        this.list = list;
    }

    public interface onItemSelectListener {
        void onSelect(int pos, int latestSelectIndex, int selectedNums);
    }

    public void setSelectListener(onItemSelectListener listener) {
        this.listener = listener;
    }

    public void setSelectedNums(int num) {
        this.selectedNums = num;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.train_item_layout, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, final int position) {
        TextView view = holder.tv_train_num;
        final ItemBean bean = list.get(position);
        view.setText(list.get(position).getContent());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!bean.isHandled()) {
                    selectedNums = (++selectedNums) % 3;
                    listener.onSelect(position, latestSelectedIndex, selectedNums);
                    latestSelectedIndex = position;
                }
            }
        });
        if (bean.isHandled()) {
            view.setBackgroundColor(ContextCompat.getColor(mContext, R.color.color_red));
        }else if (bean.isSelected()) {
            view.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
        }else if (bean.isMiddle()) {
            view.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorPrimaryDark));
        }else {
            view.setBackgroundColor(ContextCompat.getColor(mContext, R.color.color_bg_f0f0));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class Holder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_train_num)
        TextView tv_train_num;

        public Holder(View itemView) {
            super(itemView);
//            ButterKnife.bind(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
