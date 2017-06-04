package com.karntrehan.posts.utils;

import com.karntrehan.posts.list.entity.Post;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * Created by karn on 04-06-2017.
 */

public class TestUtil {

    public static void print(String print) {
        System.out.println(print);
    }


    public static Response<List<Post>> createErrorResponse(int responseCode) {
        return createErrorResponse(responseCode, ResponseBody.create(
                MediaType.parse("application/json"),
                "{}"
        ));
    }

    static Response<List<Post>> createErrorResponse(int responseCode, ResponseBody responseBody) {
        return Response.error(responseCode, responseBody);
    }
}
