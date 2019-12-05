package cn.org.bjca.zk.platform.utils;


import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.List;
import java.util.StringTokenizer;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.bouncycastle.util.encoders.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import com.itextpdf.text.pdf.security.BouncyCastleDigest;

import cn.org.bjca.zk.platform.PDFSealConstants;

/***************************************************************************
 * <pre>系统工具类</pre>
 ***************************************************************************/
public final class PDFSealUtil {

	private static Logger logger = LoggerFactory.getLogger(PDFSealUtil.class);// 日志

	/**
	 * <p>产生随机UUID</p>
	 * @Description:
	 * @return
	 */
	public static String genrRandomUUID() {
		String uuid = UUID.randomUUID().toString();
		uuid = uuid.replaceAll("-", "");
		return uuid;
	}

	/**
	 * <p>获取客户端ip地址</p>
	 * @Description:
	 * @param request 请求
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	/**
	 * <p>根据request 获取当前工程路径</p>
	 * @param request 请求
	 * @return
	 */
	public static String getCurrPath(HttpServletRequest request) {
		String path = request.getSession().getServletContext().getRealPath("/");
		path = path.replaceAll("\\\\", "/");
		return "/" + path;
	}


	/**
	 * <p>产生摘要</p>
	 * @Description:
	 * @param data 数据
	 * @return
	 * @throws Exception
	 */
	public static String digest(final String data) throws Exception {
		BouncyCastleDigest externalDigest = new BouncyCastleDigest();
		MessageDigest messageDigest = externalDigest.getMessageDigest("SHA-1");
		messageDigest.update(data.getBytes(PDFSealConstants.CODING));
		byte[] bty = messageDigest.digest();
		return new String(Base64.encode(bty), PDFSealConstants.CODING);
	}

	/**
	 * <p>产生摘要</p>
	 * @Description:
	 * @param data 数据
	 * @param alg 摘要算法
	 * @return
	 * @throws Exception
	 */
	public static byte[] digest(final byte[] data,final String alg) throws Exception {
		BouncyCastleDigest externalDigest = new BouncyCastleDigest();
		MessageDigest messageDigest = externalDigest.getMessageDigest(alg);
		messageDigest.update(data);
		byte[] result = messageDigest.digest();
		return result;
	}

	
	/**
	  * <p>数据混淆</p>
	  * @Description:
	  * @param data 数据
	  * @return
	  * @throws Exception
	 */
	public static String confuse(final String data) throws Exception {
		return reverseSort(str2Ascii(digest(str2Ascii(digest(data)))));
	}

	/**
	  * <p>倒序输出</p>
	  * @Description:
	  * @param str 字符串
	  * @return
	 */
	private static String reverseSort(final String str) {
		StringBuffer sb = new StringBuffer();
		for (int i = str.length() - 1; i > -1; i--) {
			sb.append(String.valueOf(str.charAt(i)));
		}
		return sb.toString();
	}

	/**
	 * <p>产生ASCII值</p>
	 * @Description:
	 * @param data 数据
	 * @return
	 */
	private static String str2Ascii(final String data) {
		StringBuffer sb = new StringBuffer();
		char[] chars = data.toCharArray(); // 把字符中转换为字符数组
		for (int i = 0; i < chars.length; i++) {// 输出结果
			sb.append((int) chars[i]);
		}
		return sb.toString();
	}

	/**
	 * <p>获取当前工程classes路径</p>
	 * @return
	 */
	public static String getCurrPath() {
		ClassPathResource resource = new ClassPathResource("/");
		String path = null;
		try {
			path = resource.getFile().getAbsolutePath();
			path = path.replaceAll("\\\\", "/") + "/";
			logger.debug("**************the current path is:" + path);
		} catch (IOException e) {
			logger.error("**************get classes path fails:", e);
		}
		return path;
	}

	/**
	 * <p>Description:ip转数字</p>
	 * @param ip ip字符串
	 * @return
	 */
	public static Long ipToNumber(String ip) throws Exception {
		Long ipNumberValue = null;
		if (ip == null || ip.length() == 0) {
			throw new RuntimeException("IP address is NULL error！");
		}
		try {
			StringBuffer sb = new StringBuffer("");
			String ss = null;
			for (StringTokenizer st = new StringTokenizer(ip, "."); st.hasMoreElements(); sb.append(ss)) {
				String ipVaule = (String) st.nextElement();
				String add = null;
				for (ss = Integer.toBinaryString((new Integer(ipVaule)).intValue()); ss.length() < 8; ss = add) {
					add = "0";
					add = (new StringBuilder(String.valueOf(add))).append(ss).toString();
				}

			}
			ipNumberValue = Long.valueOf(sb.toString(), 2);
		} catch (Exception e) {
			throw new RuntimeException("IP address format error！");
		}
		return ipNumberValue;
	}

