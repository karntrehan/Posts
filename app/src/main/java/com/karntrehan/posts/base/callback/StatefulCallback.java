package com.karntrehan.posts.base.callback;

import retrofit2.Response;

/**
 * Created by karn on 03-06-2017.
 */

public interface StatefulCallback<T> {
    void onSuccessLocal(T response);

    void onSuccessSync(T response);

    void onValidationError(Response response);

    void onFailure(Throwable throwable);
}
