import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.time.LocalDate;

class Kho {
  ArrayList<HangHoa> dsHangHoa;

  public Kho() {
    dsHangHoa = new ArrayList<HangHoa>();
  }

  public void timTheoNgayNhapKho(LocalDate batDau, LocalDate ketThuc) {
    ArrayList<HangHoa> ketQua = new ArrayList<HangHoa>();
    for (HangHoa hangHoa : dsHangHoa) {
      if (hangHoa.ngayNhap.isEqual(batDau) || hangHoa.ngayNhap.isEqual(ketThuc)) {
        ketQua.add(hangHoa);
      } else if (hangHoa.ngayNhap.isAfter(batDau) && hangHoa.ngayNhap.isBefore(ketThuc)) {
        ketQua.add(hangHoa);
      }
    }
    inKetQuaTimKiem(ketQua);
  }

  public void timTheoGia(int thapNhat, int caoNhat) {
    ArrayList<HangHoa> ketQua = new ArrayList<HangHoa>();
    for (HangHoa hangHoa : dsHangHoa) {
      if (hangHoa.giaNhap >= thapNhat && hangHoa.giaNhap <= caoNhat) {
        ketQua.add(hangHoa);
      }
    }
    inKetQuaTimKiem(ketQua);
  }

  public void timTheoLoai(String loai) {
    loai = loai.toLowerCase();
    ArrayList<HangHoa> ketQua = new ArrayList<HangHoa>();
    for (HangHoa hangHoa : dsHangHoa) {
      if (hangHoa.loai.toLowerCase().equals(loai)) {
        ketQua.add(hangHoa);
      }
    }
    inKetQuaTimKiem(ketQua);
  }

  // Ham them hang hoa vao danh sach hang hoa
  public boolean themHangHoa(HangHoa hangHoa) {
    // Khong them vi trung ma hang hoa
    if (hangHoa.maHang == "" || maHangHoaDaTonTai(hangHoa.maHang)) {
      return false;
    }
    dsHangHoa.add(hangHoa);
    // Them thanh cong
    System.out.print("Da them hang hoa (");
    hangHoa.inThongTin();
    System.out.print(") thanh cong!");
    return true;
  }

  // Ham xoa hang hoa theo ma hang: tra ve trang thai xoa đuoc (true) hay khong (false)
  public boolean xoaHangHoa(String maHang) {
    HangHoa hangHoa = timTheoMaHang(maHang);
    if (hangHoa == null) {
      return false;
    }

    dsHangHoa.remove(hangHoa);
    System.out.print("Da xoa hang hoa co ma: " + maHang);
    return true;
  }

  // Ham sua hang hoa theo ma hang: tra ve HangHoa neu sua đuoc, nguoc lai tra ve null
  public HangHoa suaHangHoa(String loai, String maHang, String tenHang, int giaNhap, int soLuong) {
    HangHoa hangHoa = timTheoMaHang(maHang);
    if (hangHoa == null) {
      return null;
    }

    int chiSo = dsHangHoa.indexOf(hangHoa);
    hangHoa.loai = loai;
    hangHoa.tenHang = tenHang;
    hangHoa.giaNhap = giaNhap;
    hangHoa.soLuong = soLuong;
    dsHangHoa.set(chiSo, hangHoa);
    System.out.print("Da sua thong tin hang hoa: ");
    hangHoa.inThongTin();
    return hangHoa;
  }

  // Ham thong ke hang hoa trong kho (in ra man hinh)
  public void inThongKe() {
    System.out.println("Tong so luong hang hoa: " + dsHangHoa.size());
    System.out.println("Tong gia tri nhap kho: " + tongGiaNhap());
    thongKeSoLuongTheoLoaiHang();
  }

  // Ham sap xep theo gia nhap
  public void sapXepTheoGiaNhap(boolean sapGiamDan) {
    Collections.sort(dsHangHoa, new Comparator<HangHoa>() {
      @Override
      public int compare(HangHoa hangHoa1, HangHoa hangHoa2) {
        if (sapGiamDan) {
          return hangHoa1.giaNhap < hangHoa2.giaNhap ? 1 : -1;
        }
        return hangHoa1.giaNhap > hangHoa2.giaNhap ? 1 : -1;
      }
    });
    inDanhSachHangHoa();
  }

  // Ham sap xep theo ngay nhap
  public void sapXepTheoNgayNhap(boolean sapGiamDan) {
    Collections.sort(dsHangHoa, new Comparator<HangHoa>() {
      @Override
      public int compare(HangHoa hangHoa1, HangHoa hangHoa2) {
        if (sapGiamDan) {
          return hangHoa1.ngayNhap.isBefore(hangHoa2.ngayNhap) ? 1 : -1;
        }
        return hangHoa1.ngayNhap.isAfter(hangHoa2.ngayNhap) ? 1 : -1;
      }
    });
    inDanhSachHangHoa();
  }

