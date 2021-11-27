package com.jiajia.mypractisedemos.module.demo;

import static com.jiajia.mypractisedemos.utils.Constant.URL_REGEX;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.util.StringBuilderPrinter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jiajia.mypractisedemos.R;
import com.jiajia.mypractisedemos.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BlankFragment extends Fragment {


    public static final String TAG = "BlankFragment";

    RecyclerView recyclerView;

    RecordAdapter adapter;

    SwipeToLoadLayout swipeToLoadLayout;

    Button btnAdd;

    LinearLayoutManager layoutManager;

    List<CharSequence> data = new ArrayList<>();

    private final Pattern urlPattern = Pattern.compile(URL_REGEX);

    public BlankFragment() {
        Log.d(TAG, "fjjj, BlankFragment()");
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "fjjj, onCreate");
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "fjjj, onDestroyView");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d(TAG, "fjjj, onCreateView");

        View root = inflater.inflate(R.layout.fragment_blank, container, false);

        recyclerView = root.findViewById(R.id.record_list);
        btnAdd = root.findViewById(R.id.btn_add_record);
        swipeToLoadLayout = root.findViewById(R.id.swipe_refresh_layout);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecordAdapter();
        recyclerView.setAdapter(adapter);


        btnAdd.setOnClickListener(v -> {
            CharSequence content = getInputString();
            if (TextUtils.isEmpty(content)) {
                Toast.makeText(getContext(), "输入内容不能为空！", Toast.LENGTH_SHORT).show();
                return;
            }
            data.add(content);
            adapter.updateData(data);
            layoutManager.scrollToPosition(adapter.getItemCount() - 1);
            swipeToLoadLayout.setRefreshing(false);
        });

        return root;
    }

    public CharSequence getInputString() {
        Activity activity = getActivity();
        if (activity == null) {
            return "";
        }
        String content = ((Demo2Activity) getActivity()).getInputContents().trim();
        if (TextUtils.isEmpty(content)) {
            return "";
        }
        return processUrlLink(content);
    }

    private CharSequence processUrlLink(@NonNull String content) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(content);
        Matcher matcher = urlPattern.matcher(content);
        while (matcher.find()) {
            final String url = matcher.group();
            int start = matcher.start();
            int end = matcher.end();
            spannableStringBuilder.setSpan(new ClickableSpan() {

                @Override
                public void updateDrawState(@NonNull TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setColor(getResources().getColor(R.color.C_7));
                }

                @Override
                public void onClick(@NonNull View widget) {
                    Utils.openUrlWithSystemBrowser(BlankFragment.this.getContext(), url);
                }
            }, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        return spannableStringBuilder;
    }

    /**
     * RecyclerView.Adapter
     */
    static class RecordAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        final List<CharSequence> data = new ArrayList<>();

        final static int ITEM_DATA = 0;
        final static int ITEM_FOOTER = 1;


        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.record_item_layout, parent, false);
            return viewType == ITEM_DATA ? new MyViewHolder(itemView) : new EmptyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof MyViewHolder) {
                ((MyViewHolder) holder).text.setText(data.get(position));
            } else {
                View emptyRoot = ((EmptyViewHolder) holder).root;
                ViewGroup.LayoutParams layoutParams = emptyRoot.getLayoutParams();
                layoutParams.height = Utils.dp2px(100);
                emptyRoot.setLayoutParams(layoutParams);
            }
        }

        @Override
        public int getItemCount() {
            return data.size() + 1;
        }

        @Override
        public int getItemViewType(int position) {
            return position < data.size() ? ITEM_DATA : ITEM_FOOTER;
        }

        @SuppressLint("NotifyDataSetChanged")
        public void updateData(List<CharSequence> newData) {
            data.clear();
            data.addAll(newData);
            notifyDataSetChanged();
        }

        static class MyViewHolder extends RecyclerView.ViewHolder {

            final TextView text;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                text = itemView.findViewById(R.id.text);
                text.setMovementMethod(LinkMovementMethod.getInstance());
            }
        }

        static class EmptyViewHolder extends RecyclerView.ViewHolder {

            final TextView text;
            final View root;

            public EmptyViewHolder(@NonNull View itemView) {
                super(itemView);
                text = itemView.findViewById(R.id.text);
                root = itemView;
            }
        }
    }
}