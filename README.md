

Most Used Tools (MUT) is a project have most used function , classes and controls in android collected in one project 


This project have 3 parts

let's see What's the job of every part ?

[First]

(Android libarary and applicatoin) 
{
It have classes that do this jobs
   
RouteInformations (Help you to get distance , time , draw line between two locations)
   
WebServiceHelper(Help you to send and get json , upload files with just 5 or 6 lines of codes i created both parts php and android)
   
Check (Check for many things with just one line like mail, url , numbers , bettery level etc...)
   
FileOperations (Have functions to select files , images , convert form filepath to string etc....)

MUT(Have all needed functions show toast , show dialog , open setteings etc... )

StartActivities(Have some animations  to start activities right to left , up to down and more .....)

SlideMenueandTabs(Have most used used things in Menue and tabs )

ListView(Like , click on element , delete element and more....)

DownloadImage(download and cachec images .... )

}

[Second]
(Windows applicaton program)
{
  This application have some tools that help you when you create android apps
}

[Third]
(PHPPart)
{
  It have some functions that help you when you create web service 
}


More details about every thing

First MUT is a Libarary do some functions we almost use it in all projects it have so important methos and classes 

(1)Check Class
It have manything we used to check this time we just write Check. and you will find many classes that check your data
it return true of false 

Example 
    Check.isInternetAvailable(); 
    Check.deviceHasGoogleAccount(context);
    Check.isUrl(str)
    Check.isNumeric(str)
    Check.isPhone(str)
    Check.isMail(str)
Check.batteryLevel(context) // it return integer have the level of the pattery  %

______________________________________________________________________________________________________________________________________________________________________

(2)RouteInformations Class
 
It's a class that used to get distance and time between 2 locations not only you can also draw line on map (draw line like you see in google map not just connect the 
2 locations)  

Example  

                RouteInformations rInformation = new RouteInformations(new AsyncResponse() {
					
					@Override
					public void processFinish(RouteDetails arg0) {
						// TODO Auto-generated method stub
						try
						{
						  map.addPolyline(arg0.getLineOptions()); //you can add the return line and add it to the map 
						  
						  //here you can get distance , duration it will return like you drive a car 
						  MUT.fastDialog(Map.this,"Time and Distance","Distance : "+arg0.getDistance()+"\nDuration : "+arg0.getDuration());
					  }
						catch(Exception e)
						{
							MUT.lToast(Map.this,"Can't draw line Try Again");
						}
						}
				});
        		 
        		//you should pass the 2 lat and lang which you want to draw a aline or get distance or duration between them 
        		 RouteDetails routeDetails=new RouteDetails();
         		routeDetails.setLatLong1(from.getPosition());
         		routeDetails.setLatLong2(to.getPosition());
        		rInformation.execute(routeDetails);

______________________________________________________________________________________________________________________________________________________________________


(3)WebServiceHelper Class

For sure we use webServices so much this time you can use it easily just 5-6 lines not only i created the other part using php in php you just need to write 3 or 4 lines of code

Example

1-Andriod Part

WebServiceHelper >> it's a class that takes a url and json with data which you want to send to the webServices it works in background 

WebServiceHelper.defaultUrl="http://192.168.1.6/go.php";

//first add data to json (you can also add images or files until 30 megabytes ) just convert it to string and i also created the method that will do this job

     JSONObject json=new JSONObject();
		try {
			json.put("method","insert");
			json.put("mail",""+mail.getText());
			json.put("password",""+password.getText());
			} catch (JSONException e) {
			e.printStackTrace();
		}
              WebServiceHelper wbh=new WebServiceHelper(new ServieceAsyncResponse() {
			@Override
			public void processFinish(WebServiceResult output) 
			{
                              //what will you do after the request finish ?!
                              //output is an object that have this methods
				
                                output.getJson(); // it return a json array if only webservice return correct json data
				output.getJsonString();// it return a string that ready to use with jsonarray 
				output.getString();//it return the data which will print from the webservice
				output.getConnectionResult();.
	  		}
	        	});
		
	  	wbh.execute(json); // here start execute

                                                  ============================================================================

2-Php Part
It have many functions that will help you just 
include 'MUT.php';
and update the connection configurations in MUT file 

then you can use this functions 

a-insert("tablename","method"); // this function takes 2 arguments first one is table name , second one the name of columns that you no want to insert it but something
should be the same the name of json keys should be like database columns names only 1 line every thing will be inserted into the database :) it returns the number of effected raws or -1 if you insert wrong data 

