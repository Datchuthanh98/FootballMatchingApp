package com.example.myclub.Interface;

public interface LoadCallBack<T1, T2> {
    void onSuccess(T1 t1);
    void onFailure(T2 t2);
}
