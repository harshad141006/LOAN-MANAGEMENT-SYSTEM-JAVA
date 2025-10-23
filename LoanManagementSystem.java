import java.time.LocalDate;
import java.util.*;
import java.nio.file.*;
import java.io.*;

public class LoanManagementSystem {
    private static LoanManager manager = new LoanManager();
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=== Loan Management System ===");
        loop: while (true) {
            printMenu();
            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1": createLoan(); break;
                case "2": listLoans(); break;
                case "3": searchLoan(); break;
                case "4": updateStatus(); break;    
                case "5": deleteLoan(); break;
                case "6": exportReport(); break;
                case "0":  
                    System.out.println("Goodbye.");
                    break loop;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("1) Add loan");
        System.out.println("2) List loans");
        System.out.println("3) Search loan by ID");
        System.out.println("4) Update loan status");
        System.out.println("5) Delete loan");
        System.out.println("6) Export report (CSV)");
        System.out.println("0) Exit");
        System.out.print("Choose: ");
    }

    private static void createLoan() {
        System.out.print("Enter Loan ID: ");
        String id = sc.nextLine().trim();
        if (id.isEmpty()) { System.out.println("Loan ID cannot be empty."); return; }
        if (manager.find(id) != null) { System.out.println("Loan with this ID already exists."); return; }

        System.out.print("Customer name: ");
        String name = sc.nextLine().trim();

        double principal = readDouble("Principal amount: ");
        double rate = readDouble("Annual interest rate (percent): ");
        int months = readInt("Term (months): ");
        LocalDate start = readDate("Start date (YYYY-MM-DD) [leave empty for today]: ");
        if (start == null) start = LocalDate.now();

        Loan loan = new Loan(id, name, principal, rate, months, start, "ACTIVE");
        manager.addLoan(loan);
        System.out.println("Loan added. EMI: " + String.format("%.2f", loan.monthlyPayment()));
    }

    private static void listLoans() {
        Collection<Loan> loans = manager.listLoans();
        if (loans.isEmpty()) {
            System.out.println("No loans found.");
            return;
        }
        for (Loan l : loans) System.out.println(l);
    }

    private static void searchLoan() {
        System.out.print("Enter Loan ID: ");
        String id = sc.nextLine().trim();
        Loan l = manager.find(id);
        if (l == null) System.out.println("Loan not found.");
        else System.out.println(l);
    }

    private static void updateStatus() {
        System.out.print("Enter Loan ID: ");
        String id = sc.nextLine().trim();
        Loan l = manager.find(id);
        if (l == null) { System.out.println("Loan not found."); return; }
        System.out.println("Current: " + l.status);
        System.out.print("New status (ACTIVE/CLOSED/DEFAULTED): ");
        String st = sc.nextLine().trim();
        if (st.isEmpty()) { System.out.println("No change."); return; }
        if (manager.updateStatus(id, st.toUpperCase())) System.out.println("Status updated.");
        else System.out.println("Failed to update.");
    }

    private static void deleteLoan() {
        System.out.print("Enter Loan ID: ");
        String id = sc.nextLine().trim();
        System.out.print("Are you sure? Type YES to confirm: ");
        String c = sc.nextLine().trim();
        if (!"YES".equalsIgnoreCase(c)) { System.out.println("Cancelled."); return; }
        if (manager.delete(id)) System.out.println("Deleted.");
        else System.out.println("Loan not found.");
    }

    private static void exportReport() {
        System.out.print("Export filename (default report.csv): ");
        String f = sc.nextLine().trim();
        if (f.isEmpty()) f = "report.csv";
        try (BufferedWriter w = Files.newBufferedWriter(Paths.get(f))) {
            w.write("LoanID,Customer,Principal,AnnualRate,TermMonths,StartDate,Status,EMI\n");
            for (Loan l : manager.listLoans()) {
                w.write(String.format("%s,%s,%.2f,%.2f,%d,%s,%s,%.2f\n",
                    l.loanId, l.customerName.replace(",", " "), l.principal, l.annualRate,
                    l.termMonths, l.startDate.toString(), l.status, l.monthlyPayment()));
            }
            System.out.println("Exported to " + f);
        } catch (IOException e) {
            System.err.println("Failed to export: " + e.getMessage());
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine().trim();
            try { return Double.parseDouble(s); } catch (Exception e) { System.out.println("Invalid number, try again."); }
        }
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine().trim();
            try { return Integer.parseInt(s); } catch (Exception e) { System.out.println("Invalid integer, try again."); }
        }
    }

    private static LocalDate readDate(String prompt) {
        System.out.print(prompt);
        String s = sc.nextLine().trim();
        if (s.isEmpty()) return null;
        try { return LocalDate.parse(s); }
        catch (Exception e) { System.out.println("Bad date format. Use YYYY-MM-DD."); return readDate(prompt); }
    }

    public boolean updateStatus(String loanId, String newStatus) {
        Loan loan = find(loanId);
        if (loan == null) return false;
        loan.setStatus(newStatus);
        if (newStatus.equals("CLOSED")) {
            loan.setCibilScore(Math.min(900, loan.getCibilScore() + 20));
        } else if (newStatus.equals("DEFAULTED")) {
            loan.setCibilScore(Math.max(300, loan.getCibilScore() - 50));
        }
        return true;
    }
}
