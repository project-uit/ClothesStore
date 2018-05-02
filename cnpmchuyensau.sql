drop table  if exists khachhang;
drop table  if exists nhacungcap;
drop table  if exists nhanvien;
drop table  if exists nhasanxuat;
drop table  if exists nhomhang;
drop table if exists sanpham;
drop database if exists ClothesShop;


create database ClothesShop;
use ClothesShop;

insert into khachhang
values ('123','ccc');

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
tennhomhang nvarchar(30) PRIMARY KEY COLLATE utf8_unicode_ci
)ENGINE=INNODB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
create table nhasanxuat
(
tennhasanxuat nvarchar(50)  PRIMARY KEY COLLATE utf8_unicode_ci
)ENGINE=INNODB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
-- tạo sản phẩm và chi tiết sản phẩm trước khi lập phiếu nhập, ko nhập giaban, ko nhập số lượng
create table sanpham
(
masanpham char(8) primary key,
tensanpham nvarchar(30) not null,
tennhasanxuat nvarchar(50),
tennhomhang nvarchar(30),
ghichu nvarchar(50),
giaban INT
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
mamau int  UNSIGNED AUTO_INCREMENT PRIMARY KEY,
tenmau nvarchar(20),
trangthai int
);
create table size
(
tensize char(5) primary key
);

create table chitietsanpham
(
machitietsanpham varchar(33)  PRIMARY KEY,
masanpham char(8),
FOREIGN KEY (masanpham)
REFERENCES sanpham(masanpham),
tensize char(5),
mamau int(6)  UNSIGNED,
FOREIGN KEY (mamau)
REFERENCES mausac(mamau),
gioitinh int,
soluong int
);

-- phiếu nhập đc lập bởi quản lý(admin) 
create table phieunhap
(
maphieunhap  INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
ngaynhap datetime,
manhacungcap INT(6) UNSIGNED,
FOREIGN KEY (manhacungcap)
REFERENCES nhacungcap(manhacungcap),
tongtien int
);
-- khi nhập chitietphieunhap, đồng thời nhập giá bán nhưng lưu lại ở trên bảng chitietsanpham
create table chitietphieunhap
(
machitietphieunhap  INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
masanpham char(8),
FOREIGN KEY (masanpham)
REFERENCES sanpham(masanpham),
soluongsanphamnhap int,
giavon int,
thanhtien int,
maphieunhap  INT(6) UNSIGNED,
FOREIGN KEY (maphieunhap)
REFERENCES phieunhap(maphieunhap)
);

-- kho đc nhập bởi nhân viên, mỗi nhân viên click vào phiếu nhập đc lập vào ngày hiện tại để nhập kho
-- Khi nhập số lượng mới chỉ việc cộng dồn lại trên bảng chitietsanpham
create table khosanpham 
(
makhosanpham   int(6) unsigned auto_increment  PRIMARY KEY,
manhanvien int(6) unsigned,
maphieunhap  INT(6) UNSIGNED,
FOREIGN KEY (maphieunhap)
REFERENCES phieunhap(maphieunhap),
FOREIGN KEY (manhanvien)
REFERENCES nhanvien(manhanvien)
);

insert into hoadon(manhanvien,sodienthoai,ngayban,tongtien)
 values(1,'123',now(),500);
create table hoadon
(
mahoadon   INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
manhanvien INT(6) UNSIGNED,
FOREIGN KEY (manhanvien)
REFERENCES nhanvien(manhanvien),
ngayban datetime,
tongtien int 
);
SELECT NOW();
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
--  thống kê theo masanpham theo 1 tháng 
DROP TRIGGER IF EXISTS before_nhanvien_delete;

create trigger before_nhanvien_delete
before delete ON nhanvien
for each row
	delete from dangnhap 
    	where dangnhap.manhanvien = old.manhanvien;
        
DROP TRIGGER IF EXISTS before_phieunhap_delete;

create trigger before_phieunhap_delete 
before delete ON phieunhap
for each row
	delete from chitietphieunhap 
    where chitietphieunhap.maphieunhap=old.maphieunhap;

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

CALL update_soluong_ctsp_huythanhtoan('SP0CEOYG01231',3);
