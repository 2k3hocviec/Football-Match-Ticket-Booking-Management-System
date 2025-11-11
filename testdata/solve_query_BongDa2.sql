use BongDa;
--reset data
UPDATE Seats SET status = 0

DELETE FROM Payments;
Go

DELETE FROM Tickets;
Go

DELETE FROM Orders;
Go

DELETE FROM Users;
Go

--Liet ke cac tran dau duoc to chuc tai san van dong Lusail Iconic Stadium, Lusail (bỏ)
select *
from Matches
where stadium_id = 'STD001'

-- Tinh tong doanh thu tu tat ca cac ve da ban trong co so du lieu(bo)
select sum(total_amount) as TongDoanhThu
from Orders

-- Tim 5 khach hang chi tieu nhieu nhat
select top 5 u.user_id, u.full_name, sum(o.total_amount) as TongChiTieu 
from Orders o
join Users u on o.user_id = u.user_id
group by u.user_id, u.full_name
order by Sum(o.total_amount) DESC

-- Co bao nhieu ve da duoc ban cho tran chung ket World Cup 2022(bo)
select count(*) as TongSoVeBanDuoc
from Matches m
join Tickets t on m.match_id = t.match_id
where m.match_id = 'M202264'


-- Gia ve trung binh cho moi vong dau
select distinct m.tournament, avg(t.price) as GiaVeTrungBinh
from Matches m
join Tickets t on m.match_id = t.match_id
group by m.tournament
order by avg(t.price) desc



-- Khung gio nao co nhieu nguoi dat ve nhat
select
	case
		when DATEPART(hour, o.order_date) between 0 and 5 then '0h - 6h (Early Morning)'
		when DATEPART(hour, o.order_date) between 6 and 11 then '6h - 11h (Morning)'
		when DATEPART(hour, o.order_date) between 12 and 17 then '11h - 17h (Afternoon)'
		else '18h - 24h (Evening)'
	end as FrameTime,
	count(t.ticket_id) as Quantity
from Orders o
join Tickets t on o.order_id = t.order_id
group by case
		when DATEPART(hour, o.order_date) between 0 and 5 then '0h - 6h (Early Morning)'
		when DATEPART(hour, o.order_date) between 6 and 11 then '6h - 11h (Morning)'
		when DATEPART(hour, o.order_date) between 12 and 17 then '11h - 17h (Afternoon)'
		else '18h - 24h (Evening)'
	end
order by Quantity DESC



-- Doi bong nao xuat hien trong cac tran dau co doanh thu cao nhat


WITH TotalRevenuePerMatch AS (
    SELECT 
        ma.match_id, 
        ma.home_team, 
        ma.away_team, 
        SUM(p.amount) AS TotalRevenue
    FROM Matches ma
    JOIN (
        SELECT DISTINCT order_id, match_id
        FROM Tickets
    ) ti ON ma.match_id = ti.match_id
    JOIN Payments p ON p.order_id = ti.order_id
    GROUP BY ma.match_id, ma.home_team, ma.away_team
),
MergeHomeAndAwayTeams AS (
    SELECT home_team AS TeamName, TotalRevenue FROM TotalRevenuePerMatch
    UNION ALL
    SELECT away_team AS TeamName, TotalRevenue FROM TotalRevenuePerMatch
)
SELECT TOP 5 
    TeamName, 
    SUM(TotalRevenue) AS TotalRevenue
FROM MergeHomeAndAwayTeams
GROUP BY TeamName
ORDER BY SUM(TotalRevenue) DESC;





-- Truy van xep hang cac san van dong dua tren doanh thu 
with TongDoanhThu as (
	select s.name, Sum(o.total_amount) as TongDoanhThu
	from Stadiums s
	join Tickets t on s.stadium_id = t.stadium_id
	join Orders o on o.order_id = t.order_id
	group by s.name
)
select DENSE_RANK() OVER (ORDER BY t.TongDoanhThu DESC) AS Hang, t.name, t.TongDoanhThu
from TongDoanhThu t
order by Hang

WITH StadiumPayment AS (
    SELECT DISTINCT 
        s.name, 
        p.payment_id,
        p.amount
    FROM Stadiums s
    JOIN Matches m ON s.stadium_id = m.stadium_id
    JOIN Tickets t ON m.match_id = t.match_id
    JOIN Orders o ON o.order_id = t.order_id
    JOIN Payments p ON p.order_id = o.order_id
)
, TotalRevenue AS (
    SELECT 
        Name,
        SUM(amount) AS TotalRevenue
    FROM StadiumPayment
    GROUP BY name
)
SELECT 
    DENSE_RANK() OVER (ORDER BY TotalRevenue DESC) AS Rank,
    Name,
    TotalRevenue
FROM TotalRevenue
ORDER BY Rank;





-- Tim khach hang trung thanh(chua test duoc data)
with temp as (
	select distinct u.user_id, u.full_name, YEAR(o.order_date) as NamDienRa 
	from users u
	join Orders o on o.user_id = u.user_id
	join Tickets t on o.order_id = t.order_id
)

select user_id, full_name, count(NamDienRa) as SoLuongKyWorldCupDaMua
from temp
group by user_id, full_name
having count(NamDienRa) > 1
order by SoLuongKyWorldCupDaMua DESC
