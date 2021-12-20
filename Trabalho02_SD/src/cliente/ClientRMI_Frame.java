package cliente;

import java.awt.BorderLayout;

import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.Border;

public class ClientRMI_Frame extends JFrame implements ActionListener{
	
	private static final long serialVersionUID = 1L;	
	JPanel textPanel, inputPanel;
	JTextField textField;
	String name, message;
	Font timesFont = new Font("TimesRoman", Font.PLAIN, 14);
	Border blankBorder = BorderFactory.createEmptyBorder(10,10,20,10);//top,r,b,l
	ChatClient chatClient;
    JList<String> list;
    DefaultListModel<String> listModel;
    
    JTextArea textArea, userArea;
    JFrame frame;
    JButton msgButton, startButton, sendButton;
    JPanel clientPanel, userPanel;

	
	public static void main(String args[]){

		try{
			for(LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()){
				if("Nimbus".equals(info.getName())){
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		}
		catch(Exception e){
			}
		new ClientRMI_Frame();
		}
	
	
	
	public ClientRMI_Frame(){
			
		frame = new JFrame("Console Chat");	
	
		
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		        
		    	if(chatClient != null){
			    	try {
			        	sendMessage("Tchau");
			        	chatClient.server.exit(name);
					} catch (RemoteException e) {
						e.printStackTrace();
					}		        	
		        }
		        System.exit(0);  
		    }   
		});
		
	
		Container c = getContentPane();
		JPanel outerPanel = new JPanel(new BorderLayout());
		
		outerPanel.add(getInput(), BorderLayout.CENTER);
		outerPanel.add(getText(), BorderLayout.NORTH);
		
		c.setLayout(new BorderLayout());
		c.add(outerPanel, BorderLayout.CENTER);
		c.add(getUsersPanel(), BorderLayout.WEST);

		frame.add(c);
		frame.pack();
		frame.setAlwaysOnTop(true);
		frame.setLocation(150, 150);
		textField.requestFocus();
	
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public JPanel getText(){
		String welcome = "Bem-vindo, digite seu nome e aperte no botão Inicar\n";
		textArea = new JTextArea(welcome, 14, 34);
		textArea.setMargin(new Insets(10, 10, 10, 10));
		textArea.setFont(timesFont);
		
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(textArea);
		textPanel = new JPanel();
		textPanel.add(scrollPane);
	
		textPanel.setFont(new Font("TimesRoman", Font.PLAIN, 14));
		return textPanel;
	}
	
	
	public JPanel getInput(){
		inputPanel = new JPanel(new GridLayout(1, 1, 5, 5));
		inputPanel.setBorder(blankBorder);	
		textField = new JTextField();
		textField.setFont(timesFont);
		inputPanel.add(textField);
		return inputPanel;
	}

	
	public JPanel getUsersPanel(){
		
		userPanel = new JPanel(new BorderLayout());
		String  userStr = " Usuário atual      ";
		
		JLabel userLabel = new JLabel(userStr, JLabel.CENTER);
		userPanel.add(userLabel, BorderLayout.NORTH);	
		userLabel.setFont(new Font("Meiryo", Font.PLAIN, 16));

		String[] cy = {"Nenhum usuário"};
		setClient(cy);

		clientPanel.setFont(timesFont);
		userPanel.add(button(), BorderLayout.SOUTH);		
		userPanel.setBorder(blankBorder);

		return userPanel;		
	}

	
    public void setClient(String[] currClients) {  	
    	clientPanel = new JPanel(new BorderLayout());
        listModel = new DefaultListModel<String>();
        
        for(String s : currClients){
        	listModel.addElement(s);
        }
        if(currClients.length > 1){
        	msgButton.setEnabled(true);
        }
        

        list = new JList<String>(listModel);
        list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        list.setVisibleRowCount(8);
        list.setFont(timesFont);
        JScrollPane listScrollPane = new JScrollPane(list);

        clientPanel.add(listScrollPane, BorderLayout.CENTER);
        userPanel.add(clientPanel, BorderLayout.CENTER);
    }
	
	public JPanel button() {		
		sendButton = new JButton("Enviar ");
		sendButton.addActionListener(this);
		sendButton.setEnabled(false);

        msgButton = new JButton("Enviar PM");
        msgButton.addActionListener(this);
        msgButton.setEnabled(false);
		
		startButton = new JButton("Iniciar ");
		startButton.addActionListener(this);
		
		JPanel buttonPanel = new JPanel(new GridLayout(4, 1));
		buttonPanel.add(msgButton);
		buttonPanel.add(new JLabel(""));
		buttonPanel.add(startButton);
		buttonPanel.add(sendButton);
		
		return buttonPanel;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e){

		try {

			if(e.getSource() == startButton){
				name = textField.getText();				
				if(name.length() != 0){
					frame.setTitle("Console chat");
					textField.setText("");
					textArea.append("username : " + name + " conectando-se\n");							
					con(name);
					if(!chatClient.connectionProblem){
						startButton.setEnabled(false);
						sendButton.setEnabled(true);
						}
				}
				else{
					JOptionPane.showMessageDialog(frame, "Digite seu nome para iniciar");
				}
			}


			if(e.getSource() == sendButton){
				message = textField.getText();
				textField.setText("");
				sendMessage(message);
				System.out.println("Enviando messagem : " + message);
			}
			

			if(e.getSource() == msgButton){
				int[] privateList = list.getSelectedIndices();
				
				for(int i=0; i<privateList.length; i++){
					System.out.println("índice selecionado :" + privateList[i]);
				}
				message = textField.getText();
				textField.setText("");
				sendPrivate(privateList);
			}
			
		}
		catch (RemoteException remoteExc) {			
			remoteExc.printStackTrace();	
		}
		
	}
	private void sendMessage(String chatMessage) throws RemoteException {
		chatClient.server.updateChat(name, chatMessage);
	}

	
	private void sendPrivate(int[] privateList) throws RemoteException {
		String privateMessage = "[PM para " + name + "] :" + message + "\n";
		chatClient.server.sendPM(privateList, privateMessage);
	}
	
	
	private void con(String userName) throws RemoteException{
		
		String cleanedUserName = userName.replaceAll("\\s+","_");
		cleanedUserName = userName.replaceAll("\\W+","_");
		try {		
			chatClient = new ChatClient(this, cleanedUserName);
			chatClient.start();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

}










