package com.zhu.gjj.activity;

import com.zhu.gjj.R;
import com.zhu.gjj.utils.StatusBarUtil;
import com.zhu.gjj.utils.Utils;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zhu.gjj.utils.MyRequestCallBack;
/**
 * @author WangFusheng
 * @ClassName: BaseActivity
 * @Description: 基类
 * @date 2015年9月1日 下午5:00:56
 */
@SuppressLint("NewApi")
public class BaseActivity extends FragmentActivity implements MyRequestCallBack.SuccessResult, OnClickListener {
    public static final String TAG = BaseActivity.class.getSimpleName();
    boolean showDialogType = false;
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        reSetStatusBarColorRed();
    }
    /**
     * @param @param bgTopBar
     * @return void
     * @throws
     * @Title: reSetStatusBarColor
     * @Description: 设置状态栏\导航栏颜色4.4以上有用
     * @author WangFusheng
     */
    @SuppressLint("ResourceAsColor")
	public void reSetStatusBarColorRed() {
        StatusBarUtil.setStatusBarColor(this,R.color.title_bg_color);

    }
    @Override
    protected void onResume() {
        super.onResume();
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    public void actionBack(View view) {
        onBackPressed();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    public void onClick(View v) {

    }
    ProgressDialog mProgressDialog;
    /*
	 * 显示进度框
	 */
	public void showProgressDialog(String msg) {
		if (Utils.isRunning(this)) {
			if (mProgressDialog == null) {
				mProgressDialog = new ProgressDialog(this,R.style.MyDialogStyle);
				mProgressDialog.setCancelable(true);
				mProgressDialog.setCanceledOnTouchOutside(true);
				mProgressDialog.setMessage(msg);
				
			}
			if (mProgressDialog!=null&&!mProgressDialog.isShowing()){
//				mProgressDialog.show();
				showDialogType = true;
			}
				
		}
	}

	public void showProgressDialog(String msg, boolean cancleble) {
		if (Utils.isRunning(this)) {
			if (mProgressDialog == null) {
				mProgressDialog = new ProgressDialog(this,R.style.MyDialogStyle);
				mProgressDialog.setCancelable(true);
				mProgressDialog.setMessage(msg);
			}
			if (mProgressDialog!=null&&!mProgressDialog.isShowing()){
//				mProgressDialog.show();
				showDialogType = true;
			}
		}


	}

	/*
	 * 隐藏进度框
	 */
	public void cancelProgressDialog() {
		if (Utils.isRunning(this)) {
			if (mProgressDialog != null && mProgressDialog.isShowing()){
				mProgressDialog.cancel();
				showDialogType = false;
			}
				
		}
	}


	@Override
	public void onSuccessResult(String str, int flag) {

	}

	@Override
	public void onFaileResult(String str, int flag) {

	}
   
    /**
     * 点击空白隐藏软键盘
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {

            // 获得当前得到焦点的View，一般情况下就是EditText（特殊情况就是轨迹求或者实体案件会移动焦点）
            View v = getCurrentFocus();

            if (isShouldHideInput(v, ev)) {
                hideSoftInput(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时没必要隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 多种隐藏软件盘方法的其中一种
     *
     * @param token
     */
    private void hideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
