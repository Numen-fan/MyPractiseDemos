package com.jiajia.mypractisedemos.module.recycgroup1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.DisplayMetrics;

import com.jiajia.mypractisedemos.R;
import com.jiajia.mypractisedemos.module.decoration.DecorationAdapter;
import com.jiajia.mypractisedemos.utils.Utils;

public class RecycGroup1Activity extends AppCompatActivity {

    RecyclerView recyclerView;
    DecorationAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyc_group1);

        initRecyclerView();

        findViewById(R.id.btn_add_data).setOnClickListener(v -> {
            adapter.addData(String.format("我是第%1s个数据", adapter.getItemCount()));
            recyclerView.smoothScrollToPosition(adapter.getItemCount() - 1);
//            recyclerView.scrollToPosition(adapter.getItemCount() - 1);
        });

    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.rv_group_1);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this) {
            @Override
            public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
                RecyclerView.SmoothScroller smoothScroller = new LinearSmoothScroller(RecycGroup1Activity.this) {
                    @Override
                    protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                        return 220f / displayMetrics.densityDpi;
                    }

                    @Override
                    public int calculateDtToFit(int viewStart, int viewEnd, int boxStart, int boxEnd, int snapPreference) {
                        if (snapPreference == SNAP_TO_ANY) {
                            return boxEnd - viewEnd + Utils.dp2px(1);
                        }
                        return super.calculateDtToFit(viewStart, viewEnd, boxStart, boxEnd, snapPreference);
                    }
                };
                smoothScroller.setTargetPosition(position);
                startSmoothScroll(smoothScroller);
            }
        };
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new DecorationAdapter();
        recyclerView.setAdapter(adapter);
    }
}
