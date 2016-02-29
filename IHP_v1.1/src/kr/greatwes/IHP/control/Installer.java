package kr.greatwes.IHP.control;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

public abstract class Installer extends Thread {
	protected InstallManager installManager;
	protected NodeManager nodeManager;
	protected String command;
	protected int nodeID;

	public Installer() {
		// TODO Auto-generated constructor stub
		installManager = InstallManager.getInstallManagerInstant();
		nodeManager = NodeManager.getNodeManagerInstant();
	}

	/*
	 * factory method pattern 사용하여 구현. osName을 이용하여 상이한 클래스를 생성.
	 */
	public static Installer createInstaller(String osName, String command,
			int nodeID, String password) {
		if (osName.indexOf("windows") != -1) {
			return new WindowsInstaller(command, nodeID);
		} else if (osName.indexOf("linux") != -1) {
				return new LinuxInstaller(command, nodeID, password);
		}
		return null;
	}
}
