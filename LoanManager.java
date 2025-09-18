
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class LoanManager {
    private Map<String, Loan> loans = new LinkedHashMap<>();
    private Path storage = Paths.get("loans.csv");

    public LoanManager() {
        load();
    }

    public void addLoan(Loan loan) {
        loans.put(loan.loanId, loan);
        save();
    }

    public Collection<Loan> listLoans() {
        return loans.values();
    }

    public Loan find(String loanId) {
        return loans.get(loanId);
    }

    public boolean updateStatus(String loanId, String status) {
        Loan l = loans.get(loanId);
        if (l == null) return false;
        l.status = status;
        save();
        return true;
    }

    public boolean delete(String loanId) {
        if (loans.remove(loanId) != null) {
            save();
            return true;
        }
        return false;
    }

    public void save() {
        try (BufferedWriter w = Files.newBufferedWriter(storage)) {
            for (Loan l : loans.values()) {
                w.write(l.toCSV());
                w.newLine();
            }
        } catch (IOException e) {
            System.err.println("Failed to save loans: " + e.getMessage());
        }
    }

    public void load() {
        loans.clear();
        if (!Files.exists(storage)) return;
        try (BufferedReader r = Files.newBufferedReader(storage)) {
            String line;
            while ((line = r.readLine()) != null) {
                Loan l = Loan.fromCSV(line);
                if (l != null) loans.put(l.loanId, l);
            }
        } catch (IOException e) {
            System.err.println("Failed to load loans: " + e.getMessage());
        }
    }
}
