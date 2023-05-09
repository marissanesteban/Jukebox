package edu.sandiego.comp375.jukebox;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Scanner;
import java.lang.Exception;

<<<<<<< HEAD
enum MessageType {
  PLAY, INFO, LIST, STOP, ILLEGAL;

  private static final MessageType values[] = values();

	/**
	 * Returns a MessageType based on the given integer. Returns ILLEGAL type
	 * if the integer isn't valid.
	 */
	public static MessageType get(int ordinal) { 
		if (ordinal >= values.length) {
			return MessageType.ILLEGAL;
		}
		return values[ordinal];
	}
=======
enum MESSAGE {
  PLAY,
  INFO,
  LIST,
  STOP,
  BAD_REQ,
  SONG_LEN,
  INFO_DATA,
  LIST_DATA
}

public class Header {
	int song_num;
	MESSAGE type;
>>>>>>> 5e58157d8f8a31a642490980ee0fd7d677fa122b
}

/**
 * Class representing a client to our Jukebox.
 */
public class AudioClient {
	public static void main(String[] args) throws Exception {
		Scanner s = new Scanner(System.in);
		BufferedInputStream in = null;
		Thread player = null;

		System.out.println("Client: Connecting to localhost (127.0.0.1) port 6666");

		while (true) {
			System.out.print(">> ");
			String command = s.nextLine();
			String[] command = command.split(" ");
			Socket socket = new Socket("127.0.0.1", 6666); // moved this outside the if (command) statements
			if (command[0].equals("play")) {
				try {
					if (socket.isConnected()) {
						in = new BufferedInputStream(socket.getInputStream(), 2048); // QUESTION: what is in

						// checking that the song number is a number
						try {
							int song_num = (int)command[0];
							sendHeader(socket, MessageType.PLAY, song_num);

<<<<<<< HEAD
							// keep calling getMessage until 
							while (getMessage(socket));
							
								//serverSocket.close();
=======
							Header hdr = new Header();
							hdr.song_num = song_num;
							hdr.type = PLAY;

							// put the header in a buffer and send it
							// QUESTION: how do we send a message?

							// we wait to receive a message from server with the byte size of the song
							// continue receiving until the recv_count is equal to ^^
							// utilize 'in'?

>>>>>>> 5e58157d8f8a31a642490980ee0fd7d677fa122b
						} catch (Exception e) {
							System.out.println(e);
						}

						player = new Thread(new AudioPlayerThread(in));
						player.start();
					}
				}
				catch (Exception e) {
					System.out.println(e);
				}
			}
			else if (command[0].equals("exit")) {
				// Currently this doesn't actually stop the music from
				// playing.
				// Your final solution should make sure that the exit command
				// causes music to stop playing immediately.
				System.out.println("Goodbye!");
				break;
			}
			else if (command[0].equals("list")) {
				
				break;
			}
			else if (command[0].equals("info")) {
				
				break;
			}
			else if (command[0].equals("stop")) {
				
				break;
			}
			else {
				System.err.println("ERROR: unknown command");
			}
		}

		System.out.println("Client: Exiting");
	}
	/**
	 * Creates and sends a message header.
	 *
	 * @param s The socket to send the data over
	 * @param messageType The type of message
	 * @param songNumber The size (in bytes) of the message (not including the header)
	 */
	public static void sendHeader(Socket s, MessageType messageType, int songNumber) throws IOException {
		// Creates a ByteBuffer, which is a convenient class for turning
		// different types of values into their byte representations.
		// The byte order is set to BIG_ENDIAN, because that is the standard
		// format for data sent of a network.
		ByteBuffer header = ByteBuffer.allocate(5);
		header.order(ByteOrder.BIG_ENDIAN);


		// send the header
		header.put((byte)messageType.ordinal()); // ordinal gets the number
												 // associated with the
												 // MessageType
		header.putInt(songNumber);

		// use basic output stream to write header 
		OutputStream o = s.getOutputStream();
		o.write(header.array());
	}
		/**
	 * Reads a message from the socket.
	 *
	 * @param s The Socket to read data from
	 * @return False if there are no more messages to receive. True otherwise.
	 */
	public static boolean getMessage(Socket s) throws IOException {
		DataInputStream in = new DataInputStream(s.getInputStream());
		MessageType response_type = MessageType.get(in.readByte());
		int data_len = in.readInt();

		if (response_type == MessageType.PLAY) {
			byte[] res = s.getInputStream().readNBytes(data_len);
			String response_str = new String(res);
			System.out.println(response_str);
			return true;
		}
		else if (response_type == MessageType.INFO) {
			System.out.println("Server said goodbye!");
			return false;
		}
		else if (response_type == MessageType.LIST) {
			System.out.println("Server said goodbye!");
			return false;
		}
		else if (response_type == MessageType.STOP) {
			System.out.println("Server said goodbye!");
			return false;
		}
		else if (response_type == MessageType.ILLEGAL) {
			System.out.println("Server said goodbye!");
			return false;
		}
		else {
			// Something weird happened here...
			return false;
		}
	}
}
