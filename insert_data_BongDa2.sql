/*
================================================================================
SCRIPT T?O D? LI?U WORLD CUP (2014-2022) CHO DATABASE BONGDA2
PHIÊN B?N T? ??NG SINH NHI?U D? LI?U (MÔ HÌNH GI? HÀNG)
================================================================================
*/

-- B??C 0: S? D?NG DATABASE VÀ T?O CÁC SEQUENCE C?N THI?T
USE BongDa2;
GO

-- T?o các SEQUENCE ?? sinh ID t? ??ng (ch?y 1 l?n duy nh?t)
IF NOT EXISTS (SELECT * FROM sys.sequences WHERE name = 'OrderSeq')
    CREATE SEQUENCE OrderSeq START WITH 1 INCREMENT BY 1;
GO
IF NOT EXISTS (SELECT * FROM sys.sequences WHERE name = 'TicketSeq')
    CREATE SEQUENCE TicketSeq START WITH 1 INCREMENT BY 1;
GO
IF NOT EXISTS (SELECT * FROM sys.sequences WHERE name = 'PaymentSeq')
    CREATE SEQUENCE PaymentSeq START WITH 1 INCREMENT BY 1;
GO

----------------------------------------------------------------------
-- B??C 1: D?N S?CH TOÀN B? D? LI?U C?
----------------------------------------------------------------------
PRINT 'STEP 1: Deleting old data...';
-- Xóa theo ?úng th? t? (ng??c l?i c?a các khóa ngo?i)
DELETE FROM Payments;
DELETE FROM Tickets;
DELETE FROM Orders;
DELETE FROM Seats;
DELETE FROM Matches;
DELETE FROM Users;
DELETE FROM Stadiums;
GO

----------------------------------------------------------------------
-- B??C 2: THÊM D? LI?U SÂN V?N ??NG (STADIUMS)
----------------------------------------------------------------------
PRINT 'STEP 2: Inserting Stadiums...';
INSERT INTO Stadiums (stadium_id, name, location, capacity) VALUES
('STD001', N'Lusail Iconic Stadium, Lusail', N'Qatar', 88966),
('STD002', N'Al Bayt Stadium, Al Khor', N'Qatar', 68895),
('STD003', N'Education City Stadium, Al Rayyan', N'Qatar', 44667),
('STD004', N'Ahmad bin Ali Stadium, Al Rayyan', N'Qatar', 45032),
('STD005', N'Khalifa International Stadium, Al Rayyan', N'Qatar', 45857),
('STD006', N'Al Thumama Stadium, Doha', N'Qatar', 44400),
('STD007', N'Stadium 974, Doha', N'Qatar', 44089),
('STD008', N'Al Janoub Stadium, Al Wakrah', N'Qatar', 44325),
('STD009', N'Luzhniki Stadium, Moscow', N'Russia', 78011),
('STD010', N'Saint Petersburg Stadium, Saint Petersburg', N'Russia', 64468),
('STD011', N'Fisht Olympic Stadium, Sochi', N'Russia', 44287),
('STD012', N'Volgograd Arena, Volgograd', N'Russia', 43713),
('STD013', N'Spartak Stadium, Moscow', N'Russia', 44190),
('STD014', N'Nizhny Novgorod Stadium, Nizhny Novgorod', N'Russia', 43319),
('STD015', N'Rostov Arena, Rostov-on-Don', N'Russia', 43472),
('STD016', N'Kazan Arena, Kazan', N'Russia', 42873),
('STD017', N'Samara Arena, Samara', N'Russia', 41970),
('STD018', N'Mordovia Arena, Saransk', N'Russia', 41685),
('STD019', N'Kaliningrad Stadium, Kaliningrad', N'Russia', 33973),
('STD020', N'Central Stadium, Yekaterinburg', N'Russia', 33061),
('STD021', N'Estádio do Maracanã, Rio de Janeiro', N'Brazil', 74738),
('STD022', N'Estádio Nacional Mané Garrincha, Brasília', N'Brazil', 69349),
('STD023', N'Arena de São Paulo, São Paulo', N'Brazil', 63267),
('STD024', N'Estádio Castelão, Fortaleza', N'Brazil', 60342),
('STD025', N'Estádio Mineirão, Belo Horizonte', N'Brazil', 58170),
('STD026', N'Arena Fonte Nova, Salvador', N'Brazil', 51900),
('STD027', N'Estádio Beira-Rio, Porto Alegre', N'Brazil', 43012),
('STD028', N'Arena Pernambuco, Recife', N'Brazil', 42610),
('STD029', N'Arena Pantanal, Cuiabá', N'Brazil', 41112),
('STD030', N'Arena da Amazônia, Manaus', N'Brazil', 39118),
('STD031', N'Arena das Dunas, Natal', N'Brazil', 39971),
('STD032', N'Arena da Baixada, Curitiba', N'Brazil', 39131);
GO

