# Loan Management System (Java)

A small, easy-to-read Java application that manages loan records persisted to a CSV file. Designed as a learning / prototype project with a minimal API to add, list, find, update, and delete loans.

## Key features
- Simple CSV persistence (loans.csv)
- Basic CRUD operations via LoanManager
- Deterministic ordering using LinkedHashMap
- Small codebase intended for learning and extension

## Prerequisites
- Java 8 or newer (Java 11+ recommended)
- Git (optional)
- Windows PowerShell or Command Prompt (examples below use PowerShell)

## Quick start (compile & run)
1. Open PowerShell in the project root:
2. Compile:
   ```
   javac *.java
   ```
3. Run (replace `MainClass` with your actual entry point if different):
   ```
   java MainClass
   ```

## CSV storage format
Each loan is one CSV line with these fields (comma-separated):
- loanId, borrowerName, amount, interestRate, termMonths, startDate(YYYY-MM-DD), status

Example line:
```
12,harshad,12000.0,5.6,5,2025-09-17,ACTIVE
```

## Usage (programmatic example)
```java
LoanManager manager = new LoanManager();          // loads loans.csv if present
Loan loan = new Loan("12","harshad",12000.0,5.6,5, LocalDate.parse("2025-09-17"), "ACTIVE");
manager.addLoan(loan);                            // adds and saves
Collection<Loan> all = manager.listLoans();       // read all loans
manager.updateStatus("12", "CLOSED");             // modify and save
manager.delete("12");                             // remove and save
```

## Project layout
- Loan.java — model for a loan (CSV serialization helpers)
- LoanManager.java — load/save and CRUD logic
- loans.csv — runtime data file created/updated by the app
- README.md — this document

## Contributing
1. Fork the repository.
2. Create a feature branch: `git checkout -b feature/your-feature`
3. Commit and push to your fork.
4. Open a Pull Request describing the change.

## Troubleshooting & tips
- If push fails with 403: push to your fork or ensure the GitHub account you use has write access. Remove stale credentials via Windows Credential Manager if Git prompts for the wrong account.
- Back up `loans.csv` before bulk edits.

## License

