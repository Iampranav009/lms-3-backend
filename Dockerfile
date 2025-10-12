FROM openjdk:17-slim

WORKDIR /app

# Copy all Java files
COPY *.java ./
COPY *.sql ./

# Compile Java files
RUN javac SimpleJavaAPI.java LibraryManagement.java

# Expose port
EXPOSE 8000

# Run the application
CMD ["java", "SimpleJavaAPI"]