----------------------------------------------------------------------
-- B??C 3: THÊM D? LI?U NG??I DÙNG (USERS)
----------------------------------------------------------------------
PRINT 'STEP 3: Inserting Users...';
INSERT INTO Users (user_id, full_name, email, phone, pass) VALUES
('USER00001', N'Nguy?n Hoàng Anh', 'anh.nguyen@email.com', '0912345601', 'pass123'),
('USER00002', N'Tr?n Minh Tu?n', 'tuan.tran@email.com', '0987654302', 'pass123'),
('USER00003', N'Lê Th? Thu? Linh', 'linh.le@email.com', '0905558803', 'pass123'),
('USER00004', N'Ph?m Gia Hân', 'han.pham@email.com', '0918123404', 'pass123'),
('USER00005', N'Võ Qu?c B?o', 'bao.vo@email.com', '0935678905', 'pass123'),
('USER00006', N'?? Khánh Huy?n', 'huyen.do@email.com', '0977888906', 'pass123'),
('USER00007', N'Hoàng Trung Kiên', 'kien.hoang@email.com', '0944555607', 'pass123'),
('USER00008', N'Mai Ph??ng Th?o', 'thao.mai@email.com', '0913456708', 'pass123'),
('USER00009', N'Lý ??c Minh', 'minh.ly@email.com', '0988111209', 'pass123'),
('USER00010', N'Bùi Thanh Trúc', 'truc.bui@email.com', '0902333410', 'pass123'),
('USER00011', N'Tr??ng Công Vinh', 'vinh.truong@email.com', '0965874111', 'pass123'),
('USER00012', N'D??ng Ng?c Ánh', 'anh.duong@email.com', '0911223312', 'pass123'),
('USER00013', N'Hu?nh T?n Phát', 'phat.huynh@email.com', '0922334413', 'pass123'),
('USER00014', N'??ng Minh Châu', 'chau.dang@email.com', '0933445514', 'pass123'),
('USER00015', N'Ngô Gia Khiêm', 'khiem.ngo@email.com', '0944556615', 'pass123');
GO

