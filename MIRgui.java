import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.Graphics;
public class MIRgui implements ActionListener{
	private static int count = 0;
	private static JButton button;
	private static JLabel name;
	private static JLabel date;
	private static JTextField nameText;
	private static JTextField dateText;
	private static JLabel questionOne;
	private static JTextField QOneText;
	private static JLabel success;
	private static JLabel explanation;
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();
		frame.setSize(1000,1000);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);
		frame.setBackground(Color.red);
		
		panel.setLayout(null);
		panel.setBounds(70,80,100,100);
		
		name = new JLabel("Name: ");
		name.setBounds(10,20,80,25);
		panel.add(name);
		
		nameText = new JTextField(20);
		nameText.setBounds(100,20,165,25);
		panel.add(nameText);
		
		date = new JLabel("Date: ");
		date.setBounds(10,50,80,25);
		panel.add(date);
				
		dateText = new JTextField(20);
		dateText.setBounds(100,50,165,25);
		panel.add(dateText);
		
		explanation = new JLabel("Hello! please enter a query for output based off said query.");
		explanation.setBounds(5, 300, 600, 25);
		panel.add(explanation);
		
		questionOne = new JLabel("Query:");
		questionOne.setBounds(10, 80, 80, 25);
		panel.add(questionOne);
		
		QOneText = new JTextField();
		QOneText.setBounds(100,80,300,100);
		
		panel.add(QOneText);

		button = new JButton("Submit");
		button.setBounds(10, 230, 80, 25);
		button.addActionListener(new MIRgui());
		panel.add(button);
		
		success = new JLabel(" ");
		success.setBounds(100, 230, 300, 300);
		panel.add(success);
		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String one = QOneText.getText();
		success.setText("Test");
	}
}
