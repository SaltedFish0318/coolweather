package com.zero.coolweather.util;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.zero.coolweather.db.City;
import com.zero.coolweather.db.Country;
import com.zero.coolweather.db.Province;
import com.zero.coolweather.gson.Weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 86738 on 2019/1/19.
 */

public class Utility {

    /**
     * 解析和处理服务器返回的省级数据
     */
    public static boolean handleProvinceResponce(String responce){
        if (!TextUtils.isEmpty(responce)){
            try{
                JSONArray allProvince = new JSONArray(responce);
                for(int i = 0; i < allProvince.length(); i++){
                    JSONObject provinceObject = allProvince.getJSONObject(i);
                    Province province = new Province();
                    province.setProvinceName(provinceObject.getString("name"));
                    province.setProvinceCode(provinceObject.getInt("id"));
                    province.save();
                }
                return true;
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 解析和处理服务器返回的市级数据
     */
    public static boolean handleCityResponce(String responce, int provinceId){
        if(!TextUtils.isEmpty(responce)){
            try{
                JSONArray allCity = new JSONArray(responce);
                for (int i = 0; i < allCity.length(); i++){
                    JSONObject cityObject = allCity.getJSONObject(i);
                    City city = new City();
                    city.setCityName(cityObject.getString("name"));
                    city.setCityCode(cityObject.getInt("id"));
                    city.setProvinceId(provinceId);
                    city.save();
                }
                return true;
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 解析和处理服务器返回的市级数据
     */
    public static boolean handleCountryResponce(String responce, int cityId){
        if(!TextUtils.isEmpty(responce)){
            try {
                JSONArray allCountry = new JSONArray(responce);
                for(int i = 0; i < allCountry.length(); i++){
                    JSONObject countryObject = allCountry.getJSONObject(i);
                    Country country = new Country();
                    country.setCountryName(countryObject.getString("name"));
                    country.setWeatherId(countryObject.getString("weather_id"));
                    country.setCityId(cityId);
                    country.save();
                }
                return true;
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
        return false;
    }


    /**
     * 将返回的JSON数据解析成Weather.AQIInfo实体类
     */
    public static Weather.AQIInfo handleAQIInfoResonse(String response){
        try{
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("HeWeather6");
            String weatherContent = jsonArray.getJSONObject(0).toString();
            return new Gson().fromJson(weatherContent,Weather.AQIInfo.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将返回的JSON数据解析成Weather.WeatherInfo实体类
     */
    public static Weather.WeatherInfo handleWeatherInfoResonse(String response){
        try{
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("HeWeather6");
            String weatherContent = jsonArray.getJSONObject(0).toString();
            return new Gson().fromJson(weatherContent,Weather.WeatherInfo.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}