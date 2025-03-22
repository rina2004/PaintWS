CREATE DATABASE Paint_Store;
USE Paint_Store;

-- Tạo bảng Users
CREATE TABLE Users (
    UserID INT IDENTITY(1,1) PRIMARY KEY,  -- Sử dụng UserID làm khóa chính
    UserName NVARCHAR(50) NOT NULL,
    Password NVARCHAR(255) NOT NULL,
    RoleID INT NOT NULL,
    Email NVARCHAR(50) NULL,
    Address NVARCHAR(200) NULL,
    Phone NVARCHAR(15) NULL
);

-- Tạo bảng Categories
CREATE TABLE Categories (
    CategoryID INT IDENTITY(1,1) NOT NULL,
    CategoryName NVARCHAR(50) NOT NULL,
    Description NTEXT NULL,
    CONSTRAINT PK_Categories PRIMARY KEY (CategoryID)
);

-- Tạo bảng Suppliers
CREATE TABLE Suppliers (
    SupplierID INT IDENTITY(1,1) NOT NULL,
    CompanyName NVARCHAR(40) NOT NULL,
    ContactName NVARCHAR(50) NULL,
    Country NVARCHAR(50) NULL,
    Phone NVARCHAR(24) NULL,
    CONSTRAINT PK_Suppliers PRIMARY KEY (SupplierID)
);

-- Tạo bảng Paints
CREATE TABLE Paints (
    ProductID INT IDENTITY(1,1) NOT NULL,
    ProductName NVARCHAR(100) NOT NULL,
    SupplierID INT NULL,
    CategoryID INT NULL,
    Volume DECIMAL(5,2) NULL,              -- Dung tích sản phẩm (lít)
    Color NVARCHAR(50) NULL,               -- Màu sắc của sơn
    UnitPrice MONEY NULL,                  -- Giá bán
    UnitsInStock SMALLINT NULL,            -- Số lượng hàng tồn kho
    QuantitySold INT NULL,                 -- Số lượng đã bán
    StarRating SMALLINT NULL CHECK (StarRating BETWEEN 1 AND 5),  -- Đánh giá sao từ 1-5
    Discontinued BIT NULL,                 -- Ngừng bán
    Image NVARCHAR(MAX) NULL,              -- Hình ảnh sản phẩm
    Description NVARCHAR(MAX) NULL,        -- Mô tả chi tiết sản phẩm
    ReleaseDate DATE NULL,                 -- Ngày phát hành sản phẩm
    Discount FLOAT NULL,                   -- Giảm giá
    Status BIT NOT NULL,                   -- Trạng thái (còn hàng, hết hàng)
    CONSTRAINT PK_Paints PRIMARY KEY (ProductID),
    CONSTRAINT FK_Paints_Suppliers FOREIGN KEY (SupplierID)
        REFERENCES Suppliers (SupplierID) ON DELETE SET NULL ON UPDATE CASCADE,
    CONSTRAINT FK_Paints_Categories FOREIGN KEY (CategoryID)
        REFERENCES Categories (CategoryID) ON DELETE SET NULL ON UPDATE CASCADE
);

-- Tạo bảng Orders
CREATE TABLE Orders (
    OrderID INT IDENTITY(1,1) NOT NULL,
    Date DATE NOT NULL,
    UserID INT NOT NULL,  -- Sử dụng UserID làm khóa ngoại thay vì UserName
    TotalMoney MONEY NULL,
    Status BIT NOT NULL,
    CONSTRAINT PK_Orders PRIMARY KEY (OrderID),
    CONSTRAINT FK_Orders_Users FOREIGN KEY (UserID)
        REFERENCES Users (UserID) ON DELETE NO ACTION
);

