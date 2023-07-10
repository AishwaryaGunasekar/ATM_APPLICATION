package com.solvd.ATM.StatePattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HasDebitCardState implements AtmMachineState {

	
	public static final Logger LOGGER = LogManager.getLogger(HasDebitCardState.class);
	@Override
	public void insertDebitCard() {
		LOGGER.info("As there is already debitcard inserted , you cannot insert the debitcard");

	}

	@Override
	public void ejectDebitCard() {
		LOGGER.info("You can eject the debitcard ");

	}

	@Override
	public void enterPinAndWithdrawCash() {
	LOGGER.info("You can enter the atm pin and withdraw the cash");
	}

}
