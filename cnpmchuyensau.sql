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
where tenkhachhang = 'chú'
Collate utf8_unicode_ci;

create table khachhang
(
sodienthoai char(15) PRIMARY KEY,
tenkhachhang nvarchar(50)
);

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
tennhomhang nvarchar(30) PRIMARY KEY
);
create table nhasanxuat
(
tennhasanxuat nvarchar(50)  PRIMARY KEY
);
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

insert into sanpham(masanpham,tensanpham,tennhasanxuat,tennhomhang,ghichu) values ("12344","aokhoacda","nike","ao","");
insert into sanpham(masanpham,tensanpham,tennhasanxuat,tennhomhang,ghichu) values ("12346","aokhoacbong","adidas","ao","");
insert into sanpham(masanpham,tensanpham,tennhasanxuat,tennhomhang,ghichu) values ("12347","quandai","apple","quan","");
insert into sanpham(masanpham,tensanpham,tennhasanxuat,tennhomhang,ghichu) values ("12348","quanngan","nike","quan","");
insert into sanpham(masanpham,tensanpham,tennhasanxuat,tennhomhang,ghichu) values ("12349","aonguc","victoriasecret","aovu","");

create table chitietsanpham
(
machitietsanpham varchar(30)  PRIMARY KEY,
masanpham char(8) ,
FOREIGN KEY (masanpham)
REFERENCES sanpham(masanpham),
size char(5),
mausac nvarchar(15),
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

create table hoadon
(
mahoadon   INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
sodienthoai char(15),
FOREIGN KEY (sodienthoai)
REFERENCES khachhang(sodienthoai),
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
soluongmua int 
);

CREATE TABLE dangnhap(
tentaikhoan varchar(55) not null primary key,
matkhau varchar(55) not null,
phanquyen int not null,
manhanvien INT(6) UNSIGNED,
FOREIGN KEY (manhanvien)
REFERENCES nhanvien(manhanvien)
);
--  thống kê theo masanpham theo 1 tháng 
DROP TRIGGER IF EXISTS before_nhanvien_delete;

create trigger before_nhanvien_delete
before delete ON nhanvien
for each row
	delete from dangnhap 
    	where dangnhap.manhanvien = old.manhanvien;
        

insert into dangnhap(tentaikhoan, matkhau,phanquyen,manhanvien)
values ('admin','123',1,1);

DROP TRIGGER IF EXISTS before_phieunhap_delete;


create trigger before_phieunhap_delete 
before delete ON phieunhap
for each row
	delete from chitietphieunhap 
    where chitietphieunhap.maphieunhap=old.maphieunhap;
 
