-- Exercise 1: Control Structures
-- Scenario 1
SELECT * FROM loans;
update loans
set InterestRate=InterestRate-(InterestRate*0.01)
where CustomerID in(
select CustomerID
from customers
WHERE EXTRACT(YEAR FROM '2024-08-05') - EXTRACT(YEAR FROM DOB) > 60);

-- Scenario 2
select *from customers;
update customers
set isVip=true
where customers.Balance>1000;

-- Scenario 3
select loans.LoanID,customers.Name,loans.EndDate
from loans
join customers
on customers.CustomerID=loans.CustomerID
where loans.EndDate between '2024-08-05' and '2024-08-05'+30;

-- Exercise 2: Error Handling
-- Scenario 1
CREATE PROCEDURE SafeTransferFunds
    @FromAccountID INT,
    @ToAccountID INT,
    @Amount DECIMAL(18, 2)
AS
BEGIN
SET NOCOUNT ON;
-- Start a transaction
    BEGIN TRY
        BEGIN TRANSACTION;
-- Check for sufficient funds
        DECLARE @FromAccountBalance DECIMAL(18, 2);
        SELECT @FromAccountBalance = Balance FROM Accounts WHERE AccountID = @FromAccountID;
        IF @FromAccountBalance < @Amount
        BEGIN
            RAISERROR('Insufficient funds in account %d', 16, 1, @FromAccountID);
            ROLLBACK TRANSACTION;
            RETURN;
        END
-- Deduct the amount from the FromAccount
        UPDATE Accounts
        SET Balance = Balance - @Amount
        WHERE AccountID = @FromAccountID;

        -- Add the amount to the ToAccount
        UPDATE Accounts
        SET Balance = Balance + @Amount
        WHERE AccountID = @ToAccountID;
-- Commit the transaction
        COMMIT TRANSACTION;
        PRINT 'Transfer completed successfully.';
    END TRY
    BEGIN CATCH
        -- Log the error details
        DECLARE @ErrorMessage NVARCHAR(4000);
        DECLARE @ErrorSeverity INT;
        DECLARE @ErrorState INT;

        SELECT 
            @ErrorMessage = ERROR_MESSAGE(),
            @ErrorSeverity = ERROR_SEVERITY(),
            @ErrorState = ERROR_STATE();

        -- Rollback the transaction if it is still active
        IF @@TRANCOUNT > 0
        BEGIN
            ROLLBACK TRANSACTION;
        END

        -- Log the error (assuming a logging table exists)
        INSERT INTO ErrorLog (ErrorMessage, ErrorSeverity, ErrorState, ErrorDate)
        VALUES (@ErrorMessage, @ErrorSeverity, @ErrorState, GETDATE());

        -- Re-throw the error
        RAISERROR(@ErrorMessage, @ErrorSeverity, @ErrorState);
    END CATCH
END

-- Scenario 2
CREATE PROCEDURE UpdateSalary
    @EmployeeID INT,
    @Percentage DECIMAL(5, 2) -- Percentage as a decimal (e.g., 10 for 10%)
AS
BEGIN
    SET NOCOUNT ON;
-- Start a transaction
    BEGIN TRY
        BEGIN TRANSACTION;
-- Check if the employee exists and get the current salary
        DECLARE @CurrentSalary DECIMAL(18, 2);
        SELECT @CurrentSalary = Salary FROM Employees WHERE EmployeeID = @EmployeeID;
-- Check if the employee was found
        IF @CurrentSalary IS NULL
        BEGIN
            RAISERROR('Employee with ID %d does not exist.', 16, 1, @EmployeeID);
            ROLLBACK TRANSACTION;
            RETURN;
        END
-- Calculate the new salary
        DECLARE @NewSalary DECIMAL(18, 2);
        SET @NewSalary = @CurrentSalary * (1 + @Percentage / 100);

        -- Update the employee's salary
        UPDATE Employees
        SET Salary = @NewSalary
        WHERE EmployeeID = @EmployeeID;
