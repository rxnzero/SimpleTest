public class QuickSort {
    static final int MAX_SIZE = 9;

    // �ǹ��� �������� 2���� �κ� ����Ʈ�� ������.
    // �ǹ����� ���� ���� ��� ���� �κ� ����Ʈ��, ū ���� ������ �κ� ����Ʈ�� �ű��.
    // 2���� ��յ� �迭 list[left...pivot-1]�� list[pivot+1...right]�� �պ� ����
    // (������ ���ڵ��� ���ĵǴ� ����)
    static int partition(int[] list, int left, int right) {
    	System.out.printf(">> left=%s right=%s\n", left, right);
        int pivot, temp;
        int low, high;

        low = left;
        high = right + 1;
        pivot = list[left]; // ������ ����Ʈ�� ���� ���� �����͸� �ǹ����� ����(������ ���� �ǹ����� ����)

        /* low�� high�� ������ ������ �ݺ�(low<high) */
        do {
            /* list[low]�� �ǹ����� ������ ��� low�� ���� */
            do {
                low++; // low�� left+1 ���� ����
            } while (low <= right && list[low] < pivot);

            /* list[high]�� �ǹ����� ũ�� ��� high�� ���� */
            do {
                high--; //high�� right ���� ����
            } while (high >= left && list[high] > pivot);

            // ���� low�� high�� �������� �ʾ����� list[low]�� list[high] ��ȯ
            if (low < high) {
                temp = list[low];
                list[low] = list[high];
                list[high] = temp;
                System.out.printf("# low=%s high=%s\n", low, high);
                printList(list);
            }
        } while (low < high);

        // low�� high�� ���������� �ݺ����� �������� list[left]�� list[high]�� ��ȯ
        temp = list[left];
        list[left] = list[high];
        list[high] = temp;
        System.out.printf("# left=%s high=%s\n", left, high);
        printList(list);
        
        // �ǹ��� ��ġ�� high�� ��ȯ
        return high;
    }

    // �� ����
    static void quickSort(int[] list, int left, int right) {
        /* ������ ������ 2�� �̻��� �������̸�(����Ʈ�� ũ�Ⱑ 0�̳� 1�� �ƴϸ�) */
        if (left < right) {
            // partition �Լ��� ȣ���Ͽ� �ǹ��� �������� ����Ʈ�� ��յ� ���� -����(Divide)
            int q = partition(list, left, right); // q: �ǹ��� ��ġ

            // �ǹ��� ������ 2���� �κ� ����Ʈ�� ������� ��ȯ ȣ��
            quickSort(list, left, q - 1); // (left ~ �ǹ� �ٷ� ��) ���� �κ� ����Ʈ ���� -����(Conquer)
            quickSort(list, q + 1, right); // (�ǹ� �ٷ� �� ~ right) ���� �κ� ����Ʈ ���� -����(Conquer)
        }
    }
    
    private static void printList(int[] list) {
    	int n = list.length;
    	for (int i = 0; i < n; i++) {
            System.out.print(list[i] + " ");
        }
        System.out.print("\n");        
    }
    public static void main(String[] args) {
        int[] list = {5, 3, 8, 4, 9, 1, 6, 2, 7};
        int n = list.length;
        
        printList(list);
        
        // �� ���� ����(left: �迭�� ���� = 0, right: �迭�� �� = n-1)
        quickSort(list, 0, n - 1);

        // ���� ��� ���
        printList(list);        
    }
}