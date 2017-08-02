package retrofitdemo.fujisoft.com.filterdemo.filter;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;

/**
 * Created by 860617010 on 2017/7/31.
 */

public class Filter {
    public static final int ORIGINAL_PIC = 0;//原图
    public static final int REMEMBER_OLD = 1;//怀旧
    public static final int YOUNG_PINK = 2;//粉嫩
    public static final int PINK = 3;//粉色
    public static final int YOUNG_BLUE = 4;//浅蓝
    public static final int BEAUTIFUL_WHITE = 5;//美白
    public static final int LIGHT_WHITE = 6;//亮白
    public static final int BLACK_WHITE = 7;//黑白
    public static final int DARK_WHITE = 8;//暗色

    // 黑白效果函数
    public static Bitmap changeToGray(Bitmap bitmap, int type) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        Bitmap grayBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(grayBitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true); // 设置抗锯齿

        ColorMatrix colorMatrix = null;
        switch (type) {
            case ORIGINAL_PIC:
                colorMatrix = getOriginalPic();
                break;
            case REMEMBER_OLD:
                colorMatrix = rememberOld();
                break;
            case YOUNG_PINK://粉嫩
                colorMatrix = getYoungPink();
                break;
            case PINK://粉色
                colorMatrix = getPink();
                break;
            case YOUNG_BLUE://浅蓝
                colorMatrix = getYoungBlue();
                break;
            case BEAUTIFUL_WHITE://美白
                colorMatrix = getBeautifulWhite();
                break;
            case LIGHT_WHITE://亮白
                colorMatrix = getLightWhite();
                break;
            case BLACK_WHITE://黑白
                colorMatrix = getBlackWhite();
                break;
            case DARK_WHITE://暗色
                colorMatrix = getDarkWhite();
                break;
        }
        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(colorMatrix);
        paint.setColorFilter(filter);
        canvas.drawBitmap(bitmap, 0, 0, paint);
        return grayBitmap;
    }

    /**
     * 原图
     * @return
     */
    public static ColorMatrix getOriginalPic() {
        float[] array = {1, 0, 0, 0, 0,
                0, 1, 0, 0, 0,
                0, 0, 1, 0, 0,
                0, 0, 0, 1, 0};
        return new ColorMatrix(array);
    }

    /**
     * 怀旧
     * @return
     */
    private static ColorMatrix rememberOld() {
        float[] array = {0.393f, 0.769f, 0.189f, 0, 0,
                0.349f, 0.686f, 0.168f, 0, 0,
                0.272f, 0.534f, 0.131f, 0, 0,
                0, 0, 0, 1, 0};
        return new ColorMatrix(array);
    }

    /**
     * 粉嫩
     * @return
     */
    private static ColorMatrix getYoungPink() {
        float[] array = {1, 0, 0, 0, 50,
                0, 1, 0, 0, 50,
                0, 0, 1, 0, 0,
                0, 0, 0, 1, 0};
        ColorMatrix colorMatrix = new ColorMatrix(array);
        return colorMatrix;
    }

    /**
     * 粉色
     * @return
     */
    private static ColorMatrix getPink() {
        float[] array = {1, 0, 0, 0, 0,
                0, 1, 0, 0, 0,
                0, 0, 1.6f, 0, 0,
                0, 0, 0, 1, 0};
        ColorMatrix colorMatrix = new ColorMatrix(array);
        return colorMatrix;
    }

    /**
     * 浅蓝
     * @return
     */
    private static ColorMatrix getYoungBlue() {
        float[] array = {1, 0, 0, 0, 0,
                0, 1.4f, 0, 0, 0,
                0, 0, 1, 0, 0,
                0, 0, 0, 1, 0};
        ColorMatrix colorMatrix = new ColorMatrix(array);
        return colorMatrix;
    }

    /**
     * 美白
     * @return
     */
    private static ColorMatrix getBeautifulWhite() {
        float[] array = {2, 0, 0, 0, 0,
                0, 1, 0, 0, 0,
                0, 0, 1, 0, 0,
                0, 0, 0, 1, 0};
        ColorMatrix colorMatrix = new ColorMatrix(array);
        return colorMatrix;
    }

    /**
     * 亮白
     * @return
     */
    private static ColorMatrix getLightWhite() {
        float[] array = {1.438F, -0.062F, -0.062F, 0, 0,
                -0.122F, 1.378F, -0.122F, 0, 0,
                -0.016F, -0.016F, 1.483F, 0, 0,
                -0.03F, 0.05F, -0.02F, 1, 0};
        ColorMatrix colorMatrix = new ColorMatrix(array);
        return colorMatrix;
    }

    /**
     * 黑白
     * @return
     */
    private static ColorMatrix getBlackWhite() {
        float[] array = {1, 0, 0, 0, 100,
                0, 1, 0, 0, 100,
                0, 0, 1, 0, 0,
                0, 0, 0, 1, 0};
        ColorMatrix colorMatrix = new ColorMatrix(array);
        return colorMatrix;
    }

    /**
     * 暗色
     * @return
     */
    private static ColorMatrix getDarkWhite() {
        float[] array = {2, 0, 0, 0, 0,
                0, 1, 0, 0, 0,
                0, 0, 1, 0, 0,
                0, 0, 0, 1, 0};
        ColorMatrix colorMatrix = new ColorMatrix(array);
        return colorMatrix;
    }
}
