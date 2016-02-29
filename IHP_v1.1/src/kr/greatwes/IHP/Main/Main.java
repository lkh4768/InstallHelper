package kr.greatwes.IHP.Main;

import kr.greatwes.IHP.boundary.MainFrame;

/*
 * 1.1v 업데이트 내용
 * 1. nodePanel의 속성 증가(nodeID) : TreeParser의 load, save의 불필요한 메소드를 삭제.
 * 2. nodePanel의 생성자 변수 증가 : 생성할 때 노드의 모든 속성이 필요하므로 따로 설정 메소드를 없애고 생성자에 인자를 포함하여 생성.
 * 3. nodePanel을 집적적으로 접근 가능.  nodeManager을 통해서 접근하는 것은 불필요한
 * 메소드 증가인 것으로 판단하여 직접적으로 접근할 수 있는 건 nodePanel에 집적 접근.
 */

public class Main {

	public static void main(String[] args) {
		MainFrame mf = new MainFrame();
	}
}
