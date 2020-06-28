DROP TABLE IF EXISTS employee_invoices;

-- table for holding invoice data
CREATE TABLE employee_invoices (
    EmployeeId int,
    CustomerId int,
    Total decimal
);

-- insert employees with customers
INSERT INTO employee_invoices (CustomerId, EmployeeId)
SELECT CustomerId, SupportRepId FROM customers;

-- insert the employees with no customers
INSERT INTO employee_invoices (EmployeeId)
SELECT employees.EmployeeId FROM employees
WHERE NOT EXISTS(SELECT EmployeeId FROM employee_invoices WHERE employees.EmployeeId = employee_invoices.EmployeeId);

-- add totals for each invoice
UPDATE employee_invoices
SET Total = (SELECT SUM (Total) FROM invoices WHERE CustomerId = employee_invoices.CustomerId);

-- shows the sum of invoices for each employee
SELECT EmployeeId, SUM (Total) from employee_invoices GROUP BY EmployeeId;