-- Tạo bảng OrderDetails
CREATE TABLE OrderDetails (
    OrderID INT NOT NULL,
    ProductID INT NOT NULL,
    Quantity DECIMAL(10, 2) NOT NULL,  -- Đổi sang DECIMAL để tăng độ chính xác
    UnitPrice MONEY NULL,
    Discount FLOAT NULL,
    CONSTRAINT PK_OrderDetails PRIMARY KEY (OrderID, ProductID),
    CONSTRAINT FK_OrderDetails_Products FOREIGN KEY (ProductID)
        REFERENCES Paints (ProductID) ON DELETE CASCADE,
    CONSTRAINT FK_OrderDetails_Orders FOREIGN KEY (OrderID)
        REFERENCES Orders (OrderID) ON DELETE CASCADE
);

-- Thêm dữ liệu mẫu vào bảng Users
INSERT INTO Users (UserName, Password, RoleID, Email, Address, Phone)
VALUES 
('john_doe', 'password123', 1, 'john@example.com', '123 Main St, City A', '0123456789'),
('jane_smith', 'password456', 2, 'jane@example.com', '456 Oak Ave, City B', '0987654321'),
('alex_johnson', 'password789', 1, 'alex@example.com', '789 Pine Blvd, City C', '0112233445'),
('emma_watson', 'password321', 2, 'emma@example.com', '321 Maple Rd, City D', '0223344556'),
('michael_brown', 'password654', 1, 'michael@example.com', '654 Elm Dr, City E', '0334455667');

-- Thêm dữ liệu mẫu vào bảng Categories
INSERT INTO Categories (CategoryName, Description)
VALUES 
(N'Sơn Nội Thất', N'Sơn dành cho không gian nội thất, giúp bề mặt tường trong nhà sáng và bền màu.'),
(N'Sơn Ngoại Thất', N'Sơn dành cho không gian ngoại thất, có khả năng chống chịu thời tiết khắc nghiệt.'),
(N'Sơn Lót', N'Sơn lót giúp bề mặt tường mịn màng hơn, tạo lớp nền hoàn hảo trước khi sơn phủ.'),
(N'Sơn Chống Thấm', N'Sơn chống thấm giúp bảo vệ bề mặt khỏi nước và độ ẩm, tăng độ bền cho công trình.');

-- Thêm dữ liệu mẫu vào bảng Suppliers
INSERT INTO Suppliers (CompanyName, ContactName, Country, Phone)
VALUES 
(N'Công ty TNHH Sơn Đẹp', N'Nguyễn Văn A', N'Việt Nam', N'0901234567'),
(N'Công ty Cổ phần Sơn Xanh', N'Trần Thị B', N'Việt Nam', N'0912345678'),
(N'Sơn Quốc Tế Dulux', N'John Smith', N'United Kingdom', N'+441234567890'),
(N'Công ty TNHH Nippon Paint', N'Hiroshi Tanaka', N'Nhật Bản', N'+81312345678'),
(N'Sherwin-Williams Company', N'Alice Johnson', N'USA', N'+12123456789'),
(N'Công ty TNHH Sơn Jotun', N'Lê Minh C', N'Na Uy', N'0987654321');

