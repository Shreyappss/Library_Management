package folio.library;

import java.time.LocalDate;
import java.util.*;

/**
 * Domain/service reference implementation for a future server-backed Folio API.
 * This in-memory class is intentionally independent of the browser demo.
 */
public final class LibraryService {
    public record Book(int id, String title, String author, String category, int copies) {}
    public record Loan(int bookId, String studentId, LocalDate borrowedOn, LocalDate dueOn) {}
    private final Map<Integer, Book> catalogue = new LinkedHashMap<>();
    private final Map<String, List<Loan>> loans = new HashMap<>();

    public LibraryService(Collection<Book> books) {
        for (Book book : books) catalogue.put(book.id(), book);
    }

    public synchronized Loan borrow(int bookId, String studentId, LocalDate today) {
        Book book = Optional.ofNullable(catalogue.get(bookId))
                .orElseThrow(() -> new IllegalArgumentException("Unknown book"));
        List<Loan> studentLoans = loans.computeIfAbsent(studentId, ignored -> new ArrayList<>());
        if (studentLoans.stream().anyMatch(loan -> loan.bookId() == bookId))
            throw new IllegalStateException("Book is already borrowed by this student");
        long checkedOut = loans.values().stream().flatMap(Collection::stream)
                .filter(loan -> loan.bookId() == bookId).count();
        if (checkedOut >= book.copies()) throw new IllegalStateException("No copies available");
        Loan loan = new Loan(bookId, studentId, today, today.plusDays(14));
        studentLoans.add(loan);
        return loan;
    }

    public synchronized boolean returnBook(int bookId, String studentId) {
        List<Loan> studentLoans = loans.get(studentId);
        return studentLoans != null && studentLoans.removeIf(loan -> loan.bookId() == bookId);
    }

    public synchronized List<Loan> activeLoans(String studentId) {
        return List.copyOf(loans.getOrDefault(studentId, List.of()));
    }
}
