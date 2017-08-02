package retrofitdemo.fujisoft.com.filterdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import retrofitdemo.fujisoft.com.filterdemo.bean.FilterBean;
import retrofitdemo.fujisoft.com.filterdemo.R;

/**
 * Created by 860617010 on 2017/7/31.
 */

public class FilterRecyclerAdapter extends RecyclerView.Adapter<FilterRecyclerAdapter.FilterViewHolder> {
    private List<FilterBean> data;
    private Context context;
    private OnItemClickListener mOnItemClickListener = null;
    public FilterRecyclerAdapter(Context context,List<FilterBean> data) {
        this.context = context;
        this.data = data;
    }
    @Override
    public FilterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_item_filter,parent,false);
        FilterViewHolder filterViewHolder = new FilterViewHolder(view);
        //将创建的View注册点击事件
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    //注意这里使用getTag方法获取position,在getTag之前我们需要在onBindViewHolder方法中通过holder获取出来itemView再将position保存在itemView的Tag中。
                    mOnItemClickListener.onItemClick(v,(int)v.getTag());//将点击事件转移给外面的调用者
                }
            }
        });
        return filterViewHolder;
    }

    @Override
    public void onBindViewHolder(final FilterViewHolder holder, final int position) {
        holder.textViewType.setText(data.get(position).getName());
        FilterBean bean = data.get(position);
        if (bean.getSelect()){
            holder.ivSelectOrNot.setVisibility(View.VISIBLE);
        }else {
            holder.ivSelectOrNot.setVisibility(View.GONE);
        }

        //将position保存在itemView的Tag中，以便点击时进行获取
        holder.itemView.setTag(position);
    }

    private static final String TAG = "FilterRecyclerAdapter";
    @Override
    public int getItemCount() {
        return data.size();
    }

    class FilterViewHolder extends RecyclerView.ViewHolder{
        private TextView textViewType;
        private ImageView ivSelectOrNot;
        public FilterViewHolder(final View itemView) {
            super(itemView);
            textViewType = (TextView) itemView.findViewById(R.id.tv_type);
            ivSelectOrNot = (ImageView) itemView.findViewById(R.id.iv_select_or_not);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //如果OnItemClickListener不为空，则说明外部注册了条目的点击事件，在这里我们把被点击了的条目位置以及条目view返回给点击之间注册者
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(itemView, (Integer) itemView.getTag());
                    }
                }
            });
        }
    }

    /**
     * 定义条目点击事件的接口
     */
    public interface OnItemClickListener {
        void onItemClick(View view , int position);
    }

    /**
     * 暴露接口给外部调用
     * @param listener
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}
