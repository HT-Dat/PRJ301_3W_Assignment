# Novel website
## Introduction
A Novel website that allows users to have access to novels written by many other people, as well as to provide a way so that writers, especially amateurs, can freely publish their own works for other readers.
## Background
This is a group assignment for PRJ301 subject at FPT University.
## Team members 
Ho Tien Dat\
Nguyen Quang Dung\
Pham Thanh Minh
## Require naming convention

- Tên biến: camelCase 
  + Đúng: productID
  + Sai: ProductID
- Tên hằng: viết hoa toàn bộ
  + Đúng: private final String LOGIN_PAGE
  + Sai: private final String loginpage
- Tên file: Viết hoa mỗi chữ cái đầu mỗi từ
  + Đúng: ViewProduct.jsp
  + Sai: view_product.jsp
- Tên file để forward, sendRedirect: bắt buộc phải khai báo dạng final String trên đầu Servlet
- Tên class: Viết hoa mỗi chữ cái đầu mỗi từ
  + Đúng: CartObject
  + Sai: cartObject
- Servlet: Viết hoa mỗi chữ cái đầu mỗi từ + "Servlet" ở cuối

- Package liên quan tới Database: tên viết thường tên giống hệt table trong DB
  + Class DAO: têntableDAO
  + Class DTO: têntableDTO
Ví dụ:
products
| ProductsDAO
| ProductsDTO
