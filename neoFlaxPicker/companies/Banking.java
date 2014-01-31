package neoFlaxPicker.companies;

import neoFlaxPicker.Constants;

import org.hexbot.api.methods.interactable.Players;
import org.hexbot.api.methods.node.Inventory;
import org.hexbot.core.concurrent.script.Company;
import org.hexbot.core.concurrent.script.Worker;


public class Banking extends Company {
	
	public Banking(Worker...workers) {
		super(workers);
	}
	
	@Override
	public boolean activate() {
		return ((Constants.bank1.contains(Players.getLocal()) ||
				Constants.bank2.contains(Players.getLocal())) && Inventory.isFull());
	}

}
