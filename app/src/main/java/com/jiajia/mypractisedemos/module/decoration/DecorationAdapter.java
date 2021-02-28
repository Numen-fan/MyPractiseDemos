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

public class DecorationAdapter extends RecyclerView.Adapter<DecorationAdapter.ViewHolder>{

    private Context context;
    private List<CityBean> datas = new ArrayList<CityBean>();

    public DecorationAdapter() {}

    public DecorationAdapter(Context context, List<CityBean> datas) {
        this.context = context;
        this.datas = datas;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder holder;
        if (context == null) {
            context = parent.getContext();
        }
        View view = LayoutInflater.from(context).inflate(R.layout.decoration_item, parent, false);
        holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String str = datas.get(position).getCity();
        holder.tv_content.setText(str);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_content;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_content = (TextView) itemView.findViewById(R.id.tv_content);
        }
    }
}
