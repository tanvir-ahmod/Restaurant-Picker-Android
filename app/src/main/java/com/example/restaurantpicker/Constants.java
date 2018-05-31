package com.example.restaurantpicker;

/**
 * Created by Shoukhin on 5/27/2018.
 */

public class Constants {

    public static final String REQUEST_TAG = "com.example.restaurantpicker";
    public static final String LOGTAG = "logtag";

    private static final String BASE_URL = "http://192.168.161.1/Restaurant%20Picker/";

    //To get restaurant info
    public static final String USER_REGISTRATION_URL = "user_registration.php";
    public static final String USER_LOGIN_URL = BASE_URL + "user_login.php";
    public static final String GET_RESTAURANT = BASE_URL + "restaurant_info_api.php";
    public static final String RESTAURANT_IMAGE_URL = BASE_URL;

    //to get item of a restaurant
    public static final String RESTAURANT_ID = "restaurantid";
    public static final String RESTAURANT_ITEM_URL = BASE_URL + "show_restaurant_product_api.php";


}