  // Ham sap xep theo loai va ngay nhap
  public void sapXepTheoLoaiVaNgayNhap(boolean sapGiamDan) {
    Collections.sort(dsHangHoa, new Comparator<HangHoa>() {
      @Override
      public int compare(HangHoa hangHoa1, HangHoa hangHoa2) {
        int soSanhTheoLoai = hangHoa1.loai.compareTo(hangHoa2.loai);
        // Neu khong cung loai --> sap theo loai la OK
        if (soSanhTheoLoai != 0) {
          if (sapGiamDan) {
            return -soSanhTheoLoai;
          }
          return soSanhTheoLoai;
        }
        // Neu cung loai --> sap xep theo ngay nhap
        if (sapGiamDan) {
          return hangHoa1.ngayNhap.isBefore(hangHoa2.ngayNhap) ? 1 : -1;
        }
        return hangHoa1.ngayNhap.isAfter(hangHoa2.ngayNhap) ? 1 : -1;
      }
    });
    inDanhSachHangHoa();
  }

  // Ham sap xep theo loai va gia nhap
  public void sapXepTheoLoaiVaGiaNhap(boolean sapGiamDan) {
    Collections.sort(dsHangHoa, new Comparator<HangHoa>() {
      @Override
      public int compare(HangHoa hangHoa1, HangHoa hangHoa2) {
        int soSanhTheoLoai = hangHoa1.loai.compareTo(hangHoa2.loai);
        // Neu khong cung loai --> sap theo loai la duoc
        if (soSanhTheoLoai != 0) {
          if (sapGiamDan) {
            return -soSanhTheoLoai;
          }
          return soSanhTheoLoai;
        }
        // Neu cung loai --> sap xep theo gia nhap
        if (sapGiamDan) {
          return hangHoa1.giaNhap < hangHoa2.giaNhap ? 1 : -1;
        }
        return hangHoa1.giaNhap > hangHoa2.giaNhap ? 1 : -1;
      }
    });
    inDanhSachHangHoa();
  }

  // Ham tim hang hoa theo ma hang: tra ve HangHoa neu ton tai, nguoc lai tra ve null
  public HangHoa timTheoMaHang(String maHang) {
    for (HangHoa hangHoa : dsHangHoa) {
      if (hangHoa.maHang.equals(maHang)) {
        return hangHoa;
      }
    }

    return null;
  }

  private void inThongTinHangHoa(ArrayList<HangHoa> danhSach) {
    for (int i = 0; i < danhSach.size(); i++) {
      System.out.print((i + 1) + "/ ");
      danhSach.get(i).inThongTin();
      System.out.println("");
    }
  }

  private void inDanhSachHangHoa() {
    System.out.println("\nDanh sach hang hoa trong kho: ");
    inThongTinHangHoa(dsHangHoa);
  }

  // Ham thong ke so luong theo loai hang
  private void thongKeSoLuongTheoLoaiHang() {
    ArrayList<String> dsLoaiHang = new ArrayList<String>();
    ArrayList<Long> dsSoLuong = new ArrayList<Long>();
    for (HangHoa hangHoa : dsHangHoa) {
      int chiSo = dsLoaiHang.indexOf(hangHoa.loai);
      // Lan đau tien loai hang nay xuat hien
      if (chiSo == -1) {
        dsLoaiHang.add(hangHoa.loai);
        dsSoLuong.add((long) hangHoa.soLuong);
      } else {
        // Cap nhat lai so luong hang hoa theo loai hang neu đa thong ke (đa xuat hien trong dsLoaiHang))
        Long soLuongCu = dsSoLuong.get(chiSo);
        dsSoLuong.set(chiSo, soLuongCu + hangHoa.soLuong);
      }
    }
    // In thong ke
    for (int chiSo = 0; chiSo < dsLoaiHang.size(); chiSo++) {
      System.out.println("Loai hang [" + dsLoaiHang.get(chiSo) + "] co so luong la: " + dsSoLuong.get(chiSo));
    }
  }

  // Ham tinh tong gia nhap
  private long tongGiaNhap() {
    long tong = 0;
    for (HangHoa hangHoa : dsHangHoa) {
      tong += hangHoa.giaNhap;
    }
    return tong;
  }

  // Ham kiem tra ma hang co trung voi tat ca cac hang hoa trong danh sach hang hoa hay khong
  private boolean maHangHoaDaTonTai(String maHang) {
    HangHoa hangHoa = timTheoMaHang(maHang);
    return hangHoa != null;
  }

  private void inKetQuaTimKiem(ArrayList<HangHoa> ketQua) {
    if (ketQua.size() == 0) {
      System.out.println("Khong tim thay hang hoa theo du lieu ban can tim kiem");
    } else {
      System.out.println("\nDanh sach hang hoa tim thay : ");
      inThongTinHangHoa(ketQua);
    }
  }
}