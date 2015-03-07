package com.xwh.anychat.util;

import java.io.ByteArrayOutputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;

public class BitmapToAvatorUtil {

	/**
	 * 将制定路径下的图像文件转换为指定长宽的byte数据
	 * 
	 * @param bitmapPath
	 * @param width
	 * @param height
	 * @return
	 */
	public static Bitmap resizeBitmap(String bitmapPath, int width, int height) {
		Options options = new Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(bitmapPath, options);
		options.inSampleSize = calcSampleSize(options, width, height);
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(bitmapPath, options);
	}

	// Bitmap转byte[]
	public static byte[] bitmapToBytes(Bitmap bitmap) {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
		return byteArrayOutputStream.toByteArray();
	}

	// 计算sampleSize
	private static int calcSampleSize(Options options, int width, int height) {
		int returnData = 1;
		if (options.outHeight > height || options.outWidth > width) {
			int heightRatio = Math.round((float) options.outHeight / (float) height);
			int widthRatio = Math.round((float) options.outWidth / (float) width);
			returnData = heightRatio > widthRatio ? heightRatio : widthRatio;
		}
		return returnData;
	}

}
