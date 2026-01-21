/*
================================================================================
SCRIPT T?O D? LI?U WORLD CUP (2014-2022) CHO DATABASE BONGDA2
PHIÊN B?N M?I: USER_ID (PER000001 - CHAR(9)) VÀ MATCH_ID (M001 - CHAR(4))
================================================================================
*/

-- BƯỚC 0: SỬ DỤNG DATABASE VÀ TẠO CÁC SEQUENCE CẦN THIẾT
USE BongDa;
GO

-- Tao sequences sinh cac to hop
IF NOT EXISTS (SELECT * FROM sys.sequences WHERE name = 'OrderSeq')
    CREATE SEQUENCE OrderSeq START WITH 1 INCREMENT BY 1;
GO
IF NOT EXISTS (SELECT * FROM sys.sequences WHERE name = 'TicketSeq')
    CREATE SEQUENCE TicketSeq START WITH 1 INCREMENT BY 1;
GO
IF NOT EXISTS (SELECT * FROM sys.sequences WHERE name = 'PaymentSeq')
    CREATE SEQUENCE PaymentSeq START WITH 1 INCREMENT BY 1;
GO
IF NOT EXISTS (SELECT * FROM sys.sequences WHERE name = 'SeatSeq')
    CREATE SEQUENCE SeatSeq START WITH 1 INCREMENT BY 1;
GO

----------------------------------------------------------------------
-- BƯỚC 1: DỌN SẠCH TOÀN BỘ DỮ LIỆU CŨ
----------------------------------------------------------------------
PRINT 'STEP 1: Deleting old data...';
DELETE FROM Payments;
DELETE FROM Tickets;
DELETE FROM Orders;
DELETE FROM Seats;
DELETE FROM Matches;
DELETE FROM Users;
DELETE FROM Stadiums;
GO

----------------------------------------------------------------------
-- BƯỚC 2: THÊM DỮ LIỆU SÂN VẬN ĐỘNG (STADIUMS)
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
-- BƯỚC 3: THÊM DỮ LIỆU NGƯỜI DÙNG (USERS) - ĐÃ SỬA ID VỀ CHAR(9)
----------------------------------------------------------------------
PRINT N'BƯỚC 3: Thêm Người Dùng (PER000001 - 9 ký tự)...';
INSERT INTO Users (user_id, full_name, email, phone, pass) VALUES
('PER000001', N'Nguyễn Hoàng Anh', 'anh.nguyen@email.com', '0912345601', 'pass123'),
('PER000002', N'Trần Minh Tuấn', 'tuan.tran@email.com', '0987654302', 'pass123'),
('PER000003', N'Lê Thị Thuỳ Linh', 'linh.le@email.com', '0905558803', 'pass123'),
('PER000004', N'Phạm Gia Hân', 'han.pham@email.com', '0918123404', 'pass123'),
('PER000005', N'Võ Quốc Bảo', 'bao.vo@email.com', '0935678905', 'pass123'),
('PER000006', N'Đỗ Khánh Huyền', 'huyen.do@email.com', '0977888906', 'pass123'),
('PER000007', N'Hoàng Trung Kiên', 'kien.hoang@email.com', '0944555607', 'pass123'),
('PER000008', N'Mai Phương Thảo', 'thao.mai@email.com', '0913456708', 'pass123'),
('PER000009', N'Lý Đức Minh', 'minh.ly@email.com', '0988111209', 'pass123'),
('PER000010', N'Bùi Thanh Trúc', 'truc.bui@email.com', '0902333410', 'pass123'),
('PER000011', N'Trương Công Vinh', 'vinh.truong@email.com', '0965874111', 'pass123'),
('PER000012', N'Dương Ngọc Ánh', 'anh.duong@email.com', '0911223312', 'pass123'),
('PER000013', N'Huỳnh Tấn Phát', 'phat.huynh@email.com', '0922334413', 'pass123'),
('PER000014', N'Đặng Minh Châu', 'chau.dang@email.com', '0933445514', 'pass123'),
('PER000015', N'Ngô Gia Khiêm', 'khiem.ngo@email.com', '0944556615', 'pass123');
GO

