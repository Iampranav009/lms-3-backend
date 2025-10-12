import com.sun.net.httpserver.*;
import java.io.*;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * Simple Java REST API Server for Library Management System
 * This provides Java backend functionality while keeping the web frontend
 */
public class SimpleJavaAPI {
    
    private static final int PORT = 8000;
    private static HttpServer server;
    
    public static void main(String[] args) {
        try {
            // Create HTTP server
            server = HttpServer.create(new InetSocketAddress(PORT), 0);
            
            // Create contexts for different API endpoints
            server.createContext("/api/books", new BooksHandler());
            server.createContext("/api/issue", new IssueHandler());
            server.createContext("/api/return", new ReturnHandler());
            server.createContext("/api/search", new SearchHandler());
            server.createContext("/api/student", new StudentHandler());
            server.createContext("/api/health", new HealthHandler());
            
            // Set executor for handling requests
            server.setExecutor(Executors.newFixedThreadPool(10));
            
            // Start the server
            server.start();
            
            System.out.println("🚀 Java Library Management API Server started!");
            System.out.println("📡 Server running on: http://localhost:" + PORT);
            System.out.println("📚 Available endpoints:");
            System.out.println("   GET  /api/books - Get all books");
            System.out.println("   POST /api/issue - Issue a book");
            System.out.println("   POST /api/return - Return a book");
            System.out.println("   GET  /api/search?q=query - Search books");
            System.out.println("   GET  /api/student?id=studentId - Get student info");
            System.out.println("   GET  /api/health - Health check");
            System.out.println("\n💡 Your web frontend can now call these Java APIs!");
            System.out.println("🔄 Press Ctrl+C to stop the server");
            
        } catch (IOException e) {
            System.err.println("❌ Failed to start server: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Utility class for handling JSON responses
     */
    static class JsonResponse {
        public static void sendJsonResponse(HttpExchange exchange, int statusCode, String json) throws IOException {
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
            exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type");
            
            byte[] response = json.getBytes(StandardCharsets.UTF_8);
            exchange.sendResponseHeaders(statusCode, response.length);
            OutputStream os = exchange.getResponseBody();
            os.write(response);
            os.close();
        }
        
        public static void sendErrorResponse(HttpExchange exchange, int statusCode, String error) throws IOException {
            String json = "{\"success\": false, \"error\": \"" + error + "\"}";
            sendJsonResponse(exchange, statusCode, json);
        }
        
        public static void sendSuccessResponse(HttpExchange exchange, String data) throws IOException {
            String json = "{\"success\": true, \"data\": " + data + "}";
            sendJsonResponse(exchange, 200, json);
        }
    }
    
    /**
     * Books Handler - Get all books
     */
    static class BooksHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("OPTIONS".equals(exchange.getRequestMethod())) {
                JsonResponse.sendJsonResponse(exchange, 200, "");
                return;
            }
            
            if (!"GET".equals(exchange.getRequestMethod())) {
                JsonResponse.sendErrorResponse(exchange, 405, "Method not allowed");
                return;
            }
            
            try {
                // Use existing Java logic to get books
                java.util.List<LibraryManagement.Book> books = LibraryManagement.getAllBooks();
                
                // Convert to JSON
                StringBuilder json = new StringBuilder("[");
                for (int i = 0; i < books.size(); i++) {
                    LibraryManagement.Book book = books.get(i);
                    if (i > 0) json.append(",");
                    json.append("{")
                        .append("\"bookId\":\"").append(book.getBookId()).append("\",")
                        .append("\"title\":\"").append(book.getTitle()).append("\",")
                        .append("\"author\":\"").append(book.getAuthor()).append("\",")
                        .append("\"status\":\"").append(book.getStatus()).append("\"")
                        .append("}");
                }
                json.append("]");
                
                JsonResponse.sendSuccessResponse(exchange, json.toString());
                
            } catch (Exception e) {
                JsonResponse.sendErrorResponse(exchange, 500, "Internal server error: " + e.getMessage());
            }
        }
    }
    
    /**
     * Issue Handler - Issue a book to a student
     */
    static class IssueHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("OPTIONS".equals(exchange.getRequestMethod())) {
                JsonResponse.sendJsonResponse(exchange, 200, "");
                return;
            }
            
            if (!"POST".equals(exchange.getRequestMethod())) {
                JsonResponse.sendErrorResponse(exchange, 405, "Method not allowed");
                return;
            }
            
            try {
                // Read request body
                InputStream requestBody = exchange.getRequestBody();
                String body = new String(requestBody.readAllBytes(), StandardCharsets.UTF_8);
                
                // Parse JSON (simple parsing)
                String studentId = extractJsonValue(body, "studentId");
                String bookId = extractJsonValue(body, "bookId");
                
                if (studentId == null || bookId == null) {
                    JsonResponse.sendErrorResponse(exchange, 400, "Missing studentId or bookId");
                    return;
                }
                
                // Use existing Java logic to issue book
                String result = LibraryManagement.issueBook(studentId, bookId);
                
                // Return result
                String json = "{\"message\":\"" + result + "\"}";
                JsonResponse.sendSuccessResponse(exchange, json);
                
            } catch (Exception e) {
                JsonResponse.sendErrorResponse(exchange, 500, "Internal server error: " + e.getMessage());
            }
        }
    }
    
    /**
     * Return Handler - Return a book
     */
    static class ReturnHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("OPTIONS".equals(exchange.getRequestMethod())) {
                JsonResponse.sendJsonResponse(exchange, 200, "");
                return;
            }
            
            if (!"POST".equals(exchange.getRequestMethod())) {
                JsonResponse.sendErrorResponse(exchange, 405, "Method not allowed");
                return;
            }
            
            try {
                // Read request body
                InputStream requestBody = exchange.getRequestBody();
                String body = new String(requestBody.readAllBytes(), StandardCharsets.UTF_8);
                
                // Parse JSON
                String studentId = extractJsonValue(body, "studentId");
                String bookId = extractJsonValue(body, "bookId");
                
                if (studentId == null || bookId == null) {
                    JsonResponse.sendErrorResponse(exchange, 400, "Missing studentId or bookId");
                    return;
                }
                
                // Use existing Java logic to return book
                String result = LibraryManagement.returnBook(studentId, bookId);
                
                // Return result
                String json = "{\"message\":\"" + result + "\"}";
                JsonResponse.sendSuccessResponse(exchange, json);
                
            } catch (Exception e) {
                JsonResponse.sendErrorResponse(exchange, 500, "Internal server error: " + e.getMessage());
            }
        }
    }
    
    /**
     * Search Handler - Search books
     */
    static class SearchHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("OPTIONS".equals(exchange.getRequestMethod())) {
                JsonResponse.sendJsonResponse(exchange, 200, "");
                return;
            }
            
            if (!"GET".equals(exchange.getRequestMethod())) {
                JsonResponse.sendErrorResponse(exchange, 405, "Method not allowed");
                return;
            }
            
            try {
                // Get query parameter
                String query = exchange.getRequestURI().getQuery();
                String searchQuery = null;
                
                if (query != null) {
                    String[] params = query.split("&");
                    for (String param : params) {
                        if (param.startsWith("q=")) {
                            searchQuery = param.substring(2);
                            break;
                        }
                    }
                }
                
                if (searchQuery == null || searchQuery.isEmpty()) {
                    JsonResponse.sendErrorResponse(exchange, 400, "Missing search query parameter 'q'");
                    return;
                }
                
                // Use existing Java logic to search books
                java.util.List<LibraryManagement.Book> books = LibraryManagement.searchBooks(searchQuery);
                
                // Convert to JSON
                StringBuilder json = new StringBuilder("[");
                for (int i = 0; i < books.size(); i++) {
                    LibraryManagement.Book book = books.get(i);
                    if (i > 0) json.append(",");
                    json.append("{")
                        .append("\"bookId\":\"").append(book.getBookId()).append("\",")
                        .append("\"title\":\"").append(book.getTitle()).append("\",")
                        .append("\"author\":\"").append(book.getAuthor()).append("\",")
                        .append("\"status\":\"").append(book.getStatus()).append("\"")
                        .append("}");
                }
                json.append("]");
                
                JsonResponse.sendSuccessResponse(exchange, json.toString());
                
            } catch (Exception e) {
                JsonResponse.sendErrorResponse(exchange, 500, "Internal server error: " + e.getMessage());
            }
        }
    }
    
    /**
     * Student Handler - Get student information
     */
    static class StudentHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("OPTIONS".equals(exchange.getRequestMethod())) {
                JsonResponse.sendJsonResponse(exchange, 200, "");
                return;
            }
            
            if (!"GET".equals(exchange.getRequestMethod())) {
                JsonResponse.sendErrorResponse(exchange, 405, "Method not allowed");
                return;
            }
            
            try {
                // Get student ID from query parameter
                String query = exchange.getRequestURI().getQuery();
                String studentId = null;
                
                if (query != null) {
                    String[] params = query.split("&");
                    for (String param : params) {
                        if (param.startsWith("id=")) {
                            studentId = param.substring(3);
                            break;
                        }
                    }
                }
                
                if (studentId == null || studentId.isEmpty()) {
                    JsonResponse.sendErrorResponse(exchange, 400, "Missing student ID parameter 'id'");
                    return;
                }
                
                // Use existing Java logic to get student
                LibraryManagement.Student student = LibraryManagement.authenticateStudent(studentId);
                
                if (student == null) {
                    JsonResponse.sendErrorResponse(exchange, 404, "Student not found");
                    return;
                }
                
                // Convert to JSON
                String json = "{"
                    + "\"studentId\":\"" + student.getStudentId() + "\","
                    + "\"name\":\"" + student.getName() + "\","
                    + "\"rollNumber\":\"" + student.getRollNumber() + "\","
                    + "\"branch\":\"" + student.getBranch() + "\""
                    + "}";
                
                JsonResponse.sendSuccessResponse(exchange, json);
                
            } catch (Exception e) {
                JsonResponse.sendErrorResponse(exchange, 500, "Internal server error: " + e.getMessage());
            }
        }
    }
    
    /**
     * Health Handler - Check if server is running
     */
    static class HealthHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("OPTIONS".equals(exchange.getRequestMethod())) {
                JsonResponse.sendJsonResponse(exchange, 200, "");
                return;
            }
            
            String json = "{\"status\":\"healthy\",\"server\":\"Java Library Management API\",\"timestamp\":\"" + 
                         java.time.Instant.now().toString() + "\"}";
            JsonResponse.sendSuccessResponse(exchange, json);
        }
    }
    
    /**
     * Simple JSON value extractor
     */
    private static String extractJsonValue(String json, String key) {
        Pattern pattern = Pattern.compile("\"" + key + "\"\\s*:\\s*\"([^\"]+)\"");
        Matcher matcher = pattern.matcher(json);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }
    
    /**
     * Shutdown hook to gracefully stop the server
     */
    static {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (server != null) {
                System.out.println("\n🛑 Shutting down Java API server...");
                server.stop(0);
                System.out.println("✅ Server stopped successfully!");
            }
        }));
    }
}
