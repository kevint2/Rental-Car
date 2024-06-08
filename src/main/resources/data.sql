INSERT INTO rental_position (position)
SELECT 'ROLE_MANAGER'
    WHERE NOT EXISTS (
        SELECT 1 FROM rental_position WHERE position = 'ROLE_MANAGER'
    );

INSERT INTO rental_position (position)
SELECT 'ROLE_EMPLOYEE'
    WHERE NOT EXISTS (
        SELECT 1 FROM rental_position WHERE position = 'ROLE_EMPLOYEE'
    );
INSERT INTO rental_position (position)
SELECT 'ROLE_OWNER'
    WHERE NOT EXISTS (
        SELECT 1 FROM rental_position WHERE position = 'ROLE_OWNER'
    );
INSERT INTO rental_position (position)
SELECT 'ROLE_ADMIN'
    WHERE NOT EXISTS (
        SELECT 1 FROM rental_position WHERE position = 'ROLE_ADMIN'
    );
-- INSERT INTO employees if not exists (employee_id, active, password, username, employee_position) VALUES (1, true, '$2y$10$WCzdd8gZdi6ySDt/XHRHS.cgfWg.Bk2qH6Puzi36oH7mtdk5t5LOO', 'admin', 'ROLE_ADMIN') ;