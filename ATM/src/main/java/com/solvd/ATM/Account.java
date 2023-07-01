package com.solvd.ATM;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Account {

	public static final Logger LOGGER = LogManager.getLogger(ATM.class);

	private String name;

	private String uuid;

	private User holder;

	private ArrayList<Transaction> transactions;

	public Account(String name, User holder, Bank thebank) {
		this.name = name;
		this.holder = holder;

		this.uuid = thebank.getNewAccountUUID();
		this.transactions = new ArrayList<Transaction>();

	}

	public String getUUID() {

		return this.uuid;
	}

	public String getSummaryLine() {
		// get the account's balance
		double balance = this.getBalance();

		// format the summaryline , depending on whether
		// the balance is negative
		if (balance >= 0) {
			return String.format("%s: $%.02f: %s", this.uuid, balance, this.name);
		} else {
			return String.format("%s :$(%.02f) : %s", this.uuid, balance, this.name);

		}
	}

	public double getBalance() {

		double balance = 0;
		for (Transaction t : this.transactions) {
			balance += t.getAmount();
		}
		return balance;
	}

	public void printTransaction() {

		LOGGER.info(String.format("\n Transaction history for the account %s\n", this.uuid));
		for (int t = this.transactions.size() - 1; t >= 0; t--) {
			LOGGER.info(String.format(this.transactions.get(t).getSummaryLine()));
		}

	}

	public void addTransaction(double amount, String memo) {
		// create new transacation object and add it to our list
		Transaction newTrans = new Transaction(amount, memo, this);
		this.transactions.add(newTrans);
	}
}
