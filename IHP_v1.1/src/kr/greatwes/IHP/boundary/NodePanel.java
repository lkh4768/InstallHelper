package kr.greatwes.IHP.boundary;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import kr.greatwes.IHP.control.NodeManager;

public class NodePanel extends JPanel implements ActionListener, FocusListener,
		MouseListener {
	private JButton addSibingNodeBtn;
	private JButton removeNodeBtn;
	private JButton addChildNodeBtn;

	private JTextArea tx;

	private NodeManager nodeManager;

	private int nodeID;
	private int level;
	private int parentNodeID;

	public NodePanel(int nodeID, int level, int parentNodeID, String nodeInfo) {
		// TODO Auto-generated constructor stub
		this.nodeID = nodeID;
		this.level = level;
		this.parentNodeID = parentNodeID;

		JPanel sibingBtnPanel = new JPanel();

		sibingBtnPanel
				.setLayout(new BoxLayout(sibingBtnPanel, BoxLayout.Y_AXIS));

		addSibingNodeBtn = new JButton("+");
		removeNodeBtn = new JButton("X");
		addChildNodeBtn = new JButton("+");

		addSibingNodeBtn.addActionListener(this);
		removeNodeBtn.addActionListener(this);
		addChildNodeBtn.addActionListener(this);

		tx = new JTextArea(5, 20);
		tx.setLineWrap(true); // JTextArea의 자동개행
		
		if(nodeInfo.equals("")) tx.setText("터미널 명령어 입력");
		else tx.setText(nodeInfo);
		
		tx.setDisabledTextColor(Color.gray);
		tx.setFocusable(false);
		JScrollPane txScrollPane = new JScrollPane(tx);

		sibingBtnPanel.add(removeNodeBtn);

		/*
		 * 맨 처음에 하나의 노드가 있기 위해서 설정
		 */
		if (level == 0)
			sibingBtnPanel.add(addSibingNodeBtn);

		JLabel headerLabel = new JLabel(parentNodeID + "-" + nodeID);

		add(headerLabel, BorderLayout.PAGE_START);
		add(txScrollPane, BorderLayout.CENTER);
		add(sibingBtnPanel, BorderLayout.LINE_END);
		add(addChildNodeBtn, BorderLayout.PAGE_END);

		setBorder(BorderFactory.createLineBorder(Color.black));

		nodeManager = NodeManager.getNodeManagerInstant();

		tx.addFocusListener(this);
		tx.addMouseListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == addSibingNodeBtn) {
			nodeManager.addSiblingNode(0, 0, -1,"");
		} else if (e.getSource() == removeNodeBtn) {
			if (!getName().equals("1"))
				nodeManager.removeNode(level, Integer.parseInt(this.getName()),
						parentNodeID);
		} else if (e.getSource() == addChildNodeBtn) {
			nodeManager.addChildNode(level, Integer.parseInt(this.getName()),
					-1,"");
		}
	}

	public String getNodeInfo() {
		return tx.getText();
	}

	public void setNodeBackground() {
		setBackground(Color.green);
	}

	@Override
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub
		if (tx.getText().equals("터미널 명령어 입력"))
			tx.setText("");
	}

	@Override
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub
		if (tx.getText().equals("")) {
			tx.setText("터미널 명령어 입력");
			tx.setDisabledTextColor(Color.gray);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		tx.setFocusable(true);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public int getNodeID() {
		return nodeID;
	}

	public int getLevel() {
		return level;
	}

	public int getParentNodeID() {
		return parentNodeID;
	}

}
