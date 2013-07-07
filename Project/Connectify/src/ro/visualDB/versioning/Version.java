package ro.visualDB.versioning;

import ro.visualDB.remotes.Remote;

public class Version {
	private String version;
	private Remote remote;
	
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public Remote getRemote() {
		return remote;
	}
	public void setRemote(Remote remote) {
		this.remote = remote;
	}
	
	@Override
	public String toString() {
		return version;
	}
}
