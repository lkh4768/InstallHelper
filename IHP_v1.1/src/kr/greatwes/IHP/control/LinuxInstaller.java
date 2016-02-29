package kr.greatwes.IHP.control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class LinuxInstaller extends Installer {
	private String password;

	public LinuxInstaller(String command, int nodeID, String password) {
		// TODO Auto-generated constructor stub
		this.command = command;
		this.nodeID = nodeID;
		this.password = password;
	}

	public void run() {
		try {
			Process proc = Runtime.getRuntime().exec(command);
			proc.getOutputStream().write((password + "\n").getBytes());
			proc.getOutputStream().flush();

			// Read the output
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					proc.getInputStream()));

			String line = "";
			while ((line = reader.readLine()) != null) {
				System.out.print(line + "\n");
			}

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
