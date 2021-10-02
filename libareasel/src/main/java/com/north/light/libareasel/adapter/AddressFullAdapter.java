package com.north.light.libareasel.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.north.light.libareasel.R;
import com.north.light.libareasel.bean.AddressDetailInfo;
import com.north.light.libareasel.bean.local.AddressLocalFullItemInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * FileName: AddressFullAdapter
 * Author: lizhengting
 * Date: 2021/10/1 20:59
 * Description:地址全屏adapter
 */
public class AddressFullAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    /**
     * 数据集合
     */
    private List<AddressLocalFullItemInfo> mData = new ArrayList<>();
    /**
     * holder type
     */
    private static final int TYPE_COMMEND = 0x0001;
    private static final int TYPE_CONTENT = 0x0002;
    private static final int TYPE_TITLE = 0x0003;

    private OnClickListener mListener;

    private Context mContext;

    public AddressFullAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getItemViewType(int position) {
        AddressLocalFullItemInfo info = mData.get(position);
        switch (info.getType()) {
            case 1:
                return TYPE_COMMEND;
            case 2:
                return TYPE_CONTENT;
            case 3:
                return TYPE_TITLE;
        }
        return super.getItemViewType(position);

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_COMMEND:
                View root1 = LayoutInflater.from(mContext).inflate(R.layout.recy_fragment_lib_sel_full_recommend,
                        parent, false);
                return new RecommendHolder(root1);
            case TYPE_CONTENT:
                View root2 = LayoutInflater.from(mContext).inflate(R.layout.recy_fragment_lib_sel_full_content,
                        parent, false);
                return new ContentHolder(root2);
            case TYPE_TITLE:
                View root3 = LayoutInflater.from(mContext).inflate(R.layout.recy_fragment_lib_sel_full_title,
                        parent, false);
                return new TitleHolder(root3);
        }
        //默认holder
        View root1 = LayoutInflater.from(mContext).inflate(R.layout.recy_fragment_lib_sel_full_recommend,
                parent, false);
        return new RecommendHolder(root1);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        AddressLocalFullItemInfo info = mData.get(position);
        if (holder instanceof RecommendHolder) {
            //推荐holder
            List<AddressDetailInfo> recList = info.getRecommendList();
            try {
                ((RecommendHolder) holder).mTitle1.setText(recList.get(0).getName());
                ((RecommendHolder) holder).mTitle1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mListener != null) {
                            mListener.Select(info.getSourceType(),info.getRecommendList().get(0));
                        }
                    }
                });
            } catch (Exception e) {

            }
            try {
                ((RecommendHolder) holder).mTitle2.setText(recList.get(1).getName());
                ((RecommendHolder) holder).mTitle2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mListener != null) {
                            mListener.Select(info.getSourceType(),info.getRecommendList().get(1));
                        }
                    }
                });
            } catch (Exception e) {

            }
            try {
                ((RecommendHolder) holder).mTitle3.setText(recList.get(2).getName());
                ((RecommendHolder) holder).mTitle3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mListener != null) {
                            mListener.Select(info.getSourceType(),info.getRecommendList().get(2));
                        }
                    }
                });
            } catch (Exception e) {

            }
            try {
                ((RecommendHolder) holder).mTitle4.setText(recList.get(3).getName());
                ((RecommendHolder) holder).mTitle4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mListener != null) {
                            mListener.Select(info.getSourceType(),info.getRecommendList().get(3));
                        }
                    }
                });
            } catch (Exception e) {

            }
        } else if (holder instanceof ContentHolder) {
            //内容holder
            ((ContentHolder) holder).mContent.setText(info.getAddress());
            ((ContentHolder) holder).mContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.Select(info.getSourceType(),info.getAddressDetail());
                    }
                }
            });
        } else if (holder instanceof TitleHolder) {
            //标题holder
            ((TitleHolder) holder).mTitle.setText(info.getTitle());
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public void setData(List<AddressLocalFullItemInfo> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }

    /**
     * 滚动到指定字母位置
     */
    public void scrollToTips(String letter, RecyclerView mFullRecy) {
        if (mFullRecy == null || TextUtils.isEmpty(letter)) {
            return;
        }
        for (int i = 0; i < mData.size() - 1; i++) {
            AddressLocalFullItemInfo info = mData.get(i);
            if (info.getType() == 3 && info.getTitle().equals(letter)) {
                mFullRecy.smoothScrollToPosition(i);
                break;
            }
        }
    }

    /**
     * 推荐信息
     */
    public static class RecommendHolder extends RecyclerView.ViewHolder {
        private TextView mTitle1;
        private TextView mTitle2;
        private TextView mTitle3;
        private TextView mTitle4;

        public RecommendHolder(@NonNull View itemView) {
            super(itemView);
            mTitle1 = itemView.findViewById(R.id.recy_fragment_lib_sel_full_recommend_1);
            mTitle2 = itemView.findViewById(R.id.recy_fragment_lib_sel_full_recommend_2);
            mTitle3 = itemView.findViewById(R.id.recy_fragment_lib_sel_full_recommend_3);
            mTitle4 = itemView.findViewById(R.id.recy_fragment_lib_sel_full_recommend_4);
        }
    }

    /**
     * 内容信息
     */
    public static class ContentHolder extends RecyclerView.ViewHolder {
        private TextView mContent;

        public ContentHolder(@NonNull View itemView) {
            super(itemView);
            mContent = itemView.findViewById(R.id.recy_fragment_lib_sel_full_content_title);
        }
    }

    /**
     * 标题信息
     */
    public static class TitleHolder extends RecyclerView.ViewHolder {
        private TextView mTitle;

        public TitleHolder(@NonNull View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.recy_fragment_lib_sel_full_content_title);
        }
    }

    public void setOnClickListener(OnClickListener listener) {
        mListener = listener;
    }

    public interface OnClickListener {
        void Select(int sourceType,AddressDetailInfo info);
    }
}
