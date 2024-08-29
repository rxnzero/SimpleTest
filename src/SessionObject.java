
public class SessionObject {
	private String name;
	private String sessionId;
	public SessionObject() {

	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	@Override
	public String toString() {
		return "SessionObject [name=" + name + ", sessionId=" + sessionId + "]";
	}
	
}
