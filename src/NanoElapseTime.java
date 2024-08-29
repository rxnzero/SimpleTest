
public class NanoElapseTime {

	public NanoElapseTime() {
		// TODO Auto-generated constructor stub
	}
	
	public static void main(String[] argv) {
		// 작업 시작 시간 기록
        long startTime = System.nanoTime();
        
        // 여기에 수행할 작업을 삽입하세요.
        // 예를 들어, 간단한 반복 작업을 수행한다고 가정합니다.
        int sum = 0;
//        for (int i = 0; i < 1000000; i++) {
//            sum += i;
//        }
        
        // 작업 종료 시간 기록
        long endTime = System.nanoTime();
        
        // 시간 측정
        long duration = (endTime - startTime); // 나노초 단위로 수행 시간을 얻습니다.
        
        // 결과 출력
        System.out.println("작업 수행 시간: " + duration + " 나노초");
	}
}
