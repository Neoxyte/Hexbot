package neoFlaxPicker.workers.banking;

import neoFlaxPicker.Stats;

import org.hexbot.api.methods.Camera;
import org.hexbot.api.methods.helper.Bank;
import org.hexbot.api.methods.interactable.GameObjects;
import org.hexbot.api.methods.node.Inventory;
import org.hexbot.api.util.Random;
import org.hexbot.api.util.Time;
import org.hexbot.api.wrapper.interactable.GameObject;
import org.hexbot.core.concurrent.script.Worker;

public class Banker extends Worker {
	
	private void antiBan(int seed) {
		//TODO add more antiban ideas here
		if(seed > 975) {
			Camera.setCameraRotation(Random.nextInt(-360, 360));
		}
		if(seed > 800 && seed < 815) {
			Camera.setPitch(true);
		}
	}
	
	public boolean needBank() {
		return (Inventory.getCount() > 0);
	}
	
	@Override
	public void run() {
		antiBan(Random.nextInt(1, 1000));
		GameObject bankBooth = GameObjects.getNearest("Bank booth");
		if (!Bank.isOpen()) {
			if (!bankBooth.isVisible()) {
				Camera.turnTo(bankBooth);
			}
			bankBooth.interact("Bank");
			Time.sleep(500, 2000);
		}
		else {
			if(!Stats.firstBankCompleted) {
				if(!Bank.contains("Flax")) {
					Stats.startBankedFlax = 0;
					Stats.firstBankCompleted = true;
				}
				else {
					Stats.startBankedFlax = Bank.getItem("Flax").getStackSize();
					Stats.firstBankCompleted = true;
				}
			}
			Bank.depositAll();
			Time.sleep(200,1500);
			if(Bank.contains("Flax")) {
				Stats.flaxBanked =  Bank.getItem("Flax").getStackSize() - Stats.startBankedFlax;
			}
			Bank.close();
		}
	}

	@Override
	public boolean validate() {
		return (needBank());
	}

}
