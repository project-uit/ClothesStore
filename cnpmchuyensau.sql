drop database if exists clothesshop;
create database ClothesShop;
use ClothesShop;

create table cuahang
(
id int primary key,
tencuahang nvarchar(50),
diachi nvarchar(100), 
sodienthoai nvarchar(15),
email nvarchar(50)
);
insert into cuahang
values(1,'Zalora store','35 đường Hưng pro, phường 4 quận 4,
 TpHCM','090123456789','Zalorastore@gmail.com');
create table nhacungcap
(
manhacungcap INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
tencungcap nvarchar(30) NOT NULL,
diachi nvarchar(35) NOT NULL,
email nvarchar(20),
ghichu nvarchar(50),
trangthai int
);

create table nhanvien
(
manhanvien INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
tennhanvien nvarchar(40) not null,
diachi nvarchar(35) not null,
gioitinh int not null,
ngaysinh datetime,
cmnd varchar(13),
trangthai int,
luong int 
);

insert into nhanvien(tennhanvien,diachi,gioitinh,ngaysinh,cmnd,trangthai,luong)
values('ccc', 'le hong phong',1,'1995-01-19','123456',1,50000);

create table nhomhang
(
tennhomhang nvarchar(30) COLLATE utf8_unicode_ci primary key,
trangthai int
);

create table nhasanxuat
(
tennhasanxuat nvarchar(50) COLLATE utf8_unicode_ci primary key,
trangthai int
);
-- tạo sản phẩm và chi tiết sản phẩm trước khi lập phiếu nhập, ko nhập giaban, ko nhập số lượng
create table sanpham
(
masanpham char(8) primary key,
tensanpham nvarchar(30),
tennhasanxuat nvarchar(50) COLLATE utf8_unicode_ci,
tennhomhang nvarchar(30) COLLATE utf8_unicode_ci,
FOREIGN KEY (tennhasanxuat)
REFERENCES nhasanxuat(tennhasanxuat),
FOREIGN KEY (tennhomhang)
REFERENCES nhomhang(tennhomhang),
ghichu nvarchar(50),
giaban INT,
tonkhotoithieu int,
tonkhotoida int,
thoihan_thang int,
ngayhethan date
);

DELIMITER $$
CREATE FUNCTION GenerateLicensePlate()
    RETURNS CHAR(8)
    BEGIN
    DECLARE plate CHAR(8) DEFAULT "" ;
    WHILE LENGTH(plate) = 0 DO
        SELECT concat('S',
                'P',
                substring('ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789', rand()*36+1, 1),
                substring('ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789', rand()*36+1, 1),
                substring('ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789', rand()*36+1, 1),
                substring('ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789', rand()*36+1, 1),
                substring('ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789', rand()*36+1, 1),
                substring('ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789', rand()*36+1, 1)
                ) into @newplate;
    
        SET @rcount = -1;
        SELECT COUNT(*) INTO @rcount FROM `sanpham` WHERE `masanpham` = @newplate ;
    
        IF @rcount = 0 THEN
            SET plate = @newplate ;
        END IF ;
    END WHILE ;
 
    RETURN plate ;
    END$$
DELIMITER ;

create table mausac
(
tenmau nvarchar(30) Collate utf8_unicode_ci primary key,
trangthai int
);
insert into mausac
values ('Xanh dương',1);
insert into mausac
values ('Xanh lá',1);
insert into mausac
values ('Đỏ',1);
create table size
(
tensize char(5) primary key,
trangthai int
);
insert into size
values ('S',1);
insert into size
values ('L',1);
insert into size
values ('M',1);
create table chitietsanpham
(
machitietsanpham varchar(45)  PRIMARY KEY,
masanpham char(8),
FOREIGN KEY (masanpham)
REFERENCES sanpham(masanpham),
tensize char(5),
tenmau nvarchar(30)  Collate utf8_unicode_ci,
FOREIGN KEY (tensize)
REFERENCES size(tensize),
FOREIGN KEY (tenmau)
REFERENCES mausac(tenmau),
gioitinh int,
soluong int
);

-- phiếu nhập đc lập bởi quản lý(admin) 
create table hoadonmuahang
(
mahoadonmuahang INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
ngaynhap datetime,
manhacungcap INT(6) UNSIGNED,
FOREIGN KEY (manhacungcap)
REFERENCES nhacungcap(manhacungcap),
tongtien int
);
-- khi nhập chitiethoadonmuahang, đồng thời nhập giá bán nhưng lưu lại ở trên bảng chitietsanpham
create table chitiethoadonmuahang
(
machitiethoadonmuahang INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
masanpham char(8),
FOREIGN KEY (masanpham)
REFERENCES sanpham(masanpham),
soluongsanphamnhap int,
giavon int,
thanhtien int,
mahoadonmuahang  INT(6) UNSIGNED,
FOREIGN KEY (mahoadonmuahang)
REFERENCES hoadonmuahang(mahoadonmuahang)
);

-- kho đc nhập bởi nhân viên, mỗi nhân viên click vào phiếu nhập đc lập vào ngày hiện tại để nhập kho
-- Khi nhập số lượng mới chỉ việc cộng dồn lại trên bảng chitietsanpham
create table nhapkho 
(
manhapkho   int(6) unsigned auto_increment  PRIMARY KEY,
manhanvien int(6) unsigned,
mahoadonmuahang  INT(6) UNSIGNED,
FOREIGN KEY (mahoadonmuahang)
REFERENCES hoadonmuahang(mahoadonmuahang),
FOREIGN KEY (manhanvien)
REFERENCES nhanvien(manhanvien)
);

create table chitietnhapkho
(
machitietnhapkho int (6) unsigned auto_increment  PRIMARY KEY,
manhapkho  int(6) unsigned,
machitietsanpham varchar(45),
soluong int,

FOREIGN KEY (machitietsanpham)
REFERENCES chitietsanpham(machitietsanpham),
FOREIGN KEY (manhapkho)
REFERENCES nhapkho(manhapkho)
);
create table hoadon
(
mahoadon   INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
manhanvien INT(6) UNSIGNED,
FOREIGN KEY (manhanvien)
REFERENCES nhanvien(manhanvien),
ngayban datetime,
tongtien int 
);
-- thanh toán hóa đơn bằng cách nhập machitietsanpham đc định nghĩa do user
create table chitiethoadon
(
machitiethoadon  INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
mahoadon   INT(6) UNSIGNED,
FOREIGN KEY (mahoadon)
REFERENCES hoadon(mahoadon),
machitietsanpham varchar(30),
FOREIGN KEY (machitietsanpham)
REFERENCES chitietsanpham(machitietsanpham),
soluongmua int,
thanhtien int
);
create table khachhang
(
sodienthoai char(15) PRIMARY KEY,
tenkhachhang nvarchar(50)
);

insert into khachhang
values ('0909478','Hope');
insert into khachhang
values ('0905678','Hand');

create table chitietkhachhang
(
machitietkhachhang  INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
sodienthoai char(15),
FOREIGN KEY (sodienthoai)
REFERENCES khachhang(sodienthoai),
mahoadon   INT(6) UNSIGNED,
FOREIGN KEY (mahoadon)
REFERENCES hoadon(mahoadon)
);

CREATE TABLE dangnhap(
tentaikhoan varchar(55) not null primary key,
matkhau varchar(55) not null,
phanquyen int not null,
manhanvien INT(6) UNSIGNED,
FOREIGN KEY (manhanvien)
REFERENCES nhanvien(manhanvien)
);

insert into dangnhap(tentaikhoan, matkhau,phanquyen,manhanvien)
values ('admin','123',1,1);

create table doitra
(
madoitra INT NOT NULL PRIMARY KEY auto_increment,
mahoadon INT(6) unsigned,
ngaytra datetime,
lydo nvarchar(300),
FOREIGN KEY (mahoadon)
REFERENCES hoadon(mahoadon)
);

create table chitietdoitra
(
machitietdoitra INT NOT NULL PRIMARY KEY auto_increment,
madoitra int,
machitietsanpham varchar(45),
soluong int(6),
FOREIGN KEY (machitietsanpham)
REFERENCES chitietsanpham(machitietsanpham),
FOREIGN KEY (madoitra)
REFERENCES doitra(madoitra)
);

create table hoadondoitra
(
mahoadondoitra INT NOT NULL PRIMARY KEY auto_increment,
madoitra INT(6),
thanhtien int(6),
FOREIGN KEY (madoitra)
REFERENCES doitra(madoitra)
);

create table chitiethoadondoitra
(
machitiethoadondoitra INT NOT NULL PRIMARY KEY auto_increment,
mahoadondoitra INT,
machitietsanpham varchar(45),
soluong int(6),
thanhtien int,
FOREIGN KEY (machitietsanpham)
REFERENCES chitietsanpham(machitietsanpham),
FOREIGN KEY (mahoadondoitra)
REFERENCES hoadondoitra(mahoadondoitra)
);
--  thống kê theo masanpham theo 1 tháng 
DROP TRIGGER IF EXISTS before_nhanvien_delete;

create trigger before_nhanvien_delete
before delete ON nhanvien
for each row
	delete from dangnhap 
    	where dangnhap.manhanvien = old.manhanvien;
        
DROP TRIGGER IF EXISTS before_hoadonmuahang_delete;

create trigger before_hoadonmuahang_delete 
before delete ON hoadonmuahang
for each row
	delete from chitiethoadonmuahang 
    where chitiethoadonmuahang.mahoadonmuahang=old.mahoadonmuahang;

DROP TRIGGER IF EXISTS before_sanpham_delete;
create trigger before_sanpham_delete 
before delete ON sanpham
for each row
	delete from chitietsanpham 
    where chitietsanpham.masanpham=old.masanpham and old.giaban is null;

DROP TRIGGER IF EXISTS before_hoadon_delete;
DELIMITER $$
create trigger before_hoadon_delete 
before delete ON hoadon
for each row
BEGIN
	delete from chitiethoadon 
    where chitiethoadon.mahoadon=old.mahoadon;    
    delete from chitietkhachhang
    where chitietkhachhang.mahoadon = old.mahoadon;
END$$
DELIMITER ;

drop PROCEDURE IF EXISTS update_soluong_ctsp_sauthanhtoan;
DELIMITER $$
CREATE PROCEDURE update_soluong_ctsp_sauthanhtoan(IN mactsp varchar(33), in soluongmua int)
BEGIN
	Declare soluongsp int default 0;
    set soluongsp= (select soluong from chitietsanpham where machitietsanpham=mactsp) - soluongmua;
    update chitietsanpham
    set soluong=soluongsp
    where machitietsanpham = mactsp;
END; $$
DELIMITER ;

drop PROCEDURE IF EXISTS update_soluong_ctsp_huythanhtoan;
DELIMITER $$
CREATE PROCEDURE update_soluong_ctsp_huythanhtoan(IN mactsp varchar(33), in soluongmua int)
BEGIN
	Declare soluongsp int default 0;
    set soluongsp= (select soluong from chitietsanpham where machitietsanpham=mactsp) + soluongmua;
    update chitietsanpham
    set soluong=soluongsp
    where machitietsanpham = mactsp;
END; $$
DELIMITER ;

-- xuất 5 mặt hàng bán chạy trong 1 quý tháng nằm trong khoảng (x,y)
drop PROCEDURE if exists soluongban_theoquy;
DELIMITER $$
CREATE PROCEDURE soluongban_theoquy(in quy int, in nam int)
BEGIN
DECLARE x int;
DECLARE y int;
if quy = 1 then set x=1;
elseif quy =2 then set x=4;
elseif quy = 3 then set x=7;
else set x = 10;
end if;
set y= x+2;
select  sp.masanpham, sp.tensanpham, sum(soluongmua),
sum(soluongmua)*sp.giaban as tongdoanhthu
from chitiethoadon cthd, hoadon hd, chitietsanpham ctsp, sanpham sp
where cthd.mahoadon=hd.mahoadon and ctsp.masanpham=sp.masanpham 
and cthd.machitietsanpham=ctsp.machitietsanpham 
and month(ngayban) >=  x  and  month(ngayban) <=  y
and  year(ngayban) = nam
group by sp.masanpham 
order by sum(soluongmua) desc
limit 5;
END; $$
DELIMITER ;
call soluongban_theoquy(1,2018);
DELIMITER $$
CREATE FUNCTION getTongsoluong_quy(quy int, nam int)
    RETURNS int
    BEGIN
	declare tongsoluong int default 0;
	DECLARE x int;
	DECLARE y int;
	if quy = 1 then set x=1;
	elseif quy =2 then set x=4;
	elseif quy = 3 then set x=7;
	else set x = 10;
	end if;
	set y= x+2;
    set tongsoluong = (select sum(soluongmua) from chitiethoadon cthd, hoadon hd 
	where cthd.mahoadon=hd.mahoadon and year(ngayban) = nam
	and month(ngayban) >=  x  and  month(ngayban) <=  y);
    RETURN tongsoluong ;
    END$$
DELIMITER ;

drop PROCEDURE if exists tonkholau;
DELIMITER $$
CREATE PROCEDURE tonkholau()
BEGIN

END; $$
DELIMITER ;

drop procedure if exists tonkho_hangton;
DELIMITER $$
CREATE PROCEDURE tonkho_hangton(in chucnang int)
BEGIN
if chucnang=1 then
-- tất cả
select sp.masanpham,sp.tensanpham,sp.tonkhotoithieu,
(select sum(soluong) from chitietsanpham ctsp where ctsp.masanpham=sp.masanpham),
sp.tonkhotoida
from sanpham sp;

elseif chucnang=2 then
-- hàng tồn ( hàng có trong kho)
select sp.masanpham,sp.tensanpham,sp.tonkhotoithieu, sum(soluong) as sl,
sp.tonkhotoida
from sanpham sp,chitietsanpham ctsp
where ctsp.masanpham = sp.masanpham
group by sp.masanpham
having sl>0;

elseif chucnang=3 then
-- chưa nhập vào kho
select sp.masanpham,sp.tensanpham,sp.tonkhotoithieu,soluong,sp.tonkhotoida
from sanpham sp
left join  chitietsanpham ctsp on ctsp.masanpham=sp.masanpham
where soluong is null;

elseif chucnang=4 then
-- hết hàng
select sp.masanpham,sp.tensanpham,sp.tonkhotoithieu, sum(soluong) as sl,
sp.tonkhotoida
from sanpham sp,chitietsanpham ctsp
where ctsp.masanpham = sp.masanpham
group by sp.masanpham
having sl=0;

elseif chucnang=5 then
-- sắp hết hàng
select sp.masanpham,sp.tensanpham,sp.tonkhotoithieu, sum(soluong) as sl,
sp.tonkhotoida
from sanpham sp,chitietsanpham ctsp
where ctsp.masanpham = sp.masanpham
group by sp.masanpham
having sl < sp.tonkhotoithieu and  sl>0;

elseif chucnang=6 then
-- vượt định mức
select sp.masanpham,sp.tensanpham,sp.tonkhotoithieu, sum(soluong) as sl,
sp.tonkhotoida
from sanpham sp,chitietsanpham ctsp
where ctsp.masanpham = sp.masanpham
group by sp.masanpham
having sl > sp.tonkhotoida;

elseif chucnang=7 then
-- hàng tồn kho lâu
select sp.masanpham,sp.tensanpham,sp.tonkhotoithieu,
(select sum(soluong) from chitietsanpham ctsp where ctsp.masanpham=sp.masanpham) as sl,
sp.tonkhotoida, max(date_format(pn.ngaynhap,'%d/%m/%y')) as ngaynhap,sp.ngayhethan
from sanpham sp, chitiethoadonmuahang ctpn, hoadonmuahang pn
where ctpn.masanpham = sp.masanpham and pn.mahoadonmuahang=ctpn.mahoadonmuahang
group by sp.masanpham
having date(now()) >= ngayhethan and sl > 0;

End if;
END; $$
DELIMITER ;

drop PROCEDURE if exists update_ngayhethan_sp;
DELIMITER $$
CREATE PROCEDURE update_ngayhethan_sp(in mapn int, in masp char(8) )
BEGIN
declare ngay Date;
declare thoihan int;
set thoihan = (select thoihan_thang from sanpham where masanpham=masp);
set ngay = (select Date(ngaynhap) from hoadonmuahang where mahoadonmuahang = mapn);
update sanpham 
set ngayhethan=DATE_ADD(ngay, INTERVAL thoihan MONTH) 
where masanpham=masp;
END; $$
DELIMITER ;

call update_ngayhethan_sp(3,'SPY27INH');
-- 'SPG7DW6U', 'SPFZA92'

select ctpn.masanpham, ngaynhap, giavon, ctpn.mahoadonmuahang
from chitiethoadonmuahang ctpn,hoadonmuahang pn 
where ctpn.mahoadonmuahang = pn.mahoadonmuahang  and ctpn.mahoadonmuahang =  
(SELECT ctpn1.mahoadonmuahang 
FROM chitiethoadonmuahang ctpn1,hoadonmuahang pn1 
WHERE  ctpn1.mahoadonmuahang = pn1.mahoadonmuahang  and ctpn1.masanpham = ctpn.masanpham            
ORDER BY ctpn1.mahoadonmuahang DESC
LIMIT 1);

select  sp.masanpham,sp.tensanpham,sp.tennhomhang,sp.tennhasanxuat,
ctsp.tenmau,ctsp.gioitinh, ctsp.tensize,ctsp.soluong,sp.giaban 
from sanpham sp 
join chitietsanpham ctsp on sp.masanpham = ctsp.masanpham 
where ctsp.soluong >=0;
