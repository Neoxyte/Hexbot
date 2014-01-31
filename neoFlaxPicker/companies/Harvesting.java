package neoFlaxPicker.companies;

import neoFlaxPicker.Constants;

import org.hexbot.api.methods.interactable.Players;
import org.hexbot.api.methods.node.Inventory;
import org.hexbot.core.concurrent.script.Company;
import org.hexbot.core.concurrent.script.Worker;

public class Harvesting extends Company {
	
	public Harvesting(Worker... workers) {
		super(workers);
	}
	
	@Override
	public boolean activate() {

		return (Constants.flaxField.contains(Players.getLocal()) && !Inventory.isFull());
	}
	
}