----------------------------------------------------------------------
-- B??C 4: THÊM D? LI?U CÁC TR?N ??U (MATCHES) - DÙNG ĐỊNH DẠNG M001 (CHAR(4))
----------------------------------------------------------------------
PRINT 'STEP 4: Inserting Matches (M001 - 4 chars)...';
INSERT INTO Matches (match_id, home_team, away_team, stadium_id, match_date, tournament) VALUES
-- ID 2022
('M001', N'Qatar', N'Ecuador', 'STD002', '2022-11-20 19:00:00', N'Group stage'),
('M002', N'England', N'Iran', 'STD005', '2022-11-21 16:00:00', N'Group stage'),
('M003', N'Senegal', N'Netherlands', 'STD006', '2022-11-21 19:00:00', N'Group stage'),
('M004', N'United States', N'Wales', 'STD004', '2022-11-21 22:00:00', N'Group stage'),
('M005', N'Argentina', N'Saudi Arabia', 'STD001', '2022-11-22 13:00:00', N'Group stage'),
('M006', N'Denmark', N'Tunisia', 'STD003', '2022-11-22 16:00:00', N'Group stage'),
('M007', N'Mexico', N'Poland', 'STD007', '2022-11-22 19:00:00', N'Group stage'),
('M008', N'France', N'Australia', 'STD008', '2022-11-22 22:00:00', N'Group stage'),
('M009', N'Morocco', N'Croatia', 'STD002', '2022-11-23 13:00:00', N'Group stage'),
('M010', N'Germany', N'Japan', 'STD005', '2022-11-23 16:00:00', N'Group stage'),
('M011', N'Spain', N'Costa Rica', 'STD006', '2022-11-23 19:00:00', N'Group stage'),
('M012', N'Belgium', N'Canada', 'STD004', '2022-11-23 22:00:00', N'Group stage'),
('M013', N'Switzerland', N'Cameroon', 'STD008', '2022-11-24 13:00:00', N'Group stage'),
('M014', N'Uruguay', N'Korea Republic', 'STD003', '2022-11-24 16:00:00', N'Group stage'),
('M015', N'Portugal', N'Ghana', 'STD007', '2022-11-24 19:00:00', N'Group stage'),
('M016', N'Brazil', N'Serbia', 'STD001', '2022-11-24 22:00:00', N'Group stage'),
('M017', N'Croatia', N'Morocco', 'STD005', '2022-12-17 18:00:00', N'Third place'),
('M018', N'Argentina', N'France', 'STD001', '2022-12-18 18:00:00', N'Final'),
-- ID 2018
('M019', N'Russia', N'Saudi Arabia', 'STD009', '2018-06-14 18:00:00', N'Group stage'),
('M020', N'Egypt', N'Uruguay', 'STD020', '2018-06-15 17:00:00', N'Group stage'),
('M021', N'Morocco', N'Iran', 'STD010', '2018-06-15 18:00:00', N'Group stage'),
('M022', N'Portugal', N'Spain', 'STD011', '2018-06-15 21:00:00', N'Group stage'),
('M023', N'France', N'Australia', 'STD016', '2018-06-16 13:00:00', N'Group stage'),
('M024', N'Argentina', N'Iceland', 'STD013', '2018-06-16 16:00:00', N'Group stage'),
('M025', N'Peru', N'Denmark', 'STD018', '2018-06-16 19:00:00', N'Group stage'),
('M026', N'Croatia', N'Nigeria', 'STD019', '2018-06-16 21:00:00', N'Group stage'),
('M027', N'Belgium', N'England', 'STD010', '2018-07-14 17:00:00', N'Third place'),
('M028', N'France', N'Croatia', 'STD009', '2018-07-15 18:00:00', N'Final'),
-- ID 2014
('M029', N'Brazil', N'Croatia', 'STD023', '2014-06-12 17:00:00', N'Group stage'),
('M030', N'Mexico', N'Cameroon', 'STD031', '2014-06-13 13:00:00', N'Group stage'),
('M031', N'Spain', N'Netherlands', 'STD026', '2014-06-13 16:00:00', N'Group stage'),
('M032', N'Chile', N'Australia', 'STD029', '2014-06-13 18:00:00', N'Group stage'),
('M033', N'Colombia', N'Greece', 'STD025', '2014-06-14 13:00:00', N'Group stage'),
('M034', N'Brazil', N'Netherlands', 'STD022', '2014-07-12 17:00:00', N'Third place'),
('M035', N'Germany', N'Argentina', 'STD021', '2014-07-13 16:00:00', N'Final');
GO