b-select("tablename","mail,password"); // it takes 1 or 2 or 3 arguments first one is table name , then where columns (where coulmns which mean that the data of the key 
of sent data is equal to some data in your database ) , then where not coulmns it's the opposite of where it returns a json array with return data you can write ""
instead of any parameter to ignore it or you can leave it for like the example ("tablename","mail,password"); here you will ignore where not if you want to ignore
where and use where not write that ("tablename","","mail,password"); if you want to select all just write table name ("tablename");

c-update("testtable","name","mail,password");//it takes 2 or 3 or 4 paramaters first one is table name then columns names which you want to update , third one is where columns  finally where not columns sure now you can understand it it returns number of effected raws or -1 if you add wrong data or wrong parameters

d-delete ("testtable","name,password","mail"); it like select but it returns number of effected raws or -1 if you add wrong data or wrong parameters

Other Important php functions 

1-send_android($notification_ids,$message) //it used to send notifications to android devices it takes 2 arguments first one array of notification ids and will send //to all , second one associative array of data you want to send  (Don't forget to change the key)

2-send_ios($device_token,$alert,$message,$passphrase); //it sends notification to IOS mobiles

3-download_file($base,$filename); //first parameter is the string of the file which you will send in json parameter , second parameter is filename
this function will download the file in the server with new name  

and more methods........

______________________________________________________________________________________________________________________________________________________________________

(4)FileOperations Class

It have functions that help you to work with files
1-selectImage(context) // it takes one parameter context it will show 2 chooses camera or gallery then the result will back in onactivityresult
2-selectFile(context,type) // it takes two parameter context and type you can pass type like you want image,audio,video etc.... or * for all kind of files
3-fileToString(selectedPath) // it takes one parameter the path of the file it returns a string after convert the file to string
4-getFileAsString(context, uri) // it takes two parameter context and uri  it returns a string after convert the file to string

______________________________________________________________________________________________________________________________________________________________________

(5)MUT Class
It have functions that we use so much like 
a-Toast functions
MUT.lToast(context,text); //It show long toast it easier than Toast.makeText(context,text,Toast.LENGTH_LONG).show();
MUT.sToast(context,text); //It show short toast
MUT.dToast(context,text,duration); //It show toast with duration

b-fastDialog(activity,title,message); //It show message dialog with title and message

c-settingDialog(activity,action,title,message); //it take activity or context , action , title and message and it show dialog with yes or no to open the settings depend on the action
______________________________________________________________________________________________________________________________________________________________________


I also create a big program which use most needed things and i almost used all function of MUT libarary 

this project have 
1-Activities >> Many ways to open activity left to right , up to down , diagonal , and no animation 

                                                  ============================================================================

2-SlideMenue and Tabs >> every thing handle back in fragments , move between 2 maps without craches , move between tabs which have map without craches (this problems many people face it) and custom tabs
                                                  ============================================================================

3-ListView >>  clickable button on item , delete button and custom data on listview you can use the same things with gridview 

                                                  ============================================================================

4-Notifications >> almost every thing about notification (Notification with every thing contant , title , bigtext , icon and more) , (Notification with big Image) and (Notification with buttons)

                                                  ============================================================================

5-Maps >> Animate Camera , add marker , dragmarker , click on marker , custom marker , click on map , click on get location button , draw line , get time , get distance and more about maps 

                                                  ============================================================================
6-Download images >> it's not just download the image it also can cache it just some small codes i used the best of the best libarary ImageLoader libarary it can
do this thing easily

                                                  ============================================================================
7-Animations >> some known animations i got so nice example from the internet and used it 

                                                  ============================================================================
Finally 2 windows applications
1-Android programming helper 
first function webservice >> it used to generate android code and sql queries to save your time just you will select some spinners and buttons
second function GetColor >> it have all colors just choose color you like and it will print an xml code of this color 
third function Multilanguage >>  you will write the word and it's translate and it will generate code for both language in android
finally >> so important sites like
get all icons for free
get icons with all sizes for android
design in photoshop then get xml code
almost all known libararies for android 
and more sites 

2-Program solve some of most known problems like adb is using , avd ... , etc... just run the program but first close all the programs on your pc 
                                               
                                                               Note
                                                               In code
                          //You will find the permssion need for every function to go wrote in comment near it 
                         //You will find the sites that helped me to create it for honesty 
