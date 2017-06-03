package com.karntrehan.posts.base.callback;

import retrofit2.Response;

/**
 * Created by karn on 03-06-2017.
 */

public interface ValidationCallback<T> {
    void onSuccess(T response);

    void onValidationError(Response response);

    void onFailure(Throwable throwable);
}
