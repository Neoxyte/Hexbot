package neoFlaxPicker.workers.harvesting;

import org.hexbot.api.methods.Camera;
import org.hexbot.api.methods.interactable.GameObjects;
import org.hexbot.api.methods.interactable.Players;
import org.hexbot.api.util.Random;
import org.hexbot.api.util.Time;
import org.hexbot.api.wrapper.interactable.GameObject;
import org.hexbot.core.concurrent.script.Worker;

public class FlaxPicker extends Worker {
	
	private void antiBan(int seed) {
		//TODO add more antiban ideas here
		if(seed > 975) {
			Camera.setCameraRotation(Random.nextInt(-360, 360));
		}
		if(seed > 800 && seed < 815) {
			Camera.setPitch(true);
		}
	}
	
	public boolean canPick() {
		return (isIdle());
	}
	
	private boolean isIdle() {
		return Players.getLocal().isIdle();
	}

	@Override
	public void run() {
		GameObject flax = GameObjects.getNearest("Flax");
		if(!flax.isVisible()) {;
			Camera.turnTo(flax);
		}
		antiBan(Random.nextInt(1, 1000));
		flax.interact("Pick");
		Time.sleep(300, Random.nextInt(275, 425));
		
	}
	
	@Override
	public boolean validate() {
		return canPick();
	}
	
}