-- Commit the transaction
        COMMIT TRANSACTION;

        PRINT 'Salary updated successfully.';
    END TRY
    BEGIN CATCH
        -- Log the error details
        DECLARE @ErrorMessage NVARCHAR(4000);
        DECLARE @ErrorSeverity INT;
        DECLARE @ErrorState INT;

        SELECT 
            @ErrorMessage = ERROR_MESSAGE(),
            @ErrorSeverity = ERROR_SEVERITY(),
            @ErrorState = ERROR_STATE();

        -- Rollback the transaction if it is still active
        IF @@TRANCOUNT > 0
        BEGIN
            ROLLBACK TRANSACTION;
        END

        -- Log the error (assuming a logging table exists)
        INSERT INTO ErrorLog (ErrorMessage, ErrorSeverity, ErrorState, ErrorDate)
        VALUES (@ErrorMessage, @ErrorSeverity, @ErrorState, GETDATE());

        -- Re-throw the error
        RAISERROR(@ErrorMessage, @ErrorSeverity, @ErrorState);
    END CATCH
END

-- Scenario 3
 CREATE PROCEDURE AddNewCustomer
    @CustomerID INT,
    @CustomerName NVARCHAR(100),
    @Email NVARCHAR(100),
    @Phone NVARCHAR(15)
AS
BEGIN
    SET NOCOUNT ON;

    -- Start a transaction
    BEGIN TRY
        BEGIN TRANSACTION;

        -- Check if the customer already exists
        IF EXISTS (SELECT 1 FROM Customers WHERE CustomerID = @CustomerID)
        BEGIN
            RAISERROR('Customer with ID %d already exists.', 16, 1, @CustomerID);
            ROLLBACK TRANSACTION;
            RETURN;
        END

        -- Insert the new customer
        INSERT INTO Customers (CustomerID, CustomerName, Email, Phone)
        VALUES (@CustomerID, @CustomerName, @Email, @Phone);

        -- Commit the transaction
        COMMIT TRANSACTION;

        PRINT 'Customer added successfully.';
    END TRY
    BEGIN CATCH
        -- Log the error details
        DECLARE @ErrorMessage NVARCHAR(4000);
        DECLARE @ErrorSeverity INT;
        DECLARE @ErrorState INT;

        SELECT 
            @ErrorMessage = ERROR_MESSAGE(),
            @ErrorSeverity = ERROR_SEVERITY(),
            @ErrorState = ERROR_STATE();

        -- Rollback the transaction if it is still active
        IF @@TRANCOUNT > 0
        BEGIN
            ROLLBACK TRANSACTION;
        END

        -- Log the error (assuming a logging table exists)
        INSERT INTO ErrorLog (ErrorMessage, ErrorSeverity, ErrorState, ErrorDate)
        VALUES (@ErrorMessage, @ErrorSeverity, @ErrorState, GETDATE());

        -- Re-throw the error
        RAISERROR(@ErrorMessage, @ErrorSeverity, @ErrorState);
    END CATCH
END


-- Exercise 3: Stored Procedures
-- Scenario 1
CREATE PROCEDURE ProcessMonthlyInterest
AS
BEGIN
    SET NOCOUNT ON;

    -- Start a transaction
    BEGIN TRY
        BEGIN TRANSACTION;

        -- Update the balance of all savings accounts by applying a 1% interest rate
        UPDATE Accounts
        SET Balance = Balance * 1.01
        WHERE AccountType = 'Savings'; -- Assuming AccountType distinguishes between account types

        -- Commit the transaction
        COMMIT TRANSACTION;

        PRINT 'Monthly interest processed successfully for all savings accounts.';
    END TRY
    BEGIN CATCH
        -- Log the error details
        DECLARE @ErrorMessage NVARCHAR(4000);
        DECLARE @ErrorSeverity INT;
        DECLARE @ErrorState INT;

        SELECT 
            @ErrorMessage = ERROR_MESSAGE(),
            @ErrorSeverity = ERROR_SEVERITY(),
            @ErrorState = ERROR_STATE();

        -- Rollback the transaction if it is still active
        IF @@TRANCOUNT > 0
        BEGIN
            ROLLBACK TRANSACTION;
        END

        -- Log the error (assuming a logging table exists)
        INSERT INTO ErrorLog (ErrorMessage, ErrorSeverity, ErrorState, ErrorDate)
        VALUES (@ErrorMessage, @ErrorSeverity, @ErrorState, GETDATE());

        -- Re-throw the error
        RAISERROR(@ErrorMessage, @ErrorSeverity, @ErrorState);
    END CATCH
