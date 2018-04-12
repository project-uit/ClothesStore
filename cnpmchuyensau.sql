drop table khachhang;
drop table nhacungcap;
drop table nhanvien;
drop table nhasanxuat;
drop table nhomhang;
drop table thoigianlamviec;
drop table sanpham ;

create database ClothesShop
use ClothesShop
create table khachhang
(
makhachhang int(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
tenkhachhang nvarchar(50) not null,
sodienthoai char(13)
);
insert into khachhang
values (1,'test1','090');
insert into khachhang(tenkhachhang, sodienthoai)
values ('test2','09110');
insert into khachhang(tenkhachhang, sodienthoai)
values ('chú','09111110');

select *
from khachhang
where tenkhachhang = 'chú'
Collate utf8_unicode_ci;

create table nhacungcap
(
manhacungcap INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
tencungcap nvarchar(30) NOT NULL,
diachi nvarchar(35) NOT NULL,
email nvarchar(20),
ghichu nvarchar(50)
);
create table thoigianlamviec
(
tenca char(5) PRIMARY KEY,
giolam nvarchar(40)
);
create table nhanvien
(
manhanvien INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
tenhanvien nvarchar(40) not null,
diachi nvarchar(35) not null,
gioitinh int not null,
ngaysinh datetime,
tenca char(5),
FOREIGN KEY (tenca)
REFERENCES thoigianlamviec(tenca),
cmnd varchar(13),
trangthai int,
luong int 
);

create table nhomhang
(
manhomhang  INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
tennhomhang nvarchar(30) not null
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
manhomhang  INT(6) UNSIGNED,
FOREIGN KEY (manhomhang)
REFERENCES nhomhang(manhomhang),
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

create table nhapkho
(
manhapkho   INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
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
manhapkho   INT(6) UNSIGNED,
FOREIGN KEY (manhapkho)
REFERENCES nhapkho(manhapkho),
soluong int not null
);

CREATE TABLE dangnhap(
id int not null primary key auto_increment,
tentaikhoan varchar(55) not null,
matkhau varchar(55) not null,
phanquyen int not null,
manhanvien INT(6) UNSIGNED,
FOREIGN KEY (manhanvien)
REFERENCES nhanvien(manhanvien)
);


