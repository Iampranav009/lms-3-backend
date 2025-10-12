import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Library Management System - Core Business Logic
 * This class handles all library operations including student authentication,
 * book management, and issue/return functionality.
 */
public class LibraryManagement {
    
    // Data storage using HashMaps
    private static Map<String, Student> students = new HashMap<>();
    private static Map<String, Book> books = new HashMap<>();
    private static Map<String, List<BookIssue>> issuedBooks = new HashMap<>();
    
    // Static initializer to load demo data
    static {
        initializeStudents();
        initializeBooks();
    }
    
    /**
     * Initialize students with real CSE student data
     * Matching the database-clean.sql student records
     */
    private static void initializeStudents() {
        // Sample students from CSE batch (matching database)
        students.put("CSE45", new Student("Krushna Dinesh Patalbansi", "45", "Computer Science Engineering", "CSE45"));
        students.put("CSE46", new Student("Mayur Nandkishor Badiyani", "46", "Computer Science Engineering", "CSE46"));
        students.put("CSE47", new Student("Neel Rupesh Thotage", "47", "Computer Science Engineering", "CSE47"));
        students.put("CSE48", new Student("Piyush Hitesh Palan", "48", "Computer Science Engineering", "CSE48"));
        students.put("CSE49", new Student("Pranav Shankar Shinde", "49", "Computer Science Engineering", "CSE49"));
        students.put("CSE50", new Student("Prathamesh Sunil Yadav", "50", "Computer Science Engineering", "CSE50"));
        students.put("CSE51", new Student("Priyansh Ritesh Sharma", "51", "Computer Science Engineering", "CSE51"));
        
        // Add more students as needed
        students.put("CSE1", new Student("Alisha Aslam Lodha", "1", "Computer Science Engineering", "CSE1"));
        students.put("CSE2", new Student("Anushka Jayant Karoddeo", "2", "Computer Science Engineering", "CSE2"));
        students.put("CSE3", new Student("Chetna Kishor Satpute", "3", "Computer Science Engineering", "CSE3"));
    }
    
    /**
     * Initialize books catalog
     */
    private static void initializeBooks() {
        books.put("BK001", new Book("BK001", "Data Structures and Algorithms", "Reema Thareja", "Available"));
        books.put("BK002", new Book("BK002", "Operating System Concepts", "Abraham Silberschatz", "Available"));
        books.put("BK003", new Book("BK003", "Electrical Circuits", "James W. Nilsson", "Available"));
        books.put("BK004", new Book("BK004", "Engineering Mechanics", "S. Timoshenko", "Available"));
        books.put("BK005", new Book("BK005", "Thermodynamics: An Engineering Approach", "Yunus A. Cengel", "Available"));
        books.put("BK006", new Book("BK006", "Mechanical Engineering Design", "Joseph E. Shigley", "Available"));
        books.put("BK007", new Book("BK007", "Fluid Mechanics", "P.N. Modi & S.M. Seth", "Available"));
        books.put("BK008", new Book("BK008", "Digital Logic and Computer Design", "M. Morris Mano", "Available"));
        books.put("BK009", new Book("BK009", "Computer Networks", "Andrew S. Tanenbaum", "Available"));
        books.put("BK010", new Book("BK010", "Microprocessor Architecture", "Ramesh S. Gaonkar", "Available"));
        books.put("BK011", new Book("BK011", "Properties of Concrete", "A.M. Neville", "Available"));
        books.put("BK012", new Book("BK012", "Chemical Reaction Engineering", "Octave Levenspiel", "Available"));
        books.put("BK013", new Book("BK013", "Textile Manufacturing", "Corbman", "Available"));
        books.put("BK014", new Book("BK014", "Higher Engineering Mathematics", "B.S. Grewal", "Available"));
        books.put("BK015", new Book("BK015", "Database System Concepts", "Abraham Silberschatz", "Available"));
        books.put("BK016", new Book("BK016", "Compilers: Principles, Techniques", "Alfred V. Aho", "Available"));
        books.put("BK017", new Book("BK017", "Environmental Engineering", "Peavy & Rowe", "Available"));
        books.put("BK018", new Book("BK018", "Electric Machinery", "A.E. Fitzgerald", "Available"));
        books.put("BK019", new Book("BK019", "Structural Analysis", "R.C. Hibbeler", "Available"));
        books.put("BK020", new Book("BK020", "Java: The Complete Reference", "Herbert Schildt", "Available"));
    }
    
    /**
     * Authenticate a student by their student ID
     * @param studentId The student's ID (e.g., CSE45)
     * @return Student object if found, null otherwise
     */
    public static Student authenticateStudent(String studentId) {
        return students.get(studentId);
    }
    
    /**
     * Get all available books
     * @return List of available books
     */
    public static List<Book> getAvailableBooks() {
        List<Book> availableBooks = new ArrayList<>();
        for (Book book : books.values()) {
            if ("Available".equals(book.getStatus())) {
                availableBooks.add(book);
            }
        }
        return availableBooks;
    }
    
    /**
     * Get all books in the library
     * @return List of all books
     */
    public static List<Book> getAllBooks() {
        return new ArrayList<>(books.values());
    }
    
