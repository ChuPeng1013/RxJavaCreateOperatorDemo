# RxJavaCreateOperatorDemo
## RxJava中部分创建类操作符，包括create()、just()、from()、defer()、interval()、timer()、range()、repeat()
### (1)Create()
create()操作符是所有创建型操作符的“根”，也就是说其他创建型操作符最后都是通过create操作符来创建Observable的，它的作用是从头开始创建一个Observable对象，默认不在任何调度器上运行。<br>
根据官方给出的原理图：<br>
![](https://github.com/ChuPeng1013/RxJavaCreateOperatorDemo/raw/master/material/create.png)<br>
现在要使用create()创建一个Observable对象<br>
```Java
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
```
当点击按钮后在控制台上，可以看到这样的结果：<br>
![](https://github.com/ChuPeng1013/RxJavaCreateOperatorDemo/raw/master/material/createResult.png)<br>
### (2)From
from()操作符是把其他类型的对象和数据类型转化成Observable，在RxJava中，from()操作符可以转换Future、Iterator和数组，产生的Observable会发射Iterator或数组的每一项数据。from()默认不在任何特定的调度器上执行。然而你可以将Scheduler作为可选的第二个参数传递给Observable，它会在那个调度器上管理这个Future。<br>
根据官方给出的原理图：<br>
![](https://github.com/ChuPeng1013/RxJavaCreateOperatorDemo/raw/master/material/from.png)<br>
现在要使用create()创建一个Observable对象<br>
```Java
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
```
当点击按钮后在控制台上，可以看到这样的结果：A B C D E<br>
### (3)Just
just()操作符也是把其他类型的对象和数据类型转化成Observable，只是简单的将数据原样发射，原来是什么类型就发射什么类型，顺序就是按照参数列表的顺序进行发射，它和from操作符很像，只是方法的参数有所差别。<br>
根据官方给出的原理图：<br>
![](https://github.com/ChuPeng1013/RxJavaCreateOperatorDemo/raw/master/material/just.png)<br>
现在要使用just()创建一个Observable对象<br>
```Java
Observable.just("A", "B", "C", "D", "E")  
        .subscribe(new Action1<String>()  
        {  
            public void call(String s)  
            {  
                Log.d("Just", s);      
            }  
        });
```
当点击按钮后在控制台上，可以看到这样的结果：A B C D E<br>
### (4)Defer()
defer操作符是直到有订阅者订阅时，才通过Observable的工厂方法创建Observable并执行，defer操作符能够保证Observable的状态是最新的。<br>
根据官方给出的原理图：<br>
![](https://github.com/ChuPeng1013/RxJavaCreateOperatorDemo/raw/master/material/defer.png)<br>
defer()与just()不同点在于，just()是在创建Observable就进行了赋值操作，而defer()是在订阅者订阅时才创建Observable，此时才进行真正的赋值操作。<br>
### (5)Interval()
interval()操作符返回一个Observable，它按固定的时间间隔发射一个无限递增的长整型的整数序列，此操作符接收两个参数，第一个参数表示间隔的时间，第二个参数表示间隔的时间单位。<br>
根据官方给出的原理图：<br>
![](https://github.com/ChuPeng1013/RxJavaCreateOperatorDemo/raw/master/material/interval.png)<br>
现在要使用interval()创建一个Observable对象<br>
```Java
Observable.interval(1, TimeUnit.SECONDS)  
        .subscribe(new Action1<Long>()  
        {  
            public void call(Long aLong)  
            {  
                Log.d("interval", aLong.toString());  
            }  
        });
```
以上代码是以秒为单位，每过1秒发射一个数据
### (6)Range
range()操作符创建一个发射指定范围的整数序列的Observable，你可以指定范围的起始和长度，此操作符接收两个参数，第一个参数表示范围的起始值，第二个参数表示数据的数目，如果将第二个参数设置为0将导致Observable不发射任何数据，如果将第二个参数设置为负数将导致Observable抛异常。<br>
根据官方给出的原理图：<br>
![](https://github.com/ChuPeng1013/RxJavaCreateOperatorDemo/raw/master/material/range.png)<br>
现在要使用range()创建一个Observable对象<br>
```Java
Observable.range(0, 10)  
        .subscribe(new Action1<Integer>()  
        {  
            public void call(Integer integer)  
            {  
                Log.d("range", integer.toString());  
            }  
        });
```
### (7)Repeat
repeat()操作符用于设置成为重复地发射数据，某些实现允许你重复的发射某个数据序列，还有一些允许你限制重复的次数。要注意的是这个操作符并不能创建一个Observable，而是将一个已经创建好的Observable设置成为重复发射的Observable数据序列，通过参数指定要重复的次数，如果不指定参数则是设置为无限发射。<br>
根据官方给出的原理图：<br>
![](https://github.com/ChuPeng1013/RxJavaCreateOperatorDemo/raw/master/material/repeat.png)<br>
现在要使用range()设置一个重复发射的Observable对象<br>
```Java
Observable.just("1", "2", "3")  
        .repeat(2)  
        .subscribe(new Action1<String>()  
        {  
            public void call(String s)  
            {  
                Log.d("repeat", s);  
            }  
        });
```
当点击按钮后在控制台上，可以看到这样的结果：1 2 3 1 2 3<br>
### (8)Timer
timer()操作符创建一个在给定的时间段之后返回一个特殊的Observable ，这个特殊的Observable将在延迟一段时间后发射一个简单的数字0。<br>     
根据官方给出的原理图：<br>
![](https://github.com/ChuPeng1013/RxJavaCreateOperatorDemo/raw/master/material/timer.png)<br>
现在要使用timer()创建一个延迟发射的Observable对象<br>
```Java
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
```
当点击按钮后在控制台上，可以看到这样的结果：
![](https://github.com/ChuPeng1013/RxJavaCreateOperatorDemo/raw/master/material/timerResult.png)<br>
[想了解更多关于Android开发的内容欢迎进入我的CSDN博客](http://blog.csdn.net/zackchu)
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
