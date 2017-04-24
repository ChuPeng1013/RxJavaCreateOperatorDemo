package com.example.chupeng.rxjavacreateoperatordemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

public class MainActivity extends AppCompatActivity
{
    private Button createButton;
    private Button justButton;
    private Button fromButton;
    private Button intervalButton;
    private Button timerButton;
    private Button rangeButton;
    private Button repeatButton;
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createButton = (Button) findViewById(R.id.createButton);
        justButton = (Button) findViewById(R.id.justButton);
        fromButton = (Button) findViewById(R.id.fromButton);
        intervalButton = (Button) findViewById(R.id.intervalButton);
        timerButton = (Button) findViewById(R.id.timerButton);
        rangeButton = (Button) findViewById(R.id.rangeButton);
        repeatButton = (Button) findViewById(R.id.repeatButton);

        createButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                Observable.create(new Observable.OnSubscribe<Integer>()
                {
                    public void call(Subscriber<? super Integer> subscriber)
                    {
                        try
                        {
                            if(!subscriber.isUnsubscribed())
                            {
                                for(int i = 0; i < 5; i++)
                                {
                                    subscriber.onNext(i);
                                }
                                subscriber.onCompleted();
                            }
                        }
                        catch (Exception e)
                        {
                            subscriber.onError(e);
                        }
                    }})
                        .subscribe(new Subscriber<Integer>()
                        {
                            public void onCompleted()
                            {
                                Log.d("Create", "onCompleted()");
                            }

                            public void onError(Throwable e)
                            {
                                e.printStackTrace();
                                Log.d("Create", "onError()");
                            }

                            public void onNext(Integer integer)
                            {
                                Log.d("Create", "onNext：" + integer);
                            }
                        });
            }
        });

        fromButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                List<String> list = new ArrayList<String>();
                list.add("A");
                list.add("B");
                list.add("C");
                list.add("D");
                list.add("E");
                Observable.from(list)
                        .subscribe(new Action1<String>()
                        {
                            public void call(String s)
                            {
                                Log.d("From", s);
                            }
                        });
            }
        });

        justButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                Observable.just("A", "B", "C", "D", "E")
                        .subscribe(new Action1<String>()
                        {
                            public void call(String s)
                            {
                                Log.d("Just", s);
                            }
                        });
            }
        });

        intervalButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                Observable.interval(1, TimeUnit.SECONDS)
                        .subscribe(new Action1<Long>()
                        {
                            public void call(Long aLong)
                            {
                                Log.d("interval", aLong.toString());
                            }
                        });
            }
        });

        rangeButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                Observable.range(0, 10)
                        .subscribe(new Action1<Integer>()
                        {
                            public void call(Integer integer)
                            {
                                Log.d("range", integer.toString());
                            }
                        });
            }
        });

        repeatButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                Observable.just("1", "2", "3")
                        .repeat(2)
                        .subscribe(new Action1<String>()
                        {
                            public void call(String s)
                            {
                                Log.d("repeat", s);
                            }
                        });
            }
        });

        timerButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                final SimpleDateFormat sdf = new SimpleDateFormat("HH:MM:SS");
                final String beginTime = sdf.format(new Date());
                Log.d("timer", "beginTime: " + beginTime);
                Observable.timer(2, TimeUnit.SECONDS)
                        .subscribe(new Action1<Long>()
                        {
                            public void call(Long aLong)
                            {
                                Log.d("timer", aLong.toString());
                                String endTime =  sdf.format(new Date());
                                Log.d("timer", "endTime: " + endTime);
                            }
                        });
            }
        });

    }
}
