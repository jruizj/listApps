package com.example.listapps;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {	
	
	String[] app_labels;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        getPackages();      

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, app_labels);
        ListView list = (ListView) findViewById(R.id.ListView01);
        try{
        	list.setAdapter(adapter);
        }catch(Exception e){
        	System.out.print(e.getMessage());
        }
        
    }
    
    private ArrayList<PackageInfoStruct> getPackages() {
    	ArrayList<PackageInfoStruct> res = new ArrayList<PackageInfoStruct>();
        ArrayList<PackageInfoStruct> apps = getInstalledApps(false, res);
        final int max = apps.size();
        System.out.println("Apps.size> " + apps.size());
        for (int i=0; i < max; i++) {
            apps.get(i);
        }
        return apps;
    }

    private ArrayList<PackageInfoStruct> getInstalledApps(boolean getSysPackages, ArrayList<PackageInfoStruct> res) {
    	
        List<PackageInfo> packs = getPackageManager().getInstalledPackages(0);        
		try{
            app_labels = new String[packs.size()];
        }catch(Exception e){
             Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
        
        
		for(int i=0;i < packs.size();i++) {
            PackageInfo p = packs.get(i);
            if ((!getSysPackages) && (p.versionName == null)) {
                continue ;
            }
            PackageInfoStruct newInfo = new PackageInfoStruct();
            newInfo.appname = p.applicationInfo.loadLabel(getPackageManager()).toString();
            newInfo.pname = p.packageName;
            newInfo.versionName = p.versionName;
            newInfo.versionCode = p.versionCode;
            newInfo.icon = p.applicationInfo.loadIcon(getPackageManager());
            res.add(newInfo);

            app_labels[i] = newInfo.appname;
        }
        return res;
    }
 }  


class PackageInfoStruct {             
    String appname = "";           
    String pname = "";             
    String versionName = "";      
    int versionCode = 0;           
    Drawable icon;                 
}