----------------------------------------------------------------------
-- B??C 4: THÊM D? LI?U CÁC TR?N ??U (MATCHES) T? 2014-2022
----------------------------------------------------------------------
PRINT 'STEP 4: Inserting Matches...';
INSERT INTO Matches (match_id, home_team, away_team, stadium_id, match_date, tournament) VALUES
('M202201', N'Qatar', N'Ecuador', 'STD002', '2022-11-20 19:00:00', N'Group stage'),
('M202202', N'England', N'Iran', 'STD005', '2022-11-21 16:00:00', N'Group stage'),
('M202203', N'Senegal', N'Netherlands', 'STD006', '2022-11-21 19:00:00', N'Group stage'),
('M202204', N'United States', N'Wales', 'STD004', '2022-11-21 22:00:00', N'Group stage'),
('M202205', N'Argentina', N'Saudi Arabia', 'STD001', '2022-11-22 13:00:00', N'Group stage'),
('M202206', N'Denmark', N'Tunisia', 'STD003', '2022-11-22 16:00:00', N'Group stage'),
('M202207', N'Mexico', N'Poland', 'STD007', '2022-11-22 19:00:00', N'Group stage'),
('M202208', N'France', N'Australia', 'STD008', '2022-11-22 22:00:00', N'Group stage'),
('M202209', N'Morocco', N'Croatia', 'STD002', '2022-11-23 13:00:00', N'Group stage'),
('M202210', N'Germany', N'Japan', 'STD005', '2022-11-23 16:00:00', N'Group stage'),
('M202211', N'Spain', N'Costa Rica', 'STD006', '2022-11-23 19:00:00', N'Group stage'),
('M202212', N'Belgium', N'Canada', 'STD004', '2022-11-23 22:00:00', N'Group stage'),
('M202213', N'Switzerland', N'Cameroon', 'STD008', '2022-11-24 13:00:00', N'Group stage'),
('M202214', N'Uruguay', N'Korea Republic', 'STD003', '2022-11-24 16:00:00', N'Group stage'),
('M202215', N'Portugal', N'Ghana', 'STD007', '2022-11-24 19:00:00', N'Group stage'),
('M202216', N'Brazil', N'Serbia', 'STD001', '2022-11-24 22:00:00', N'Group stage'),
('M202263', N'Croatia', N'Morocco', 'STD005', '2022-12-17 18:00:00', N'Third place'),
('M202264', N'Argentina', N'France', 'STD001', '2022-12-18 18:00:00', N'Final'),
('M201801', N'Russia', N'Saudi Arabia', 'STD009', '2018-06-14 18:00:00', N'Group stage'),
('M201802', N'Egypt', N'Uruguay', 'STD020', '2018-06-15 17:00:00', N'Group stage'),
('M201803', N'Morocco', N'Iran', 'STD010', '2018-06-15 18:00:00', N'Group stage'),
('M201804', N'Portugal', N'Spain', 'STD011', '2018-06-15 21:00:00', N'Group stage'),
('M201805', N'France', N'Australia', 'STD016', '2018-06-16 13:00:00', N'Group stage'),
('M201806', N'Argentina', N'Iceland', 'STD013', '2018-06-16 16:00:00', N'Group stage'),
('M201807', N'Peru', N'Denmark', 'STD018', '2018-06-16 19:00:00', N'Group stage'),
('M201808', N'Croatia', N'Nigeria', 'STD019', '2018-06-16 21:00:00', N'Group stage'),
('M201863', N'Belgium', N'England', 'STD010', '2018-07-14 17:00:00', N'Third place'),
('M201864', N'France', N'Croatia', 'STD009', '2018-07-15 18:00:00', N'Final'),
('M201401', N'Brazil', N'Croatia', 'STD023', '2014-06-12 17:00:00', N'Group stage'),
('M201402', N'Mexico', N'Cameroon', 'STD031', '2014-06-13 13:00:00', N'Group stage'),
('M201403', N'Spain', N'Netherlands', 'STD026', '2014-06-13 16:00:00', N'Group stage'),
('M201404', N'Chile', N'Australia', 'STD029', '2014-06-13 18:00:00', N'Group stage'),
('M201405', N'Colombia', N'Greece', 'STD025', '2014-06-14 13:00:00', N'Group stage'),
('M201463', N'Brazil', N'Netherlands', 'STD022', '2014-07-12 17:00:00', N'Third place'),
('M201464', N'Germany', N'Argentina', 'STD021', '2014-07-13 16:00:00', N'Final');
GO

----------------------------------------------------------------------
-- B??C 5: MÔ PH?NG QUÁ TRÌNH BÁN VÉ (LOGIC GI? HÀNG 1-N) - ?Ã S?A L?I
----------------------------------------------------------------------
PRINT 'STEP 5: Auto-simulating transactions for ALL matches (Cart Logic) - FIXED...';

-- Khai báo các bi?n ?? l?p (cursor)
DECLARE @match_id CHAR(10), @stadium_id CHAR(10), @tournament NVARCHAR(100), @match_date DATETIME;

-- Khai báo cursor ?? l?p qua T?T C? các tr?n ??u
DECLARE match_cursor CURSOR FOR
SELECT match_id, stadium_id, tournament, match_date
FROM Matches;

OPEN match_cursor;
FETCH NEXT FROM match_cursor INTO @match_id, @stadium_id, @tournament, @match_date;

