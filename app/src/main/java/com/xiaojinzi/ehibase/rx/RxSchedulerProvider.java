package com.xiaojinzi.ehibase.rx;

/**
 * time   : 2018/05/14
 *
 * @author : xiaojinzi 30212
 */
public class RxSchedulerProvider {

    /**
     * 这里先直接使用实现类的,当单元测试的时候,这个类再进行改写,所有Android 的切换线程都应该使用这个类来获取调度器
     * @return
     */
    public static BaseSchedulerProvider getInstance(){
        return AndroidSchedulerProvider.getInstance();
    }

}
