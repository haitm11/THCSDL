/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khachhang;

import java.awt.Dimension;
import java.awt.Graphics;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.border.TitledBorder;

/**
 *
 * @author tran minh hai
 */
public class KhachHang extends javax.swing.JFrame {
    /**
     * Creates new form KhachHang
     * 
     * @param Kid
     * @param Hoten
     * @param Gioitinh
     * @param Sdt
     * @param Ngaysinh
     * @param Diachi
     * @param Tk
     * @param Din
     * @param Dout
     * @param Mk
     * @param Ip
     */
    public KhachHang(String Kid,String Hoten,String Gioitinh,String Diachi,String Ngaysinh,String Sdt,String Tk,DataInputStream Din,DataOutputStream Dout,String Mk,String Ip) {
        
        initComponents();
        tk = Tk;
        kid = Kid;
        hoten = Hoten;
        gioitinh = Gioitinh;
        diachi = Diachi;
        ngaysinh = Ngaysinh;
        sdt = Sdt;
        din = Din;
        dout = Dout;
        mk = Mk;
        ip = Ip;
        setTitle("Xin Chào "+hoten);
        /*TitledBorder title1 = new TitledBorder("Thông tin cá nhân");
        jPanel1.setBorder(title1);
        TitledBorder title2 = new TitledBorder("Lộ trình");
        jPanel2.setBorder(title2);*/
        lb_taikhoan.setText(tk);
        lb_hoten.setText(hoten);
        lb_id.setText(kid);

        Thread readData = new Thread(new ReadData());
        readData.start();
        
        String url = System.getProperty("user.dir", null);
        String icontk,iconmk,iconht,iconct,icontien,icondx,iconcntt,iconnaptien,iconnk,icontimkiem;
        icontk = url + "\\icon\\214.png";
        iconmk = url + "\\icon\\216.png";
        iconht = url + "\\icon\\208.png";
        iconct = url + "\\icon\\217.png";
        icontien = url + "\\icon\\218.png";
        icondx = url + "\\icon\\5.png";
        iconnaptien = url + "\\icon\\219.png";
        iconcntt = url + "\\icon\\206.png";
        iconnk = url + "\\icon\\220.png";
        icontimkiem = url + "\\icon\\145.png";
        jLabel1.setIcon(new javax.swing.ImageIcon(icontk));
        jLabel2.setIcon(new javax.swing.ImageIcon(iconmk));
        jLabel5.setIcon(new javax.swing.ImageIcon(iconht));
        jLabel4.setIcon(new javax.swing.ImageIcon(iconct));
        btn_$.setIcon(new javax.swing.ImageIcon(icontien));
        btn_dangxuat.setIcon(new javax.swing.ImageIcon(icondx));
        btn_capnhat.setIcon(new javax.swing.ImageIcon(iconcntt));
        btn_naptien.setIcon(new javax.swing.ImageIcon(iconnaptien));
        btn_nhatki.setIcon(new javax.swing.ImageIcon(iconnk));
        jLabel3.setIcon(new javax.swing.ImageIcon(icontimkiem));
    }

    
    public class ReadData implements Runnable {

