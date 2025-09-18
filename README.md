# Loan Management System (Java)

Small Java project to manage simple loan records stored in a CSV file. The project provides basic operations: add, list, find, update status, delete, and persistent storage to `loans.csv`.

## Features
- Add, list, find, update status and delete loans
- Simple CSV persistence (`loans.csv`)
- Predictable iteration order using LinkedHashMap
- Minimal, easy-to-read code suitable for learning and small use cases

## Prerequisites
- Java 8+ (recommended Java 11 or newer)
- Git (optional, for cloning/forking)

## Build & Run (CLI)
1. Open PowerShell or Command Prompt in project root:
2. Compile:
   ```
   javac *.java
   ```
3. Run (replace MainClass with your actual entry point, e.g. Main or App):
   ```
   java MainClass
   ```
4. The application reads/writes `loans.csv` in the project directory.

## Usage (programmatic)
Example using the provided LoanManager:
```java
LoanManager manager = new LoanManager();
Loan loan = new Loan("12", "harshad", 12000.0, 5.6, 5, LocalDate.parse("2025-09-17"), "ACTIVE");
manager.addLoan(loan);
Collection<Loan> loans = manager.listLoans();
manager.updateStatus("12", "CLOSED");
manager.delete("12");
```

Sample CSV row (one line per loan):
```
12,harshad,12000.0,5.6,5,2025-09-17,ACTIVE
```

## Project layout
- LoanManager.java — core manager that loads/saves loans
- Loan.java — model class (expects toCSV() and fromCSV(String) helpers)
- loans.csv — CSV storage (created automatically)
- README.md — this file

## Tips
- If you get permission errors when pushing to a remote, push to your fork or add the correct account as a collaborator.
- Backup `loans.csv` before mass edits.

## Contributing
1. Fork the repository.
2. Create a feature branch.
3. Open a pull request with a clear description.

## License
Add a license file (e.g. MIT) or update this README with the chosen license.
