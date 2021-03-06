/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Danielyt
 */
public class UserInfoPanel extends javax.swing.JPanel {

    /**
     * Creates new form UserInfoPanel
     */
    public UserInfoPanel() {
        initComponents();
    }
    public void fillUserInfo(String[] userInfo) {
        userNameLabel.setText(userInfo[0]);
        userAgeLabel.setText(userInfo[1]);
        userGenderLabel.setText(userInfo[2]);
        userSingleLabel.setText(userInfo[3]);
        userBooksLabel.setText(userInfo[4]);
        userCompanyLabel.setText(userInfo[5]);
        userDescriptionTextArea.setText(userInfo[6]);
        userHobbiesLabel.setText(userInfo[7]);
        userHomeTownLabel.setText(userInfo[8]);
        userLocationLabel.setText(userInfo[9]);
        userMoviesLabel.setText(userInfo[10]);
        userMusicLabel.setText(userInfo[11]);
        userJobLabel.setText(userInfo[12]);
        userSchoolLabel.setText(userInfo[13]);
        userSubscriberCountLabel.setText(userInfo[14]);
        userLastWebAccessLabel.setText(userInfo[15]);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        userNameLabel = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        userAgeLabel = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        userGenderLabel = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        userSingleLabel = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        userBooksLabel = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        userCompanyLabel = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        userDescriptionTextArea = new javax.swing.JTextArea();
        jLabel16 = new javax.swing.JLabel();
        userHobbiesLabel = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        userHomeTownLabel = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        userLocationLabel = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        userMoviesLabel = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        userMusicLabel = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        userJobLabel = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        userSchoolLabel = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        userSubscriberCountLabel = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        userLastWebAccessLabel = new javax.swing.JLabel();

        setMaximumSize(new java.awt.Dimension(400, 500));
        setMinimumSize(new java.awt.Dimension(0, 0));
        setPreferredSize(new java.awt.Dimension(400, 500));
        java.awt.FlowLayout flowLayout1 = new java.awt.FlowLayout();
        flowLayout1.setAlignOnBaseline(true);
        setLayout(flowLayout1);

        jLabel1.setText("User name:");
        add(jLabel1);

        userNameLabel.setMaximumSize(new java.awt.Dimension(1000, 1000));
        userNameLabel.setPreferredSize(new java.awt.Dimension(330, 14));
        add(userNameLabel);

        jLabel3.setText("Age:");
        add(jLabel3);

        userAgeLabel.setMaximumSize(new java.awt.Dimension(1000, 1000));
        userAgeLabel.setPreferredSize(new java.awt.Dimension(330, 14));
        add(userAgeLabel);

        jLabel5.setText("Gender:");
        add(jLabel5);

        userGenderLabel.setMaximumSize(new java.awt.Dimension(1000, 1000));
        userGenderLabel.setPreferredSize(new java.awt.Dimension(340, 14));
        add(userGenderLabel);

        jLabel7.setText("Single?");
        add(jLabel7);

        userSingleLabel.setMaximumSize(new java.awt.Dimension(1000, 1000));
        userSingleLabel.setPreferredSize(new java.awt.Dimension(330, 14));
        add(userSingleLabel);

        jLabel9.setText("Books:");
        add(jLabel9);

        userBooksLabel.setMaximumSize(new java.awt.Dimension(1000, 1000));
        userBooksLabel.setPreferredSize(new java.awt.Dimension(330, 14));
        add(userBooksLabel);

        jLabel11.setText("Company:");
        add(jLabel11);

        userCompanyLabel.setMaximumSize(new java.awt.Dimension(1000, 1000));
        userCompanyLabel.setPreferredSize(new java.awt.Dimension(330, 14));
        add(userCompanyLabel);

        jLabel14.setText("Description:");
        add(jLabel14);

        jScrollPane1.setPreferredSize(new java.awt.Dimension(320, 96));

        userDescriptionTextArea.setColumns(20);
        userDescriptionTextArea.setRows(5);
        jScrollPane1.setViewportView(userDescriptionTextArea);

        add(jScrollPane1);

        jLabel16.setText("Hobbies:");
        add(jLabel16);

        userHobbiesLabel.setMaximumSize(new java.awt.Dimension(1000, 1000));
        userHobbiesLabel.setPreferredSize(new java.awt.Dimension(330, 14));
        add(userHobbiesLabel);

        jLabel17.setText("Hometown:");
        add(jLabel17);

        userHomeTownLabel.setMaximumSize(new java.awt.Dimension(1000, 1000));
        userHomeTownLabel.setPreferredSize(new java.awt.Dimension(330, 14));
        add(userHomeTownLabel);

        jLabel2.setText("Location:");
        add(jLabel2);

        userLocationLabel.setMaximumSize(new java.awt.Dimension(1000, 1000));
        userLocationLabel.setPreferredSize(new java.awt.Dimension(330, 14));
        add(userLocationLabel);

        jLabel4.setText("Movies:");
        add(jLabel4);

        userMoviesLabel.setMaximumSize(new java.awt.Dimension(1000, 1000));
        userMoviesLabel.setPreferredSize(new java.awt.Dimension(330, 14));
        add(userMoviesLabel);

        jLabel6.setText("Music:");
        add(jLabel6);

        userMusicLabel.setMaximumSize(new java.awt.Dimension(1000, 1000));
        userMusicLabel.setPreferredSize(new java.awt.Dimension(340, 14));
        add(userMusicLabel);

        jLabel8.setText("Job:");
        add(jLabel8);

        userJobLabel.setMaximumSize(new java.awt.Dimension(1000, 1000));
        userJobLabel.setPreferredSize(new java.awt.Dimension(340, 14));
        add(userJobLabel);

        jLabel10.setText("School:");
        jLabel10.setToolTipText("");
        add(jLabel10);

        userSchoolLabel.setMaximumSize(new java.awt.Dimension(1000, 1000));
        userSchoolLabel.setPreferredSize(new java.awt.Dimension(330, 14));
        add(userSchoolLabel);

        jLabel13.setText("Subscriber count:");
        add(jLabel13);

        userSubscriberCountLabel.setMaximumSize(new java.awt.Dimension(1000, 1000));
        userSubscriberCountLabel.setPreferredSize(new java.awt.Dimension(300, 14));
        add(userSubscriberCountLabel);

        jLabel15.setText("Last web access:");
        add(jLabel15);

        userLastWebAccessLabel.setMaximumSize(new java.awt.Dimension(1000, 1000));
        userLastWebAccessLabel.setPreferredSize(new java.awt.Dimension(300, 14));
        add(userLastWebAccessLabel);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel userAgeLabel;
    private javax.swing.JLabel userBooksLabel;
    private javax.swing.JLabel userCompanyLabel;
    private javax.swing.JTextArea userDescriptionTextArea;
    private javax.swing.JLabel userGenderLabel;
    private javax.swing.JLabel userHobbiesLabel;
    private javax.swing.JLabel userHomeTownLabel;
    private javax.swing.JLabel userJobLabel;
    private javax.swing.JLabel userLastWebAccessLabel;
    private javax.swing.JLabel userLocationLabel;
    private javax.swing.JLabel userMoviesLabel;
    private javax.swing.JLabel userMusicLabel;
    private javax.swing.JLabel userNameLabel;
    private javax.swing.JLabel userSchoolLabel;
    private javax.swing.JLabel userSingleLabel;
    private javax.swing.JLabel userSubscriberCountLabel;
    // End of variables declaration//GEN-END:variables
}