END

-- Scenario 2
CREATE PROCEDURE UpdateEmployeeBonus
    @DepartmentID INT,
    @BonusPercentage DECIMAL(5, 2) -- Bonus percentage as a decimal (e.g., 10 for 10%)
AS
BEGIN
    SET NOCOUNT ON;

    -- Start a transaction
    BEGIN TRY
        BEGIN TRANSACTION;

        -- Update the salary of employees in the specified department by adding the bonus
        UPDATE Employees
        SET Salary = Salary * (1 + @BonusPercentage / 100)
        WHERE DepartmentID = @DepartmentID;

        -- Commit the transaction
        COMMIT TRANSACTION;

        PRINT 'Bonus updated successfully for employees in department ID ' + CAST(@DepartmentID AS NVARCHAR(10)) + '.';
    END TRY
    BEGIN CATCH
        -- Log the error details
        DECLARE @ErrorMessage NVARCHAR(4000);
        DECLARE @ErrorSeverity INT;
        DECLARE @ErrorState INT;

        SELECT 
            @ErrorMessage = ERROR_MESSAGE(),
            @ErrorSeverity = ERROR_SEVERITY(),
            @ErrorState = ERROR_STATE();

        -- Rollback the transaction if it is still active
        IF @@TRANCOUNT > 0
        BEGIN
            ROLLBACK TRANSACTION;
        END

        -- Log the error (assuming a logging table exists)
        INSERT INTO ErrorLog (ErrorMessage, ErrorSeverity, ErrorState, ErrorDate)
        VALUES (@ErrorMessage, @ErrorSeverity, @ErrorState, GETDATE());

        -- Re-throw the error
        RAISERROR(@ErrorMessage, @ErrorSeverity, @ErrorState);
    END CATCH
END

-- Scenario 3
CREATE PROCEDURE TransferFunds
    @SourceAccountID INT,
    @TargetAccountID INT,
    @TransferAmount DECIMAL(18, 2) -- Amount to transfer
AS
BEGIN
    SET NOCOUNT ON;

    -- Start a transaction
    BEGIN TRY
        BEGIN TRANSACTION;

        -- Check if the source account has sufficient balance
        DECLARE @SourceBalance DECIMAL(18, 2);
        SELECT @SourceBalance = Balance FROM Accounts WHERE AccountID = @SourceAccountID;

        IF @SourceBalance IS NULL
        BEGIN
            RAISERROR('Source account does not exist.', 16, 1);
            ROLLBACK TRANSACTION;
            RETURN;
        END

        IF @SourceBalance < @TransferAmount
        BEGIN
            RAISERROR('Insufficient balance in the source account.', 16, 1);
            ROLLBACK TRANSACTION;
            RETURN;
        END

        -- Deduct the amount from the source account
        UPDATE Accounts
        SET Balance = Balance - @TransferAmount
        WHERE AccountID = @SourceAccountID;

        -- Add the amount to the target account
        UPDATE Accounts
        SET Balance = Balance + @TransferAmount
        WHERE AccountID = @TargetAccountID;

        -- Check if the target account exists
        IF @@ROWCOUNT = 0
        BEGIN
            RAISERROR('Target account does not exist.', 16, 1);
            ROLLBACK TRANSACTION;
            RETURN;
        END

        -- Commit the transaction
        COMMIT TRANSACTION;

        PRINT 'Transfer completed successfully.';
    END TRY
    BEGIN CATCH
        -- Log the error details
        DECLARE @ErrorMessage NVARCHAR(4000);
        DECLARE @ErrorSeverity INT;
        DECLARE @ErrorState INT;

        SELECT 
            @ErrorMessage = ERROR_MESSAGE(),
            @ErrorSeverity = ERROR_SEVERITY(),
            @ErrorState = ERROR_STATE();

        -- Rollback the transaction if it is still active
        IF @@TRANCOUNT > 0
        BEGIN
            ROLLBACK TRANSACTION;
        END

        -- Log the error (assuming a logging table exists)
        INSERT INTO ErrorLog (ErrorMessage, ErrorSeverity, ErrorState, ErrorDate)
        VALUES (@ErrorMessage, @ErrorSeverity, @ErrorState, GETDATE());

        -- Re-throw the error
        RAISERROR(@ErrorMessage, @ErrorSeverity, @ErrorState);
    END CATCH
