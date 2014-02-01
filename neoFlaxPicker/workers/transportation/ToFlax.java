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

public class ToFlax extends Worker {
	
	private void checkEnergy() {
		if (Game.getEnergy() > 80) {
			Game.setRun(true);
		}
	}
	
	@Override
	public void run() {
		checkEnergy();
		Path toFlaxPath = Walking.generatePath(new Tile(2741 + Random.nextInt(-7, 7), (3444 + Random.nextInt(-5, 5))));
		if(Constants.scriptZone.contains(toFlaxPath.getNext().getX(), toFlaxPath.getNext().getY())) {
			//Will only walk path if it is inside contained zone to avoid it walking out of zone
			toFlaxPath.traverse();
		}
	}

	@Override
	public boolean validate() {
		return  (!Constants.flaxField.contains(Players.getLocal()) && !Inventory.isFull());
	}
	
}