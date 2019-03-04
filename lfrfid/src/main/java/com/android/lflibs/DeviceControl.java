package com.android.lflibs;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DeviceControl
{
	private BufferedWriter CtrlFile;
	
	public DeviceControl(String path) throws IOException	
	{
		File DeviceName = new File(path);
		CtrlFile = new BufferedWriter(new FileWriter(DeviceName, false));	//open file
	}
	
	public void PowerOnDevice() throws IOException		//poweron lf device
	{
		CtrlFile.write("-wdout94 1");//64
		CtrlFile.flush();
	}
	
	public void PowerOffDevice() throws IOException	//poweroff lf device
	{
  		CtrlFile.write("-wdout94 0");//64
  		CtrlFile.flush();
	}
	public void DeviceClose() throws IOException		//close file
	{
		CtrlFile.close();
	}
}