END

-- Exercise 4: Functions
-- Scenario 1
CREATE FUNCTION CalculateAge
(
    @DateOfBirth DATE
)
RETURNS INT
AS
BEGIN
    DECLARE @Age INT;

    -- Calculate the age
    SET @Age = DATEDIFF(YEAR, @DateOfBirth, GETDATE()) 
               - CASE 
                   WHEN MONTH(@DateOfBirth) > MONTH(GETDATE()) 
                        OR (MONTH(@DateOfBirth) = MONTH(GETDATE()) AND DAY(@DateOfBirth) > DAY(GETDATE())) 
                   THEN 1 
                   ELSE 0 
                 END;

    RETURN @Age;
END

-- Scenario 2
CREATE FUNCTION CalculateMonthlyInstallment
(
    @LoanAmount DECIMAL(18, 2),
    @AnnualInterestRate DECIMAL(5, 2), -- Annual interest rate as a percentage
    @LoanDurationYears INT
)
RETURNS DECIMAL(18, 2)
AS
BEGIN
    DECLARE @MonthlyInstallment DECIMAL(18, 2);
    DECLARE @MonthlyInterestRate DECIMAL(5, 5);
    DECLARE @TotalPayments INT;

    -- Calculate monthly interest rate
    SET @MonthlyInterestRate = @AnnualInterestRate / 100 / 12;

    -- Calculate total number of payments (months)
    SET @TotalPayments = @LoanDurationYears * 12;

    -- Calculate monthly installment using the formula
    IF @MonthlyInterestRate = 0
    BEGIN
        -- If interest rate is 0, simply divide loan amount by total payments
        SET @MonthlyInstallment = @LoanAmount / @TotalPayments;
    END
    ELSE
    BEGIN
        SET @MonthlyInstallment = @LoanAmount * 
                                  (@MonthlyInterestRate * POWER(1 + @MonthlyInterestRate, @TotalPayments)) / 
                                  (POWER(1 + @MonthlyInterestRate, @TotalPayments) - 1);
    END

    RETURN @MonthlyInstallment;
END

-- Scenario 3
CREATE FUNCTION HasSufficientBalance
(
    @AccountID INT,
    @Amount DECIMAL(18, 2) -- Amount to check
)
RETURNS BIT
AS
BEGIN
    DECLARE @Balance DECIMAL(18, 2);
    DECLARE @Result BIT;

    -- Get the current balance for the specified account
    SELECT @Balance = Balance 
    FROM Accounts 
    WHERE AccountID = @AccountID;

    -- Check if the account exists and has sufficient balance
    IF @Balance IS NULL
    BEGIN
        -- Account does not exist
        SET @Result = 0; -- false
    END
    ELSE IF @Balance >= @Amount
    BEGIN
        -- Sufficient balance
        SET @Result = 1; -- true
    END
    ELSE
    BEGIN
        -- Insufficient balance
        SET @Result = 0; -- false
    END

    RETURN @Result;
END

-- Exercise 5: Triggers
-- Scenario 1
CREATE TRIGGER UpdateCustomerLastModified
ON Customers
AFTER UPDATE
AS
BEGIN
    SET NOCOUNT ON;

    -- Update the LastModified column to the current date
    UPDATE Customers
    SET LastModified = GETDATE()
    FROM Customers c
    INNER JOIN inserted i ON c.CustomerID = i.CustomerID;
END

-- Scenario 2
CREATE TRIGGER LogTransaction
ON Transactions
AFTER INSERT
AS
BEGIN
    SET NOCOUNT ON;
