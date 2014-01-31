package neoFlaxPicker;
//Statistics file for paint and debug logging.

public class Stats {
	/*
			Flax Picked:                         Flax per Hr: 
			Profit Gained:                      Profit per Hr:*/
	public static String scriptVersion = "1.0";
	public static int flaxBanked = 0;
	public static int profitGained = 0;
	public static int flaxPerHour;
	public static int profitPerHour;
	public static int startBankedFlax = 0;
	public static boolean firstBankCompleted = false;
	
	
	public static String timeRunning () {
		return NeoFlaxPicker.scriptTimer.toElapsedString();
	}
	
}
