package com.cloudwise.sdg.function;

import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
/**
 * Created by f1026 on 2019/5/14.
 */
public class CreateAddress {
    // 高德地图请求秘钥
    private static final String KEYLIST[] = {"73b2384003933715b5f82f6ad596c9e1","0624584145a7300d20658ed0136c0574","6dccb792030125506839084d3d043ccf",
            "e1232d327633b1eb8c30e3e2dd6e9bd7","da176d721803946e07a1836f52655c17","54469f1110f68e41c19f62ba404483ef","95d253f1924a063eb9acc0cf3dc1a6ef",
            "67ab0cc68238e2283161d9424ac1a55f","d87aa8f6ed20be6e40030e25dbdf1786","89f2b4f79ce1750628f6676ffe8000771"};

    private static int COUNT = 0;

    //private static String KEY = KEYLIST[COUNT];


    //返回值类型
    private static final String OUTPUT = "JSON";

    //根据高德经纬度获取地名Api
    private static final String GET_ADDRESS_URL = "https://restapi.amap.com/v3/geocode/regeo";

    //根据百度经纬度获取地名Api
    //private static final String GET_ADDRESS_URL = "http://api.map.baidu.com/geocoder/v2/";

    private static final double minLon = 118;

    private static final double maxLon = 123;

    private static final double minLat = 27.2161;

    private static final double maxLat = 31.5203;


    /**
     * 根据高德经纬度获取地址信息
     *
     //* @param gdLon 高德地图经度
     //* @param gdLat 高德地图纬度
     * @return
     */
    public static void main(String[] args){

        Map<String, String> addressMap = getAddressByLonLat();
        System.out.println(addressMap.get("address"));
        System.out.println(COUNT);



    }

    public static Map<String, String> getAddressByLonLat() {

        Map<String, String> lonLatMap = randomLonLat(minLon,maxLon,minLat,maxLat);

        Map<String,String> addressMap = new HashMap<>();


        //高德获取地址
        String location = lonLatMap.get("lon") + "," + lonLatMap.get("lat");
        String url, response;
        boolean keyStatus;
        do{
            url= GET_ADDRESS_URL +" ?output=" + OUTPUT + "&location=" + location + "&key=" + KEYLIST[COUNT] + "&extensions=base";
            response =  getResponse(url);
            keyStatus = checkKey(response);

        }while (!keyStatus);

        JSONObject responseJson=JSONObject.fromObject(response);
        String regeocode = responseJson.getString("regeocode");
        JSONObject regeocodeJson=JSONObject.fromObject(regeocode);
        String address = regeocodeJson.getString("formatted_address");
        String addressComponent = regeocodeJson.getString("addressComponent");
        JSONObject componentJson = JSONObject.fromObject(addressComponent);
        String province = componentJson.getString("province");
        String city = componentJson.getString("city");
        String district = componentJson.getString("district");
        String township = componentJson.getString("township");

        //判断地址各个参数是否为空
        if(address.equals("[]")) address = "";
        if(province.equals("[]")) province = "";
        if(city.equals("[]")) city = "";
        if(district.equals("[]")) district = "";
        if(township.equals("[]")) township = "";

        addressMap.put("address", address);
        addressMap.put("province", province);
        addressMap.put("city", city);
        addressMap.put("district", district);
        addressMap.put("township", township);

        JSONObject streetJson = JSONObject.fromObject(componentJson.getString("streetNumber"));
        String streetNumber = "";
        if(!streetJson.getString("street").equals("[]"))
            streetNumber = streetJson.getString("street")+ streetJson.getString("number");
        addressMap.put("streetNumber", streetNumber);
        //System.out.println(addressMap);

        return addressMap;

        //百度获取地址
//      String location = gdLat + "," + gdLon;
//      String url= GET_ADDRESS_URL+"?callback=renderReverse&location="+location+"&output=json&pois=1&latest_admin=1&ak=7efKVVGWFnEMADr1WkLujaaeazP5uiRG";
//      System.out.println(url);
//      String response =  getResponse(url);
//      String baiduJson = response.substring(response.indexOf("(")+1,response.lastIndexOf(")"));
//      System.out.println(baiduJson);
//      JSONObject jsonResult=JSONObject.fromObject(baiduJson);
//      String baiduResult = jsonResult.getString("result");
//      JSONObject resultJson = JSONObject.fromObject(baiduResult);
//      String address = resultJson.getString("formatted_address");
//      addressMap.put("address", address);
//      String addressComponent = resultJson.getString("addressComponent");
//      JSONObject componentJson = JSONObject.fromObject(addressComponent);
//      addressMap.put("province", componentJson.getString("province"));
//      addressMap.put("city", componentJson.getString("city"));
//      addressMap.put("district", componentJson.getString("district"));
//      addressMap.put("town", componentJson.getString("town"));

    }

    public static boolean checkKey(String response){
        boolean keyStatus = true;
        JSONObject responseJson = JSONObject.fromObject(response);
        String status = responseJson.getString("infocode");
        if(status.equals("10000")) {
            keyStatus = true;
        }
        else{
            keyStatus = false;
            COUNT = (COUNT + 1) % 10;
        }
        return keyStatus;

    }

    public static String getResponse(String serverUrl){
        //用JAVA发起http请求，并返回json格式的结果
        StringBuffer result = new StringBuffer();
        try {
            URL url = new URL(serverUrl);
            URLConnection conn = url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

            String line;
            while((line = in.readLine()) != null){
                result.append(line);
            }
            in.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result.toString();
    }

    /**
     * @Title: randomLonLat
     * @Description: 在矩形内随机生成经纬度
     * @param MinLon：最小经度  MaxLon： 最大经度   MinLat：最小纬度   MaxLat：最大纬度
     * @return
     * @throws
     */

    public static Map<String, String> randomLonLat(double MinLon, double MaxLon, double MinLat, double MaxLat) {
        Map<String, String> lonLatMap = new HashMap<String, String>();
        Random random = new Random();
        BigDecimal db = new BigDecimal(Math.random() * (MaxLon - MinLon) + MinLon);
        String lon = db.setScale(6, BigDecimal.ROUND_HALF_UP).toString();// 小数后6位
        lonLatMap.put("lon", lon);
        db = new BigDecimal(Math.random() * (MaxLat - MinLat) + MinLat);
        String lat = db.setScale(6, BigDecimal.ROUND_HALF_UP).toString();
        lonLatMap.put("lat", lat);
        return lonLatMap;
    }


}