-- Insert a record into the AuditLog table for each new transaction
    INSERT INTO AuditLog (TransactionID, AccountID, Amount, TransactionDate, Action)
    SELECT 
        i.TransactionID, 
        i.AccountID, 
        i.Amount, 
        i.TransactionDate, 
        'INSERT' AS Action
    FROM inserted i;


-- Scenario 3
CREATE TRIGGER CheckTransactionRules
ON Transactions
INSTEAD OF INSERT
AS
BEGIN
    SET NOCOUNT ON;

    DECLARE @AccountID INT;
    DECLARE @Amount DECIMAL(18, 2);
    DECLARE @TransactionType VARCHAR(10);

    -- Loop through each inserted record
    DECLARE transaction_cursor CURSOR FOR
    SELECT AccountID, Amount, TransactionType
    FROM inserted;

    OPEN transaction_cursor;

    FETCH NEXT FROM transaction_cursor INTO @AccountID, @Amount, @TransactionType;

    WHILE @@FETCH_STATUS = 0
    BEGIN
        DECLARE @CurrentBalance DECIMAL(18, 2);

        -- Get the current balance for the account
        SELECT @CurrentBalance = Balance
        FROM Accounts
        WHERE AccountID = @AccountID;

        -- Check business rules
        IF @TransactionType = 'WITHDRAWAL' AND @Amount > @CurrentBalance
        BEGIN
            RAISERROR('Withdrawal amount exceeds account balance.', 16, 1);
            ROLLBACK TRANSACTION;
            RETURN;
        END
        ELSE IF @TransactionType = 'DEPOSIT' AND @Amount <= 0
        BEGIN
            RAISERROR('Deposit amount must be positive.', 16, 1);
            ROLLBACK TRANSACTION;
            RETURN;
        END

        -- If rules are satisfied, insert the transaction
        INSERT INTO Transactions (AccountID, Amount, TransactionDate, TransactionType)
        SELECT AccountID, Amount, GETDATE(), TransactionType
        FROM inserted
        WHERE AccountID = @AccountID AND Amount = @Amount AND TransactionType = @TransactionType;

        FETCH NEXT FROM transaction_cursor INTO @AccountID, @Amount, @TransactionType;
    END

    CLOSE transaction_cursor;
    DEALLOCATE transaction_cursor;
END


-- Exercise 6: Cursors
-- Scenario 1
DECLARE
    -- Define a record type for the cursor
    TYPE TransactionRec IS RECORD (
        customer_id Customers.CustomerID%TYPE,
        customer_name Customers.CustomerName%TYPE,
        transaction_date Transactions.TransactionDate%TYPE,
        amount Transactions.Amount%TYPE
    );

    -- Define a cursor for retrieving transactions
    CURSOR TransactionCursor IS
        SELECT c.CustomerID, c.CustomerName, t.TransactionDate, t.Amount
        FROM Customers c
        JOIN Transactions t ON c.CustomerID = t.AccountID
        WHERE EXTRACT(MONTH FROM t.TransactionDate) = EXTRACT(MONTH FROM SYSDATE)
          AND EXTRACT(YEAR FROM t.TransactionDate) = EXTRACT(YEAR FROM SYSDATE)
        ORDER BY c.CustomerID, t.TransactionDate;

    -- Variable to hold each record fetched by the cursor
    v_transaction TransactionRec;

BEGIN
    -- Open the cursor and loop through each record
    OPEN TransactionCursor;
    LOOP
        FETCH TransactionCursor INTO v_transaction;

        -- Exit the loop when no more records are found
        EXIT WHEN TransactionCursor%NOTFOUND;

        -- Print the statement for each customer
        DBMS_OUTPUT.PUT_LINE('Customer ID: ' || v_transaction.customer_id);
        DBMS_OUTPUT.PUT_LINE('Customer Name: ' || v_transaction.customer_name);
        DBMS_OUTPUT.PUT_LINE('Transaction Date: ' || TO_CHAR(v_transaction.transaction_date, 'DD-MON-YYYY'));
        DBMS_OUTPUT.PUT_LINE('Amount: ' || v_transaction.amount);
        DBMS_OUTPUT.PUT_LINE('----------------------------------------');
    END LOOP;

    -- Close the cursor
    CLOSE TransactionCursor;

