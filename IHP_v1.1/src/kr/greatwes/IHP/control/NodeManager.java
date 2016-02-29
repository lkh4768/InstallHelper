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
	 * 동기노드를 추가하기 위함 주로 level 0의 노드들을 추가
	 */
	public void addSiblingNode(int level, int parentNodeID, int nodeID, String nodeInfo) {
		JPanel targetLevelPanel = treePanel.getLevelPanelList().get(level);

		/*
		 * loadTreeFile을 할 때는 END_NODE가 필요없기 때문에 설정 END_NODE는 Ti 파일을 로드할 때가 아닌
		 * 패널에서 사용자가 추가할 때 사용
		 */
		if (nodeID == -1)
			nodeID = END_NODE;

		NodePanel nodePanel = new NodePanel(nodeID, level, parentNodeID, nodeInfo);
		nodePanel.setPreferredSize(new Dimension(315, 150));

		// 추가할 때 nodeMap의 Null에러 때문에 설정
		if (nodePanelMap.get(parentNodeID) == null)
			nodePanelMap.put(parentNodeID, new ArrayList<NodePanel>());
		nodePanelMap.get(parentNodeID).add(nodePanel);

		targetLevelPanel.add(nodePanel, BorderLayout.PAGE_END);
		targetLevelPanel.revalidate();
		targetLevelPanel.repaint();

		// 이것 또한 loadTiFile과 관련된 설정
		if (nodeID == END_NODE)
			END_NODE++;
	}

	/*
	 * node를 지울때 설정 1. 지울 node의 자식 노드들을 재귀적으로 remove함 2. nodePanel을 담고 있는
	 * levelPanel에서 해당 nodePanel을 삭제 3. nodeMap에서 해당 nodeID을 삭제 4. 지울 node의
	 * levelPanel에서 nodePanel이 하나도 없으면 해당 levelPanel을 삭제
	 */
	public void removeNode(int level, int nodeID, int parentNodeID) {
		System.out.println(level + ", " + nodeID + ", " + parentNodeID);
		JPanel targetLevelPanel = treePanel.getLevelPanelList().get(level);

		// 1번 실행
		if (nodePanelMap.get(nodeID) != null) {
			Iterator itr = nodePanelMap.get(nodeID).iterator();
			while (itr.hasNext()) {
				removeNode(level + 1, (Integer) itr.next(), nodeID);
				itr = nodePanelMap.get(nodeID).iterator();
			}
		}

		NodePanel np = findNodePanelInNodePanelList(nodeID, parentNodeID);
		
		// 2번 실행
		targetLevelPanel.remove(np);

		// 3번 실행
		nodePanelMap.get(parentNodeID).remove(np);

		// 4번 실행
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
	 * 첫 노드를 제외한 모든 노드를 삭제할 때 사용 1. 첫 노드를 제외한 level 0의 노드들을 삭제(자식노드들도 다 지워지기 때문에
	 * level 0 노드를 삭제) 2. 첫 노드의 자식 노드들을 삭제
	 */
	public void removeAllNode() {
		// 1번 실행
		Iterator itr = nodePanelMap.get(0).iterator();
		while (itr.hasNext()) {
			NodePanel np = (NodePanel) itr.next();
			if (np.getNodeID() != 1) {
				removeNode(0, np.getNodeID(), 0);
				itr = nodePanelMap.get(0).iterator();
			}
		}

		// 2번 실행
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
	 * 자식 노드들을 추가 하기 위해 설정 1. 자식노드를 만들기 위해 먼저 현재 level의 +1의 유무를 판단 후 생성 2. 현재
	 * level의 +1에 addSiblingNode 함수를 사용하여 추가
	 */
	public void addChildNode(int level, int parentNodeID, int nodeID, String nodeInfo) {
		// 1번 실행
		if (treePanel.getLevelPanelList().size() == level + 1)
			treePanel.addLevelPanel();
		// 2번 실행
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
