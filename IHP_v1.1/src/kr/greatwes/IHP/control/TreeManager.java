package kr.greatwes.IHP.control;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;

public class TreeManager {
	public void saveTreeFile(String saveFilePath) {
		String str;
		TreeParser tp = new TreeParser();
		str = tp.parseToTI();

		/*
		 * parse�� str�� �����ϱ� ���� ����
		 */
		try {
			BufferedWriter output = new BufferedWriter(new FileWriter(saveFilePath + ".ti"));
			output.write(str);
			output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void loadTreeFile(String loadFilePath) {
		String str = "";
		String line;
		BufferedReader br;
		
		/*
		 * ti������ ������ ���پ��о�鿩 ��ü ������ str�� ����
		 */
		try {
			br = new BufferedReader(new FileReader(loadFilePath));
			while ((line = br.readLine()) != null)
				str = str + line;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(str);
		TreeParser tp = new TreeParser();
		tp.parseToTree(str);
	}
}
