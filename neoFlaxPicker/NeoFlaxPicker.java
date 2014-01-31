package neoFlaxPicker;

import java.awt.Color;
import java.awt.Graphics;

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

/* Make sure to either 1. put your bank pin in before starting the script
 *                 OR  2. select your account under account settings in the bot with proper pin
 *                 
 * TODO: GUI, improve antiban, draw better paint, test for longer periods of time.
 * 
 * Antiban ideas:
 * -Human error (Misclicks, breaks, camera rotation, banking errors).
 * -Log out if jmod nearby.
 * -Turn private off and turn public off.
 * -Widen script zone just a little (due to path finding that sometimes go far too east
 * -talking using custom text file that can be uploaded to pastie?
 */
 
@Info(author = "neoxyte", name = "NeoFlaxPicker", description = "Picks flax in Seers Village.")

public class NeoFlaxPicker extends TaskScript implements Paintable {
	
	public static Timer scriptTimer =  new Timer(999999999);
	public static long startTime = System.currentTimeMillis();
	
	public static boolean debugOn = true;
	
	
	public static void log(String output) {
		if (debugOn) {
			System.out.println("\n" + Stats.timeRunning() + output);
		}
		else {
			System.out.println(output);
		}
	}
	
	public NeoFlaxPicker() {
		submit( new Harvesting(new FlaxPicker()), 
				new Banking(new Banker()),
				new Transportation(new ToBank(), new ToFlax())
		);
	}
 
    public void onStart() {
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

   private void paintCalculations() {
	   long timeRan = System.currentTimeMillis() - startTime;
       
       //Flax per hour
       double flaxBankedPerMs= Stats.flaxBanked * 1.0 / timeRan;
       Stats.flaxPerHour = (int) (flaxBankedPerMs * 3600 * 1000);
       
       //Profit gained
       Stats.profitGained = Stats.flaxBanked * 30;
       
       //Profit per hour
       double profitPerMillisecond = Stats.profitGained * 1.0 / timeRan;
       Stats.profitPerHour = (int) (profitPerMillisecond * 3600 * 1000);
       
       

    } 
    
	public void paint(Graphics paint) {
		paintCalculations();
		paint.setColor(Color.CYAN);
		paint.fillRect(5, 235, 250, 100);
		paint.setColor(Color.BLACK);
		paint.drawString("NeoFletch- Made by Neoxyte", 10, 250);
		paint.drawString("Time Running: " + scriptTimer.toElapsedString(), 10, 270);
		paint.drawString("Flax Banked: " + String.valueOf(Stats.flaxBanked), 10, 285);
		paint.drawString("Flax per Hour: " + String.valueOf(Stats.flaxPerHour), 10, 300);
		paint.drawString("Profit Gained: " + String.valueOf(Stats.profitGained),10, 315);
		paint.drawString("Profit per Hour: " + String.valueOf(Stats.profitPerHour), 10, 330);
	}
    
}

