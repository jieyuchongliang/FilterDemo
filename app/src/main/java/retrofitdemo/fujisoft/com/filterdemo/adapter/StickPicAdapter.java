package retrofitdemo.fujisoft.com.filterdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import retrofitdemo.fujisoft.com.filterdemo.R;

/**
 * Created by 860617010 on 2017/8/2.
 */

public class StickPicAdapter extends RecyclerView.Adapter<StickPicAdapter.StickPicViewHolder> {
    private OnClickListener onClickListener;
    private int[] picData;
    private Context context;

    public StickPicAdapter(Context context,int[] picData) {
        this.context = context;
        this.picData = picData;
    }

    @Override
    public StickPicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_item_stick_pic,parent,false);
        StickPicViewHolder holder = new StickPicViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener != null) {
                    onClickListener.clickListener(v, (Integer) v.getTag());
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(StickPicViewHolder holder, int position) {
        holder.imageView.setImageResource(picData[position]);
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return picData.length;
    }


    class StickPicViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        public StickPicViewHolder(View itemView) {
            super(itemView);
            imageView= (ImageView) itemView.findViewById(R.id.iv_stick_type);
        }
    }

    public interface OnClickListener{
        void clickListener(View view,int position);
    }
    public void setOnClickListener(OnClickListener onClickListener){
        this.onClickListener = onClickListener;
    }
}
