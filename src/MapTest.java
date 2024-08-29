import java.util.concurrent.ConcurrentHashMap;

class MapTest {
    private final ConcurrentHashMap<String, SessionObject> sessionMap;

    public MapTest() {
        sessionMap = new ConcurrentHashMap<>();
    }

    public static void main(String[] args) {
        MapTest test = new MapTest();

        // Thread 1: Put operation
        Runnable putTask = () -> {
            SessionObject session = new SessionObject();
            session.setName("test");
            session.setSessionId("testID");
            test.putSession("1234", session);
            System.out.println("Put operation completed by " + Thread.currentThread().getName());
            try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
            session.setName(null);
            session.setSessionId(null);
            System.out.println("Put operation reset to NUL:L completed by " + Thread.currentThread().getName());
        };

        // Thread 2: Get operation
        Runnable getTask = () -> {
            try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
            SessionObject session = test.getSession("1234");
            System.out.println("Get operation by " + Thread.currentThread().getName() + ": " + session);
            try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
            SessionObject session2 = test.getSession("1234");
            System.out.println("Get operation by " + Thread.currentThread().getName() + ": " + session2);
        };

        // Create threads
        Thread putThread = new Thread(putTask, "Thread-1");
        Thread getThread = new Thread(getTask, "Thread-2");

        // Start threads simultaneously
        putThread.start();
        getThread.start();

        // Wait for both threads to finish
        try {
            putThread.join();
            getThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void putSession(String sessionId, SessionObject session) {
        sessionMap.put(sessionId, session);
    }

    public SessionObject getSession(String sessionId) {
        return sessionId != null ? sessionMap.get(sessionId) : null;
    }
}