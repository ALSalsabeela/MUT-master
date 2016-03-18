package com.example.mosusedtools;

import java.io.File;
import org.json.JSONException;
import org.json.JSONObject;
import android.view.View.OnLongClickListener;
import com.example.androidprogramminghelper.FileOperations;
import com.example.androidprogramminghelper.MUT;
import com.example.androidprogramminghelper.WebServiceHelper;
import com.example.androidprogramminghelper.WebServiceHelper.ServieceAsyncResponse;
import com.example.androidprogramminghelper.WebServiceResult;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


@SuppressLint("InflateParams")
public class ConnectToWebServices extends Activity {
	FileOperations file;
	EditText mail,password,name;
	String fileres="",imgres="";
	Boolean isImg=false;
	Button showImg,showFile;
	ProgressDialog pDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_connect_to_web_services);
		WebServiceHelper.defaultUrl="http://192.168.1.6/go.php";
		file=new FileOperations();
		mail=(EditText)findViewById(R.id.mail);
		password=(EditText)findViewById(R.id.password);
		name=(EditText)findViewById(R.id.name);
		showImg =(Button)findViewById(R.id.selectimage);
		pDialog=new ProgressDialog(this,ProgressDialog.STYLE_SPINNER);
		pDialog.setMessage("Loading");
		pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pDialog.setIndeterminate(false);
		pDialog.setCancelable(false);
		
		showImg.setOnLongClickListener(new OnLongClickListener() {
			
			
			
			 
			@Override
			public boolean onLongClick(View v) {
				 View view = LayoutInflater.from(ConnectToWebServices.this).inflate(R.layout.show_image, null);
				 File imgFile = new  File(imgres);

				 if(imgFile.exists()&&isImg){
				     Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

				     ImageView myImage = (ImageView)view.findViewById(R.id.image);

				     myImage.setImageBitmap(myBitmap);

				 
			      AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(new ContextThemeWrapper(ConnectToWebServices.this, R.style.AppTheme));
			        
			        alertDialogBuilder
			                .setCancelable(false)
			                .setView(view)
			                .setNegativeButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
			                    public void onClick(DialogInterface dialog, int id) {

			                    }
			                });

			        AlertDialog alertDialog = alertDialogBuilder.create();
			        alertDialog.show();
			        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
			   	 Button bq = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);  
				  bq.setBackgroundResource(R.drawable.button_design);
				  
				 }
				 else
				 {
					 MUT.lToast(ConnectToWebServices.this,"No image Selected or path not working");
				 }
				return false;
			}
		});
		showFile=(Button)findViewById(R.id.selectfile);
		
		showFile.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				MUT.lToast(ConnectToWebServices.this,fileres);
				return false;
			}
		});
	}
	
	public void insert(View view) 
	{
		pDialog.show();
		WebServiceHelper wbh=new WebServiceHelper(new ServieceAsyncResponse() {
			@Override
			public void processFinish(WebServiceResult output) 
			{
				MUT.lToast(ConnectToWebServices.this,output.getString());
				pDialog.dismiss();
			}
		});
		JSONObject json=new JSONObject();
		try {
			json.put("method","insert");
			json.put("mail",""+mail.getText());
			json.put("password",""+password.getText());
			} catch (JSONException e) {
			e.printStackTrace();
		}
		wbh.execute(json);
	
	}
	
	public void select(View view)
	{
		pDialog.show();
		WebServiceHelper wbh=new WebServiceHelper(new ServieceAsyncResponse() {
			@Override
			public void processFinish(WebServiceResult output) 
			{
				pDialog.dismiss();
				try {
					
					JSONObject json=output.getJson().getJSONObject(0);
					MUT.lToast(ConnectToWebServices.this,"Welcome to You "+json.getString("name"));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		JSONObject json=new JSONObject();
		try {
			json.put("method","select");
			json.put("mail",""+mail.getText());
			json.put("password",""+password.getText());
			} catch (JSONException e) {
			e.printStackTrace();
		}
		wbh.execute(json);
	}
	
	public void selectImg(View view)
	{
		isImg=true;
		file.selectImage(this);
	}
	
	public void update(View view)
	{
		pDialog.show();
		
		WebServiceHelper wbh=new WebServiceHelper(new ServieceAsyncResponse() {
			@Override
			public void processFinish(WebServiceResult output) 
			{
				pDialog.dismiss();
             MUT.lToast(ConnectToWebServices.this,output.getString());
			}
		});
		JSONObject json=new JSONObject();
		try {
			json.put("method","update");
			json.put("mail",""+mail.getText());
			json.put("password",""+password.getText());
			json.put("name",""+name.getText());
			} catch (JSONException e) {
			e.printStackTrace();
		}
		wbh.execute(json);
	}
	
	public void delete(View view)
	{
		pDialog.show();
		WebServiceHelper wbh=new WebServiceHelper(new ServieceAsyncResponse() {
			@Override
			public void processFinish(WebServiceResult output) 
			{
				pDialog.dismiss();
					MUT.lToast(ConnectToWebServices.this,output.getString());
			}
		});
		JSONObject json=new JSONObject();
		try {
			json.put("method","delete");
			json.put("mail",""+mail.getText());
			json.put("password",""+password.getText());
			} catch (JSONException e) {
			e.printStackTrace();
		}
		wbh.execute(json);
	}
	
	
	
	public void selectFile(View view)
	{
		isImg=false;			
		file.selectFile(this,"*");
		
 	}
	
	public void uploadFile(View view)
	{
		if(fileres.equals("")&&imgres.equals("")){MUT.lToast(ConnectToWebServices.this,"Select image or file please"); return;}
		pDialog.show();
		WebServiceHelper wbh=new WebServiceHelper(new ServieceAsyncResponse()
		{
			@Override
			public void processFinish(WebServiceResult output) 
			{
				pDialog.dismiss();
					MUT.lToast(ConnectToWebServices.this,output.getString());
			}
		});
		JSONObject json=new JSONObject();
		try {
			json.put("method","upload_file");
			try 
			{
				json.put("file",file.fileToString(fileres));
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			MUT.lToast(this,fileres);
			json.put("file_name",fileres.substring(fileres.lastIndexOf("/")+1));
			} catch (JSONException e) {
			e.printStackTrace();
		}
		wbh.execute(json);
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		try
		{
		fileres=file.getPath(this,data.getData());
		imgres=file.getPath(this,data.getData());
		}
		catch(Exception e)
		{}
		super.onActivityResult(requestCode, resultCode, data);
	}
}
