package nimbus.utils;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;

public class NimbusOutputStream extends OutputStream {

	public static final char CMD_TOKEN = '*';
	public static final char END_CMD_TOKEN = '%';
	public static final char ARGS_TOKEN = '&';
	public static final char BYTES_TOKEN = '$';
	public static final char CR = '\r';
	public static final char LF = '\n';
	public static final char[] CRLF = new char[] { CR, LF };
	private DataOutputStream strm = null;

	public NimbusOutputStream(OutputStream out) {
		strm = new DataOutputStream(new BufferedOutputStream(out));
	}

	public void prepStreamingWrite(int cmd, long numArgs) throws IOException {
		strm.writeChar(CMD_TOKEN);
		strm.writeInt(cmd);
		strm.writeChar(CR);
		strm.writeChar(LF);

		strm.writeChar(ARGS_TOKEN);
		strm.writeLong(numArgs);
		strm.writeChar(CR);
		strm.writeChar(LF);
	}

	public void streamingWrite(byte[] arg) throws IOException {
		strm.writeChar(BYTES_TOKEN);
		strm.writeInt(arg.length);
		strm.writeChar(CR);
		strm.writeChar(LF);

		strm.write(arg);
		strm.writeChar(CR);
		strm.writeChar(LF);
	}

	public void streamingWrite(String arg) throws IOException {

		byte[] bytes = BytesUtil.toBytes(arg);
		strm.writeChar(BYTES_TOKEN);
		strm.writeInt(bytes.length);
		strm.writeChar(CR);
		strm.writeChar(LF);

		strm.write(bytes);
		strm.writeChar(CR);
		strm.writeChar(LF);
	}

	public void streamingWrite(String... args) throws IOException {

		byte[] bytes = null;
		for (String arg : args) {
			bytes = BytesUtil.toBytes(arg);
			strm.writeChar(BYTES_TOKEN);
			strm.writeInt(bytes.length);
			strm.writeChar(CR);
			strm.writeChar(LF);

			strm.write(bytes);
			strm.writeChar(CR);
			strm.writeChar(LF);
		}
	}

	public void endStreamingWrite() throws IOException {
		strm.writeChar(END_CMD_TOKEN);
		strm.writeChar(CR);
		strm.writeChar(LF);
		strm.flush();
	}

	public void write(int cmd, byte[]... args) throws IOException {

		strm.writeChar(CMD_TOKEN);
		strm.writeInt(cmd);
		strm.writeChar(CR);
		strm.writeChar(LF);

		strm.writeChar(ARGS_TOKEN);
		strm.writeLong(args.length);
		strm.writeChar(CR);
		strm.writeChar(LF);

		for (byte[] arg : args) {
			strm.writeChar(BYTES_TOKEN);
			strm.writeInt(arg.length);
			strm.writeChar(CR);
			strm.writeChar(LF);

			strm.write(arg);

			strm.writeChar(CR);
			strm.writeChar(LF);
		}

		strm.writeChar(END_CMD_TOKEN);
		strm.writeChar(CR);
		strm.writeChar(LF);
		strm.flush();
	}

	public void write(int cmd, Collection<? extends String> args)
			throws IOException {

		strm.writeChar(CMD_TOKEN);
		strm.writeInt(cmd);
		strm.writeChar(CR);
		strm.writeChar(LF);

		strm.writeChar(ARGS_TOKEN);
		strm.writeLong(args.size());
		strm.writeChar(CR);
		strm.writeChar(LF);

		byte[] bytes = null;
		for (String arg : args) {
			bytes = BytesUtil.toBytes(arg);
			strm.writeChar(BYTES_TOKEN);
			strm.writeInt(bytes.length);
			strm.writeChar(CR);
			strm.writeChar(LF);

			strm.write(bytes);

			strm.writeChar(CR);
			strm.writeChar(LF);
		}

		strm.writeChar(END_CMD_TOKEN);
		strm.writeChar(CR);
		strm.writeChar(LF);
		strm.flush();
	}

	@Override
	public void write(int cmd) throws IOException {
		strm.writeChar(CMD_TOKEN);
		strm.writeInt(cmd);
		strm.writeChar(CR);
		strm.writeChar(LF);

		strm.writeChar(ARGS_TOKEN);
		strm.writeLong(0L);
		strm.writeChar(CR);
		strm.writeChar(LF);

		strm.writeChar(END_CMD_TOKEN);
		strm.writeChar(CR);
		strm.writeChar(LF);
		strm.flush();
	}

	public void write(int cmd, String... args) throws IOException {
		strm.writeChar(CMD_TOKEN);
		strm.writeInt(cmd);
		strm.writeChar(CR);
		strm.writeChar(LF);

		strm.writeChar(ARGS_TOKEN);
		strm.writeLong(args.length);
		strm.writeChar(CR);
		strm.writeChar(LF);

		byte[] bytes = null;
		for (String arg : args) {
			bytes = BytesUtil.toBytes(arg);
			strm.writeChar(BYTES_TOKEN);
			strm.writeInt(bytes.length);
			strm.writeChar(CR);
			strm.writeChar(LF);

			strm.write(bytes);
			strm.writeChar(CR);
			strm.writeChar(LF);
		}

		strm.writeChar(END_CMD_TOKEN);
		strm.writeChar(CR);
		strm.writeChar(LF);
		strm.flush();
	}
	
	@Override
	public void flush() throws IOException {
		strm.flush();
	}
}