package example.com.testanimation;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends Activity {


    private ImageView iv_connect;
    private AnimationDrawable animationDrawable;
    private Button button1;
    private Button button2;
    private ImageView iv_connect2;
    private AnimationDrawable animationDrawable2;


    private TasksCompletedView mTasksView;
    private int mTotalProgress;
    private int mCurrentProgress;

    private boolean ringflag = true;

    private Thread ringthread;


    private ImageView iv_user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_one);

        iv_connect = (ImageView) findViewById(R.id.iv_connect);
        iv_connect2 = (ImageView) findViewById(R.id.iv_connect2);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        mTasksView = (TasksCompletedView) findViewById(R.id.tasks_view);
        iv_user = (ImageView) findViewById(R.id.iv_user);
        initVariable();
        initData();
    }

    public void initData() {
        iv_user.setOnTouchListener(new PicOnTouchListener());
    }


    public class PicOnTouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            Log.i("11111", "event.getAction()----->" + event.getAction());


            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
//                    Log.i("11111", "----->ACTION_DOWN");
                    iv_user.setImageDrawable(getResources().getDrawable(R.drawable.user_big));
                    break;
                case MotionEvent.ACTION_UP:
//                    Log.i("11111", "----->ACTION_UP");
                    iv_user.setImageDrawable(getResources().getDrawable(R.drawable.user));
                    break;
            }
            return true;
        }
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("11111", "动画开始了");
                    iv_connect.setImageDrawable(getResources().getDrawable(R.drawable.animationaround));
                    animationDrawable = (AnimationDrawable) iv_connect.getDrawable();

                    iv_connect2.setImageDrawable(getResources().getDrawable(R.drawable.animationaround2));
                    animationDrawable2 = (AnimationDrawable) iv_connect2.getDrawable();


                    animationDrawable.start();
                    animationDrawable2.start();
// ------------------------------华丽的分割线------------------------------//
                    mCurrentProgress = 0;
                    ringflag = true;
                    if (ringthread == null) {
                        ringthread = new Thread(new ProgressRunable());
                    }
                    ringthread.start();
                }
            });
            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("11111", "动画停止了");
//                    animationDrawable.stop();
//                    animationDrawable2.stop();
// ------------------------------华丽的分割线------------------------------//
//                    ringflag = false;
//                    if (!ringthread.isInterrupted()) {
//                        ringthread.interrupt();
//                    }
//                    ringthread = null;

// ------------------------------华丽的分割线------------------------------//
                    CustomDialogJumpLogin dialogJumpLogin = new CustomDialogJumpLogin(MainActivity.this, "警告", "你的账号异常，无法使用，请重新登录！");

                }
            });
        } else {
        }
    }

    private void initVariable() {
        mTotalProgress = 100;
        mCurrentProgress = 0;
    }

    class ProgressRunable implements Runnable {
        @Override
        public void run() {
            while (ringflag) {
                try {
                    if (mCurrentProgress < mTotalProgress) {
                        mCurrentProgress += 1;
                        mTasksView.setProgress(mCurrentProgress);
                    } else {
                        mCurrentProgress = 0;
                    }
                    Thread.sleep(20);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
