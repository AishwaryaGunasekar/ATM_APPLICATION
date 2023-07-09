package com.solvd.ATM;

import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/* This class of
 * for bank's particular ATM
 */
public class ATM {

	public static final Logger LOGGER = LogManager.getLogger(ATM.class);

	public static void main(String args[]) {

		// init Scanner
		Scanner sc = new Scanner(System.in);

		// init Bank
		Bank theBank = new Bank("Bank of AshNavi");

		// add a user ,which also creates a savings account
		User aUser = theBank.addUser("Aishu", "Guna", "2208");

		// add a checking account for our user
		Account newAccount = new Account("Checking", aUser, theBank);
		aUser.addAccount(newAccount);
		theBank.addAccount(newAccount);

		User curUser;
		while (true) {
			// stay in the login prompt until successful login
			curUser = ATM.mainMenuPrompt(theBank, sc);

			// stay in main menu until user quits
			ATM.printUserMenu(curUser, sc);

		}
	}

	public static User mainMenuPrompt(Bank theBank, Scanner sc) {

		String userID;
		String pin;
		User authUser;

		// prompt the user for user ID/pin combo until a correct one is reached
		do {
			LOGGER.info(String.format("\n\nWelcome to %s\n\n", theBank.getName()));
			LOGGER.info("Enter the UserID:");
			userID = sc.nextLine();
			LOGGER.info("Enter pin:");
			pin = sc.nextLine();

			authUser = theBank.userLogin(userID, pin);
			if (authUser == null) {
				LOGGER.error("Incorrect userID/pin combination." + "Please try again");

			}

		} while (authUser == null); // continue loopoing until successful login
		return authUser;
	}

	public static void printUserMenu(User theUser, Scanner sc) {
		// print a summary of the User's accounts
		theUser.printAccountsSummary();
		// init
		int choice;

		// user menu
		do {
			LOGGER.info(String.format("Welcome %s, what would you like to do?", theUser.getFirstName()));
			LOGGER.info("  1) Show account transaction history");
			LOGGER.info("  2) Withdrawal");
			LOGGER.info("  3) Deposit");
			LOGGER.info("  4) Transfer");
			LOGGER.info("  5) Balance Enquiry");
			LOGGER.info("  6) Change Pin");
			LOGGER.info("  7) Pay Bill");
			LOGGER.info("  8) Exit");

			LOGGER.info("Enter choice");
			choice = sc.nextInt();

			if (choice < 1 || choice > 8) {
				LOGGER.error("Invalid choice,Please choose 1-8");
			}

		} while (choice < 1 || choice > 7);
		switch (choice) {
		case 1:
			ATM.showTransactionHistory(theUser, sc);
			break;
		case 2:
			ATM.withdrawFunds(theUser, sc);
			break;
		case 3:
			ATM.depositFunds(theUser, sc);
			break;
		case 4:
			ATM.transferFunds(theUser, sc);
			break;
		case 5:
			ATM.balanceEnquiry(theUser, sc);
			break;
		case 6:
			ATM.changePin(theUser, sc);
			break;
		case 7:
			ATM.payBill(theUser, sc);
			break;

		case 8:
			System.out.println("Thank you for using the ATM. Goodbye!");
			break;
		default:
			System.out.println("Invalid choice. Please try again.");
			break;

		}
		// redisplay the menu until the user wants to quit
		if (choice != 7) {
			ATM.printUserMenu(theUser, sc);
		}
	}

	public static void balanceEnquiry(User theUser, Scanner sc) {
		LOGGER.info("Account Balance Enquiry");
		theUser.printAccountsSummary();
	}

	public static void changePin(User theUser, Scanner sc) {
		LOGGER.info("Change Pin");
		sc.nextLine(); // Consume the newline character
		LOGGER.info("Enter your old pin:");
		String oldPin = sc.nextLine();
		LOGGER.info("Enter your new pin:");
		String newPin = sc.nextLine();

		if (theUser.validatePin(oldPin)) {
			theUser.setPin(newPin);
			LOGGER.info("Pin changed successfully.");
		} else {
			LOGGER.error("Invalid old pin. Pin not changed.");
		}
	}

	public static void showTransactionHistory(User theUser, Scanner sc) {
		int theAcct;
		// get account whose transaction history to look at
		do {
			LOGGER.info(String.format("Enter the number(1-%d) of the account\n" + "whose transactions you want to see:",
					theUser.numAccounts()));
			theAcct = sc.nextInt() - 1;
			if (theAcct < 0 || theAcct >= theUser.numAccounts())

			{
				LOGGER.error("Invalid account.Please try again. ");
			}

		} while (theAcct < 0 || theAcct >= theUser.numAccounts());

		// print the transaction history
		theUser.printAcctTransactionHistory(theAcct);

	}

