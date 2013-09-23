package tttrung43.yahoo.com.libs;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;

public class Utilities {

	private static Utilities utilities = new Utilities();

	private Utilities() {

	}

	public static Utilities getInstance() {
		return utilities;
	}

	public boolean checkSDCard() {
		return Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED);
	}

	public boolean checkNetworkConnection(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnected()) {
			return true;
		}
		return false;
	}

	public String MD5(String strData) {
		try {
			byte[] byeData = strData.getBytes("UTF-8");
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] afterHash = md.digest(byeData);
			return new BigInteger(1, afterHash).toString(16).toUpperCase(
					Locale.getDefault());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Bitmap Link2Image(String imgLink) {
		try {
			URL url = new URL(imgLink);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.connect();						
			Bitmap bit = BitmapFactory.decodeStream(conn.getInputStream());
			conn.disconnect();
			return bit;
		} catch (Exception e) {
			return null;
		}
	}
}