-- Thêm dữ liệu mẫu vào bảng Paints
INSERT INTO Paints (ProductName, SupplierID, CategoryID, Volume, Color, UnitPrice, UnitsInStock, QuantitySold, StarRating, Discontinued, Image, Description, ReleaseDate, Discount, Status)
VALUES 
(N'Sơn Nội Thất Bóng Cao Cấp', 1, 1, 18.00, N'Trắng', 1500000, 200, 50, 5, 0, 'https://product.hstatic.net/1000403408/product/sieu_bong_noi_that_thuong_hang_18l_mocup_e18f98cc12d8444a82dfa42aa53190d0_master.png', N'Sơn nội thất cao cấp, bền đẹp, dễ lau chùi.', '2023-01-15', 0.1, 1),
(N'Sơn Ngoại Thất Đặc Biệt', 2, 2, 10.00, N'Xám', 4990000, 150, 80, 4, 0, 'https://product.hstatic.net/1000403408/product/gloss_special_18l_mocup_cda7a19c9eb54571b7d43efda2612107_master.png', N'Sản phẩm sơn chống thấm màu được sản xuất với công nghệ từ Hoa Kỳ sẽ giúp tường đứng và sàn vệ sinh chống thấm tối đa. Ngoài ra, sản phẩm còn có khả năng ngăn ngừa rêu mốc, ố vượt trội.', '2023-02-10', 0.05, 1),
(N'Sơn Lót Kháng Kiềm', 3, 3, 4.00, N'Trắng', 2845000, 300, 100, 5, 0, 'https://product.hstatic.net/1000403408/product/t_slkkn_1597963299f344d4a8c8969e06dbafc3_master.png', N'Sơn lót kháng kiềm, tăng độ bền và độ bám cho lớp sơn phủ.', '2022-11-20', 0.0, 1),
(N'Sơn Chống Thấm Cao Cấp', 4, 4, 5.00, N'Xanh', 4750000, 120, 70, 4, 0, 'https://product.hstatic.net/1000403408/product/t_chong_tham_mau_bda9c168478a4cc6bdc4228e56241407_master.png', N'Sơn chống thấm dành cho các công trình ngoài trời.', '2023-03-05', 0.15, 1),
(N'Sơn Bóng Ngoại Thượng Hạng', 1, 1, 1.50, N'Nâu', 1800000, 80, 40, 5, 0, 'https://product.hstatic.net/1000403408/product/sieu_bong_ngoai_thuong_hang_18l_mocup_6f188baf11cf4f7ca6edca2c2b87f03b_master.png', N'Sơn siêu bóng nano ngoại thất với màng sơn siêu bóng mang lại cho bề mặt vẻ đẹp thẩm mỹ tối ưu. Khả năng chùi rửa dễ dàng và kháng kiếm chống lại rêu mốc tối đa.', '2023-01-25', 0.2, 1),
(N'Sơn chống Thấm Pha Xi Măng', 4, 4, 2.00, N'Đen', 2200000, 90, 60, 4, 0, 'https://product.hstatic.net/1000403408/product/son_chong_tham_pha_xi_mang_x9_0a484a0308564285bfb2cbc91fa59ecd_master.png', N'Sản phẩm sơn chống thấm pha xi măng được Color8 sản xuất với công nghệ từ Hoa Kỳ sẽ giúp tường đứng và sàn vệ sinh chống thấm tối đa. Ngoài ra, sản phẩm còn có khả năng ngăn ngừa rêu mốc, ố vượt trội.', '2022-12-10', 0.1, 1),
(N'Sơn Bóng Nội Thất Mờ', 1, 1, 3.00, N'Kem', 3400000, 210, 45, 4, 0, 'https://product.hstatic.net/1000403408/product/18l_moi_jotun_252_1768184eccde419799b464e6f3aab397_master.png', N'Sơn nội thất mờ, màu kem sang trọng, thích hợp cho không gian phòng khách.', '2023-04-01', 0.0, 1),
(N'Sơn Ngoại Thất Mịn Cao cấp', 2, 2, 12.00, N'Xanh', 830000, 100, 90, 5, 0, 'https://product.hstatic.net/1000403408/product/t_s5_669254cf8cd44183bc4e023248109a08_master.png', N'Sơn ngoại thất siêu bền, chống chịu thời tiết khắc nghiệt.', '2023-05-15', 0.05, 1),
(N'Sơn Nội Thất Bóng Ngọc Trai X3', 3, 1, 12.00, N'Xanh', 4154000, 200, 90, 5, 0, 'https://product.hstatic.net/1000403408/product/noi_that_bong_ngoc_trai_18l_mocup_f8ed73aae7ec467eb47005c590328e9a_master.png', N'Sơn nội thất bóng ngọc trai x3 có tính năng đặc biệt tạo độ bóng, làm cho không gian phòng sáng và rộng hơn, đồng thời tạo cho bề mặt sơn có độ bóng mịn, độ phủ cao, bền đẹp hơn so với các loại sơn thông thường khác.', '2023-05-15', 0.05, 1),
(N'Sơn Siêu Bóng Nano Nội Thất', 4, 1, 5.0, N'Tím', 348000, 300, 80, 5, 0, 'https://product.hstatic.net/1000403408/product/s4_6f8ec22943db48018600c4b833ef4c22_master.png', N'Sơn siêu bóng có tính năng đặc biệt tạo độ bóng, làm cho không gian phòng sáng và rộng hơn, đồng thời tạo cho bề mặt sơn có độ bóng mịn, độ phủ cao, bền đẹp hơn so với các loại sơn thông thường khác.', '2023-05-15', 0.05, 1),
(N'Sơn Lót Chống Kiềm Ngoại Thất Đặc Biệt X8', 5, 2, 18.00, N'Vàng', 2814000, 150, 90, 5, 0, 'https://product.hstatic.net/1000403408/product/18l_moi_jotun_x8_cfe10279d6c54e29bd6e2f70d9ab0a19_master.png', N'Sơn lót chống kiềm ngoại thất cao cấp được Color8 sản xuất với các tính năng nổi bật:Độ che phủ cao, độ bền màu cao, Chống kiềm hóa, rêu mốc.', '2023-05-15', 0.05, 1),
(N'Sơn Mịn Nội Thất Nano Cao Cấp K300', 2, 1, 18.00, N'Xanh', 2231000, 100, 70, 5, 0, 'https://product.hstatic.net/1000403408/product/son_bong_mo_18l_mocup_b0baa588ad1242499a2bdbdf7f3fca46_master.png', N'Sơn nội thất lau chùi hiệu quả là loại sơn nội thất cao cấp, giải pháp giúp tường nhà bạn luôn tươi mới, sạch sáng nhờ công nghệ tiên tiến ngăn vết bẩn thấm sâu vào màng sơn khiến chúng dễ dàng bị đánh bật chỉ với chiếc khăn ướt hoặc xà phòng mà không hề làm tổn hại màng sơn.', '2023-05-15', 0.05, 1);

