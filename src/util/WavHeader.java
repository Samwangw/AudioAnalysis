package util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class WavHeader {
	protected class RIFFchunk {
		private String chunkID;
		private long chunkSize;
		private String formatCode;

		public String getType() {
			return chunkID;
		}

		public void setType(String type) {
			this.chunkID = type;
		}

		public long getSize() {
			return chunkSize;
		}

		public void setSize(long size) {
			this.chunkSize = size;
		}

		public String getFmt() {
			return formatCode;
		}

		public void setFmt(String fmt) {
			this.formatCode = fmt;
		}

	}

	public class FormatChunk {
		private String mFmtHeader;
		private int mFmtHeaderSize;
		private short mEncoding;
		private short mNumChannels;
		private int mSampleRate;
		private int mByteRate;
		private short mBlockAlign;// Bytes per Sample Frame
		private short mBitsPerSample;

		public String getFmtHeader() {
			return mFmtHeader;
		}

		public void setFmtHeader(String mFmtHeader) {
			this.mFmtHeader = mFmtHeader;
		}

		public int getFmtHeaderSize() {
			return mFmtHeaderSize;
		}

		public void setFmtHeaderSize(int mFmtHeaderSize) {
			this.mFmtHeaderSize = mFmtHeaderSize;
		}

		public short getEncoding() {
			return mEncoding;
		}

		public void setEncoding(short mEncoding) {
			this.mEncoding = mEncoding;
		}

		public short getNumChannels() {
			return mNumChannels;
		}

		public void setNumChannels(short mNumChannels) {
			this.mNumChannels = mNumChannels;
		}

		public int getSampleRate() {
			return mSampleRate;
		}

		public void setSampleRate(int mSampleRate) {
			this.mSampleRate = mSampleRate;
		}

		public int getByteRate() {
			return mByteRate;
		}

		public void setByteRate(int mByteRate) {
			this.mByteRate = mByteRate;
		}

		public short getBlockAlign() {
			return mBlockAlign;
		}

		public void setBlockAlign(short mBlockAlign) {
			this.mBlockAlign = mBlockAlign;
		}

		public short getBitsPerSample() {
			return mBitsPerSample;
		}

		public void setBitsPerSample(short mBitsPerSample) {
			this.mBitsPerSample = mBitsPerSample;
		}

	}

	protected class DataChunk {
		private String mDataHeader;
		private int mDataSize;

		public String getDataHeader() {
			return mDataHeader;
		}

		public void setDataHeader(String dataHeader) {
			this.mDataHeader = dataHeader;
		}

		public int getDataSize() {
			return mDataSize;
		}

		public void setDataSize(int dataSize) {
			this.mDataSize = dataSize;
		}

	}

	private RIFFchunk _header;
	private FormatChunk _fmt;
	private DataChunk _data;

	public WavHeader() {
		this._header = new RIFFchunk();
		this._fmt = new FormatChunk();
		this._data = new DataChunk();
	}

	public RIFFchunk get_header() {
		return _header;
	}

	public FormatChunk get_fmt() {
		return _fmt;
	}

	public DataChunk get_data() {
		return _data;
	}

	public void printInfo() {
		System.out.println("Type: " + this.get_header().getType());
		System.out.println("FileSize(bytes): " + (this.get_header().getSize() + 8));
		System.out.println("Format: " + (this.get_header().getFmt()));
		System.out.println("FormatHeader: " + this.get_fmt().getFmtHeader());
		System.out.println("FormatHeaderSize(bytes): " + this.get_fmt().getFmtHeaderSize());
		System.out.println("Encoding: " + this.get_fmt().getEncoding());
		System.out.println("NumChannels: " + this.get_fmt().getNumChannels());
		System.out.println("SampleRate: " + this.get_fmt().getSampleRate());
		System.out.println("ByteRate: " + this.get_fmt().getByteRate());
		System.out.println("BlockAlign: " + this.get_fmt().getBlockAlign());
		System.out.println("BitsPerSample: " + this.get_fmt().getBitsPerSample());
		System.out.println("DataHeader: " + this.get_data().getDataHeader());
		System.out.println("DataSize: " + this.get_data().getDataSize());
	}

	/**
	 * Get the header information in HEX format from a wav file.
	 * 
	 * @param filename
	 *            A pathname string of a wav file.
	 * @return A formatted HEX header information of a wav file.
	 */
	public static String getWavHeaderString(String filename) {
		String info = "";
		if (filename == null) {
			throw new IllegalArgumentException("filename is null");
		}
		ByteArrayOutputStream ous = null;
		InputStream ios = null;
		try {
			File file = new File(filename);
			if (!file.exists()) {
				throw new FileNotFoundException();
			}
			byte[] buffer = new byte[44];
			ous = new ByteArrayOutputStream();
			ios = new FileInputStream(file);
			int read = 0;
			if ((read = ios.read(buffer)) != -1) {
				ous.write(buffer, 0, read);
			}
			String headerString = bytesToHexFun1(buffer);
			for (int pos = 0; pos < headerString.length(); pos++) {
				info += headerString.charAt(pos);
				if (pos % 24 == 23) {
					info += "\r\n";
					continue;
				}
				if (pos % 2 == 1)
					info += " | ";
			}
			System.out.println(info);
			return info;

		} catch (Exception e) {
			e.printStackTrace();
			return info;
		} finally {
			try {
				if (ous != null)
					ous.close();
			} catch (IOException e) {
			}

			try {
				if (ios != null)
					ios.close();
			} catch (IOException e) {
			}

		}
	}

	/**
	 * Get the header information from a wav file.
	 * 
	 * @param filename
	 *            A pathname string of a wav file
	 * @return Creates a new WavHeader instance by converting the given pathname
	 *         string.
	 */
	public static WavHeader getWavHeader(String filename) {
		WavHeader wheader = null;
		if (filename == null) {
			throw new IllegalArgumentException("filename is not exist");
		}
		ByteArrayOutputStream ous = null;
		InputStream ios = null;
		try {
			File file = new File(filename);
			if (!file.exists()) {
				throw new FileNotFoundException();
			}
			wheader = new WavHeader();
			byte[] buffer = new byte[44];
			ous = new ByteArrayOutputStream();
			ios = new FileInputStream(file);
			int read = 0;
			if ((read = ios.read(buffer)) != -1) {
				ous.write(buffer, 0, read);
			}

			byte[] temp = new byte[4];

			System.arraycopy(buffer, 0, temp, 0, 4);
			wheader.get_header().setType(new String(temp));

			System.arraycopy(buffer, 4, temp, 0, 4);
			ByteBuffer bbuffer = ByteBuffer.wrap(temp);
			bbuffer.order(ByteOrder.LITTLE_ENDIAN); // if you want little-endian
			long size = bbuffer.getInt();
			wheader.get_header().setSize(size);

			System.arraycopy(buffer, 8, temp, 0, 4);
			wheader.get_header().setFmt(new String(temp));

			System.arraycopy(buffer, 12, temp, 0, 4);
			wheader.get_fmt().setFmtHeader(new String(temp));

			System.arraycopy(buffer, 16, temp, 0, 4);
			ByteBuffer bb2 = ByteBuffer.wrap(temp);
			bb2.order(ByteOrder.LITTLE_ENDIAN); // if you want little-endian
			wheader.get_fmt().setFmtHeaderSize(bb2.getInt());

			System.arraycopy(buffer, 20, temp, 0, 2);
			ByteBuffer bb3 = ByteBuffer.wrap(temp);
			bb3.order(ByteOrder.LITTLE_ENDIAN); // if you want little-endian
			wheader.get_fmt().setEncoding(bb3.getShort());

			System.arraycopy(buffer, 22, temp, 0, 2);
			ByteBuffer bb4 = ByteBuffer.wrap(temp);
			bb4.order(ByteOrder.LITTLE_ENDIAN); // if you want little-endian
			wheader.get_fmt().setNumChannels(bb4.getShort());

			System.arraycopy(buffer, 24, temp, 0, 4);
			ByteBuffer bb5 = ByteBuffer.wrap(temp);
			bb5.order(ByteOrder.LITTLE_ENDIAN); // if you want little-endian
			wheader.get_fmt().setSampleRate(bb5.getInt());

			System.arraycopy(buffer, 28, temp, 0, 4);
			ByteBuffer bb6 = ByteBuffer.wrap(temp);
			bb6.order(ByteOrder.LITTLE_ENDIAN); // if you want little-endian
			wheader.get_fmt().setByteRate(bb6.getInt());

			System.arraycopy(buffer, 32, temp, 0, 2);
			ByteBuffer bb7 = ByteBuffer.wrap(temp);
			bb7.order(ByteOrder.LITTLE_ENDIAN); // if you want little-endian
			wheader.get_fmt().setBlockAlign(bb7.getShort());

			System.arraycopy(buffer, 34, temp, 0, 2);
			ByteBuffer bb8 = ByteBuffer.wrap(temp);
			bb8.order(ByteOrder.LITTLE_ENDIAN); // if you want little-endian
			wheader.get_fmt().setBitsPerSample(bb8.getShort());

			System.arraycopy(buffer, 36, temp, 0, 4);
			wheader.get_data().setDataHeader(new String(temp));

			System.arraycopy(buffer, 40, temp, 0, 4);
			ByteBuffer bb9 = ByteBuffer.wrap(temp);
			bb9.order(ByteOrder.LITTLE_ENDIAN); // if you want little-endian
			wheader.get_data().setDataSize(bb9.getInt());

			return wheader;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (ous != null)
					ous.close();
			} catch (IOException e) {
			}

			try {
				if (ios != null)
					ios.close();
			} catch (IOException e) {
			}

		}
	}

	public static String bytesToHexFun1(byte[] bytes) {
		final char[] HEX_CHAR = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		// 一个byte为8位，可用两个十六进制位标识
		char[] buf = new char[bytes.length * 2];
		int a = 0;
		int index = 0;
		for (byte b : bytes) { // 使用除与取余进行转换
			if (b < 0) {
				a = 256 + b;
			} else {
				a = b;
			}

			buf[index++] = HEX_CHAR[a / 16];
			buf[index++] = HEX_CHAR[a % 16];
		}

		return new String(buf);
	}

	public static void main(String[] args) {
		WavHeader.getWavHeader("dataset\\sample\\sunday.wav").printInfo();
	}
}
