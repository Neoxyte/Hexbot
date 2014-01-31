package neoFlaxPicker.companies;

import neoFlaxPicker.Constants;
import neoFlaxPicker.NeoFlaxPicker;
import neoFlaxPicker.Stats;

import org.hexbot.api.methods.Game;
import org.hexbot.api.methods.interactable.Players;
import org.hexbot.api.methods.node.Inventory;
import org.hexbot.core.concurrent.script.Company;
import org.hexbot.core.concurrent.script.Worker;

public class Transportation extends Company {
	
	public Transportation(Worker... workers) {
		super(workers);
	}
	
	private boolean inScriptZone() {
		return Constants.scriptZone.contains(Players.getLocal());
	}
	
	private boolean needToGoFlax() {
		//Returns true if not in flax field and does not need to bank.
		return  (!Constants.flaxField.contains(Players.getLocal()) && !Inventory.isFull());
	}
	
	private boolean needToGoBank() {
		//Returns true if player is not in bank and inventory is full
		return ((!Constants.bank1.contains(Players.getLocal()) && !Constants.bank2.contains(Players.getLocal())) && Inventory.isFull());
	}
	
	@Override
	public boolean activate() {
		//NeoFlaxPicker.log(String.valueOf(NeoFlaxPicker.scriptTimer.getElapsed()));
		if(!inScriptZone()) {
			//log out and stop script
			NeoFlaxPicker.log("\n" + Stats.timeRunning() + " Not in script zone. Stopping script.");
			Game.logout();
		}
		return (needToGoFlax() || needToGoBank());
	}


}
