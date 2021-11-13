package laptrinhmang_doan;

import EventComponentCustomize.SearchCallBack;
import EventComponentCustomize.SearchEvent;
import java.awt.Font;

public class TraCuuDiaLyPanel extends javax.swing.JPanel {

    public TraCuuDiaLyPanel() {
        initComponents();
        searchComponent1.hintText = "Nhập vào tên thành phố, quốc qia cần tìm ...";
        searchComponent1.addEvent(new SearchEvent() {
            @Override
            public void onPressed(SearchCallBack call) {
                System.out.println(searchComponent1.getText());
                call.done();
            }

            @Override
            public void onCancel() {
                
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        searchComponent1 = new laptrinhmang_doan.SearchComponent();

        setBackground(new java.awt.Color(204, 204, 204));

        searchComponent1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchComponent1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(searchComponent1, javax.swing.GroupLayout.DEFAULT_SIZE, 939, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(searchComponent1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(575, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void searchComponent1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchComponent1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchComponent1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private laptrinhmang_doan.SearchComponent searchComponent1;
    // End of variables declaration//GEN-END:variables
}
