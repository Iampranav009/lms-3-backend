-- Database Setup with Real Student Data (CSE Format)
-- Run this script in Supabase SQL Editor

-- Drop existing tables in correct order (to avoid foreign key constraints)
DROP TABLE IF EXISTS book_issues CASCADE;
DROP TABLE IF EXISTS books CASCADE;
DROP TABLE IF EXISTS students CASCADE;

-- Recreate tables with proper structure
CREATE TABLE students (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    student_id TEXT UNIQUE NOT NULL,
    name TEXT NOT NULL,
    roll_number TEXT NOT NULL,
    branch TEXT NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

CREATE TABLE books (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    book_id TEXT UNIQUE NOT NULL,
    title TEXT NOT NULL,
    author TEXT NOT NULL,
    status TEXT DEFAULT 'Available' CHECK (status IN ('Available', 'Issued')),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

CREATE TABLE book_issues (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    student_id TEXT NOT NULL,
    book_id TEXT NOT NULL,
    issue_date DATE NOT NULL,
    return_date DATE NOT NULL,
    status TEXT DEFAULT 'Active' CHECK (status IN ('Active', 'Returned')),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    FOREIGN KEY (student_id) REFERENCES students(student_id) ON DELETE CASCADE,
    FOREIGN KEY (book_id) REFERENCES books(book_id) ON DELETE CASCADE
);

-- Create indexes for better performance
CREATE INDEX idx_students_student_id ON students(student_id);
CREATE INDEX idx_students_roll_number ON students(roll_number);
CREATE INDEX idx_books_book_id ON books(book_id);
CREATE INDEX idx_books_status ON books(status);
CREATE INDEX idx_book_issues_student_id ON book_issues(student_id);
CREATE INDEX idx_book_issues_book_id ON book_issues(book_id);
CREATE INDEX idx_book_issues_status ON book_issues(status);

-- Enable Row Level Security (RLS)
ALTER TABLE students ENABLE ROW LEVEL SECURITY;
ALTER TABLE books ENABLE ROW LEVEL SECURITY;
ALTER TABLE book_issues ENABLE ROW LEVEL SECURITY;

-- Create policies for public access
CREATE POLICY "Allow public read access to students" ON students
    FOR SELECT USING (true);

CREATE POLICY "Allow public insert access to students" ON students
    FOR INSERT WITH CHECK (true);

CREATE POLICY "Allow public read access to books" ON books
    FOR SELECT USING (true);

CREATE POLICY "Allow public insert access to books" ON books
    FOR INSERT WITH CHECK (true);

CREATE POLICY "Allow public update access to books" ON books
    FOR UPDATE USING (true);

CREATE POLICY "Allow public read access to book_issues" ON book_issues
    FOR SELECT USING (true);

CREATE POLICY "Allow public insert access to book_issues" ON book_issues
    FOR INSERT WITH CHECK (true);

CREATE POLICY "Allow public update access to book_issues" ON book_issues
    FOR UPDATE USING (true);

-- Insert real student data (CSE format)
INSERT INTO students (student_id, name, roll_number, branch) VALUES
('CSE1', 'Alisha Aslam Lodha', '1', 'Computer Science Engineering'),
('CSE2', 'Anushka Jayant Karoddeo', '2', 'Computer Science Engineering'),
('CSE3', 'Chetna Kishor Satpute', '3', 'Computer Science Engineering'),
('CSE4', 'Devanshi Ravindra Yeole', '4', 'Computer Science Engineering'),
('CSE5', 'Devashri Dharmendra Dudhe', '5', 'Computer Science Engineering'),
('CSE6', 'Dhruvi Rakesh Suchak', '6', 'Computer Science Engineering'),
('CSE7', 'Diya Anil Chavhan', '7', 'Computer Science Engineering'),
('CSE8', 'Hitakshi Ramdas Neware', '8', 'Computer Science Engineering'),
('CSE9', 'Janhavi Pramod Bhoyar', '9', 'Computer Science Engineering'),
('CSE10', 'Jayashri Gunuprasad Yadav', '10', 'Computer Science Engineering'),
('CSE11', 'Kanak Purushottam Shelke', '11', 'Computer Science Engineering'),
('CSE12', 'Khushi Ajay Yadav', '12', 'Computer Science Engineering'),
('CSE13', 'Khushi Mahendra Chaliwal', '13', 'Computer Science Engineering'),
('CSE14', 'Madiha Javed Lakhani', '14', 'Computer Science Engineering'),
('CSE15', 'Mahima Vaibhav Dhande', '15', 'Computer Science Engineering'),
('CSE16', 'Manjiri Giridhar Tupone', '16', 'Computer Science Engineering'),
('CSE17', 'Mrudula Chandrashekhar Kha', '17', 'Computer Science Engineering'),
('CSE18', 'Nakshatra Anant Bidwe', '18', 'Computer Science Engineering'),
('CSE19', 'Pranjali Suresh Jawade', '19', 'Computer Science Engineering'),
('CSE20', 'Pratiksha Vinod Jaiswal', '20', 'Computer Science Engineering'),
('CSE21', 'Rajnandini Deokrishna Jadhav', '21', 'Computer Science Engineering'),
('CSE22', 'Riya Sunil Sharma', '22', 'Computer Science Engineering'),
('CSE23', 'Sakshi Ajay Thakare', '23', 'Computer Science Engineering'),
('CSE24', 'Sanchita Vijay Giri', '24', 'Computer Science Engineering'),
('CSE25', 'Sanskruti Sachin Dhobale', '25', 'Computer Science Engineering'),
('CSE26', 'Sanskruti Vilas Gawande', '26', 'Computer Science Engineering'),
('CSE27', 'Shrawani Martand Badole', '27', 'Computer Science Engineering'),
('CSE28', 'Singida Avijit Dey', '28', 'Computer Science Engineering'),
('CSE29', 'Srushti Rajesh Gupta', '29', 'Computer Science Engineering'),
('CSE30', 'Tahreem Tamkanat Niyar Ahn', '30', 'Computer Science Engineering'),
('CSE31', 'Vedantii Pravin Bobade', '31', 'Computer Science Engineering'),
('CSE32', 'Yashshri Neminath Raulkar', '32', 'Computer Science Engineering'),
('CSE33', 'Yugandhara Santosh Mokashe', '33', 'Computer Science Engineering'),
('CSE34', 'Aadarsh Vikram Ade', '34', 'Computer Science Engineering'),
('CSE35', 'Aayush Kiran Uttarwar', '35', 'Computer Science Engineering'),
('CSE36', 'Aayush Sujit Waghade', '36', 'Computer Science Engineering'),
('CSE37', 'Aditya Dhanaraj Chavhan', '37', 'Computer Science Engineering'),
('CSE38', 'Aditya Swaminath Sah', '38', 'Computer Science Engineering'),
('CSE39', 'Amir Zia Aslam Khan', '39', 'Computer Science Engineering'),
('CSE40', 'Anurag Vinod Taksande', '40', 'Computer Science Engineering'),
('CSE41', 'Ashraf Wahed Bawani', '41', 'Computer Science Engineering'),
('CSE42', 'Ateeb Inayat Khan', '42', 'Computer Science Engineering'),
('CSE43', 'Chaitanya Ajay Satao', '43', 'Computer Science Engineering'),
('CSE44', 'Himanshu R. Yadav', '44', 'Computer Science Engineering'),
('CSE45', 'Krushna Dinesh Patalbansi', '45', 'Computer Science Engineering'),
('CSE46', 'Mayur Nandkishor Badiyani', '46', 'Computer Science Engineering'),
('CSE47', 'Neel Rupesh Thotage', '47', 'Computer Science Engineering'),
('CSE48', 'Piyush Hitesh Palan', '48', 'Computer Science Engineering'),
('CSE49', 'Pranav Shankar Shinde', '49', 'Computer Science Engineering'),
('CSE50', 'Prathamesh Sunil Yadav', '50', 'Computer Science Engineering'),
('CSE51', 'Priyansh Ritesh Sharma', '51', 'Computer Science Engineering'),
('CSE52', 'Purvesh Vijay Upasani', '52', 'Computer Science Engineering'),
('CSE53', 'Rishi Dinesh Jadhwani', '53', 'Computer Science Engineering'),
('CSE54', 'Rupesh Indal Jadhav', '54', 'Computer Science Engineering'),
('CSE55', 'Shrijeet A. Deshmukh', '55', 'Computer Science Engineering'),
('CSE56', 'Sujal Rameshrao Dandekar', '56', 'Computer Science Engineering'),
('CSE57', 'Suyash Jitendra Bagde', '57', 'Computer Science Engineering'),
('CSE58', 'Talaha Ather Khan Husain Kh', '58', 'Computer Science Engineering'),
('CSE59', 'Tushar Keshav Parhad', '59', 'Computer Science Engineering'),
('CSE60', 'Vinayak A. Maske', '60', 'Computer Science Engineering'),
('CSE61', 'Yash Gautam Meshram', '61', 'Computer Science Engineering'),
('CSE62', 'Prajakta P. Bhoyar', '62', 'Computer Science Engineering'),
('CSE63', 'Priya C. Kakde', '63', 'Computer Science Engineering'),
('CSE64', 'Dimple G. Nandardhane', '64', 'Computer Science Engineering'),
('CSE65', 'Pratik R. Pohekar', '65', 'Computer Science Engineering'),
('CSE66', 'Vivek D. Sartape', '66', 'Computer Science Engineering'),
('CSE67', 'Rohit R. Gole', '67', 'Computer Science Engineering'),
('CSE68', 'Anushka R. Patil', '68', 'Computer Science Engineering');

-- Insert demo books data
INSERT INTO books (book_id, title, author, status) VALUES
('BK001', 'Data Structures and Algorithms', 'Reema Thareja', 'Available'),
('BK002', 'Operating System Concepts', 'Abraham Silberschatz', 'Available'),
('BK003', 'Electrical Circuits', 'James W. Nilsson', 'Available'),
('BK004', 'Engineering Mechanics', 'S. Timoshenko', 'Available'),
('BK005', 'Thermodynamics: An Engineering Approach', 'Yunus A. Cengel', 'Available'),
('BK006', 'Mechanical Engineering Design', 'Joseph E. Shigley', 'Available'),
('BK007', 'Fluid Mechanics', 'P.N. Modi & S.M. Seth', 'Available'),
('BK008', 'Digital Logic and Computer Design', 'M. Morris Mano', 'Available'),
('BK009', 'Computer Networks', 'Andrew S. Tanenbaum', 'Available'),
('BK010', 'Microprocessor Architecture', 'Ramesh S. Gaonkar', 'Available'),
('BK011', 'Properties of Concrete', 'A.M. Neville', 'Available'),
('BK012', 'Chemical Reaction Engineering', 'Octave Levenspiel', 'Available'),
('BK013', 'Textile Manufacturing', 'Corbman', 'Available'),
('BK014', 'Higher Engineering Mathematics', 'B.S. Grewal', 'Available'),
('BK015', 'Database System Concepts', 'Abraham Silberschatz', 'Available'),
('BK016', 'Compilers: Principles, Techniques', 'Alfred V. Aho', 'Available'),
('BK017', 'Environmental Engineering', 'Peavy & Rowe', 'Available'),
('BK018', 'Electric Machinery', 'A.E. Fitzgerald', 'Available'),
('BK019', 'Structural Analysis', 'R.C. Hibbeler', 'Available'),
('BK020', 'Java: The Complete Reference', 'Herbert Schildt', 'Available');

-- Verify the data was inserted correctly
SELECT 'Students inserted:' as info, COUNT(*) as count FROM students;
SELECT 'Books inserted:' as info, COUNT(*) as count FROM books;
