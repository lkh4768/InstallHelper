package kr.greatwes.IHP.boundary;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import kr.greatwes.IHP.control.InstallManager;
import kr.greatwes.IHP.control.NodeManager;
import kr.greatwes.IHP.control.TreeManager;

public class ControlPanel extends JPanel implements ActionListener {
	private JButton installBtn;
	private JButton saveTreeFileBtn;
	private JButton loadTreeFileBtn;
	private JButton initTreeBtn;

	private NodeManager nodeManager;
	private InstallManager installManager;

	public ControlPanel() {
		// TODO Auto-generated constructor stub
		JPanel rootPanel = new JPanel();
		installBtn = new JButton("설치");
		saveTreeFileBtn = new JButton("트리 저장");
		loadTreeFileBtn = new JButton("트리 불러오기");
		initTreeBtn = new JButton("초기화");

		rootPanel.setLayout(new BoxLayout(rootPanel, BoxLayout.Y_AXIS));
		rootPanel.add(Box.createVerticalStrut(10)); // BoxLayout에서 공백설정
		rootPanel.add(installBtn);
		rootPanel.add(Box.createVerticalStrut(20));
		rootPanel.add(saveTreeFileBtn);
		rootPanel.add(Box.createVerticalStrut(20));
		rootPanel.add(loadTreeFileBtn);
		rootPanel.add(Box.createVerticalStrut(20));
		rootPanel.add(initTreeBtn);

		add(rootPanel);

		installBtn.addActionListener(this);
		saveTreeFileBtn.addActionListener(this);
		loadTreeFileBtn.addActionListener(this);
		initTreeBtn.addActionListener(this);

		nodeManager = NodeManager.getNodeManagerInstant();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == initTreeBtn) {
			nodeManager.removeAllNode();
		} else if (e.getSource() == installBtn) {
			installManager = InstallManager.getInstallManagerInstant();

			if (installManager.getOsName().equals("linux")) {
				final JFrame f = new JFrame();
				final JTextField tf = new JTextField(20);
				JButton btn = new JButton("확인");

				f.setLayout(new FlowLayout());
				f.add(tf);
				f.add(btn);
				f.setSize(300, 50);
				f.setVisible(true);

				btn.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						installManager.setPassword(tf.getText());
						installManager.install(0);
						f.dispose();
					}
				});
			} else
				installManager.install(0);

		} else if (e.getSource() == saveTreeFileBtn) {
			JFileChooser jfc = new JFileChooser(); // 파일관리자(파일탐색기)
			FileNameExtensionFilter filter = new FileNameExtensionFilter(
					"ti 파일", "ti"); // 확장자가 ti인것만 보이도록 설정

			jfc.setFileFilter(filter);
			jfc.setFileSelectionMode(JFileChooser.FILES_ONLY); // 파일만 열수있도록 설정
			jfc.showDialog(this, null);
			File file = jfc.getSelectedFile();

			TreeManager tm = new TreeManager();
			tm.saveTreeFile(file.getPath());
		} else if (e.getSource() == loadTreeFileBtn) {
			JFileChooser jfc = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter(
					"ti 파일", "ti");

			jfc.setFileFilter(filter);
			jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			jfc.showDialog(this, null);
			File file = jfc.getSelectedFile();

			TreeManager tm = new TreeManager();
			tm.loadTreeFile(file.getPath());
		}

	}
}
