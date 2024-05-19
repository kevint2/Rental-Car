INSERT INTO rental_position (role)
SELECT 'ROLE_MANAGER'
    WHERE NOT EXISTS (
        SELECT 1 FROM rental_position WHERE position = 'ROLE_MANAGER'
    );

INSERT INTO rental_position (role)
SELECT 'ROLE_EMPLOYEE'
    WHERE NOT EXISTS (
        SELECT 1 FROM rental_position WHERE position = 'ROLE_EMPLOYEE'
    );