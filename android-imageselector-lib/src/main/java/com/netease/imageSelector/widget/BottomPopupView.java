package com.netease.imageSelector.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.netease.imageSelector.R;


/**
 * author: Chen Wei
 * time: 16/10/20
 * desc: 通用的底部弹出View
 */

public class BottomPopupView extends PopupWindow implements PopupWindow.OnDismissListener, View.OnClickListener {

    private CommitConfirmListener listener;


    private Button confirmBtn;
    private Button cancelBtn;
    private TextView titleTv;

    public BottomPopupView(Context context, String title, String confirm, String cancel) {
        super(context);

        View view = LayoutInflater.from(context).inflate(R.layout.bottom_popup_view, null);
        titleTv = (TextView)view.findViewById(R.id.id_tv_popup_window_title);
        titleTv.setText(title);
        confirmBtn = (Button)view.findViewById(R.id.id_btn_confirm);
        confirmBtn.setText(confirm);
        cancelBtn = (Button)view.findViewById(R.id.id_btn_cancel);
        cancelBtn.setText(cancel);
        confirmBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);

        setContentView(view);

        setOutsideTouchable(true);
        setFocusable(true);
        setOnDismissListener(this);
        setAnimationStyle(R.style.popUpwindow);
    }

    @Override
    public void onClick(View v) {
        if (listener == null) {
            dismiss();
            return;
        }
        if (v.getId() == R.id.id_btn_confirm) {
            if(listener != null){
                listener.onConfirm();
            }
        } else  if (v.getId() == R.id.id_btn_cancel) {
            if(listener != null){
                listener.onCancel();
            }
        }
        dismiss();
    }

    @Override
    public void onDismiss() {
        if(listener != null){
            listener.onPopWindowDismiss();
        }
    }

    public void setCommitConfirmListener(CommitConfirmListener listener) {
        this.listener = listener;
    }

    public interface CommitConfirmListener {
        void onConfirm();
        void onCancel();
        void onPopWindowDismiss();
    }
}