    /**
     * Search books by title or author
     * @param query Search query string
     * @return List of matching books
     */
    public static List<Book> searchBooks(String query) {
        List<Book> results = new ArrayList<>();
        String lowerQuery = query.toLowerCase();
        
        for (Book book : books.values()) {
            if (book.getTitle().toLowerCase().contains(lowerQuery) || 
                book.getAuthor().toLowerCase().contains(lowerQuery)) {
                results.add(book);
            }
        }
        return results;
    }
    
    /**
     * Issue a book to a student
     * @param studentId Student's ID
     * @param bookId Book's ID
     * @return Result message
     */
    public static String issueBook(String studentId, String bookId) {
        Student student = students.get(studentId);
        Book book = books.get(bookId);
        
        // Validation
        if (student == null) {
            return "Student not found!";
        }
        if (book == null) {
            return "Book not found!";
        }
        if (!"Available".equals(book.getStatus())) {
            return "Book is not available!";
        }
        
        // Check if student has reached maximum limit (5 books)
        List<BookIssue> studentBooks = issuedBooks.getOrDefault(studentId, new ArrayList<>());
        if (studentBooks.size() >= 5) {
            return "Maximum limit of 5 books reached!";
        }
        
        // Issue the book
        book.setStatus("Issued");
        LocalDate issueDate = LocalDate.now();
        LocalDate returnDate = issueDate.plusMonths(6); // 6-month loan period
        
        BookIssue bookIssue = new BookIssue(studentId, bookId, issueDate, returnDate);
        studentBooks.add(bookIssue);
        issuedBooks.put(studentId, studentBooks);
        
        return "Book issued successfully! Return by: " + 
               returnDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }
    
    /**
     * Return a book from a student
     * @param studentId Student's ID
     * @param bookId Book's ID
     * @return Result message
     */
    public static String returnBook(String studentId, String bookId) {
        Student student = students.get(studentId);
        Book book = books.get(bookId);
        
        // Validation
        if (student == null) {
            return "Student not found!";
        }
        if (book == null) {
            return "Book not found!";
        }
        
        // Check if student has this book
        List<BookIssue> studentBooks = issuedBooks.get(studentId);
        if (studentBooks == null || studentBooks.isEmpty()) {
            return "No books issued to this student!";
        }
        
        // Find and remove the book issue
        boolean found = false;
        Iterator<BookIssue> iterator = studentBooks.iterator();
        while (iterator.hasNext()) {
            BookIssue issue = iterator.next();
            if (issue.getBookId().equals(bookId)) {
                iterator.remove();
                found = true;
                break;
            }
        }
        
        if (!found) {
            return "This book is not issued to this student!";
        }
        
        // Mark book as available
        book.setStatus("Available");
        
        // Check for overdue books
        LocalDate today = LocalDate.now();
        for (BookIssue issue : studentBooks) {
            if (today.isAfter(issue.getReturnDate())) {
                return "Book returned successfully! Note: You have overdue books.";
            }
        }
        
        return "Book returned successfully!";
    }
    
    /**
     * Get all books issued to a student
     * @param studentId Student's ID
     * @return List of issued books
     */
    public static List<BookIssue> getIssuedBooks(String studentId) {
        return issuedBooks.getOrDefault(studentId, new ArrayList<>());
    }
    
    /**
     * Get all issued books in the system
     * @return Map of all issued books
     */
    public static Map<String, List<BookIssue>> getAllIssuedBooks() {
        return issuedBooks;
    }
    
    /**
     * Get a student by ID
     * @param studentId Student's ID
     * @return Student object
     */
    public static Student getStudent(String studentId) {
        return students.get(studentId);
    }
    
    /**
     * Get a book by ID
     * @param bookId Book's ID
     * @return Book object
     */
    public static Book getBook(String bookId) {
        return books.get(bookId);
    }
    
    // Inner Classes
    
    /**
     * Student class representing a student in the system
     */
    public static class Student {
        private String name;
        private String rollNumber;
        private String branch;
        private String studentId;
        
        public Student(String name, String rollNumber, String branch, String studentId) {
            this.name = name;
            this.rollNumber = rollNumber;
            this.branch = branch;
            this.studentId = studentId;
        }
        
        public String getName() { return name; }
        public String getRollNumber() { return rollNumber; }
        public String getBranch() { return branch; }
        public String getStudentId() { return studentId; }
    }
    
    /**
     * Book class representing a book in the library
     */
    public static class Book {
        private String bookId;
        private String title;
        private String author;
        private String status;
        
        public Book(String bookId, String title, String author, String status) {
            this.bookId = bookId;
            this.title = title;
            this.author = author;
            this.status = status;
        }
        
        public String getBookId() { return bookId; }
        public String getTitle() { return title; }
        public String getAuthor() { return author; }
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
    }
    
    /**
     * BookIssue class representing a book issue transaction
     */
    public static class BookIssue {
        private String studentId;
        private String bookId;
        private LocalDate issueDate;
        private LocalDate returnDate;
        
        public BookIssue(String studentId, String bookId, LocalDate issueDate, LocalDate returnDate) {
            this.studentId = studentId;
            this.bookId = bookId;
            this.issueDate = issueDate;
            this.returnDate = returnDate;
        }
        
        public String getStudentId() { return studentId; }
        public String getBookId() { return bookId; }
        public LocalDate getIssueDate() { return issueDate; }
        public LocalDate getReturnDate() { return returnDate; }
    }
}
