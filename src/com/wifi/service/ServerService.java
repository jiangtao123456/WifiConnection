package com.wifi.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Calendar;

import com.example.andriodmvc.R;
import com.wifi.activity.ChatActivity;
import com.wifi.entity.ChatMsgEntity;
import com.wifi.util.ChatMsgViewAdapter;
import com.wifi.util.HandleUtil;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

/**
 * author:_phy
 */

public class ServerService extends IntentService{
	
	private static final String TAG = ChatActivity.class.getSimpleName();;
	int port = 0;
	ServerSocket serversocket = null;
	String rec = "";
	Activity chat = null;
	//HandleUtil handleUtil;
	
	public ServerService() {
		super("ServerService");
	}


	@Override
	protected void onHandleIntent(Intent intent) {
		port = ((Integer) intent.getExtras().get("port")).intValue();
		//handleUtil = (HandleUtil)intent.getSerializableExtra("Handler");
		intent = new Intent("com.wifi.broadcast"); 
		
		int len = 0;
		OutputStream outputstream = null;
		InputStream inputstream = null;
		byte[] rece = new byte[1000];
		String reces = null;	
		Log.v(TAG, "Server������");
		try {
			serversocket = new ServerSocket(port);
			Socket socket = serversocket.accept();
			inputstream = socket.getInputStream();
			outputstream = socket.getOutputStream();
//            String name = getName();
//            String date = getDate();
//            int RId = R.layout.list_say_me_item;
            
			while(true){
			len = inputstream.read(rece);// ���ܿͻ�����Ϣ
			if (len != 0) {
				//reces = new String(rece, 0, len);
				//rec = reces;
                //������������Ϣ����������
				Log.v(TAG, "Service����������");
				//handleUtil.getHd().sendEmptyMessage(1);
				intent.putExtra("Update", "update");
				sendBroadcast(intent);
				outputstream.write("close".getBytes());
				break;
			}
		}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
    // shuold be redefine in the future
    private String getName() {
        return getResources().getString(R.string.myDisplayName);
    }

    // shuold be redefine in the future
    private String getDate() {
        Calendar c = Calendar.getInstance();
        String date = String.valueOf(c.get(Calendar.YEAR)) + "-"
                + String.valueOf(c.get(Calendar.MONTH)) + "-" + String.valueOf(c.get(Calendar.DAY_OF_MONTH));
        return date;
    }

}
