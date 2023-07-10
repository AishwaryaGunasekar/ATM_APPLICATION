package com.solvd.ATM.StatePattern;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class NoDebitCardState implements AtmMachineState {

	public static final Logger LOGGER = LogManager.getLogger(NoDebitCardState.class);

	@Override
	public void insertDebitCard() {
		LOGGER.info("you can insert debitcard at this state");

	}

	@Override
	public void ejectDebitCard() {
		LOGGER.info("As there is no debitcard , you cannot eject debitcard");

	}

	@Override
	public void enterPinAndWithdrawCash() {
		LOGGER.info("As there is no debitcard inserted , you cannot enter pin or withdraw money");
		
	}
}
