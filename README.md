# CountDownView -- 启动页倒计时

 - gradle

```
compile 'cn.lemon:countdownview:0.2.0'
```

## 使用
```xml
<com.bsnl.base.widget.countdowntv.CountDownTextView
        android:id="@+id/tv_countdown"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_centerInParent="true"
        bsnl:box_height="40dp"
        bsnl:model="solo"
        bsnl:show="three"
        bsnl:box_width="25dp"
        bsnl:unit_text_Size="14sp">

    </com.bsnl.base.widget.countdowntv.CountDownTextView>
```

```java

        CountDownTextView textView = findViewById(R.id.tv_countdown);
        textView.setCownDownListener(new CountDownTextView.stopListener() {
            @Override
            public void stop() {

            }
        });
        textView.startTimer(1000 * 60 * 60 * 24 *23);
```

## demo

<image src="demo.jpg" width="320" heigh="564"/>