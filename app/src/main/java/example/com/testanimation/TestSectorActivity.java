package example.com.testanimation;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/7/10.
 */
public class TestSectorActivity extends Activity implements View.OnClickListener {


    //    private int[] res = {R.id.iv_open, R.id.iv_camera, R.id.iv_music, R.id.iv_place, R.id.iv_sleep, R.id.iv_thought, R.id.iv_with};
//    private int[] res = {R.id.iv_open, R.id.iv_camera, R.id.iv_music, R.id.iv_place};
    private int[] res = {R.id.tv_open, R.id.tv_music, R.id.tv_place, R.id.tv_sleep};
    private List<ImageView> imageViewList = new ArrayList<ImageView>();
    private List<TextView> textViewList = new ArrayList<TextView>();
    private boolean isOpen = false;//菜单是否打开状态
    private int r = 500;//扇形半径
    private float angle;//按钮之间的夹角
    private final long intervalTime = 200; //菜单展开的时间间隔

    private float[] angleset = {0, 0, 60, 300};//按钮之间的夹角集合


    private ImageView iv_open;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    textViewList.get((Integer) msg.obj).setVisibility(View.GONE);
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sector);

        initImageView();
        //计算按钮之间的夹角
//        angle = (float) Math.PI / (2 * (res.length - 1));
    }

    /**
     * 初始化View
     */
    private void initImageView() {
//        ImageView imageView = null;
//        for (int i = 0; i < res.length; i++) {
//            imageView = (ImageView) findViewById(res[i]);
//            imageView.setOnClickListener(this);
//            imageViewList.add(imageView);
//        }
        TextView textView = null;
        for (int i = 0; i < res.length; i++) {
            textView = (TextView) findViewById(res[i]);
            textViewList.add(textView);
        }
        iv_open = (ImageView) findViewById(R.id.iv_open);
        iv_open.setOnClickListener(this);
    }

    /**
     * 点击事件
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_open:
                if (isOpen) {
                    closeAnim();
                } else {
                    startAnim();
                }
                break;
            default:
                Toast.makeText(TestSectorActivity.this, "" + view.getId(), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    /**
     * 展开菜单
     */
    private void startAnim() {
        ObjectAnimator animatorX = null;
        ObjectAnimator animatorY = null;
        float translationX;//横坐标偏移距离
        float translationY;//纵坐标偏移距离
        for (int i = 1; i < res.length; i++) {
            textViewList.get(i).setVisibility(View.VISIBLE);
//            translationY = (float) (r * Math.sin(i * angle));
//            translationX = (float) (r * Math.cos(i * angle));
            translationY = (float) (r * Math.cos(angleset[i] * Math.PI / 180));
            translationX = (float) (r * Math.sin(angleset[i] * Math.PI / 180));

//            int y = ScreenUtil.px2dip(TestSectorActivity.this, translationY);
//            int x = ScreenUtil.px2dip(TestSectorActivity.this, translationX);


            animatorX = ObjectAnimator.ofFloat(textViewList.get(i), "translationX", 0F, translationX);
            animatorY = ObjectAnimator.ofFloat(textViewList.get(i), "translationY", 0F, -translationY);
            AnimatorSet animSet = new AnimatorSet();
            animSet.playTogether(animatorX, animatorY);
            animSet.setDuration(i * intervalTime);
            animSet.start();
        }
        isOpen = true;
    }

    /**
     * 关闭菜单
     */
    private void closeAnim() {
        AnimatorSet animSet = null;
        ObjectAnimator animatorX = null;
        ObjectAnimator animatorY = null;
        float translationX;//横坐标偏移距离
        float translationY;//纵坐标偏移距离
        for (int i = res.length - 1; i > 0; i--) {
//            translationX = (float) (r * Math.sin(i * angle));
//            translationY = (float) (r * Math.cos(i * angle));
            translationY = (float) (r * Math.cos(angleset[i] * Math.PI / 180));
            translationX = (float) (r * Math.sin(angleset[i] * Math.PI / 180));
            animatorX = ObjectAnimator.ofFloat(textViewList.get(i), "translationX", translationX, 0F);
            animatorY = ObjectAnimator.ofFloat(textViewList.get(i), "translationY", -translationY, 0F);
            animSet = new AnimatorSet();
            animSet.playTogether(animatorX, animatorY);
            animSet.setDuration((res.length - i) * intervalTime);
            animSet.start();


            Message message = Message.obtain();
            message.what = 1;
            message.obj = i;
            handler.sendMessageDelayed(message, animSet.getDuration() - 50);
        }


        isOpen = false;
    }

    /**
     * 监听是否按下返回键
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //如果按下返回键，执行退出操作
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            showDialog();
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 退出弹框
     */
    private void showDialog() {
        new AlertDialog.Builder(this)
                .setTitle("退出")
                .setMessage("要退出么？")
                .setNegativeButton("退出", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        finish();
                    }
                })
                .setPositiveButton("返回", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                }).show();
    }
}
