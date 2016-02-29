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
		 * parse된 str를 저장하기 위한 설정
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
		 * ti파일의 내용을 한줄씩읽어들여 전체 내용을 str에 저장
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