	/**
	 * <p>Description:数字转IP</p>
	 * @param ipNumber ip数字
	 * @return
	 */
	public static String numberToIp(Long ipNumber) throws Exception {
		if (Long.valueOf("0").longValue() > ipNumber.longValue() || ipNumber.longValue() > Long.valueOf("4294967295").longValue()) {
			throw new RuntimeException("number to ip error，number out of range!");
		}
		String ipBStr = Long.toBinaryString(ipNumber.intValue());
		if (ipBStr.length() > 32)
			ipBStr = ipBStr.substring(32);
		StringBuffer ipAddress = new StringBuffer("");
		String add;
		for (; ipBStr.length() < 32; ipBStr = add) {
			add = "0";
			add = (new StringBuilder(String.valueOf(add))).append(ipBStr).toString();
		}

		int b = 0;
		for (int n = 8; n <= 32;) {
			ipAddress.append((new StringBuilder()).append(Long.valueOf(ipBStr.substring(b, n), 2)).append(".").toString());
			n += 8;
			b += 8;
		}

		String ip = ipAddress.toString();
		int nLast = ip.lastIndexOf(".");
		ip = ip.substring(0, nLast);
		return ip;
	}

	/**
	 * <p>十进制转十六进制</p>
	 * @Description:
	 * @param n int型数据
	 * @return
	 */
	public static String numtoHex(int n) {
		String hexStr = Integer.toHexString(n).toUpperCase();
		for (int i = hexStr.length(); i < 3; i++) {
			hexStr = "0" + hexStr;
		}
		return hexStr;
	}

	/**
	  * <p>进行密码的方向XOR</p>
	  * @Description:
	  * @param str 字符串
	  * @param xorKey 整数
	  * @return
	 */
	public static String xorDecode(String str, int xorKey) {
		char[] charArray = str.toCharArray();
		for (int i = 0; i < charArray.length; i++) {
			charArray[i] = (char) (charArray[i] ^ xorKey);
		}
		return new String(charArray);
	}

	/**
	  * <p>合并图片</p>
	  * @Description:
	  * @param imgList 图片列表
	  * @param type 类型
	  * @return
	  * @throws Exception
	 */
	public static byte[] mergeImage(List<byte[]> imgList, String type) throws Exception {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] allImageBty = null;
		try {
			int len = imgList.size();
			BufferedImage[] images = new BufferedImage[len];
			int[][] ImageArrays = new int[len][];
			for (int i = 0; i < len; i++) {
				images[i] = ImageIO.read(new ByteArrayInputStream(imgList.get(i)));
				int width = images[i].getWidth();
				int height = images[i].getHeight();
				ImageArrays[i] = new int[width * height];// 从图片中读取RGB
				ImageArrays[i] = images[i].getRGB(0, 0, width, height, ImageArrays[i], 0, width);
			}
			int dst_height = 0;
			for (int i = 0; i < images.length; i++) {
				dst_height += images[i].getHeight();
			}
			// 生成新图片
			int dst_width = images[0].getWidth();
			BufferedImage ImageNew = new BufferedImage(images[0].getWidth(), dst_height, BufferedImage.TYPE_INT_RGB);
			int height_i = 0;
			for (int i = 0; i < images.length; i++) {
				ImageNew.setRGB(0, height_i, dst_width, images[i].getHeight(), ImageArrays[i], 0, dst_width);
				height_i += images[i].getHeight();
			}
			ImageIO.write(ImageNew, type, out);// 写图片
			allImageBty = out.toByteArray();
		} finally {
			IOUtils.closeQuietly(out);
		}
		return allImageBty;
	}

	
	public static void createDirectory (String direPath) throws Exception {
		File inDir = new File(direPath);
		if (!inDir.exists()) {
			FileUtils.forceMkdir(inDir); 
		}
	}
	
	public static void main(String[] args) throws Exception {
		String direPath = "c:/1111/2222";
		PDFSealUtil.createDirectory(direPath);
	}
	
}
