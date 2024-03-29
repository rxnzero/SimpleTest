public class QuickSort {
    static final int MAX_SIZE = 9;

    // 피벗을 기준으로 2개의 부분 리스트로 나눈다.
    // 피벗보다 작은 값은 모두 왼쪽 부분 리스트로, 큰 값은 오른쪽 부분 리스트로 옮긴다.
    // 2개의 비균등 배열 list[left...pivot-1]와 list[pivot+1...right]의 합병 과정
    // (실제로 숫자들이 정렬되는 과정)
    static int partition(int[] list, int left, int right) {
    	System.out.printf(">> left=%s right=%s\n", left, right);
        int pivot, temp;
        int low, high;

        low = left;
        high = right + 1;
        pivot = list[left]; // 정렬할 리스트의 가장 왼쪽 데이터를 피벗으로 선택(임의의 값을 피벗으로 선택)

        /* low와 high가 교차할 때까지 반복(low<high) */
        do {
            /* list[low]가 피벗보다 작으면 계속 low를 증가 */
            do {
                low++; // low는 left+1 에서 시작
            } while (low <= right && list[low] < pivot);

            /* list[high]가 피벗보다 크면 계속 high를 감소 */
            do {
                high--; //high는 right 에서 시작
            } while (high >= left && list[high] > pivot);

            // 만약 low와 high가 교차하지 않았으면 list[low]를 list[high] 교환
            if (low < high) {
                temp = list[low];
                list[low] = list[high];
                list[high] = temp;
                System.out.printf("# low=%s high=%s\n", low, high);
                printList(list);
            }
        } while (low < high);

        // low와 high가 교차했으면 반복문을 빠져나와 list[left]와 list[high]를 교환
        temp = list[left];
        list[left] = list[high];
        list[high] = temp;
        System.out.printf("# left=%s high=%s\n", left, high);
        printList(list);
        
        // 피벗의 위치인 high를 반환
        return high;
    }

    // 퀵 정렬
    static void quickSort(int[] list, int left, int right) {
        /* 정렬할 범위가 2개 이상의 데이터이면(리스트의 크기가 0이나 1이 아니면) */
        if (left < right) {
            // partition 함수를 호출하여 피벗을 기준으로 리스트를 비균등 분할 -분할(Divide)
            int q = partition(list, left, right); // q: 피벗의 위치

            // 피벗은 제외한 2개의 부분 리스트를 대상으로 순환 호출
            quickSort(list, left, q - 1); // (left ~ 피벗 바로 앞) 앞쪽 부분 리스트 정렬 -정복(Conquer)
            quickSort(list, q + 1, right); // (피벗 바로 뒤 ~ right) 뒤쪽 부분 리스트 정렬 -정복(Conquer)
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
        
        // 퀵 정렬 수행(left: 배열의 시작 = 0, right: 배열의 끝 = n-1)
        quickSort(list, 0, n - 1);

        // 정렬 결과 출력
        printList(list);        
    }
}