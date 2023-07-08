package com.solvd.ATM;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class User {

	public static final Logger LOGGER = LogManager.getLogger(User.class);

	private String firstName;

	private String lastName;

	private String uuid;

	private byte pinHash[];

	private ArrayList<Account> accounts;

	public User(String firstName, String lastName, String pin, Bank thebank) {
		this.firstName = firstName;
		this.lastName = lastName;

		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			this.pinHash = md.digest(pin.getBytes());
		} catch (NoSuchAlgorithmException e) {
			LOGGER.error("error,caught NoSuchoAlgorithmException");
			e.printStackTrace();
			System.exit(1);
		}
		this.uuid = thebank.getNewUserUUID();
		this.accounts = new ArrayList<Account>();

		LOGGER.info(String.format("New user %s,%s with ID %s is created.\n ", lastName, firstName, this.uuid));
	}

	public void addAccount(Account anAcct) {
		this.accounts.add(anAcct);

	}

	public String getUUID() {
		return this.uuid;
	}

	public boolean validatePin(String aPin) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			return MessageDigest.isEqual(md.digest(aPin.getBytes()), this.pinHash);
		} catch (NoSuchAlgorithmException e) {
			LOGGER.error("error,caught NoSuchoAlgorithmException");
			e.printStackTrace();
		}
		return false;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void printAccountsSummary() {

		LOGGER.info(String.format("\n\n%s 's accounts summary", this.firstName));
		for (int a = 0; a < this.accounts.size(); a++) {
			LOGGER.info(String.format("%d) %s\n", a + 1, this.accounts.get(a).getSummaryLine()));

		}

	}

	public int numAccounts() {

		return this.accounts.size();
	}

	public void printAcctTransactionHistory(int acctIdx) {
		this.accounts.get(acctIdx).printTransaction();
	}

	public double getAcctBalance(int acctIdx) {

		return this.accounts.get(acctIdx).getBalance();
	}

	public String getAcctUUID(int acctIdx) {

		return this.accounts.get(acctIdx).getUUID();
	}

	public void addAcctTransaction(int acctIdx, double amount, String memo) {
		{
			this.accounts.get(acctIdx).addTransaction(amount, memo);
		}

	}
	public void changePin(String oldPin, String newPin) {
		if (validatePin(oldPin)) {
			try {
				MessageDigest md = MessageDigest.getInstance("MD5");
				this.pinHash = md.digest(newPin.getBytes());
				LOGGER.info("Pin changed successfully.");
			} catch (NoSuchAlgorithmException e) {
				LOGGER.error("Error: Unable to change pin. Caught NoSuchAlgorithmException");
				e.printStackTrace();
			}
		} else {
			LOGGER.error("Invalid old pin. Pin not changed.");
		}
	}
	public void setPin(String newPin) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			this.pinHash = md.digest(newPin.getBytes());
			LOGGER.info("Pin changed successfully.");
		} catch (NoSuchAlgorithmException e) {
			LOGGER.error("Error: Unable to change pin. Caught NoSuchAlgorithmException");
			e.printStackTrace();
		}
	}

	public void balanceEnquiry() {
			LOGGER.info("Account Balance Enquiry");
			for (Account account : accounts) {
				LOGGER.info(account.getSummaryLine());
			}
		}
}
