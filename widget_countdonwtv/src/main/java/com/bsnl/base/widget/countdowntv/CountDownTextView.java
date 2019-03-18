package com.bsnl.base.widget.countdowntv;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bsnl.base.widget.countdowntv.CDTVEnum.Model;
import com.bsnl.base.widget.countdowntv.CDTVEnum.Show;

import java.util.ArrayList;
import java.util.List;

import static com.bsnl.base.widget.countdowntv.CDTVEnum.DAY;

public class CountDownTextView extends LinearLayout {

    private Context context;
    //View
    private TextView tvDayDecade, tvDayUnit;
    private TextView tvHourDecade, tvHourUnit;
    private TextView tvMinuDecade, tvMinuUnit;
    private TextView tvSecondDecade, tvSecondUnit;
    private TextView tvHour, tvDay, tvMinu, tvSecond;
    private List<TextView> unitList = new ArrayList<>();
    private List<TextView> timeList = new ArrayList<>();

    //Value
    private long time;
    private MyCount mc;

    private int days_decade = 0, days_unit = 0;
    private int hour_decade, hour_unit;
    private int min_decade, min_unit;
    private int sec_decade, sec_unit;

    private stopListener listener;
    private Model model;
    private Show show;

    private View layoutDay, layoutHour, layoutMinu, layoutSecond;

    public void setCownDownListener(stopListener listener) {
        this.listener = listener;
    }

    public interface stopListener {
        void stop();
    }