-- Thêm dữ liệu mẫu vào bảng Orders
INSERT INTO Orders (Date, UserID, TotalMoney, Status)
VALUES 
('2023-01-15', 1, 3000000, 1),  -- Đơn hàng của người dùng có UserID = 1
('2024-01-20', 2, 2845000, 1),  -- Đơn hàng của người dùng có UserID = 2
('2024-01-22', 3, 2490000, 0),  -- Đơn hàng của người dùng có UserID = 3
('2024-01-25', 1, 2500000, 1),  -- Đơn hàng thứ 2 của người dùng có UserID = 1
('2024-01-30', 4, 4400000, 1),  -- Đơn hàng của người dùng có UserID = 4
('2024-02-02', 2, 2845000, 0),  -- Đơn hàng thứ 2 của người dùng có UserID = 2
('2024-02-05', 5, 6000000, 1);  -- Đơn hàng của người dùng có UserID = 5

-- Thêm dữ liệu mẫu vào bảng OrderDetails
INSERT INTO OrderDetails (OrderID, ProductID, Quantity, UnitPrice, Discount)
VALUES 
(1, 1, 2, 3000000, 0.1),  -- 2 sản phẩm Sơn Nội Thất Bóng Cao Cấp với đơn hàng ID = 1
(1, 3, 1, 2845000, 0.0),  -- 1 sản phẩm Sơn Lót Kháng Kiềm với đơn hàng ID = 1
(2, 2, 3, 2490000, 0.05), -- 3 sản phẩm Sơn Ngoại Mịn Cao Cấp ID = 2
(3, 4, 1, 4750000, 0.15), -- 1 sản phẩm Sơn Chống Thấm Cao Cấp với đơn hàng ID = 3
(4, 5, 2, 4400000, 0.0),  -- 2 sản phẩm Sơn Chống Thấm Pha Xi Măng với đơn hàng ID = 4
(5, 6, 1, 2845000, 0.1),  -- 1 sản phẩm Sơn Lót Kháng Kiềm với đơn hàng ID = 5
(6, 1, 4, 6000000, 0.0);  -- 4 sản phẩm Sơn Nội Thất Bóng Cao Cấp với đơn hàng ID = 6