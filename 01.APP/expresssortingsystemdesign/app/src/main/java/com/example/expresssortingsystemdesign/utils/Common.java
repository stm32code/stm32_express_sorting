package com.example.expresssortingsystemdesign.utils;

import com.itfitness.mqttlibrary.MQTTHelper;

public class Common {
    //    public static String Port = "6002";
//    public static String Sever = "tcp://183.230.40.39" + ":" + Port;
    public static String Port = "1883";
    public static String Sever = "tcp://iot-06z00axdhgfk24n.mqtt.iothub.aliyuncs.com" + ":" + Port;

    public static String ReceiveTopic = "/broadcast/h9sj8FzJ0gy/test2";
    public static String PushTopic = "/broadcast/h9sj8FzJ0gy/test1";
    public static String DriveID = "h9sj8FzJ0gy.smartapp|securemode=2,signmethod=hmacsha256,timestamp=1715129898507|";
    public static String DriveName = "smartapp&h9sj8FzJ0gy";
    public static String DrivePassword = "2e93189335e194b434aaaf156045c627d2702c7eb09c62b7f58f07706a4a8ea4";
    public static String Drive2ID = "1181073144";
    public static String api_key = "l=yQf7V3jwBh10rp7rjt=5GUQfU=";
    public static boolean DeviceOnline = false;
    public static String LatestOnlineDate = "离线";
    public static MQTTHelper mqttHelper = null;
}
