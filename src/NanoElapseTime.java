
public class NanoElapseTime {

	public NanoElapseTime() {
		// TODO Auto-generated constructor stub
	}
	
	public static void main(String[] argv) {
		// �۾� ���� �ð� ���
        long startTime = System.nanoTime();
        
        // ���⿡ ������ �۾��� �����ϼ���.
        // ���� ���, ������ �ݺ� �۾��� �����Ѵٰ� �����մϴ�.
        int sum = 0;
//        for (int i = 0; i < 1000000; i++) {
//            sum += i;
//        }
        
        // �۾� ���� �ð� ���
        long endTime = System.nanoTime();
        
        // �ð� ����
        long duration = (endTime - startTime); // ������ ������ ���� �ð��� ����ϴ�.
        
        // ��� ���
        System.out.println("�۾� ���� �ð�: " + duration + " ������");
	}
}
