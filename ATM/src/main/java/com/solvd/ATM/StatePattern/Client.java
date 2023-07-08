package com.solvd.ATM.StatePattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Client {

	public static final Logger LOGGER = LogManager.getLogger(Client.class);

	public static void main(String[] args)

	{
		/*
		 * Initially ATM Machine in NoDebitCardState
		 * 
		 */

		ATMMachine atmmachine = new ATMMachine();
		LOGGER.info("ATM Machine current state is :" + atmmachine.getAtmMachineState().getClass().getName());

		atmmachine.enterPinAndWithdrawCash();
		atmmachine.ejectDebitCard();
		atmmachine.insertDebitCard();

		LOGGER.info("***************************************************************");

		/*
		 * Debit card has been inserted so the internal state of AtmMachine changes to
		 * HasDebitCard State
		 * 
		 */
		LOGGER.info("ATM Machine current state is :" + atmmachine.getAtmMachineState().getClass().getName());

		atmmachine.enterPinAndWithdrawCash();
		atmmachine.ejectDebitCard();
		atmmachine.insertDebitCard();

	}
}
