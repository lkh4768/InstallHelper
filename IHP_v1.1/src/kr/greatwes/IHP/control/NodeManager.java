package kr.greatwes.IHP.control;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;

import javax.swing.JPanel;

import kr.greatwes.IHP.boundary.NodePanel;
import kr.greatwes.IHP.boundary.TreePanel;

public class NodeManager {
	private Map<Integer, ArrayList<NodePanel>> nodePanelMap;
	private static NodeManager nodeManagerInstant;
	private TreePanel treePanel;
	private static int END_NODE = 1;

	private NodeManager() {
		nodePanelMap = new HashMap<Integer, ArrayList<NodePanel>>();

		treePanel = TreePanel.getTreePanelInstant();
	}

	public static NodeManager getNodeManagerInstant() {
		if (nodeManagerInstant == null)
			nodeManagerInstant = new NodeManager();

		return nodeManagerInstant;
	}

	/*
	 * �����带 �߰��ϱ� ���� �ַ� level 0�� ������ �߰�
	 */
	public void addSiblingNode(int level, int parentNodeID, int nodeID, String nodeInfo) {
		JPanel targetLevelPanel = treePanel.getLevelPanelList().get(level);

		/*
		 * loadTreeFile�� �� ���� END_NODE�� �ʿ���� ������ ���� END_NODE�� Ti ������ �ε��� ���� �ƴ�
		 * �гο��� ����ڰ� �߰��� �� ���
		 */
		if (nodeID == -1)
			nodeID = END_NODE;

		NodePanel nodePanel = new NodePanel(nodeID, level, parentNodeID, nodeInfo);
		nodePanel.setPreferredSize(new Dimension(315, 150));

		// �߰��� �� nodeMap�� Null���� ������ ����
		if (nodePanelMap.get(parentNodeID) == null)
			nodePanelMap.put(parentNodeID, new ArrayList<NodePanel>());
		nodePanelMap.get(parentNodeID).add(nodePanel);

		targetLevelPanel.add(nodePanel, BorderLayout.PAGE_END);
		targetLevelPanel.revalidate();
		targetLevelPanel.repaint();

		// �̰� ���� loadTiFile�� ���õ� ����
		if (nodeID == END_NODE)
			END_NODE++;
	}

	/*
	 * node�� ���ﶧ ���� 1. ���� node�� �ڽ� ������ ��������� remove�� 2. nodePanel�� ��� �ִ�
	 * levelPanel���� �ش� nodePanel�� ���� 3. nodeMap���� �ش� nodeID�� ���� 4. ���� node��
	 * levelPanel���� nodePanel�� �ϳ��� ������ �ش� levelPanel�� ����
	 */
	public void removeNode(int level, int nodeID, int parentNodeID) {
		System.out.println(level + ", " + nodeID + ", " + parentNodeID);
		JPanel targetLevelPanel = treePanel.getLevelPanelList().get(level);

		// 1�� ����
		if (nodePanelMap.get(nodeID) != null) {
			Iterator itr = nodePanelMap.get(nodeID).iterator();
			while (itr.hasNext()) {
				removeNode(level + 1, (Integer) itr.next(), nodeID);
				itr = nodePanelMap.get(nodeID).iterator();
			}
		}

		NodePanel np = findNodePanelInNodePanelList(nodeID, parentNodeID);
		
		// 2�� ����
		targetLevelPanel.remove(np);

		// 3�� ����
		nodePanelMap.get(parentNodeID).remove(np);

		// 4�� ����
		if (targetLevelPanel.getComponentCount() == 0) {
			treePanel.remove(targetLevelPanel);
			treePanel.getLevelPanelList().remove(level);

			treePanel.revalidate();
			treePanel.repaint();
		}

		targetLevelPanel.revalidate();
		targetLevelPanel.repaint();
	}

	/*
	 * ù ��带 ������ ��� ��带 ������ �� ��� 1. ù ��带 ������ level 0�� ������ ����(�ڽĳ��鵵 �� �������� ������
	 * level 0 ��带 ����) 2. ù ����� �ڽ� ������ ����
	 */
	public void removeAllNode() {
		// 1�� ����
		Iterator itr = nodePanelMap.get(0).iterator();
		while (itr.hasNext()) {
			NodePanel np = (NodePanel) itr.next();
			if (np.getNodeID() != 1) {
				removeNode(0, np.getNodeID(), 0);
				itr = nodePanelMap.get(0).iterator();
			}
		}

		// 2�� ����
		if (nodePanelMap.get(1) != null) {
			itr = nodePanelMap.get(1).iterator();
			while (itr.hasNext()) {
				NodePanel np = (NodePanel) itr.next();
				removeNode(1, np.getNodeID(), 1);
				itr = nodePanelMap.get(1).iterator();
			}
		}

	}

	/*
	 * �ڽ� ������ �߰� �ϱ� ���� ���� 1. �ڽĳ�带 ����� ���� ���� ���� level�� +1�� ������ �Ǵ� �� ���� 2. ����
	 * level�� +1�� addSiblingNode �Լ��� ����Ͽ� �߰�
	 */
	public void addChildNode(int level, int parentNodeID, int nodeID, String nodeInfo) {
		// 1�� ����
		if (treePanel.getLevelPanelList().size() == level + 1)
			treePanel.addLevelPanel();
		// 2�� ����
		addSiblingNode(level + 1, parentNodeID, nodeID, nodeInfo);
	}

	public ArrayList<NodePanel> getChildNodeList(int key) {
		return nodePanelMap.get(key);
	}

	public void setNodePanelBackground(int nodeID) {
		Iterator itr = treePanel.getLevelPanelList().iterator();
		while (itr.hasNext()) {
			Container con = (Container) itr.next();
			if (con instanceof JPanel) {
				for (int i = 0; i < con.getComponentCount(); i++) {
					Component com = con.getComponent(i);
					if (com instanceof NodePanel)
						if (com.getName().equals(Integer.toString(nodeID)))
							((NodePanel) com).setNodeBackground();
				}
			}
		}
	}

	public NodePanel findNodePanelInNodePanelList(int nodeID, int parentNodeID) {
		Iterator itr = nodePanelMap.get(parentNodeID).iterator();
		while (itr.hasNext()) {
			NodePanel np = (NodePanel) itr.next();
			if (np.getNodeID() == nodeID)
				return np;
		}
		return null;
	}
}
