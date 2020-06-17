package Client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JDesktopPane;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.StringTokenizer;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JPasswordField;
import javax.swing.ImageIcon;

public class Ftp {

	private JFrame frame;
	private JTextField textField;
    private JPasswordField passwordField;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Ftp window = new Ftp();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	
		
	}
	
	
	

	/**
	 * Create the application.
	 * @throws IOException 
	 */
	public Ftp() throws IOException {
		
		initialize(	);
	}
	
	
		
		
	
	  
		   
	/**
	 * Initialize the contents of the frame.
	 * @throws IOException 
	 */
	public void initialize() throws IOException {
	
		
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new CardLayout(0, 0));
		
		JDesktopPane desktopPane = new JDesktopPane();
		desktopPane.setBackground(Color.DARK_GRAY);
		desktopPane.setForeground(Color.WHITE);
		frame.getContentPane().add(desktopPane, "name_1056779973396800");
		
		JLabel lblNewLabel = new JLabel("File Transfert Application");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Forte", Font.PLAIN, 22));
		lblNewLabel.setBounds(90, 11, 334, 52);
		desktopPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("User name:");
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(68, 83, 88, 14);
		desktopPane.add(lblNewLabel_1);
		
		textField = new JTextField();
		textField.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		textField.setBounds(155, 82, 86, 20);
		desktopPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Passeword:");
		lblNewLabel_2.setForeground(Color.WHITE);
		lblNewLabel_2.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		lblNewLabel_2.setBounds(68, 116, 88, 17);
		desktopPane.add(lblNewLabel_2);
		JButton btnNewButton = new JButton("Connect");
		btnNewButton.setFont(new Font("Comic Sans MS", Font.PLAIN, 11));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String nom=(String)textField.getText();
				String psw=(String)passwordField.getText();
				
				
					try {
						interface2 f =new interface2(nom,psw);
						f.setVisible(true);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
			}
		});
		btnNewButton.setBounds(154, 148, 89, 23);
		desktopPane.add(btnNewButton);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(155, 116, 86, 20);
		desktopPane.add(passwordField);
		
		JLabel lblNewLabel_3 = new JLabel("New label");
		Image img1=new ImageIcon(this.getClass().getResource("/icone.png")).getImage();
		
		lblNewLabel_3.setIcon(new ImageIcon(img1));
		
		lblNewLabel_3.setBounds(273, 133, 135, 105);
		desktopPane.add(lblNewLabel_3);
	}
	public void msg(String response) {
		JOptionPane.showMessageDialog(frame, "Erreur de connexion avec le compte utilisateur : \n" + response,"erreur",JOptionPane.ERROR_MESSAGE);
	}
	
	}

