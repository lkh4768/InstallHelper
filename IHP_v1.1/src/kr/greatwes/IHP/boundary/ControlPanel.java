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
		installBtn = new JButton("��ġ");
		saveTreeFileBtn = new JButton("Ʈ�� ����");
		loadTreeFileBtn = new JButton("Ʈ�� �ҷ�����");
		initTreeBtn = new JButton("�ʱ�ȭ");

		rootPanel.setLayout(new BoxLayout(rootPanel, BoxLayout.Y_AXIS));
		rootPanel.add(Box.createVerticalStrut(10)); // BoxLayout���� ���鼳��
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
				JButton btn = new JButton("Ȯ��");

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
			JFileChooser jfc = new JFileChooser(); // ���ϰ�����(����Ž����)
			FileNameExtensionFilter filter = new FileNameExtensionFilter(
					"ti ����", "ti"); // Ȯ���ڰ� ti�ΰ͸� ���̵��� ����

			jfc.setFileFilter(filter);
			jfc.setFileSelectionMode(JFileChooser.FILES_ONLY); // ���ϸ� �����ֵ��� ����
			jfc.showDialog(this, null);
			File file = jfc.getSelectedFile();

			TreeManager tm = new TreeManager();
			tm.saveTreeFile(file.getPath());
		} else if (e.getSource() == loadTreeFileBtn) {
			JFileChooser jfc = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter(
					"ti ����", "ti");

			jfc.setFileFilter(filter);
			jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			jfc.showDialog(this, null);
			File file = jfc.getSelectedFile();

			TreeManager tm = new TreeManager();
			tm.loadTreeFile(file.getPath());
		}

	}
}
