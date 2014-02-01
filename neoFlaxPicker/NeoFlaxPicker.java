package neoFlaxPicker;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import neoFlaxPicker.companies.Banking;
import neoFlaxPicker.companies.Harvesting;
import neoFlaxPicker.companies.Transportation;
import neoFlaxPicker.workers.banking.Banker;
import neoFlaxPicker.workers.harvesting.FlaxPicker;
import neoFlaxPicker.workers.transportation.ToBank;
import neoFlaxPicker.workers.transportation.ToFlax;

import org.hexbot.api.listeners.Paintable;
import org.hexbot.api.methods.Camera;
import org.hexbot.api.util.Timer;
import org.hexbot.core.concurrent.script.Info;
import org.hexbot.core.concurrent.script.TaskScript;
 
@Info(author = "neoxyte", name = "NeoFlaxPicker", description = "Picks flax in Seers Village.")

public class NeoFlaxPicker extends TaskScript implements Paintable {
	
	public static Timer scriptTimer =  new Timer(999999999);
	public static long startTime = System.currentTimeMillis();
	
	public static Image paintImage = null;
	
	public static boolean debugOn = false;
	
	
	public static void log(String output) {
		System.out.println("\n" + Stats.timeRunning() + output);
	}
	
	public NeoFlaxPicker() {
		submit( new Harvesting(new FlaxPicker()), 
				new Banking(new Banker()),
				new Transportation(new ToBank(), new ToFlax())
		);
	}
 
    public void onStart() {
    	setUpPaintImage();
    	if(debugOn) {
    		log( " NeoFlaxPicker script version " + Stats.scriptVersion + " started.");
    	}
    	Camera.setPitch(true);
    }
    
    public void onEnd() {
    	if(debugOn) {
    		log(" NeoFlaxPicker script version " + Stats.scriptVersion +" terminated.");
    	}
    }

    public static void setUpPaintImage() {
        try {
            URL url = new URL("http://i.imgur.com/H20zJWW.png");
            paintImage = ImageIO.read(url);
        } catch (IOException e) {
        	log("Can not find paint image. Is imgur.com down?");
            return;
        }
    }
    
   private void paintCalculations() {
	   long timeRan = System.currentTimeMillis() - startTime;
       
       //Flax per hour
       double flaxBankedPerMs= Stats.flaxBanked * 1.0 / timeRan;
       Stats.flaxPerHour = (int) (flaxBankedPerMs * 3600 * 1000);
       
       //Profit gained
       Stats.profitGained = Stats.flaxBanked * 35;
       
       //Profit per hour
       double profitPerMillisecond = Stats.profitGained * 1.0 / timeRan;
       Stats.profitPerHour = (int) (profitPerMillisecond * 3600 * 1000);
       
    } 
    
	public void paint(Graphics paint) {
		paintCalculations();
		paint.drawImage(paintImage, 5, 5, null);
		paint.setColor(Color.WHITE);
		paint.drawString(scriptTimer.toElapsedString(), 26, 62); //Time Running
		paint.drawString(String.valueOf(Stats.flaxBanked), 26, 95); 
		paint.drawString(String.valueOf(Stats.profitGained), 26, 129); 
		paint.drawString(String.valueOf(Stats.flaxPerHour), 26, 162);
		paint.drawString(String.valueOf(Stats.profitPerHour), 26, 197); 
	}
    
}

