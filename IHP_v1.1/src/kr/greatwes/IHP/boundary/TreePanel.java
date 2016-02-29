package kr.greatwes.IHP.boundary;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class TreePanel extends JPanel {
	private static TreePanel treePanelInstant;
	private ArrayList<JPanel> levelPanelList;

	private TreePanel() {
		// TODO Auto-generated constructor stub
		levelPanelList = new ArrayList<JPanel>();
	}

	public static TreePanel getTreePanelInstant() {
		if (treePanelInstant == null)
			treePanelInstant = new TreePanel();
		return treePanelInstant;
	}

	public void addLevelPanel() {
		JPanel levelPanel = new JPanel();
		JScrollPane sp = new JScrollPane(levelPanel);
		sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);

		levelPanel.setName(Integer.toString(levelPanelList.size()));
		sp.setName(Integer.toString(levelPanelList.size()));
		levelPanelList.add(levelPanel);

		sp.setPreferredSize(new Dimension(725, 180));  //View에 추가하기 전에 크기 설정
		add(sp, BorderLayout.NORTH);
		revalidate();
		repaint();
	}

	public ArrayList<JPanel> getLevelPanelList() {
		return levelPanelList;
	}
}
