/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taixe;

import Server.server;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.border.TitledBorder;

/**
 *
 * @author tran minh hai
 */
public class KhoiTaoTaiXe extends javax.swing.JFrame {
    private String TK=null; 
    private String MK = null;
      private  String HoTen = null;
    private    String GioiTinh = null;
    private    String NgaySinh = null;
     private   String SDT = null;
     private   String MieuTa = null;
     private   String DiaChi = null;
     private   String TXID = "TX00";
     private   String bien1 = null;
     private   String bien2 = null;
     private   String bien3 = null;
     private   String ten1 = null;
     private   String ten2 = null;
     private   String ten3 = null;
      private  Object tien1 = null;
     private   Object tien2 = null;
     private   Object tien3 = null;
    /**
     * Creates new form KhoiTaoTaiXe1
     */
    public KhoiTaoTaiXe() {
        initComponents();
        setTitle("Tạo Tài Khoản");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        TitledBorder title1 = new TitledBorder("Tạo Tài Khoản");
        pn1.setBorder(title1);
        TitledBorder title2 = new TitledBorder("Thông Tin Tài Xế");
        pn2.setBorder(title2);
        
        Thread t = new Thread(new run());
        t.start();
        /*try {
            socket = new Socket("127.0.0.1", 2000);
            din = new DataInputStream(socket.getInputStream());
            dout = new DataOutputStream(socket.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(KhoiTaoTaiXe.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String ten=null;
        while(true) {
            try {
                ten = din.readUTF();
                if(ten.equalsIgnoreCase("hetxe")) break;
                vtt.add(ten);
            } catch (IOException ex) {
                Logger.getLogger(KhoiTaoTaiXe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            dout.writeUTF("quit");
        } catch (IOException ex) {
            Logger.getLogger(KhoiTaoTaiXe.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        
        
        /*postgres_connect ss = new postgres_connect();
        Connection conn = ss.getConnectToMSSQL();
        Statement stm;
        ResultSet rs;
        try {
            stm = conn.createStatement();
            rs = stm.executeQuery("SELECT * FROM \"LoaiXe\"");
            while(rs.next()) {
                vtt.add(rs.getString("Ten"));
            }
            rs.close();
            stm.close();
            conn.close();
            ss.Close();
        } catch (SQLException ex) {
            Logger.getLogger(KhoiTaoTaiXe.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        //cb_1.setModel(new DefaultComboBoxModel(vtt));
        //cb_2.setModel(new DefaultComboBoxModel(vtt));
        //cb_3.setModel(new DefaultComboBoxModel(vtt));
    }
    public class run implements Runnable {

        @Override
        public void run() {
            try {
            socket = new Socket("127.0.0.1", 2000);
            din = new DataInputStream(socket.getInputStream());
            dout = new DataOutputStream(socket.getOutputStream());
            dout.writeUTF("yeucauloaixe");
        } catch (IOException ex) {
            Logger.getLogger(KhoiTaoTaiXe.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String ten=null;
        while(true) {
            try {
                ten = din.readUTF();
                if(ten.equalsIgnoreCase("hetxe")) break;
                vtt.add(ten);
            } catch (IOException ex) {
                Logger.getLogger(KhoiTaoTaiXe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            dout.writeUTF("quit");
        } catch (IOException ex) {
            Logger.getLogger(KhoiTaoTaiXe.class.getName()).log(Level.SEVERE, null, ex);
        }
        cb_1.setModel(new DefaultComboBoxModel(vtt));
        cb_2.setModel(new DefaultComboBoxModel(vtt));
        cb_3.setModel(new DefaultComboBoxModel(vtt));
        
        }
    }
    
    /*String HamTach(String string) {
        if(string == null) {
            return "TX00";
        }
        String s1 = string.substring(0, 2);
        String s2 = string.substring(2, 4);
        int n = Integer.parseInt(s2);
        String s3;
        if(0<=n && n<=8) {
            n=n+1;
            s3 = s1+"0"+String.valueOf(n);
        }
        else {
            n=n+1;
            s3 = s1+String.valueOf(n); 
        }
        return s3;
    }*/
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pn1 = new javax.swing.JPanel();
        lb_taiKhoan = new javax.swing.JLabel();
        lb_matKhau = new javax.swing.JLabel();
        txt_taiKhoan = new javax.swing.JTextField();
        pwd = new javax.swing.JPasswordField();
        pn2 = new javax.swing.JPanel();
        lb_hoTen = new javax.swing.JLabel();
        lb_gioiTinh = new javax.swing.JLabel();
        lb_diaChi = new javax.swing.JLabel();
        lb_ngaySinh = new javax.swing.JLabel();
        lb_sdt = new javax.swing.JLabel();
        lb_mieuTa = new javax.swing.JLabel();
        txt_hoTen = new javax.swing.JTextField();
        cb_gioiTinh = new javax.swing.JComboBox();
        txt_diaChi = new javax.swing.JTextField();
        cb_ngay = new javax.swing.JComboBox();
        cb_thang = new javax.swing.JComboBox();
        txt_nam = new javax.swing.JTextField();
        txt_sdt = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txt_mieuTa = new javax.swing.JTextArea();
        lb_1 = new javax.swing.JLabel();
        cb_1 = new javax.swing.JComboBox();
        lb_bien1 = new javax.swing.JLabel();
        txt_bien1 = new javax.swing.JTextField();
        lb_2 = new javax.swing.JLabel();
        cb_2 = new javax.swing.JComboBox();
        lb_bien2 = new javax.swing.JLabel();
        txt_bien2 = new javax.swing.JTextField();
        lb_3 = new javax.swing.JLabel();
        cb_3 = new javax.swing.JComboBox();
        lb_bien3 = new javax.swing.JLabel();
        txt_bien3 = new javax.swing.JTextField();
        btn1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lb_taiKhoan.setText("Tài Khoản");

        lb_matKhau.setText("Mật Khẩu");

        javax.swing.GroupLayout pn1Layout = new javax.swing.GroupLayout(pn1);
        pn1.setLayout(pn1Layout);
        pn1Layout.setHorizontalGroup(
            pn1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pn1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lb_taiKhoan, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
                    .addComponent(lb_matKhau, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(pn1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_taiKhoan, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                    .addComponent(pwd))
                .addContainerGap())
        );
        pn1Layout.setVerticalGroup(
            pn1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pn1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lb_taiKhoan)
                    .addComponent(txt_taiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pn1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lb_matKhau)
                    .addComponent(pwd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lb_hoTen.setText("Họ Tên");

        lb_gioiTinh.setText("Giới Tính");

        lb_diaChi.setText("Địa Chỉ");

        lb_ngaySinh.setText("Ngày Sinh");

        lb_sdt.setText("Số ĐT");

        lb_mieuTa.setText("Miêu Tả");

        cb_gioiTinh.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Nam", "Nữ" }));

        cb_ngay.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" }));

        cb_thang.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" }));

        txt_mieuTa.setColumns(20);
        txt_mieuTa.setRows(5);
        jScrollPane1.setViewportView(txt_mieuTa);

        lb_1.setText("Xe 1");

        cb_1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lb_bien1.setText("Biển");

        lb_2.setText("Xe 2");

        cb_2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lb_bien2.setText("Biển");

        lb_3.setText("Xe 3");

        cb_3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lb_bien3.setText("Biển");

        javax.swing.GroupLayout pn2Layout = new javax.swing.GroupLayout(pn2);
        pn2.setLayout(pn2Layout);
        pn2Layout.setHorizontalGroup(
            pn2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pn2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pn2Layout.createSequentialGroup()
                        .addGroup(pn2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(lb_hoTen, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lb_gioiTinh, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lb_diaChi, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lb_ngaySinh, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
                            .addComponent(lb_sdt, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lb_mieuTa, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(pn2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_hoTen)
                            .addComponent(cb_gioiTinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_diaChi)
                            .addGroup(pn2Layout.createSequentialGroup()
                                .addComponent(cb_ngay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cb_thang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txt_nam))
                            .addComponent(txt_sdt)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pn2Layout.createSequentialGroup()
                        .addGroup(pn2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pn2Layout.createSequentialGroup()
                                .addComponent(lb_1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cb_1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pn2Layout.createSequentialGroup()
                                .addComponent(lb_2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cb_2, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pn2Layout.createSequentialGroup()
                                .addComponent(lb_3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cb_3, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(pn2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(pn2Layout.createSequentialGroup()
                                .addComponent(lb_bien3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txt_bien3))
                            .addGroup(pn2Layout.createSequentialGroup()
                                .addComponent(lb_bien2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txt_bien2))
                            .addGroup(pn2Layout.createSequentialGroup()
                                .addComponent(lb_bien1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txt_bien1, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pn2Layout.setVerticalGroup(
            pn2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pn2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lb_hoTen)
                    .addComponent(txt_hoTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pn2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lb_gioiTinh)
                    .addComponent(cb_gioiTinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(pn2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lb_diaChi)
                    .addComponent(txt_diaChi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pn2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lb_ngaySinh)
                    .addComponent(cb_ngay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cb_thang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_nam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pn2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lb_sdt)
                    .addComponent(txt_sdt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pn2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lb_mieuTa)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addGroup(pn2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lb_1)
                    .addComponent(cb_1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lb_bien1)
                    .addComponent(txt_bien1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pn2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lb_2)
                    .addComponent(cb_2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lb_bien2)
                    .addComponent(txt_bien2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pn2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lb_3)
                    .addComponent(cb_3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lb_bien3)
                    .addComponent(txt_bien3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34))
        );

        btn1.setText("Xong");
        btn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(pn1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(pn2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(116, 116, 116)
                        .addComponent(btn1)))
                .addContainerGap(39, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pn1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pn2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn1)
                .addContainerGap(95, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn1ActionPerformed
        // cap nhat vao bang tai xe
        TK = txt_taiKhoan.getText();
       MK = pwd.getText();
        HoTen = txt_hoTen.getText();
        //int GioiTinh;
        //if (cb_gioiTinh.getSelectedItem().toString().equalsIgnoreCase("Nam")) GioiTinh=1; else GioiTinh=0;
        GioiTinh = cb_gioiTinh.getSelectedItem().toString();
       NgaySinh = txt_nam.getText()+"-"+cb_thang.getSelectedItem().toString()+"-"+cb_ngay.getSelectedItem().toString();
       SDT = txt_sdt.getText();
        MieuTa = txt_mieuTa.getText();
        DiaChi = txt_diaChi.getText();
        TXID = "TX00";
        bien1 = txt_bien1.getText();
        bien2 = txt_bien2.getText();
        bien3 = txt_bien3.getText();
        ten1 = cb_1.getSelectedItem().toString();
        ten2 = cb_2.getSelectedItem().toString();
         ten3 = cb_3.getSelectedItem().toString();
        tien1 = new Object();
         tien2 = new Object();
        tien3 = new Object();
                
        /*postgres_connect s = new postgres_connect();
        Connection conn = s.getConnectToMSSQL();
        Statement stm,stm1;
        ResultSet rs,rs1;
        try {       
            stm = conn.createStatement();
            stm1 = conn.createStatement();
            rs = stm.executeQuery("SELECT \"TXID\" FROM \"TaiXe\""); 
            while(rs.next()) {
                TXID = rs.getString("TXID");
                TXID = HamTach(TXID);
                rs1 = stm1.executeQuery("SELECT \"TXID\" FROM \"TaiXe\" WHERE \"TXID\" LIKE '"+TXID+"'");
                if(rs1.next()==false) {rs1.close();break;}
                rs1.close();
            }
            stm1.close();
            rs.close();
            rs = stm.executeQuery("SELECT \"Gia\" FROM \"LoaiXe\" WHERE \"Ten\" = '"+ten1+"'");
            rs.next();
            tien1 = rs.getString("Gia");
            rs = stm.executeQuery("SELECT \"Gia\" FROM \"LoaiXe\" WHERE \"Ten\" = '"+ten2+"'");
            rs.next();
            tien2 = rs.getString("Gia");
            rs = stm.executeQuery("SELECT \"Gia\" FROM \"LoaiXe\" WHERE \"Ten\" = '"+ten3+"'");
            rs.next();
            tien3 = rs.getString("Gia");
            rs.close();
                    
            int rowCount_tk = stm.executeUpdate("INSERT INTO \"TaiKhoan\" VALUES ('"+TK+"','"+MK+"','0','1','0');");
            int rowCount_tx = stm.executeUpdate("INSERT INTO \"TaiXe\" VALUES ('"+TXID+"','"+HoTen+"','"+GioiTinh+"','"+NgaySinh+"','"+DiaChi+"','"+SDT+"','"+MieuTa+"','1','"+TK+"','10','1');");    // Lỗi sai trùng khóa chính, khi in ra nó không đúng thứ tự ???
            if(bien1.equalsIgnoreCase("")==false) {
                int rowCount_x1 = stm.executeUpdate("INSERT INTO \"Xe\" VALUES ('"+bien1+"','"+ten1+"','"+tien1+"','"+TXID+"');");
            }
            if(bien2.equalsIgnoreCase("")==false) {
                int rowCount_x2 = stm.executeUpdate("INSERT INTO \"Xe\" VALUES ('"+bien2+"','"+ten2+"','"+tien2+"','"+TXID+"');");
            }
            if(bien3.equalsIgnoreCase("")==false) {
                int rowCount_x3 = stm.executeUpdate("INSERT INTO \"Xe\" VALUES ('"+bien3+"','"+ten3+"','"+tien3+"','"+TXID+"');");
            }
            stm.close();
        } catch (SQLException ex) {
            Logger.getLogger(KhoiTaoTaiXe.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        
        
        
        try {
            socket = new Socket("127.0.0.1", 2000);
            din = new DataInputStream(socket.getInputStream());
            dout = new DataOutputStream(socket.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(KhoiTaoTaiXe.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Thread readData = new Thread(new ReadData());
        readData.start();
        //dispose();
    }//GEN-LAST:event_btn1ActionPerformed
    
    
    public class ReadData implements Runnable {

        @Override
        public void run() {
            try {
                //System.out.println("khoitao&taixe&"+TK+"&"+MK+"&"+HoTen+"&"+GioiTinh+"&"+NgaySinh+"&"+SDT+"&"+MieuTa+"&"+DiaChi+"&"+TXID+"&"+bien1+"&"+ten1+"&"+bien2+"&"+ten2+"&"+bien3+"&"+ten3);
                dout.writeUTF("taotaikhoan&taixe&"+TK+"&"+MK+"&"+HoTen+"&"+GioiTinh+"&"+NgaySinh+"&"+SDT+"&"+MieuTa+"&"+DiaChi+"&"+TXID+"&"+bien1+"&"+ten1+"&"+bien2+"&"+ten2+"&"+bien3+"&"+ten3);
            } catch (IOException ex) {
                Logger.getLogger(KhoiTaoTaiXe.class.getName()).log(Level.SEVERE, null, ex);
            }
            dispose();
        }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(KhoiTaoTaiXe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(KhoiTaoTaiXe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(KhoiTaoTaiXe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(KhoiTaoTaiXe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new KhoiTaoTaiXe().setVisible(true);
            }
        });
    }
    Socket socket;
    DataOutputStream dout=null;
    DataInputStream din=null;
    Vector vtt = new Vector();
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn1;
    private javax.swing.JComboBox cb_1;
    private javax.swing.JComboBox cb_2;
    private javax.swing.JComboBox cb_3;
    private javax.swing.JComboBox cb_gioiTinh;
    private javax.swing.JComboBox cb_ngay;
    private javax.swing.JComboBox cb_thang;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lb_1;
    private javax.swing.JLabel lb_2;
    private javax.swing.JLabel lb_3;
    private javax.swing.JLabel lb_bien1;
    private javax.swing.JLabel lb_bien2;
    private javax.swing.JLabel lb_bien3;
    private javax.swing.JLabel lb_diaChi;
    private javax.swing.JLabel lb_gioiTinh;
    private javax.swing.JLabel lb_hoTen;
    private javax.swing.JLabel lb_matKhau;
    private javax.swing.JLabel lb_mieuTa;
    private javax.swing.JLabel lb_ngaySinh;
    private javax.swing.JLabel lb_sdt;
    private javax.swing.JLabel lb_taiKhoan;
    private javax.swing.JPanel pn1;
    private javax.swing.JPanel pn2;
    private javax.swing.JPasswordField pwd;
    private javax.swing.JTextField txt_bien1;
    private javax.swing.JTextField txt_bien2;
    private javax.swing.JTextField txt_bien3;
    private javax.swing.JTextField txt_diaChi;
    private javax.swing.JTextField txt_hoTen;
    private javax.swing.JTextArea txt_mieuTa;
    private javax.swing.JTextField txt_nam;
    private javax.swing.JTextField txt_sdt;
    private javax.swing.JTextField txt_taiKhoan;
    // End of variables declaration//GEN-END:variables
}