EXCEPTION
    WHEN OTHERS THEN
        -- Handle exceptions
        DBMS_OUTPUT.PUT_LINE('An error occurred: ' || SQLERRM);
END;

-- Scenario 2
DECLARE
    -- Define a constant for the annual maintenance fee
    annual_fee CONSTANT NUMBER := 50; -- Set the annual fee amount

    -- Define a cursor for retrieving all accounts
    CURSOR AccountCursor IS
        SELECT AccountID, Balance
        FROM Accounts
        FOR UPDATE; -- Use FOR UPDATE to allow updates on fetched rows

    -- Variable to hold each account record
    v_account AccountCursor%ROWTYPE;

BEGIN
    -- Open the cursor and loop through each account
    OPEN AccountCursor;
    LOOP
        FETCH AccountCursor INTO v_account;

        -- Exit the loop when no more records are found
        EXIT WHEN AccountCursor%NOTFOUND;

        -- Deduct the annual fee from the account balance
        UPDATE Accounts
        SET Balance = Balance - annual_fee
        WHERE CURRENT OF AccountCursor;

        -- Optional: Output the updated balance
        DBMS_OUTPUT.PUT_LINE('Account ID: ' || v_account.AccountID || 
                             ' - New Balance: ' || (v_account.Balance - annual_fee));
    END LOOP;

    -- Close the cursor
    CLOSE AccountCursor;

    -- Commit the changes
    COMMIT;

EXCEPTION
    WHEN OTHERS THEN
        -- Handle exceptions
        DBMS_OUTPUT.PUT_LINE('An error occurred: ' || SQLERRM);
        ROLLBACK; -- Rollback in case of an error
END;

-- Scenario 3
DECLARE
    -- Define a constant for the new interest rate policy
    new_interest_rate CONSTANT NUMBER := 5.5; -- Set the new interest rate

    -- Define a cursor for retrieving all loans
    CURSOR LoanCursor IS
        SELECT LoanID, InterestRate
        FROM Loans
        FOR UPDATE; -- Use FOR UPDATE to allow updates on fetched rows

    -- Variable to hold each loan record
    v_loan LoanCursor%ROWTYPE;

BEGIN
    -- Open the cursor and loop through each loan
    OPEN LoanCursor;
    LOOP
        FETCH LoanCursor INTO v_loan;

        -- Exit the loop when no more records are found
        EXIT WHEN LoanCursor%NOTFOUND;

        -- Update the interest rate for the current loan
        UPDATE Loans
        SET InterestRate = new_interest_rate
        WHERE CURRENT OF LoanCursor;

        -- Optional: Output the updated loan ID and new interest rate
        DBMS_OUTPUT.PUT_LINE('Loan ID: ' || v_loan.LoanID || 
                             ' - New Interest Rate: ' || new_interest_rate);
    END LOOP;

    -- Close the cursor
    CLOSE LoanCursor;

    -- Commit the changes
    COMMIT;

EXCEPTION
    WHEN OTHERS THEN
        -- Handle exceptions
        DBMS_OUTPUT.PUT_LINE('An error occurred: ' || SQLERRM);
        ROLLBACK; -- Rollback in case of an error
END;


-- Exercise 7: Packages
-- scenario 1
CREATE OR REPLACE PACKAGE CustomerManagement AS
    -- Procedure to add a new customer
    PROCEDURE AddCustomer(
        p_customer_id IN NUMBER,
        p_customer_name IN VARCHAR2,
        p_initial_balance IN NUMBER
    );

    -- Procedure to update customer details
    PROCEDURE UpdateCustomer(
        p_customer_id IN NUMBER,
        p_customer_name IN VARCHAR2,
        p_balance IN NUMBER
    );

    -- Function to get customer balance
    FUNCTION GetCustomerBalance(
        p_customer_id IN NUMBER
    ) RETURN NUMBER;
END CustomerManagement;
/

