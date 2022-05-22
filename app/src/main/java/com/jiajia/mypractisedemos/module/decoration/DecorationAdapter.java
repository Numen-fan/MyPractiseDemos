package com.jiajia.mypractisedemos.module.decoration;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jiajia.mypractisedemos.R;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *  Created by fanjiajia on 2018/12/21.
 *  desc:
 */

public class DecorationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<CityBean> datas = new ArrayList<>();

    public DecorationAdapter() {}

    public DecorationAdapter(Context context, List<CityBean> datas) {
        this.context = context;
        this.datas = datas;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        if (context == null) {
            context = parent.getContext();
        }
        if (viewType == 0) {
            View view = LayoutInflater.from(context).inflate(R.layout.decoration_item, parent, false);
            holder = new DataViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_footer, parent, false);
            holder = new FooterHolder(view);
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof DataViewHolder) {
            DataViewHolder dataViewHolder = (DataViewHolder) holder;
            String str = datas.get(position).getCity();
            dataViewHolder.tv_content.setText(str);
        }
    }

    public void addData(String str) {
        CityBean cityBean = new CityBean("", str);
        datas.add(cityBean);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return datas.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position < datas.size()) {
            return 0;
        }
        return 1;
    }

    static class DataViewHolder extends RecyclerView.ViewHolder {
        TextView tv_content;

        public DataViewHolder(View itemView) {
            super(itemView);
            tv_content = itemView.findViewById(R.id.tv_content);
        }
    }

    static class FooterHolder extends RecyclerView.ViewHolder {

        public FooterHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
