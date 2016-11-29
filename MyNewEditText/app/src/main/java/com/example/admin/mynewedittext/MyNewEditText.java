package com.example.admin.mynewedittext;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by admin on 2016/10/5.
 */
public class MyNewEditText extends RelativeLayout {
    private TextView contentTv;
    private EditText contentEdt;
    private LinearLayout lin;
    private Integer DurationTime = 200;
    private String hintStr;
    private final int DEFAULT_PADDING = 0;
    private int colorSelectTv = getResources().getColor(R.color.red);
    private int backGround;
    private int left, right, top, bottom;
    private int hintLinColor;
    private final int DEFAULT_COLOR = getResources().getColor(R.color.white);

    public MyNewEditText(Context context) {
        super(context);
        init();
    }

    public MyNewEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        initView(attrs);
    }

    public MyNewEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        initView(attrs);


    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MyNewEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
        initView(attrs);
    }

    public void init() {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.new_edittext, this);
        contentEdt = (EditText) v.findViewById(R.id.edt_content);
        contentTv = (TextView) v.findViewById(R.id.tv_content);
        lin = (LinearLayout) v.findViewById(R.id.lin);
        lin.setVisibility(GONE);
        hintStr = contentEdt.getHint().toString();
        setContentEdtOnTouch();
    }

    public void initView(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs,
                R.styleable.MyNewEditText);
        hintStr = a.getString(R.styleable.MyNewEditText_hintText);
        backGround = a.getResourceId(R.styleable.MyNewEditText_hintBackGround, -1);
        if (hintStr.isEmpty()) {
            hintStr = contentEdt.getHint().toString();
        } else {
            contentEdt.setHint(hintStr);
        }
        contentTv.setText(hintStr);
        if (backGround != -1) {
            setContentEdtBackGround(backGround);
        }
        left = a.getDimensionPixelSize(R.styleable.MyNewEditText_paddingLeft, DEFAULT_PADDING);
        right = a.getDimensionPixelSize(R.styleable.MyNewEditText_paddingRight, DEFAULT_PADDING);
        top = a.getDimensionPixelSize(R.styleable.MyNewEditText_paddingTop, DEFAULT_PADDING);
        bottom = a.getDimensionPixelSize(R.styleable.MyNewEditText_paddingBotton, DEFAULT_PADDING);
        hintLinColor = a.getColor(R.styleable.MyNewEditText_hintLinColor, DEFAULT_COLOR);
        lin.setBackgroundColor(hintLinColor);
        contentEdt.setPadding(left, top, right, bottom);
        contentTv.setPadding(contentEdt.getCompoundPaddingLeft(), contentEdt.getCompoundPaddingTop(), contentEdt.getCompoundPaddingRight(), contentEdt.getCompoundPaddingBottom());

    }

    public void setContentEdtOnTouch() {
        contentEdt.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    contentEdt.setHint("");
                }
                setContentTvAnimal(hasFocus);
            }
        });
    }

    public void setContentTvAnimal(boolean isFocus) {
        if (isFocus) {
            if (contentEdt.getText().toString().length() == 0) {
                AnimalIsFocus();
            }
        } else {
            if (contentEdt.getText().toString().length() == 0) {
                AnimalNotFocus();
            }
        }
    }

    public void AnimalIsFocus() {
        contentTv.setVisibility(View.VISIBLE);
        contentTv.setTextColor(colorSelectTv);
        AnimationSet set = new AnimationSet(true);
        TranslateAnimation translateAnimation = new TranslateAnimation(0, contentTv.getCompoundPaddingLeft(), 0,
                -contentEdt.getHeight() / 3 * 2);
        ScaleAnimation scaleAnimation = new ScaleAnimation(1f, 0.6f, 1f, 0.6f,
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f);
        set.addAnimation(translateAnimation);
        set.addAnimation(scaleAnimation);
        set.setInterpolator(new LinearInterpolator());
        set.setDuration(DurationTime);
        set.setFillAfter(true);
        contentTv.startAnimation(set);
        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                LayoutParams rl = (LayoutParams) lin.getLayoutParams();
                rl.width = (int) (contentTv.getWidth() * 0.6);
                rl.leftMargin = contentTv.getCompoundPaddingLeft();
                lin.setLayoutParams(rl);
                lin.setVisibility(VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    public void AnimalNotFocus() {
        AnimationSet set = new AnimationSet(true);
        contentTv.setTextColor(contentEdt.getHintTextColors());
        TranslateAnimation translateAnimation = new TranslateAnimation(contentTv.getCompoundPaddingLeft(), 0,
                -contentEdt.getHeight() / 3 * 2, 0);
        ScaleAnimation scaleAnimation = new ScaleAnimation(0.6f, 1f, 0.6f, 1f,
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f);
        set.addAnimation(translateAnimation);
        set.addAnimation(scaleAnimation);
        set.setFillAfter(true);
        set.setDuration(DurationTime);
        set.setInterpolator(new LinearInterpolator());
        contentTv.startAnimation(set);
        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                contentTv.setVisibility(View.GONE);
                contentEdt.setHint(hintStr);
                lin.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    /**
     * 设置选中色
     *
     * @param color
     */
    public void setColorSelectTv(int color) {
        colorSelectTv = color;
    }

    /**
     * 动态设置背景
     *
     * @param backGround
     */
    public void setContentEdtBackGround(int backGround) {
        contentEdt.setBackgroundResource(backGround);
        contentTv.setPadding(contentEdt.getCompoundPaddingLeft(), contentEdt.getCompoundPaddingTop(),
                contentEdt.getCompoundPaddingRight(), contentEdt.getCompoundPaddingBottom());

    }

    public String getContent() {
        return contentEdt.getText().toString();
    }
}


