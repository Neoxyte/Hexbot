package neoFlaxPicker.workers.transportation;

import neoFlaxPicker.Constants;

import org.hexbot.api.methods.Game;
import org.hexbot.api.methods.Walking;
import org.hexbot.api.methods.interactable.Players;
import org.hexbot.api.methods.node.Inventory;
import org.hexbot.api.util.Random;
import org.hexbot.api.wrapper.Path;
import org.hexbot.api.wrapper.Tile;
import org.hexbot.core.concurrent.script.Worker;

public class ToBank extends Worker {
	
	private void checkEnergy() {
		if (Game.getEnergy() > 80) {
			Game.setRun(true);
		}
	}
	
	public boolean inBank() {
		return (Constants.bank1.contains(Players.getLocal()) || Constants.bank2.contains(Players.getLocal()));
	}
	
	@Override
	public void run() {
		checkEnergy();
		Path toBankPath = Walking.generatePath(new Tile((Random.nextInt(-3, 3) + 2725), (Random.nextInt(-3, 3) + 3490)));
		toBankPath.traverse();
	}

	@Override
	public boolean validate() {
		return (Inventory.isFull() && !inBank());
	}
	
}