    public CountDownTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.widget_countdowntv, this, true);
        initView(view);

        initValue(attrs);

    }

    private void initView(View view) {
        tvDayDecade = view.findViewById(R.id.tv_day_decade);
        tvDayUnit = view.findViewById(R.id.tv_day_unit);

        tvHourDecade = view.findViewById(R.id.tv_hour_decade);
        tvHourUnit = view.findViewById(R.id.tv_hour_unit);

        tvHourDecade = view.findViewById(R.id.tv_hour_decade);
        tvHourUnit = view.findViewById(R.id.tv_hour_unit);

        tvMinuDecade = view.findViewById(R.id.tv_minu_decade);
        tvMinuUnit = view.findViewById(R.id.tv_minu_unit);

        tvSecondDecade = view.findViewById(R.id.tv_second_decade);
        tvSecondUnit = view.findViewById(R.id.tv_second_unit);

        tvDay = view.findViewById(R.id.tv_day);
        tvHour = view.findViewById(R.id.tv_hour);
        tvMinu = view.findViewById(R.id.tv_minu);
        tvSecond = view.findViewById(R.id.tv_second);

        layoutDay = view.findViewById(R.id.layout_day);
        layoutHour = view.findViewById(R.id.layout_hour);
        layoutMinu = view.findViewById(R.id.layout_minu);
        layoutSecond = view.findViewById(R.id.layout_second);

        unitList.add(tvDay);
        unitList.add(tvHour);
        unitList.add(tvMinu);
        unitList.add(tvSecond);

        timeList.add(tvDayDecade);
        timeList.add(tvDayUnit);
        timeList.add(tvHourDecade);
        timeList.add(tvHourUnit);
        timeList.add(tvMinuDecade);
        timeList.add(tvMinuUnit);
        timeList.add(tvSecondDecade);
        timeList.add(tvSecondUnit);
    }

    private void initValue(AttributeSet attrs) {
        if (attrs == null)
            return;
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.CountDownTextView);
        if (attributes != null) {

            //获取其他配置
            if (attributes.hasValue(R.styleable.CountDownTextView_model)) {
                int modeInt = attributes.getInt(R.styleable.CountDownTextView_model, Model.Solo.model);
                model = (modeInt == Model.Solo.model ? Model.Solo : Model.Merge);
            }
            if (attributes.hasValue(R.styleable.CountDownTextView_show)) {
                int showInt = attributes.getInt(R.styleable.CountDownTextView_show, Show.All.show);
                if (showInt == Show.Hide.show) {
                    this.show = Show.Hide;
                } else if (showInt == Show.Three.show) {
                    this.show = Show.Three;
                } else {
                    this.show = Show.All;
                }
            }

            int unitColor = 0;
            if (attributes.hasValue(R.styleable.CountDownTextView_unit_text_color)) {
                unitColor = attributes.getColor(R.styleable.CountDownTextView_unit_text_color, getResources().getColor(R.color.cdtv_colorAccent));
            }

            float unitTextSize = 0;
            if (attributes.hasValue(R.styleable.CountDownTextView_unit_text_Size)) {
                unitTextSize = attributes.getDimension(R.styleable.CountDownTextView_unit_text_Size, getResources().getDimension(R.dimen.cdtv_textSize));
            }
            int orientation = 0;

            if (attributes.hasValue(R.styleable.CountDownTextView_unit_orientation)) {
                orientation = attributes.getInt(R.styleable.CountDownTextView_unit_orientation, 1);
            }

            //修改单位样式
            if (unitList != null) {
                for (TextView textView : unitList) {
                    if (unitColor != 0)
                        textView.setTextColor(unitColor);
                    if (unitTextSize > 0)
                        textView.getPaint().setTextSize(unitTextSize);
//                    textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, unitTextSize);
//                    if (orientation == Orientation.Top.code) {
//                        textView.setGravity(Gravity.TOP);
//                    } else if (orientation == Orientation.Bottom.code) {
//                        textView.setGravity(Gravity.BOTTOM);
//                    } else if (orientation == Orientation.Center.code) {
//                        textView.setGravity(Gravity.CENTER);
//                    }
                }
            }

            //修改时间样式
            float boxWidth = 0, boxHeight = 0;
            if (attributes.hasValue(R.styleable.CountDownTextView_box_width)) {
                boxWidth = attributes.getDimension(R.styleable.CountDownTextView_box_width, getResources().getDimension(R.dimen.cdtv_width));
            }
            if (attributes.hasValue(R.styleable.CountDownTextView_box_height)) {
                boxHeight = attributes.getDimension(R.styleable.CountDownTextView_box_height, getResources().getDimension(R.dimen.cdtv_height));
            }

            if (timeList != null) {
                for (TextView textView : timeList) {
                    if (boxWidth > 0 && boxHeight > 0) {
                        ViewGroup.LayoutParams params = textView.getLayoutParams();
                        params.height = (int) boxHeight;
                        params.width = (int) boxWidth;
                        if (model == Model.Merge) {
                            params.width *= 1.5;
                        }
                        textView.setLayoutParams(params);
                    }
                }
            }

            if (model == Model.Merge) {
                tvDayDecade.setVisibility(View.GONE);
                tvHourDecade.setVisibility(View.GONE);
                tvMinuDecade.setVisibility(View.GONE);
                tvSecondDecade.setVisibility(View.GONE);
            } else {
                tvDayDecade.setVisibility(View.VISIBLE);
                tvHourDecade.setVisibility(View.VISIBLE);
                tvMinuDecade.setVisibility(View.VISIBLE);
                tvSecondDecade.setVisibility(View.VISIBLE);
            }
        }
    }

    public void startTimer(long ms) {
        time = ms / 1000;
        mc = new MyCount(time * 1000, 1000);
        mc.start();
    }


    /**
     * 倒计时timer
     */
    private class MyCount extends CountDownTimer {


        public MyCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long l) {
            long ltime = l / 1000;
            int days = (int) (ltime / (60 * 60 * 24));                         //计算出天数
            int hour = (int) ((ltime - (days * 60 * 60 * 24)) / (60 * 60));     //计算出小时
            int minute = (int) ((ltime - (days * 60 * 60 * 24 )- (hour * 60 * 60)) / 60);
            int second = (int) (ltime - (days * 60 * 60 * 24) - (hour * 60 * 60) - minute * 60);


            if (ltime > DAY) {
                days_decade = days / 10;
                days_unit = days % 10;
            }

            if (show == Show.Hide) {
                if (days == 0) {
                    layoutDay.setVisibility(View.GONE);
                }
                if (hour == 0) {
                    layoutHour.setVisibility(View.GONE);
                }
                if (minute == 0) {
                    layoutMinu.setVisibility(View.GONE);
                }
                if (second == 0) {
                    layoutSecond.setVisibility(View.GONE);
                }
            } else if (show == Show.Three) {
                if (ltime > DAY) {
                    layoutSecond.setVisibility(View.GONE);
                    layoutDay.setVisibility(View.VISIBLE);
                } else {
                    layoutDay.setVisibility(View.GONE);
                    layoutSecond.setVisibility(View.VISIBLE);
                }
            } else {

            }

            setDay(days_decade, days_unit);

            hour_decade = hour / 10;
            hour_unit = hour % 10;
            setHour(hour_decade, hour_unit);

            min_decade = minute / 10;
            min_unit = minute % 10;
            setMinu(min_decade, min_unit);

            sec_decade = second / 10;
            sec_unit = second % 10;
            setSecond(sec_decade, sec_unit);


        }

        @Override
        public void onFinish() {
            mc.cancel();
            if (listener != null) {
                listener.stop();
            }
        }
    }


    private boolean isMerge() {
        return model.model == Model.Merge.model;
    }

    private void setDay(int decade, int unit) {
        if (isMerge()) {
            tvDayUnit.setText(decade + "" + unit);
        } else {
            tvDayUnit.setText(unit + "");
            tvDayDecade.setText(decade + "");
        }
    }

    private void setHour(int decade, int unit) {
        if (isMerge()) {
            tvHourUnit.setText(decade + "" + unit);
        } else {
            tvHourUnit.setText(unit + "");
            tvHourDecade.setText(decade + "");
        }
    }

    private void setMinu(int decade, int unit) {
        if (isMerge()) {
            tvMinuUnit.setText(decade + "" + unit);
        } else {
            tvMinuUnit.setText(unit + "");
            tvMinuDecade.setText(decade + "");
        }
    }

    private void setSecond(int decade, int unit) {
        if (isMerge()) {
            tvSecondUnit.setText(decade + "" + unit);
        } else {
            tvSecondUnit.setText(unit + "");
            tvSecondDecade.setText(decade + "");
        }
    }

}