        @Override
        public void run() {
            String s1=null;
            String[] s2;
            try {
                while(true) {
                    s1 = din.readUTF();
                    if(s1.equalsIgnoreCase("naptienthanhcong")) {
                        JOptionPane.showMessageDialog(null,"Nap tien thanh cong");
                    }
                    if(s1.equalsIgnoreCase("naptienthatbai")) {
                        JOptionPane.showMessageDialog(null,"Khong thanh cong");
                    }
                    s2 = s1.split("&");
                    if(s2[0].equalsIgnoreCase("taixeonline")) {
                        String name = s2[1];
                        //System.out.println(name);
                        mo.addElement(name);
                        jList1.setModel(mo);
                    }
                    if(s2[0].equalsIgnoreCase("taixeofline")) {
                        String name = s2[1];
                        mo.removeElement(name);
                        jList1.setModel(mo);
                    }
                    if(s2[0].equalsIgnoreCase("kiemtratien")) {
                        JOptionPane.showMessageDialog(null,"Ban co: "+s2[1]+" d trong tai khoan");
                    }
                    if(s2[0].equalsIgnoreCase("timkiemthongtintaixe")) {
                        String name = s2[1];
                        if(name.equalsIgnoreCase("khongco")) JOptionPane.showMessageDialog(null, "Khong co tai xe nay, hoac tai xe nay dang ofline");
                        else {
                            String diemdi = txt_diemdi.getText();
                            String diemden = txt_diemden.getText();
                            new ChonTaiXe(tk,name,dout,diemdi,diemden,ip).setVisible(true);
                        }
                    }
                    if(s2[0].equalsIgnoreCase("hienthi")) {
                        JOptionPane.showMessageDialog(null, s2[1]);
                    }
                    if(s2[0].equalsIgnoreCase("capnhattenkhachang")) {
                        String ht = s2[1];
                        lb_hoten.setText(ht);
                    }
                    if(s2[0].equalsIgnoreCase("danhgia")) {
                        String tktx = s2[1];
                        String bien = s2[2];
                        String tkkh = s2[3];
                        String tbatdau = s2[4];
                        String tketthuc = s2[5];
                        String diemdi = s2[6];
                        String diemden = s2[7];
                        String thoigian = s2[8];
                        String km = s2[9];
                        String thanhtien = s2[10];
                        String hoten = s2[11];
                        jTextField4.setText(km);
                        jTextField1.setText(thanhtien);
                        new DanhGia(tktx,bien,tkkh,tbatdau,tketthuc,diemdi,diemden,thoigian,km,thanhtien,hoten,dout).setVisible(true);
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(KhachHang.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar1 = new javax.swing.JToolBar();
        btn_capnhat = new javax.swing.JButton();
        btn_nhatki = new javax.swing.JButton();
        btn_$ = new javax.swing.JButton();
        btn_naptien = new javax.swing.JButton();
        btn_dangxuat = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel(){
            ImageIcon icon = new ImageIcon(panel3);
            public void paintComponent(Graphics g){
                Dimension d = this.getSize();
                g.drawImage(icon.getImage(), 0, 0, d.width, d.height, null);
                setOpaque(false);
                super.paintComponent(g);
            }
        };
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lb_taikhoan = new javax.swing.JLabel();
        lb_id = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lb_hoten = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jLabel3 = new javax.swing.JLabel();
        tf_timkiem = new javax.swing.JTextField();
        btn_ok = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txt_diemdi = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txt_diemden = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel(){
            ImageIcon icon = new ImageIcon(panel4);
            public void paintComponent(Graphics g){
                Dimension d = this.getSize();
                g.drawImage(icon.getImage(), 0, 0, d.width, d.height, null);
                setOpaque(false);
                super.paintComponent(g);
            }
        };

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jToolBar1.setRollover(true);

        btn_capnhat.setText("Cập nhật thông tin");
        btn_capnhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_capnhatActionPerformed(evt);
            }
        });
        jToolBar1.add(btn_capnhat);

        btn_nhatki.setText("Nhật kí");
        btn_nhatki.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_nhatkiActionPerformed(evt);
            }
        });
        jToolBar1.add(btn_nhatki);

