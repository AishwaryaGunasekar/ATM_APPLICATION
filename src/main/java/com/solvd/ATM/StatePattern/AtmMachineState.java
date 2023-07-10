package com.solvd.ATM.StatePattern;

public interface AtmMachineState {
	
	public void insertDebitCard();
	public void ejectDebitCard();
	public void enterPinAndWithdrawCash();

}
