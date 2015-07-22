# Homework_L19
<title>Hello. Its homework lesson 19 project working with Google Cloud Messeges.</title>
<br>This application receive your push messages and store it to list view.</br>
![alt tag](https://raw.github.com/RomanSaldan/Homework_L19/master/app/src/main/res/drawable/background_image.png)
<br><b>API:</b></br>
<p>
1. To display stored notification click on it in listview of notifications.</br>
2. If you want to delete some of stored notifications you can do it by getting context menu on this notification in list and select <b>"Delete"</b> option.</br>
3. You can also delete all of stored notifications by getting context menu on any of stored notifications and select <b>"Delete all"</b> option.</br>
4. For send push notification use next JSON code snippet</br>
<pre>
{
    "registration_ids" : ["<i>YOUR_DEVICE_KEY</i>"],
    "data" : {
        "message" : "<i>notification_message</i>",
        "title" : "<i>notification_title</i>",
        "subtitle" : "<i>notification_subtitle</i>",
        "tickerText" : "<i>notification_ticker</i>",
        "sound" : "<i>on/off</i>",
        "vibration" : "<i>on/off</i>"
    }
}
</pre>
When you build push notification dont forget to set next options:</br>
"Authorozation" - "key=<b>AIzaSyDimJbJ2ZL3nOkoA13zuFgMpJ2NwtgGYVs</b>"<br>
"Content-Type"  - "application/json"</br>
<br>
You can download apk from DropBox: <a href="https://www.dropbox.com/s/aqpk78hnwjjhp3h/Homework_L19.apk?dl=0">download</a>
</p>