-- scenario 2
CREATE OR REPLACE PACKAGE EmployeeManagement AS
    TYPE EmployeeRec IS RECORD (
        id   NUMBER,
        name  VARCHAR2(100),
        position VARCHAR2(100),
        salary  NUMBER
    );

    PROCEDURE HireEmployee(
        emp_id  IN NUMBER,
        emp_name  IN VARCHAR2,
        emp_position IN VARCHAR2,
        emp_salary  IN NUMBER
    );

    PROCEDURE UpdateEmployee(
        emp_id  IN NUMBER,
        emp_name  IN VARCHAR2 DEFAULT NULL,
        emp_position IN VARCHAR2 DEFAULT NULL,
        emp_salary  IN NUMBER DEFAULT NULL
    );

    FUNCTION CalculateAnnualSalary(emp_id IN NUMBER) RETURN NUMBER;
END EmployeeManagement;
/
CREATE OR REPLACE PACKAGE BODY EmployeeManagement AS
-- A table to hold employee data
    emp_table SYS.ODCIVARCHAR2LIST;
PROCEDURE HireEmployee(
        emp_id      IN NUMBER,
        emp_name    IN VARCHAR2,
        emp_position IN VARCHAR2,
        emp_salary   IN NUMBER
    ) IS
    BEGIN
        emp_table(emp_id) := emp_name || ',' || emp_position || ',' || emp_salary;
        DBMS_OUTPUT.PUT_LINE('Employee hired: ' || emp_name);
    END HireEmployee;

    PROCEDURE UpdateEmployee(
        emp_id      IN NUMBER,
        emp_name    IN VARCHAR2 DEFAULT NULL,
        emp_position IN VARCHAR2 DEFAULT NULL,
        emp_salary   IN NUMBER DEFAULT NULL
    ) IS
    BEGIN
        IF emp_name IS NOT NULL THEN
            emp_table(emp_id) := emp_name || ',' || emp_position || ',' || emp_salary;
        ELSIF emp_position IS NOT NULL THEN
            emp_table(emp_id) := emp_name || ',' || emp_position || ',' || emp_salary;
        ELSIF emp_salary IS NOT NULL THEN
            emp_table(emp_id) := emp_name || ',' || emp_position || ',' || emp_salary;
        END IF;
        DBMS_OUTPUT.PUT_LINE('Employee updated: ' || emp_id);
    END UpdateEmployee;

    FUNCTION CalculateAnnualSalary(emp_id IN NUMBER) RETURN NUMBER IS
        emp_salary NUMBER;
    BEGIN
        emp_salary := TO_NUMBER(SUBSTR(emp_table(emp_id), INSTR(emp_table(emp_id), ',', -1) + 1));
        RETURN emp_salary * 12;
    END CalculateAnnualSalary;

END EmployeeManagement;
/

-- Scenario 3
CREATE OR REPLACE PACKAGE BODY AccountOperations IS
-- Procedure to open a new account
  PROCEDURE OpenAccount(customer_id IN NUMBER, initial_balance IN NUMBER) IS
    new_account_id NUMBER;
  BEGIN
    SELECT NVL(MAX(account_id), 0) + 1 INTO new_account_id FROM accounts;
    INSERT INTO accounts (account_id, customer_id, balance)
    VALUES (new_account_id, customer_id, initial_balance);
    DBMS_OUTPUT.PUT_LINE('Account ' || new_account_id || ' opened for customer ' || customer_id || ' with initial balance ' || initial_balance);
  END OpenAccount;

  -- Procedure to close an account
  PROCEDURE CloseAccount(account_id IN NUMBER) IS
  BEGIN
    DELETE FROM accounts WHERE account_id = account_id;
    DBMS_OUTPUT.PUT_LINE('Account ' || account_id || ' closed.');
  END CloseAccount;

  -- Function to get the total balance of a customer across all accounts
  FUNCTION GetTotalBalance(customer_id IN NUMBER) RETURN NUMBER IS
    total_balance NUMBER;
  BEGIN
    SELECT NVL(SUM(balance), 0) INTO total_balance FROM accounts WHERE customer_id = customer_id;
    RETURN total_balance;
  END GetTotalBalance;

END AccountOperations;
/


