CREATE TABLE seating_chart (
    floor_seat_seq INT PRIMARY KEY AUTO_INCREMENT,
    floor_no INT NOT NULL,
    seat_no INT NOT NULL,
    is_occupied BOOLEAN DEFAULT FALSE
);

CREATE TABLE employee (
    emp_id VARCHAR(5) PRIMARY KEY, 
    name VARCHAR(20),
    email VARCHAR(50), 
    floor_seat_seq INT, 
    FOREIGN KEY (floor_seat_seq) REFERENCES seating_chart(floor_seat_seq)
);