-- B?t ??u l?p qua t?ng TR?N ??U
WHILE @@FETCH_STATUS = 0
BEGIN
    PRINT ' -> Processing match: ' + @match_id;

    -- Bi?n chung cho m?i tr?n
    DECLARE @base_price DECIMAL(12, 2);
    DECLARE @num_orders_per_match INT;

    -- 1. Thi?t l?p giá vé và s? l??ng ??n hàng cho tr?n ??u
    SELECT @base_price = CASE
        WHEN @tournament = 'Final' THEN 20000000
        WHEN @tournament = 'Third place' THEN 10000000
        WHEN @tournament LIKE '%Group%' THEN 3000000
        ELSE 8000000 -- Các vòng knock-out khác
    END;
    
    -- Sinh ng?u nhiên 50-100 ??n hàng cho m?i tr?n
    SET @num_orders_per_match = CAST(RAND() * 50 AS INT) + 50;

    -- 2. Vòng l?p bên trong: T?o các ??N HÀNG
    DECLARE @order_counter INT = 1;
    WHILE @order_counter <= @num_orders_per_match
    BEGIN
        -- Bi?n cho m?i ??n hàng
        DECLARE @order_id CHAR(10) = 'O' + FORMAT(NEXT VALUE FOR OrderSeq, '000000');
        DECLARE @user_id CHAR(9) = 'USER000' + FORMAT(CAST(RAND() * 15 AS INT) + 1, '00');
        DECLARE @order_date DATETIME = DATEADD(DAY, -CAST(RAND() * 30 AS INT) - 1, @match_date);
        DECLARE @order_status NVARCHAR(20) = CASE WHEN RAND() > 0.1 THEN 'Paid' ELSE 'Pending' END;
        DECLARE @total_amount_for_order DECIMAL(12, 2) = 0;
        
        -- ==========================================================
        -- S?A L?I LOGIC: T?O ??N HÀNG (CHA) TR??C
        -- ==========================================================
        -- 4. Thêm ??N HÀNG (v?i t?ng ti?n t?m th?i = 0)
        INSERT INTO Orders (order_id, user_id, order_date, total_amount, status)
        VALUES (@order_id, @user_id, @order_date, 0, @order_status); -- Chèn v?i t?ng ti?n = 0

        -- 3. Vòng l?p trong cùng: T?o các VÉ (CON) cho ??n hàng
        DECLARE @num_tickets_per_order INT = CAST(RAND() * 3 AS INT) + 1; -- M?i ??n hàng có 1-4 vé
        DECLARE @ticket_counter INT = 1;
        
        WHILE @ticket_counter <= @num_tickets_per_order
        BEGIN
            DECLARE @seat_id CHAR(10) = LEFT(REPLACE(@tournament, ' ', ''), 3) + '-' + FORMAT(@order_counter * 10 + @ticket_counter, '000');
            DECLARE @final_price DECIMAL(12, 2) = @base_price + (RAND() * 1000000);
            DECLARE @ticket_id CHAR(10) = 'T' + FORMAT(NEXT VALUE FOR TicketSeq, '000000');

            -- Thêm gh? vào b?ng Seats (ph?i t?n t?i tr??c khi vé tham chi?u ??n nó)
            INSERT INTO Seats (match_id, stadium_id, seat_id, status) 
            VALUES (@match_id, @stadium_id, @seat_id, 1);
            
            -- Thêm vé vào b?ng Tickets, BÂY GI? @order_id ?Ã T?N T?I
            INSERT INTO Tickets (ticket_id, order_id, match_id, stadium_id, seat_id, price)
            VALUES (@ticket_id, @order_id, @match_id, @stadium_id, @seat_id, @final_price);

            -- C?ng d?n vào t?ng ti?n c?a ??n hàng
            SET @total_amount_for_order = @total_amount_for_order + @final_price;
            SET @ticket_counter = @ticket_counter + 1;
        END; -- K?t thúc vòng l?p VÉ

        -- ==========================================================
        -- C?P NH?T L?I ??N HÀNG V?I T?NG TI?N CHÍNH XÁC
        -- ==========================================================
        UPDATE Orders
        SET total_amount = @total_amount_for_order
        WHERE order_id = @order_id;

        -- 5. Thêm THANH TOÁN (n?u ??n hàng 'Paid')
        IF @order_status = 'Paid'
        BEGIN
            DECLARE @payment_id CHAR(10) = 'P' + FORMAT(NEXT VALUE FOR PaymentSeq, '000000');
            DECLARE @method NVARCHAR(20) = CASE CAST(RAND() * 3 AS INT)
                                            WHEN 0 THEN 'card'
                                            WHEN 1 THEN 'momo'
                                            ELSE 'paypal'
                                          END;
            INSERT INTO Payments (payment_id, order_id, method, amount, status, payment_date)
            VALUES (@payment_id, @order_id, @method, @total_amount_for_order, 'success', DATEADD(MINUTE, 1, @order_date));
        END;
        
        SET @order_counter = @order_counter + 1;
    END; -- K?t thúc vòng l?p ??N HÀNG

    -- L?y tr?n ??u ti?p theo
    FETCH NEXT FROM match_cursor INTO @match_id, @stadium_id, @tournament, @match_date;
END;

-- ?óng và gi?i phóng cursor
CLOSE match_cursor;
DEALLOCATE match_cursor;
GO

PRINT '------------------------------------------------';
PRINT 'SUCCESS: Auto-generated thousands of transactions for BongDa2.';
PRINT '------------------------------------------------';
GO