	public static void transferFunds(User theUser, Scanner sc) {
		// inits
		int fromAcct;
		int toAcct;
		double amount;
		double acctBal;

		// get the account to transfer from
		do {
			LOGGER.info("Enter the number (1-%d) of the account:\n" + "to transfer from:");
			fromAcct = sc.nextInt() - 1;
			if (fromAcct < 0 || fromAcct >= theUser.numAccounts()) {
				LOGGER.error("Invalid account.Please try again.");
			}

		} while (fromAcct < 0 || fromAcct >= theUser.numAccounts());
		acctBal = theUser.getAcctBalance(fromAcct);

		// get the account to transfer to
		do {

			LOGGER.info("Enter the number (1-%d) of the account:\n" + "to transfer to:");
			toAcct = sc.nextInt() - 1;
			if (toAcct < 0 || toAcct >= theUser.numAccounts()) {
				LOGGER.error("Invalid account.Please try again.");
			}

		} while (toAcct < 0 || toAcct >= theUser.numAccounts());

		// get the amount to transfer
		do {
			LOGGER.info(String.format("Enter the amount to transfer (max $%.02f): $", acctBal));
			amount = sc.nextDouble();
			if (amount < 0) {
				LOGGER.info("Amount must be greater than zero.");

			} else if (amount > acctBal) {
				LOGGER.info("Amount must not be greater than\n" + "balance of $%.02f.\n", acctBal);
			}
		} while (amount < 0 || amount > acctBal);

		// finally do the transfer
		theUser.addAcctTransaction(fromAcct, -1 * amount,
				String.format("Transfer to account %s", theUser.getAcctUUID(toAcct)));
		theUser.addAcctTransaction(toAcct, amount,
				String.format("Transfer to account %s", theUser.getAcctUUID(fromAcct)));

	}

	public static void withdrawFunds(User theUser, Scanner sc) {
		// inits
		int fromAcct;
		double amount;
		double acctBal;
		String memo;
		// get the account to withdraw from
		do {
			LOGGER.info(String.format("Enter the number (1-%d) of the account:\n" + "to withdraw from:",
					theUser.numAccounts()));
			fromAcct = sc.nextInt() - 1;
			if (fromAcct < 0 || fromAcct >= theUser.numAccounts()) {
				LOGGER.error("Invalid account.Please try again.");
			}

		} while (fromAcct < 0 || fromAcct >= theUser.numAccounts());
		acctBal = theUser.getAcctBalance(fromAcct);

		// get the amount to transfer
		do {
			LOGGER.info(String.format("Enter the amount to withdraw (max $%.02f): $", acctBal));
			amount = sc.nextDouble();
			if (amount < 0) {
				LOGGER.info("Amount must be greater than zero.");

			} else if (amount > acctBal) {
				LOGGER.info(String.format("Amount must not be greater than\n" + "balance of $%.02f.\n", acctBal));
			}

		} while (amount < 0 || amount > acctBal);

		sc.nextLine();
		LOGGER.info("Enter the memo:");
		memo = sc.nextLine();

		// do the withdrawl
		theUser.addAcctTransaction(fromAcct, -1 * amount, memo);
	}

	public static void depositFunds(User theUser, Scanner sc) {

		// inits
		int toAcct;
		double amount;
		double acctBal;
		String memo;
		// get the account to deposit from
		do {
			LOGGER.info(String.format("Enter the number (1-%d) of the account:\n" + "to deposit in:",
					theUser.numAccounts()));
			toAcct = sc.nextInt() - 1;
			if (toAcct < 0 || toAcct >= theUser.numAccounts()) {
				LOGGER.error("Invalid account.Please try again.");
			}

		} while (toAcct < 0 || toAcct >= theUser.numAccounts());
		acctBal = theUser.getAcctBalance(toAcct);

		// get the amount to deposit
		do {
			LOGGER.info(String.format("Enter the amount to deposit (max $%.02f): $", acctBal));
			amount = sc.nextDouble();
			if (amount < 0) {
				LOGGER.info("Amount must be greater than zero.");

			}

		} while (amount < 0);

		sc.nextLine();
		LOGGER.info("Enter the memo:");
		memo = sc.nextLine();

		// do the deposit
		theUser.addAcctTransaction(toAcct, amount, memo);
	}

	private static void payBill(User theUser, Scanner sc) {
		// inits
		int fromAcct;
		double amount;
		double acctBal;
		String memo;
		// get the account to paybill from
		do {
			LOGGER.info(
					String.format("Enter the number (1-%d) of the account:\n" + "to pay from:", theUser.numAccounts()));
			fromAcct = sc.nextInt() - 1;
			if (fromAcct < 0 || fromAcct >= theUser.numAccounts()) {
				LOGGER.error("Invalid account.Please try again.");
			}

		} while (fromAcct < 0 || fromAcct >= theUser.numAccounts());
		acctBal = theUser.getAcctBalance(fromAcct);

		// get the amount to paybill
		do {
			LOGGER.info(String.format("Enter the amount to pay (max $%.02f): $", acctBal));
			amount = sc.nextDouble();
			if (amount < 0) {
				LOGGER.info("Amount must be greater than zero.");

			} else if (amount > acctBal) {
				LOGGER.info(String.format("Amount must not be greater than\n" + "balance of $%.02f.\n", acctBal));
			}

		} while (amount < 0 || amount > acctBal);

		sc.nextLine();
		LOGGER.info("Enter the memo:");
		memo = sc.nextLine();

		// do the paybill
		theUser.addAcctTransaction(fromAcct, -1 * amount, memo);
	}
}
