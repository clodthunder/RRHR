package com.lskj.gx.rrhr.RxBus;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * courtesy: https://gist.github.com/benjchristensen/04eef9ca0851f3a5d7bf
 * http://brucezz.itscoder.com/articles/2016/06/02/a-simple-rxbus-implementation/
 * <p>
 * rxbus 异常处理：http://www.jianshu.com/p/0493cc28a811 目前没有碰到
 * <p>
 * <p>
 * usage:
 * private CompositeSubscription compositeSubscription;
 * <p>
 * onCreate() add follow code:
 * //        compositeSubscription = new CompositeSubscription();
 * //        compositeSubscription.add((Subscription) RxBus.getRxInstance()
 * //                .toObserverable(MovieEvent.class)
 * //                .subscribe(new Action1<MovieEvent>() {
 * //                    @Override
 * //                    public void call(MovieEvent movieEvent) {
 * //                        for (SubjectsBean c : movieEvent.getSubjectsBeanList()) {
 * //                            System.out.println(c.getTitle());
 * //                        }
 * //                    }
 * //                }));
 * <p>
 * onDestroy() add follow code:
 * //        if (compositeSubscription != null && !compositeSubscription.isUnsubscribed()) {
 * //            compositeSubscription.unsubscribe();
 * //        }
 * }
 */
public class RxBus {
    private static RxBus instance;
    //private final PublishSubject<Object> _bus = PublishSubject.create();

    // If multiple threads are going to emit events to this
    // then it must be made thread-safe like this instead
    private final Subject<Object, Object> _bus;

    private RxBus() {
        _bus = new SerializedSubject<>(PublishSubject.create());
    }

    // 单例RxBus
    public static RxBus getRxInstance() {
        if (instance == null) {
            synchronized (RxBus.class) {
                if (instance == null) {
                    instance = new RxBus();
                }
            }
        }
        return instance;
    }

    /**
     * 发布一个 Event 对象给 bus，然后由 bus 转发给订阅者们
     *
     * @param o
     */
    public void post(Object o) {
        _bus.onNext(o);
    }

    /**
     * 获得一个包含目标事件的 Observable，订阅者对其订阅即可响应
     * bus.ofType() 等效于filter。。。。。。
     *
     * @param eventType
     * @param <T>
     * @return
     */
    public <T> Observable<T> toObserverable(final Class<T> eventType) {
        return _bus.ofType(eventType);
//        return _bus.filter(new Func1<Object, Boolean>() {
//            @Override
//            public Boolean call(Object o) {
//
//                return eventType.isInstance(o);
//            }
//        }).cast(eventType);
    }

    public boolean hasObservers() {
        return _bus.hasObservers();
    }
}