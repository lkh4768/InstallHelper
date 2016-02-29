package kr.greatwes.IHP.control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class WindowsInstaller extends Installer {

	public WindowsInstaller(String command, int nodeID) {
		// TODO Auto-generated constructor stub
		this.command = command;
		this.nodeID = nodeID;
	}

	public void run() {
		try {
			Process proc = Runtime.getRuntime().exec(command);  //process는 terminal에 command라는 명령을 실행하기 위함

			Thread.sleep(7000);
			
			while (true) {
				if (!proc.isAlive()) {
					proc.destroy();
					installManager.install(nodeID);
					nodeManager.setNodePanelBackground(nodeID);
					currentThread().interrupt();
					return;
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
