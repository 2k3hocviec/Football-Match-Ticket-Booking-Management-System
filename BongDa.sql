CREATE DATABASE BongDa;
GO

USE BongDa;
Go

CREATE TABLE Users(
    user_id CHAR(9) NOT NULL PRIMARY KEY,
    full_name NVARCHAR(100) NOT NULL,
    email NVARCHAR(100) NOT NULL,
    phone NVARCHAR(20) NOT NULL,
    pass NVARCHAR(20) NOT NULL,
    --created DATETIME NOT NULL DEFAULT GETDATE()
);

CREATE TABLE Stadiums(
    stadium_id CHAR(10) NOT NULL PRIMARY KEY,
    name NVARCHAR(100) NOT NULL,
    location NVARCHAR(100) NOT NULL,
    capacity INT
);

CREATE TABLE Matches(
    match_id CHAR(10) NOT NULL PRIMARY KEY,
    home_team NVARCHAR(100) NOT NULL,
    away_team NVARCHAR(100) NOT NULL,
    stadium_id CHAR(10) NOT NULL,
    match_date DATETIME NOT NULL,
    tournament NVARCHAR(100),
    CONSTRAINT fk_Matches_Stadium FOREIGN KEY (stadium_id) REFERENCES Stadiums(stadium_id)
);

CREATE TABLE Seats (
    match_id CHAR(10) NOT NULL,
    stadium_id CHAR(10) NOT NULL,
    seat_id CHAR(10) NOT NULL,
    trang_thai BIT NOT NULL,
    CONSTRAINT pk_Seats PRIMARY KEY (match_id, seat_id, stadium_id),
    CONSTRAINT fk_Seats_Stadium FOREIGN KEY (stadium_id) REFERENCES Stadiums(stadium_id),
    CONSTRAINT fk_Seats_Match FOREIGN KEY (match_id) REFERENCES Matches(match_id)
);

CREATE TABLE Tickets (
    ticket_id CHAR(10) NOT NULL PRIMARY KEY,
    match_id CHAR(10) NOT NULL,
    stadium_id CHAR(10) NOT NULL,
    seat_id CHAR(10) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    --trang_thai NVARCHAR(20) NOT NULL DEFAULT 'available'
        --CHECK (trang_thai IN ('available','booked','sold')),
    CONSTRAINT fk_Tickets_Match FOREIGN KEY (match_id) REFERENCES Matches(match_id) ON DELETE CASCADE,
    CONSTRAINT fk_Tickets_Stadium FOREIGN KEY (stadium_id) REFERENCES Stadiums(stadium_id) ON DELETE CASCADE,
    CONSTRAINT fk_Tickets_Seat FOREIGN KEY (match_id, seat_id, stadium_id) REFERENCES Seats(match_id, seat_id, stadium_id) ON DELETE CASCADE
);

CREATE TABLE Orders (
    order_id CHAR(10) NOT NULL PRIMARY KEY,
    ticket_id CHAR(10) NOT NULL,
    user_id CHAR(9) NOT NULL,
    order_date DATETIME DEFAULT GETDATE(),
    total_amount DECIMAL(12,2) NOT NULL,
    --trang_thai NVARCHAR(20) NOT NULL DEFAULT 'pending'
        --CHECK (trang_thai IN ('pending','paid','cancelled')),
    CONSTRAINT fk_Orders_User FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE,
    CONSTRAINT fk_Orders_Ticket FOREIGN KEY (ticket_id) REFERENCES Tickets(ticket_id) ON DELETE CASCADE
);

CREATE TABLE Payments (
    payment_id CHAR(10) NOT NULL PRIMARY KEY,
    order_id CHAR(10) NOT NULL,
    --method NVARCHAR(20) NOT NULL
        --CHECK (method IN ('card','momo','paypal','cash')),
    amount DECIMAL(12,2) NOT NULL,
    --status NVARCHAR(20) NOT NULL DEFAULT 'pending'
        --CHECK (status IN ('success','failed','pending')),
    payment_date DATETIME DEFAULT GETDATE(),
    CONSTRAINT fk_Payments_Order FOREIGN KEY (order_id) REFERENCES Orders(order_id) ON DELETE CASCADE
);

INSERT INTO Stadiums (stadium_id, name, location, capacity)
VALUES
('STD001', N'Sân Mỹ Đình', N'Hà Nội', 48),
('STD002', N'Sân Thống Nhất', N'Hồ Chí Minh', 48);

INSERT INTO Matches (match_id, home_team, away_team, stadium_id, match_date, tournament)
VALUES
('M001', N'Việt Nam', N'Thái Lan', 'STD001', '2025-12-29 19:00:00', N'Friendly'),
('M002', N'Anh', N'Uruguay', 'STD002', '2025-12-30 19:00:00', N'WorldCup'),
('M003', N'Brazil', N'Đức', 'STD001', '2025-11-20 19:00:00', N'Friendly'),
('M004', N'Nhật Bản', N'Bồ Đào Nha', 'STD002', '2025-12-24 19:00:00', N'Olympic');

-- Sinh ghế cho từng trận dựa vào Matches
INSERT INTO Seats (match_id, stadium_id, seat_id, trang_thai)
SELECT 
    m.match_id,
    m.stadium_id,
    r.c + RIGHT('00' + CAST(n.n AS VARCHAR(2)), 2) AS seat_id,
    0 AS trang_thai
FROM Matches m
CROSS JOIN (VALUES ('A'),('B'),('C'),('D'),('E'),('F')) AS r(c)   -- Hàng ghế
CROSS JOIN (VALUES (1),(2),(3),(4),(5),(6),(7),(8),(9),(10)) AS n(n); -- Số ghế


--DELETE FROM Seats;


UPDATE Seats
SET trang_thai = 1
WHERE seat_id = 'B09' and match_id = 'M003';