package retrofitdemo.fujisoft.com.filterdemo;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GetPhotoActivity extends AppCompatActivity {

    @BindView(R.id.btn_get_photo_album)
    Button btnGetPhotoAlbum;
    @BindView(R.id.btn_get_photo_camera)
    Button btnGetPhotoCamera;
    @BindView(R.id.btn_start_activity)
    Button btnStartActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_photo);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_get_photo_album, R.id.btn_get_photo_camera,R.id.btn_start_activity})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_get_photo_album:
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");//相片类型
                startActivityForResult(intent, 1);
                break;
            case R.id.btn_get_photo_camera:
                break;
            case R.id.btn_start_activity:
                startMainActivity();
                break;
        }
    }

    /**
     * 跳转到主页activity
     */
    private void startMainActivity() {
        if (TextUtils.isEmpty(pic_path)) {
            Toast.makeText(this,"请先选择一张图片",Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra("uri",uri.toString());
        intent.putExtra("pic_path",pic_path);
        startActivity(intent);
    }

    private Uri uri;
    private String pic_path;
    private static final String TAG = "MainActivity";

    /**
     * 选择照片返回数据的方法
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && data != null) {
            uri = data.getData();
            String[] filePathColumn = {MediaStore.MediaColumns.DATA};
            Cursor cursor = GetPhotoActivity.this.getContentResolver().query(
                    uri, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            pic_path = cursor.getString(columnIndex);
            Log.i(TAG, "onActivityResult: " + uri);
            Log.i(TAG, "onActivityResult: " + pic_path);
        }
    }
}
