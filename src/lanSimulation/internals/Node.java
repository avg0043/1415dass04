/*   This file is part of lanSimulation.
 *
 *   lanSimulation is free software; you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation; either version 2 of the License, or
 *   (at your option) any later version.
 *
 *   lanSimulation is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with lanSimulation; if not, write to the Free Software
 *   Foundation, Inc. 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 *
 *   Copyright original Java version: 2004 Bart Du Bois, Serge Demeyer
 *   Copyright C++ version: 2006 Matthias Rieger, Bart Van Rompaey
 */
package lanSimulation.internals;

import java.io.IOException;
import java.io.Writer;

import lanSimulation.Network;

/**
A <em>Node</em> represents a single Node in a Local Area Network (LAN).
Several types of Nodes exist.
 */
public class Node {
	//enumeration constants specifying all legal node types
	/**
    A node with type NODE has only basic functionality.
	 */
	public static final byte NODE = 0;
	/**
    A node with type WORKSTATION may initiate requests on the LAN.
	 */
	public static final byte WORKSTATION = 1;
	/**
    A node with type PRINTER may accept packages to be printed.
	 */
	public static final byte PRINTER = 2;

	/**
    Holds the type of the Node.
	 */
	public byte type_;
	/**
    Holds the name of the Node.
	 */
	public String name_;
	/**
    Holds the next Node in the token ring architecture.
    @see lanSimulation.internals.Node
	 */
	public Node nextNode_;

	/**
Construct a <em>Node</em> with given #type and #name.
<p><strong>Precondition:</strong> (type >= NODE) & (type <= PRINTER);</p>
	 */
	public Node(byte type, String name) {
		assert (type >= NODE) & (type <= PRINTER);
		type_ = type;
		name_ = name;
		nextNode_ = null;
	}

	/**
Construct a <em>Node</em> with given #type and #name, and which is linked to #nextNode.
<p><strong>Precondition:</strong> (type >= NODE) & (type <= PRINTER);</p>
	 */
	public Node(byte type, String name, Node nextNode) {
		assert (type >= NODE) & (type <= PRINTER);
		type_ = type;
		name_ = name;
		nextNode_ = nextNode;
	}

	/**
	 * Prints logging.
	 * 
	 * @param report
	 *            Writer
	 * @throws IOException
	 *             excepci�n.
	 */
	public void printLogging(Writer report) throws IOException {
		report.write("\tNode '");
		report.write(name_);
		report.write("' passes packet on.\n");
		report.flush();
	}

	public boolean printDocument(Network network, Packet document, Writer report) {
		String author = "Unknown";
		String title = "Untitled";
		int startPos = 0, endPos = 0;
	
		if (type_ == Node.PRINTER) {
			try {
				if (document.message_.startsWith("!PS")) {
					startPos = document.message_.indexOf("author:");
					if (startPos >= 0) {
						endPos = document.message_.indexOf(".", startPos + 7);
						if (endPos < 0) {
							endPos = document.message_.length();
						}
						;
						author = document.message_.substring(startPos + 7,
								endPos);
					}
					;
					startPos = document.message_.indexOf("title:");
					if (startPos >= 0) {
						endPos = document.message_.indexOf(".", startPos + 6);
						if (endPos < 0) {
							endPos = document.message_.length();
						}
						;
						title = document.message_.substring(startPos + 6,
								endPos);
					}
					;
					network.printAccounting(report, author, title);
					report.write(">>> Postscript job delivered.\n\n");
					report.flush();
				} else {
					title = "ASCII DOCUMENT";
					if (document.message_.length() >= 16) {
						author = document.message_.substring(8, 16);
					}
					;
					network.printAccounting(report, author, title);
					report.write(">>> ASCII Print job delivered.\n\n");
					report.flush();
				}
				;
			} catch (IOException exc) {
				// just ignore
			}
			;
			return true;
		} else {
			try {
				report.write(">>> Destinition is not a printer, print job cancelled.\n\n");
				report.flush();
			} catch (IOException exc) {
				// just ignore
			}
			;
			return false;
		}
	}

	/**
	 * Prints node information.
	 * @param buf Buffer
	 */
	public void printNodeInformation(StringBuffer buf) {
		switch (type_) {
		case Node.NODE:
			buf.append("Node ");
			buf.append(name_);
			buf.append(" [Node]");
			break;
		case Node.WORKSTATION:
			buf.append("Workstation ");
			buf.append(name_);
			buf.append(" [Workstation]");
			break;
		case Node.PRINTER:
			buf.append("Printer ");
			buf.append(name_);
			buf.append(" [Printer]");
			break;
		default:
			buf.append("(Unexpected)");
			;
			break;
		}
		;
	}

}