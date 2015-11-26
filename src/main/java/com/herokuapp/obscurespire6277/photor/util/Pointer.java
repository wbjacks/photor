package com.herokuapp.obscurespire6277.photor.util;

public class Pointer<T> {

    private T _delegate;

    public Pointer(T _delegate) {
        this._delegate = _delegate;
    }

    public static <P> Pointer<P> pointer() {
        return new Pointer<>(null);
    }

    public static <P> Pointer<P> pointer(P initialValue) {
        return new Pointer<>(initialValue);
    }

    public T get() {
        return _delegate;
    }

    public void set(T newDelegate) {
        _delegate = newDelegate;
    }

}
