package kr.greatwes.IHP.control;

import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JOptionPane;

import kr.greatwes.IHP.boundary.NodePanel;

public class InstallManager {
	private static InstallManager installManagerInstant;
	private NodeManager nodeManager;
	private String osName;
	private String password;

	/*
	 * singelton pattern�� ���.
	 */
	private InstallManager() {
		osName = System.getProperty("os.name").toLowerCase();
		System.out.println(osName);

		nodeManager = NodeManager.getNodeManagerInstant();
	}

	public static InstallManager getInstallManagerInstant() {
		if (installManagerInstant == null)
			installManagerInstant = new InstallManager();

		return installManagerInstant;
	}
	
	/*
	 * parentNodeID�� ��� �ڽĳ����� ����.
	 */
	public void install(int parentNodeID) {
		
		if (nodeManager.getChildNodeList(parentNodeID) != null) {
			ArrayList<NodePanel> childNodePanelList = nodeManager.getChildNodeList(parentNodeID);
			Iterator itr = childNodePanelList.iterator();
			while (itr.hasNext()) {
				NodePanel np = (NodePanel) itr.next();
				String nodeInfo = np.getNodeInfo();
				if(nodeInfo.equals("�͹̳� ��ɾ� �Է�")){
					JOptionPane.showMessageDialog(null,
							np.getNodeID()+"�� ����� �Է¶��� ä���ּ���",
						    "�Է¶��� ä���ּ���",
						    JOptionPane.ERROR_MESSAGE);
				}
				
				System.out.println("nodeID : " + np.getNodeID() + ", nodeInfo : " + nodeInfo);
				Installer installer = Installer.createInstaller(osName, nodeInfo, np.getNodeID(), password);
				installer.start();
			}
		}
	}
	
	public void setPassword(String password){
		this.password = password;
	}
	
	public String getOsName(){
		return osName;
	}
}
