package com.netease.imageSelector.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.netease.imageSelector.R;


public class DialogCommonView extends LinearLayout {

	private static final String TAG = "DialogCommonView";
	TextView mMessageTextView;
	TextView mLeftBtn;
	TextView mRightBtn;
	View mBtnDividingLine;
	
	public DialogCommonView(Context context) {
		super(context);
		initViews();
	}

	public DialogCommonView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initViews();
	}

	public DialogCommonView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initViews();
	}

	private void initViews() {
		LayoutInflater.from(getContext()).inflate(R.layout.dialog_course_evaluate, this, true);
		mMessageTextView = (TextView) findViewById(R.id.dialog_message);
		mLeftBtn = (TextView) findViewById(R.id.dialog_btn_left);
		mRightBtn = (TextView) findViewById(R.id.dialog_btn_right);
		mBtnDividingLine = findViewById(R.id.dialog_btn_dividing_line);

		mRightBtn.setTextColor(this.getResources().getColor(R.color.color_f6454a));


		//默认显示一个按钮
		mLeftBtn.setVisibility(View.GONE);
		mBtnDividingLine.setVisibility(View.GONE);
	}
	
	public void setLeftBtn(String text, OnClickListener listener) {
		setLeftBtnText(text);
		setLeftBtnOnClickListener(listener);
	}
	
	public void setLeftBtn(int res, OnClickListener listener) {
		setLeftBtnText(res);
		setLeftBtnOnClickListener(listener);
	}
	
	public void setRightBtn(String text, OnClickListener listener) {
		setRightBtnText(text);
		setRightBtnOnClickListener(listener);
	}
	
	public void setRightBtn(int res, OnClickListener listener) {
		setRightBtnText(res);
		setRightBtnOnClickListener(listener);
	}
	
	public void setMessage(String message) {
		mMessageTextView.setText(message);
	}
	
	public void setMessage(int res) {
		setMessage(getResources().getString(res));
	}
	
	public void setLeftBtnText(String text) {
		mLeftBtn.setText(text);
		mLeftBtn.setVisibility(View.VISIBLE);
		mBtnDividingLine.setVisibility(View.VISIBLE);
	}
	
	public void setLeftBtnText(int res) {
		setLeftBtnText(getResources().getString(res));
	}

	public void setRightBtnText(String text) {
		mRightBtn.setText(text);
	}
	
	public void setRightBtnText(int res) {
		setRightBtnText(getResources().getString(res));
	}
	
	public void setLeftBtnOnClickListener(OnClickListener listener) {
		mLeftBtn.setOnClickListener(listener);
	}

	public void setRightBtnOnClickListener(OnClickListener listener) {
		mRightBtn.setOnClickListener(listener);
	}
}
