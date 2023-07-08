package com.solvd.ATM.StatePattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ATMMachine implements AtmMachineState {

	public static final Logger LOGGER = LogManager.getLogger(ATMMachine.class);
	private AtmMachineState atmMachineState;

	public ATMMachine() {
		atmMachineState = new NoDebitCardState();
	}

	public AtmMachineState getAtmMachineState() {
		return atmMachineState;
	}

	public void setAtmMachineState(AtmMachineState atmMachineState) {
		this.atmMachineState = atmMachineState;
	}

	@Override
	public void insertDebitCard() {
		atmMachineState.insertDebitCard();

		/*
		 * Debit card has been inserted so the internal state of ATMMAchine has been
		 * changed to HasDebitCardState
		 */

		if (atmMachineState instanceof NoDebitCardState) {
			AtmMachineState hasdebitcardstate = new HasDebitCardState();
			setAtmMachineState(hasdebitcardstate);
			LOGGER.info("ATM Machine debitcard state has been moved to :" + atmMachineState.getClass().getName());
		}
	}

	@Override
	public void ejectDebitCard() {

		atmMachineState.ejectDebitCard();

		/*
		 * Debit card has been ejected so the internal state of ATMMAchine has been
		 * changed to NoDebitCardState
		 */

		if (atmMachineState instanceof HasDebitCardState) {
			AtmMachineState nodebitcardstate = new NoDebitCardState();
			setAtmMachineState(nodebitcardstate);
			LOGGER.info("ATM Machine debitcard state has been moved to :" + atmMachineState.getClass().getName());
		}

	}

	@Override
	public void enterPinAndWithdrawCash() {
		atmMachineState.enterPinAndWithdrawCash();
	}

}