----------------------------------------------------------------------
-- BƯỚC 5: MÔ PHỎNG QUÁ TRÌNH BÁN VÉ (LOGIC GIỎ HÀNG 1-N)
----------------------------------------------------------------------
PRINT 'STEP 5: Auto-simulating transactions with new ID formats (Block Seats A001, B002, ...)...';

-- KIỂM TRA VÀ GIẢI PHÓNG CURSOR CŨ (KHẮC PHỤC LỖI 16915 VÀ 16905)
IF CURSOR_STATUS('local', 'match_cursor') >= 0
BEGIN
    CLOSE match_cursor;
    DEALLOCATE match_cursor;
END

-- Khai báo các biến để lặp (cursor)
DECLARE @match_id CHAR(4), @stadium_id CHAR(10), @tournament NVARCHAR(100), @match_date DATETIME;
DECLARE match_cursor CURSOR LOCAL FOR
SELECT match_id, stadium_id, tournament, match_date
FROM Matches;

OPEN match_cursor;
FETCH NEXT FROM match_cursor INTO @match_id, @stadium_id, @tournament, @match_date;

-- Bắt đầu lặp qua từng TRẬN ĐẤU
WHILE @@FETCH_STATUS = 0
BEGIN
    PRINT ' -> Processing match: ' + @match_id;

    -- Biến chung cho mỗi trận
    DECLARE @base_price DECIMAL(12, 2);
    DECLARE @num_orders_per_match INT;

    -- 1. Thiết lập MỨC GIÁ CƠ SỞ theo giải đấu (Đơn vị: VND)
    SELECT @base_price = CASE
        WHEN @tournament = 'Final' THEN 200000 
        WHEN @tournament = 'Third place' THEN 100000
        WHEN @tournament LIKE '%Group%' THEN 30000
        ELSE 80000 
    END;
    
    -- Sinh ngẫu nhiên 50-100 đơn hàng cho mỗi trận
    SET @num_orders_per_match = CAST(RAND() * 50 AS INT) + 50;
    
    -- *** KHỞI TẠO BIẾN ĐẾM GHẾ CHO TRẬN ĐẤU NÀY ***
    DECLARE @seat_row_counter INT = 1;

    -- 2. Vòng lặp bên trong: Tạo các ĐƠN HÀNG
    DECLARE @order_counter INT = 1;
    WHILE @order_counter <= @num_orders_per_match
    BEGIN
        -- *** T?O ORDER ID ***
        DECLARE @order_id CHAR(10) = 'ORD' + FORMAT(NEXT VALUE FOR OrderSeq, '0000000');
        
        DECLARE @user_id CHAR(9) = 'PER' + FORMAT(CAST(RAND() * 15 AS INT) + 1, '000000');
        DECLARE @order_date DATETIME = DATEADD(DAY, -CAST(RAND() * 30 AS INT) - 1, @match_date);
        DECLARE @order_status NVARCHAR(20) = CASE WHEN RAND() > 0.1 THEN 'Paid' ELSE 'Pending' END;
        DECLARE @total_amount_for_order DECIMAL(12, 2) = 0;
        
        -- ==========================================================
        -- TẠO ĐƠN HÀNG (CHA) TRƯỚC
        -- ==========================================================
        INSERT INTO Orders (order_id, user_id, order_date, total_amount, status)
        VALUES (@order_id, @user_id, @order_date, 0, @order_status); 

        -- 3. Vòng lặp trong cùng: Tạo các VÉ (CON) cho đơn hàng
        DECLARE @num_tickets_per_order INT = CAST(RAND() * 3 AS INT) + 1;
        DECLARE @ticket_counter INT = 1;
        
        WHILE @ticket_counter <= @num_tickets_per_order
        BEGIN
            -- *** T?O SEAT ID M?I THEO DÃY (A001, B002, ...) ***
            -- Lấy chữ cái dãy ghế (luân phiên A, B, C, D)
            DECLARE @seat_block CHAR(1) = 
                CASE (@seat_row_counter - 1) % 4
                    WHEN 0 THEN 'A'
                    WHEN 1 THEN 'B'
                    WHEN 2 THEN 'C'
                    ELSE 'D' -- Xử lý số dư 3
                END;

            -- Số thứ tự ghế trong dãy, tăng lên sau mỗi vé được bán
            DECLARE @seat_number CHAR(3) = FORMAT(@seat_row_counter, '000');

            -- Ghép lại: Ví d?: A001, A002, B003, B004, C005, ...
            DECLARE @seat_id CHAR(10) = @seat_block + @seat_number;
            
            SET @seat_row_counter = @seat_row_counter + 1; -- Tăng biến đếm ghế

            DECLARE @final_price DECIMAL(12, 2) = @base_price + (RAND() * 10000);
            DECLARE @ticket_id CHAR(10) = 'TIC' + FORMAT(NEXT VALUE FOR TicketSeq, '0000000');

            -- Thêm ghế vào bang Seats.
            INSERT INTO Seats (match_id, stadium_id, seat_id, status) 
            VALUES (@match_id, @stadium_id, @seat_id, 1);
            
            -- Thêm vé vào bảng Tickets
            INSERT INTO Tickets (ticket_id, order_id, match_id, seat_id, price)
            VALUES (@ticket_id, @order_id, @match_id, @seat_id, @final_price);

            -- Cộng dồn vào tổng tiền của đơn hàng
            SET @total_amount_for_order = @total_amount_for_order + @final_price;
            SET @ticket_counter = @ticket_counter + 1;
        END; -- K?t thúc vòng l?p VÉ

        -- ==========================================================
        -- CẬP NHẬT LẠI ĐƠN HÀNG VỚI TỔNG TIỀN CHÍNH XÁC
        -- ==========================================================
        UPDATE Orders
        SET total_amount = @total_amount_for_order
        WHERE order_id = @order_id;

       -- 5. Thêm THANH TOÁN (nếu đơn hàng 'Paid')
        IF @order_status = 'Paid'
        BEGIN
            DECLARE @payment_id CHAR(10) = 'PID' + FORMAT(NEXT VALUE FOR PaymentSeq, '0000000');
            
            DECLARE @method NVARCHAR(20) = CASE CAST(RAND() * 3 AS INT)
                                            WHEN 0 THEN 'card'
                                            WHEN 1 THEN 'momo'
                                            ELSE 'paypal'
                                          END;
            INSERT INTO Payments (payment_id, order_id, method, amount, status, payment_date)
            VALUES (@payment_id, @order_id, @method, @total_amount_for_order, 'success', DATEADD(MINUTE, 1, @order_date));
        END;
        
        SET @order_counter = @order_counter + 1;
    END; -- Kết thúc vòng lặp đơn hàng

    -- Lấy trận đấu tiếp theo
    FETCH NEXT FROM match_cursor INTO @match_id, @stadium_id, @tournament, @match_date;
END;

-- Giải phóng cursor (Quan trọng: phải làm cuối cùng!)
CLOSE match_cursor;
DEALLOCATE match_cursor;
GO

PRINT '------------------------------------------------';
PRINT 'THÀNH CÔNG: Hoàn tất tạo dữ liệu với ID ghế theo dãy (A001, B002, v.v.) và giá vé được điều chỉnh.';
PRINT '------------------------------------------------';
GO