        btn_$.setText("Kiểm tra $");
        btn_$.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_$ActionPerformed(evt);
            }
        });
        jToolBar1.add(btn_$);

        btn_naptien.setText("Nạp tiền");
        btn_naptien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_naptienActionPerformed(evt);
            }
        });
        jToolBar1.add(btn_naptien);

        btn_dangxuat.setText("Đăng xuất");
        btn_dangxuat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_dangxuatActionPerformed(evt);
            }
        });
        jToolBar1.add(btn_dangxuat);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setText("Tài khoản");

        jLabel2.setText("ID");

        lb_taikhoan.setText("jLabel3");

        lb_id.setText("jLabel4");

        jLabel5.setText("Họ tên");

        lb_hoten.setText("jLabel6");

        jLabel4.setText("Chi tiết");
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(lb_id, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lb_hoten, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lb_taikhoan, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lb_taikhoan))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lb_id)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(lb_hoten)
                    .addComponent(jLabel4))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jList1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList1MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jList1);

        btn_ok.setText("OK");
        btn_ok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_okActionPerformed(evt);
            }
        });

        jLabel6.setText("Điểm bắt đầu");

        jLabel7.setText("Điểm kết thúc");

        jLabel8.setText("Số km");

        jLabel9.setText("Thành tiền");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(199, 199, 199)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(46, 46, 46)
                        .addComponent(tf_timkiem, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(btn_ok))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jTextField4, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txt_diemden, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txt_diemdi, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tf_timkiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_ok)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_diemdi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_diemden, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2))
                .addContainerGap())
        );

        jPanel4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 127, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(175, Short.MAX_VALUE))
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jList1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList1MouseClicked
        String diemdi = txt_diemdi.getText();
        String diemden = txt_diemden.getText();
        if (evt.getClickCount() == 2) {
            String name=(String)jList1.getSelectedValue();
            new ChonTaiXe(tk,name,dout,diemdi,diemden,ip).setVisible(true);
        }
    }//GEN-LAST:event_jList1MouseClicked

    private void btn_$ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_$ActionPerformed
        try {
            dout.writeUTF("kiemtratien&"+tk);
        } catch (IOException ex) {
            Logger.getLogger(KhachHang.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_$ActionPerformed

    private void btn_dangxuatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_dangxuatActionPerformed
        try {
            dout.writeUTF("dangxuat&khachhang&"+tk);
        } catch (IOException ex) {
            Logger.getLogger(KhachHang.class.getName()).log(Level.SEVERE, null, ex);
        }
        dispose();
        System.exit(0);
    }//GEN-LAST:event_btn_dangxuatActionPerformed

    private void btn_capnhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_capnhatActionPerformed
        new update_khach(kid,ip).setVisible(true);
    }//GEN-LAST:event_btn_capnhatActionPerformed

    private void btn_naptienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_naptienActionPerformed
        new naptien(tk,dout).setVisible(true);
    }//GEN-LAST:event_btn_naptienActionPerformed

    private void btn_okActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_okActionPerformed
        String name = tf_timkiem.getText();
        try {
            dout.writeUTF("thongtintaixehoten&"+name);
        } catch (IOException ex) {
            Logger.getLogger(KhachHang.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_okActionPerformed

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
        if(evt.getClickCount() == 2) new ChiTiet(kid,ip).setVisible(true);
    }//GEN-LAST:event_jLabel4MouseClicked

    private void btn_nhatkiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_nhatkiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_nhatkiActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        try {
            dout.writeUTF("dangxuat&khachhang&"+tk);
        } catch (IOException ex) {
            Logger.getLogger(KhachHang.class.getName()).log(Level.SEVERE, null, ex);
        }
        dispose();
        System.exit(0);
    }//GEN-LAST:event_formWindowClosing

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
            java.util.logging.Logger.getLogger(KhachHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(KhachHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(KhachHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(KhachHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                //new KhachHang("tranminhhai@khachhang").setVisible(true);
            }
        });
    }
    private String ip=null;
    private String tk=null;
    private String hoten=null;
    private String gioitinh=null;
    private String diachi=null;
    private String ngaysinh=null;
    private String sdt=null;
    private String kid=null;
    private String mk=null;
    private String panel4="C:\\Users\\tran minh hai\\Documents\\JAVA PROJECT\\BaiTapLonCSDL\\picture\\taxi6.jpg";
    private String panel3="C:\\Users\\tran minh hai\\Documents\\JAVA PROJECT\\BaiTapLonCSDL\\picture\\Patrick Hoesly_Warning Stripe_YkZhQw.jpg";
    private final DefaultListModel<String> mo = new DefaultListModel<>();
    private final DefaultListModel<String> mok = new DefaultListModel<>();
    Socket socket;
    DataOutputStream dout=null;
    DataInputStream din=null;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_$;
    private javax.swing.JButton btn_capnhat;
    private javax.swing.JButton btn_dangxuat;
    private javax.swing.JButton btn_naptien;
    private javax.swing.JButton btn_nhatki;
    private javax.swing.JButton btn_ok;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList jList1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel lb_hoten;
    private javax.swing.JLabel lb_id;
    private javax.swing.JLabel lb_taikhoan;
    private javax.swing.JTextField tf_timkiem;
    private javax.swing.JTextField txt_diemden;
    private javax.swing.JTextField txt_diemdi;
    // End of variables declaration//GEN-END:variables
}
