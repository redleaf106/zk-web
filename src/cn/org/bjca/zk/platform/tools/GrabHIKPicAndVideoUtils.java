package cn.org.bjca.zk.platform.tools;

import cn.org.bjca.zk.hikvision.HCNetSDK;
import com.sun.jna.NativeLong;
import com.sun.jna.ptr.IntByReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;

/**  
* @ClassName: GrabHIKPicAndVideoUtils  
* @Description: 获取视频、照片功能     
*  sdk使用说明：第一步、初始化SDK(NET_DVR_Init)；第二步、用户注册设备(NET_DVR_Login_V30); 第三步、功能应用；第四步、注销设备(NET_DVR_Logout)；第五步、释放SDK资源(NET_DVR_Cleanup)
* @author huangminjiang  
* @date 2019年11月7日  
*    
**/
public class GrabHIKPicAndVideoUtils {

	private final static Logger logger = LoggerFactory.getLogger(GrabHIKPicAndVideoUtils.class);
	
	NativeLong m_lLoadHandle;//下载句柄
	
	Timer Downloadtimer;//下载用定时器
	
	NativeLong lUserID;//用户句柄
	/**  
	* @Title: main  
	* @Description: TODO(这里用一句话描述这个方法的作用)  
	* @param @param args 参数  
	* @return void 返回类型  
	* @throws  
	**/
	static HCNetSDK hCNetSDK = HCNetSDK.INSTANCE;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GrabHIKPicAndVideoUtils cap = new GrabHIKPicAndVideoUtils();
		boolean initSuc = hCNetSDK.NET_DVR_Init();
		if (initSuc) {
			//录像机相关参数
			String m_sDeviceIP = "192.168.18.100";//录像机ip
			int iPort = 8000;//录像机端口
			String userName = "admin";//录像机用户名
			String pwd = "wd12345678";//录像机密码
			int m_iChanShowNum = 33; // 摄像头通道

			// 20200421225208_20200421225218
			String startTime = "2020-05-13 22:40:00";//截取视频的起始时间
			String endTime = "2020-05-13 22:40:10";//截取视频的结束时间
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");//时间格式，文件名的命名相关
			StringBuilder fileName = null;
			// 获取视频
//			fileName = new StringBuilder("D:/HIKVISION/" + dateFormat.format(new Date()) + ".mp4");//创建视频文件的文件名
//			cap.GetFileByTime(m_sDeviceIP, iPort, userName, pwd, m_iChanShowNum, startTime, endTime, fileName.toString());//截取视频
			// 抓取拍照
			fileName = new StringBuilder("D:/HIKVISION/" + dateFormat.format(new Date()) + ".jpg");
			NativeLong IChannel = new NativeLong(m_iChanShowNum);
			cap.GrabPicture(m_sDeviceIP, iPort, userName, pwd, IChannel, fileName.toString());
			//cap.start(m_sDeviceIP, iPort, userName, pwd, IChannel);
			//cap.stop(m_sDeviceIP, iPort, userName, pwd, IChannel);
			//22:24:32 - 22:24:36
			int errorCode = hCNetSDK.NET_DVR_GetLastError();
			System.out.println("errorCode: " + errorCode);
		}
	}
	public void start(final String m_sDeviceIP, final int iPort, final String userName, final String pwd, NativeLong IChannel) {
		boolean initSuc = hCNetSDK.NET_DVR_Init();
		if (!initSuc) {
			logger.error("初始化设备失败!");
		}
		HCNetSDK.NET_DVR_DEVICEINFO_V30 m_strDeviceInfo = new HCNetSDK.NET_DVR_DEVICEINFO_V30();//设备信息

		lUserID = hCNetSDK.NET_DVR_Login_V30(m_sDeviceIP,
				(short) iPort, userName, pwd, m_strDeviceInfo);

		if (lUserID.longValue() == 0) {//注册成功
			System.out.println("lUserID: " + lUserID);
			NativeLong recordType = new NativeLong(0);
			boolean startResult = hCNetSDK.NET_DVR_StartDVRRecord(lUserID, IChannel, recordType);
			System.out.println("startResult: " + startResult);

			int errorCode = hCNetSDK.NET_DVR_GetLastError();
			System.out.println("errorCode: " + errorCode);

			boolean logoutResult = hCNetSDK.NET_DVR_Logout(lUserID);
			System.out.println("logoutResult: " + logoutResult);

			boolean cleanUpResult = hCNetSDK.NET_DVR_Cleanup();
			System.out.println("cleanUpResult: " + cleanUpResult);
		}
	}

	public void stop(final String m_sDeviceIP, final int iPort, final String userName, final String pwd, NativeLong IChannel) {
		boolean initSuc = hCNetSDK.NET_DVR_Init();
		if (!initSuc) {
			logger.error("初始化设备失败!");
		}
		HCNetSDK.NET_DVR_DEVICEINFO_V30 m_strDeviceInfo = new HCNetSDK.NET_DVR_DEVICEINFO_V30();//设备信息

		lUserID = hCNetSDK.NET_DVR_Login_V30(m_sDeviceIP,
				(short) iPort, userName, pwd, m_strDeviceInfo);

		if (lUserID.longValue() == 0) {//注册成功
			System.out.println("lUserID: " + lUserID);
			NativeLong recordType = new NativeLong(0);
			boolean startResult = hCNetSDK.NET_DVR_StopDVRRecord(lUserID, IChannel);
			System.out.println("startResult: " + startResult);

			int errorCode = hCNetSDK.NET_DVR_GetLastError();
			System.out.println("errorCode: " + errorCode);

			boolean logoutResult = hCNetSDK.NET_DVR_Logout(lUserID);
			System.out.println("logoutResult: " + logoutResult);

			boolean cleanUpResult = hCNetSDK.NET_DVR_Cleanup();
			System.out.println("cleanUpResult: " + cleanUpResult);
		}
	}
	
	public void GrabPicture(final String m_sDeviceIP, final int iPort, final String userName, final String pwd, NativeLong IChannel, final String fileName) {
		
		boolean initSuc = hCNetSDK.NET_DVR_Init();
		if (!initSuc) { 
			logger.error("初始化设备失败!");
		}
		HCNetSDK.NET_DVR_DEVICEINFO_V30 m_strDeviceInfo = new HCNetSDK.NET_DVR_DEVICEINFO_V30();//设备信息
		
		lUserID = hCNetSDK.NET_DVR_Login_V30(m_sDeviceIP,
                (short) iPort, userName, pwd, m_strDeviceInfo);

        if (lUserID.longValue() == 0) {//注册成功
        	System.out.println("lUserID: " + lUserID);
        	
        	HCNetSDK.NET_DVR_JPEGPARA lpJpegPara = new HCNetSDK.NET_DVR_JPEGPARA();
        	lpJpegPara.wPicQuality = 0;
        	lpJpegPara.wPicSize = 2;
        	boolean capResult = hCNetSDK.NET_DVR_CaptureJPEGPicture(lUserID, IChannel, lpJpegPara, fileName);
        	System.out.println("capResult:" + capResult);
        	int errorCode = hCNetSDK.NET_DVR_GetLastError();
        	System.out.println("errorCode: " + errorCode);
        	
        	boolean logoutResult = hCNetSDK.NET_DVR_Logout(lUserID);
            System.out.println("logoutResult: " + logoutResult);
            
            boolean cleanUpResult = hCNetSDK.NET_DVR_Cleanup();
            System.out.println("cleanUpResult: " + cleanUpResult);
            
        } else {
        	System.out.println("注册失败");
        }
	}
	
	/**
	 * 获取视频片段 
	 * @param m_iChanShowNum 回放通道
	 * @param startTime 开始时间
	 * @param endTime 结束时间  ， 注意格式要求： yyyy-MM-dd HH:mm:ss
	 * **/
	public void GetFileByTime(final String m_sDeviceIP, final int iPort, final String userName, final String pwd, int m_iChanShowNum, final String startTime, final String endTime, final String fileName) {
		
		boolean initSuc = hCNetSDK.NET_DVR_Init();
		if (!initSuc) { 
			logger.error("初始化设备失败!");
		}
		HCNetSDK.NET_DVR_TIME struStartTime = new HCNetSDK.NET_DVR_TIME();
	    HCNetSDK.NET_DVR_TIME struStopTime = new HCNetSDK.NET_DVR_TIME();
	     
	    if (null != startTime && !"".equals(startTime) && null != endTime && !"".equals(endTime)) {
	    	 String[] startTimes = startTime.split(" ");
	    	 String[] endTimes = endTime.split(" ");
	    	 struStartTime.dwYear = Integer.parseInt(startTimes[0].split("-")[0]);//开始时间
	         struStartTime.dwMonth = Integer.parseInt(startTimes[0].split("-")[1]);
	         struStartTime.dwDay = Integer.parseInt(startTimes[0].split("-")[2]);
	         struStartTime.dwHour = Integer.parseInt(startTimes[1].split(":")[0]);
	         struStartTime.dwMinute = Integer.parseInt(startTimes[1].split(":")[1]);
	         struStartTime.dwSecond = Integer.parseInt(startTimes[1].split(":")[2]);
	         struStopTime.dwYear = Integer.parseInt(endTimes[0].split("-")[0]);//结束时间
	         struStopTime.dwMonth = Integer.parseInt(endTimes[0].split("-")[1]);
	         struStopTime.dwDay = Integer.parseInt(endTimes[0].split("-")[2]);
	         struStopTime.dwHour = Integer.parseInt(endTimes[1].split(":")[0]);
	         struStopTime.dwMinute = Integer.parseInt(endTimes[1].split(":")[1]);
	         struStopTime.dwSecond = Integer.parseInt(endTimes[1].split(":")[2]);
	         HCNetSDK.NET_DVR_DEVICEINFO_V30 m_strDeviceInfo = new HCNetSDK.NET_DVR_DEVICEINFO_V30();//设备信息
	         //NativeLong lUserID;//用户句柄
	         lUserID = hCNetSDK.NET_DVR_Login_V30(m_sDeviceIP,
		                (short) iPort, userName, pwd, m_strDeviceInfo);
	         m_lLoadHandle = hCNetSDK.NET_DVR_GetFileByTime(lUserID, new NativeLong(m_iChanShowNum), struStartTime, struStopTime, fileName);
	         System.out.println("m_lLoadHandle: " + m_lLoadHandle);
	         System.out.println(" ==================================== ");
	         if (m_lLoadHandle.intValue() >= 0) {
	        	 hCNetSDK.NET_DVR_PlayBackControl(m_lLoadHandle, HCNetSDK.NET_DVR_PLAYSTART, 0, null);
	        	 Downloadtimer = new Timer();//新建定时器
	        	 Downloadtimer.schedule(new DownloadTask(), 0, 5000);//0秒后开始响应函数
	         }
	    } else {
	    	System.out.println("时间格式不符合要求规范!");
	    }
	}
	
	class DownloadTask extends java.util.TimerTask
    {
        //定时器函数 
        @Override
        public void run()
        {
            IntByReference nPos = new IntByReference(0);
            boolean playBackControl = hCNetSDK.NET_DVR_PlayBackControl(m_lLoadHandle, HCNetSDK.NET_DVR_PLAYGETPOS, 0, nPos);
            System.out.println("playBackControl: " + playBackControl);
            if (!playBackControl) {
            	
            	Downloadtimer.cancel();
            	boolean logoutResult = hCNetSDK.NET_DVR_Logout(lUserID);
                System.out.println("logoutResult: " + logoutResult);
                 
                boolean cleanUpResult = hCNetSDK.NET_DVR_Cleanup();
                System.out.println("cleanUpResult: " + cleanUpResult);
                 
            }
            if (nPos.getValue() > 100)
            {
                hCNetSDK.NET_DVR_StopGetFile(m_lLoadHandle);
                m_lLoadHandle.setValue(-1);
                Downloadtimer.cancel();
            }
            if (nPos.getValue() == 100)
            {
                hCNetSDK.NET_DVR_StopGetFile(m_lLoadHandle);
                m_lLoadHandle.setValue(-1);
                Downloadtimer.cancel();
            }
        }
    }
}