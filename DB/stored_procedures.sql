DELIMITER $$

-- 1. 新增員工資料
CREATE PROCEDURE insert_employee(
    IN p_emp_id VARCHAR(5),
    IN p_name VARCHAR(20),
    IN p_email VARCHAR(50),
    IN p_floor_seat_seq INT
)
BEGIN
    INSERT INTO employee (emp_id, name, email, floor_seat_seq)
    VALUES (p_emp_id, p_name, p_email, p_floor_seat_seq);
END $$

-- 2. 修改員工資料
CREATE PROCEDURE update_employee(
    IN p_emp_id VARCHAR(5),
    IN p_name VARCHAR(20),
    IN p_email VARCHAR(50),
    IN p_floor_seat_seq INT
)
BEGIN
    UPDATE employee
    SET name = p_name, 
        email = p_email, 
        floor_seat_seq = p_floor_seat_seq
    WHERE emp_id = p_emp_id;
END $$

-- 3. 刪除員工資料
CREATE PROCEDURE delete_employee(
    IN p_emp_id VARCHAR(5)
)
BEGIN
    DELETE FROM employee WHERE emp_id = p_emp_id;
END $$

-- 4. 查詢所有員工資料
CREATE PROCEDURE get_all_employees()
BEGIN
    SELECT emp_id, name, email, floor_seat_seq FROM employee;
END $$

-- 5. 根據員工編號查詢員工資料
CREATE PROCEDURE get_employee_by_id(
    IN p_emp_id VARCHAR(5)
)
BEGIN
    SELECT emp_id, name, email, floor_seat_seq
    FROM employee
    WHERE emp_id = p_emp_id;
END $$

-- 6. 查詢所有座位資料
CREATE PROCEDURE get_all_seating_chart()
BEGIN
    SELECT floor_seat_seq, floor_no, seat_no, is_occupied
    FROM seating_chart;
END $$

-- 7. 根據座位序號查詢座位資料
CREATE PROCEDURE get_seating_by_floor_seat_seq(
    IN p_floor_seat_seq INT
)
BEGIN
    SELECT floor_seat_seq, floor_no, seat_no, is_occupied
    FROM seating_chart
    WHERE floor_seat_seq = p_floor_seat_seq;
END $$

-- 8. 新增座位資料
CREATE PROCEDURE insert_seating_chart(
    IN p_floor_seat_seq INT,
    IN p_floor_no INT,
    IN p_seat_no INT,
    IN p_is_occupied BOOLEAN
)
BEGIN
    INSERT INTO seating_chart (floor_seat_seq, floor_no, seat_no, is_occupied)
    VALUES (p_floor_seat_seq, p_floor_no, p_seat_no, p_is_occupied);
END $$

-- 9. 修改座位資料，並更新員工資料
CREATE PROCEDURE update_seating_chart(
    IN p_floor_seat_seq INT,
    IN p_floor_no INT,
    IN p_seat_no INT,
    IN p_is_occupied BOOLEAN
)
BEGIN
    DECLARE current_emp_id VARCHAR(255);

    -- 先檢查該座位是否已被佔用
    SELECT emp_id INTO current_emp_id
    FROM employee
    WHERE floor_seat_seq = p_floor_seat_seq;
    
    -- 如果該座位已被佔用，更新員工的座位資料為 NULL
    IF current_emp_id IS NOT NULL THEN
        UPDATE employee
        SET floor_seat_seq = NULL
        WHERE emp_id = current_emp_id;
    END IF;

    -- 更新座位資料
    UPDATE seating_chart
    SET floor_no = p_floor_no, 
        seat_no = p_seat_no, 
        is_occupied = p_is_occupied
    WHERE floor_seat_seq = p_floor_seat_seq;
END $$


-- 10. 刪除座位資料，並更新員工資料
CREATE PROCEDURE delete_seating_chart(
    IN p_floor_seat_seq INT
)
BEGIN
    DECLARE current_emp_id VARCHAR(255);

    -- 先檢查該座位是否已被佔用
    SELECT emp_id INTO current_emp_id
    FROM employee
    WHERE floor_seat_seq = p_floor_seat_seq;
    
    -- 如果該座位已被佔用，更新員工的座位資料為 NULL
    IF current_emp_id IS NOT NULL THEN
        UPDATE employee
        SET floor_seat_seq = NULL
        WHERE emp_id = current_emp_id;
    END IF;

    -- 刪除座位資料
    DELETE FROM seating_chart WHERE floor_seat_seq = p_floor_seat_seq;
END $$

-- 11. 座位標記為已佔用
CREATE PROCEDURE update_seat_status_to_available(
	IN p_floor_seat_seq INT
)
BEGIN
    UPDATE seating_chart
    SET is_occupied = FALSE
    WHERE floor_seat_seq = p_floor_seat_seq;
END $$

-- 12. 座位標記為可用
CREATE PROCEDURE update_seat_status_to_occupied(
	IN p_floor_seat_seq INT
)
BEGIN
    UPDATE seating_chart
    SET is_occupied = TRUE
    WHERE floor_seat_seq = p_floor_seat_seq;
END $$

-- 13. 查詢座位標記
CREATE PROCEDURE get_seat_status(
    IN p_floor_seat_seq INT,
    OUT p_is_occupied BOOLEAN
)
BEGIN
    SELECT is_occupied
    INTO p_is_occupied
    FROM seating_chart
    WHERE floor_seat_seq = p_floor_seat_seq;
    
    IF p_is_occupied IS NULL THEN
        SET p_is_occupied = FALSE;
    END IF;
END $$

-- 14. 取得員工座位
CREATE PROCEDURE get_employee_seat_assignment()
BEGIN
    SELECT 
        s.floor_seat_seq, s.floor_no, s.seat_no, s.is_occupied, 
        e.emp_id
    FROM 
        seating_chart s
    LEFT JOIN 
        employee e
    ON 
        s.floor_seat_seq = e.floor_seat_seq;
END $$

DELIMITER ;

