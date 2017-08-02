package retrofitdemo.fujisoft.com.filterdemo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.js.photosdk.operate.ImageObject;
import com.js.photosdk.operate.OperateUtils;
import com.js.photosdk.operate.OperateView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofitdemo.fujisoft.com.filterdemo.adapter.FilterRecyclerAdapter;
import retrofitdemo.fujisoft.com.filterdemo.adapter.StickPicAdapter;
import retrofitdemo.fujisoft.com.filterdemo.bean.FilterBean;
import retrofitdemo.fujisoft.com.filterdemo.filter.Filter;

//图片URI   content://media/external/images/media/46404
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.tv_finish)
    TextView tvFinish;
    @BindView(R.id.koutu)
    TextView koutu;
    @BindView(R.id.cachu)
    TextView cachu;
    @BindView(R.id.filter)
    TextView filter;
    @BindView(R.id.tietu)
    TextView tietu;
    @BindView(R.id.iv_show_pic)
    ImageView ivShowPic;
    @BindView(R.id.type_select)
    RecyclerView typeSelect;//展示滤镜样式,贴图样式的RecyclerView
    @BindView(R.id.group_iv)
    LinearLayout groupIv;
    private TextView[] textViews;//底部，样式选择的TextView集合
    private List<FilterBean> list;//滤镜样式的bean集合
    private int[] stickPic;//贴图资源集合
    private Uri uri;
    private Bitmap bitmap;
    private String pic_path;//图片路径

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        uri = Uri.parse(intent.getStringExtra("uri"));
        pic_path = intent.getStringExtra("pic_path");
        bitmap = getBitmapFromUri(uri);
        operateUtils = new OperateUtils(this);
        initData();
        initView();
    }

    private void initView() {
        koutu.setSelected(true);//默认是抠图状态
        Glide.with(this).load(pic_path).into(ivShowPic);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        //将底部样式选择的TextView全部加入数组，以便后期维护。
        textViews = new TextView[]{koutu, cachu, filter, tietu};
        //滤镜数据
        list = new ArrayList<>();
        list.add(new FilterBean("原图", false));
        list.add(new FilterBean("怀旧", false));
        list.add(new FilterBean("粉嫩", false));
        list.add(new FilterBean("粉色", false));
        list.add(new FilterBean("浅蓝", false));
        list.add(new FilterBean("美白", false));
        list.add(new FilterBean("亮白", false));
        list.add(new FilterBean("黑白", false));
        list.add(new FilterBean("暗色", false));
        //贴图数据
        stickPic = new int[]{R.drawable.watermark_chunvzuo, R.drawable.comment,
                R.drawable.gouda, R.drawable.guaishushu, R.drawable.haoxingzuop,
                R.drawable.wanhuaile, R.drawable.xiangsi, R.drawable.xingzuokong,
                R.drawable.xinnian, R.drawable.zaoan, R.drawable.zuile,
                R.drawable.zuo, R.drawable.zui};
    }


    @OnClick({R.id.iv_back, R.id.tv_cancel, R.id.tv_finish, R.id.koutu, R.id.cachu, R.id.filter, R.id.tietu, R.id.iv_show_pic})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                break;
            case R.id.tv_cancel:
                break;
            case R.id.tv_finish:
                //点击完成保存滤镜化之后的图片到相册
                //保存bitmap图片,调用系统提供的插入图库的方法,将bitmap保存成图片
                MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "title", "description");
                Toast.makeText(this, "保存图片成功", Toast.LENGTH_SHORT).show();
                break;
            case R.id.koutu:
                colorChange(koutu);
                tvCancel.setVisibility(View.VISIBLE);
                break;
            case R.id.cachu:
                colorChange(cachu);
                tvCancel.setVisibility(View.VISIBLE);
                break;
            case R.id.filter:
                colorChange(filter);
                filterMake();//滤镜
                break;
            case R.id.tietu:
                colorChange(tietu);
                stickPic();//贴图
                break;
            case R.id.iv_show_pic:
                break;
        }
    }

    private static final String TAG = "MainActivity";

    /**
     * 滤镜
     */
    private void filterMake() {
        typeSelect.setVisibility(View.VISIBLE);
        tvCancel.setVisibility(View.INVISIBLE);
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        typeSelect.setLayoutManager(linearLayoutManager);
        final FilterRecyclerAdapter adapter = new FilterRecyclerAdapter(this, list);
        //设置适配器
        typeSelect.setAdapter(adapter);
        adapter.setOnItemClickListener(new FilterRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                for (int i = 0; i < 9; i++) {
                    if (i == position) {
                        list.get(position).setSelect(true);
                    } else {
                        list.get(i).setSelect(false);
                    }
                }
                adapter.notifyDataSetChanged();
                makeFilterImage(position);
            }
        });
    }

    /**
     * 设置TextView选中的监听，改变对应字体颜色
     *
     * @param tv
     */
    private void colorChange(TextView tv) {
        for (TextView textView : textViews) {
            if (tv == textView) {
                tv.setSelected(true);
            } else {
                textView.setSelected(false);
            }
            if (tv != filter) {
                typeSelect.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 通过一个uri获取一个Bitmap对象
     * 此方法没有对图片的大小进行判断和处理，在图片太大的情况下，可能会出现OOM的问题。
     *
     * @param uri
     * @return
     */
    private Bitmap getBitmapFromUri(Uri uri) {
        try {
            // 读取uri所在的图片
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
            return bitmap;
        } catch (Exception e) {
            Log.e("[Android]", e.getMessage());
            Log.e("[Android]", "目录为：" + uri);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据点击条目不同，给图片设置不同的滤镜
     *
     * @param position
     */
    private void makeFilterImage(int position) {
        if (uri == null) {
            Toast.makeText(this, "请先选择一张图片", Toast.LENGTH_SHORT).show();
            return;
        }
        Bitmap bitmap = getBitmapFromUri(uri);
        bitmap = Filter.changeToGray(bitmap, position);
        //把添加滤镜后的效果显示在imageview上
        ivShowPic.setImageBitmap(bitmap);
    }

    /*-------------------------------------------贴图----------------------------------------------------*/
    private OperateUtils operateUtils;
    private OperateView operateView;
    private Handler myHandler ;
    private Timer timer = new Timer();
    private TimerTask task;
    private boolean timerIsSchedule = false;//timer默认情况没有执行任务

    /**
     * 贴图
     */
    private void stickPic() {
        typeSelect.setVisibility(View.VISIBLE);
        tvCancel.setVisibility(View.INVISIBLE);
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        typeSelect.setLayoutManager(linearLayoutManager);
        final StickPicAdapter adapter = new StickPicAdapter(this,stickPic);
        //设置适配器
        typeSelect.setAdapter(adapter);
        adapter.setOnClickListener(new StickPicAdapter.OnClickListener() {
            @Override
            public void clickListener(View view, int position) {
                addpic(stickPic[position]);
            }
        });
        if (myHandler == null ) {
            myHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    if (msg.what == 1) {
                        if (groupIv.getWidth() != 0) {
                            Log.i("LinearLayoutW", groupIv.getWidth() + "");
                            Log.i("LinearLayoutH", groupIv.getHeight() + "");
                            // 取消定时器
                            timer.cancel();
                            fillContent();
                        }
                    }
                }
            };
        }

        if (task == null) {
            task = new TimerTask() {
                public void run() {
                    Message message = new Message();
                    message.what = 1;
                    myHandler.sendMessage(message);
                }
            };
        }
        if (!timerIsSchedule) {
            initStickPic();
        }
        ivShowPic.setVisibility(View.GONE);
    }

    /**
     * 贴图的初始化操作
     */
    private void initStickPic() {
        // 延迟每次延迟10 毫秒 隔1秒执行一次
        timer.schedule(task, 10, 1000);
        timerIsSchedule = true;//执行任务之后把标记设置为true
    }

    private void fillContent() {
        Bitmap resizeBmp = BitmapFactory.decodeFile(pic_path);
        operateView = new OperateView(MainActivity.this, resizeBmp);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                resizeBmp.getWidth(), resizeBmp.getHeight());
        operateView.setLayoutParams(layoutParams);
        groupIv.addView(operateView);
        operateView.setMultiAdd(true); // 设置此参数，可以添加多个图片
    }

    /**
     * 点击贴图不同的item，将对应的图片添加到operateView上
     * @param waterPic
     */
    private void addpic(int waterPic) {
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), waterPic);
        // ImageObject imgObject = operateUtils.getImageObject(bmp);
        ImageObject imgObject = operateUtils.getImageObject(bmp, operateView,
                5, 150, 100);
        operateView.addItem(imgObject);
    }
}
