package jammy;

import java.text.DecimalFormat;

public class MediaSizeFormatter {

	public static int MODE_DYNAMIC = 10;
	public static int MODE_B = 0;
	public static int MODE_KB = 1;
	public static int MODE_MB = 2;
	public static int MODE_GB = 3;
	public static int MODE_TB = 4;
	private static String[] measures = { "B", "kB", "MB", "GB", "TB" };
	private static int measureSelector = 0;
	private static DecimalFormat twoDForm = new DecimalFormat("#,##");
	
	public static String getText(long size){
		
		String value = twoDForm.format(getValue(size,MediaSizeFormatter.MODE_DYNAMIC));
		String res = new String(value+measures[measureSelector]);

		//String res = new String(getValue(size,MediaSizeFormatter.MODE_DYNAMIC)+measures[measureSelector]);
		return res;
	}
	
	public static String getText(long size,int mode){
		return twoDForm.format(getValue(size,mode))+measures[measureSelector];
	}
	
	private static float getValue(long size, int mode){
		float ret = size;
		measureSelector = 0;
		while(ret >= 1000 && mode > measureSelector){
			ret = ret / 1000;
			measureSelector++;
		}
		return ret;
	}
}
