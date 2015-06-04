package lanSimulation.internals;

public class WorkStation extends Node {

	public WorkStation(byte type, String name) {
		super(type, name);
		// TODO Auto-generated constructor stub
	}

	public WorkStation(byte type, String name, Node nextNode) {
		super(type, name, nextNode);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Prints node information.
	 * 
	 * @param buf
	 *            Buffer
	 */
	public void printOn(StringBuffer buf) {
		buf.append("Workstation ");
		buf.append(name_);
		buf.append(" [Workstation]");
	}

	/**
	 * Prints node information in XML.
	 * 
	 * @param buf
	 *            Buffer.
	 */
	public void printXMLOn(StringBuffer buf) {
		buf.append("<workstation>");
		buf.append(name_);
		buf.append("</workstation>");
	}

}
