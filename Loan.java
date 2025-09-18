import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
public class Loan {
    private static final DateTimeFormatter DF = DateTimeFormatter.ISO_LOCAL_DATE;
    public String loanId;
    public String customerName;
    public double principal;
    public double annualRate;
    public int termMonths;
    public LocalDate startDate;
    public String status;
    public Loan(String loanId, String customerName, double principal, double annualRate, int termMonths, LocalDate startDate, String status) {
        this.loanId = loanId;
        this.customerName = customerName;
        this.principal = principal;
        this.annualRate = annualRate;
        this.termMonths = termMonths;
        this.startDate = startDate;
        this.status = status;
    }
    public static Loan fromCSV(String csvLine) {
        String[] parts = csvLine.split(",", -1);
        if (parts.length < 7) return null;
        try {
            return new Loan(
                parts[0],
                parts[1],
                Double.parseDouble(parts[2]),
                Double.parseDouble(parts[3]),
                Integer.parseInt(parts[4]),
                LocalDate.parse(parts[5], DF),
                parts[6]
            );
        } catch (Exception e) {
            return null;
        }
    }
    public String toCSV() {
        return String.join(",",
            safe(loanId), safe(customerName), String.valueOf(principal), String.valueOf(annualRate),
            String.valueOf(termMonths), startDate.format(DF), safe(status)
        );
    }
    private String safe(String s) {
        return s == null ? "" : s.replace(",", " ");
    }
    public double monthlyPayment() {
        double monthlyRate = (annualRate / 100.0) / 12.0;
        int n = termMonths;
        if (monthlyRate == 0) return principal / n;
        double denom = 1 - Math.pow(1 + monthlyRate, -n);
        return principal * (monthlyRate / denom);
    }
    @Override
    public String toString() {
        return String.format("ID: %s | Customer: %s | Principal: %.2f | Rate: %.2f%% | Term: %d mo | Start: %s | Status: %s | EMI: %.2f",
            loanId, customerName, principal, annualRate, termMonths, startDate.format(DF), status, monthlyPayment());
    }
}
