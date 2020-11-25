package cn.org.bjca.zk.hikvision.ClientDemo;

import com.sun.jna.NativeLong;

//创建用户实例（单例模式）
public class LUserCreateUtil {
    private static NativeLong instance;
    static HCNetSDK hCNetSDK = HCNetSDK.INSTANCE;
    private LUserCreateUtil(){}
    public static NativeLong getInstance(String m_sDeviceIP, short iPort, String userName, String pwd, HCNetSDK.NET_DVR_DEVICEINFO_V30 m_strDeviceInfo){
        if(instance == null){
            instance = hCNetSDK.NET_DVR_Login_V30(m_sDeviceIP,
                    (short) iPort, userName, pwd, m_strDeviceInfo);
        }
        return instance;
    }
}
