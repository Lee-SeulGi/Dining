package prj.nat.sres.customer.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import prj.nat.sres.customer.machine.VirtualCashModule;
import prj.nat.sres.customer.machine.VirtualTicketPrinter;
import prj.nat.sres.customer.net.ConnectionManager;
import prj.nat.sres.customer.util.PropertiesManager;
import prj.nat.sres.dto.Menu;
import prj.nat.sres.dto.Purchase;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class KioskMain extends JFrame {
	
	static final long serialVersionUID = 1L;
	
	ConnectionManager cm = new ConnectionManager();

	private JPanel contentPane;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new KioskMain();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public KioskMain() {	
		setTitle("메뉴 주문 프로그램 (손님용)");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(812, 635);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(239, 236, 229));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);		
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(239, 236, 229));
		panel.setBounds(12, 91, 560, 499);
		contentPane.add(panel);
		try {
			if(cm.getMenuList().size() <= 9)
				panel.setLayout(new GridLayout(3, 3, 12, 12));
			else
				panel.setLayout(new GridLayout(4, 4, 12, 12));
		} catch (Exception e3) {
			
			e3.printStackTrace();
		}
		// 여백 넣기
		panel.setBorder(BorderFactory.createEmptyBorder(10 , 10 , 10 , 10));
		
		JLabel lblSum = new JLabel("\\");
		lblSum.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSum.setForeground(new Color(59, 35, 20));
		lblSum.setFont(new Font("THE왼손잡이", Font.BOLD, 25));
		lblSum.setBounds(649, 279, 138, 40);
		contentPane.add(lblSum);
		
		String[] props = null;
		PropertiesManager pm = new PropertiesManager();
		try {
			props = pm.propertiesImport("kiosk.properties");
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		JLabel lblNewLabel = new JLabel(props[0]);
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("THE외계인설명서", Font.PLAIN, 33));
		lblNewLabel.setBounds(96, 10, 542, 71);
		contentPane.add(lblNewLabel);
		
		JButton btnPayment = new JButton("결제");
		
		btnPayment.setBounds(584, 518, 96, 72);
		btnPayment.setForeground(new Color(59, 35, 20));
		btnPayment.setBackground(new Color(236, 233, 226));
		btnPayment.setFont(new Font("THE외계인설명서", Font.BOLD, 14));
		btnPayment.setBorderPainted(false);
		btnPayment.setFocusPainted(false);
		contentPane.add(btnPayment);
		
		JButton btnPayCancel = new JButton("취소");
		btnPayCancel.setForeground(new Color(59, 35, 20));
		btnPayCancel.setBackground(new Color(236, 233, 226));
		btnPayCancel.setFont(new Font("THE외계인설명서", Font.BOLD, 14));
		btnPayCancel.setBorderPainted(false);
		btnPayCancel.setFocusPainted(false);		
		btnPayCancel.setBounds(690, 518, 96, 72);
		
		contentPane.add(btnPayCancel);
		
		// 장바구니 테이블 추가
		String[] tableHeaderTitles = {"품명", "수량", "금액"};
		DefaultTableModel model = new DefaultTableModel(tableHeaderTitles, 0);
		JTable table = new JTable(model);
		table.getColumnModel().getColumn(0).setPreferredWidth(200);
		
		JPanel tablePanel = new JPanel();
		tablePanel.setBounds(584, 100, 204, 176);
		contentPane.add(tablePanel);
		tablePanel.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		tablePanel.add(scrollPane);
		
		tablePanel.add(table.getTableHeader(), BorderLayout.NORTH);
		scrollPane.setViewportView(table);
		
		table.setFont(new Font("THE외계인설명서", Font.PLAIN, 13));
		table.getTableHeader().setFont(new Font("THE외계인설명서", Font.PLAIN, 13));
		
		JLabel lblNewLabel_1 = new JLabel("");
		Image img = new ImageIcon("logo.png").getImage().getScaledInstance(160, 80, Image.SCALE_SMOOTH);
		lblNewLabel_1.setIcon(new ImageIcon(img));
		lblNewLabel_1.setBounds(15, 12, 160, 80);
		contentPane.add(lblNewLabel_1);
		
		
		List<Menu> list = null;
		try {
			list = cm.getMenuList();
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(null, "서버가 열리지 않았습니다.");
			e1.printStackTrace();
		}	
		
		for(int i = 0; i < list.size(); i++) {			
			
			String tagStart = "<html><center><font size=5>";
			String tagMid = list.get(i).getMenuName() + "</font><br>" 
					+ list.get(i).getPrice() 
					+ "원";
			
			// 품절 시 텍스트 추가
			if(list.get(i).getQuantity() <= 0) {
				tagMid += " <font color=red>(품절)</font>";
				String tagEnd = "</center></html>";				
				JButton tempButton = new JButton(tagStart + tagMid + tagEnd);
				tempButton.setFont(new Font("THE외계인설명서", Font.PLAIN, 15));
				tempButton.setBackground(new Color(168, 142, 125));
				tempButton.setForeground(Color.WHITE);
				tempButton.setBorderPainted(false);
				tempButton.setFocusPainted(false);
				panel.add(tempButton);
				continue;
			}
			
			String tagEnd = "</center></html>";
			
			JButton tempButton = new JButton(tagStart + tagMid + tagEnd);
			tempButton.setFont(new Font("THE외계인설명서", Font.PLAIN, 15));
			tempButton.setBackground(new Color(168, 142, 125));
			tempButton.setForeground(Color.WHITE);
			tempButton.setBorderPainted(false);
			tempButton.setFocusPainted(false);
			panel.add(tempButton);
			final int CURRENT_INDEX = i; // 람다식에서 사용하기 위해
			tempButton.addActionListener(al ->
			
			{
				try {
					List<Menu> listToCheck = cm.getMenuList();

					Object[] objArr = new Object[4];
					objArr[0] = listToCheck.get(CURRENT_INDEX).getMenuName();
					objArr[1] = 1;
					objArr[2] = listToCheck.get(CURRENT_INDEX).getPrice();
					
					// 이미 메뉴가 추가되었는지 검색
					boolean isFind = false;
					if (table.getRowCount() == 0) {
						model.addRow(objArr);
					} else {
						for (int index = 0; index < table.getRowCount(); index++) {
							if (table.getValueAt(index, 0).toString().equals(listToCheck.get(CURRENT_INDEX).getMenuName())){
								isFind = true;
								int value = (Integer)table.getValueAt(index, 1);
								if(value >= 5) {	
									JOptionPane.showMessageDialog(null, "5개 이상 구입하실 수 없습니다.");
									break;
								} else if(value >= listToCheck.get(CURRENT_INDEX).getQuantity()) {
									JOptionPane.showMessageDialog(null, "재고가 없습니다.");
									break;
								}
								table.setValueAt(value + 1, index, 1);
								break;
							} 
						}	
						if(!isFind)	model.addRow(objArr);						
					}
					
					// 합계 계산
					int sum = 0;
					for (int index = 0; index < table.getRowCount(); index++) {
						sum += (Integer)table.getValueAt(index, 1) * (Integer)table.getValueAt(index, 2);
					}	
					
					
					lblSum.setText("￦" + new DecimalFormat().format(sum));			
					
													
						
					if(listToCheck.get(CURRENT_INDEX).getQuantity() == 0) {
						setVisible(false);
						new KioskMain();
						// 목표: 창 전체 내용을 새로고침하기
					}
				
				} catch (Exception e) {
					e.printStackTrace();
				}			
				
			}
			
					);
		}
		
		for(int i = list.size(); i < 9; i++) {
			panel.add(new JLabel(""));
		}
		
		
		JLabel lblNewLabel_2 = new JLabel("합계");
		lblNewLabel_2.setFont(new Font("THE외계인설명서", Font.BOLD, 20));
		lblNewLabel_2.setForeground(new Color(59, 35, 20));
		lblNewLabel_2.setBounds(584, 279, 70, 40);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblKioskno = new JLabel("kioskNo: " + props[1]);
		lblKioskno.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyChar() == 'q') {
					System.out.println("@종료 요청됨");
					System.exit(0);
				}
			}
		});
		lblKioskno.setForeground(new Color(59, 35, 20));
		lblKioskno.setFont(new Font("THE외계인설명서", Font.PLAIN, 15));
		lblKioskno.setBounds(722, 8, 70, 23);
		contentPane.add(lblKioskno);
		
		JLabel banner = new JLabel("");
		banner.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("@배너 클릭됨");
				lblKioskno.requestFocus();
			}
		});
		banner.setBounds(584, 326, 204, 180);
		contentPane.add(banner);
		Image imgBanner = new ImageIcon("banner.png").getImage().getScaledInstance(204, 180, Image.SCALE_SMOOTH);
		banner.setIcon(new ImageIcon(imgBanner));
		
		
		JLabel bg = new JLabel();
		bg.setIcon(new ImageIcon("backcolor.png"));
		bg.setBounds(0, 0, 800, 600);
		contentPane.add(bg);
		
		// 취소 액션
		
		btnPayCancel.addActionListener(ev -> {
			setVisible(false);
			new KioskMain();
			// 목표: 창 전체 내용을 새로고침하기
		});
		
		// 결제 버튼
		
		final String[] PROPS = props;
		btnPayment.addActionListener(ev -> {
			List<Purchase> outputPurchaseList = new ArrayList<>(); 
			VirtualCashModule vc = new VirtualCashModule(
					Integer.parseInt(lblSum.getText().replace("￦", "").replace(",", "")));
			if(!vc.isPurchaseSuccess()) {
				setVisible(false);
				new KioskMain();
				// 목표: 창 전체 내용을 새로고침하기
			} else {
				try {
					List<Menu> listToCheck = cm.getMenuList();
					String category = "";
					String menuName = "";
					int price = 0;
					
					for(int i = 0; i < table.getRowCount(); i++) {
						int menuIndex = -1;					
						for(int j = 0; j < listToCheck.size(); j++) {
							if(table.getValueAt(i, 0).equals(listToCheck.get(j).getMenuName())) {
								menuIndex = j;
								category = listToCheck.get(j).getCategory();
								menuName = listToCheck.get(j).getMenuName();
								price = listToCheck.get(j).getPrice();
							}
						}
						outputPurchaseList.add(new Purchase(menuIndex, (Integer)table.getValueAt(i, 1)));
						for(int k = 0; k < (Integer)table.getValueAt(i, 1); k++) {
							new VirtualTicketPrinter(category, menuName, price, PROPS[0], Integer.parseInt(PROPS[1]));
						}						
						
					}
					
					boolean purchaseResult =  cm.purchaseMultipleMenu(outputPurchaseList);
					System.out.println("구매결과: " + purchaseResult);
										
					
					setVisible(false);
					new KioskMain();
					// 목표: 창 전체 내용을 새로고침하기
				} catch (Exception e) {
					
				}
			}
			
			
		});
		
		setVisible(true);
	}
}
