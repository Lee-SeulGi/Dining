package prj.nat.sres.customer.machine;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Font;

public class VirtualCashModule extends JDialog {
	
	static final long serialVersionUID = 1L;
	
	private JTextField cashField;
	private JTextField fldInsertedCash;
	private int insertedCash;
	private boolean isPurchaseSuccess = false;

	public VirtualCashModule(int cashSum) {
		setModal(true);
		setTitle("현금결제창");
		setSize(420, 372);
		setLocationRelativeTo(getParent());
		getContentPane().setLayout(null);

		JButton button = new JButton("10,000");
		button.setBackground(new Color(240, 240, 240));
		button.setFont(new Font("THE외계인설명서", Font.PLAIN, 14));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fldInsertedCash.setText(String.valueOf(insertedCash += 10000));
			}
		});
		button.setBounds(12, 95, 185, 56);
		button.setFocusPainted(false);
		getContentPane().add(button);

		JButton button_1 = new JButton("5,000");
		button_1.setBackground(new Color(240, 240, 240));
		button_1.setFont(new Font("THE외계인설명서", Font.PLAIN, 14));
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fldInsertedCash.setText(String.valueOf(insertedCash += 5000));
			}
		});
		button_1.setBounds(210, 95, 185, 56);
		button_1.setFocusPainted(false);
		getContentPane().add(button_1);

		JButton button_2 = new JButton("1,000");
		button_2.setBackground(new Color(240, 240, 240));
		button_2.setFont(new Font("THE외계인설명서", Font.PLAIN, 14));
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fldInsertedCash.setText(String.valueOf(insertedCash += 1000));
			}
		});
		button_2.setBounds(12, 160, 122, 56);
		button_2.setFocusPainted(false);
		getContentPane().add(button_2);

		JButton button_3 = new JButton("500");
		button_3.setBackground(new Color(240, 240, 240));
		button_3.setFont(new Font("THE외계인설명서", Font.PLAIN, 14));
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fldInsertedCash.setText(String.valueOf(insertedCash += 500));
			}
		});
		button_3.setBounds(143, 160, 122, 56);
		button_3.setFocusPainted(false);
		getContentPane().add(button_3);

		JButton button_4 = new JButton("100");
		button_4.setBackground(new Color(240, 240, 240));
		button_4.setFont(new Font("THE외계인설명서", Font.PLAIN, 14));
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fldInsertedCash.setText(String.valueOf(insertedCash += 100));
			}
		});
		button_4.setBounds(272, 160, 122, 56);
		button_4.setFocusPainted(false);
		getContentPane().add(button_4);

		cashField = new JTextField();
		cashField.setBackground(new Color(240, 240, 240));
		cashField.setEditable(false);
		cashField.setText(String.valueOf(cashSum));
		cashField.setBounds(95, 10, 300, 31);
		getContentPane().add(cashField);
		cashField.setColumns(10);
		cashField.setFont(new Font("THE외계인설명서", Font.PLAIN, 14));

		fldInsertedCash = new JTextField();
		fldInsertedCash.setBackground(new Color(232, 232, 232));
		fldInsertedCash.setEditable(false);
		fldInsertedCash.setColumns(10);
		fldInsertedCash.setBounds(95, 51, 300, 31);
		fldInsertedCash.setFont(new Font("THE외계인설명서", Font.PLAIN, 14));
		getContentPane().add(fldInsertedCash);

		JLabel label = new JLabel("결제할 금액");
		label.setBounds(13, 18, 85, 15);
		label.setFont(new Font("THE외계인설명서", Font.BOLD, 14));
		getContentPane().add(label);

		JLabel label_1 = new JLabel("투입한 금액");
		label_1.setBounds(13, 59, 85, 15);
		label_1.setFont(new Font("THE외계인설명서", Font.BOLD, 14));
		getContentPane().add(label_1);

		JButton btnPurchase = new JButton("결   제");
		btnPurchase.setFocusPainted(false);
		btnPurchase.setBackground(new Color(224, 221, 214));
		btnPurchase.setBounds(12, 232, 382, 40);
		btnPurchase.setFont(new Font("THE외계인설명서", Font.BOLD, 14));
		getContentPane().add(btnPurchase);

		JButton button_6 = new JButton("취   소");
		button_6.setFocusPainted(false);
		button_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setPurchaseSuccess(false);
				dispose();
			}
		});
		button_6.setBounds(12, 280, 382, 40);
		button_6.setBackground(new Color(224, 221, 214));
		button_6.setFont(new Font("THE외계인설명서", Font.BOLD, 14));
		getContentPane().add(button_6);

		// 결제 버튼
		btnPurchase.addActionListener(ev -> {
			if(cashSum == Integer.parseInt(fldInsertedCash.getText())) {
				System.out.println("결제 성공: 식권 발매");
				JOptionPane.showMessageDialog(null, "결제가 성공되었습니다. 식권을 발매합니다.");
				setPurchaseSuccess(true);
				dispose();
			} else if (cashSum < Integer.parseInt(fldInsertedCash.getText())){
				JOptionPane.showMessageDialog(null, "초과 지급하였습니다. 거스름돈을 지급합니다.");
				setPurchaseSuccess(true);
				int change = keepTheChange(Integer.parseInt(fldInsertedCash.getText()), cashSum);
				JOptionPane.showMessageDialog(null, "거스름돈 " + change + "원 지급합니다.");
				payMonetary(change);
				dispose();
			} else {
				JOptionPane.showMessageDialog(null, "잔액이 부족합니다.");
			}
		});


		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
		setType(Type.UTILITY);


	}

	public boolean isPurchaseSuccess() {
		return isPurchaseSuccess;
	}

	public void setPurchaseSuccess(boolean isPurchaseSuccess) {
		this.isPurchaseSuccess = isPurchaseSuccess;
	}

	public int keepTheChange(int insertedCash, int cashSum) {
		return insertedCash - cashSum;
	}

	// 거스름돈을 화폐별로 지급합니다. (1000원 ~ 100원)
	public void payMonetary(int change) {
		int[] p = new int[3];
		int[] t = new int[3];
		int res = change;
		int m = 1000;
		int sw = 1;

		for(int k = 0; k < p.length; k++) {
			p[k] = res / m;
			t[k] += p[k];
			res = res - (p[k] * m);
			if(sw == 1) {
				m = m / 2;
				sw = 0;
			} else {
				m = m / 5;
				sw = 1;
			}
		}

		// 거스름돈 지급 상황 재현
		System.out.print("거스름돈(1000, 500, 100):\t");
		for(int i = 0; i < p.length; i++) {
			System.out.print(p[i] + "\t");
		}
		System.out.println();
	}

}
