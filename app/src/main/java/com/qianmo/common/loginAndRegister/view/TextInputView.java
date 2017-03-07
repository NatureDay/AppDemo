package com.qianmo.common.loginAndRegister.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;

import com.example.appdemo.R;

/**
 * 自定义文本输入组合view
 */

public class TextInputView extends RelativeLayout implements CompoundButton.OnCheckedChangeListener, View.OnClickListener, TextWatcher {

    private Context mContext;
    private AppCompatTextView mLabel;
    private AppCompatEditText mInput;
    private AppCompatImageView mClear;

    public TextInputView(Context context) {
        this(context, null);
    }

    public TextInputView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextInputView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        mContext = context;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TextInputView);
        String label = a.getString(R.styleable.TextInputView_label);
        String hint = a.getString(R.styleable.TextInputView_textHint);
        int length = a.getInt(R.styleable.TextInputView_textLength, 50);
        boolean visiable = a.getBoolean(R.styleable.TextInputView_inputPassword, false);
        a.recycle();

        View view = LayoutInflater.from(context).inflate(R.layout.common_layout_text_input, this, true);
        mLabel = (AppCompatTextView) view.findViewById(R.id.text_input_label);
        mInput = (AppCompatEditText) view.findViewById(R.id.text_input_area);
        mClear = (AppCompatImageView) view.findViewById(R.id.text_input_clear);
        if (!TextUtils.isEmpty(label)) {
            mLabel.setText(label);
        }
        if (!TextUtils.isEmpty(hint)) {
            mInput.setHint(hint);
        }
        mInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(length)});
        //updatePasswordStatus(false);
    }

    private void setInputType(int type) {
        mInput.setInputType(type);
    }

    private void updateViewStatus() {
        setInputType(InputType.TYPE_CLASS_TEXT);
        updateClearStatus();
        mInput.addTextChangedListener(this);
        mClear.setOnClickListener(this);
    }

    private void updatePasswordStatus(boolean isChecked) {
        if (isChecked) {
            mInput.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            mInput.setSelection(mInput.length());
        } else {
            mInput.setTransformationMethod(PasswordTransformationMethod.getInstance());
            mInput.setSelection(mInput.length());
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        updateClearStatus();
    }

    private void updateClearStatus() {
        if (mInput.getText().length() == 0) {
            mClear.setVisibility(View.INVISIBLE);
        } else {
            mClear.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_input_clear:
                mInput.getText().clear();
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        updatePasswordStatus(isChecked);
    }

    /**
     * 对外接口，设置输入框内容
     *
     * @param resId
     */
    public void setText(int resId) {
        setText(mContext.getString(resId));
    }

    /**
     * 对外接口，设置输入框内容
     *
     * @param text
     */
    public void setText(String text) {
        mInput.setText(text);
    }

    /**
     * 对外接口，获取输入框内容
     *
     * @return
     */
    public String getText() {
        return mInput.getText().toString();
    }

}
