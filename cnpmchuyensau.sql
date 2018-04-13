drop table khachhang;
drop table nhacungcap;
drop table nhanvien;
drop table nhasanxuat;
drop table nhomhang;
drop table sanpham ;

create database ClothesShop
drop database ClothesShop
use ClothesShop

select *
from khachhang
where tenkhachhang = 'chú'
Collate utf8_unicode_ci;

create table khachhang
(
makhachhang int(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
tenkhachhang nvarchar(50) not null,
sodienthoai char(13)
);

create table nhacungcap
(
manhacungcap INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
tencungcap nvarchar(30) NOT NULL,
diachi nvarchar(35) NOT NULL,
email nvarchar(20),
ghichu nvarchar(50)
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
tennhomhang nvarchar(30) PRIMARY KEY
);
create table nhasanxuat
(
tennhasanxuat nvarchar(50)  PRIMARY KEY
);
create table sanpham
(
masanpham char(8) primary key,
tensanpham nvarchar(30) not null,
tennhasanxuat nvarchar(50),
tennhomhang nvarchar(30),
ghichu nvarchar(50)
);
create table phieunhap
(
maphieunhap  INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
ngaynhap datetime,
tennhanvien nvarchar(40),
manhacungcap INT(6) UNSIGNED,
FOREIGN KEY (manhacungcap)
REFERENCES nhacungcap(manhacungcap),
tongtien int not null
);

create table chitietphieunhap
(
machitietphieunhap  INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
masanpham char(8),
FOREIGN KEY (masanpham)
REFERENCES sanpham(masanpham),
soluong int not null,
giavon int not null,
thanhtien int not null,
maphieunhap  INT(6) UNSIGNED,
FOREIGN KEY (maphieunhap)
REFERENCES phieunhap(maphieunhap)
);

create table khosanpham 
(
makhosanpham   INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
masanpham char(8),
FOREIGN KEY (masanpham)
REFERENCES chitietphieunhap(masanpham),
size char(5) not null,
mausac nvarchar(15),
gioitinh int,
giaban INT not null,
soluong int
);

create table hoadon
(
mahoadon   INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
manhanvien INT(6) UNSIGNED,
FOREIGN KEY (manhanvien)
REFERENCES nhanvien(manhanvien),
makhachhang INT(6) UNSIGNED,
FOREIGN KEY (makhachhang)
REFERENCES khachhang(makhachhang),
ngayban datetime,
tongtien int not null
);

create table chitiethoadon
(
machitiethoadon  INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
mahoadon   INT(6) UNSIGNED,
FOREIGN KEY (mahoadon)
REFERENCES hoadon(mahoadon),
makhosanpham   INT(6) UNSIGNED,
FOREIGN KEY (makhosanpham)
REFERENCES khosanpham(makhosanpham),
soluong int not null
);

CREATE TABLE dangnhap(
tentaikhoan varchar(55) not null primary key,
matkhau varchar(55) not null,
phanquyen int not null,
manhanvien INT(6) UNSIGNED,
FOREIGN KEY (manhanvien)
REFERENCES nhanvien(manhanvien)
);

DROP TRIGGER IF EXISTS before_nhanvien_delete;

create trigger before_nhanvien_delete
before delete ON nhanvien
for each row
	delete from dangnhap 
    	where dangnhap.manhanvien = old.manhanvien;

insert into dangnhap(tentaikhoan, matkhau,phanquyen,manhanvien)
values ('admin','123',1,1);






