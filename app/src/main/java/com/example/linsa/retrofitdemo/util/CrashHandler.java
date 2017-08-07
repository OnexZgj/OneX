package com.example.linsa.retrofitdemo.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Process;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;
import java.util.TreeSet;

/**
 * Created by nike on 2016/5/31.
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler{

//	private static final String YYYYMMDDHHMMSS = "yyyy年MM月dd日HH时mm分ss秒";
	private static final String YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";
	private static final String YYYYMMDD = "yyyyMMdd";
	private static final String TAG = "CrashHandler";
    private static CrashHandler crashHandler;
    private Thread.UncaughtExceptionHandler defaultHandler;
    private Context context;
    private Properties properties = new Properties();
    
    /** 错误报告文件的扩展名 */
    private static final String CRASH_REPORTER_EXTENSION = ".log";

    private CrashHandler(){
        
    }

    public static CrashHandler newInstance() {
        if (crashHandler == null){
            crashHandler = new CrashHandler();
        }
        return crashHandler;
    }

    /**
     * 初始化,注册Context对象,   
     * 获取系统默认的UncaughtException处理器,   
     * 设置该CrashHandler为程序的默认处理器
     * @param context
     */
    public void init(Context context){
        this.context = context;
        defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }
    
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && defaultHandler != null){
            defaultHandler.uncaughtException(thread, ex);
        }else{
	        try {
	            Thread.sleep(4000);
	        } catch (InterruptedException e) {
	            Log.e(TAG, "Error : ", e);
	        }
	        Process.killProcess(Process.myPid());
	        System.exit(10);
        }
    }
    
    /** 
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成. 
     *  
     * @param ex 
     * @return true:如果处理了该异常信息;否则返回false. 
     */  
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }
        //使用Toast来显示异常信息  
        new CrashThread(context, ex.getLocalizedMessage()).start();
        //收集设备参数信息   
        collectDevInfo();
        //保存日志文件   
        saveCrashInfo2File(ex);  
        return true;  
    }
    
    /**
     * 收集设备信息
     */
    private void collectDevInfo(){
    	try {
            PackageInfo packageInfo = this.context.getPackageManager().getPackageInfo(this.context.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (packageInfo != null){
                this.properties.put("versionName", packageInfo.versionName == null ? "not set" : packageInfo.versionName);
                this.properties.put("versionCode", packageInfo.versionCode);
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "Error while collect package info", e);
        }
        for (Field field : Build.class.getDeclaredFields()){
            try {
                field.setAccessible(true);
                this.properties.put(field.getName(), field.get(null));
//                Log.d(TAG, field.getName() + " : " + field.get(null));
            } catch (IllegalAccessException e) {
                Log.e(TAG, "Error while collect crash info", e);
            }
        }
    }
    /**
     * 保存错误日志信息
     * @param ex
     */
    private void saveCrashInfo2File(Throwable ex){
    	if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
    		return;
    	}
    	Date date = new Date();
    	DateFormat formatter = new SimpleDateFormat(YYYYMMDDHHMMSS, Locale.CHINA);
    	StringBuffer buffer = new StringBuffer();
    	buffer.append("==========").append(formatter.format(date)).append("==========\r\n");
    	Enumeration<?> names = properties.propertyNames();
    	while (names.hasMoreElements()) {
			String name= names.nextElement().toString();
			String value = this.properties.getProperty(name);
			buffer.append(name + "=" + value + "\r\n");  
		}
    	Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {  
            cause.printStackTrace(printWriter);  
            cause = cause.getCause();  
        }
        printWriter.close();
        String result = writer.toString();
        buffer.append(result);
        try {
        	formatter = new SimpleDateFormat(YYYYMMDD, Locale.CHINA);
        	String filePath = Environment.getExternalStorageDirectory().getPath() + "/OneXLog/ddjc/";
        	String dateStr = formatter.format(date);
        	String fileName = "pdsbs-" + dateStr + CRASH_REPORTER_EXTENSION;
        	deleteFiles(filePath, formatter, formatter.parse(dateStr));
            File file = new File(filePath, fileName);
            if (!file.getParentFile().exists()) {
            	file.getParentFile().mkdirs();
            }
            FileOutputStream fos = null;
            OutputStreamWriter osw = null;
            try{
            	fos = new FileOutputStream(file, true);
            	osw = new OutputStreamWriter(fos, "UTF-8");
            	String errorInfo = new String(buffer.toString().getBytes("UTF-8"), "UTF-8");
            	osw.write(errorInfo+"\n\r");
            	osw.flush();
            	osw.close();
            	fos.flush();
            	fos.close();
            }catch(Exception e){
            	Log.e(TAG, "写入日志文件时，出错："+e.getMessage());
            }finally{
            	if (fos != null) {
            		try{
					fos.close();
            		}catch(Exception e){
            			Log.e(TAG, "关闭文件流时，出错："+e.getMessage());
            		}
				}
            	if (osw != null) {
            		try{
            			osw.close();
            		}catch(Exception e){
            			Log.e(TAG, "关闭输出流时，出错："+e.getMessage());
            		}
            	}
            }
        } catch (Exception e) {  
            Log.e(TAG, "an error occured while writing file...", e);  
        }  
    }
    public void deleteFiles(String filePath, final DateFormat formater, final Date curDate){
        File fileDir = new File(filePath);
        FilenameFilter filter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String filename) {
            	try {
            		String dateStr = filename.substring(filename.indexOf("-")+1, filename.indexOf("."));
					Date date = formater.parse(dateStr);
					int flag = date.compareTo(curDate);
					if (flag < 0) {
						return true;
					}
					return false;
				} catch (ParseException e) {
					return false;
				}
            }
        };
        String[] list = fileDir.list(filter);
        if (list != null && list.length > 0){
            TreeSet<String> treeSet = new TreeSet<String>();
            treeSet.addAll(Arrays.asList(list));
            Iterator<String> it = treeSet.iterator();
            while (it.hasNext()){
                new File(filePath, it.next()).delete();
            }
        }
    }
}
