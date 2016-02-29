package kr.greatwes.IHP.boundary;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.xml.ws.handler.MessageContext.Scope;

import kr.greatwes.IHP.control.NodeManager;

public class MainFrame extends JFrame {
	public MainFrame() {
		// TODO Auto-generated constructor stub
		setSize(1000, 500);
		setTitle("Install Helper");
		setResizable(false);

		TreePanel treePanel = TreePanel.getTreePanelInstant();
		JScrollPane scrollPane = new JScrollPane(treePanel);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

		ControlPanel controlPanel = new ControlPanel();

		treePanel.setPreferredSize(new Dimension(740, 1000));
		controlPanel.setPreferredSize(new Dimension(240, 500));

		treePanel.addLevelPanel();

		NodeManager nodeManager = NodeManager.getNodeManagerInstant();
		nodeManager.addSiblingNode(0, 0, -1, "");

		add(scrollPane, BorderLayout.WEST);
		add(controlPanel, BorderLayout.EAST);

		setVisible(true);
	}

}
