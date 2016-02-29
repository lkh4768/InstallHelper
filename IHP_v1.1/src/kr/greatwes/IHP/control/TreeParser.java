package kr.greatwes.IHP.control;

import java.awt.Component;
import java.awt.Container;
import java.util.Iterator;

import kr.greatwes.IHP.boundary.NodePanel;
import kr.greatwes.IHP.boundary.TreePanel;

public class TreeParser {
	private NodeManager nodeManager;

	/*
	 * node의 내용을 저장
	 * 저장형식은 xml을 본따서 구성함
	 */
	public String parseToTI() {
		TreePanel treePanel = TreePanel.getTreePanelInstant();
		nodeManager = NodeManager.getNodeManagerInstant();

		Iterator itr = treePanel.getLevelPanelList().iterator();
		String str = "";

		while (itr.hasNext()) {
			Container con = (Container) itr.next();
			for (int i = 0; i < con.getComponentCount(); i++) {
				Component com = con.getComponent(i);
				if (com instanceof NodePanel) {
					str = str + "<node>\n";
					str = str + "<nodeID>" + ((NodePanel)com).getNodeID() + "</nodeID>\n";
					str = str + "<levelID>" + ((NodePanel)com).getLevel() + "</levelID>\n";
					str = str + "<parentNodeID>" + ((NodePanel)com).getParentNodeID() + "</parentNodeID>\n";
					str = str + "<nodeInfo>" + ((NodePanel)com).getNodeInfo()
							+ "</nodeInfo>\n</node>\n";
				}
			}
		}
		return str;
	}

	/*
	 * ti 파일의 내용을 UI에 적용 시키기 위함
	 */
	public void parseToTree(String ti) {
		nodeManager = NodeManager.getNodeManagerInstant();

		nodeManager.removeAllNode();

		String subTi;
		String nodeID;
		String levelID;
		String parentNodeID;
		String nodeInfo;

		while (ti.length() > 0) {
			subTi = ti.substring(ti.indexOf("<node>") + 6, ti.indexOf("</node>"));
			ti = ti.substring(ti.indexOf("</node>") + 7);

			nodeID = subTi.substring(subTi.indexOf("<nodeID>") + 8, subTi.indexOf("</nodeID>"));
			levelID = subTi.substring(subTi.indexOf("<levelID>") + 9, subTi.indexOf("</levelID>"));
			parentNodeID = subTi.substring(subTi.indexOf("<parentNodeID>") + 14, subTi.indexOf("</parentNodeID>"));
			nodeInfo = subTi.substring(subTi.indexOf("<nodeInfo>") + 10, subTi.indexOf("</nodeInfo>"));

			//System.out.println("node : " + nodeID + ", " + levelID + ", " + parentNodeID + ", " + nodeInfo);

			if (levelID.equals("0")) {
				if (!nodeID.equals("1"))
					nodeManager.addSiblingNode(Integer.parseInt(levelID), Integer.parseInt(parentNodeID),
							Integer.parseInt(nodeID), nodeInfo);
			} else
				nodeManager.addChildNode(Integer.parseInt(levelID)-1, Integer.parseInt(parentNodeID),
						Integer.parseInt(nodeID), nodeInfo);
		}

	}
}
