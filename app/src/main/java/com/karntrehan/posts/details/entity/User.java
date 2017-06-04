package com.karntrehan.posts.details.entity;

import com.google.gson.annotations.SerializedName;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteColumn;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteType;

/**
 * Created by karn on 03-06-2017.
 */

@StorIOSQLiteType(table = User.TABLE_NAME)
public class User {
    public static final String TABLE_NAME = "users";
/* {
    "id": 1,
    "name": "Leanne Graham",
    "username": "Bret",
    "email": "Sincere@april.biz",
                        //IGNORED
                        "address": {
                          "street": "Kulas Light",
                          "suite": "Apt. 556",
                          "city": "Gwenborough",
                          "zipcode": "92998-3874",
                          "geo": {
                            "lat": "-37.3159",
                            "lng": "81.1496"
                          }
                        },
                        "phone": "1-770-736-8031 x56442",
                        "website": "hildegard.org",
                        "company": {
                          "name": "Romaguera-Crona",
                          "catchPhrase": "Multi-layered client-server neural-net",
                          "bs": "harness real-time e-markets"
    }
  }*/

    @StorIOSQLiteColumn(name = "user_id", key = true)
    @SerializedName("id")
    int userId;

    @StorIOSQLiteColumn(name = "name")
    @SerializedName("name")
    String name;

    @StorIOSQLiteColumn(name = "username")
    @SerializedName("username")
    String username;

    @StorIOSQLiteColumn(name = "email")
    @SerializedName("email")
    String email;

    public User() {
    }

    public static String createTableQuery() {
        return "CREATE TABLE " + User.TABLE_NAME + " ("
                + "user_id INTEGER NOT NULL PRIMARY KEY, "
                + " name TEXT NOT NULL, "
                + " username TEXT NOT NULL,"
                + " email TEXT NOT NULL "
                + ");";
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
