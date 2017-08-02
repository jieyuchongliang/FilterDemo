package retrofitdemo.fujisoft.com.filterdemo;

import android.graphics.Matrix;
import android.util.Log;
import android.widget.ImageView;

/**
 * Created by 860617010 on 2017/8/2.
 */

public class Knowledge {
    public void getPicWidthAndHight(ImageView iv){
        //显示图片的真实宽高
        //获得ImageView中Image的真实宽高，
        int dw = iv.getDrawable().getBounds().width();
        int dh = iv.getDrawable().getBounds().height();
        Log.d("lxy", "drawable_X = " + dw + ", drawable_Y = " + dh);

        //获得ImageView中Image的变换矩阵
        Matrix m = iv.getImageMatrix();
        float[] values = new float[10];
        m.getValues(values);

        //Image在绘制过程中的变换矩阵，从中获得x和y方向的缩放系数
        float sx = values[0];
        float sy = values[4];
        Log.d("lxy", "scale_X = " + sx + ", scale_Y = " + sy);

        //计算Image在屏幕上实际绘制的宽高
        int cw = (int)(dw * sx);
        int ch = (int)(dh * sy);
        Log.d("lxy", "caculate_W = " + cw + ", caculate_H = " + ch);
    }
}
