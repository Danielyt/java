
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import javax.swing.JOptionPane;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Danielyt
 */
public class LogInFrame extends javax.swing.JFrame {

    /**
     * Creates new form LogInFrame
     */
    private MainFrame pparent;
    public LogInFrame(MainFrame pparent) {
        this.pparent = pparent;
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        usernameLabel = new javax.swing.JLabel();
        usernameTextField = new javax.swing.JTextField();
        passwordLabel = new javax.swing.JLabel();
        userPasswordField = new javax.swing.JPasswordField();
        logInButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Log in");
        setMinimumSize(new java.awt.Dimension(33, 33));
        setPreferredSize(new java.awt.Dimension(260, 130));
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(new java.awt.FlowLayout());

        usernameLabel.setText("Username:");
        getContentPane().add(usernameLabel);

        usernameTextField.setColumns(20);
        usernameTextField.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        usernameTextField.setToolTipText("Enter username");
        usernameTextField.setMinimumSize(new java.awt.Dimension(10, 10));
        getContentPane().add(usernameTextField);

        passwordLabel.setText("Password:");
        passwordLabel.setPreferredSize(new java.awt.Dimension(52, 14));
        getContentPane().add(passwordLabel);

        userPasswordField.setColumns(20);
        userPasswordField.setToolTipText("");
        userPasswordField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                userPasswordFieldKeyPressed(evt);
            }
        });
        getContentPane().add(userPasswordField);

        logInButton.setText("Log in");
        logInButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logInButtonActionPerformed(evt);
            }
        });
        getContentPane().add(logInButton);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void logInAction(){
        //log in if username and password are correct
        try {
            YouTubeActions.LogIn(usernameTextField.getText(), userPasswordField.getPassword());
            String userName = YouTubeActions.currentUser.getUsername();
            JOptionPane.showMessageDialog(this, "You have successfully logged in as " + userName,
                    "Log in successful", JOptionPane.INFORMATION_MESSAGE);
            pparent.setTitle("Logged in as " + userName);
            String[] userInfo = new String[16];
            if ( YouTubeActions.currentUser.getUsername() != null)
                userInfo[0] = YouTubeActions.currentUser.getUsername();
            if ( YouTubeActions.currentUser.getAge() != null )
                userInfo[1] = YouTubeActions.currentUser.getAge().toString();
            if ( YouTubeActions.currentUser.getGender() != null )
                userInfo[2] = YouTubeActions.currentUser.getGender().toString();            
            if ( YouTubeActions.currentUser.getRelationship() != null ) 
                userInfo[3] = YouTubeActions.currentUser.getRelationship().toString();
            if ( YouTubeActions.currentUser.getBooks() != null )
                userInfo[4] = YouTubeActions.currentUser.getBooks();
            if ( YouTubeActions.currentUser.getCompany() != null )
                userInfo[5] = YouTubeActions.currentUser.getCompany();
            if ( YouTubeActions.currentUser.getAboutMe() != null ) 
                userInfo[6] = YouTubeActions.currentUser.getAboutMe();
            if ( YouTubeActions.currentUser.getHobbies() != null )
                userInfo[7] = YouTubeActions.currentUser.getHobbies();
            if ( YouTubeActions.currentUser.getHometown() != null )
                userInfo[8] = YouTubeActions.currentUser.getHometown();
            if ( YouTubeActions.currentUser.getLocation() != null )
                userInfo[9] = YouTubeActions.currentUser.getLocation();
            if ( YouTubeActions.currentUser.getMovies() != null )
                userInfo[10] = YouTubeActions.currentUser.getMovies();
            if ( YouTubeActions.currentUser.getMusic() != null )
                userInfo[11] = YouTubeActions.currentUser.getMusic();
            if ( YouTubeActions.currentUser.getOccupation() != null )
                userInfo[12] = YouTubeActions.currentUser.getOccupation();
            if ( YouTubeActions.currentUser.getSchool() != null )
                userInfo[13] = YouTubeActions.currentUser.getSchool();
            if ( YouTubeActions.currentUser.getStatistics() != null )
                userInfo[14] = String.valueOf(YouTubeActions.currentUser.getStatistics().getSubscriberCount());
            if ( YouTubeActions.currentUser.getStatistics() != null )
                userInfo[15] = YouTubeActions.currentUser.getStatistics().getLastWebAccess().toUiString();            
            //YouTubeActions.currentUser.setAboutMe("ala bala nitsa");
            pparent.fillUserInfo();
            pparent.getUserInfoPanel().fillUserInfo(userInfo);
            this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));            
        }
        //username and/or password incorrect
        catch (AuthenticationException exception){
           JOptionPane.showMessageDialog(this, exception.getMessage() + "\nTry again.",
                   "Invalid username or password", JOptionPane.ERROR_MESSAGE);
           System.err.println("Invalid username or password\n" + exception.getMessage());
        }
        catch (MalformedURLException exception){
            System.err.println("MalformedURLException: " + exception.getMessage());
        }
        catch (IOException exception){
            System.err.println("IOException: " + exception.getMessage());
        }
        catch (ServiceException exception){
            System.err.println("ServiceException: " + exception.getMessage());
        }
    }
    private void userPasswordFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_userPasswordFieldKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            logInAction();
        }
    }//GEN-LAST:event_userPasswordFieldKeyPressed

    private void logInButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logInButtonActionPerformed
        // TODO add your handling code here:
        logInAction();
    }//GEN-LAST:event_logInButtonActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        usernameTextField.setText("");
        userPasswordField.setText("");
        if (pparent != null)
            pparent.setEnabled(true);
    }//GEN-LAST:event_formWindowClosing

    /**
     * @param args the command line arguments
     */
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton logInButton;
    private javax.swing.JLabel passwordLabel;
    private javax.swing.JPasswordField userPasswordField;
    private javax.swing.JLabel usernameLabel;
    private javax.swing.JTextField usernameTextField;
    // End of variables declaration//GEN-END:variables
}
