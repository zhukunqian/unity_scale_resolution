package com.unity3d.player;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

class WWW extends Thread {
	private int a;
	private int b;
	private String c;
	private byte[] d;
	private Map<String, String> e;

	WWW(final int b, final String c, final byte[] d, final Map e) {
		this.b = b;
		this.c = c;
		this.d = d;
		this.e = e;
		this.a = 0;
		this.start();
	}

	@Override
	public void run() {
		if (++this.a > 5) {
			errorCallback(this.b, "Too many redirects");
			return;
		}
		URL url;
		URLConnection openConnection;
		try {
			openConnection = (url = new URL(this.c)).openConnection();
		} catch (MalformedURLException ex) {
			errorCallback(this.b, ex.toString());
			return;
		} catch (IOException ex2) {
			errorCallback(this.b, ex2.toString());
			return;
		}
		if (url.getProtocol().equalsIgnoreCase("file") && url.getHost() != null
				&& url.getHost().length() != 0) {
			errorCallback(this.b, url.getHost() + url.getFile()
					+ " is not an absolute path!");
			return;
		}
		if (this.e != null) {
			for (final Map.Entry<String, String> entry : this.e.entrySet()) {
				openConnection.addRequestProperty(entry.getKey(),
						(String) entry.getValue());
			}
		}
		int n = 1428;
		if (this.d != null) {
			openConnection.setDoOutput(true);
			try {
				final OutputStream outputStream = openConnection
						.getOutputStream();
				int i = 0;
				while (i < this.d.length) {
					final int min = Math.min(1428, this.d.length - i);
					outputStream.write(this.d, i, min);
					this.progressCallback(i += min, this.d.length, 0, 0, 0L, 0L);
				}
			} catch (Exception ex3) {
				errorCallback(this.b, ex3.toString());
				return;
			}
		}
		if (openConnection instanceof HttpURLConnection) {
			final HttpURLConnection httpURLConnection = (HttpURLConnection) openConnection;
			int responseCode;
			try {
				responseCode = httpURLConnection.getResponseCode();
			} catch (IOException ex4) {
				errorCallback(this.b, ex4.toString());
				return;
			}
			final Map<String, List<String>> headerFields;
			final List<String> list;
			if ((headerFields = httpURLConnection.getHeaderFields()) != null
					&& (responseCode == 301 || responseCode == 302)
					&& (list = headerFields.get("Location")) != null
					&& !list.isEmpty()) {
				httpURLConnection.disconnect();
				this.c = list.get(0);
				this.run();
				return;
			}
		}
		final Map<String, List<String>> headerFields2 = openConnection
				.getHeaderFields();
		boolean headerCallback = this.headerCallback(headerFields2);
		if ((headerFields2 == null || !headerFields2
				.containsKey("content-length"))
				&& openConnection.getContentLength() != -1) {
			headerCallback = (headerCallback || this.headerCallback(
					"content-length",
					String.valueOf(openConnection.getContentLength())));
		}
		if ((headerFields2 == null || !headerFields2
				.containsKey("content-type"))
				&& openConnection.getContentType() != null) {
			headerCallback = (headerCallback || this.headerCallback(
					"content-type", openConnection.getContentType()));
		}
		if (headerCallback) {
			errorCallback(this.b, this.c + " aborted");
			return;
		}
		final int n2 = (openConnection.getContentLength() > 0) ? openConnection
				.getContentLength() : 0;
		if (url.getProtocol().equalsIgnoreCase("file")
				|| url.getProtocol().equalsIgnoreCase("jar")) {
			n = ((n2 == 0) ? 32768 : Math.min(n2, 32768));
		}
		int n3 = 0;
		try {
			final long currentTimeMillis = System.currentTimeMillis();
			final byte[] array = new byte[n];
			final InputStream inputStream = openConnection.getInputStream();
			for (int j = 0; j != -1; j = inputStream.read(array)) {
				if (this.readCallback(array, j)) {
					errorCallback(this.b, this.c + " aborted");
					return;
				}
				n3 += j;
				this.progressCallback(0, 0, n3, n2, System.currentTimeMillis(),
						currentTimeMillis);
			}
		} catch (Exception ex5) {
			errorCallback(this.b, ex5.toString());
			return;
		}
		this.progressCallback(0, 0, n3, n3, 0L, 0L);
		doneCallback(this.b);
	}

	private static native boolean headerCallback(final int p0, final String p1);

	protected boolean headerCallback(final Map map) {
		if (map == null || map.size() == 0) {
			return false;
		}
		final StringBuilder sb = new StringBuilder();
		final Iterator<Map.Entry<String, List<String>>> iterator = map
				.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, List<String>> o = iterator.next();
			for (final String s : o.getValue()) {
				sb.append(o.getKey());
				sb.append(": ");
				sb.append(s);
				sb.append("\n\r");
			}
		}
		return headerCallback(this.b, sb.toString());
	}

	protected boolean headerCallback(final String s, final String s2) {
		final StringBuilder sb;
		(sb = new StringBuilder()).append(s);
		sb.append(": ");
		sb.append(s2);
		sb.append("\n\r");
		return headerCallback(this.b, sb.toString());
	}

	private static native boolean readCallback(final int p0, final byte[] p1,
			final int p2);

	protected boolean readCallback(final byte[] array, final int n) {
		return readCallback(this.b, array, n);
	}

	private static native void progressCallback(final int p0, final float p1,
			final float p2, final double p3, final int p4);

	protected void progressCallback(final int n, int max, final int n2,
			final int n3, final long n4, final long n5) {
		float n6;
		float n7;
		double n8;
		if (n3 > 0) {
			n6 = n2 / n3;
			n7 = 1.0f;
			max = Math.max(n3 - n2, 0);
			if (Double.isInfinite(n8 = max
					/ (1000.0 * n2 / Math.max(n4 - n5, 0.1)))
					|| Double.isNaN(n8)) {
				n8 = 0.0;
			}
		} else {
			if (max <= 0) {
				return;
			}
			n6 = 0.0f;
			n7 = n / max;
			n8 = 0.0;
		}
		progressCallback(this.b, n7, n6, n8, n3);
	}

	private static native void errorCallback(final int p0, final String p1);

	private static native void doneCallback(final int p0);
}
