package Client;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Client.Ftp;

import javax.swing.JDesktopPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;
import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.StringTokenizer;

import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JFormattedTextField;
import javax.swing.JEditorPane;
import javax.swing.JTextPane;
import javax.swing.ImageIcon;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

public class interface2 extends JFrame    {

	private JPanel contentPane;
	private JTextField txtFileName;
	private JTextField txtFolderName;
	private JTextField txtFileName_1;
	private JTextField txtFileName_2;
     
	

	 
	/**
	 * Launch the application.
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					interface2 frame = new interface2(toString(),toString() );
					frame.setVisible(true);
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
	public interface2() {
		
	
	}
	
	public interface2(String nom, String psw) throws IOException {
		
		Connect cl=new Connect(nom,psw);
		cl.connect();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 615, 404);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JDesktopPane desktopPane = new JDesktopPane();
		desktopPane.setBackground(Color.DARK_GRAY);
		contentPane.add(desktopPane, BorderLayout.CENTER);
		
		JLabel lblNewLabel = new JLabel("Connexion au FTP");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Forte", Font.PLAIN, 18));
		lblNewLabel.setBounds(224, 13, 143, 28);
		desktopPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Vous \u00EAtes maintenant connect\u00E9 au FTP");
		lblNewLabel_1.setForeground(Color.LIGHT_GRAY);
		lblNewLabel_1.setFont(new Font("Comic Sans MS", Font.PLAIN, 13));
		lblNewLabel_1.setBounds(160, 80, 239, 14);
		desktopPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Vous avez le droit aux commandes:");
		lblNewLabel_2.setForeground(Color.LIGHT_GRAY);
		lblNewLabel_2.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		lblNewLabel_2.setBounds(30, 101, 267, 14);
		desktopPane.add(lblNewLabel_2);

		
		
		JButton btnNewButton = new JButton("PWD");
		btnNewButton.setBackground(Color.BLACK);
		btnNewButton.setFont(new Font("Comic Sans MS", Font.PLAIN, 13));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					cl.pwd();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}		
		});
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setBounds(26, 172, 143, 23);
		desktopPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("CWD");
		btnNewButton_1.setBackground(Color.BLACK);
		btnNewButton_1.setForeground(Color.WHITE);
		btnNewButton_1.setFont(new Font("Comic Sans MS", Font.PLAIN, 13));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			String fch =(String)txtFolderName.getText();
			try {
				cl.cwd(fch);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			}
		});
		btnNewButton_1.setBounds(26, 233, 143, 23);
		desktopPane.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("LIST");
		btnNewButton_2.setForeground(Color.WHITE);
		btnNewButton_2.setBackground(Color.BLACK);
		btnNewButton_2.setFont(new Font("Comic Sans MS", Font.PLAIN, 13));
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					cl.list();
					cl.quit();
					cl.connect();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		btnNewButton_2.setBounds(26, 297, 143, 23);
		desktopPane.add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("QUIT");
		btnNewButton_3.setFont(new Font("Comic Sans MS", Font.PLAIN, 13));
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.quit();
			}
			
		});
		btnNewButton_3.setBounds(244, 308, 122, 23);
		desktopPane.add(btnNewButton_3);
		
		JLabel lblNewLabel_3 = new JLabel("Bienvenue "+nom);
		lblNewLabel_3.setForeground(Color.WHITE);
		lblNewLabel_3.setBackground(new Color(255, 255, 255));
		lblNewLabel_3.setFont(new Font("Comic Sans MS", Font.ITALIC, 18));
		lblNewLabel_3.setBounds(224, 46, 216, 23);
		desktopPane.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("New label");
		Image img1=new ImageIcon(this.getClass().getResource("/User-Files-icon.png")).getImage();
		
		lblNewLabel_4.setIcon(new ImageIcon(img1));
		
		lblNewLabel_4.setBounds(462, 13, 93, 87);
		desktopPane.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("New label");
       Image img2=new ImageIcon(this.getClass().getResource("/doc-icon.png")).getImage();
		
		lblNewLabel_5.setIcon(new ImageIcon(img2));
		
		lblNewLabel_5.setBounds(30, 11, 97, 83);
		desktopPane.add(lblNewLabel_5);
		
		txtFileName = new JTextField();
		txtFileName.addFocusListener(new FocusAdapter() {
			
			public void focusGained(FocusEvent e) {
				if(txtFileName.getText().equals("File Name")) {
					txtFileName.setText("");
				}
				txtFolderName.setForeground(Color.black);
			}
			
			public void focusLost(FocusEvent e) {
				if(txtFileName.getText().equals("")) {
					txtFileName.setText("File Name");
				}
				txtFileName.setForeground(Color.black);
			}
		});
		txtFileName.setForeground(Color.BLACK);
		txtFileName.setFont(new Font("Comic Sans MS", Font.PLAIN, 11));
		txtFileName.setText("File Name");
		txtFileName.setBounds(432, 174, 131, 20);
		desktopPane.add(txtFileName);
		txtFileName.setColumns(10);
		
		JButton btnNewButton_5 = new JButton("Delate");
		btnNewButton_5.setForeground(Color.WHITE);
		btnNewButton_5.setBackground(Color.BLACK);
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String f=txtFileName_1 .getText();
				
				try {
					
					cl.deleteFile(f);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton_5.setFont(new Font("Comic Sans MS", Font.PLAIN, 13));
		btnNewButton_5.setBounds(422, 205, 145, 23);
		desktopPane.add(btnNewButton_5);
		
		JButton btnNewButton_4 = new JButton("Download");
		btnNewButton_4.setBackground(Color.BLACK);
		btnNewButton_4.setForeground(Color.WHITE);
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String f=(String)txtFileName .getText();
				try {
					cl.downloadFile(f);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton_4.setFont(new Font("Comic Sans MS", Font.PLAIN, 13));
		btnNewButton_4.setBounds(422, 140, 145, 23);
		desktopPane.add(btnNewButton_4);
		
		JButton btnNewButton_6 = new JButton("Upload");
		btnNewButton_6.setBackground(Color.BLACK);
		btnNewButton_6.setForeground(Color.WHITE);
		btnNewButton_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String f=(String)txtFileName_2 .getText();
				try {
					cl.uploadFile(f);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton_6.setFont(new Font("Comic Sans MS", Font.PLAIN, 13));
		btnNewButton_6.setBounds(422, 267, 145, 23);
		desktopPane.add(btnNewButton_6);
		
		txtFolderName = new JTextField();
		txtFolderName.setForeground(Color.black);
		txtFolderName.setFont(new Font("Comic Sans MS", Font.PLAIN, 11));
		txtFolderName.setText("Folder Name");
		txtFolderName.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(txtFolderName.getText().equals("Folder Name")) {
					txtFolderName.setText("");
				}
				txtFolderName.setForeground(Color.black);
				
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(txtFolderName.getText().equals("")) {
					txtFolderName.setText("Folder Name");
				}
				txtFolderName.setForeground(Color.gray);
			}
		});
		txtFolderName.setBounds(244, 172, 123, 20);
		desktopPane.add(txtFolderName);
		txtFolderName.setColumns(10);
		
		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(10, 121, 6, 223);
		desktopPane.add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setOrientation(SwingConstants.VERTICAL);
		separator_1.setBounds(198, 121, 2, 223);
		desktopPane.add(separator_1);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setOrientation(SwingConstants.VERTICAL);
		separator_2.setBounds(403, 121, 9, 223);
		desktopPane.add(separator_2);
		
		JSeparator separator_3 = new JSeparator();
		separator_3.setOrientation(SwingConstants.VERTICAL);
		separator_3.setBounds(577, 121, 2, 223);
		desktopPane.add(separator_3);
		
		JSeparator separator_4 = new JSeparator();
		separator_4.setBounds(10, 121, 569, 2);
		desktopPane.add(separator_4);
		
		JSeparator separator_5 = new JSeparator();
		separator_5.setBounds(10, 342, 569, 2);
		desktopPane.add(separator_5);
		
		JLabel lblNewLabel_6 = new JLabel("Current Directory");
		lblNewLabel_6.setForeground(Color.WHITE);
		lblNewLabel_6.setFont(new Font("Comic Sans MS", Font.PLAIN, 11));
		lblNewLabel_6.setBounds(30, 147, 139, 14);
		desktopPane.add(lblNewLabel_6);
		
		JLabel lblNewLabel_7 = new JLabel("Change Directory");
		lblNewLabel_7.setFont(new Font("Comic Sans MS", Font.PLAIN, 11));
		lblNewLabel_7.setForeground(Color.WHITE);
		lblNewLabel_7.setBounds(30, 206, 93, 23);
		desktopPane.add(lblNewLabel_7);
		
		JLabel lblNewLabel_8 = new JLabel("List of Files");
		lblNewLabel_8.setFont(new Font("Comic Sans MS", Font.PLAIN, 11));
		lblNewLabel_8.setForeground(Color.WHITE);
		lblNewLabel_8.setBounds(30, 272, 97, 14);
		desktopPane.add(lblNewLabel_8);
		
		txtFileName_1 = new JTextField();
		txtFileName_1.setFont(new Font("Comic Sans MS", Font.PLAIN, 11));
		txtFileName_1.setText("File Name");
		txtFileName_1.addFocusListener(new FocusAdapter() {
			
			public void focusGained(FocusEvent e) {
				if(txtFileName_1.getText().equals("File Name")) {
					txtFileName_1.setText("");
				}
				txtFolderName.setForeground(Color.black);
			}
			
			public void focusLost(FocusEvent e) {
				if(txtFileName_1.getText().equals("")) {
					txtFileName_1.setText("File Name");
				}
				txtFileName_1.setForeground(Color.black);
			
			}
		});
		txtFileName_1.setBounds(432, 236, 131, 20);
		desktopPane.add(txtFileName_1);
		txtFileName_1.setColumns(10);
		
		txtFileName_2 = new JTextField();
		txtFileName_2.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(txtFileName_2.getText().equals("File Name")) {
					txtFileName_2.setText("");
				}
				txtFolderName.setForeground(Color.black);
			}
			
			public void focusLost(FocusEvent e) {
				if(txtFileName_2.getText().equals("")) {
					txtFileName_2.setText("File Name");
				}
				txtFileName_2.setForeground(Color.black);
			
			}
		});
		txtFileName_2.setFont(new Font("Comic Sans MS", Font.PLAIN, 11));
		txtFileName_2.setText("File Name");
		txtFileName_2.setBounds(432, 300, 131, 20);
		desktopPane.add(txtFileName_2);
		txtFileName_2.setColumns(10);
		
		JLabel lblNewLabel_9 = new JLabel("Enter the Folder Name !!");
		lblNewLabel_9.setForeground(Color.WHITE);
		lblNewLabel_9.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		lblNewLabel_9.setBounds(224, 144, 175, 14);
		desktopPane.add(lblNewLabel_9);
		
		JButton btnNewButton_7 = new JButton("ASCII");
		btnNewButton_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					cl.Ascci();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		});
		btnNewButton_7.setFont(new Font("Comic Sans MS", Font.PLAIN, 13));
		btnNewButton_7.setBounds(244, 222, 123, 23);
		desktopPane.add(btnNewButton_7);
		
		JLabel lblNewLabel_10 = new JLabel("ASCII type");
		lblNewLabel_10.setForeground(Color.WHITE);
		lblNewLabel_10.setFont(new Font("Comic Sans MS", Font.PLAIN, 11));
		lblNewLabel_10.setBounds(266, 205, 79, 14);
		desktopPane.add(lblNewLabel_10);
		
		JButton btnNewButton_8 = new JButton("PASV");
		btnNewButton_8.setFont(new Font("Comic Sans MS", Font.PLAIN, 13));
		btnNewButton_8.setBounds(245, 272, 123, 23);
		desktopPane.add(btnNewButton_8);
		
		JLabel lblNewLabel_11 = new JLabel("Pasive mode");
		lblNewLabel_11.setForeground(Color.WHITE);
		lblNewLabel_11.setFont(new Font("Comic Sans MS", Font.PLAIN, 11));
		lblNewLabel_11.setBounds(266, 251, 78, 20);
		desktopPane.add(lblNewLabel_11);
		
			}
	
	

		public void msg(String response) {
			JOptionPane.showMessageDialog(contentPane,"" + response,"Resultat",JOptionPane.PLAIN_MESSAGE);
		}
	}

