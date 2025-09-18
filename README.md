# Loan Management System (Java)

Small, self-contained Java project to manage loan records persisted as CSV. Provides basic operations: add, list, find, update status, delete, and simple CSV persistence.

## Status
Prototype / learning project — single-file storage (loans.csv) and a minimal manager class (LoanManager.java).

## Features
- Add, list, find, update status and delete loans
- CSV persistence (`loans.csv`)
- Predictable iteration order via LinkedHashMap

## Prerequisites
- Java 8+ (Java 11+ recommended)
- Git (optional)

## Quick start (CLI)
1. Open PowerShell in the project root:
2. Compile:
   ```
   javac *.java
   ```
3. Run (replace `MainClass` with your actual entry-point class if different):
   ```
   java MainClass
   ```

## CSV format
One loan per line, comma-separated:
- loanId, borrowerName, amount, interestRate, termMonths, startDate(YYYY-MM-DD), status

Example:
```
12,harshad,12000.0,5.6,5,2025-09-17,ACTIVE
```

## Usage (example)
```java
LoanManager manager = new LoanManager();
Loan loan = new Loan("12","harshad",12000.0,5.6,5,LocalDate.parse("2025-09-17"),"ACTIVE");
manager.addLoan(loan);
manager.listLoans();
```

## Contributing
- Fork the repo, create a topic branch, open a PR.
- Keep CSV backward-compatible when changing fields.

## Troubleshooting
- If push fails with 403: push to your fork or ensure your GitHub account has permission. Use Windows Credential Manager to remove wrong saved credentials if needed.

## License
Add a LICENSE file (e.g., MIT) or state the preferred license here.
