package javatutorial;

import java.awt.EventQueue;
import java.text.DecimalFormat;


import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.Set;
import java.awt.event.ActionEvent;
import ca.odell.glazedlists.GlazedLists;
import ca.odell.glazedlists.swing.AutoCompleteSupport;

import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;




public class converter {

	private JFrame frame;
	private JTextField textField2;
	private JTextField textField;
	private JButton btnNewButton2;

	
	public static void main(String[] args) {
	
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					converter window = new converter();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public converter() throws IOException, ParseException {
		initialize();
	}

	private void initialize() throws IOException, ParseException { 
		
		URL url = new URL("http://api.exchangeratesapi.io/v1/latest?access_key=9b34490d02ef798d0d312a4b70479f83&format=1");
	    HttpURLConnection con = (HttpURLConnection) url.openConnection();
	    con.setRequestMethod("GET");
	    int responseCode = con.getResponseCode();
	    System.out.println("GET Response Code :: " + responseCode);
		if (responseCode == HttpURLConnection.HTTP_OK) { 
			// success.....if it succeed then API respond as 200 OK else it shows "code is fail"
			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			String finalresponse = response.toString();
			//here our response is recorded in JSON....bt java cant access it so we have to use parser
            JSONParser parserx = new JSONParser(); 
			JSONObject rate_data = (JSONObject) parserx.parse(finalresponse);
			JSONObject rate_data2 = (JSONObject) rate_data.get("rates");
			Set<String> rate_data3 = rate_data2.keySet();
			
			frame = new JFrame();
			frame.setBounds(100, 100, 791, 499);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.getContentPane().setLayout(null);
			
			JLabel lblNewLabel1 = new JLabel("converter");
			lblNewLabel1.setBounds(0, 0, 777, 38);
			lblNewLabel1.setForeground(new Color(51, 51, 51));
			lblNewLabel1.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel1.setFont(new Font("Segoe UI Semibold", Font.BOLD, 28));
			frame.getContentPane().add(lblNewLabel1);
			
			JLabel lblNewLabel2 = new JLabel("Input value");
			lblNewLabel2.setFont(new Font("Segoe UI Semibold", Font.BOLD, 15));
			lblNewLabel2.setBounds(20, 105, 90, 27);
			frame.getContentPane().add(lblNewLabel2);
			
			textField2 = new JTextField();
			textField2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				}
			});
			textField2.setBounds(128, 112, 126, 19);
			frame.getContentPane().add(textField2);
			textField2.setColumns(10);
		
			JLabel lblNewLabel3 = new JLabel("Input Currency Dropdown");
			lblNewLabel3.setFont(new Font("Segoe UI Semibold", Font.BOLD, 15));
			lblNewLabel3.setBounds(321, 48, 188, 45);
			frame.getContentPane().add(lblNewLabel3);
			
			//conversion of set into array
			//GlazedLists requires array
			int n = rate_data3.size();
			String arr1[] = new String[n];
			int i = 0;
			for (String x : rate_data3) {
			         arr1[i++] = x;}
			
			JComboBox comboBox1 = new JComboBox();
			AutoCompleteSupport.install(comboBox1, GlazedLists.eventListOf(arr1));
			comboBox1.addActionListener(new ActionListener() {
		   
				public void actionPerformed(ActionEvent e) {
				}
			});
			comboBox1.setBounds(331, 111, 132, 21);
			frame.getContentPane().add(comboBox1);
		
			JLabel lblNewLabel4 = new JLabel("Output Currency Dropdown");
			lblNewLabel4.setFont(new Font("Segoe UI Semibold", Font.BOLD, 15));
			lblNewLabel4.setBounds(571, 56, 196, 29);
			frame.getContentPane().add(lblNewLabel4);

			JComboBox comboBox2 = new JComboBox();
			AutoCompleteSupport.install(comboBox2, GlazedLists.eventListOf(arr1));
			
			comboBox2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				}
			});
			comboBox2.setBounds(591, 111, 126, 21);
			frame.getContentPane().add(comboBox2);
			
			
			JLabel lblNewLabel5 = new JLabel("Output Value");
			lblNewLabel5.setFont(new Font("Segoe UI Semibold", Font.BOLD, 15));
			lblNewLabel5.setBounds(177, 349, 99, 38);
			frame.getContentPane().add(lblNewLabel5);
			lblNewLabel5.setVisible(false);
			

			JButton btnNewButton1 = new JButton("Convert");
			btnNewButton1.setFont(new Font("Segoe UI Semibold", Font.BOLD, 15));
			btnNewButton1.setBounds(220, 238, 116, 27);
			frame.getContentPane().add(btnNewButton1);
			btnNewButton1.addActionListener(new ActionListener() {
			   
			public void actionPerformed(ActionEvent e) {
				double input_xchange_factor = (double) rate_data2.get(comboBox1.getSelectedItem());
				double output_xchange_factor = (double) rate_data2.get(comboBox2.getSelectedItem());
				double inp_val = Double.parseDouble(textField2.getText()); 
				double output_val = (double) inp_val * (output_xchange_factor / input_xchange_factor);
				String outputString = String.format("Value of %.2f %s in %s is %.2f", inp_val,
								comboBox1.getSelectedItem(),
								comboBox2.getSelectedItem(), output_val);
				lblNewLabel5.setVisible(true);
				System.out.println(outputString);
				showDisplay(output_val);
			}
			});
		
			btnNewButton2 = new JButton("Close");
			btnNewButton2.setFont(new Font("Segoe UI Semibold", Font.BOLD, 15));
			btnNewButton2.setBounds(464, 238, 99, 27);
			frame.getContentPane().add(btnNewButton2);
			btnNewButton2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					frame.dispose();
				}
			});
		}
		else {
			System.out.println("GET request not worked");
			throw new RuntimeException("Exchange Rate Website is down for maintenance.\nPlease Try again later");
		}
	}

	public void showDisplay(double finalResult) {
		textField = new JTextField();
		textField.setBounds(290, 358, 220, 27);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		DecimalFormat df = new DecimalFormat("0.00");
		String finalString = String.valueOf(df.format(finalResult));
		textField.setText(finalString);
	}
}